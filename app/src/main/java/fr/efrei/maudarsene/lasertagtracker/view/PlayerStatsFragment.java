package fr.efrei.maudarsene.lasertagtracker.view;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.efrei.maudarsene.lasertagtracker.R;

public class PlayerStatsFragment extends Fragment {

    private Fragment f1, f2;

    public PlayerStatsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player_stats, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fg = this.getChildFragmentManager();
        if (f1 == null)
            f1 = TeamScoreStatsFragment.newInstance();

        if (f2 == null)
            f2 = GivenReceivedStatsFragment.newInstance();

        fg.beginTransaction().add(R.id.frame1, f1).add(R.id.frame2, f2).commit();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        Log.d("Rotating",String.valueOf(newConfig.orientation));
        switchPositions();
    }

    public void switchPositions() {
        Fragment tmp = f1;
        f1 = f2;
        f2 = tmp;

        FragmentManager fg = this.getChildFragmentManager();
        fg.beginTransaction().remove(f1).remove(f2).commitNow();
        fg.beginTransaction().add(R.id.frame1, f1).add(R.id.frame2, f2).commit();
    }
}