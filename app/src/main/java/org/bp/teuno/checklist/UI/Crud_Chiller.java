package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Chiller;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Chiller.I_CHILLER;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Chiller
 * sobre las entidades existentes.
 */
public final class Crud_Chiller {

    private static BD BASEDATOS;

    private static Crud_Chiller INSTANCIA = new Crud_Chiller();

    private Crud_Chiller() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static Crud_Chiller OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_CHILLER() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.CHILLER, I_CHILLER.ESTADO);
        String[] selectionArgs = {"A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_CHILLER_POR_ID(String ID_CHILLER) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.CHILLER, I_CHILLER.ID_CHILLER);
        String[] selectionArgs = {ID_CHILLER};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_CHILLER_POR_VALORES(String ID_CHILLER, String NOMBRE_CHILLER) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.CHILLER, I_CHILLER.ID_CHILLER, I_CHILLER.NOMBRE_CHILLER, I_CHILLER.ESTADO);
        String[] selectionArgs = {ID_CHILLER, NOMBRE_CHILLER, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_CHILLER_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.CHILLER, I_CHILLER.ID);
        String[] selectionArgs = {ID};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_CHILLER(Chiller CHILLER) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_CHILLER.GENERAR_ID_CHILLER();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_CHILLER.ID, ID);
        VALORES.put(I_CHILLER.ID_CHILLER, CHILLER.ID_CHILLER);
        VALORES.put(I_CHILLER.NOMBRE_CHILLER, CHILLER.NOMBRE_CHILLER);
        VALORES.put(I_CHILLER.MARCA, CHILLER.MARCA);
        VALORES.put(I_CHILLER.MODELO, CHILLER.MODELO);
        VALORES.put(I_CHILLER.SERIE, CHILLER.SERIE);
        VALORES.put(I_CHILLER.ESTADO, CHILLER.ESTADO);
        VALORES.put(I_CHILLER.USUARIO_INGRESA, CHILLER.USUARIO_INGRESA);
        VALORES.put(I_CHILLER.USUARIO_MODIFICA, CHILLER.USUARIO_MODIFICA);
        VALORES.put(I_CHILLER.FECHA_INGRESO, CHILLER.FECHA_INGRESO);
        VALORES.put(I_CHILLER.FECHA_MODIFICACION, CHILLER.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.CHILLER, null, VALORES)> 0 ? ID : null;
    }

    public String INSERTAR_CHILLER_VACIA(Chiller CHILLER) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        //String ID = I_CHILLER.GENERAR_ID_CHILLER();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_CHILLER.ID, CHILLER.ID);
        VALORES.put(I_CHILLER.ID_CHILLER, CHILLER.ID_CHILLER);
        VALORES.put(I_CHILLER.NOMBRE_CHILLER, CHILLER.NOMBRE_CHILLER);
        VALORES.put(I_CHILLER.MARCA, CHILLER.MARCA);
        VALORES.put(I_CHILLER.MODELO, CHILLER.MODELO);
        VALORES.put(I_CHILLER.SERIE, CHILLER.SERIE);
        VALORES.put(I_CHILLER.ESTADO, CHILLER.ESTADO);
        VALORES.put(I_CHILLER.USUARIO_INGRESA, CHILLER.USUARIO_INGRESA);
        VALORES.put(I_CHILLER.USUARIO_MODIFICA, CHILLER.USUARIO_MODIFICA);
        VALORES.put(I_CHILLER.FECHA_INGRESO, CHILLER.FECHA_INGRESO);
        VALORES.put(I_CHILLER.FECHA_MODIFICACION, CHILLER.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.CHILLER, null, VALORES) >= -1 ? CHILLER.ID : null;
    }

    public boolean MODIFICAR_CHILLER(Chiller CHILLER) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_CHILLER.ID_CHILLER, CHILLER.ID_CHILLER);
        VALORES.put(I_CHILLER.NOMBRE_CHILLER, CHILLER.NOMBRE_CHILLER);
        VALORES.put(I_CHILLER.MARCA, CHILLER.MARCA);
        VALORES.put(I_CHILLER.MODELO, CHILLER.MODELO);
        VALORES.put(I_CHILLER.SERIE, CHILLER.SERIE);
        VALORES.put(I_CHILLER.ESTADO, CHILLER.ESTADO);
        VALORES.put(I_CHILLER.USUARIO_INGRESA, CHILLER.USUARIO_INGRESA);
        VALORES.put(I_CHILLER.USUARIO_MODIFICA, CHILLER.USUARIO_MODIFICA);
        VALORES.put(I_CHILLER.FECHA_INGRESO, CHILLER.FECHA_INGRESO);
        VALORES.put(I_CHILLER.FECHA_MODIFICACION, CHILLER.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_CHILLER.ID);
        String[] WHEREARGS = {CHILLER.ID};
        int resultado = db.update(TABLAS.CHILLER, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_CHILLER(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_CHILLER.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.CHILLER, whereClause, whereArgs);
        return resultado > 0;
    }

    public boolean DESACTIVAR_CHILLER(Chiller CHILLER) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_CHILLER.ID_CHILLER, CHILLER.ID_CHILLER);
        VALORES.put(I_CHILLER.ESTADO, CHILLER.ESTADO);
        VALORES.put(I_CHILLER.USUARIO_INGRESA, CHILLER.USUARIO_INGRESA);
        VALORES.put(I_CHILLER.USUARIO_MODIFICA, CHILLER.USUARIO_MODIFICA);
        VALORES.put(I_CHILLER.FECHA_INGRESO, CHILLER.FECHA_INGRESO);
        VALORES.put(I_CHILLER.FECHA_MODIFICACION, CHILLER.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_CHILLER.ID);
        String[] WHEREARGS = {CHILLER.ID};
        int resultado = db.update(TABLAS.CHILLER, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
