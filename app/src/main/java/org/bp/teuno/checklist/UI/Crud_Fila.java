package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Fila;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Fila.I_FILA;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Fila
 * sobre las entidades existentes.
 */
public final class Crud_Fila {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Fila INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Fila();

    private Crud_Fila() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Fila OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_FILA() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.FILA, I_FILA.ESTADO);
        String[] selectionArgs = {"A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_FILA_POR_CLIENTE(String CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s Like ?", TABLAS.FILA, I_FILA.ESTADO, I_FILA.ID_FILA);
        String[] selectionArgs = {"A", CLIENTE};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_FILA_POR_ID(String ID_FILA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.FILA, I_FILA.ID_FILA);
        String[] selectionArgs = {ID_FILA};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_FILA_POR_VALORES(String ID_FILA, String NOMBRE_FILA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.FILA, I_FILA.ID_FILA, I_FILA.NOMBRE_FILA, I_FILA.ESTADO);
        String[] selectionArgs = {ID_FILA, NOMBRE_FILA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_FILA_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.FILA, I_FILA.ID, I_FILA.ESTADO);
        String[] selectionArgs = {ID, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public long INSERTAR_FILA(Fila FILA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_FILA.GENERAR_ID_FILA();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_FILA.ID, ID);
        VALORES.put(I_FILA.ID_FILA, FILA.ID_FILA);
        VALORES.put(I_FILA.NOMBRE_FILA, FILA.NOMBRE_FILA);
        VALORES.put(I_FILA.ESTADO, FILA.ESTADO);
        VALORES.put(I_FILA.USUARIO_INGRESA, FILA.USUARIO_INGRESA);
        VALORES.put(I_FILA.USUARIO_MODIFICA, FILA.USUARIO_MODIFICA);
        VALORES.put(I_FILA.FECHA_INGRESO, FILA.FECHA_INGRESO);
        VALORES.put(I_FILA.FECHA_MODIFICACION, FILA.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.FILA, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long MODIFICAR_FILA(Fila FILA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_FILA.ID_FILA, FILA.ID_FILA);
        VALORES.put(I_FILA.NOMBRE_FILA, FILA.NOMBRE_FILA);
        VALORES.put(I_FILA.ESTADO, FILA.ESTADO);
        VALORES.put(I_FILA.USUARIO_INGRESA, FILA.USUARIO_INGRESA);
        VALORES.put(I_FILA.USUARIO_MODIFICA, FILA.USUARIO_MODIFICA);
        VALORES.put(I_FILA.FECHA_INGRESO, FILA.FECHA_INGRESO);
        VALORES.put(I_FILA.FECHA_MODIFICACION, FILA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_FILA.ID);
        String[] WHEREARGS = {FILA.ID};
        return db.updateWithOnConflict(TABLAS.FILA, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long DESACTIVAR_FILA(Fila FILA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_FILA.ID_FILA, FILA.ID_FILA);
        VALORES.put(I_FILA.ESTADO, FILA.ESTADO);
        VALORES.put(I_FILA.USUARIO_INGRESA, FILA.USUARIO_INGRESA);
        VALORES.put(I_FILA.USUARIO_MODIFICA, FILA.USUARIO_MODIFICA);
        VALORES.put(I_FILA.FECHA_INGRESO, FILA.FECHA_INGRESO);
        VALORES.put(I_FILA.FECHA_MODIFICACION, FILA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_FILA.ID);
        String[] WHEREARGS = {FILA.ID};
        return db.updateWithOnConflict(TABLAS.FILA, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
