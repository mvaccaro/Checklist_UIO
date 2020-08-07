package org.bp.teuno.checklist.UI;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Operador.I_OPERADOR;
import org.bp.teuno.checklist.SQLite.IT_Turno;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Operador
 * sobre las entidades existentes.
 */
public final class Crud_Inicio_Sesion {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Inicio_Sesion INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Inicio_Sesion();

    private Crud_Inicio_Sesion() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Inicio_Sesion OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_OPERADOR() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", TABLAS.OPERADOR);
        return db.rawQuery(sql, null);
    }

    public Cursor CONSULTA_GENERAL_OPERADOR_POR_ID(String ID_OPERADOR) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.OPERADOR, I_OPERADOR.ID_OPERADOR, I_OPERADOR.ESTADO);
        String[] selectionArgs = {ID_OPERADOR, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_TURNO() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", TABLAS.TURNO);
        return db.rawQuery(sql, null);
    }

    public Cursor CONSULTA_GENERAL_TURNO_POR_HORA() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE ? BETWEEN %s and %s", TABLAS.TURNO, IT_Turno.I_TURNO.HORA_INICIO, IT_Turno.I_TURNO.HORA_FIN);
        String[] selectionArgs = {getDateTime()};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_TURNOS() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT ID FROM %s", TABLAS.TURNO);
        return db.rawQuery(sql, null);
    }


    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm", Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, 0);
        return dateFormat.format(calendar.getTime());
    }

}
