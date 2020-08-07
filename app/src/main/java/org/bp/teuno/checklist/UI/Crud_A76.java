package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.A76;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_A76.I_A76;
import org.bp.teuno.checklist.SQLite.IT_Fila;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_A76
 * sobre las entidades existentes.
 */
public final class Crud_A76 {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_A76 INSTANCIA = new org.bp.teuno.checklist.UI.Crud_A76();

    private Crud_A76() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static org.bp.teuno.checklist.UI.Crud_A76 OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_A76_POR_FILA(String ID_FILA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.A76, I_A76.ID_FILA, I_A76.ESTADO);
        String[] selectionArgs = {ID_FILA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_RONDA(String TURNO, String DIA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) =?", TABLAS.A76, I_A76.ID_TURNO, I_A76.FECHA_INGRESO);
        String[] selectionArgs = {TURNO, DIA};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor EXISTEN_DATOS_SEMANA_ANTERIOR(String FILA, String DIA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) != ? AND %s=?", TABLAS.A76, I_A76.ID_FILA, I_A76.FECHA_INGRESO, I_A76.ESTADO);
        String[] selectionArgs = {FILA, DIA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor EXISTEN_DATOS_SEMANA_ACTUAL(String FILA, String DIA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) = ? AND %s=?", TABLAS.A76, I_A76.ID_FILA, I_A76.FECHA_INGRESO, I_A76.ESTADO);
        String[] selectionArgs = {FILA, DIA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor EXISTEN_FILAS_PENDIENTES_DE_REGISTRAR(String DIA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s NOT IN (SELECT %s FROM %s WHERE substr(%s, 0, 11) = ? AND %s=?) AND %s=? AND %s Like ?", TABLAS.FILA, IT_Fila.I_FILA.ID, I_A76.ID_FILA, TABLAS.A76, I_A76.FECHA_INGRESO, I_A76.ESTADO, IT_Fila.I_FILA.ESTADO, IT_Fila.I_FILA.ID_FILA);
        String[] selectionArgs = {DIA, "A", "A", "%BP%"};
        return db.rawQuery(sql, selectionArgs);
    }


    public long INSERTAR_A76(A76 A76) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = I_A76.GENERAR_ID_A76();
        VALORES = new ContentValues();
        VALORES.put(I_A76.ID, ID);
        VALORES.put(I_A76.ID_FILA, A76.ID_FILA);
        VALORES.put(I_A76.ID_RACK, A76.ID_RACK);
        VALORES.put(I_A76.ID_CANTIDAD_UR, A76.ID_CANTIDAD_UR);
        VALORES.put(I_A76.UR_OCUPADAS, A76.UR_OCUPADAS);
        VALORES.put(I_A76.UR_DISPONIBLES, A76.UR_DISPONIBLES);
        VALORES.put(I_A76.HORA_INGRESO, A76.HORA_INGRESO);
        VALORES.put(I_A76.HORA_SALIDA, A76.HORA_SALIDA);
        VALORES.put(I_A76.ID_TURNO, A76.ID_TURNO);
        VALORES.put(I_A76.RONDA, A76.RONDA);
        VALORES.put(I_A76.ESTADO, A76.ESTADO);
        VALORES.put(I_A76.USUARIO_INGRESA, A76.USUARIO_INGRESA);
        VALORES.put(I_A76.USUARIO_MODIFICA, A76.USUARIO_MODIFICA);
        VALORES.put(I_A76.FECHA_INGRESO, A76.FECHA_INGRESO);
        VALORES.put(I_A76.FECHA_MODIFICACION, A76.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A76, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long ELIMINAR_DATOS_INGRESADOS(String FILA, String DIA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=? AND substr(%s, 0, 11) = ? AND %s=?", I_A76.ID_FILA, I_A76.FECHA_INGRESO, I_A76.ESTADO);
        final String[] whereArgs = {FILA, DIA, "A"};
        return db.delete(TABLAS.A76, whereClause, whereArgs);
    }

    public long DESACTIVAR_DATOS_SEMANA_ANTERIOR(String FILA, String DIA) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_A76.ESTADO, "I");
        String WHERECLAUSE = String.format("%s=? AND substr(%s, 0, 11) != ? AND %s=?", I_A76.ID_FILA, I_A76.FECHA_INGRESO, I_A76.ESTADO);
        String[] WHEREARGS = {FILA, DIA, "A"};
        return db.updateWithOnConflict(TABLAS.A76, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
