package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Alerta;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Alerta.I_ALERTA;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Alerta
 * sobre las entidades existentes.
 */
public final class Crud_Alerta {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Alerta INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Alerta();

    private Crud_Alerta() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Alerta OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_ALERTA_POR_ESTADO(String ESTADO) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String[] selectionArgs = null;
        String sql = String.format("SELECT * FROM %s WHERE %s=? order by 2,3,4", TABLAS.ALERTA, I_ALERTA.ESTADO);
        selectionArgs = new String[]{ESTADO};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_ALERTA_POR_CLIENTE(String ESTADO, String CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String[] selectionArgs = null;
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s Like ? order by 2,3,4", TABLAS.ALERTA, I_ALERTA.ESTADO, I_ALERTA.ID);
        selectionArgs = new String[]{ESTADO, CLIENTE};
        return db.rawQuery(sql, selectionArgs);
    }


    public Cursor CONSULTA_GENERAL_POR_EQUIPO(String ID_EQUIPO) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = null;
        String[] selectionArgs = null;
        sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.ALERTA, I_ALERTA.ID_EQUIPO, I_ALERTA.ESTADO);
        selectionArgs = new String[]{ID_EQUIPO, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_POR_CONDICION(String FILA, String RACK, String UR, String EQUIPO, String ESTADO, int CONDICION) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = null;
        String[] selectionArgs = null;
        if (CONDICION == 1) {
            sql = String.format("SELECT * FROM %s WHERE (%s=? and %s=? and %s=? and %s=? and %s=?)", TABLAS.ALERTA, I_ALERTA.ID_FILA, I_ALERTA.ID_RACK, I_ALERTA.ID_RACK_UR, I_ALERTA.ID_EQUIPO, I_ALERTA.ESTADO);
            selectionArgs = new String[]{FILA, RACK, UR, EQUIPO, ESTADO};
        }
        return db.rawQuery(sql, selectionArgs);
    }

    public long INSERTAR_ALERTA(Alerta ALERTA, String CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_ALERTA.GENERAR_ID_ALERTA(CLIENTE);
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_ALERTA.ID, ID);
        VALORES.put(I_ALERTA.ID_FILA, ALERTA.ID_FILA);
        VALORES.put(I_ALERTA.ID_RACK, ALERTA.ID_RACK);
        VALORES.put(I_ALERTA.ID_RACK_UR, ALERTA.UR);
        VALORES.put(I_ALERTA.ID_EQUIPO, ALERTA.ID_EQUIPO);
        VALORES.put(I_ALERTA.ID_TIPO_ALERTA, ALERTA.ID_TIPO_ALERTA);
        VALORES.put(I_ALERTA.ID_COLOR_ALERTA, ALERTA.ID_COLOR_ALERTA);
        VALORES.put(I_ALERTA.COMENTARIO, ALERTA.COMENTARIO);
        VALORES.put(I_ALERTA.TICKET_EXTERNO, ALERTA.TICKET_EXTERNO);
        VALORES.put(I_ALERTA.IMAGEN, ALERTA.IMAGEN);
        VALORES.put(I_ALERTA.ESTADO, ALERTA.ESTADO);
        VALORES.put(I_ALERTA.USUARIO_INGRESA, ALERTA.USUARIO_INGRESA);
        VALORES.put(I_ALERTA.USUARIO_MODIFICA, ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_ALERTA.FECHA_INGRESO, ALERTA.FECHA_INGRESO);
        VALORES.put(I_ALERTA.FECHA_MODIFICACION, ALERTA.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.ALERTA, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long MODIFICAR_ALERTA(Alerta ALERTA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_ALERTA.ID_FILA, ALERTA.ID_FILA);
        VALORES.put(I_ALERTA.ID_RACK, ALERTA.ID_RACK);
        VALORES.put(I_ALERTA.ID_RACK_UR, ALERTA.UR);
        VALORES.put(I_ALERTA.ID_EQUIPO, ALERTA.ID_EQUIPO);
        VALORES.put(I_ALERTA.ID_TIPO_ALERTA, ALERTA.ID_TIPO_ALERTA);
        VALORES.put(I_ALERTA.ID_COLOR_ALERTA, ALERTA.ID_COLOR_ALERTA);
        VALORES.put(I_ALERTA.COMENTARIO, ALERTA.COMENTARIO);
        VALORES.put(I_ALERTA.TICKET_EXTERNO, ALERTA.TICKET_EXTERNO);
        VALORES.put(I_ALERTA.IMAGEN, ALERTA.IMAGEN);
        VALORES.put(I_ALERTA.ESTADO, ALERTA.ESTADO);
        VALORES.put(I_ALERTA.USUARIO_INGRESA, ALERTA.USUARIO_INGRESA);
        VALORES.put(I_ALERTA.USUARIO_MODIFICA, ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_ALERTA.FECHA_INGRESO, ALERTA.FECHA_INGRESO);
        VALORES.put(I_ALERTA.FECHA_MODIFICACION, ALERTA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_ALERTA.ID);
        String[] WHEREARGS = {ALERTA.ID};
        return db.updateWithOnConflict(TABLAS.ALERTA, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long DESACTIVAR_ALERTA(Alerta ALERTA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_ALERTA.COMENTARIO, ALERTA.COMENTARIO);
        VALORES.put(I_ALERTA.ESTADO, ALERTA.ESTADO);
        VALORES.put(I_ALERTA.USUARIO_INGRESA, ALERTA.USUARIO_INGRESA);
        VALORES.put(I_ALERTA.USUARIO_MODIFICA, ALERTA.USUARIO_MODIFICA);
        VALORES.put(I_ALERTA.FECHA_INGRESO, ALERTA.FECHA_INGRESO);
        VALORES.put(I_ALERTA.FECHA_MODIFICACION, ALERTA.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_ALERTA.ID);
        String[] WHEREARGS = {ALERTA.ID};
        return db.updateWithOnConflict(TABLAS.ALERTA, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
