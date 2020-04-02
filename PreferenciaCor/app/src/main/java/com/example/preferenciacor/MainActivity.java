package com.example.preferenciacor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonPressionado;
    private Button buttonSalvar;
    private ConstraintLayout constraintLayout;
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup= findViewById(R.id.radioGroup_id);
        buttonSalvar = findViewById(R.id.button_id);
        constraintLayout = findViewById(R.id.layout_id);

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int corPresionado = radioGroup.getCheckedRadioButtonId();

                if(corPresionado > 0){
                    radioButtonPressionado = findViewById(corPresionado);

                    radioButtonPressionado.getText().toString();

                    String corRadioEscolhida = "";
                    corRadioEscolhida = radioButtonPressionado.getText().toString();

                    SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    editor.putString("corEscolhida ", corRadioEscolhida);

                    editor.commit();
                    setBackground(corRadioEscolhida);
                }
            }
        });

        //Salvar a preferencia
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
        if(sharedPreferences.contains("corEscolhida")){
            String corRecuperada = sharedPreferences.getString("corEscolhida", "Laranja");
            setBackground(corRecuperada);
        }
    }
    //Muda a cor do layout
    private void setBackground(String cor){
        if(cor.equals("Azul")){
            constraintLayout.setBackgroundColor(Color.parseColor("#495b7c"));
        }else if(cor.equals("Laranja")){
            constraintLayout.setBackgroundColor(Color.parseColor("#ffce26"));
        }else if(cor.equals("Verde")){
            constraintLayout.setBackgroundColor(Color.parseColor("#009670"));
        }
    }
}
