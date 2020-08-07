package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.A98;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_A98.I_A98;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_A98
 * sobre las entidades existentes.
 */
public final class Crud_A98 {

    private static BD BASEDATOS;

    private static Crud_A98 INSTANCIA = new Crud_A98();

    private Crud_A98() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static Crud_A98 OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_RONDA(String TURNO, String DIA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) =?", TABLAS.A98, I_A98.ID_TURNO, I_A98.FECHA_INGRESO);
        String[] selectionArgs = {TURNO, DIA};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_A98(String FILA, String RACK) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=? AND %s=?", TABLAS.A98, I_A98.ID_FILA, I_A98.ID_RACK, I_A98.USUARIO_MODIFICA);
        String[] selectionArgs = {FILA, RACK, ""};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION(String FILA, String RACK) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=? AND %s=?", TABLAS.A98, I_A98.ID_FILA, I_A98.ID_RACK, I_A98.USUARIO_MODIFICA);
        String[] selectionArgs = {FILA, RACK, ""};
        return db.rawQuery(sql, selectionArgs);
    }

    public long INSERTAR_A98(A98 A98) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = I_A98.GENERAR_ID_A98();
        VALORES = new ContentValues();
        VALORES.put(I_A98.ID, ID);
        VALORES.put(I_A98.ID_FILA, A98.ID_FILA);
        VALORES.put(I_A98.ID_RACK, A98.ID_RACK);
        VALORES.put(I_A98.ID_AREA, A98.ID_AREA);
        VALORES.put(I_A98.INICIO, A98.INICIO);
        VALORES.put(I_A98.FIN, A98.FIN);
        VALORES.put(I_A98.OBSERVACIONES, A98.OBSERVACIONES);
        VALORES.put(I_A98.HORA_INGRESO, A98.HORA_INGRESO);
        VALORES.put(I_A98.HORA_SALIDA, A98.HORA_SALIDA);
        VALORES.put(I_A98.ID_TURNO, A98.ID_TURNO);
        VALORES.put(I_A98.RONDA, A98.RONDA);
        VALORES.put(I_A98.ESTADO, A98.ESTADO);
        VALORES.put(I_A98.USUARIO_INGRESA, A98.USUARIO_INGRESA);
        VALORES.put(I_A98.USUARIO_MODIFICA, A98.USUARIO_MODIFICA);
        VALORES.put(I_A98.FECHA_INGRESO, A98.FECHA_INGRESO);
        VALORES.put(I_A98.FECHA_MODIFICACION, A98.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A98, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long MODIFICAR_A98(A98 A98) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        VALORES = new ContentValues();
        VALORES.put(I_A98.FIN, A98.FIN);
        VALORES.put(I_A98.OBSERVACIONES, A98.OBSERVACIONES);
        VALORES.put(I_A98.HORA_SALIDA, A98.HORA_SALIDA);
        VALORES.put(I_A98.RONDA, A98.RONDA);
        VALORES.put(I_A98.ESTADO, A98.ESTADO);
        VALORES.put(I_A98.USUARIO_INGRESA, A98.USUARIO_INGRESA);
        VALORES.put(I_A98.USUARIO_MODIFICA, A98.USUARIO_MODIFICA);
        VALORES.put(I_A98.FECHA_INGRESO, A98.FECHA_INGRESO);
        VALORES.put(I_A98.FECHA_MODIFICACION, A98.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_A98.ID);
        String[] WHEREARGS = {A98.ID};
        return db.updateWithOnConflict(TABLAS.A98, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long ELIMINAR_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION(String FILA, String RACK) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=? AND %s=? AND %s=?", I_A98.ID_FILA, I_A98.ID_RACK, I_A98.USUARIO_MODIFICA);
        final String[] whereArgs = {FILA, RACK, ""};
        return db.delete(TABLAS.A98, whereClause, whereArgs);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
