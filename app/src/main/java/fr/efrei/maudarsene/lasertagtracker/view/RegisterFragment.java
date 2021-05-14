package fr.efrei.maudarsene.lasertagtracker.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentRegisterBinding;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private RegisterViewModel viewModel;

    public RegisterFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        FragmentRegisterBinding binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_register,container, false );
        binding.setViewModel(this.viewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();

        viewModel.setNavigationService(new NavigationServiceImpl(view));
        viewModel.setLaserTagTrackerService(LaserTagTrackerServiceImpl.getINSTANCE());

        return view;
    }
}