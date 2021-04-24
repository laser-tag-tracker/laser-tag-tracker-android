package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.view.MatchListFragmentDirections;

public class MatchListViewModel extends AndroidViewModel {

    public MutableLiveData<List<Match>> matchList;

    public MatchListViewModel(@NonNull Application application) {
        super(application);
    }

}
