package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A34_Uma {

    interface COLUMNAS_A34_Uma {
        String ID = "ID";
        String ID_UMA = "ID_UMA";
        String MEDICION = "MEDICION";
        String TEMPERATURA = "TEMPERATURA";
        String HUMEDAD = "HUMEDAD";
        String ALERTA = "ALERTA";
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

    public static class I_A34_Uma implements COLUMNAS_A34_Uma {
        public static String GENERAR_ID_A34_Uma() {
            return "A34U-" + UUID.randomUUID().toString();
        }
    }

}
