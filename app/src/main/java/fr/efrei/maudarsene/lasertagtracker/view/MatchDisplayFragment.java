package fr.efrei.maudarsene.lasertagtracker.view;

import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentLoginBinding;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentMatchDisplayBinding;
import fr.efrei.maudarsene.lasertagtracker.model.Match;
import fr.efrei.maudarsene.lasertagtracker.utils.BindingAdapters;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.LoginViewModel;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.MatchDisplayViewModel;

public class MatchDisplayFragment extends Fragment {

    private MatchDisplayViewModel viewModel;

    public MatchDisplayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.viewModel = ViewModelProviders.of(this).get(MatchDisplayViewModel.class);
        FragmentMatchDisplayBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_match_display,container, false );

        binding.setViewModel(this.viewModel);
        binding.setLifecycleOwner(this);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            Match match = MatchDisplayFragmentArgs.fromBundle(getArguments()).getMatch();
            this.viewModel.match.setValue(match);
        }
    }

    @BindingAdapter("app:imageBitmap")
    public static void loadImage(ImageView iv, Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
    }


    @InverseBindingAdapter(attribute = "android:text")
    public static Integer getInt(TextView widget){
        return BindingAdapters.getInt(widget);
    }

    @BindingAdapter("android:text")
    public static void setInteger(TextView widget, int number){
        BindingAdapters.setInteger(widget, number);
    }

}