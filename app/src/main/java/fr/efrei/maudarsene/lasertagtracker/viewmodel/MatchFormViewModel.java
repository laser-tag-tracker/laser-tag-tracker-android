package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MatchFormViewModel extends AndroidViewModel {
    public MutableLiveData<Integer> rank;
    public MutableLiveData<Integer> score;
    public MutableLiveData<Integer> precision;
    public MutableLiveData<Integer> teamScore;

    // Given
    public MutableLiveData<Integer> chestGiven;
    public MutableLiveData<Integer> backGiven;
    public MutableLiveData<Integer> shouldersGiven;
    public MutableLiveData<Integer> gunGiven;

    // Received
    public MutableLiveData<Integer> chestReceived;
    public MutableLiveData<Integer> backReceived;
    public MutableLiveData<Integer> shouldersReceived;
    public MutableLiveData<Integer> gunReceived;

    // Location
    public MutableLiveData<String> address;
    public MutableLiveData<Double> latitude;
    public MutableLiveData<Double> longitude;

    public MatchFormViewModel(@NonNull Application application) {
        super(application);
    }

    public void submit(){

    }
}
