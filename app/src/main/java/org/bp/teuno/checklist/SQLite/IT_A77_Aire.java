package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A77_Aire {

    interface COLUMNAS_A77_Aire {
        String ID = "ID";
        String ID_PASILLO = "ID_PASILLO";
        String A1 = "A1";
        String A2 = "A2";
        String A3 = "A3";
        String B1 = "B1";
        String B2 = "B2";
        String B3 = "B3";
        String C1 = "C1";
        String C2 = "C2";
        String C3 = "C3";
        String HORA_INGRESO = "HORA_INGRESO";
        String HORA_SALIDA = "HORA_SALIDA";
        String ID_TURNO = "ID_TURNO";
        String RONDA = "RONDA";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_A77_Aire implements COLUMNAS_A77_Aire {
        public static String GENERAR_ID_A77_Aire() {
            return "A77F-" + UUID.randomUUID().toString();
        }
    }

}
