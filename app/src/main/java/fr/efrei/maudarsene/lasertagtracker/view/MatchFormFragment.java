package fr.efrei.maudarsene.lasertagtracker.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentMatchFormBinding;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepositoryImpl;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.MatchFormViewModel;

public class MatchFormFragment extends Fragment {

    private MatchFormViewModel viewModel;

    public MatchFormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewModel = ViewModelProviders.of(this).get(MatchFormViewModel.class);

        FragmentMatchFormBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_match_form,container, false );
        binding.setViewModel(this.viewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        ButterKnife.bind(this,view);
        this.viewModel.setNavigationService(new NavigationServiceImpl(view));
        this.viewModel.setMatchLocalRepository(new MatchLocalRepositoryImpl(this.getContext()));
        return view;
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static Double getDouble(TextInputEditText widget){
        String value = widget.getText().toString();
        if(value.equals("")){
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e){
            if(value.length() >= 2){
                return Double.parseDouble(value.substring(1,value.length() - 2));
            }
            else {
                return null;
            }
        }
    }


    @BindingAdapter("android:text")
    public static void setDouble(TextInputEditText widget, Double number){
        if(number == null || widget.getText().toString().equals(number.toString())){
            return;
        }
        else {
            widget.setText(number.toString());
        }

    }

    @InverseBindingAdapter(attribute = "android:text")
    public static Integer getInt(TextInputEditText widget){
        String value = widget.getText().toString();
        if(value.equals("")){
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e){
            if(value.length() >= 2){
                return Integer.parseInt(value.substring(1,value.length() - 2));
            }
            else {
                return null;
            }
        }
    }
    @BindingAdapter("android:text")
    public static void setInteger(TextInputEditText widget, Integer number){
        if(number == null || widget.getText().toString().equals(number.toString())){
            return;
        }
        else {
            widget.setText(number.toString());
        }

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        this.viewModel.playerName.observe(this, value -> System.out.println(value));
    }
}