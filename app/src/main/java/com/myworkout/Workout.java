package com.myworkout;

import androidx.annotation.NonNull;

import java.util.List;

public class Workout {
    private int id;
    private String name;
    private List<Exercicio> exercicios;

    Workout(){}
    Workout(String name){
        this.name = name;
    }
    Workout(String name, List<Exercicio> exercicios){
        this.name = name;
        this.exercicios = exercicios;
    }

    public void addExercice(Exercicio exercicio){
        exercicios.add(exercicio);
    }

    public String getName() {
        return this.name;
    }

    public List<Exercicio> getExercicios() {
        return this.exercicios;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name + "\n" + "Quantidade: " + (exercicios == null? "0" : Integer.toString(exercicios.size()));
    }
}
