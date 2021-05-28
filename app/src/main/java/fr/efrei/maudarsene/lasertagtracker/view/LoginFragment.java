package fr.efrei.maudarsene.lasertagtracker.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentLoginBinding;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.LoginViewModel;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.MatchFormViewModel;

public class LoginFragment extends Fragment {

    @BindView(R.id.loginUsername)
    public TextInputLayout loginUsername;
    @BindView(R.id.loginPassword)
    public TextInputLayout loginPassword;
    @BindView(R.id.loginUsernameInput)
    public TextInputEditText loginUsernameInput;
    @BindView(R.id.loginPasswordInput)
    public TextInputEditText loginPasswordInput;


    private LoginViewModel viewModel;



    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        FragmentLoginBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container, false );

        binding.setViewModel(this.viewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        ButterKnife.bind(this,view);
        viewModel.setNavigationService(new NavigationServiceImpl(view));
        viewModel.setLaserTagTrackerService(LaserTagTrackerServiceImpl.getINSTANCE());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.viewModel.checkStoredCredentials();

        this.viewModel.usernameValid.observe(getViewLifecycleOwner(), valid -> {
            if(!valid && loginUsernameInput.isFocused()){
                loginUsername.setError(getResources().getString(R.string.usernameError));
            }
            else {
                loginUsername.setError(null);
            }
        });
        this.viewModel.passwordValid.observe(getViewLifecycleOwner(), valid -> {
            if(!valid && loginPasswordInput.isFocused()){
                loginPassword.setError(getResources().getString(R.string.passwordError));
            }
            else {
                loginPassword.setError(null);
            }
        });
    }
}