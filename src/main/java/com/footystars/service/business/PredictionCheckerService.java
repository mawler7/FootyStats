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

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PredictionCheckerService {

    private final Logger log = LoggerFactory.getLogger(PredictionCheckerService.class);
    private final PredictionRepository predictionRepository;
    private final FixtureService fixtureService;

    @Nullable
    public Boolean checkAdvice(@NotNull Prediction prediction, Fixture fixture) {
        var advice = prediction.getPredictions().getAdvice();
        if (advice == null || advice.equals("No predictions available")) {
            return null;
        }

        if (advice.contains("Double chance")) {
            return checkDoubleChance(advice, fixture);
        } else if (advice.contains("Combo Double chance")) {
            return checkComboDoubleChance(advice, fixture);
        } else if (advice.contains("Winner")) {
            return checkWinner(advice, fixture);
        }

        return false;
    }

    @NotNull
    private Boolean checkDoubleChance(@NotNull String advice, @NotNull Fixture fixture) {
        var parts = advice.split(":");
        var options = parts[1].trim().split(" or ");
        var homeGoals = fixture.getGoals() != null ? fixture.getGoals().getHome() : null;
        var awayGoals = fixture.getGoals() != null ? fixture.getGoals().getAway() : null;

        if (homeGoals == null || awayGoals == null) {
            return false;
        }

        var isDraw = Objects.equals(homeGoals, awayGoals);
        var isHomeWin = fixture.getTeams().getHomeTeam().getHomeName().equalsIgnoreCase(options[1].trim()) && homeGoals > awayGoals;
        var isAwayWin = fixture.getTeams().getAwayTeam().getAwayName().equalsIgnoreCase(options[1].trim()) && awayGoals > homeGoals;

        return isDraw || isHomeWin || isAwayWin;
    }

    @NotNull
    private Boolean checkComboDoubleChance(@NotNull String advice, Fixture fixture) {
        var parts = advice.split("and");
        var doubleChanceCorrect = checkDoubleChance(parts[0], fixture);

        var goalsCondition = parts[1].trim();
        return doubleChanceCorrect && checkGoalsCondition(goalsCondition, fixture);
    }

    @NotNull
    private Boolean checkGoalsCondition(@NotNull String condition, @NotNull Fixture fixture) {
        var totalGoals = fixture.getGoals() != null ? fixture.getGoals().getHome() + fixture.getGoals().getAway() : 0;

        if (condition.contains("+")) {
            var threshold = Double.parseDouble(condition.split("\\+")[1].trim());
            return totalGoals >= threshold;
        } else if (condition.contains("-")) {
            var threshold = Double.parseDouble(condition.split("\\-")[1].trim());
            return totalGoals <= threshold;
        }

        return false;
    }

    @NotNull
    private Boolean checkWinner(@NotNull String advice, @NotNull Fixture fixture) {
        var parts = advice.split(":");
        var predictedWinner = parts[1].trim();
        var homeGoals = fixture.getGoals() != null ? fixture.getGoals().getHome() : null;
        var awayGoals = fixture.getGoals() != null ? fixture.getGoals().getAway() : null;

        if (homeGoals == null || awayGoals == null) {
            return false;
        }

        if (fixture.getTeams().getHomeTeam().getHomeName().equalsIgnoreCase(predictedWinner)) {
            return homeGoals > awayGoals;
        } else if (fixture.getTeams().getAwayTeam().getAwayName().equalsIgnoreCase(predictedWinner)) {
            return awayGoals > homeGoals;
        }

        return false;
    }

    @Nullable
    private Boolean checkHomeGoalsPrediction(@NotNull Prediction prediction, @NotNull Fixture fixture) {
        if (prediction.getPredictions().getGoals().getHomePrediction() == null) {
            return null;
        }

        var homeGoalsPrediction = prediction.getPredictions().getGoals().getHomePrediction();
        if (fixture.getGoals() != null && fixture.getGoals().getHome() != null) {
            int actualHomeGoals = fixture.getGoals().getHome();
            return evaluateGoalsPrediction(homeGoalsPrediction, actualHomeGoals);
        } else {
            return null;
        }
    }

    @Nullable
    private Boolean checkAwayGoalsPrediction(@NotNull Prediction prediction, @NotNull Fixture fixture) {

        if (prediction.getPredictions().getGoals().getAwayPrediction() == null) {
            return null;
        }

        var awayGoalsPrediction = prediction.getPredictions().getGoals().getAwayPrediction();
        if (fixture.getGoals() != null && fixture.getGoals().getAway() != null) {
            int actualAwayGoals = fixture.getGoals().getAway();
            return evaluateGoalsPrediction(awayGoalsPrediction, actualAwayGoals);
        } else {
            return null;
        }
    }

    @Nullable
    private Boolean checkOverUnderPrediction(@NotNull Prediction prediction, @NotNull Fixture fixture) {
        // Sprawdzamy, czy prediction.getPredictions().getUnderOver() nie jest null
        var underOverPrediction = prediction.getPredictions().getUnderOver();
        if (underOverPrediction == null) {
            return null;
        }

        var totalGoals = fixture.getGoals() != null ? fixture.getGoals().getHome() + fixture.getGoals().getAway() : 0;

        // Sprawdzamy, czy predykcja jest dodatnia (over) czy ujemna (under)
        if (underOverPrediction.contains("+")) {
            // "Over" - całkowita liczba bramek musi być większa niż próg
            var threshold = Double.parseDouble(underOverPrediction.split("\\+")[1].trim());
            return totalGoals > threshold;
        } else if (underOverPrediction.contains("-")) {
            // "Under" - całkowita liczba bramek musi być mniejsza niż próg
            var threshold = Double.parseDouble(underOverPrediction.split("\\-")[1].trim());
            return totalGoals < threshold;
        } else {
            return false;
        }
    }


    @NotNull
    private Boolean evaluateGoalsPrediction(@NotNull String goalsPrediction, int actualGoals) {
        if (goalsPrediction.contains("+")) {
            var threshold = Double.parseDouble(goalsPrediction.split("\\+")[1].trim());
            return actualGoals >= threshold;
        } else if (goalsPrediction.contains("-")) {
            var threshold = Double.parseDouble(goalsPrediction.split("\\-")[1].trim());
            return actualGoals <= threshold;
        } else {
            return actualGoals == Integer.parseInt(goalsPrediction.trim());
        }
    }

    public void updatePredictionResult(Prediction prediction, Fixture fixture) {
        var adviceCorrect = checkAdvice(prediction, fixture);
        var overUnderCorrect = checkOverUnderPrediction(prediction, fixture);

        if (prediction.getPredictions().getGoals() != null) {
            if (prediction.getPredictions().getGoals().getHomePrediction() != null) {
                var homeGoalsCorrect = checkHomeGoalsPrediction(prediction, fixture);
                prediction.setHomeGoals(homeGoalsCorrect);
            }
            if (prediction.getPredictions().getGoals().getAwayPrediction() != null) {
                var awayGoalsCorrect = checkAwayGoalsPrediction(prediction, fixture);
                prediction.setAwayGoals(awayGoalsCorrect);
            }
        }

        prediction.setAdvice(adviceCorrect);
        prediction.setOverUnder(overUnderCorrect);
        predictionRepository.save(prediction);
    }

    @Async
    public void updatePredictionResult(@NotNull List<Long> uncheckedPredictions) {
        uncheckedPredictions.parallelStream().forEach(f -> {
            var optionalFixture = fixtureService.findById(f);
            if (optionalFixture.isPresent()) {
                var fixture = optionalFixture.get();
                var prediction = fixture.getPrediction();
                updatePredictionResult(prediction, fixture);
            }
        });
        log.info(LogsNames.PREDICTIONS_CHECKED);
    }
}
