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
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentRegisterBinding;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    @BindView(R.id.registerUsername)
    public TextInputLayout registerUsername;
    @BindView(R.id.registerPassword)
    public TextInputLayout registerPassword;
    @BindView(R.id.registerPasswordConf)
    public TextInputLayout registerPasswordConf;
    @BindView(R.id.registerUsernameInput)
    public TextInputEditText registerUsernameInput;
    @BindView(R.id.registerPasswordInput)
    public TextInputEditText registerPasswordInput;
    @BindView(R.id.registerPasswordConfInput)
    public TextInputEditText registerPasswordConfInput;

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
        ButterKnife.bind(this,view);
        viewModel.setNavigationService(new NavigationServiceImpl(view));
        viewModel.setLaserTagTrackerService(LaserTagTrackerServiceImpl.getINSTANCE());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.viewModel.usernameValid.observe(getViewLifecycleOwner(), valid -> {
            if(!valid && registerUsernameInput.isFocused()){
                registerUsername.setError(getResources().getString(R.string.usernameError));
            }
            else {
                registerUsername.setError(null);
            }
        });
        this.viewModel.passwordsValid.observe(getViewLifecycleOwner(), valid -> {
            if(!valid && (registerPasswordInput.isFocused() || registerPasswordConfInput.isFocused())){
                registerPasswordConf.setError(getResources().getString(R.string.passwordError));
                registerPassword.setError(getResources().getString(R.string.passwordError));
            }
            else {
                registerPassword.setError(null);
                registerPasswordConf.setError(null);
            }
        });
    }
}