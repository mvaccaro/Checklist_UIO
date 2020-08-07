package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.A71_Area;
import org.bp.teuno.checklist.Modelo.A71_Pasillo;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_A71_Area;
import org.bp.teuno.checklist.SQLite.IT_A71_Pasillo.I_A71_Pasillo;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_A71
 * sobre las entidades existentes.
 */
public final class Crud_A71 {

    private static BD BASEDATOS;

    private static Crud_A71 INSTANCIA = new Crud_A71();

    private Crud_A71() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static Crud_A71 OBTENER_INSTANCIA(Context CONTEXTO) {
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
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) =?", TABLAS.A71_PASILLO, I_A71_Pasillo.ID_TURNO, I_A71_Pasillo.FECHA_INGRESO);
        String[] selectionArgs = {TURNO, DIA};
        return db.rawQuery(sql, selectionArgs);
    }


    public long INSERTAR_A71_PASILLO(A71_Pasillo A71) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = I_A71_Pasillo.GENERAR_ID_A71_Pasillo();
        VALORES = new ContentValues();
        VALORES.put(I_A71_Pasillo.ID, ID);
        VALORES.put(I_A71_Pasillo.ID_PASILLO, A71.ID_PASILLO);
        VALORES.put(I_A71_Pasillo.LUMINOSIDAD_A, A71.LUMINOSIDAD_A);
        VALORES.put(I_A71_Pasillo.LUMINOSIDAD_B, A71.LUMINOSIDAD_B);
        VALORES.put(I_A71_Pasillo.RUIDO_A, A71.RUIDO_A);
        VALORES.put(I_A71_Pasillo.RUIDO_B, A71.RUIDO_B);
        VALORES.put(I_A71_Pasillo.HORA_INGRESO, A71.HORA_INGRESO);
        VALORES.put(I_A71_Pasillo.HORA_SALIDA, A71.HORA_SALIDA);
        VALORES.put(I_A71_Pasillo.ID_TURNO, A71.ID_TURNO);
        VALORES.put(I_A71_Pasillo.RONDA, A71.RONDA);
        VALORES.put(I_A71_Pasillo.ESTADO, A71.ESTADO);
        VALORES.put(I_A71_Pasillo.USUARIO_INGRESA, A71.USUARIO_INGRESA);
        VALORES.put(I_A71_Pasillo.USUARIO_MODIFICA, A71.USUARIO_MODIFICA);
        VALORES.put(I_A71_Pasillo.FECHA_INGRESO, A71.FECHA_INGRESO);
        VALORES.put(I_A71_Pasillo.FECHA_MODIFICACION, A71.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A71_PASILLO, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long INSERTAR_A71_AREA(A71_Area A71) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = IT_A71_Area.I_A71_Area.GENERAR_ID_A71_Area();
        VALORES = new ContentValues();
        VALORES.put(IT_A71_Area.I_A71_Area.ID, ID);
        VALORES.put(IT_A71_Area.I_A71_Area.ID_AREA, A71.ID_AREA);
        VALORES.put(IT_A71_Area.I_A71_Area.ID_EVENTO, A71.ID_EVENTO);
        VALORES.put(IT_A71_Area.I_A71_Area.DESCRIPCION, A71.DESCRIPCION);
        VALORES.put(IT_A71_Area.I_A71_Area.HORA_INGRESO, A71.HORA_INGRESO);
        VALORES.put(IT_A71_Area.I_A71_Area.HORA_SALIDA, A71.HORA_SALIDA);
        VALORES.put(IT_A71_Area.I_A71_Area.ID_TURNO, A71.ID_TURNO);
        VALORES.put(IT_A71_Area.I_A71_Area.RONDA, A71.RONDA);
        VALORES.put(IT_A71_Area.I_A71_Area.ESTADO, A71.ESTADO);
        VALORES.put(IT_A71_Area.I_A71_Area.USUARIO_INGRESA, A71.USUARIO_INGRESA);
        VALORES.put(IT_A71_Area.I_A71_Area.USUARIO_MODIFICA, A71.USUARIO_MODIFICA);
        VALORES.put(IT_A71_Area.I_A71_Area.FECHA_INGRESO, A71.FECHA_INGRESO);
        VALORES.put(IT_A71_Area.I_A71_Area.FECHA_MODIFICACION, A71.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A71_AREA, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
