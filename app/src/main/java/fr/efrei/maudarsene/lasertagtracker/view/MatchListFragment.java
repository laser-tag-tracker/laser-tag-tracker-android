package fr.efrei.maudarsene.lasertagtracker.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentMatchListBinding;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepositoryImpl;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.utils.BindingAdapters;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.MatchFormViewModel;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.MatchListViewModel;

public class MatchListFragment extends Fragment {

    @BindView(R.id.matchListRecyclerView)
    public RecyclerView matchListRecyclerView;

    private MatchListViewModel viewModel;

    public MatchListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(MatchListViewModel.class);
        FragmentMatchListBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_match_list,container, false );
        binding.setViewModel(this.viewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        ButterKnife.bind(this,view);


        this.viewModel.setMatchLocalRepository(new MatchLocalRepositoryImpl(this.getContext()));
        this.viewModel.setNavigationService(new NavigationServiceImpl(view));
        this.viewModel.setLaserTagTrackerService(LaserTagTrackerServiceImpl.getINSTANCE());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.viewModel.loadMatches();
        MatchListAdapter adapter = new MatchListAdapter(this.viewModel.matchList.getValue(), this.viewModel::handleItemClicked);
        this.matchListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.matchListRecyclerView.setAdapter(adapter);

        this.viewModel.matchList.observe(getViewLifecycleOwner(), value -> adapter.setMatches(value));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.viewModel.navigateToPlayerStats();
        return true;
    }
}