package org.bp.teuno.checklist.UI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.bp.teuno.checklist.Modelo.A77_Aire;
import org.bp.teuno.checklist.Modelo.A77_Area;
import org.bp.teuno.checklist.Modelo.A77_Pasillo;
import org.bp.teuno.checklist.SQLite.BD;
import org.bp.teuno.checklist.SQLite.BD.TABLAS;
import org.bp.teuno.checklist.SQLite.IT_A77_Aire;
import org.bp.teuno.checklist.SQLite.IT_A77_Area;
import org.bp.teuno.checklist.SQLite.IT_A77_Pasillo.I_A77_Pasillo;

/**
 * Clase auxiliar que implementa a {@link BD para llevar a cabo el Crud_A77
 * sobre las entidades existentes.
 */
public final class Crud_A77 {

    private static BD BASEDATOS;

    private static Crud_A77 INSTANCIA = new Crud_A77();

    private Crud_A77() {
        /*
         * CONSTRUCTOR VACIO
         * */
    }

    public static Crud_A77 OBTENER_INSTANCIA(Context CONTEXTO) {
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
        String sql = String.format("SELECT * FROM %s WHERE %s=? AND substr(%s, 0, 11) =?", TABLAS.A77_PASILLO, I_A77_Pasillo.ID_TURNO, I_A77_Pasillo.FECHA_INGRESO);
        String[] selectionArgs = {TURNO, DIA};
        return db.rawQuery(sql, selectionArgs);
    }


