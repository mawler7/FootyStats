package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.predictions.response.PredictionApi;
import com.footystars.foot8.api.model.predictions.response.predictions.PredictionDetails;
import com.footystars.foot8.api.model.predictions.response.predictions.info.Goals;
import com.footystars.foot8.api.model.predictions.response.predictions.info.Winner;
import com.footystars.foot8.business.model.entity.Fixture;
import com.footystars.foot8.business.model.entity.Lineup;
import com.footystars.foot8.business.model.entity.Prediction;
import com.footystars.foot8.business.model.entity.Team;
import com.footystars.foot8.business.model.entity.TeamStats;
import com.footystars.foot8.mapper.PredictionMapper;
import com.footystars.foot8.repository.FixtureRepository;
import com.footystars.foot8.repository.PredictionRepository;
import com.footystars.foot8.openai.OpenAIClient;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PredictionService {

    private final FixtureService fixtureService;
    private final PredictionMapper predictionMapper;
    private final PredictionRepository predictionRepository;
    private final FixtureRepository fixtureRepository;
    private final OpenAIClient openAIClient;

    private final Logger log = LoggerFactory.getLogger(PredictionService.class);

    @Transactional
    public void fetchPredictions(@NotNull PredictionApi predictionApi, @NotNull Long fixtureId) {
        try {
            var optionalFixture = fixtureService.findById(fixtureId);
            if (optionalFixture.isPresent()) {
                var fixtureEntity = optionalFixture.get();

                    var h2h = new HashSet<Fixture>();
                    predictionApi.getH2h().forEach(f -> {
                        var id = f.getFixture().getId();
                        var optionalFixtureH2h = fixtureService.findById(id);

                        if (optionalFixtureH2h.isPresent()) {
                            var fixture = optionalFixtureH2h.get();
                            h2h.add(fixture);
                        }
                    });

                    var predictionDto = predictionMapper.apiToDto(predictionApi);
                    var newPrediction  = predictionMapper.toEntity(predictionDto);
                    newPrediction.setFixturesH2H(h2h);

                if (fixtureEntity.getPrediction() == null) {
                    var savedPrediction = predictionRepository.save(newPrediction);
                    fixtureEntity.setPrediction(savedPrediction);
                    fixtureService.save(fixtureEntity);
                    log.info("Predictions saved for fixture id {}", fixtureId);
                } else {
                    var existingPrediction = fixtureEntity.getPrediction();
                    if (!existingPrediction.getPredictions().equals(newPrediction.getPredictions())) {
                        newPrediction.setId(existingPrediction.getId());
                        newPrediction.setLastUpdated(ZonedDateTime.now());
                        var savedPrediction = predictionRepository.save(newPrediction);
                        fixtureEntity.setPrediction(savedPrediction);
                        fixtureService.save(fixtureEntity);
                        log.info("Predictions updated for fixture id {}", fixtureId);
                    } else {
                        log.info("Predictions are identical, no update required for fixture id {}", fixtureId);
                    }
                }
            }
        } catch (Exception e) {
           log.error(e.getMessage(), e);
        }
    }

    public String getPredictionsForToday() {
        List<Fixture> fixtures = fixtureRepository.findTodayFixturesWithDetails();
        String prompt = formatDetailedMatchDataForPrompt(fixtures);

        try {
            return openAIClient.getPrediction(prompt);
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to retrieve predictions.";
        }
    }

    public String formatDetailedMatchDataForPrompt(List<Fixture> fixtures) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Please analyze the following football match data and provide predictions:\n\n");

        for (Fixture fixture : fixtures) {
            Team homeTeam = Optional.ofNullable(fixture.getHomeTeam()).orElse(new Team());
            Team awayTeam = Optional.ofNullable(fixture.getAwayTeam()).orElse(new Team());
            TeamStats homeStats = Optional.ofNullable(homeTeam.getStatistics()).orElse(new TeamStats());
            TeamStats awayStats = Optional.ofNullable(awayTeam.getStatistics()).orElse(new TeamStats());
            Prediction prediction = Optional.ofNullable(fixture.getPrediction()).orElse(new Prediction());

            prompt.append("Match:\n");
            prompt.append("Teams: ").append(Optional.ofNullable(homeTeam.getName()).orElse("-"))
                    .append(" vs ").append(Optional.ofNullable(awayTeam.getName()).orElse("-")).append("\n");
            prompt.append("Date: ").append(Optional.ofNullable(fixture.getDate()).orElse(ZonedDateTime.now())).append("\n");
            prompt.append("Venue: ").append(Optional.ofNullable(fixture.getVenueName()).orElse("-")).append("\n");
            prompt.append("Home Team Form: ").append(Optional.ofNullable(homeStats.getForm()).orElse("-")).append("\n");
            prompt.append("Away Team Form: ").append(Optional.ofNullable(awayStats.getForm()).orElse("-")).append("\n");
            prompt.append("Home Team Goals Scored at Home: ").append(Optional.ofNullable(homeStats.getGoalsScoredTotalHome()).orElse(0)).append("\n");
            prompt.append("Away Team Goals Scored Away: ").append(Optional.ofNullable(awayStats.getGoalsScoredTotalAway()).orElse(0)).append("\n");
            prompt.append("Home Goals Conceded at Home: ").append(Optional.ofNullable(homeStats.getGoalsConcededTotalHome()).orElse(0)).append("\n");
            prompt.append("Away Goals Conceded Away: ").append(Optional.ofNullable(awayStats.getGoalsConcededTotalAway()).orElse(0)).append("\n");
            prompt.append("Home Team Biggest Win at Home: ").append(Optional.ofNullable(homeStats.getBiggestWinHome()).orElse("-")).append("\n");
            prompt.append("Away Team Biggest Win Away: ").append(Optional.ofNullable(awayStats.getBiggestWinAway()).orElse("-")).append("\n");

            String homeFormation = fixture.getLineups().stream()
                    .filter(l -> l.getTeam().equals(homeTeam))
                    .findFirst()
                    .map(Lineup::getFormation)
                    .orElse("-");
            prompt.append("Home Team Formation: ").append(homeFormation).append("\n");

            String awayFormation = fixture.getLineups().stream()
                    .filter(l -> l.getTeam().equals(awayTeam))
                    .findFirst()
                    .map(Lineup::getFormation)
                    .orElse("-");
            prompt.append("Away Team Formation: ").append(awayFormation).append("\n");

            String predictedHomeGoals = Optional.ofNullable(prediction.getPredictions())
                    .map(PredictionDetails::getGoals)
                    .map(Goals::getHomeGoalsPrediction)
                    .orElse("-");
            prompt.append("Predicted Home Goals: ").append(predictedHomeGoals).append("\n");

            String predictedAwayGoals = Optional.ofNullable(prediction.getPredictions())
                    .map(PredictionDetails::getGoals)
                    .map(Goals::getAwayGoalsPrediction)
                    .orElse("-");
            prompt.append("Predicted Away Goals: ").append(predictedAwayGoals).append("\n");

            String predictedWinner = Optional.ofNullable(prediction.getPredictions())
                    .map(PredictionDetails::getWinner)
                    .map(Winner::getWinnerName)
                    .orElse("-");
            prompt.append("Predicted Winner: ").append(predictedWinner).append("\n");

            prompt.append("Match Status: ").append(Optional.ofNullable(fixture.getStatus()).orElse("-")).append("\n\n");
        }

        prompt.append("Provide predictions for the outcomes of these matches, including expected scores and potential outcomes.");
        return prompt.toString();
    }

}
