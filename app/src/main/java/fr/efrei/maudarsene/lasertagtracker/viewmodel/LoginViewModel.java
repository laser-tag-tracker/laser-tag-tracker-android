package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.maudarsene.lasertagtracker.services.api.GenericAsyncTask;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerService;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.AuthSuccessDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.CredentialsDto;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationService;
import fr.efrei.maudarsene.lasertagtracker.view.LoginFragmentDirections;

public class LoginViewModel extends AndroidViewModel {

    private NavigationService navigationService;
    private LaserTagTrackerService laserTagTrackerService;

    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public void setLaserTagTrackerService(LaserTagTrackerService laserTagTrackerService) {
        this.laserTagTrackerService = laserTagTrackerService;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void handleClickRegister() {


        this.navigationService.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment());
    }

    public void checkStoredCredentials(){
        SharedPreferences credentials = this.getApplication().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        if(credentials.contains("username") && credentials.contains("password") && credentials.contains("token")){
            navigateToList();
            this.laserTagTrackerService.setAuthToken(credentials.getString("token", null));
        }
    }


    public void handleClickLogin() {
        new GenericAsyncTask<CredentialsDto, AuthSuccessDto>(
                credentialsDto -> laserTagTrackerService.login(credentialsDto),
                this::handleAuthSuccess
        ).execute(new CredentialsDto(username.getValue(), password.getValue()));
    }

    public void handleAuthSuccess(AuthSuccessDto dto) {
        SharedPreferences credentials = this.getApplication().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = credentials.edit();
        editor.putString("username", dto.getUsername());
        editor.putString("password", password.getValue());
        editor.putString("token", dto.getToken());
        editor.putString("userId", dto.getUserId());
        editor.commit();

        this.laserTagTrackerService.setAuthToken(dto.getToken());

        navigateToList();
    }

    private void navigateToList() {
        this.navigationService.navigate(LoginFragmentDirections.actionLoginFragmentToMatchListFragment());
    }
}
