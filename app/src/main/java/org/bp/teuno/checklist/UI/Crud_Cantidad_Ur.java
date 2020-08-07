package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Cantidad_Ur;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Cantidad_Ur.I_UR;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Cantidad_Ur
 * sobre las entidades existentes.
 */
public final class Crud_Cantidad_Ur {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Cantidad_Ur INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Cantidad_Ur();

    private Crud_Cantidad_Ur() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Cantidad_Ur OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_UR() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.CANTIDAD_UR, I_UR.ESTADO);
        String[] selectionArgs = {"A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_UR_POR_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.CANTIDAD_UR, I_UR.ID, I_UR.ESTADO);
        String[] selectionArgs = {ID, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_UR_POR_CANTIDAD(String CANTIDAD) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.CANTIDAD_UR, I_UR.CANTIDAD_UR);
        String[] selectionArgs = {CANTIDAD};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_CANTIDAD_UR_POR_VALORES(String ID_CANTIDAD_UR, String CANTIDAD_UR) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.CANTIDAD_UR, I_UR.ID_UR, I_UR.CANTIDAD_UR, I_UR.ESTADO);
        String[] selectionArgs = {ID_CANTIDAD_UR, CANTIDAD_UR, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_UR_POR_ID_UR(String ID_UR) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.CANTIDAD_UR, I_UR.ID_UR);
        String[] selectionArgs = {ID_UR};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_CANTIDAD_UR(Cantidad_Ur UR) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_UR.GENERAR_ID_UR();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_UR.ID, ID);
        VALORES.put(I_UR.ID_UR, UR.ID_UR);
        VALORES.put(I_UR.CANTIDAD_UR, UR.CANTIDAD_UR);
        VALORES.put(I_UR.ESTADO, UR.ESTADO);
        VALORES.put(I_UR.USUARIO_INGRESA, UR.USUARIO_INGRESA);
        VALORES.put(I_UR.USUARIO_MODIFICA, UR.USUARIO_MODIFICA);
        VALORES.put(I_UR.FECHA_INGRESO, UR.FECHA_INGRESO);
        VALORES.put(I_UR.FECHA_MODIFICACION, UR.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.CANTIDAD_UR, null, VALORES)> 0 ? ID : null;
    }

    public String INSERTAR_UR_VACIO(Cantidad_Ur UR) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        //String ID = I_UR.GENERAR_ID_UR();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_UR.ID, UR.ID);
        VALORES.put(I_UR.ID_UR, UR.ID_UR);
        VALORES.put(I_UR.CANTIDAD_UR, UR.CANTIDAD_UR);
        VALORES.put(I_UR.ESTADO, UR.ESTADO);
        VALORES.put(I_UR.USUARIO_INGRESA, UR.USUARIO_INGRESA);
        VALORES.put(I_UR.USUARIO_MODIFICA, UR.USUARIO_MODIFICA);
        VALORES.put(I_UR.FECHA_INGRESO, UR.FECHA_INGRESO);
        VALORES.put(I_UR.FECHA_MODIFICACION, UR.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.CANTIDAD_UR, null, VALORES) >= -1 ? UR.ID : null;
    }

    public boolean MODIFICAR_CANTIDAD_UR(Cantidad_Ur UR) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_UR.ID_UR, UR.ID_UR);
        VALORES.put(I_UR.CANTIDAD_UR, UR.CANTIDAD_UR);
        VALORES.put(I_UR.ESTADO, UR.ESTADO);
        VALORES.put(I_UR.USUARIO_INGRESA, UR.USUARIO_INGRESA);
        VALORES.put(I_UR.USUARIO_MODIFICA, UR.USUARIO_MODIFICA);
        VALORES.put(I_UR.FECHA_INGRESO, UR.FECHA_INGRESO);
        VALORES.put(I_UR.FECHA_MODIFICACION, UR.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_UR.ID);
        String[] WHEREARGS = {UR.ID};
        int resultado = db.update(TABLAS.CANTIDAD_UR, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean DESACTIVAR_CANTIDAD_UR(Cantidad_Ur UR) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_UR.ID_UR, UR.ID_UR);
        VALORES.put(I_UR.ESTADO, UR.ESTADO);
        VALORES.put(I_UR.USUARIO_INGRESA, UR.USUARIO_INGRESA);
        VALORES.put(I_UR.USUARIO_MODIFICA, UR.USUARIO_MODIFICA);
        VALORES.put(I_UR.FECHA_INGRESO, UR.FECHA_INGRESO);
        VALORES.put(I_UR.FECHA_MODIFICACION, UR.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_UR.ID);
        String[] WHEREARGS = {UR.ID};
        int resultado = db.update(TABLAS.CANTIDAD_UR, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_UR(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_UR.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.CANTIDAD_UR, whereClause, whereArgs);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
