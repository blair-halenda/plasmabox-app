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

    ConnectActivity connectActivity;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        connectActivity = new ConnectActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragement1View = inflater.inflate(R.layout.page_1, container,false);
        button = fragement1View.findViewById(R.id.button);
        button1 = fragement1View.findViewById(R.id.button1);
        button2 = fragement1View.findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Button pressed!", Toast.LENGTH_SHORT).show();
                Intent syncIntent = new Intent(getActivity(), SyncActivity.class);
                startActivityForResult(syncIntent,1000);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "CMD 1",Toast.LENGTH_SHORT).show();

                connectActivity.writeData("[1LFF]", connectActivity.mWriteCharacteristic);


            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "CMD 2",Toast.LENGTH_SHORT).show();
                connectActivity.writeData("[1L01]", connectActivity.mWriteCharacteristic);
            }
        });
        return fragement1View;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1000 && resultCode == RESULT_OK){
            Toast.makeText(getContext(),"PlasmaBox successfully synced", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
