package com.footystars.foot8.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FixtureStatuses {

    TBD("Time To Be Defined", "Scheduled", "Scheduled but date and time are not known"),
    NS("Not Started", "Scheduled", ""),
    _1H("First Half, Kick Off", "In Play", "First half in play"),
    HT("Halftime", "In Play", "Finished in the regular time"),
    _2H("Second Half, 2nd Half Started", "In Play", "Second half in play"),
    ET("Extra Time", "In Play", "Extra time in play"),
    BT("Break Time", "In Play", "Break during extra time"),
    P("Penalty In Progress", "In Play", "Penalty played after extra time"),
    SUSP("Match Suspended", "In Play", "Suspended by referee's decision, may be rescheduled another day"),
    INT("Match Interrupted", "In Play", "Interrupted by referee's decision, should resume in a few minutes"),
    FT("Match Finished", "Finished", "Finished in the regular time"),
    AET("Match Finished", "Finished", "Finished after extra time without going to the penalty shootout"),
    PEN("Match Finished", "Finished", "Finished after the penalty shootout"),
    PST("Match Postponed", "Postponed", "Postponed to another day, once the new date and time are known the status will change to Not Started"),
    CANC("Match Cancelled", "Cancelled", "Cancelled, match will not be played"),
    ABD("Match Abandoned", "Abandoned", "Abandoned for various reasons (Bad Weather, Safety, Floodlights, Playing Staff Or Referees), Can be rescheduled or not, it depends on the competition"),
    AWD("Technical Loss", "Not Played", ""),
    WO("WalkOver", "Not Played", "Victory by forfeit or absence of competitor"),
    LIVE("In Progress", "In Play", "Used in very rare cases. It indicates a fixture in progress but the data indicating the half-time or elapsed time are not available");

    private final String shortName;
    private final String type;
    private final String description;
}




     
