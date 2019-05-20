package com.example.plasmabox;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;

public class Fragment1 extends Fragment {

    Button button;
    Button button1;
    Button button2;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment1View = inflater.inflate(R.layout.page_1, container,false);
        button = fragment1View.findViewById(R.id.button);

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
}
