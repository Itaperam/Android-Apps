package com.example.listadetarefas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private EditText textTarefa;
    private Button botaoAdd;
    private ListView listaTarefas;
    private SQLiteDatabase bancoDados;

    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            //Recuperando componentes da tela
            textTarefa = findViewById(R.id.text_id);
            botaoAdd = findViewById(R.id.adcionar_id);
            //listaTarefas = findViewById(R.id.listView_id);
            //Lista
            listaTarefas = findViewById(R.id.listView_id);

            //Banco de dados
            bancoDados = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);

            //tabela de tarefas
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS tarefas(id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");

            //Capturando a ação do botão add
            botaoAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String textoDigitado = textTarefa.getText().toString();

                    //Metodo d evalidação
                    salvarTarefa(textoDigitado);

                    //Salva na tabela do BD
                    //bancoDados.execSQL("INSERT INTO tarefas(tarefa) VALUES('" + textoDigitado + "') ");
                }
            });

            listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Log.i("ITEM: ", position + " / " + ids.get(position));
                    removerTarefa(ids.get(position));
                }
            });

            //Listar as tarefas
            recuperartarefas();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    //Salva a tarefa na tabela
    private void salvarTarefa(String texto){

        try{
            if(texto.equals("")){
                Toast.makeText(MainActivity.this, "Digite algo", Toast.LENGTH_SHORT).show();
            }
            else {
                bancoDados.execSQL("INSERT INTO tarefas(tarefa) VALUES('" + texto + "') ");
                Toast.makeText(MainActivity.this, "Tarefa Salva!", Toast.LENGTH_SHORT).show();
                recuperartarefas();
                textTarefa.setText("");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Recupera as tarefas
    private void recuperartarefas(){
        try {
            //Recupera as tarefas
            Cursor cursor = bancoDados.rawQuery("SELECT * FROM tarefas ORDER BY id DESC", null);
            //Recupera os ids das colunas
            int indiceColunaId = cursor.getColumnIndex("id");
            int indiceColunaTarefa = cursor.getColumnIndex("tarefa");

            //Criação adaptador
            itens = new ArrayList<String>();
            ids = new ArrayList<Integer>();
            itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1,
                    itens);

            listaTarefas.setAdapter(itensAdaptador);

            //Listar as tarefas
            cursor.moveToFirst();

            while(cursor != null){

                Log.i("Resultado - ", "Tarefa: " + cursor.getString(indiceColunaTarefa));
                itens.add(cursor.getString(indiceColunaTarefa));
                ids.add(Integer.parseInt(cursor.getString(indiceColunaId)));

                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void removerTarefa(Integer id){

        try{
            bancoDados.execSQL("DELETE FROM tarefas WHERE id=" + id);
            recuperartarefas();
            Toast.makeText(MainActivity.this, "Tarefa Apagada!", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
