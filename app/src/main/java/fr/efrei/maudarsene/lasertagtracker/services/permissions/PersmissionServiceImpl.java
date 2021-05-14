package fr.efrei.maudarsene.lasertagtracker.services.permissions;

import android.Manifest;

import androidx.fragment.app.Fragment;

public class PersmissionServiceImpl implements PermissionService{

    private final Fragment hostFragment;

    public PersmissionServiceImpl(Fragment hostFragment) {
        this.hostFragment = hostFragment;
    }

    @Override
    public void requestLocationPermission() {
        hostFragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }
}
