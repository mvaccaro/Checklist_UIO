package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Version;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Version.I_VERSION;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Version
 * sobre las entidades existentes.
 */
public final class Crud_Version {

    private static BD BASEDATOS;

    private static Crud_Version INSTANCIA = new Crud_Version();

    private Crud_Version() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static Crud_Version OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_VERSION() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.VERSION, I_VERSION.ESTADO);
        String[] selectionArgs = {"A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_VERSION_POR_NOMBRE(String NOMBRE_VERSION) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.VERSION, I_VERSION.NOMBRE_VERSION);
        String[] selectionArgs = {NOMBRE_VERSION};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_VERSION_POR_VALORES(String ID_VERSION, String NOMBRE_VERSION) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.VERSION, I_VERSION.ID_VERSION, I_VERSION.NOMBRE_VERSION, I_VERSION.ESTADO);
        String[] selectionArgs = {ID_VERSION, NOMBRE_VERSION, "A"};
        return db.rawQuery(sql, selectionArgs);
    }


    public long INSERTAR_VERSION(Version VERSION) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_VERSION.GENERAR_ID_VERSION();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_VERSION.ID, ID);
        VALORES.put(I_VERSION.ID_VERSION, VERSION.ID_VERSION);
        VALORES.put(I_VERSION.NOMBRE_VERSION, VERSION.NOMBRE_VERSION);
        VALORES.put(I_VERSION.MENSAJE, VERSION.MENSAJE);
        VALORES.put(I_VERSION.COLABORADORES, VERSION.COLABORADORES);
        VALORES.put(I_VERSION.ESTADO, VERSION.ESTADO);
        VALORES.put(I_VERSION.USUARIO_INGRESA, VERSION.USUARIO_INGRESA);
        VALORES.put(I_VERSION.USUARIO_MODIFICA, VERSION.USUARIO_MODIFICA);
        VALORES.put(I_VERSION.FECHA_INGRESO, VERSION.FECHA_INGRESO);
        VALORES.put(I_VERSION.FECHA_MODIFICACION, VERSION.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.VERSION, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public boolean DESACTIVAR_VERSION(Version VERSION) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_VERSION.ID_VERSION, VERSION.ID_VERSION);
        VALORES.put(I_VERSION.ESTADO, VERSION.ESTADO);
        VALORES.put(I_VERSION.USUARIO_INGRESA, VERSION.USUARIO_INGRESA);
        VALORES.put(I_VERSION.USUARIO_MODIFICA, VERSION.USUARIO_MODIFICA);
        VALORES.put(I_VERSION.FECHA_INGRESO, VERSION.FECHA_INGRESO);
        VALORES.put(I_VERSION.FECHA_MODIFICACION, VERSION.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_VERSION.ID);
        String[] WHEREARGS = {VERSION.ID};
        int resultado = db.update(TABLAS.VERSION, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public long DESACTIVAR_VERSION_ANTERIOR(Version VERSION) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_VERSION.ESTADO, VERSION.ESTADO);
        VALORES.put(I_VERSION.USUARIO_INGRESA, VERSION.USUARIO_INGRESA);
        VALORES.put(I_VERSION.USUARIO_MODIFICA, VERSION.USUARIO_MODIFICA);
        VALORES.put(I_VERSION.FECHA_INGRESO, VERSION.FECHA_INGRESO);
        VALORES.put(I_VERSION.FECHA_MODIFICACION, VERSION.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_VERSION.ID);
        String[] WHEREARGS = {VERSION.ID};
        return db.updateWithOnConflict(TABLAS.VERSION, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
