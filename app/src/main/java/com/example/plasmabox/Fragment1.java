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
    SeekBar seekBarLength;
    SeekBar seekBarSpeed;

    SeekBar seekBarRed;
    SeekBar seekBarGreen;
    SeekBar seekBarBlue;


    int rgb1[] = {255,0,0};
    int rgb2[] = {0,255,0};
    int rgb3[] = {0,0,255};

    Button colorPicker1;
    Button colorPicker2;
    Button colorPicker3;

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
        seekBarSpeed = fragment1View.findViewById(R.id.seekBarSpeed);
        seekBarLength = fragment1View.findViewById(R.id.seekBarLength);



        colorPicker1 = fragment1View.findViewById(R.id.set_color_1);
        colorPicker2 = fragment1View.findViewById(R.id.set_color_2);
        colorPicker3 = fragment1View.findViewById(R.id.set_color_3);

        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int brightness;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brightness = seekBarBrightness.getProgress();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String brightnessString = String.format("%02X", brightness & 0xFF);
                String data = "[1B"+ brightnessString + "]";
                Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).writeData(data, ((MainActivity)getActivity()).mWriteCharacteristic);
            }
        });

        seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int speed;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                speed = seekBarSpeed.getProgress();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String speedString = String.format("%02X", speed & 0xFF);
                String data = "[1S"+ speedString + "]";
                Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).writeData(data, ((MainActivity)getActivity()).mWriteCharacteristic);
            }
        });

        seekBarLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int length;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                length = seekBarLength.getProgress();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String lengthString = String.format("%02X", length & 0xFF);
                String data = "[1L"+ lengthString + "]";
                Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).writeData(data, ((MainActivity)getActivity()).mWriteCharacteristic);
            }
        });

        colorPicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(false, rgb1, "a",colorPicker1);
            }
        });

        colorPicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(false, rgb2, "b",colorPicker2);
            }
        });

        colorPicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(false, rgb3, "c",colorPicker3);
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
        //((MainActivity)getActivity()).writeData("[1E0]", ((MainActivity)getActivity()).mWriteCharacteristic);
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
    private void openDialog(boolean supportAlpha, final int rgb[], final String colour_id, final Button colorPicker){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), Color.rgb(rgb[0],rgb[1],rgb[2]), supportAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                currentColor = color;
                colorPicker.setBackgroundColor(currentColor);

                rgb[0] = Color.red(color);
                rgb[1] = Color.green(color);
                rgb[2] = Color.blue(color);

                updateColor(rgb, colour_id);

            }
        });
        dialog.show();
    }

    //-------------------------------- Slider 1 Updater ----------------------------------//
    public void updateColor(int rgb[], String colour_id){
        int r = rgb[0];
        int g = rgb[1];
        int b = rgb[2];

        String colorString = String.format("%02X", r & 0xFF) + String.format("%02X", g & 0xFF) + String.format("%02X", b & 0xFF);
        //setColor.setBackgroundColor(Color.rgb(r,g,b));
        String data = "[1C" + colour_id + colorString + "]";
        Toast.makeText(getContext(),data,Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).writeData(data, ((MainActivity)getActivity()).mWriteCharacteristic);
    }
}
