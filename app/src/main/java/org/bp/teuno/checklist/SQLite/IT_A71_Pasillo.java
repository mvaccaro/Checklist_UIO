package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A71_Pasillo {

    interface COLUMNAS_A71_Pasillo {
        String ID = "ID";
        String ID_PASILLO = "ID_PASILLO";
        String LUMINOSIDAD_A = "LUMINOSIDAD_A";
        String LUMINOSIDAD_B = "LUMINOSIDAD_B";
        String RUIDO_A = "RUIDO_A";
        String RUIDO_B = "RUIDO_B";
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

    public static class I_A71_Pasillo implements COLUMNAS_A71_Pasillo {
        public static String GENERAR_ID_A71_Pasillo() {
            return "A71P-" + UUID.randomUUID().toString();
        }
    }

}
