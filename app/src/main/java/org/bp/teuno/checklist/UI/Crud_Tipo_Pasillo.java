package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Tipo_Pasillo;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Tipo_Pasillo.I_TIPO_PASILLO;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Tipo_Pasillo
 * sobre las entidades existentes.
 */
public final class Crud_Tipo_Pasillo {

    private static BD BASEDATOS;

    private static Crud_Tipo_Pasillo INSTANCIA = new Crud_Tipo_Pasillo();

    private Crud_Tipo_Pasillo() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static Crud_Tipo_Pasillo OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_TIPO_PASILLO() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.TIPO_PASILLO, I_TIPO_PASILLO.ESTADO);
        String[] selectionArgs = {"A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_TIPO_PASILLO_POR_ID(String ID_TIPO_PASILLO) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.TIPO_PASILLO, I_TIPO_PASILLO.ID_TIPO_PASILLO);
        String[] selectionArgs = {ID_TIPO_PASILLO};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_TIPO_PASILLO_POR_VALORES(String ID_TIPO_PASILLO, String NOMBRE_TIPO_PASILLO) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.TIPO_PASILLO, I_TIPO_PASILLO.ID_TIPO_PASILLO, I_TIPO_PASILLO.NOMBRE_TIPO_PASILLO, I_TIPO_PASILLO.ESTADO);
        String[] selectionArgs = {ID_TIPO_PASILLO, NOMBRE_TIPO_PASILLO, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_TIPO_PASILLO_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.TIPO_PASILLO, I_TIPO_PASILLO.ID);
        String[] selectionArgs = {ID};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_TIPO_PASILLO(Tipo_Pasillo TIPO_PASILLO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_TIPO_PASILLO.GENERAR_ID_TIPO_PASILLO();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_PASILLO.ID, ID);
        VALORES.put(I_TIPO_PASILLO.ID_TIPO_PASILLO, TIPO_PASILLO.ID_TIPO_PASILLO);
        VALORES.put(I_TIPO_PASILLO.NOMBRE_TIPO_PASILLO, TIPO_PASILLO.NOMBRE_TIPO_PASILLO);
        VALORES.put(I_TIPO_PASILLO.ESTADO, TIPO_PASILLO.ESTADO);
        VALORES.put(I_TIPO_PASILLO.USUARIO_INGRESA, TIPO_PASILLO.USUARIO_INGRESA);
        VALORES.put(I_TIPO_PASILLO.USUARIO_MODIFICA, TIPO_PASILLO.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_PASILLO.FECHA_INGRESO, TIPO_PASILLO.FECHA_INGRESO);
        VALORES.put(I_TIPO_PASILLO.FECHA_MODIFICACION, TIPO_PASILLO.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.TIPO_PASILLO, null, VALORES)> 0 ? ID : null;
    }

    public String INSERTAR_TIPO_PASILLO_VACIA(Tipo_Pasillo TIPO_PASILLO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        //String ID = I_TIPO_PASILLO.GENERAR_ID_TIPO_PASILLO();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_PASILLO.ID, TIPO_PASILLO.ID);
        VALORES.put(I_TIPO_PASILLO.ID_TIPO_PASILLO, TIPO_PASILLO.ID_TIPO_PASILLO);
        VALORES.put(I_TIPO_PASILLO.NOMBRE_TIPO_PASILLO, TIPO_PASILLO.NOMBRE_TIPO_PASILLO);
        VALORES.put(I_TIPO_PASILLO.ESTADO, TIPO_PASILLO.ESTADO);
        VALORES.put(I_TIPO_PASILLO.USUARIO_INGRESA, TIPO_PASILLO.USUARIO_INGRESA);
        VALORES.put(I_TIPO_PASILLO.USUARIO_MODIFICA, TIPO_PASILLO.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_PASILLO.FECHA_INGRESO, TIPO_PASILLO.FECHA_INGRESO);
        VALORES.put(I_TIPO_PASILLO.FECHA_MODIFICACION, TIPO_PASILLO.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.TIPO_PASILLO, null, VALORES) >= -1 ? TIPO_PASILLO.ID : null;
    }

    public boolean MODIFICAR_TIPO_PASILLO(Tipo_Pasillo TIPO_PASILLO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_PASILLO.ID_TIPO_PASILLO, TIPO_PASILLO.ID_TIPO_PASILLO);
        VALORES.put(I_TIPO_PASILLO.NOMBRE_TIPO_PASILLO, TIPO_PASILLO.NOMBRE_TIPO_PASILLO);
        VALORES.put(I_TIPO_PASILLO.ESTADO, TIPO_PASILLO.ESTADO);
        VALORES.put(I_TIPO_PASILLO.USUARIO_INGRESA, TIPO_PASILLO.USUARIO_INGRESA);
        VALORES.put(I_TIPO_PASILLO.USUARIO_MODIFICA, TIPO_PASILLO.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_PASILLO.FECHA_INGRESO, TIPO_PASILLO.FECHA_INGRESO);
        VALORES.put(I_TIPO_PASILLO.FECHA_MODIFICACION, TIPO_PASILLO.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_TIPO_PASILLO.ID);
        String[] WHEREARGS = {TIPO_PASILLO.ID};
        int resultado = db.update(TABLAS.TIPO_PASILLO, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_TIPO_PASILLO(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_TIPO_PASILLO.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.TIPO_PASILLO, whereClause, whereArgs);
        return resultado > 0;
    }

    public boolean DESACTIVAR_TIPO_PASILLO(Tipo_Pasillo TIPO_PASILLO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_PASILLO.ID_TIPO_PASILLO, TIPO_PASILLO.ID_TIPO_PASILLO);
        VALORES.put(I_TIPO_PASILLO.ESTADO, TIPO_PASILLO.ESTADO);
        VALORES.put(I_TIPO_PASILLO.USUARIO_INGRESA, TIPO_PASILLO.USUARIO_INGRESA);
        VALORES.put(I_TIPO_PASILLO.USUARIO_MODIFICA, TIPO_PASILLO.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_PASILLO.FECHA_INGRESO, TIPO_PASILLO.FECHA_INGRESO);
        VALORES.put(I_TIPO_PASILLO.FECHA_MODIFICACION, TIPO_PASILLO.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_TIPO_PASILLO.ID);
        String[] WHEREARGS = {TIPO_PASILLO.ID};
        int resultado = db.update(TABLAS.TIPO_PASILLO, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
