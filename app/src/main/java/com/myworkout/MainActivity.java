package com.myworkout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvWorkouts;
    private Button btnAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WorkoutDAO.printWorkoutExercices(this);

        this.btnAdicionar = findViewById(R.id.btnAdicionar);
        this.lvWorkouts = findViewById(R.id.lvWorkouts);
        this.lvWorkouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Workout w = (Workout) lvWorkouts.getItemAtPosition(i);
                //System.out.println("mandando w para wactivity " + w.getName());
                Intent intent = new Intent(MainActivity.this, workoutActivity.class);
                intent.putExtra("acao", "editar");
                intent.putExtra("wid", w.getId());
                startActivity(intent);
            }
        });

        this.btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, workoutActivity.class);
                intent.putExtra("acao", "inserir");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarWorkouts();
    }

    private void carregarWorkouts(){
        List<Workout> lista = WorkoutDAO.getWorkouts(this);

        if (lista.size() == 0){
            Workout fake = new Workout(getString(R.string.workoutCountNull), null);
            lista.add(fake);
            lvWorkouts.setEnabled(false);
        }else{
            lvWorkouts.setEnabled(true);
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        lvWorkouts.setAdapter(adapter);
    }
}