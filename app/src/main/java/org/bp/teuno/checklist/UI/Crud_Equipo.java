package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Equipo;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Equipo.I_EQUIPO;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Equipo
 * sobre las entidades existentes.
 */
public final class Crud_Equipo {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Equipo INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Equipo();

    private Crud_Equipo() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Equipo OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_EQUIPO_POR_ID_UR(String ID_RACK, String UR) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? AND %s=?) AND %s=?", TABLAS.EQUIPO, I_EQUIPO.ID_RACK, I_EQUIPO.ID_RACK_UR, I_EQUIPO.ESTADO);
        String[] selectionArgs = {ID_RACK, UR, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_EQUIPO_POR_RACK(String ID_RACK) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.EQUIPO, I_EQUIPO.ID_RACK, I_EQUIPO.ESTADO);
        String[] selectionArgs = {ID_RACK, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_EQUIPO_POR_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.EQUIPO, I_EQUIPO.ID, I_EQUIPO.ESTADO);
        String[] selectionArgs = {ID, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_POR_NOMBRE_EQUIPO(String NOMBRE_EQUIPO) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.EQUIPO, I_EQUIPO.NOMBRE_EQUIPO, I_EQUIPO.ESTADO);
        String[] selectionArgs = {NOMBRE_EQUIPO, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_EQUIPO_POR_VALORES(String NOMBRE_EQUIPO, String FILA, String RACK, String UR) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? AND %s=? AND %s=?) AND %s=?", TABLAS.EQUIPO, I_EQUIPO.ID_FILA, I_EQUIPO.ID_RACK, I_EQUIPO.ID_RACK_UR, I_EQUIPO.ESTADO);
        String[] selectionArgs = {FILA, RACK, UR, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_POR_CONDICION(String FILA, String RACK, String UR, int CONDICION) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = null;
        String[] selectionArgs = null;
        if (CONDICION == 1){
            sql = String.format("SELECT * FROM %s WHERE (%s=? and %s=? and %s=?) and %s=?", TABLAS.EQUIPO, I_EQUIPO.ID_FILA, I_EQUIPO.ID_RACK, I_EQUIPO.ID_RACK_UR, I_EQUIPO.ESTADO);
            selectionArgs = new String[]{FILA, RACK, UR, "A"};
        }
        return db.rawQuery(sql, selectionArgs);
    }

    /*
     * METODO CREADO PARA INSERTAR UN EQUIPO EN LA BASE DE DATOS
     *
     * RECIBE UN OBJETO DEL TIPO EQUIPO
     *
     * RETORNA EL ID DEL EQUIPO INGRESADO
     *
     * */

    public String INSERTAR_EQUIPO(Equipo EQUIPO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_EQUIPO.GENERAR_ID_EQUIPO();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_EQUIPO.ID, ID);
        VALORES.put(I_EQUIPO.ID_FILA, EQUIPO.ID_FILA);
        VALORES.put(I_EQUIPO.ID_RACK, EQUIPO.ID_RACK);
        VALORES.put(I_EQUIPO.ID_RACK_UR, EQUIPO.UR);
        VALORES.put(I_EQUIPO.NOMBRE_EQUIPO, EQUIPO.NOMBRE_EQUIPO);
        VALORES.put(I_EQUIPO.MARCA, EQUIPO.MARCA);
        VALORES.put(I_EQUIPO.MODELO, EQUIPO.MODELO);
        VALORES.put(I_EQUIPO.SERIE, EQUIPO.SERIE);
        VALORES.put(I_EQUIPO.CODIGO_BANCO, EQUIPO.CODIGO_BANCO);
        VALORES.put(I_EQUIPO.ESTADO, EQUIPO.ESTADO);
        VALORES.put(I_EQUIPO.USUARIO_INGRESA, EQUIPO.USUARIO_INGRESA);
        VALORES.put(I_EQUIPO.USUARIO_MODIFICA, EQUIPO.USUARIO_MODIFICA);
        VALORES.put(I_EQUIPO.FECHA_INGRESO, EQUIPO.FECHA_INGRESO);
        VALORES.put(I_EQUIPO.FECHA_MODIFICACION, EQUIPO.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.EQUIPO, null, VALORES)> 0 ? ID : null;
    }

    /*
     * METODO CREADO PARA MODIFICAR UN EQUIPO EN LA BASE DE DATOS
     *
     * RECIBE UN OBJETO DEL TIPO EQUIPO
     *
     * RETORNA -1 EN CASO DE ERROR
     * RETORNA >0 EN CASO DE EXITO
     *
     * */
    public boolean MODIFICAR_EQUIPO(Equipo EQUIPO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_EQUIPO.ID_FILA, EQUIPO.ID_FILA);
        VALORES.put(I_EQUIPO.ID_RACK, EQUIPO.ID_RACK);
        VALORES.put(I_EQUIPO.ID_RACK_UR, EQUIPO.UR);
        VALORES.put(I_EQUIPO.NOMBRE_EQUIPO, EQUIPO.NOMBRE_EQUIPO);
        VALORES.put(I_EQUIPO.MARCA, EQUIPO.MARCA);
        VALORES.put(I_EQUIPO.MODELO, EQUIPO.MODELO);
        VALORES.put(I_EQUIPO.SERIE, EQUIPO.SERIE);
        VALORES.put(I_EQUIPO.CODIGO_BANCO, EQUIPO.CODIGO_BANCO);
        VALORES.put(I_EQUIPO.ESTADO, EQUIPO.ESTADO);
        VALORES.put(I_EQUIPO.USUARIO_INGRESA, EQUIPO.USUARIO_INGRESA);
        VALORES.put(I_EQUIPO.USUARIO_MODIFICA, EQUIPO.USUARIO_MODIFICA);
        VALORES.put(I_EQUIPO.FECHA_INGRESO, EQUIPO.FECHA_INGRESO);
        VALORES.put(I_EQUIPO.FECHA_MODIFICACION, EQUIPO.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_EQUIPO.ID);
        String[] WHEREARGS = {EQUIPO.ID};
        int resultado = db.update(TABLAS.EQUIPO, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    /*
     * METODO CREADO PARA DESACTIVAR UN EQUIPO EN LA BASE DE DATOS
     *
     * RECIBE UN OBJETO DEL TIPO EQUIPO
     *
     * RETORNA -1 EN CASO DE ERROR
     * RETORNA >0 EN CASO DE EXITO
     *
     * */
    public boolean DESACTIVAR_EQUIPO(Equipo EQUIPO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_EQUIPO.ESTADO, EQUIPO.ESTADO);
        VALORES.put(I_EQUIPO.USUARIO_INGRESA, EQUIPO.USUARIO_INGRESA);
        VALORES.put(I_EQUIPO.USUARIO_MODIFICA, EQUIPO.USUARIO_MODIFICA);
        VALORES.put(I_EQUIPO.FECHA_INGRESO, EQUIPO.FECHA_INGRESO);
        VALORES.put(I_EQUIPO.FECHA_MODIFICACION, EQUIPO.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_EQUIPO.ID);
        String[] WHEREARGS = {EQUIPO.ID};
        int resultado = db.update(TABLAS.EQUIPO, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
