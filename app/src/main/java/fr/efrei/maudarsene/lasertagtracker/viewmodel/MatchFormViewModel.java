package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MatchFormViewModel extends AndroidViewModel {
    public MutableLiveData<String> playerName = new MutableLiveData<>();
    public MutableLiveData<Integer> rank = new MutableLiveData<>();
    public MutableLiveData<Integer> score = new MutableLiveData<>();
    public MutableLiveData<Integer> precision = new MutableLiveData<>();
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

    public MatchFormViewModel(@NonNull Application application) {
        super(application);
    }

    public void submit(){

    }
}
