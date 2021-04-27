package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepository;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationService;
import fr.efrei.maudarsene.lasertagtracker.view.MatchListFragmentDirections;

public class MatchListViewModel extends AndroidViewModel {

    public MutableLiveData<List<Match>> matchList = new MutableLiveData<>();

    private MatchLocalRepository matchLocalRepository;
    private NavigationService navigationService;

    public MatchListViewModel(@NonNull Application application) {
        super(application);
    }

    public void setMatchLocalRepository(MatchLocalRepository matchLocalRepository) {
        this.matchLocalRepository = matchLocalRepository;
    }

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public void handleClickCreateMatch(){
        this.navigationService.navigate(MatchListFragmentDirections.actionMatchListFragmentToMatchFormFragment());
    }

    public void loadMatches(){
        this.matchList.setValue(this.matchLocalRepository.getMatches());
    }
}
