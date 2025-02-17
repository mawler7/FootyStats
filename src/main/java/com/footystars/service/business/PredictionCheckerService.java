package com.footystars.service.business;

import com.footystars.model.entity.Fixture;
import com.footystars.model.entity.Prediction;
import com.footystars.persistence.repository.PredictionRepository;
import com.footystars.utils.LogsNames;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.footystars.utils.LogsNames.PREDICTIONS_CHECKED;

/**
 * Service responsible for verifying and updating prediction results based on match outcomes.
 */
@Service
@RequiredArgsConstructor
public class PredictionCheckerService {

    private final Logger log = LoggerFactory.getLogger(PredictionCheckerService.class);
    private final PredictionRepository predictionRepository;
    private final FixtureService fixtureService;

    /**
     * Checks whether a given prediction advice is correct based on match results.
     *
     * @param prediction The prediction entity containing advice.
     * @param fixture    The fixture entity with match results.
     * @return True if the advice is correct, false otherwise, or null if no advice is available.
     */
    @Nullable
    public Boolean checkAdvice(@NotNull Prediction prediction, Fixture fixture) {
        var advice = prediction.getPredictions().getAdvice();
        if (advice == null || advice.equals("No predictions available")) {
            return null;
        }

        if (advice.contains("Combo Double chance")) {
            return checkComboDoubleChance(advice, fixture);
        } else if (advice.contains("Combo Winner")) {
            return checkComboWinner(advice, fixture);
        } else if (advice.contains("Double chance")) {
            return checkDoubleChance(advice, fixture);
        } else if (advice.contains("Winner")) {
            return checkWinner(advice, fixture);
        }

        return false;
    }


    /**
     * Verifies the correctness of a "Double chance" prediction.
     * Przyjmujemy, że advice ma format:
     * "Double chance : Option1 or Option2"
     * gdzie Option1 i Option2 to dwie możliwe odpowiedzi, np. "Rizespor" oraz "draw".
     * <p>
     * Predykcja jest trafiona, jeśli:
     * - Jeśli padł remis i którakolwiek z opcji to "draw", lub
     * - Jeśli padła wygrana gospodarzy i któraś z opcji odpowiada nazwie gospodarzy, lub
     * - Jeśli padła wygrana gości i któraś z opcji odpowiada nazwie gości.
     *
     * @param advice  The prediction advice.
     * @param fixture The fixture entity with match results.
     * @return True if the prediction is correct, otherwise false.
     */
    @NotNull
    private Boolean checkDoubleChance(@NotNull String advice, @NotNull Fixture fixture) {
        String[] parts = advice.split(":");
        if (parts.length < 2) {
            return false;
        }
        String optionsPart = parts[1].trim();
        String[] options = optionsPart.split(" or ");
        if (options.length < 2) {
            return false;
        }
        String option1 = options[0].trim().toLowerCase();
        String option2 = options[1].trim().toLowerCase();

        Integer homeGoals = fixture.getGoals() != null ? fixture.getGoals().getHome() : null;
        Integer awayGoals = fixture.getGoals() != null ? fixture.getGoals().getAway() : null;
        if (homeGoals == null || awayGoals == null) {
            return false;
        }

        boolean homeWin = homeGoals > awayGoals;
        boolean awayWin = awayGoals > homeGoals;
        boolean draw = Objects.equals(homeGoals, awayGoals);

        String homeTeamName = fixture.getTeams().getHomeTeam().getHomeName().toLowerCase();
        String awayTeamName = fixture.getTeams().getAwayTeam().getAwayName().toLowerCase();

        if (draw && (option1.equals("draw") || option2.equals("draw"))) {
            return true;
        }
        if (homeWin && (option1.equals(homeTeamName) || option2.equals(homeTeamName))) {
            return true;
        }
        if (awayWin && (option1.equals(awayTeamName) || option2.equals(awayTeamName))) {
            return true;
        }

        return false;
    }

