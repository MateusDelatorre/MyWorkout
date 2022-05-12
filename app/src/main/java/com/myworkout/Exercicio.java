package com.myworkout;

public class Exercicio {
    private int id;
    private String name;
    private int series;
    private int repeticoes;

    Exercicio(){}
    Exercicio(String name){
        this.name = name;
    }
    Exercicio(String name, int series, int repeticoes){
        this.name = name;
        this.repeticoes = repeticoes;
        this.series = series;
    }

    public int getRepeticoes() {
        return this.repeticoes;
    }

    public int getSeries() {
        return this.series;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return this.name + "\nSeries: " + this.series + "  Repetições:" + this.repeticoes;
    }
}
