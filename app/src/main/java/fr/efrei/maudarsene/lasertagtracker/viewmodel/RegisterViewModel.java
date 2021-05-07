package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.maudarsene.lasertagtracker.services.api.GenericAsyncTask;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerService;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.AuthSuccessDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.CredentialsDto;
import fr.efrei.maudarsene.lasertagtracker.services.api.dtos.RegisteredUserDto;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationService;
import fr.efrei.maudarsene.lasertagtracker.view.RegisterFragmentDirections;

public class RegisterViewModel extends AndroidViewModel {

    private NavigationService navigationService;
    private LaserTagTrackerService laserTagTrackerService;

    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> passwordConf = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public void setLaserTagTrackerService(LaserTagTrackerService laserTagTrackerService) {
        this.laserTagTrackerService = laserTagTrackerService;
    }

    public void register(){

        new GenericAsyncTask<CredentialsDto, RegisteredUserDto>(
                credentialsDto -> laserTagTrackerService.register(credentialsDto),
                registeredUserDto -> this.navigationService.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        ).execute(new CredentialsDto(username.getValue(), password.getValue()));
    }
}
