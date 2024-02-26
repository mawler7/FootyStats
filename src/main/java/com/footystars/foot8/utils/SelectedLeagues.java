package com.footystars.foot8.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum SelectedLeagues {

    /**
     * 159, 182, 175, 186, 157
     * <p>
     * Hertha Berlin                                   159
     * Union Berlin                                    182
     * Hamburger SV                                    175
     * FC St. Pauli                                    186
     * Bayern Munich                                   157
     * Borussia Dortmund                               165
     */
    GERMANY(78L, "Bundesliga"),

    /**
     * Paris Saint-Germain                             85
     * Olympique Marseille                             81
     * Olympique Lyon                                  80
     */
    FRANCE(61L, "Ligue 1"),
    /**
     * AS Roma                                        489
     * Lazio                                          487
     * AC Milan                                       489
     * Inter Milan                                    505
     * Napoli                                         492
     */

    ITALY(135L, "Serie A"),
    /**
     * Chelsea                                    49
     * Tottenham Hotspur                          47
     * Manchester United                          33
     * Manchester City                            50
     * Liverpool                                  40
     * Arsenal                                    42
     * Everton                                    45
     */
    ENGLAND(39L, "Premier League"),
    /**
     * LA LIGA
     * Real Madrid                                 541
     * FC Barcelona                                531
     * Sevilla                                     536
     * Atletico                                    530
     * Valencia                                    532
     * Levante                                     539
     */
    SPAIN(140L, "La Liga");


//    POLAND(106L),
//
//    BELGIUM(144L),
//    TURKEY(203L),
//    DENMARK(119L);

    private final Long id;
    private final String leagueName;

    public static List<Long> getAllIds() {
        return Arrays.stream(SelectedLeagues.values())
                .map(SelectedLeagues::getId)
                .collect(Collectors.toList());
    }

    public static List<Long> getEuropeansTop5LeaguesIds() {


        return Arrays.stream(SelectedLeagues.values())
                .filter(s -> Objects.equals(s.getId(), ENGLAND.id) || Objects.equals(s.getId(), FRANCE.id)
                        || Objects.equals(s.getId(), GERMANY.id)
                        ||
                        Objects.equals(s.getId(), ITALY.id) || Objects.equals(s.getId(), SPAIN.id))
                .map(SelectedLeagues::getId)
                .toList();
    }

}







