package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
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

    public MediatorLiveData<Boolean> formValid = new MediatorLiveData<>();
    public MediatorLiveData<Boolean> usernameValid = new MediatorLiveData<>();
    public MediatorLiveData<Boolean> passwordValid = new MediatorLiveData<>();

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
        passwordValid.setValue(false);
        usernameValid.setValue(false);
        formValid.setValue(false);

        usernameValid.addSource(username, value -> usernameValid.setValue(this.fieldValid(username)));
        passwordValid.addSource(password, value -> passwordValid.setValue(this.fieldValid(password)));

        formValid.addSource(usernameValid, value -> formValid.setValue(usernameValid.getValue() && passwordValid.getValue()));
        formValid.addSource(passwordValid, value -> formValid.setValue(usernameValid.getValue() && passwordValid.getValue()));
    }

    private boolean fieldValid(MutableLiveData<String> field){
        return field.getValue().length() > 0;
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
        if(credentials.contains("username") && credentials.contains("password")){
            this.username.setValue(credentials.getString("username",null));
            this.password.setValue(credentials.getString("password",null));

            this.handleClickLogin();
        }
    }


    public void handleClickLogin() {

            new GenericAsyncTask<CredentialsDto, AuthSuccessDto>(
                    credentialsDto -> {
                        try {
                            return laserTagTrackerService.login(credentialsDto);
                        } catch (IllegalArgumentException exception){
                            Log.d("Authentication","Failed");
                            return null;
                        }
                    },
                    this::handleAuthSuccess
            ).execute(new CredentialsDto(username.getValue(), password.getValue()));

    }

    public void handleAuthSuccess(AuthSuccessDto dto) {
        if(dto == null){
            Toast.makeText(this.getApplication().getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
            return;
        }
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
