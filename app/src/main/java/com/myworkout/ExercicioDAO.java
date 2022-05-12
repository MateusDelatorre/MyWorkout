package com.myworkout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExercicioDAO {

    public static void inserir(Context context, String name){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("name", name);

        db.insert("exercicios", null, valores);
        db.close();
    }

    public static List<Exercicio> getExercicios(Context context){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery( "SELECT e.id, e.name" +
                " FROM exercicios e " +
                " ORDER BY e.name", null );

        List<Exercicio> lista = new ArrayList<>();

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                Exercicio e = new Exercicio();
                e.setId(cursor.getInt(0));
                e.setName(cursor.getString(1));
                lista.add( e );
            }while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }
}
