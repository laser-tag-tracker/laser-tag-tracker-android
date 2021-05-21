package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepository;

public class GivenReceivedStatsViewModel extends AndroidViewModel {
    public MutableLiveData<List<Match>> matches = new MutableLiveData<>();
    private MatchLocalRepository matchLocalRepository;

    public GivenReceivedStatsViewModel(@NonNull Application application) { super(application); }

    public void setMatchLocalRepository(MatchLocalRepository matchLocalRepository) {
        this.matchLocalRepository = matchLocalRepository;
    }

    public void loadMatches(){
        SharedPreferences credentials = this.getApplication().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        this.matches.setValue(this.matchLocalRepository.getMatchesForUser(credentials.getString("userId", null)));
    }
}
