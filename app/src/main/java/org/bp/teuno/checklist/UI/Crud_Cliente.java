package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Cliente;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Cliente.I_CLIENTE;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Cliente
 * sobre las entidades existentes.
 */
public final class Crud_Cliente {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Cliente INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Cliente();

    private Crud_Cliente() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Cliente OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_CLIENTE() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s Like ?", TABLAS.CLIENTE, I_CLIENTE.ESTADO, I_CLIENTE.ID_CLIENTE);
        String[] selectionArgs = {"A", "%BP%"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_CLIENTE_POR_ID_CLIENTE(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.CLIENTE, I_CLIENTE.ESTADO, I_CLIENTE.ID);
        String[] selectionArgs = {"A", ID};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_CLIENTE_POR_CLIENTE(String CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s Like ?", TABLAS.CLIENTE, I_CLIENTE.ESTADO, I_CLIENTE.ID_CLIENTE);
        String[] selectionArgs = {"A", CLIENTE};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_CLIENTE_POR_ID(String ID_CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=?", TABLAS.CLIENTE, I_CLIENTE.ID_CLIENTE);
        String[] selectionArgs = {ID_CLIENTE};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_CLIENTE_POR_VALORES(String ID_CLIENTE, String NOMBRE_CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ?) AND %s=?", TABLAS.CLIENTE, I_CLIENTE.ID_CLIENTE, I_CLIENTE.NOMBRE_CLIENTE, I_CLIENTE.ESTADO);
        String[] selectionArgs = {ID_CLIENTE, NOMBRE_CLIENTE, "A"};
        return db.rawQuery(sql, selectionArgs);
    }


    public String INSERTAR_CLIENTE(Cliente CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_CLIENTE.GENERAR_ID_CLIENTE();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_CLIENTE.ID, ID);
        VALORES.put(I_CLIENTE.ID_CLIENTE, CLIENTE.ID_CLIENTE);
        VALORES.put(I_CLIENTE.NOMBRE_CLIENTE, CLIENTE.NOMBRE_CLIENTE);
        VALORES.put(I_CLIENTE.ESTADO, CLIENTE.ESTADO);
        VALORES.put(I_CLIENTE.USUARIO_INGRESA, CLIENTE.USUARIO_INGRESA);
        VALORES.put(I_CLIENTE.USUARIO_MODIFICA, CLIENTE.USUARIO_MODIFICA);
        VALORES.put(I_CLIENTE.FECHA_INGRESO, CLIENTE.FECHA_INGRESO);
        VALORES.put(I_CLIENTE.FECHA_MODIFICACION, CLIENTE.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.CLIENTE, null, VALORES)> 0 ? ID : null;
    }

    public String INSERTAR_CLIENTE_VACIO(Cliente CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        //String ID = I_CLIENTE.GENERAR_ID_CLIENTE();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_CLIENTE.ID, CLIENTE.ID);
        VALORES.put(I_CLIENTE.ID_CLIENTE, CLIENTE.ID_CLIENTE);
        VALORES.put(I_CLIENTE.NOMBRE_CLIENTE, CLIENTE.NOMBRE_CLIENTE);
        VALORES.put(I_CLIENTE.ESTADO, CLIENTE.ESTADO);
        VALORES.put(I_CLIENTE.USUARIO_INGRESA, CLIENTE.USUARIO_INGRESA);
        VALORES.put(I_CLIENTE.USUARIO_MODIFICA, CLIENTE.USUARIO_MODIFICA);
        VALORES.put(I_CLIENTE.FECHA_INGRESO, CLIENTE.FECHA_INGRESO);
        VALORES.put(I_CLIENTE.FECHA_MODIFICACION, CLIENTE.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.CLIENTE, null, VALORES)>= -1 ? CLIENTE.ID : null;
    }

    public boolean MODIFICAR_CLIENTE(Cliente CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_CLIENTE.ID_CLIENTE, CLIENTE.ID_CLIENTE);
        VALORES.put(I_CLIENTE.NOMBRE_CLIENTE, CLIENTE.NOMBRE_CLIENTE);
        VALORES.put(I_CLIENTE.ESTADO, CLIENTE.ESTADO);
        VALORES.put(I_CLIENTE.USUARIO_INGRESA, CLIENTE.USUARIO_INGRESA);
        VALORES.put(I_CLIENTE.USUARIO_MODIFICA, CLIENTE.USUARIO_MODIFICA);
        VALORES.put(I_CLIENTE.FECHA_INGRESO, CLIENTE.FECHA_INGRESO);
        VALORES.put(I_CLIENTE.FECHA_MODIFICACION, CLIENTE.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_CLIENTE.ID);
        String[] WHEREARGS = {CLIENTE.ID};
        int resultado = db.update(TABLAS.CLIENTE, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean DESACTIVAR_CLIENTE(Cliente CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_CLIENTE.ID_CLIENTE, CLIENTE.ID_CLIENTE);
        VALORES.put(I_CLIENTE.ESTADO, CLIENTE.ESTADO);
        VALORES.put(I_CLIENTE.USUARIO_INGRESA, CLIENTE.USUARIO_INGRESA);
        VALORES.put(I_CLIENTE.USUARIO_MODIFICA, CLIENTE.USUARIO_MODIFICA);
        VALORES.put(I_CLIENTE.FECHA_INGRESO, CLIENTE.FECHA_INGRESO);
        VALORES.put(I_CLIENTE.FECHA_MODIFICACION, CLIENTE.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_CLIENTE.ID);
        String[] WHEREARGS = {CLIENTE.ID};
        int resultado = db.update(TABLAS.CLIENTE, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_CLIENTE(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_CLIENTE.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.CLIENTE, whereClause, whereArgs);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
