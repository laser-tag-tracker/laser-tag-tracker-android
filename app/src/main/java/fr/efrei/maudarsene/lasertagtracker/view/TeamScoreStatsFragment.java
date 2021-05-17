package fr.efrei.maudarsene.lasertagtracker.view;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentTeamScoreStatsBinding;
import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepositoryImpl;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.TeamScoreStatsViewModel;

public class TeamScoreStatsFragment extends Fragment {

    @BindView(R.id.chart)
    public BarChart chart;

    private TeamScoreStatsViewModel viewModel;

    public TeamScoreStatsFragment() {

    }

    public static TeamScoreStatsFragment newInstance() {
        TeamScoreStatsFragment fragment = new TeamScoreStatsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(TeamScoreStatsViewModel.class);

        FragmentTeamScoreStatsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_team_score_stats, container, false);
        binding.setViewModel(this.viewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        ButterKnife.bind(this, view);

        this.viewModel.setMatchLocalRepository(new MatchLocalRepositoryImpl(this.getContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel.loadMatches();
        this.viewModel.matches.observe(getViewLifecycleOwner(), this::handleUpdateMatches);
    }

    protected void handleUpdateMatches(List<Match> value) {
        BarDataSet dataSetPlayer = new BarDataSet(
                value.stream()
                        .map(match -> new BarEntry(value.indexOf(match), match.getScore()))
                        .collect(Collectors.toList()),
                "Player Score");
        BarDataSet dataSetTeam = new BarDataSet(
                value.stream()
                        .map(match -> new BarEntry(value.indexOf(match), match.getTeamScore()))
                        .collect(Collectors.toList()),
                "Team Score");

        BarData data = new BarData(dataSetPlayer,dataSetTeam);
        chart.setData(data);
        dataSetPlayer.setColor(Color.rgb(0,102,204));
        dataSetPlayer.setValueTextColor(Color.WHITE);
        dataSetPlayer.setValueTextSize(16f);
        dataSetTeam.setColor(Color.rgb(204,102,0));
        dataSetTeam.setValueTextColor(Color.WHITE);
        dataSetTeam.setValueTextSize(16f);
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setTextColor(Color.WHITE);
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        chart.groupBars(0, 0.1f, 0.001f);
        data.setBarWidth(0.50f);
        chart.setDragEnabled(true);
        chart.setVisibleXRangeMinimum(10);
        chart.animate();
        chart.getLegend().setTextColor(Color.WHITE);
        chart.invalidate();
    }
}