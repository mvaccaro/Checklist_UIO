package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.A34_Busway;
import org.bp.teuno.checklist.Modelo.A34_Tanque_Ecaro;
import org.bp.teuno.checklist.Modelo.A34_Uma;
import org.bp.teuno.checklist.Modelo.A34_Ups;
import org.bp.teuno.checklist.Modelo.A34_Xcom;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_A34_Busway;
import org.bp.teuno.checklist.SQLite.IT_A34_Tanque_Ecaro;
import org.bp.teuno.checklist.SQLite.IT_A34_Uma.I_A34_Uma;
import org.bp.teuno.checklist.SQLite.IT_A34_Ups;
import org.bp.teuno.checklist.SQLite.IT_A34_Xcom;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_A34
 * sobre las entidades existentes.
 */
public final class Crud_A34 {

    private static BD BASEDATOS;

    private static Crud_A34 INSTANCIA = new Crud_A34();

    private Crud_A34() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static Crud_A34 OBTENER_INSTANCIA(Context CONTEXTO) {
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
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) =?", TABLAS.A34_UMA, I_A34_Uma.ID_TURNO, I_A34_Uma.FECHA_INGRESO);
        String[] selectionArgs = {TURNO, DIA};
        return db.rawQuery(sql, selectionArgs);
    }


    public long INSERTAR_A34_UMA(A34_Uma A34) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = I_A34_Uma.GENERAR_ID_A34_Uma();
        VALORES = new ContentValues();
        VALORES.put(I_A34_Uma.ID, ID);
        VALORES.put(I_A34_Uma.ID_UMA, A34.ID_UMA);
        VALORES.put(I_A34_Uma.MEDICION, A34.MEDICION);
        VALORES.put(I_A34_Uma.TEMPERATURA, A34.TEMPERATURA);
        VALORES.put(I_A34_Uma.HUMEDAD, A34.HUMEDAD);
        VALORES.put(I_A34_Uma.ALERTA, A34.ALERTA);
        VALORES.put(I_A34_Uma.HORA_INGRESO, A34.HORA_INGRESO);
        VALORES.put(I_A34_Uma.HORA_SALIDA, A34.HORA_SALIDA);
        VALORES.put(I_A34_Uma.ID_TURNO, A34.ID_TURNO);
        VALORES.put(I_A34_Uma.RONDA, A34.RONDA);
        VALORES.put(I_A34_Uma.ESTADO, A34.ESTADO);
        VALORES.put(I_A34_Uma.USUARIO_INGRESA, A34.USUARIO_INGRESA);
        VALORES.put(I_A34_Uma.USUARIO_MODIFICA, A34.USUARIO_MODIFICA);
        VALORES.put(I_A34_Uma.FECHA_INGRESO, A34.FECHA_INGRESO);
        VALORES.put(I_A34_Uma.FECHA_MODIFICACION, A34.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A34_UMA, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long INSERTAR_A34_UPS(A34_Ups A34) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = IT_A34_Ups.I_A34_Ups.GENERAR_ID_A34_Ups();
        VALORES = new ContentValues();
        VALORES.put(IT_A34_Ups.I_A34_Ups.ID, ID);
        VALORES.put(IT_A34_Ups.I_A34_Ups.ID_UPS, A34.ID_UPS);
        VALORES.put(IT_A34_Ups.I_A34_Ups.MEDICION, A34.MEDICION);
        VALORES.put(IT_A34_Ups.I_A34_Ups.L1, A34.L1);
        VALORES.put(IT_A34_Ups.I_A34_Ups.L2, A34.L2);
        VALORES.put(IT_A34_Ups.I_A34_Ups.L3, A34.L3);
        VALORES.put(IT_A34_Ups.I_A34_Ups.BATERIA, A34.BATERIA);
        VALORES.put(IT_A34_Ups.I_A34_Ups.ALERTA, A34.ALERTA);
        VALORES.put(IT_A34_Ups.I_A34_Ups.HORA_INGRESO, A34.HORA_INGRESO);
        VALORES.put(IT_A34_Ups.I_A34_Ups.HORA_SALIDA, A34.HORA_SALIDA);
        VALORES.put(IT_A34_Ups.I_A34_Ups.ID_TURNO, A34.ID_TURNO);
        VALORES.put(IT_A34_Ups.I_A34_Ups.RONDA, A34.RONDA);
        VALORES.put(IT_A34_Ups.I_A34_Ups.ESTADO, A34.ESTADO);
        VALORES.put(IT_A34_Ups.I_A34_Ups.USUARIO_INGRESA, A34.USUARIO_INGRESA);
        VALORES.put(IT_A34_Ups.I_A34_Ups.USUARIO_MODIFICA, A34.USUARIO_MODIFICA);
        VALORES.put(IT_A34_Ups.I_A34_Ups.FECHA_INGRESO, A34.FECHA_INGRESO);
        VALORES.put(IT_A34_Ups.I_A34_Ups.FECHA_MODIFICACION, A34.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A34_UPS, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long INSERTAR_A34_BUSWAY(A34_Busway A34) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = IT_A34_Busway.I_A34_Busway.GENERAR_ID_A34_Busway();
        VALORES = new ContentValues();
        VALORES.put(IT_A34_Busway.I_A34_Busway.ID, ID);
        VALORES.put(IT_A34_Busway.I_A34_Busway.ID_BUSWAY, A34.ID_BUSWAY);
        VALORES.put(IT_A34_Busway.I_A34_Busway.VOLTAJE, A34.VOLTAJE);
        VALORES.put(IT_A34_Busway.I_A34_Busway.CORRIENTE, A34.CORRIENTE);
        VALORES.put(IT_A34_Busway.I_A34_Busway.POTENCIA, A34.POTENCIA);
        VALORES.put(IT_A34_Busway.I_A34_Busway.PDU, A34.PDU);
        VALORES.put(IT_A34_Busway.I_A34_Busway.HORA_INGRESO, A34.HORA_INGRESO);
        VALORES.put(IT_A34_Busway.I_A34_Busway.HORA_SALIDA, A34.HORA_SALIDA);
        VALORES.put(IT_A34_Busway.I_A34_Busway.ID_TURNO, A34.ID_TURNO);
        VALORES.put(IT_A34_Busway.I_A34_Busway.RONDA, A34.RONDA);
        VALORES.put(IT_A34_Busway.I_A34_Busway.ESTADO, A34.ESTADO);
        VALORES.put(IT_A34_Busway.I_A34_Busway.USUARIO_INGRESA, A34.USUARIO_INGRESA);
        VALORES.put(IT_A34_Busway.I_A34_Busway.USUARIO_MODIFICA, A34.USUARIO_MODIFICA);
        VALORES.put(IT_A34_Busway.I_A34_Busway.FECHA_INGRESO, A34.FECHA_INGRESO);
        VALORES.put(IT_A34_Busway.I_A34_Busway.FECHA_MODIFICACION, A34.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A34_BUSWAY, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long INSERTAR_A34_TANQUE_ECARO(A34_Tanque_Ecaro A34) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.GENERAR_ID_A34_Tanque_Ecaro();
        VALORES = new ContentValues();
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.ID, ID);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.ID_TANQUE_ECARO, A34.ID_TANQUE_ECARO);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.COLOR, A34.COLOR);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.HORA_INGRESO, A34.HORA_INGRESO);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.HORA_SALIDA, A34.HORA_SALIDA);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.ID_TURNO, A34.ID_TURNO);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.RONDA, A34.RONDA);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.ESTADO, A34.ESTADO);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.USUARIO_INGRESA, A34.USUARIO_INGRESA);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.USUARIO_MODIFICA, A34.USUARIO_MODIFICA);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.FECHA_INGRESO, A34.FECHA_INGRESO);
        VALORES.put(IT_A34_Tanque_Ecaro.I_A34_Tanque_Ecaro.FECHA_MODIFICACION, A34.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A34_TANQUE_ECARO, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long INSERTAR_A34_XCOM(A34_Xcom A34) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = IT_A34_Xcom.I_A34_Xcom.GENERAR_ID_A34_Xcom();
        VALORES = new ContentValues();
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.ID, ID);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.ID_XCOM, A34.ID_XCOM);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.ALERTA, A34.ALERTA);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.VOLTAJE_RACK, A34.VOLTAJE_RACK);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.A_C, A34.A_C);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.HORA_INGRESO, A34.HORA_INGRESO);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.HORA_SALIDA, A34.HORA_SALIDA);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.ID_TURNO, A34.ID_TURNO);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.RONDA, A34.RONDA);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.ESTADO, A34.ESTADO);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.USUARIO_INGRESA, A34.USUARIO_INGRESA);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.USUARIO_MODIFICA, A34.USUARIO_MODIFICA);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.FECHA_INGRESO, A34.FECHA_INGRESO);
        VALORES.put(IT_A34_Xcom.I_A34_Xcom.FECHA_MODIFICACION, A34.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A34_XCOM, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
