package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Parametros;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Parametros.I_PARAMETROS;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Parametros
 * sobre las entidades existentes.
 */
public final class Crud_Parametros {

    private static BD BASEDATOS;

    private static Crud_Parametros INSTANCIA = new Crud_Parametros();

    private Crud_Parametros() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static Crud_Parametros OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_PARAMETROS() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.PARAMETROS, I_PARAMETROS.ESTADO);
        String[] selectionArgs = {"A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_PARAMETROS_POR_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.PARAMETROS, I_PARAMETROS.ID);
        String[] selectionArgs = {ID};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_PARAMETROS_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.PARAMETROS, I_PARAMETROS.ID, I_PARAMETROS.ESTADO);
        String[] selectionArgs = {ID, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_PARAMETROS(Parametros PARAMETROS) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_PARAMETROS.GENERAR_ID_PARAMETROS();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_PARAMETROS.ID, ID);
        VALORES.put(I_PARAMETROS.TEMP_PAS_MINIMA, PARAMETROS.TEMP_PAS_MINIMA);
        VALORES.put(I_PARAMETROS.TEMP_PAS_MAXIMA, PARAMETROS.TEMP_PAS_MAXIMA);
        VALORES.put(I_PARAMETROS.HUME_PAS_MINIMA, PARAMETROS.HUME_PAS_MINIMA);
        VALORES.put(I_PARAMETROS.HUME_PAS_MAXIMA, PARAMETROS.HUME_PAS_MAXIMA);
        VALORES.put(I_PARAMETROS.TEMP_SUM_MINIMA, PARAMETROS.TEMP_SUM_MINIMA);
        VALORES.put(I_PARAMETROS.TEMP_SUM_MAXIMA, PARAMETROS.TEMP_SUM_MAXIMA);
        VALORES.put(I_PARAMETROS.TEMP_RET_MINIMA, PARAMETROS.TEMP_RET_MINIMA);
        VALORES.put(I_PARAMETROS.TEMP_RET_MAXIMA, PARAMETROS.TEMP_RET_MAXIMA);
        VALORES.put(I_PARAMETROS.ESTADO, PARAMETROS.ESTADO);
        VALORES.put(I_PARAMETROS.USUARIO_INGRESA, PARAMETROS.USUARIO_INGRESA);
        VALORES.put(I_PARAMETROS.USUARIO_MODIFICA, PARAMETROS.USUARIO_MODIFICA);
        VALORES.put(I_PARAMETROS.FECHA_INGRESO, PARAMETROS.FECHA_INGRESO);
        VALORES.put(I_PARAMETROS.FECHA_MODIFICACION, PARAMETROS.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.PARAMETROS, null, VALORES)> 0 ? ID : null;
    }

    public boolean MODIFICAR_PARAMETROS(Parametros PARAMETROS) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_PARAMETROS.TEMP_PAS_MINIMA, PARAMETROS.TEMP_PAS_MINIMA);
        VALORES.put(I_PARAMETROS.TEMP_PAS_MAXIMA, PARAMETROS.TEMP_PAS_MAXIMA);
        VALORES.put(I_PARAMETROS.HUME_PAS_MINIMA, PARAMETROS.HUME_PAS_MINIMA);
        VALORES.put(I_PARAMETROS.HUME_PAS_MAXIMA, PARAMETROS.HUME_PAS_MAXIMA);
        VALORES.put(I_PARAMETROS.TEMP_SUM_MINIMA, PARAMETROS.TEMP_SUM_MINIMA);
        VALORES.put(I_PARAMETROS.TEMP_SUM_MAXIMA, PARAMETROS.TEMP_SUM_MAXIMA);
        VALORES.put(I_PARAMETROS.TEMP_RET_MINIMA, PARAMETROS.TEMP_RET_MINIMA);
        VALORES.put(I_PARAMETROS.TEMP_RET_MAXIMA, PARAMETROS.TEMP_RET_MAXIMA);
        VALORES.put(I_PARAMETROS.ESTADO, PARAMETROS.ESTADO);
        VALORES.put(I_PARAMETROS.USUARIO_INGRESA, PARAMETROS.USUARIO_INGRESA);
        VALORES.put(I_PARAMETROS.USUARIO_MODIFICA, PARAMETROS.USUARIO_MODIFICA);
        VALORES.put(I_PARAMETROS.FECHA_INGRESO, PARAMETROS.FECHA_INGRESO);
        VALORES.put(I_PARAMETROS.FECHA_MODIFICACION, PARAMETROS.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_PARAMETROS.ID);
        String[] WHEREARGS = {PARAMETROS.ID};
        int resultado = db.update(TABLAS.PARAMETROS, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
