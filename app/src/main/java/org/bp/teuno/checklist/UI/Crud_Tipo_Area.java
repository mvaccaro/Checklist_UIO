package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Tipo_Area;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Tipo_Area.I_TIPO_AREA;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Tipo_Area
 * sobre las entidades existentes.
 */
public final class Crud_Tipo_Area {

    private static BD BASEDATOS;

    private static Crud_Tipo_Area INSTANCIA = new Crud_Tipo_Area();

    private Crud_Tipo_Area() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static Crud_Tipo_Area OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_TIPO_AREA() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.TIPO_AREA, I_TIPO_AREA.ESTADO);
        String[] selectionArgs = {"A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_TIPO_AREA_POR_ID(String ID_TIPO_AREA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.TIPO_AREA, I_TIPO_AREA.ID_TIPO_AREA);
        String[] selectionArgs = {ID_TIPO_AREA};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_TIPO_AREA_POR_VALORES(String ID_TIPO_AREA, String NOMBRE_TIPO_AREA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.TIPO_AREA, I_TIPO_AREA.ID_TIPO_AREA, I_TIPO_AREA.NOMBRE_TIPO_AREA, I_TIPO_AREA.ESTADO);
        String[] selectionArgs = {ID_TIPO_AREA, NOMBRE_TIPO_AREA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_TIPO_AREA_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.TIPO_AREA, I_TIPO_AREA.ID);
        String[] selectionArgs = {ID};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_TIPO_AREA(Tipo_Area TIPO_AREA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_TIPO_AREA.GENERAR_ID_TIPO_AREA();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_AREA.ID, ID);
        VALORES.put(I_TIPO_AREA.ID_TIPO_AREA, TIPO_AREA.ID_TIPO_AREA);
        VALORES.put(I_TIPO_AREA.NOMBRE_TIPO_AREA, TIPO_AREA.NOMBRE_TIPO_AREA);
        VALORES.put(I_TIPO_AREA.ESTADO, TIPO_AREA.ESTADO);
        VALORES.put(I_TIPO_AREA.USUARIO_INGRESA, TIPO_AREA.USUARIO_INGRESA);
        VALORES.put(I_TIPO_AREA.USUARIO_MODIFICA, TIPO_AREA.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_AREA.FECHA_INGRESO, TIPO_AREA.FECHA_INGRESO);
        VALORES.put(I_TIPO_AREA.FECHA_MODIFICACION, TIPO_AREA.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.TIPO_AREA, null, VALORES)> 0 ? ID : null;
    }

    public String INSERTAR_TIPO_AREA_VACIA(Tipo_Area TIPO_AREA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        //String ID = I_TIPO_AREA.GENERAR_ID_TIPO_AREA();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_AREA.ID, TIPO_AREA.ID);
        VALORES.put(I_TIPO_AREA.ID_TIPO_AREA, TIPO_AREA.ID_TIPO_AREA);
        VALORES.put(I_TIPO_AREA.NOMBRE_TIPO_AREA, TIPO_AREA.NOMBRE_TIPO_AREA);
        VALORES.put(I_TIPO_AREA.ESTADO, TIPO_AREA.ESTADO);
        VALORES.put(I_TIPO_AREA.USUARIO_INGRESA, TIPO_AREA.USUARIO_INGRESA);
        VALORES.put(I_TIPO_AREA.USUARIO_MODIFICA, TIPO_AREA.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_AREA.FECHA_INGRESO, TIPO_AREA.FECHA_INGRESO);
        VALORES.put(I_TIPO_AREA.FECHA_MODIFICACION, TIPO_AREA.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.TIPO_AREA, null, VALORES) >= -1 ? TIPO_AREA.ID : null;
    }

    public boolean MODIFICAR_TIPO_AREA(Tipo_Area TIPO_AREA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_AREA.ID_TIPO_AREA, TIPO_AREA.ID_TIPO_AREA);
        VALORES.put(I_TIPO_AREA.NOMBRE_TIPO_AREA, TIPO_AREA.NOMBRE_TIPO_AREA);
        VALORES.put(I_TIPO_AREA.ESTADO, TIPO_AREA.ESTADO);
        VALORES.put(I_TIPO_AREA.USUARIO_INGRESA, TIPO_AREA.USUARIO_INGRESA);
        VALORES.put(I_TIPO_AREA.USUARIO_MODIFICA, TIPO_AREA.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_AREA.FECHA_INGRESO, TIPO_AREA.FECHA_INGRESO);
        VALORES.put(I_TIPO_AREA.FECHA_MODIFICACION, TIPO_AREA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_TIPO_AREA.ID);
        String[] WHEREARGS = {TIPO_AREA.ID};
        int resultado = db.update(TABLAS.TIPO_AREA, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_TIPO_AREA(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_TIPO_AREA.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.TIPO_AREA, whereClause, whereArgs);
        return resultado > 0;
    }

    public boolean DESACTIVAR_TIPO_AREA(Tipo_Area TIPO_AREA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TIPO_AREA.ID_TIPO_AREA, TIPO_AREA.ID_TIPO_AREA);
        VALORES.put(I_TIPO_AREA.ESTADO, TIPO_AREA.ESTADO);
        VALORES.put(I_TIPO_AREA.USUARIO_INGRESA, TIPO_AREA.USUARIO_INGRESA);
        VALORES.put(I_TIPO_AREA.USUARIO_MODIFICA, TIPO_AREA.USUARIO_MODIFICA);
        VALORES.put(I_TIPO_AREA.FECHA_INGRESO, TIPO_AREA.FECHA_INGRESO);
        VALORES.put(I_TIPO_AREA.FECHA_MODIFICACION, TIPO_AREA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_TIPO_AREA.ID);
        String[] WHEREARGS = {TIPO_AREA.ID};
        int resultado = db.update(TABLAS.TIPO_AREA, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
