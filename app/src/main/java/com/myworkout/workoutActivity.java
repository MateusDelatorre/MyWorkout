package com.myworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class workoutActivity extends AppCompatActivity {

    private EditText etWName;
    private Button btnWAdicionar, btnAdicionarExercicio;
    private ListView lvTreinos;
    private Bundle extras;
    private int wid = -1;
    private int eid = -1;
    private Workout w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        this.btnWAdicionar = findViewById(R.id.btnWAdicionar);
        this.etWName = findViewById(R.id.etWName);
        this.lvTreinos = findViewById(R.id.lvTreinos);
        this.btnAdicionarExercicio = findViewById(R.id.btnAdicionarExercicio);

        this.extras = getIntent().getExtras();
        if (extras != null) {
            String acao = extras.getString("acao");
            if (acao.equals("editar")){
                this.wid = extras.getInt("wid");
                if (this.wid > 0){
                    carregarWorkout();
                }

            }
            //The key argument here must match that used in the other activity
        }
        this.btnWAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Salvar();
            }
        });

        this.lvTreinos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercicio e = (Exercicio) lvTreinos.getItemAtPosition(i);
                Intent intent = new Intent(workoutActivity.this, ExercicioActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("wid", wid);
                intent.putExtra("eid", e.getId());
                startActivity(intent);
            }
        });

        this.btnAdicionarExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(workoutActivity.this, ExercicioActivity.class);
                intent.putExtra("acao", "inserir");

                intent.putExtra("wid", wid);
                startActivity(intent);
            }
        });
    }

    private void Salvar(){
        String name = etWName.getText().toString();
        if (!name.isEmpty()){
            if (this.wid < 0){
                Workout workout = new Workout();
                workout.setName(name);
                WorkoutDAO.inserir(this, workout);
            }else{
                w.setName(name);
                WorkoutDAO.editar(this, this.w);
            }
            this.finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarTreinos();
    }

    private void carregarWorkout(){
        this.w = WorkoutDAO.getWorkout(this, wid);
        this.etWName.setText(w.getName());
    }

    private void carregarTreinos(){
//        if (this.wid < 0){
//            return;
//        }
        List<Exercicio> lista = WorkoutDAO.getExerciciosFromWorkout(this, this.wid);

        if (lista.size() == 0){
            Exercicio fake = new Exercicio(getString(R.string.ExerciseCountNull));
            lista.add(fake);
            lvTreinos.setEnabled(false);
        }else{
            lvTreinos.setEnabled(true);
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        lvTreinos.setAdapter(adapter);
    }
}

