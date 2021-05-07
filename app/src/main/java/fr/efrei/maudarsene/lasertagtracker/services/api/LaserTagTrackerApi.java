package fr.efrei.maudarsene.lasertagtracker.services.api;

import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.AuthSuccessDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.CredentialsDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.RegisteredUserDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LaserTagTrackerApi {

    @POST("api/auth/register")
    Call<RegisteredUserDto> register(@Body CredentialsDto dto);

    @POST("api/auth/login")
    Call<AuthSuccessDto> login(@Body CredentialsDto dto);

    @POST("api/matches")
    Call<Match> createMatch(@Body Match dto);

    @GET("api/matches")
    Call<List<Match>> getMatches();
}
