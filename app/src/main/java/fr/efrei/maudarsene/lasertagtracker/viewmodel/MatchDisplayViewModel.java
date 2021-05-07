package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.efrei.maudarsene.lasertagtracker.model.Match;

public class MatchDisplayViewModel extends AndroidViewModel {

    public MutableLiveData<Match> match = new MutableLiveData<>();

    public MatchDisplayViewModel(@NonNull Application application) {
        super(application);
    }
}
