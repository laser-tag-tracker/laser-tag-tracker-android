package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.services.api.GenericAsyncTask;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerService;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepository;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepositoryImpl;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationService;
import fr.efrei.maudarsene.lasertagtracker.services.permissions.PermissionService;
import fr.efrei.maudarsene.lasertagtracker.view.MatchFormFragmentDirections;

public class MatchFormViewModel extends AndroidViewModel {
    public MutableLiveData<String> playerName = new MutableLiveData<>();
    public MutableLiveData<Integer> rank = new MutableLiveData<>();
    public MutableLiveData<Integer> score = new MutableLiveData<>();
    public MutableLiveData<Double> precision = new MutableLiveData<>();
    public MutableLiveData<Integer> teamScore = new MutableLiveData<>();

    // Given
    public MutableLiveData<Integer> chestGiven = new MutableLiveData<>();
    public MutableLiveData<Integer> backGiven = new MutableLiveData<>();
    public MutableLiveData<Integer> shouldersGiven = new MutableLiveData<>();
    public MutableLiveData<Integer> gunGiven = new MutableLiveData<>();

    // Received
    public MutableLiveData<Integer> chestReceived = new MutableLiveData<>();
    public MutableLiveData<Integer> backReceived = new MutableLiveData<>();
    public MutableLiveData<Integer> shouldersReceived = new MutableLiveData<>();
    public MutableLiveData<Integer> gunReceived = new MutableLiveData<>();

    // Location
    public MutableLiveData<String> address = new MutableLiveData<>();
    public MutableLiveData<Double> latitude = new MutableLiveData<>();
    public MutableLiveData<Double> longitude = new MutableLiveData<>();

    public MutableLiveData<Bitmap> image = new MutableLiveData<>();

    private MatchLocalRepository matchLocalRepository;
    private NavigationService navigationService;
    private LaserTagTrackerService laserTagTrackerService;
    private PermissionService permissionService;

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public void setMatchLocalRepository(MatchLocalRepository matchLocalRepository) {
        this.matchLocalRepository = matchLocalRepository;
    }

    public void setLaserTagTrackerService(LaserTagTrackerService laserTagTrackerService) {
        this.laserTagTrackerService = laserTagTrackerService;
    }

    public MatchFormViewModel(@NonNull Application application) {
        super(application);

    }

    private Match match;

    public void submit() {
        this.match = new Match(
                playerName.getValue(),
                rank.getValue(),
                score.getValue(),
                precision.getValue(),
                teamScore.getValue(),
                LocalDate.now(),
                chestGiven.getValue(),
                backGiven.getValue(),
                shouldersGiven.getValue(),
                gunGiven.getValue(),
                chestReceived.getValue(),
                backReceived.getValue(),
                shouldersReceived.getValue(),
                gunReceived.getValue(),
                null,
                0,
                0
        );

        if (ActivityCompat.checkSelfPermission(this.getApplication().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getApplication().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.permissionService.requestLocationPermission();
            return;
        }
        createMatch();

    }

    @SuppressLint("MissingPermission")
    public void createMatch() {
        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(this.getApplication().getApplicationContext());
        locationClient.getLastLocation().addOnSuccessListener(location -> {
            Log.d("On Success Location",String.valueOf(match));
            match.setLongitude(location.getLongitude());
            match.setLatitude(location.getLatitude());
            match.setAddress(this.getAdress(location.getLongitude(), location.getLatitude()));

            new GenericAsyncTask<Match, Match>(
                    param -> laserTagTrackerService.createMatch(param),
                    this::handleMatchCreated
            ).execute(match);
        }).addOnFailureListener(failure -> failure.printStackTrace());
    }

    public void handleMatchCreated(Match match) {
        SharedPreferences credentials = this.getApplication().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        match.setUserId(credentials.getString("userId", null));

        if(this.image != null){
            this.storeImage(this.image.getValue(), match.getId() + ".png");
        }

        matchLocalRepository.insertMatch(match);
        this.navigationService.navigate(MatchFormFragmentDirections.actionMatchFormFragmentToMatchListFragment());
    }

    private String getAdress(double longitude, double latitude){
        Geocoder gc = new Geocoder(this.getApplication().getApplicationContext());
        List<Address> list = null;
        try {
            list = gc.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            Toast.makeText(this.getApplication().getApplicationContext(), "Geocoding Error", Toast.LENGTH_SHORT);
        }
        if (list != null && list.size() > 0) {
            Address a = list.get(0);
            StringBuilder sb = new StringBuilder();
            for(int i = 1;i <= a.getMaxAddressLineIndex();++i){
                sb.append(" ");
                sb.append(a.getAddressLine(i));
            }
            return sb.toString();
        }
        return null;
    }

    public void storeImage(Bitmap image, String fileName){
        ContextWrapper cw = new ContextWrapper(getApplication().getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath=new File(directory,fileName);


        try(FileOutputStream fos = new FileOutputStream(mypath)) {
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
