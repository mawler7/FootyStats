package com.footystars.persistence.repository;

import com.footystars.model.entity.Fixture;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Specification class for dynamically filtering fixtures based on search criteria.
 */
public final class FixtureSpecification {


    private FixtureSpecification() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Creates a dynamic filter for searching fixtures based on various parameters.
     *
     * @param startDate The start date for filtering fixtures (inclusive).
     * @param endDate   The end date for filtering fixtures (inclusive).
     * @param leagueId  The ID of the league to filter fixtures by.
     * @param homeName  The name of the home team (if provided, filters fixtures by this team).
     * @param awayName  The name of the away team (if provided, filters fixtures by this team).
     * @return A {@link Specification} object containing the filtering logic.
     */
    @NotNull
    public static Specification<Fixture> filterByCriteria(
            ZonedDateTime startDate, ZonedDateTime endDate,
            Long leagueId, String homeName, String awayName) {

        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            addDatePredicates(predicates, root, builder, startDate, endDate);
            addLeaguePredicate(predicates, root, builder, leagueId);
            addTeamPredicates(predicates, root, builder, homeName, awayName);

            if (query != null) {
                query.orderBy(builder.desc(root.get("info").get("date")));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Adds date filtering predicates to the list.
     */
    private static void addDatePredicates(List<Predicate> predicates, Root<Fixture> root,
                                          CriteriaBuilder builder, ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("info").get("date"), startDate));
        }
        if (endDate != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("info").get("date"), endDate));
        }
    }

    /**
     * Adds a league filter predicate if leagueId is provided.
     */
    private static void addLeaguePredicate(List<Predicate> predicates, Root<Fixture> root,
                                           CriteriaBuilder builder, Long leagueId) {
        if (leagueId != null) {
            predicates.add(builder.equal(root.get("league").get("leagueId"), leagueId));
        }
    }

    /**
     * Adds team filtering predicates based on provided home and away names.
     */
    private static void addTeamPredicates(List<Predicate> predicates, Root<Fixture> root,
                                          CriteriaBuilder builder, String homeName, String awayName) {
        if (homeName != null && !homeName.isEmpty() && (awayName == null || awayName.isEmpty())) {
            predicates.add(getTeamSearchPredicate(root, builder, homeName));
        } else if (awayName != null && !awayName.isEmpty() && (homeName == null || homeName.isEmpty())) {
            predicates.add(getTeamSearchPredicate(root, builder, awayName));
        } else if (homeName != null && awayName != null && !homeName.isEmpty() && !awayName.isEmpty()) {
            predicates.add(getExactMatchPredicate(root, builder, homeName, awayName));
        }
    }

    /**
     * Helper method to create a predicate that searches for a team in both home and away positions.
     */
    private static Predicate getTeamSearchPredicate(@NotNull Root<Fixture> root, @NotNull CriteriaBuilder builder, @NotNull String teamName) {
        Predicate homePredicate = builder.like(builder.lower(root.get("teams").get("homeTeam").get("homeName")), "%" + teamName.toLowerCase() + "%");
        Predicate awayPredicate = builder.like(builder.lower(root.get("teams").get("awayTeam").get("awayName")), "%" + teamName.toLowerCase() + "%");
        return builder.or(homePredicate, awayPredicate);
    }

    /**
     * Creates a predicate that matches exact home vs away and reversed matchups.
     */
    private static Predicate getExactMatchPredicate(@NotNull Root<Fixture> root, @NotNull CriteriaBuilder builder, @NotNull String homeName, @NotNull String awayName) {
        Predicate homePredicate = builder.like(builder.lower(root.get("teams").get("homeTeam").get("homeName")), "%" + homeName.toLowerCase() + "%");
        Predicate awayPredicate = builder.like(builder.lower(root.get("teams").get("awayTeam").get("awayName")), "%" + awayName.toLowerCase() + "%");

        Predicate reverseHomePredicate = builder.like(builder.lower(root.get("teams").get("homeTeam").get("homeName")), "%" + awayName.toLowerCase() + "%");
        Predicate reverseAwayPredicate = builder.like(builder.lower(root.get("teams").get("awayTeam").get("awayName")), "%" + homeName.toLowerCase() + "%");

        return builder.or(builder.and(homePredicate, awayPredicate), builder.and(reverseHomePredicate, reverseAwayPredicate));
    }
}
