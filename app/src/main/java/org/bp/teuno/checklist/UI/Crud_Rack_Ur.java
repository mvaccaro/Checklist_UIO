package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.Rack_Ur;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_Rack_Ur.I_RACK_UR;

;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_Rack_Ur
 * sobre las entidades existentes.
 */
public final class Crud_Rack_Ur {

    private static BD BASEDATOS;

    private static org.bp.teuno.checklist.UI.Crud_Rack_Ur INSTANCIA = new org.bp.teuno.checklist.UI.Crud_Rack_Ur();

    private Crud_Rack_Ur() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static org.bp.teuno.checklist.UI.Crud_Rack_Ur OBTENER_INSTANCIA(Context CONTEXTO) {
        if (BASEDATOS == null) {
            BASEDATOS = new BD(CONTEXTO);
        }
        return INSTANCIA;
    }

    /*
     * METODOS CREADOS PARA REALIZAR CONSULTAS A LA BD
     * */

    public Cursor CONSULTA_GENERAL_RACK_UR_POR_ID(String ID) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.RACK_UR, I_RACK_UR.ID, I_RACK_UR.ESTADO);
        String[] selectionArgs = {ID, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public Cursor CONSULTA_GENERAL_RACK_UR_POR_ID_RACK(String ID_RACK) {
        SQLiteDatabase db = BASEDATOS.getReadableDatabase();
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND %s=?", TABLAS.RACK_UR, I_RACK_UR.ID_RACK, I_RACK_UR.ESTADO);
        String[] selectionArgs = {ID_RACK, "A"};
        return db.rawQuery(sql, selectionArgs);
    }

    public long INSERTAR_RACK_UR(Rack_Ur RACK_UR) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        // GENERAR PK
        String ID = I_RACK_UR.GENERAR_ID_RACK_UR();
        ContentValues VALORES = new ContentValues();
        VALORES.put(I_RACK_UR.ID, ID);
        VALORES.put(I_RACK_UR.ID_RACK, RACK_UR.ID_RACK);
        VALORES.put(I_RACK_UR.UR, RACK_UR.UR);
        VALORES.put(I_RACK_UR.ESTADO, RACK_UR.ESTADO);
        VALORES.put(I_RACK_UR.USUARIO_INGRESA, RACK_UR.USUARIO_INGRESA);
        VALORES.put(I_RACK_UR.USUARIO_MODIFICA, RACK_UR.USUARIO_MODIFICA);
        VALORES.put(I_RACK_UR.FECHA_INGRESO, RACK_UR.FECHA_INGRESO);
        VALORES.put(I_RACK_UR.FECHA_MODIFICACION, RACK_UR.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.RACK_UR, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long ELIMINAR_RACK_UR(String ID_RACK) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        String whereClause = String.format("%s=?", I_RACK_UR.ID_RACK);
        final String[] whereArgs = {ID_RACK};
        return db.delete(TABLAS.RACK_UR, whereClause, whereArgs);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
