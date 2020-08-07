package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A34_Ups {

    interface COLUMNAS_A34_Ups {
        String ID = "ID";
        String ID_UPS = "ID_UPS";
        String MEDICION = "MEDICION";
        String L1 = "L1";
        String L2 = "L2";
        String L3 = "L3";
        String BATERIA = "BATERIA";
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

    public static class I_A34_Ups implements COLUMNAS_A34_Ups {
        public static String GENERAR_ID_A34_Ups() {
            return "A34P-" + UUID.randomUUID().toString();
        }
    }

}
