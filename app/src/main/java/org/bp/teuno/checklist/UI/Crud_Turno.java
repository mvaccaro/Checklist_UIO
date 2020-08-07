package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Turno;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Turno.I_TURNO;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Turno
 * sobre las entidades existentes.
 */
public final class Crud_Turno {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Turno INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Turno();

    private Crud_Turno() {
        /*
        * CONSTRUCTOR VACIO
        * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Turno OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
    * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
    * */

    public Cursor CONSULTA_GENERAL_TURNO() {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s", TABLAS.TURNO);
        return db.rawQuery(sql, null);
    }

    public Cursor CONSULTA_GENERAL_TURNO_POR_HORA(String HORA_ACTUAL) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE ? BETWEEN %s=? AND %s=?", TABLAS.TURNO, I_TURNO.HORA_INICIO, I_TURNO.HORA_FIN);
        String[] selectionArgs = {HORA_ACTUAL};
        return db.rawQuery(sql, selectionArgs);
    }

    public long INSERTAR_TURNO(Turno TURNO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_TURNO.GENERAR_ID_TURNO();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TURNO.ID, ID);
        VALORES.put(I_TURNO.TURNO, TURNO.TURNO);
        VALORES.put(I_TURNO.HORA_INICIO, TURNO.HORA_INICIO);
        VALORES.put(I_TURNO.HORA_FIN, TURNO.HORA_FIN);
        VALORES.put(I_TURNO.ESTADO, TURNO.ESTADO);
        VALORES.put(I_TURNO.USUARIO_INGRESA, TURNO.USUARIO_INGRESA);
        VALORES.put(I_TURNO.USUARIO_MODIFICA, TURNO.USUARIO_MODIFICA);
        VALORES.put(I_TURNO.FECHA_INGRESO, TURNO.FECHA_INGRESO);
        VALORES.put(I_TURNO.FECHA_MODIFICACION, TURNO.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.TURNO, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public boolean MODIFICAR_TURNO(Turno TURNO) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_TURNO.TURNO, TURNO.TURNO);
        VALORES.put(I_TURNO.HORA_INICIO, TURNO.HORA_INICIO);
        VALORES.put(I_TURNO.HORA_FIN, TURNO.HORA_FIN);
        VALORES.put(I_TURNO.ESTADO, TURNO.ESTADO);
        VALORES.put(I_TURNO.USUARIO_INGRESA, TURNO.USUARIO_INGRESA);
        VALORES.put(I_TURNO.USUARIO_MODIFICA, TURNO.USUARIO_MODIFICA);
        VALORES.put(I_TURNO.FECHA_INGRESO, TURNO.FECHA_INGRESO);
        VALORES.put(I_TURNO.FECHA_MODIFICACION, TURNO.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_TURNO.ID);
        String[] WHEREARGS = {TURNO.ID};
        int resultado = db.update(TABLAS.TURNO, VALORES, WHERECLAUSE, WHEREARGS);
        return resultado > 0;
    }

    public boolean ELIMINAR_TURNO_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_TURNO.ID);
        final String[] whereArgs = {ID};
        int resultado = db.delete(TABLAS.TURNO, whereClause, whereArgs);
        return resultado > 0;
    }

    public long ELIMINAR_TURNO() {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        //String whereClause = String.format("%s=?", I_TURNO.ID);
        //final String[] whereArgs = {ID};
        return db.delete(TABLAS.TURNO, null, null);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
