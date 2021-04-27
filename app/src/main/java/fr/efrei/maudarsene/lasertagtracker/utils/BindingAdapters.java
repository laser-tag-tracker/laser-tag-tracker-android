package fr.efrei.maudarsene.lasertagtracker.utils;

import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

import com.google.android.material.textfield.TextInputEditText;

public class BindingAdapters {

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

}