    /**
     * Verifies the correctness of a "Combo Double chance" prediction.
     * Zakładamy, że advice ma format:
     * "Combo Double chance : Option1 or Option2 and <goals condition>"
     * Najpierw sprawdzamy, czy część dotyczącą double chance (przed "and") jest trafiona,
     * a następnie czy warunek goli jest spełniony.
     *
     * @param advice  The prediction advice.
     * @param fixture The fixture entity with match results.
     * @return True if the prediction is correct, otherwise false.
     */
    @NotNull
    private Boolean checkComboDoubleChance(@NotNull String advice, Fixture fixture) {
        // Usuń prefiks "Combo Double chance :" (można dodać obsługę innych wariantów, np. z różnymi wielkościami liter)
        String cleanedAdvice = advice.replaceFirst("(?i)Combo Double chance\\s*:\\s*", "").trim();
        // Rozdzielamy ciąg na część dotyczącą double chance oraz warunek goli.
        String[] parts = cleanedAdvice.split("and");
        if (parts.length < 2) {
            return false;
        }
        // Upewnijmy się, że do metody checkDoubleChance przekazujemy ciąg zaczynający się od "Double chance :"
        String doubleChanceAdvice = "Double chance : " + parts[0].trim();
        boolean doubleChanceCorrect = checkDoubleChance(doubleChanceAdvice, fixture);
        String goalsCondition = parts[1].replaceAll("(?i)goals", "").trim();
        return doubleChanceCorrect && checkGoalsCondition(goalsCondition, fixture);
    }

    /**
     * Sprawdza, czy predykcja Combo Winner jest poprawna.
     * Zakładamy, że advice ma format:
     * "Combo Winner : <predicted team> and <goals condition>"
     * np. "Combo Winner : Real Madrid and +1.5 goals"
     * <p>
     * Predykcja jest trafiona, jeśli:
     * - przewidywany zwycięzca (po usunięciu części "and ...") jest zgodny z wynikiem meczu,
     * - oraz warunek dotyczący goli jest spełniony.
     *
     * @param advice  The prediction advice.
     * @param fixture The fixture entity with match results.
     * @return True if both winner and goals condition are correct, otherwise false.
     */
    @NotNull
    private Boolean checkComboWinner(@NotNull String advice, Fixture fixture) {
        // Usuń prefiks "Combo Winner :" (ignorujemy wielkość liter)
        String cleanedAdvice = advice.replaceFirst("(?i)Combo Winner\\s*:\\s*", "").trim();
        // Dzielimy ciąg na dwie części po słowie "and"
        String[] parts = cleanedAdvice.split("and");
        if (parts.length < 2) {
            return false;
        }
        // Pierwsza część – przewidywany zwycięzca
        String predictedWinner = parts[0].trim().toLowerCase();
        // Druga część – warunek goli (usuń słowo "goals")
        String goalsCondition = parts[1].replaceAll("(?i)goals", "").trim();

        // Pobieramy wyniki meczu
        Integer homeGoals = fixture.getGoals() != null ? fixture.getGoals().getHome() : null;
        Integer awayGoals = fixture.getGoals() != null ? fixture.getGoals().getAway() : null;
        if (homeGoals == null || awayGoals == null) {
            return false;
        }
        // Pobieramy nazwy drużyn
        String homeTeam = fixture.getTeams().getHomeTeam().getHomeName().toLowerCase();
        String awayTeam = fixture.getTeams().getAwayTeam().getAwayName().toLowerCase();

        boolean winnerCorrect = false;
        if (predictedWinner.equals(homeTeam)) {
            winnerCorrect = homeGoals > awayGoals;
        } else if (predictedWinner.equals(awayTeam)) {
            winnerCorrect = awayGoals > homeGoals;
        }

        boolean goalsConditionCorrect = checkGoalsCondition(goalsCondition, fixture);

        return winnerCorrect && goalsConditionCorrect;
    }

