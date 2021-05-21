package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.maudarsene.lasertagtracker.services.api.GenericAsyncTask;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerService;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.CredentialsDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.RegisteredUserDto;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationService;
import fr.efrei.maudarsene.lasertagtracker.view.RegisterFragmentDirections;

public class RegisterViewModel extends AndroidViewModel {

    private NavigationService navigationService;
    private LaserTagTrackerService laserTagTrackerService;

    public MutableLiveData<String> username = new MutableLiveData<>("");
    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> passwordConf = new MutableLiveData<>("");

    public MediatorLiveData<Boolean> formValid = new MediatorLiveData<>();
    public MediatorLiveData<Boolean> passwordsValid = new MediatorLiveData<>();
    public MediatorLiveData<Boolean> usernameValid = new MediatorLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        passwordsValid.setValue(true);
        usernameValid.setValue(true);
        formValid.setValue(false);
        passwordsValid.addSource(password, value -> passwordsValid.setValue(passwordsEqual()));
        passwordsValid.addSource(passwordConf, value -> passwordsValid.setValue(passwordsEqual()));
        usernameValid.addSource(username, value -> usernameValid.setValue(usernameValid()));

        formValid.addSource(usernameValid, value -> formValid.setValue(usernameValid.getValue() && passwordsValid.getValue()));
        formValid.addSource(passwordsValid, value -> formValid.setValue(usernameValid.getValue() && passwordsValid.getValue()));
    }

    protected boolean usernameValid() {
        return username.getValue().length() > 0;
    }

    private boolean passwordsEqual() {
        return password.getValue().length() > 0
                && passwordConf.getValue().length() > 0
                && password.getValue().equals(passwordConf.getValue());
    }

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public void setLaserTagTrackerService(LaserTagTrackerService laserTagTrackerService) {
        this.laserTagTrackerService = laserTagTrackerService;
    }

    public void register(){
        new GenericAsyncTask<CredentialsDto, RegisteredUserDto>(
                credentialsDto -> {
                    try {
                        return laserTagTrackerService.register(credentialsDto);
                    } catch (IllegalArgumentException e) {
                        Log.d("Register","Failed");
                    }
                    return null;
                },
                registeredUserDto -> {
                    if(registeredUserDto == null){
                        Toast.makeText(this.getApplication().getApplicationContext(), "Username already in use", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    this.navigationService.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment());
                }
        ).execute(new CredentialsDto(username.getValue(), password.getValue()));
    }
}
