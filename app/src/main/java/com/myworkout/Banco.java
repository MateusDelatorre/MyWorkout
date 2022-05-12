package com.myworkout;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String NOME = "workout";

    public Banco(Context context){
        super(context, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL( "CREATE TABLE IF NOT EXISTS exercicios ( " +
                " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                " name TEXT NOT NULL ); "
        );

        db.execSQL( "CREATE TABLE IF NOT EXISTS workout ( " +
                " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                " name TEXT NOT NULL); "
        );

        db.execSQL( "CREATE TABLE IF NOT EXISTS workoutexercices ( " +
                " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                " idworkout INTEGER NOT NULL," +
                " idexercicio INTEGER NOT NULL, " +
                " series INTEGER NOT NULL, " +
                " repeticoes INTEGER NOT NULL, " +
                " FOREIGN KEY(idworkout) REFERENCES workout(id)," +
                "FOREIGN KEY(idexercicio) REFERENCES exercicios(id));"
        );

        //db.execSQL("INSERT INTO exercicios(name)" + " VALUES (\"Supino inclinado\"); ");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
