package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;

public class TeamScoreStatsViewModel extends AndroidViewModel {
    public MutableLiveData<List<Match>> matches;

    public TeamScoreStatsViewModel(@NonNull Application application) {
        super(application);
    }
}
