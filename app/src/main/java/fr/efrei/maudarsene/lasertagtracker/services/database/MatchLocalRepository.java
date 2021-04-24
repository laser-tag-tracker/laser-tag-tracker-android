package fr.efrei.maudarsene.lasertagtracker.services.database;

import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;

public interface MatchLocalRepository {

    void insertMatch(Match match);
    List<Match> getMatches();

}
