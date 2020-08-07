package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Tipo_Alerta;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Tipo_Alerta.I_TIPO_ALERTA;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Tipo_Alerta
 * sobre las entidades existentes.
 */
public final class Crud_Tipo_Alerta {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Tipo_Alerta INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Tipo_Alerta();

    private Crud_Tipo_Alerta() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Tipo_Alerta OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_TIPO_ALERTA() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", TABLAS.TIPO_ALERTA);
        return db.rawQuery(sql, null);
    }

    public Cursor CONSULTA_GENERAL_TIPO_ALERTA_POR_ID(String ID_TIPO_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.TIPO_ALERTA, I_TIPO_ALERTA.ID_TIPO_ALERTA, I_TIPO_ALERTA.ESTADO);
        String[] selectionArgs = {ID_TIPO_ALERTA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_TIPO_ALERTA_POR_VALORES(String ID_TIPO_ALERTA, String NOMBRE_TIPO_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.TIPO_ALERTA, I_TIPO_ALERTA.ID_TIPO_ALERTA, I_TIPO_ALERTA.NOMBRE_TIPO_ALERTA, I_TIPO_ALERTA.ESTADO);
        String[] selectionArgs = {ID_TIPO_ALERTA, NOMBRE_TIPO_ALERTA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public long INSERTAR_TIPO_ALERTA(Tipo_Alerta TIPO_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_TIPO_ALERTA.GENERAR_ID_TIPO_ALERTA();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_ALERTA.ID, ID);
        VALORES.put(I_TIPO_ALERTA.ID_TIPO_ALERTA, TIPO_ALERTA.ID_TIPO_ALERTA);
        VALORES.put(I_TIPO_ALERTA.NOMBRE_TIPO_ALERTA, TIPO_ALERTA.NOMBRE_TIPO_ALERTA);
        VALORES.put(I_TIPO_ALERTA.ESTADO, TIPO_ALERTA.ESTADO);
        VALORES.put(I_TIPO_ALERTA.USUARIO_INGRESA, TIPO_ALERTA.USUARIO_INGRESA);
        VALORES.put(I_TIPO_ALERTA.USUARIO_MODIFICA, TIPO_ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_ALERTA.FECHA_INGRESO, TIPO_ALERTA.FECHA_INGRESO);
        VALORES.put(I_TIPO_ALERTA.FECHA_MODIFICACION, TIPO_ALERTA.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.TIPO_ALERTA, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long MODIFICAR_TIPO_ALERTA(Tipo_Alerta TIPO_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_ALERTA.ID_TIPO_ALERTA, TIPO_ALERTA.ID_TIPO_ALERTA);
        VALORES.put(I_TIPO_ALERTA.NOMBRE_TIPO_ALERTA, TIPO_ALERTA.NOMBRE_TIPO_ALERTA);
        VALORES.put(I_TIPO_ALERTA.ESTADO, TIPO_ALERTA.ESTADO);
        VALORES.put(I_TIPO_ALERTA.USUARIO_INGRESA, TIPO_ALERTA.USUARIO_INGRESA);
        VALORES.put(I_TIPO_ALERTA.USUARIO_MODIFICA, TIPO_ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_ALERTA.FECHA_INGRESO, TIPO_ALERTA.FECHA_INGRESO);
        VALORES.put(I_TIPO_ALERTA.FECHA_MODIFICACION, TIPO_ALERTA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_TIPO_ALERTA.ID);
        String[] WHEREARGS = {TIPO_ALERTA.ID};
        return db.updateWithOnConflict(TABLAS.TIPO_ALERTA, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long DESACTIVAR_TIPO_ALERTA(Tipo_Alerta TIPO_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_ALERTA.ID_TIPO_ALERTA, TIPO_ALERTA.ID_TIPO_ALERTA);
        VALORES.put(I_TIPO_ALERTA.ESTADO, TIPO_ALERTA.ESTADO);
        VALORES.put(I_TIPO_ALERTA.USUARIO_INGRESA, TIPO_ALERTA.USUARIO_INGRESA);
        VALORES.put(I_TIPO_ALERTA.USUARIO_MODIFICA, TIPO_ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_ALERTA.FECHA_INGRESO, TIPO_ALERTA.FECHA_INGRESO);
        VALORES.put(I_TIPO_ALERTA.FECHA_MODIFICACION, TIPO_ALERTA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_TIPO_ALERTA.ID);
        String[] WHEREARGS = {TIPO_ALERTA.ID};
        return db.updateWithOnConflict(TABLAS.TIPO_ALERTA, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
