package com.example.seekbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qualserie.R;

public class MainActivity extends Activity {

    private SeekBar seekBar;
    private ImageView imageViewExibe;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar_id);
        imageViewExibe = findViewById(R.id.imageView_exibe_id);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                if(progress == 1){
                    imageViewExibe.setImageResource(R.drawable.pouco);
                }
                else if(progress == 2){
                    imageViewExibe.setImageResource(R.drawable.medio);
                }
                else if(progress == 3){
                    imageViewExibe.setImageResource(R.drawable.muito);
                }
                else if(progress == 4){
                    imageViewExibe.setImageResource(R.drawable.susto);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
