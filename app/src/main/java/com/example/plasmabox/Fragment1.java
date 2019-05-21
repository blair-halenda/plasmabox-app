package com.example.plasmabox;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

import static android.app.Activity.RESULT_OK;

public class Fragment1 extends Fragment {

    SeekBar seekBarBrightness;

    SeekBar seekBarRed;
    SeekBar seekBarGreen;
    SeekBar seekBarBlue;


    int valueRed;
    int valueGreen;
    int valueBlue;

    Button colorPicker;

    Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment1View = inflater.inflate(R.layout.page_1, container,false);

        spinner = fragment1View.findViewById(R.id.spinner);

        seekBarBrightness = fragment1View.findViewById(R.id.seekBarBrightness);
        colorPicker = fragment1View.findViewById(R.id.set_color);

        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int brightness = seekBarBrightness.getProgress();
                String brightnessString = String.format("%02X", brightness & 0xFF);
                String data = "[1B"+ brightnessString + "]";
                Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).writeData(data, ((MainActivity)getActivity()).mWriteCharacteristic);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        colorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(false);
            }
        });



        return fragment1View;
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!getUserVisibleHint())
        {
            return;
        }
        //INSERT CUSTOM CODE HERE
        ((MainActivity)getActivity()).writeData("[1E0]", ((MainActivity)getActivity()).mWriteCharacteristic);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000 && resultCode == RESULT_OK){
            Toast.makeText(getContext(),"PlasmaBox successfully synced", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    //-------------------------------- Color Picker ----------------------------------//
    private int currentColor;
    private void openDialog(boolean supportAlpha){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), currentColor, supportAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                colorPicker.setBackgroundColor(currentColor);

                valueRed = Color.red(color);
                valueGreen = Color.green(color);
                valueBlue = Color.blue(color);

                updateColor();

            }
        });
        dialog.show();
    }

    //-------------------------------- Slider Updater ----------------------------------//
    public void updateColor(){
        int r = valueRed;
        int g = valueGreen;
        int b = valueBlue;

        String colorString = String.format("%02X", r & 0xFF) + String.format("%02X", g & 0xFF) + String.format("%02X", b & 0xFF);
        //setColor.setBackgroundColor(Color.rgb(r,g,b));
        String data = "[1Ca"+ colorString + "]";
        Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).writeData(data, ((MainActivity)getActivity()).mWriteCharacteristic);
    }
}
