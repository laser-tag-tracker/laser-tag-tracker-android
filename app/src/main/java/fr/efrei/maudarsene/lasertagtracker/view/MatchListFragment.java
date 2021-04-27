package fr.efrei.maudarsene.lasertagtracker.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentMatchListBinding;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepositoryImpl;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationServiceImpl;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentMatchListBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_match_list,container, false );
        binding.setViewModel(this.viewModel);
        binding.setLifecycleOwner(this);

        View view = binding.getRoot();
        ButterKnife.bind(this,view);

        this.viewModel = ViewModelProviders.of(this).get(MatchListViewModel.class);
        this.viewModel.setMatchLocalRepository(new MatchLocalRepositoryImpl(this.getContext()));
        this.viewModel.setNavigationService(new NavigationServiceImpl(view));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.viewModel.loadMatches();
        MatchListAdapter adapter = new MatchListAdapter(this.viewModel.matchList.getValue());
        this.matchListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.matchListRecyclerView.setAdapter(adapter);
    }
}