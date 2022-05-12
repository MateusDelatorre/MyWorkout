package com.myworkout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class ExercicioActivity extends AppCompatActivity {

    private Spinner spExercicios;
    private EditText etSeries, etRepeticoes;
    private Button btnAddExercicio;
    private Bundle extras;
    private int wid = -1;
    private int eid = -1;
    private boolean acao = false;//fals == inserir; true == editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercicio);
        spExercicios = findViewById(R.id.spExercicio);
        this.etSeries = findViewById(R.id.etSeries);
        this.etRepeticoes = findViewById(R.id.etRepeticoes);
        this.btnAddExercicio = findViewById(R.id.btnAddExercicio);
        this.btnAddExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Salvar();
            }
        });
        this.extras = getIntent().getExtras();
        if (extras != null) {
            String acao = extras.getString("acao");
            this.wid = extras.getInt("wid");
            if (acao.equals("editar")){
                this.eid = extras.getInt("eid");
                this.acao = true;
                Exercicio e = WorkoutDAO.getExercicioFromWorkout(this, this.wid, this.eid);
                this.etSeries.setText(Integer.toString(e.getSeries())); //= findViewById(R.id.etSeries);
                this.etRepeticoes.setText(Integer.toString(e.getRepeticoes()));
                System.out.println("wid = " + this.wid + "\neid = " + this.eid);

            }
            if (this.wid < 0){
                this.finish();
            }
            //The key argument here must match that used in the other activity
        }
        carregarExercicios();
    }

    private void Salvar(){
        Exercicio exercicio = (Exercicio) spExercicios.getSelectedItem();
        int series = Integer.valueOf(this.etSeries.getText().toString());
        int repeticoes = Integer.valueOf(this.etRepeticoes.getText().toString());
        if (series > 0 && repeticoes > 0){
            exercicio.setSeries(series);
            System.out.println("eid = " + exercicio.getId());
            exercicio.setRepeticoes(repeticoes);
            if (this.acao){
                exercicio.setId(this.eid);
                WorkoutDAO.editarExercicio(this, this.wid, exercicio);
            }else {
                if(spExercicios.getSelectedItemPosition() > 0) {
                    WorkoutDAO.inserirExerciciosToWorkout(this, this.wid, exercicio);
                }
            }
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(getString(R.string.menuAddExercise));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.toString().equals(getString(R.string.menuAddExercise))){
            //GeneroDAO.inserir(MainActivity.this, "Romance");
            cadastrarExercicio();
        }
        return super.onOptionsItemSelected(item);
    }

    private void cadastrarExercicio(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(getString(R.string.menuAddExercise));
        alerta.setIcon(android.R.drawable.ic_input_add);

        EditText etNomeExercicio = new EditText(this);
        etNomeExercicio.setHint(getString(R.string.menuLabel));
        //    alerta.setMessage("Olá");1
        alerta.setView( etNomeExercicio );

        alerta.setNeutralButton(getString(R.string.menuCancelOption), null);

        alerta.setPositiveButton(getString(R.string.menuSaveOption), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome = etNomeExercicio.getText().toString();
                if( !nome.isEmpty() ){
                    ExercicioDAO.inserir(ExercicioActivity.this, nome);
                    carregarExercicios();
                }
            }
        });
        alerta.show();

    }

    private void carregarExercicios(){
        List<Exercicio> lista = ExercicioDAO.getExercicios(  this );
        if (this.eid < 0){
            Exercicio fake = new Exercicio(getString(R.string.spinnerDefault));
            lista.add(0, fake);
        }else{
            for (Exercicio e : lista) {
                if (e.getId() == this.eid){
                    //Exercicio new_e = e;
                    lista.remove(e);
                    lista.add(0, e);
                    break;
                }
            }
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista );
        spExercicios.setAdapter( adapter );
    }

}