    public long INSERTAR_A77_PASILLO(A77_Pasillo A77) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = I_A77_Pasillo.GENERAR_ID_A77_Pasillo();
        VALORES = new ContentValues();
        VALORES.put(I_A77_Pasillo.ID, ID);
        VALORES.put(I_A77_Pasillo.ID_PASILLO, A77.ID_PASILLO);
        VALORES.put(I_A77_Pasillo.LUMINOSIDAD_A, A77.LUMINOSIDAD_A);
        VALORES.put(I_A77_Pasillo.LUMINOSIDAD_C, A77.LUMINOSIDAD_C);
        VALORES.put(I_A77_Pasillo.LUMINOSIDAD_B, A77.LUMINOSIDAD_B);
        VALORES.put(I_A77_Pasillo.RUIDO_A, A77.RUIDO_A);
        VALORES.put(I_A77_Pasillo.RUIDO_C, A77.RUIDO_C);
        VALORES.put(I_A77_Pasillo.RUIDO_B, A77.RUIDO_B);
        VALORES.put(I_A77_Pasillo.EVENTO, A77.EVENTO);
        VALORES.put(I_A77_Pasillo.DESCRIPCION, A77.DESCRIPCION);
        VALORES.put(I_A77_Pasillo.HORA_INGRESO, A77.HORA_INGRESO);
        VALORES.put(I_A77_Pasillo.HORA_SALIDA, A77.HORA_SALIDA);
        VALORES.put(I_A77_Pasillo.ID_TURNO, A77.ID_TURNO);
        VALORES.put(I_A77_Pasillo.RONDA, A77.RONDA);
        VALORES.put(I_A77_Pasillo.ESTADO, A77.ESTADO);
        VALORES.put(I_A77_Pasillo.USUARIO_INGRESA, A77.USUARIO_INGRESA);
        VALORES.put(I_A77_Pasillo.USUARIO_MODIFICA, A77.USUARIO_MODIFICA);
        VALORES.put(I_A77_Pasillo.FECHA_INGRESO, A77.FECHA_INGRESO);
        VALORES.put(I_A77_Pasillo.FECHA_MODIFICACION, A77.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A77_PASILLO, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long INSERTAR_A77_AREA(A77_Area A77) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = IT_A77_Area.I_A77_Area.GENERAR_ID_A77_Area();
        VALORES = new ContentValues();
        VALORES.put(IT_A77_Area.I_A77_Area.ID, ID);
        VALORES.put(IT_A77_Area.I_A77_Area.ID_AREA, A77.ID_AREA);
        VALORES.put(IT_A77_Area.I_A77_Area.LUMINOSIDAD, A77.LUMINOSIDAD);
        VALORES.put(IT_A77_Area.I_A77_Area.RUIDO, A77.RUIDO);
        VALORES.put(IT_A77_Area.I_A77_Area.HORA_INGRESO, A77.HORA_INGRESO);
        VALORES.put(IT_A77_Area.I_A77_Area.HORA_SALIDA, A77.HORA_SALIDA);
        VALORES.put(IT_A77_Area.I_A77_Area.ID_TURNO, A77.ID_TURNO);
        VALORES.put(IT_A77_Area.I_A77_Area.RONDA, A77.RONDA);
        VALORES.put(IT_A77_Area.I_A77_Area.ESTADO, A77.ESTADO);
        VALORES.put(IT_A77_Area.I_A77_Area.USUARIO_INGRESA, A77.USUARIO_INGRESA);
        VALORES.put(IT_A77_Area.I_A77_Area.USUARIO_MODIFICA, A77.USUARIO_MODIFICA);
        VALORES.put(IT_A77_Area.I_A77_Area.FECHA_INGRESO, A77.FECHA_INGRESO);
        VALORES.put(IT_A77_Area.I_A77_Area.FECHA_MODIFICACION, A77.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A77_AREA, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public long INSERTAR_A77_AIRE(A77_Aire A77) {
        SQLiteDatabase db = BASEDATOS.getWritableDatabase();
        ContentValues VALORES = null;
        String ID = "";
        // GENERAR PK
        ID = IT_A77_Aire.I_A77_Aire.GENERAR_ID_A77_Aire();
        VALORES = new ContentValues();
        VALORES.put(IT_A77_Aire.I_A77_Aire.ID, ID);
        VALORES.put(IT_A77_Aire.I_A77_Aire.ID_PASILLO, A77.ID_PASILLO);
        VALORES.put(IT_A77_Aire.I_A77_Aire.A1, A77.A1);
        VALORES.put(IT_A77_Aire.I_A77_Aire.A2, A77.A2);
        VALORES.put(IT_A77_Aire.I_A77_Aire.A3, A77.A3);
        VALORES.put(IT_A77_Aire.I_A77_Aire.B1, A77.B1);
        VALORES.put(IT_A77_Aire.I_A77_Aire.B2, A77.B2);
        VALORES.put(IT_A77_Aire.I_A77_Aire.B3, A77.B3);
        VALORES.put(IT_A77_Aire.I_A77_Aire.C1, A77.C1);
        VALORES.put(IT_A77_Aire.I_A77_Aire.C2, A77.C2);
        VALORES.put(IT_A77_Aire.I_A77_Aire.C3, A77.C3);
        VALORES.put(IT_A77_Aire.I_A77_Aire.HORA_INGRESO, A77.HORA_INGRESO);
        VALORES.put(IT_A77_Aire.I_A77_Aire.HORA_SALIDA, A77.HORA_SALIDA);
        VALORES.put(IT_A77_Aire.I_A77_Aire.ID_TURNO, A77.ID_TURNO);
        VALORES.put(IT_A77_Aire.I_A77_Aire.RONDA, A77.RONDA);
        VALORES.put(IT_A77_Aire.I_A77_Aire.ESTADO, A77.ESTADO);
        VALORES.put(IT_A77_Aire.I_A77_Aire.USUARIO_INGRESA, A77.USUARIO_INGRESA);
        VALORES.put(IT_A77_Aire.I_A77_Aire.USUARIO_MODIFICA, A77.USUARIO_MODIFICA);
        VALORES.put(IT_A77_Aire.I_A77_Aire.FECHA_INGRESO, A77.FECHA_INGRESO);
        VALORES.put(IT_A77_Aire.I_A77_Aire.FECHA_MODIFICACION, A77.FECHA_MODIFICACION);
        return db.insertWithOnConflict(TABLAS.A77_AIRE, null, VALORES, SQLiteDatabase.CONFLICT_ROLLBACK);
    }

    public SQLiteDatabase GETBD() {
        return BASEDATOS.getWritableDatabase();
    }

}
