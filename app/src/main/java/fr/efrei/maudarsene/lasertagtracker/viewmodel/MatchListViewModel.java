package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import java.util.List;

import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.services.api.GenericAsyncTask;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerService;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepository;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationService;
import fr.efrei.maudarsene.lasertagtracker.view.MatchListFragmentDirections;

public class MatchListViewModel extends AndroidViewModel {

    public MutableLiveData<List<Match>> matchList = new MutableLiveData<>();

    private MatchLocalRepository matchLocalRepository;
    private NavigationService navigationService;
    private LaserTagTrackerService laserTagTrackerService;

    public MatchListViewModel(@NonNull Application application) {
        super(application);
    }

    public void setLaserTagTrackerService(LaserTagTrackerService laserTagTrackerService) {
        this.laserTagTrackerService = laserTagTrackerService;
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
        SharedPreferences credentials = this.getApplication().getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE);
        this.matchList.setValue(this.matchLocalRepository.getMatchesForUser(credentials.getString("userId", null)));
    }

    public void loadMatchesFromApi(){
        new GenericAsyncTask<Void,List<Match>>(
                param -> this.laserTagTrackerService.getMatches(),
                result -> this.matchList.setValue(result)
        ).execute(new Void[]{null});
    }

    public void handleItemClicked(Match match) {
        this.navigationService.navigate(MatchListFragmentDirections.actionMatchListFragmentToMatchDisplayFragment(match));
    }

    public void navigateToPlayerStats() {
        this.navigationService.navigate(MatchListFragmentDirections.actionMatchListFragmentToPlayerStatsFragment());
    }
}
