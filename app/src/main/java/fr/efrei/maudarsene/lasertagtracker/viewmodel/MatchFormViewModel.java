package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.time.LocalDate;

import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepository;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepositoryImpl;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationService;
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

    private MatchLocalRepository matchLocalRepository;

    private NavigationService navigationService;

    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    public void setMatchLocalRepository(MatchLocalRepository matchLocalRepository) {
        this.matchLocalRepository = matchLocalRepository;
    }

    public MatchFormViewModel(@NonNull Application application) {
        super(application);

    }

    public void submit() {
        Match match = new Match(
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

        matchLocalRepository.insertMatch(match);

        this.navigationService.navigate(MatchFormFragmentDirections.actionMatchFormFragmentToMatchListFragment());
    }
}
