package com.myworkout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDAO {

    public static void inserir(Context context, Workout workout){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("name", workout.getName());

        db.insert("workout", null, valores);
        db.close();
    }

    public static void inserirExercicio(Context context, Exercicio exercicio){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("name", exercicio.getName());

        db.insert("workoutexercices", null, valores);
        db.close();
    }

    public static void inserirExerciciosToWorkout(Context context, int wid, Exercicio exercicio){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idworkout", wid);
        valores.put("idexercicio", exercicio.getId());
        valores.put("series", exercicio.getSeries());
        valores.put("repeticoes", exercicio.getRepeticoes());

        db.insert("workoutexercices", null, valores);
        db.close();
    }

    public static void editar(Context context, Workout workout){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("name", workout.getName());

        db.update("workoutexercices", valores, "id = " + workout.getId(), null);
        db.close();
    }

    public static void editarExercicio(Context context, int wid, Exercicio exercicio){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("series", exercicio.getSeries());
        valores.put("repeticoes", exercicio.getRepeticoes());

        db.update("workoutexercices", valores, "idexercicio = " + exercicio.getId() + " AND idworkout = " + wid, null);
        db.close();
    }

    public static Workout getWorkout(Context context, int wid){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();
        Workout w = new Workout();

        Cursor cursor = db.rawQuery( "SELECT w.id, w.name" +
                " FROM workout w " +
                " WHERE w.id == "+ wid+ ";", null );

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();


            //do{

                w.setId(cursor.getInt(0));
                w.setName(cursor.getString(1));
                //System.out.println("pegando w do db, id =" + w.getId());
            //}while (cursor.moveToNext());
        }
        db.close();
        return  w;
    }

    public static List<Workout> getWorkouts(Context context){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();


        Cursor cursor = db.rawQuery( "SELECT w.id, w.name" +
                " FROM workout w " +
                " ORDER BY w.name", null );

        List<Workout> lista = new ArrayList<>();

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                //Genero g = new Genero();
                //g.setId(  cursor.getInt( 3 ) );
                //g.setNome(  cursor.getString( 4 ) );
                Workout w = new Workout();
                w.setId(cursor.getInt(0));
                w.setName(cursor.getString(1));
                lista.add( w );
            }while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }

    public static List<Exercicio> getExerciciosFromWorkout(Context context, int wid){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery( "SELECT w.id, w.idworkout, w.idexercicio, w.series, W.repeticoes, e.name" +
                " FROM workoutexercices w " +
                " INNER JOIN  exercicios e ON e.id = w.idexercicio" +
                " AND w.idworkout == " + wid +
                " ORDER BY e.name", null );

        List<Exercicio> lista = new ArrayList<>();

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                Exercicio e = new Exercicio();
                e.setId(cursor.getInt(2));
                e.setSeries(cursor.getInt(3));
                e.setRepeticoes(cursor.getInt(4));
                e.setName(cursor.getString(5));
                lista.add( e );
            }while (cursor.moveToNext());
        }
        db.close();
        return lista;
    }

    public static Exercicio getExercicioFromWorkout(Context context, int wid, int eid){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery( "SELECT w.id, w.idworkout, w.idexercicio, w.series, W.repeticoes, e.name" +
                " FROM workoutexercices w " +
                " INNER JOIN  exercicios e ON e.id = w.idexercicio" +
                " AND w.idworkout == " + wid +
                " AND w.idexercicio == " + eid + ";", null );

        Exercicio e = new Exercicio();
        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

                e.setId(cursor.getInt(2));
                e.setSeries(cursor.getInt(3));
                e.setRepeticoes(cursor.getInt(4));
                e.setName(cursor.getString(5));
        }
        db.close();
        return  e;
    }

    public static void printWorkoutExercices(Context context){
        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery( "SELECT * FROM workoutexercices;", null );

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                System.out.println("id = " + cursor.getInt(0));
                System.out.println("idworkout = " + cursor.getInt(1));
                System.out.println("idexercicio = " + cursor.getInt(2));
                System.out.println("series = " + cursor.getInt(3));
                System.out.println("repeticoes = " + cursor.getInt(4));
            }while (cursor.moveToNext());
        }else {
            System.out.println("null");
        }
        System.out.println("Exercicio: ");
        cursor = db.rawQuery( "SELECT * FROM exercicios;", null );

        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();

            do{
                System.out.println("id = " + cursor.getInt(0));
                System.out.println("name = " + cursor.getString(1));
            }while (cursor.moveToNext());
        }else {
            System.out.println("null");
        }
        db.close();


    }

}
