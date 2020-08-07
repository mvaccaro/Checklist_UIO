package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Pasillo;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Pasillo.I_PASILLO;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Pasillo
 * sobre las entidades existentes.
 */
public final class Crud_Pasillo {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Pasillo INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Pasillo();

    private Crud_Pasillo() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Pasillo OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_PASILLO() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.PASILLO, I_PASILLO.TIPO_PASILLO, I_PASILLO.ESTADO);
        String[] selectionArgs = {"TPA-1", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_PASILLO_FRIO() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.PASILLO, I_PASILLO.TIPO_PASILLO, I_PASILLO.ESTADO);
        String[] selectionArgs = {"TPA-3", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_PASILLO_CALIENTE() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.PASILLO, I_PASILLO.TIPO_PASILLO, I_PASILLO.ESTADO);
        String[] selectionArgs = {"TPA-4", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_PASILLO_POR_ID(String ID_PASILLO) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.PASILLO, I_PASILLO.ID_PASILLO);
        String[] selectionArgs = {ID_PASILLO};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_PASILLO_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.PASILLO, I_PASILLO.ID);
        String[] selectionArgs = {ID};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_PASILLO_POR_VALORES(String ID_PASILLO, String NOMBRE_PASILLO, String ID_TIPO_PASILLO) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ? OR %s=?) AND %s=?", TABLAS.PASILLO, I_PASILLO.ID_PASILLO, I_PASILLO.NOMBRE_PASILLO, I_PASILLO.TIPO_PASILLO, I_PASILLO.ESTADO);
        String[] selectionArgs = {ID_PASILLO, NOMBRE_PASILLO, ID_TIPO_PASILLO, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_PASILLO(Pasillo PASILLO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_PASILLO.GENERAR_ID_PASILLO();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_PASILLO.ID, ID);
        VALORES.put(I_PASILLO.ID_PASILLO, PASILLO.ID_PASILLO);
        VALORES.put(I_PASILLO.NOMBRE_PASILLO, PASILLO.NOMBRE_PASILLO);
        VALORES.put(I_PASILLO.TIPO_PASILLO, PASILLO.TIPO_PASILLO);
        VALORES.put(I_PASILLO.ESTADO, PASILLO.ESTADO);
        VALORES.put(I_PASILLO.USUARIO_INGRESA, PASILLO.USUARIO_INGRESA);
        VALORES.put(I_PASILLO.USUARIO_MODIFICA, PASILLO.USUARIO_MODIFICA);
        VALORES.put(I_PASILLO.FECHA_INGRESO, PASILLO.FECHA_INGRESO);
        VALORES.put(I_PASILLO.FECHA_MODIFICACION, PASILLO.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.PASILLO, null, VALORES) > 0 ? ID : null;
    }

    public String INSERTAR_PASILLO_VACIA(Pasillo PASILLO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        //String ID = I_PASILLO.GENERAR_ID_PASILLO();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_PASILLO.ID, PASILLO.ID);
        VALORES.put(I_PASILLO.ID_PASILLO, PASILLO.ID_PASILLO);
        VALORES.put(I_PASILLO.NOMBRE_PASILLO, PASILLO.NOMBRE_PASILLO);
        VALORES.put(I_PASILLO.TIPO_PASILLO, PASILLO.TIPO_PASILLO);
        VALORES.put(I_PASILLO.ESTADO, PASILLO.ESTADO);
        VALORES.put(I_PASILLO.USUARIO_INGRESA, PASILLO.USUARIO_INGRESA);
        VALORES.put(I_PASILLO.USUARIO_MODIFICA, PASILLO.USUARIO_MODIFICA);
        VALORES.put(I_PASILLO.FECHA_INGRESO, PASILLO.FECHA_INGRESO);
        VALORES.put(I_PASILLO.FECHA_MODIFICACION, PASILLO.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.PASILLO, null, VALORES) >= -1 ? PASILLO.ID : null;
    }

    public boolean MODIFICAR_PASILLO(Pasillo PASILLO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_PASILLO.ID_PASILLO, PASILLO.ID_PASILLO);
        VALORES.put(I_PASILLO.NOMBRE_PASILLO, PASILLO.NOMBRE_PASILLO);
        VALORES.put(I_PASILLO.TIPO_PASILLO, PASILLO.TIPO_PASILLO);
        VALORES.put(I_PASILLO.ESTADO, PASILLO.ESTADO);
        VALORES.put(I_PASILLO.USUARIO_INGRESA, PASILLO.USUARIO_INGRESA);
        VALORES.put(I_PASILLO.USUARIO_MODIFICA, PASILLO.USUARIO_MODIFICA);
        VALORES.put(I_PASILLO.FECHA_INGRESO, PASILLO.FECHA_INGRESO);
        VALORES.put(I_PASILLO.FECHA_MODIFICACION, PASILLO.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_PASILLO.ID);
        String[] WHEREARGS = {PASILLO.ID};
        int resultado = db.update(TABLAS.PASILLO, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_PASILLO(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_PASILLO.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.PASILLO, whereClause, whereArgs);
        return resultado > 0;
    }

    public boolean DESACTIVAR_PASILLO(Pasillo PASILLO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_PASILLO.ID_PASILLO, PASILLO.ID_PASILLO);
        VALORES.put(I_PASILLO.ESTADO, PASILLO.ESTADO);
        VALORES.put(I_PASILLO.USUARIO_INGRESA, PASILLO.USUARIO_INGRESA);
        VALORES.put(I_PASILLO.USUARIO_MODIFICA, PASILLO.USUARIO_MODIFICA);
        VALORES.put(I_PASILLO.FECHA_INGRESO, PASILLO.FECHA_INGRESO);
        VALORES.put(I_PASILLO.FECHA_MODIFICACION, PASILLO.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_PASILLO.ID);
        String[] WHEREARGS = {PASILLO.ID};
        int resultado = db.update(TABLAS.PASILLO, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