    /**
     * Evaluates the correctness of a goals-based prediction.
     * Jeśli condition zawiera "+" – trafione, gdy suma goli >= threshold,
     * jeśli zawiera "-" – trafione, gdy suma goli <= threshold.
     *
     * @param condition The condition to verify (e.g., "+1.5", "-2.5").
     * @param fixture   The fixture entity with match results.
     * @return True if the condition holds, otherwise false.
     */
    @NotNull
    private Boolean checkGoalsCondition(@NotNull String condition, @NotNull Fixture fixture) {
        int totalGoals = fixture.getGoals() != null
                ? fixture.getGoals().getHome() + fixture.getGoals().getAway() : 0;

        if (condition.contains("+")) {
            double threshold = Double.parseDouble(condition.split("\\+")[1].trim());
            return totalGoals >= threshold;
        } else if (condition.contains("-")) {
            double threshold = Double.parseDouble(condition.split("\\-")[1].trim());
            return totalGoals <= threshold;
        }
        return false;
    }

    /**
     * Checks if a "Winner" prediction is correct.
     * Zakładamy, że advice ma format:
     * "Winner : <predicted team>"
     *
     * @param advice  The prediction advice.
     * @param fixture The fixture entity with match results.
     * @return True if the prediction is correct, otherwise false.
     */
    @NotNull
    private Boolean checkWinner(@NotNull String advice, @NotNull Fixture fixture) {
        String[] parts = advice.split(":");
        if (parts.length < 2) {
            return false;
        }
        String predictedWinner = parts[1].trim().toLowerCase();
        Integer homeGoals = fixture.getGoals() != null ? fixture.getGoals().getHome() : null;
        Integer awayGoals = fixture.getGoals() != null ? fixture.getGoals().getAway() : null;
        if (homeGoals == null || awayGoals == null) {
            return false;
        }
        String homeTeam = fixture.getTeams().getHomeTeam().getHomeName().toLowerCase();
        String awayTeam = fixture.getTeams().getAwayTeam().getAwayName().toLowerCase();

        if (predictedWinner.equals(homeTeam)) {
            return homeGoals > awayGoals;
        } else if (predictedWinner.equals(awayTeam)) {
            return awayGoals > homeGoals;
        }
        return false;
    }

    /**
     * Checks if the home team goals prediction is correct.
     *
     * @param prediction The prediction entity.
     * @param fixture    The fixture entity with actual results.
     * @return True if the prediction is correct, false otherwise, or null if no prediction exists.
     */
    @Nullable
    private Boolean checkHomeGoalsPrediction(@NotNull Prediction prediction, @NotNull Fixture fixture) {
        if (prediction.getPredictions().getGoals().getHomePrediction() == null) {
            return null;
        }
        String homeGoalsPrediction = prediction.getPredictions().getGoals().getHomePrediction();
        if (fixture.getGoals() != null && fixture.getGoals().getHome() != null) {
            int actualHomeGoals = fixture.getGoals().getHome();
            return evaluateGoalsPrediction(homeGoalsPrediction, actualHomeGoals);
        } else {
            return null;
        }
    }

    /**
     * Evaluates whether the Over/Under prediction is correct based on the actual total goals in the match.
     *
     * @param prediction The prediction entity containing the Over/Under prediction.
     * @param fixture    The fixture entity with actual match results.
     * @return True if the prediction is correct, false if incorrect, or null if no prediction is available.
     */
    @Nullable
    private Boolean checkOverUnderPrediction(@NotNull Prediction prediction, @NotNull Fixture fixture) {
        String underOverPrediction = prediction.getPredictions().getUnderOver();
        if (underOverPrediction == null) {
            return null;
        }
        int totalGoals = fixture.getGoals() != null
                ? fixture.getGoals().getHome() + fixture.getGoals().getAway() : 0;
        if (underOverPrediction.contains("+")) {
            double threshold = Double.parseDouble(underOverPrediction.split("\\+")[1].trim());
            return totalGoals > threshold;
        } else if (underOverPrediction.contains("-")) {
            double threshold = Double.parseDouble(underOverPrediction.split("\\-")[1].trim());
            return totalGoals < threshold;
        } else {
            return false;
        }
    }

