package fr.efrei.maudarsene.lasertagtracker.services.api;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.AuthSuccessDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.CredentialsDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.RegisteredUserDto;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class LaserTagTrackerServiceImpl implements LaserTagTrackerService {

    private LaserTagTrackerApi laserTagTrackerApi;

    private static LaserTagTrackerService INSTANCE;

    public static LaserTagTrackerService getINSTANCE() {
        if(INSTANCE == null){
            INSTANCE = new LaserTagTrackerServiceImpl();
        }
        return INSTANCE;
    }

    private LaserTagTrackerServiceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://lasertagtracker.arsenelapostolet.fr/")
                .addConverterFactory(getJsonConverter())
                .build();

        laserTagTrackerApi = retrofit.create(LaserTagTrackerApi.class);
    }


    @Override
    public RegisteredUserDto register(CredentialsDto dto) {
        try {
            return laserTagTrackerApi.register(dto).execute().body();
        } catch (IOException exception) {
            Log.e("Network Error",exception.getMessage());
            throw new IllegalStateException("Network error");
        }
    }

    @Override
    public AuthSuccessDto login(CredentialsDto dto) {
        try {
            Response<AuthSuccessDto> response = laserTagTrackerApi.login(dto).execute();
            if (!response.isSuccessful()) {
                throw new IllegalArgumentException("Authentication failed");
            }

            return response.body();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new IllegalStateException("Network error");
        }

    }

    @Override
    public Match createMatch(Match match) {
        try {
            Log.d("Match", String.valueOf(match));
            Response<Match> response = laserTagTrackerApi.createMatch(match).execute();
            if(!response.isSuccessful()){
                throw new IllegalArgumentException(String.format("Match creation failed : %d %s", response.code(), response.errorBody().string()));
            }
            return response.body();
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new IllegalStateException("Network error");
        }
    }

    @Override
    public List<Match> getMatches() {
        try {
            Response<List<Match>> response = laserTagTrackerApi.getMatches().execute();
            if(!response.isSuccessful()){
                throw new IllegalArgumentException("Fetch matches failed");
            }
            return response.body();
        } catch (IOException exception) {
            throw new IllegalStateException("Network error");
        }
    }

    @Override
    public void setAuthToken(String token) {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(new AuthorizationInterceptor(token))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://lasertagtracker.arsenelapostolet.fr/")
                .addConverterFactory(getJsonConverter())
                .build();


        laserTagTrackerApi = retrofit.create(LaserTagTrackerApi.class);
    }

    private Converter.Factory getJsonConverter() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return JacksonConverterFactory.create(objectMapper);
    }
}
