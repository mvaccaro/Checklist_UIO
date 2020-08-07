package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Color_Alerta;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Color_Alerta.I_COLOR_ALERTA;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Color_Alerta
 * sobre las entidades existentes.
 */
public final class Crud_Color_Alerta {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Color_Alerta INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Color_Alerta();

    private Crud_Color_Alerta() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Color_Alerta OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_COLOR_ALERTA() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", TABLAS.COLOR_ALERTA);
        return db.rawQuery(sql, null);
    }

    public Cursor CONSULTA_GENERAL_COLOR_ALERTA_POR_ID(String ID_COLOR_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.COLOR_ALERTA, I_COLOR_ALERTA.ID_COLOR_ALERTA);
        String[] selectionArgs = {ID_COLOR_ALERTA};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_COLOR_ALERTA_POR_VALORES(String ID_COLOR_ALERTA, String NOMBRE_COLOR_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.COLOR_ALERTA, I_COLOR_ALERTA.ID_COLOR_ALERTA, I_COLOR_ALERTA.NOMBRE_COLOR_ALERTA, I_COLOR_ALERTA.ESTADO);
        String[] selectionArgs = {ID_COLOR_ALERTA, NOMBRE_COLOR_ALERTA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_COLOR_ALERTA(Color_Alerta COLOR_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_COLOR_ALERTA.GENERAR_ID_COLOR_ALERTA();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_COLOR_ALERTA.ID, ID);
        VALORES.put(I_COLOR_ALERTA.ID_COLOR_ALERTA, COLOR_ALERTA.ID_COLOR_ALERTA);
        VALORES.put(I_COLOR_ALERTA.NOMBRE_COLOR_ALERTA, COLOR_ALERTA.NOMBRE_COLOR_ALERTA);
        VALORES.put(I_COLOR_ALERTA.ESTADO, COLOR_ALERTA.ESTADO);
        VALORES.put(I_COLOR_ALERTA.USUARIO_INGRESA, COLOR_ALERTA.USUARIO_INGRESA);
        VALORES.put(I_COLOR_ALERTA.USUARIO_MODIFICA, COLOR_ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_COLOR_ALERTA.FECHA_INGRESO, COLOR_ALERTA.FECHA_INGRESO);
        VALORES.put(I_COLOR_ALERTA.FECHA_MODIFICACION, COLOR_ALERTA.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.COLOR_ALERTA, null, VALORES)> 0 ? ID : null;
    }

    public String INSERTAR_COLOR_ALERTA_VACIA(Color_Alerta COLOR_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        //String ID = I_COLOR_ALERTA.GENERAR_ID_COLOR_ALERTA();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_COLOR_ALERTA.ID, COLOR_ALERTA.ID);
        VALORES.put(I_COLOR_ALERTA.ID_COLOR_ALERTA, COLOR_ALERTA.ID_COLOR_ALERTA);
        VALORES.put(I_COLOR_ALERTA.NOMBRE_COLOR_ALERTA, COLOR_ALERTA.NOMBRE_COLOR_ALERTA);
        VALORES.put(I_COLOR_ALERTA.ESTADO, COLOR_ALERTA.ESTADO);
        VALORES.put(I_COLOR_ALERTA.USUARIO_INGRESA, COLOR_ALERTA.USUARIO_INGRESA);
        VALORES.put(I_COLOR_ALERTA.USUARIO_MODIFICA, COLOR_ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_COLOR_ALERTA.FECHA_INGRESO, COLOR_ALERTA.FECHA_INGRESO);
        VALORES.put(I_COLOR_ALERTA.FECHA_MODIFICACION, COLOR_ALERTA.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.COLOR_ALERTA, null, VALORES) >= -1 ? COLOR_ALERTA.ID : null;
    }

    public boolean MODIFICAR_COLOR_ALERTA(Color_Alerta COLOR_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_COLOR_ALERTA.ID_COLOR_ALERTA, COLOR_ALERTA.ID_COLOR_ALERTA);
        VALORES.put(I_COLOR_ALERTA.NOMBRE_COLOR_ALERTA, COLOR_ALERTA.NOMBRE_COLOR_ALERTA);
        VALORES.put(I_COLOR_ALERTA.ESTADO, COLOR_ALERTA.ESTADO);
        VALORES.put(I_COLOR_ALERTA.USUARIO_INGRESA, COLOR_ALERTA.USUARIO_INGRESA);
        VALORES.put(I_COLOR_ALERTA.USUARIO_MODIFICA, COLOR_ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_COLOR_ALERTA.FECHA_INGRESO, COLOR_ALERTA.FECHA_INGRESO);
        VALORES.put(I_COLOR_ALERTA.FECHA_MODIFICACION, COLOR_ALERTA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_COLOR_ALERTA.ID);
        String[] WHEREARGS = {COLOR_ALERTA.ID};
        int resultado = db.update(TABLAS.COLOR_ALERTA, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_COLOR_ALERTA(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_COLOR_ALERTA.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.COLOR_ALERTA, whereClause, whereArgs);
        return resultado > 0;
    }

    public boolean DESACTIVAR_COLOR_ALERTA(Color_Alerta COLOR_ALERTA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_COLOR_ALERTA.ID_COLOR_ALERTA, COLOR_ALERTA.ID_COLOR_ALERTA);
        VALORES.put(I_COLOR_ALERTA.ESTADO, COLOR_ALERTA.ESTADO);
        VALORES.put(I_COLOR_ALERTA.USUARIO_INGRESA, COLOR_ALERTA.USUARIO_INGRESA);
        VALORES.put(I_COLOR_ALERTA.USUARIO_MODIFICA, COLOR_ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_COLOR_ALERTA.FECHA_INGRESO, COLOR_ALERTA.FECHA_INGRESO);
        VALORES.put(I_COLOR_ALERTA.FECHA_MODIFICACION, COLOR_ALERTA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_COLOR_ALERTA.ID);
        String[] WHEREARGS = {COLOR_ALERTA.ID};
        int resultado = db.update(TABLAS.COLOR_ALERTA, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
