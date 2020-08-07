package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Operador;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Operador.I_OPERADOR;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Operador
 * sobre las entidades existentes.
 */
public final class Crud_Operador {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Operador INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Operador();

    private Crud_Operador() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Operador OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_OPERADOR() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", TABLAS.OPERADOR);
        return db.rawQuery(sql, null);
    }

    public Cursor CONSULTA_GENERAL_OPERADOR_POR_ID(String ID_OPERADOR) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.OPERADOR, I_OPERADOR.ID_OPERADOR, I_OPERADOR.ESTADO);
        String[] selectionArgs = {ID_OPERADOR, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_OPERADOR_POR_VALORES(String ID_OPERADOR, String PRIMER_NOMBRE, String PRIMER_APELLIDO) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s=? OR %s like ? OR %s like ?) AND %s=?", TABLAS.OPERADOR, I_OPERADOR.ID_OPERADOR, I_OPERADOR.PRIMER_NOMBRE, I_OPERADOR.PRIMER_APELLIDO, I_OPERADOR.ESTADO);
        String[] selectionArgs = {ID_OPERADOR, PRIMER_NOMBRE, PRIMER_APELLIDO, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_OPERADOR_POR_NOT_IN_ID(String ID, String ID_OPERADOR) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? and %s NOT IN (?)", TABLAS.OPERADOR, I_OPERADOR.ID, I_OPERADOR.ID_OPERADOR);
        String[] selectionArgs = {ID, ID_OPERADOR};
        return db.rawQuery(sql, selectionArgs);
    }

    public String INSERTAR_OPERADOR(Operador OPERADOR) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_OPERADOR.GENERAR_ID_OPERADOR();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_OPERADOR.ID, ID);
        VALORES.put(I_OPERADOR.ID_OPERADOR, OPERADOR.ID_OPERADOR);
        VALORES.put(I_OPERADOR.PRIMER_NOMBRE, OPERADOR.PRIMER_NOMBRE);
        VALORES.put(I_OPERADOR.SEGUNDO_NOMBRE, OPERADOR.SEGUNDO_NOMBRE);
        VALORES.put(I_OPERADOR.PRIMER_APELLIDO, OPERADOR.PRIMER_APELLIDO);
        VALORES.put(I_OPERADOR.SEGUNDO_APELLIDO, OPERADOR.SEGUNDO_APELLIDO);
        VALORES.put(I_OPERADOR.ID_GRUPO, OPERADOR.ID_GRUPO);
        VALORES.put(I_OPERADOR.ESTADO, OPERADOR.ESTADO);
        VALORES.put(I_OPERADOR.USUARIO_INGRESA, OPERADOR.USUARIO_INGRESA);
        VALORES.put(I_OPERADOR.USUARIO_MODIFICA, OPERADOR.USUARIO_MODIFICA);
        VALORES.put(I_OPERADOR.FECHA_INGRESO, OPERADOR.FECHA_INGRESO);
        VALORES.put(I_OPERADOR.FECHA_MODIFICACION, OPERADOR.FECHA_MODIFICACION);
        return db.insertOrThrow(TABLAS.OPERADOR, null, VALORES)> 0 ? ID : null;
    }

    public boolean MODIFICAR_OPERADOR(Operador OPERADOR) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_OPERADOR.ID_OPERADOR, OPERADOR.ID_OPERADOR);
        VALORES.put(I_OPERADOR.PRIMER_NOMBRE, OPERADOR.PRIMER_NOMBRE);
        VALORES.put(I_OPERADOR.SEGUNDO_NOMBRE, OPERADOR.SEGUNDO_NOMBRE);
        VALORES.put(I_OPERADOR.PRIMER_APELLIDO, OPERADOR.PRIMER_APELLIDO);
        VALORES.put(I_OPERADOR.SEGUNDO_APELLIDO, OPERADOR.SEGUNDO_APELLIDO);
        VALORES.put(I_OPERADOR.ID_GRUPO, OPERADOR.ID_GRUPO);
        VALORES.put(I_OPERADOR.ESTADO, OPERADOR.ESTADO);
        VALORES.put(I_OPERADOR.USUARIO_INGRESA, OPERADOR.USUARIO_INGRESA);
        VALORES.put(I_OPERADOR.USUARIO_MODIFICA, OPERADOR.USUARIO_MODIFICA);
        VALORES.put(I_OPERADOR.FECHA_INGRESO, OPERADOR.FECHA_INGRESO);
        VALORES.put(I_OPERADOR.FECHA_MODIFICACION, OPERADOR.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_OPERADOR.ID);
        String[] WHEREARGS = {OPERADOR.ID};
        int resultado = db.update(TABLAS.OPERADOR, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean DESACTIVAR_OPERADOR(Operador OPERADOR) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_OPERADOR.ID_OPERADOR, OPERADOR.ID_OPERADOR);
        VALORES.put(I_OPERADOR.ESTADO, OPERADOR.ESTADO);
        VALORES.put(I_OPERADOR.USUARIO_INGRESA, OPERADOR.USUARIO_INGRESA);
        VALORES.put(I_OPERADOR.USUARIO_MODIFICA, OPERADOR.USUARIO_MODIFICA);
        VALORES.put(I_OPERADOR.FECHA_INGRESO, OPERADOR.FECHA_INGRESO);
        VALORES.put(I_OPERADOR.FECHA_MODIFICACION, OPERADOR.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_OPERADOR.ID);
        String[] WHEREARGS = {OPERADOR.ID};
        int resultado = db.update(TABLAS.OPERADOR, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_OPERADOR(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_OPERADOR.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.OPERADOR, whereClause, whereArgs);
        return resultado > 0;
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
