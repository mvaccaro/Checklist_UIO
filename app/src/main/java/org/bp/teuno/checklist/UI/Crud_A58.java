package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.A58_Chiller;
import org.bp.teuno.checklist.Modelo.A58_Pasillo;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_A58_Chiller.I_A58_Chiller;
import org.bp.teuno.checklist.SQLite.IT_A58_Pasillo.I_A58_Pasillo;
import org.bp.teuno.checklist.SQLite.IT_A58_Pasillo;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_A58
 * sobre las entidades existentes.
 */
public final class Crud_A58 {

    private static BD BASEDATOS;

    private static Crud_A58 INSTANCIA = new Crud_A58();

    private Crud_A58() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static Crud_A58 OBTENER_INSTANCIA(Context CONTEXTO) {
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
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) =?", TABLAS.A58_PASILLO, I_A58_Pasillo.ID_TURNO, I_A58_Pasillo.FECHA_INGRESO);
        String[] selectionArgs = {TURNO, DIA};
        return db.rawQuery(sql, selectionArgs);
    }

    public long INSERTAR_A58_PASILLO(A58_Pasillo A58) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = IT_A58_Pasillo.I_A58_Pasillo.GENERAR_ID_A58_Pasillo();
        VALORES = new ContentValues();
        VALORES.put(I_A58_Pasillo.ID, ID);
        VALORES.put(I_A58_Pasillo.ID_PASILLO, A58.ID_PASILLO);
        VALORES.put(I_A58_Pasillo.TEMPERATURA, A58.TEMPERATURA);
        VALORES.put(I_A58_Pasillo.TEMP_SENSOR, A58.TEMP_SENSOR);
        VALORES.put(I_A58_Pasillo.HUMEDAD, A58.HUMEDAD);
        VALORES.put(I_A58_Pasillo.HORA_INGRESO, A58.HORA_INGRESO);
        VALORES.put(I_A58_Pasillo.HORA_SALIDA, A58.HORA_SALIDA);
        VALORES.put(I_A58_Pasillo.ID_TURNO, A58.ID_TURNO);
        VALORES.put(I_A58_Pasillo.RONDA, A58.RONDA);
        VALORES.put(I_A58_Pasillo.ESTADO, A58.ESTADO);
        VALORES.put(I_A58_Pasillo.USUARIO_INGRESA, A58.USUARIO_INGRESA);
        VALORES.put(I_A58_Pasillo.USUARIO_MODIFICA, A58.USUARIO_MODIFICA);
        VALORES.put(I_A58_Pasillo.FECHA_INGRESO, A58.FECHA_INGRESO);
        VALORES.put(I_A58_Pasillo.FECHA_MODIFICACION, A58.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A58_PASILLO, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long INSERTAR_A58_CHILLER(A58_Chiller A58) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = I_A58_Chiller.GENERAR_ID_A58_Chiller();
        VALORES = new ContentValues();
        VALORES.put(I_A58_Chiller.ID, ID);
        VALORES.put(I_A58_Chiller.ID_CHILLER, A58.ID_CHILLER);
        VALORES.put(I_A58_Chiller.TEMP_SUMINISTRO, A58.TEMP_SUMINISTRO);
        VALORES.put(I_A58_Chiller.TEMP_RETORNO, A58.TEMP_RETORNO);
        VALORES.put(I_A58_Chiller.HORA_INGRESO, A58.HORA_INGRESO);
        VALORES.put(I_A58_Chiller.HORA_SALIDA, A58.HORA_SALIDA);
        VALORES.put(I_A58_Chiller.ID_TURNO, A58.ID_TURNO);
        VALORES.put(I_A58_Chiller.RONDA, A58.RONDA);
        VALORES.put(I_A58_Chiller.ESTADO, A58.ESTADO);
        VALORES.put(I_A58_Chiller.USUARIO_INGRESA, A58.USUARIO_INGRESA);
        VALORES.put(I_A58_Chiller.USUARIO_MODIFICA, A58.USUARIO_MODIFICA);
        VALORES.put(I_A58_Chiller.FECHA_INGRESO, A58.FECHA_INGRESO);
        VALORES.put(I_A58_Chiller.FECHA_MODIFICACION, A58.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A58_CHILLER, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
