package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A58_Pasillo {

    interface COLUMNAS_A58_Pasillo {
        String ID = "ID";
        String ID_PASILLO = "ID_PASILLO";
        String TEMPERATURA = "TEMPERATURA";
        String TEMP_SENSOR = "TEMP_SENSOR";
        String HUMEDAD = "HUMEDAD";
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

    public static class I_A58_Pasillo implements COLUMNAS_A58_Pasillo {
        public static String GENERAR_ID_A58_Pasillo() {
            return "A58P-" + UUID.randomUUID().toString();
        }
    }

}
