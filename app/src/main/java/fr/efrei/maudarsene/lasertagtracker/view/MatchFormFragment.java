package fr.efrei.maudarsene.lasertagtracker.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.efrei.maudarsene.lasertagtracker.R;
import fr.efrei.maudarsene.lasertagtracker.databinding.FragmentMatchFormBinding;
import fr.efrei.maudarsene.lasertagtracker.services.api.LaserTagTrackerServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.services.database.MatchLocalRepositoryImpl;
import fr.efrei.maudarsene.lasertagtracker.services.navigation.NavigationServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.services.permissions.PersmissionServiceImpl;
import fr.efrei.maudarsene.lasertagtracker.utils.BindingAdapters;
import fr.efrei.maudarsene.lasertagtracker.viewmodel.MatchFormViewModel;

import static android.app.Activity.RESULT_OK;

public class MatchFormFragment extends Fragment {

    private MatchFormViewModel viewModel;

    @BindView(R.id.imageFloatingActionButton)
    public FloatingActionButton imageFloatingActionButton;

    @BindView(R.id.imageView)
    public ImageView imageView;

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
        this.viewModel.setLaserTagTrackerService(LaserTagTrackerServiceImpl.getINSTANCE());
        this.viewModel.setPermissionService(new PersmissionServiceImpl(this));
        return view;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this.getContext(), "No camera available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.viewModel.image.setValue(imageBitmap);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != 1) {
            return;
        }
        this.viewModel.createMatch();
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static Double getDouble(TextInputEditText widget){
        return BindingAdapters.getDouble(widget);
    }

    @BindingAdapter("android:text")
    public static void setDouble(TextInputEditText widget, Double number){
        BindingAdapters.setDouble(widget, number);
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static Integer getInt(TextInputEditText widget){
        return BindingAdapters.getInt(widget);
    }

    @BindingAdapter("android:text")
    public static void setInteger(TextInputEditText widget, Integer number){
        BindingAdapters.setInteger(widget, number);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.imageFloatingActionButton.setOnClickListener(v -> dispatchTakePictureIntent());
    }
}