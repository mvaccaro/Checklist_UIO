package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Rack;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Rack.I_RACK;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Rack
 * sobre las entidades existentes.
 */
public final class Crud_Rack {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Rack INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Rack();

    private Crud_Rack() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Rack OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_POR_ID_FILA(String ID_FILA) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.RACK, I_RACK.ID_FILA, I_RACK.ESTADO);
        String[] selectionArgs = {ID_FILA, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_POR_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.RACK, I_RACK.ID, I_RACK.ESTADO);
        String[] selectionArgs = {ID, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_POR_RACK(String NOMBRE_RACK) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.RACK, I_RACK.NOMBRE_RACK, I_RACK.ESTADO);
        String[] selectionArgs = {NOMBRE_RACK, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_RACK_POR_VALORES(String NOMBRE_RACK, String FILA, String CLIENTE, String UR) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE (%s LIKE ? OR %s=? OR %s=? OR %s=?) AND %s=?", TABLAS.RACK, I_RACK.NOMBRE_RACK, I_RACK.ID_FILA, I_RACK.ID_CLIENTE, I_RACK.ID_CANTIDAD_UR, I_RACK.ESTADO);
        String[] selectionArgs = {NOMBRE_RACK, FILA, CLIENTE, UR, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public long INSERTAR_RACK(Rack RACK) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_RACK.GENERAR_ID_RACK();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_RACK.ID, ID);
        VALORES.put(I_RACK.NOMBRE_RACK, RACK.NOMBRE_RACK);
        VALORES.put(I_RACK.ID_FILA, RACK.ID_FILA);
        VALORES.put(I_RACK.ID_CANTIDAD_UR, RACK.ID_UR);
        VALORES.put(I_RACK.ID_CLIENTE, RACK.ID_CLIENTE);
        VALORES.put(I_RACK.MARCA, RACK.MARCA);
        VALORES.put(I_RACK.MODELO, RACK.MODELO);
        VALORES.put(I_RACK.SERIE, RACK.SERIE);
        VALORES.put(I_RACK.CODIGO_BANCO, RACK.CODIGO_BANCO);
        VALORES.put(I_RACK.ESTADO, RACK.ESTADO);
        VALORES.put(I_RACK.USUARIO_INGRESA, RACK.USUARIO_INGRESA);
        VALORES.put(I_RACK.USUARIO_MODIFICA, RACK.USUARIO_MODIFICA);
        VALORES.put(I_RACK.FECHA_INGRESO, RACK.FECHA_INGRESO);
        VALORES.put(I_RACK.FECHA_MODIFICACION, RACK.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.RACK, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long MODIFICAR_RACK(Rack RACK) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_RACK.NOMBRE_RACK, RACK.NOMBRE_RACK);
        VALORES.put(I_RACK.ID_FILA, RACK.ID_FILA);
        VALORES.put(I_RACK.ID_CANTIDAD_UR, RACK.ID_UR);
        VALORES.put(I_RACK.ID_CLIENTE, RACK.ID_CLIENTE);
        VALORES.put(I_RACK.MARCA, RACK.MARCA);
        VALORES.put(I_RACK.MODELO, RACK.MODELO);
        VALORES.put(I_RACK.SERIE, RACK.SERIE);
        VALORES.put(I_RACK.CODIGO_BANCO, RACK.CODIGO_BANCO);
        VALORES.put(I_RACK.ESTADO, RACK.ESTADO);
        VALORES.put(I_RACK.USUARIO_INGRESA, RACK.USUARIO_INGRESA);
        VALORES.put(I_RACK.USUARIO_MODIFICA, RACK.USUARIO_MODIFICA);
        VALORES.put(I_RACK.FECHA_INGRESO, RACK.FECHA_INGRESO);
        VALORES.put(I_RACK.FECHA_MODIFICACION, RACK.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_RACK.ID);
        String[] WHEREARGS = {RACK.ID};
        return db.updateWithOnConflict(TABLAS.RACK, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long DESACTIVAR_RACK(Rack RACK) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_RACK.ESTADO, RACK.ESTADO);
        VALORES.put(I_RACK.USUARIO_INGRESA, RACK.USUARIO_INGRESA);
        VALORES.put(I_RACK.USUARIO_MODIFICA, RACK.USUARIO_MODIFICA);
        VALORES.put(I_RACK.FECHA_INGRESO, RACK.FECHA_INGRESO);
        VALORES.put(I_RACK.FECHA_MODIFICACION, RACK.FECHA_MODIFICACION);
        String WHERECLAUSE = String.format("%s=?", I_RACK.ID);
        String[] WHEREARGS = {RACK.ID};
        return db.updateWithOnConflict(TABLAS.RACK, VALORES, WHERECLAUSE, WHEREARGS, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
