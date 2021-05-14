package fr.efrei.maudarsene.lasertagtracker.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.efrei.maudarsene.lasertagtracker.R;


public class GivenReceivedStatsFragment extends Fragment {

    public GivenReceivedStatsFragment() {
        // Required empty public constructor
    }

    public static GivenReceivedStatsFragment newInstance() {
        GivenReceivedStatsFragment fragment = new GivenReceivedStatsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_given_received_stats, container, false);
    }
}