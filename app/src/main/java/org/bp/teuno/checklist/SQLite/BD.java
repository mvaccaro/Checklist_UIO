package org.bp.teuno.checklist.SQLite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

/**
 * Class that manages the BD
 */
public class BD extends SQLiteOpenHelper {

    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Database/";
    private static final String NAME_BD = "ChecklistAutomaticoDB.db";
    private static final int VERSION = 1;
    private Context context;

    /**
     * Builder
     * It takes reference to the context of the application that invokes it for
     * be able to access the 'assets' and 'resources' of the application.
     * Create a DBOpenHelper object that will allow us to control the opening of
     * the database.
     *
     * @param
     */
    public BD(Context context) {
        super(context, PATH + NAME_BD, null, VERSION, new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase dbObj) {
            }
        });
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public interface TABLAS {
        /**
         * Maintenance
         **/
        String OPERADOR = "OPERADOR";
        String FILA = "FILA";
        String RACK = "RACK";
        String EQUIPO = "EQUIPO";
        String CLIENTE = "CLIENTE";
        String CANTIDAD_UR = "CANTIDAD_UR";
        String TIPO_ALERTA = "TIPO_ALERTA";
        String COLOR_ALERTA = "COLOR_ALERTA";
        String RACK_UR = "RACK_UR";
        String TURNO = "TURNO";
        String ALERTA = "ALERTA";
        String GRUPO = "GRUPO";
        String PASILLO = "PASILLO";
        String AREA = "AREA";
        String CHILLER = "CHILLER";
        String TIPO_PASILLO = "TIPO_PASILLO";
        String TIPO_AREA = "TIPO_AREA";
        String VERSION = "VERSION";
        String PARAMETROS = "PARAMETROS";
        /**
         * Forms
         **/
        String A34_UMA = "A34_UMA";
        String A34_UPS = "A34_UPS";
        String A34_TANQUE_ECARO = "A34_TANQUE_ECARO";
        String A34_BUSWAY = "A34_BUSWAY";
        String A34_XCOM = "A34_XCOM";
        String A56 = "A56";
        String A58_PASILLO = "A58_PASILLO";
        String A58_CHILLER = "A58_CHILLER";
        String A71_PASILLO = "A71_PASILLO";
        String A71_AREA = "A71_AREA";
        String A77_PASILLO = "A77_PASILLO";
        String A77_AREA = "A77_AREA";
        String A77_AIRE = "A77_AIRE";
        String A76 = "A76";
        String A98 = "A98";
    }
}