    /**
     * Evaluates whether the predicted goal count is correct based on actual goals scored.
     *
     * @param goalsPrediction The predicted number of goals, which may be an exact value or a threshold (e.g., "+2.5", "-1.5").
     * @param actualGoals     The actual number of goals scored.
     * @return True if the prediction matches the actual goals, false otherwise.
     */
    @NotNull
    private Boolean evaluateGoalsPrediction(@NotNull String goalsPrediction, int actualGoals) {
        if (goalsPrediction.contains("+")) {
            double threshold = Double.parseDouble(goalsPrediction.split("\\+")[1].trim());
            return actualGoals >= threshold;
        } else if (goalsPrediction.contains("-")) {
            double threshold = Double.parseDouble(goalsPrediction.split("\\-")[1].trim());
            return actualGoals <= threshold;
        } else {
            return actualGoals == Integer.parseInt(goalsPrediction.trim());
        }
    }

    /**
     * Checks if the away team goals prediction is correct.
     *
     * @param prediction The prediction entity.
     * @param fixture    The fixture entity with actual results.
     * @return True if the prediction is correct, false otherwise, or null if no prediction exists.
     */
    @Nullable
    private Boolean checkAwayGoalsPrediction(@NotNull Prediction prediction, @NotNull Fixture fixture) {
        if (prediction.getPredictions().getGoals().getAwayPrediction() == null) {
            return null;
        }
        String awayGoalsPrediction = prediction.getPredictions().getGoals().getAwayPrediction();
        if (fixture.getGoals() != null && fixture.getGoals().getAway() != null) {
            int actualAwayGoals = fixture.getGoals().getAway();
            return evaluateGoalsPrediction(awayGoalsPrediction, actualAwayGoals);
        } else {
            return null;
        }
    }

    /**
     * Updates the prediction result in the database.
     *
     * @param prediction The prediction entity.
     * @param fixture    The fixture entity with match results.
     */
    public void updatePredictionResult(Prediction prediction, Fixture fixture) {
        Boolean adviceCorrect = checkAdvice(prediction, fixture);
        Boolean overUnderCorrect = checkOverUnderPrediction(prediction, fixture);

        if (prediction.getPredictions().getGoals() != null) {
            if (prediction.getPredictions().getGoals().getHomePrediction() != null) {
                Boolean homeGoalsCorrect = checkHomeGoalsPrediction(prediction, fixture);
                prediction.setHomeGoals(homeGoalsCorrect);
            }
            if (prediction.getPredictions().getGoals().getAwayPrediction() != null) {
                Boolean awayGoalsCorrect = checkAwayGoalsPrediction(prediction, fixture);
                prediction.setAwayGoals(awayGoalsCorrect);
            }
        }

        prediction.setAdvice(adviceCorrect);
        prediction.setOverUnder(overUnderCorrect);
        predictionRepository.save(prediction);

        if (isFixtureFinished(fixture)) {
            fixture.setPredictionsUpdated(true);
            fixtureService.save(fixture);
        }
    }

    private boolean isFixtureFinished(@NotNull Fixture fixture) {
        if (fixture.getInfo() == null || fixture.getInfo().getStatus() == null || fixture.getInfo().getStatus().getShortStatus() == null) {
            return false;
        }
        String status = fixture.getInfo().getStatus().getShortStatus().toUpperCase();
        return status.equals("FT") || status.equals("PEN") || status.equals("AET");
    }

    /**
     * Asynchronously updates prediction results for a list of fixture IDs.
     */
    @Async
    public void updatePredictionResult() {
        List<Long> uncheckedFixtureIds = fixtureService.findUncheckedPredictions();

        uncheckedFixtureIds.parallelStream().forEach(fixtureId -> {
            Optional<Fixture> optionalFixture = fixtureService.findById(fixtureId);
            if (optionalFixture.isPresent()) {
                Fixture fixture = optionalFixture.get();
                Prediction prediction = fixture.getPrediction();
                if (prediction != null) {
                    updatePredictionResult(prediction, fixture);

                }
                log.info(PREDICTIONS_CHECKED);
            }
        });
    }
}
