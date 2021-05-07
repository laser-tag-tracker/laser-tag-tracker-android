package fr.efrei.maudarsene.lasertagtracker.services.api;

import java.io.IOException;
import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.AuthSuccessDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.CredentialsDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.RegisteredUserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LaserTagTrackerService {

    RegisteredUserDto register(CredentialsDto dto) ;
    AuthSuccessDto login(CredentialsDto dto);
    Match createMatch(Match match);
    List<Match> getMatches();
    void setAuthToken(String token);
}
