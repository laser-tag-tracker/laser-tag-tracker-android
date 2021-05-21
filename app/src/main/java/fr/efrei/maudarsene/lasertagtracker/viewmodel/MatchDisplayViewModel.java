package fr.efrei.maudarsene.lasertagtracker.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;

import fr.efrei.maudarsene.lasertagtracker.model.Match;

public class MatchDisplayViewModel extends AndroidViewModel {

    public MutableLiveData<Match> match = new MutableLiveData<>();

    public MatchDisplayViewModel(@NonNull Application application) {
        super(application);
    }

    public String matchDateString(){
        Log.d("MyActivity", ""+ match.getValue().getDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return match.getValue().getDate().format(formatter);
    }

    public Bitmap loadImageFromStorage() {
        ContextWrapper cw = new ContextWrapper(getApplication().getApplicationContext());
        String path = cw.getDir("imageDir", Context.MODE_PRIVATE).getAbsolutePath();
        try {
            File f = new File(path, match.getValue().getId() + ".png");
            return BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
