package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A77_Pasillo {

    interface COLUMNAS_A77_Pasillo {
        String ID = "ID";
        String ID_PASILLO = "ID_PASILLO";
        String LUMINOSIDAD_A = "LUMINOSIDAD_A";
        String LUMINOSIDAD_C = "LUMINOSIDAD_C";
        String LUMINOSIDAD_B = "LUMINOSIDAD_B";
        String RUIDO_A = "RUIDO_A";
        String RUIDO_C = "RUIDO_C";
        String RUIDO_B = "RUIDO_B";
        String EVENTO = "EVENTO";
        String DESCRIPCION = "DESCRIPCION";
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

    public static class I_A77_Pasillo implements COLUMNAS_A77_Pasillo {
        public static String GENERAR_ID_A77_Pasillo() {
            return "A77P-" + UUID.randomUUID().toString();
        }
    }

}
