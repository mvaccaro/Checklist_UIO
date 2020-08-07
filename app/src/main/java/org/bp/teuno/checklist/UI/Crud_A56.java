package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.A56;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_A56.I_A56;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_A56
 * sobre las entidades existentes.
 */
public final class Crud_A56 {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_A56 INSTANCIA = new org.bp.teuno.checklist.UI.Crud_A56();

    private Crud_A56() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static org.bp.teuno.checklist.UI.Crud_A56 OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_RONDA_BP(String TURNO, String DIA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) =? AND %s LIKE ?", TABLAS.A56, I_A56.ID_TURNO, I_A56.FECHA_INGRESO, I_A56.ID);
        String[] selectionArgs = {TURNO, DIA, "%A56BP%"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_RONDA_DN(String TURNO, String DIA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) =? AND %s LIKE ?", TABLAS.A56, I_A56.ID_TURNO, I_A56.FECHA_INGRESO, I_A56.ID);
        String[] selectionArgs = {TURNO, DIA, "%A56DN%"};
        return db.rawQuery(sql, selectionArgs);
    }


    public long INSERTAR_A56(A56 A56, String CLIENTE) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = I_A56.GENERAR_ID_A56(CLIENTE);
        VALORES = new ContentValues();
        VALORES.put(I_A56.ID, ID);
        VALORES.put(I_A56.ID_ALERTA, A56.ID_ALERTA);
        VALORES.put(I_A56.A, A56.A);
        VALORES.put(I_A56.C, A56.C);
        VALORES.put(I_A56.PDUA, A56.PDUA);
        VALORES.put(I_A56.PDUB, A56.PDUB);
        VALORES.put(I_A56.HORA_INGRESO, A56.HORA_INGRESO);
        VALORES.put(I_A56.HORA_SALIDA, A56.HORA_SALIDA);
        VALORES.put(I_A56.ID_TURNO, A56.ID_TURNO);
        VALORES.put(I_A56.RONDA, A56.RONDA);
        VALORES.put(I_A56.ESTADO, A56.ESTADO);
        VALORES.put(I_A56.USUARIO_INGRESA, A56.USUARIO_INGRESA);
        VALORES.put(I_A56.USUARIO_MODIFICA, A56.USUARIO_MODIFICA);
        VALORES.put(I_A56.FECHA_INGRESO, A56.FECHA_INGRESO);
        VALORES.put(I_A56.FECHA_MODIFICACION, A56.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A56, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
