package com.example.raddiobutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonEscolhido;
    private Button buttonEscolher;
    private TextView textViewEscolhido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup_id);
        buttonEscolher = findViewById(R.id.button_escolher_id);
        textViewEscolhido = findViewById(R.id.textView_exibe_id);

        buttonEscolher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRadioEscolhido = radioGroup.getCheckedRadioButtonId();

                if(idRadioEscolhido > 0){
                    radioButtonEscolhido = findViewById(idRadioEscolhido);
                    textViewEscolhido.setText(radioButtonEscolhido.getText());
                }
            }
        });
    }
}
