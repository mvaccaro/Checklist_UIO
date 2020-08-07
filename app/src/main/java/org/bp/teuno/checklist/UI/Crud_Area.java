package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Area;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Area.I_AREA;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Area
 * sobre las entidades existentes.
 */
public final class Crud_Area {

    private static BD BASEDATOS;

    private static Crud_Area INSTANCIA = new Crud_Area();

    private Crud_Area() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static Crud_Area OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_AREA() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s=?) AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-1", "TAR-2", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_AREA_A71() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s=?) AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-5", "TAR-6", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_EVENTO_A71() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-7", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_AREA_A98() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s=?) AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-3", "TAR-4", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_AREA_POR_ID(String ID_AREA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.AREA, I_AREA.ID_AREA);
        String[] selectionArgs = {ID_AREA};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_AREA_POR_VALORES(String ID_AREA, String NOMBRE_AREA, String ID_TIPO_AREA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ? OR %s=?) AND %s=?", TABLAS.AREA, I_AREA.ID_AREA, I_AREA.NOMBRE_AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {ID_AREA, NOMBRE_AREA, ID_TIPO_AREA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_UMA_A34() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-8", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_UPSA_A34() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-9", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_UPSB_A34() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-10", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_BUSWAY_A34() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-12", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_TANQUE_ECARO_A34() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-11", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_XCOM_A34() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.AREA, I_AREA.TIPO_AREA, I_AREA.ESTADO);
        String[] selectionArgs = {"TAR-13", "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_AREA(Area AREA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_AREA.GENERAR_ID_AREA();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_AREA.ID, ID);
        VALORES.put(I_AREA.ID_AREA, AREA.ID_AREA);
        VALORES.put(I_AREA.NOMBRE_AREA, AREA.NOMBRE_AREA);
        VALORES.put(I_AREA.TIPO_AREA, AREA.TIPO_AREA);
        VALORES.put(I_AREA.ESTADO, AREA.ESTADO);
        VALORES.put(I_AREA.USUARIO_INGRESA, AREA.USUARIO_INGRESA);
        VALORES.put(I_AREA.USUARIO_MODIFICA, AREA.USUARIO_MODIFICA);
        VALORES.put(I_AREA.FECHA_INGRESO, AREA.FECHA_INGRESO);
        VALORES.put(I_AREA.FECHA_MODIFICACION, AREA.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.AREA, null, VALORES)> 0 ? ID : null;
    }

    public boolean MODIFICAR_AREA(Area AREA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_AREA.ID_AREA, AREA.ID_AREA);
        VALORES.put(I_AREA.NOMBRE_AREA, AREA.NOMBRE_AREA);
        VALORES.put(I_AREA.TIPO_AREA, AREA.TIPO_AREA);
        VALORES.put(I_AREA.ESTADO, AREA.ESTADO);
        VALORES.put(I_AREA.USUARIO_INGRESA, AREA.USUARIO_INGRESA);
        VALORES.put(I_AREA.USUARIO_MODIFICA, AREA.USUARIO_MODIFICA);
        VALORES.put(I_AREA.FECHA_INGRESO, AREA.FECHA_INGRESO);
        VALORES.put(I_AREA.FECHA_MODIFICACION, AREA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_AREA.ID);
        String[] WHEREARGS = {AREA.ID};
        int resultado = db.update(TABLAS.AREA, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean DESACTIVAR_AREA(Area AREA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_AREA.ID_AREA, AREA.ID_AREA);
        VALORES.put(I_AREA.ESTADO, AREA.ESTADO);
        VALORES.put(I_AREA.USUARIO_INGRESA, AREA.USUARIO_INGRESA);
        VALORES.put(I_AREA.USUARIO_MODIFICA, AREA.USUARIO_MODIFICA);
        VALORES.put(I_AREA.FECHA_INGRESO, AREA.FECHA_INGRESO);
        VALORES.put(I_AREA.FECHA_MODIFICACION, AREA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_AREA.ID);
        String[] WHEREARGS = {AREA.ID};
        int resultado = db.update(TABLAS.AREA, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
