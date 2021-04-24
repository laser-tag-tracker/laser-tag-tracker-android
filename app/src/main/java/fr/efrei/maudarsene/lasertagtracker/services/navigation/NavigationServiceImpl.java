package fr.efrei.maudarsene.lasertagtracker.services.navigation;

import android.view.View;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import fr.efrei.maudarsene.lasertagtracker.view.MatchListFragmentDirections;

public class NavigationServiceImpl implements NavigationService {

    private final View view;

    public NavigationServiceImpl(View view) {
        this.view = view;
    }


    @Override
    public void navigate(NavDirections directions) {
        Navigation.findNavController(view).navigate(directions);
    }
}
