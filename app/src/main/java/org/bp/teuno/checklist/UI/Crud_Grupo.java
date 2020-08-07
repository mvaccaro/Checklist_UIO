package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Grupo;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Grupo.I_GRUPO;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Grupo
 * sobre las entidades existentes.
 */
public final class Crud_Grupo {

    private static BD BASEDATOS;

    private static Crud_Grupo INSTANCIA = new Crud_Grupo();

    private Crud_Grupo() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static Crud_Grupo OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_GRUPO() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.GRUPO, I_GRUPO.ESTADO);
        String[] selectionArgs = {"A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_GRUPO_POR_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.GRUPO, I_GRUPO.ID, I_GRUPO.ESTADO);
        String[] selectionArgs = {ID, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_GRUPO_POR_VALORES(String ID_GRUPO, String NOMBRE_GRUPO) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.GRUPO, I_GRUPO.ID_GRUPO, I_GRUPO.NOMBRE_GRUPO, I_GRUPO.ESTADO);
        String[] selectionArgs = {ID_GRUPO, NOMBRE_GRUPO, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_GRUPO_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.GRUPO, I_GRUPO.ID, I_GRUPO.ESTADO);
        String[] selectionArgs = {ID, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_GRUPO(Grupo GRUPO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_GRUPO.GENERAR_ID_GRUPO();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_GRUPO.ID, ID);
        VALORES.put(I_GRUPO.ID_GRUPO, GRUPO.ID_GRUPO);
        VALORES.put(I_GRUPO.NOMBRE_GRUPO, GRUPO.NOMBRE_GRUPO);
        VALORES.put(I_GRUPO.PIN_SEGURIDAD, GRUPO.PIN_SEGURIDAD);
        VALORES.put(I_GRUPO.PERMISO_FILA, GRUPO.PERMISO_FILA);
        VALORES.put(I_GRUPO.PERMISO_CLIENTE, GRUPO.PERMISO_CLIENTE);
        VALORES.put(I_GRUPO.PERMISO_RACK, GRUPO.PERMISO_RACK);
        VALORES.put(I_GRUPO.PERMISO_EQUIPO, GRUPO.PERMISO_EQUIPO);
        VALORES.put(I_GRUPO.PERMISO_TIPO_ALERTA, GRUPO.PERMISO_TIPO_ALERTA);
        VALORES.put(I_GRUPO.PERMISO_COLOR_ALERTA, GRUPO.PERMISO_COLOR_ALERTA);
        VALORES.put(I_GRUPO.PERMISO_ALERTA, GRUPO.PERMISO_ALERTA);
        VALORES.put(I_GRUPO.PERMISO_PASILLO, GRUPO.PERMISO_PASILLO);
        VALORES.put(I_GRUPO.PERMISO_CHILLER, GRUPO.PERMISO_CHILLER);
        VALORES.put(I_GRUPO.PERMISO_AREA, GRUPO.PERMISO_AREA);
        VALORES.put(I_GRUPO.PERMISO_GRUPO, GRUPO.PERMISO_GRUPO);
        VALORES.put(I_GRUPO.PERMISO_VERSION, GRUPO.PERMISO_VERSION);
        VALORES.put(I_GRUPO.PERMISO_PARAMETROS, GRUPO.PERMISO_PARAMETROS);
        VALORES.put(I_GRUPO.PERMISO_CANTIDAD_UR, GRUPO.PERMISO_CANTIDAD_UR);
        VALORES.put(I_GRUPO.ESTADO, GRUPO.ESTADO);
        VALORES.put(I_GRUPO.USUARIO_INGRESA, GRUPO.USUARIO_INGRESA);
        VALORES.put(I_GRUPO.USUARIO_MODIFICA, GRUPO.USUARIO_MODIFICA);
        VALORES.put(I_GRUPO.FECHA_INGRESO, GRUPO.FECHA_INGRESO);
        VALORES.put(I_GRUPO.FECHA_MODIFICACION, GRUPO.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.GRUPO, null, VALORES)> 0 ? ID : null;
    }

    public boolean MODIFICAR_GRUPO(Grupo GRUPO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_GRUPO.ID_GRUPO, GRUPO.ID_GRUPO);
        VALORES.put(I_GRUPO.NOMBRE_GRUPO, GRUPO.NOMBRE_GRUPO);
        VALORES.put(I_GRUPO.PIN_SEGURIDAD, GRUPO.PIN_SEGURIDAD);
        VALORES.put(I_GRUPO.PERMISO_FILA, GRUPO.PERMISO_FILA);
        VALORES.put(I_GRUPO.PERMISO_CLIENTE, GRUPO.PERMISO_CLIENTE);
        VALORES.put(I_GRUPO.PERMISO_RACK, GRUPO.PERMISO_RACK);
        VALORES.put(I_GRUPO.PERMISO_EQUIPO, GRUPO.PERMISO_EQUIPO);
        VALORES.put(I_GRUPO.PERMISO_TIPO_ALERTA, GRUPO.PERMISO_TIPO_ALERTA);
        VALORES.put(I_GRUPO.PERMISO_COLOR_ALERTA, GRUPO.PERMISO_COLOR_ALERTA);
        VALORES.put(I_GRUPO.PERMISO_ALERTA, GRUPO.PERMISO_ALERTA);
        VALORES.put(I_GRUPO.PERMISO_PASILLO, GRUPO.PERMISO_PASILLO);
        VALORES.put(I_GRUPO.PERMISO_CHILLER, GRUPO.PERMISO_CHILLER);
        VALORES.put(I_GRUPO.PERMISO_AREA, GRUPO.PERMISO_AREA);
        VALORES.put(I_GRUPO.PERMISO_GRUPO, GRUPO.PERMISO_GRUPO);
        VALORES.put(I_GRUPO.PERMISO_VERSION, GRUPO.PERMISO_VERSION);
        VALORES.put(I_GRUPO.PERMISO_PARAMETROS, GRUPO.PERMISO_PARAMETROS);
        VALORES.put(I_GRUPO.PERMISO_CANTIDAD_UR, GRUPO.PERMISO_CANTIDAD_UR);
        VALORES.put(I_GRUPO.ESTADO, GRUPO.ESTADO);
        VALORES.put(I_GRUPO.USUARIO_INGRESA, GRUPO.USUARIO_INGRESA);
        VALORES.put(I_GRUPO.USUARIO_MODIFICA, GRUPO.USUARIO_MODIFICA);
        VALORES.put(I_GRUPO.FECHA_INGRESO, GRUPO.FECHA_INGRESO);
        VALORES.put(I_GRUPO.FECHA_MODIFICACION, GRUPO.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_GRUPO.ID);
        String[] WHEREARGS = {GRUPO.ID};
        int resultado = db.update(TABLAS.GRUPO, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_GRUPO(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_GRUPO.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.GRUPO, whereClause, whereArgs);
        return resultado > 0;
    }

    public boolean DESACTIVAR_GRUPO(Grupo GRUPO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_GRUPO.ID_GRUPO, GRUPO.ID_GRUPO);
        VALORES.put(I_GRUPO.ESTADO, GRUPO.ESTADO);
        VALORES.put(I_GRUPO.USUARIO_INGRESA, GRUPO.USUARIO_INGRESA);
        VALORES.put(I_GRUPO.USUARIO_MODIFICA, GRUPO.USUARIO_MODIFICA);
        VALORES.put(I_GRUPO.FECHA_INGRESO, GRUPO.FECHA_INGRESO);
        VALORES.put(I_GRUPO.FECHA_MODIFICACION, GRUPO.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_GRUPO.ID);
        String[] WHEREARGS = {GRUPO.ID};
        int resultado = db.update(TABLAS.GRUPO, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
