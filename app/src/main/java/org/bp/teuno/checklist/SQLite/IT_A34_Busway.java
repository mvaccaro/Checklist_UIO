package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A34_Busway {

    interface COLUMNAS_A34_Busway {
        String ID = "ID";
        String ID_BUSWAY = "ID_BUSWAY";
        String VOLTAJE = "VOLTAJE";
        String CORRIENTE = "CORRIENTE";
        String POTENCIA = "POTENCIA";
        String PDU = "PDU";
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

    public static class I_A34_Busway implements COLUMNAS_A34_Busway {
        public static String GENERAR_ID_A34_Busway() {
            return "A34B-" + UUID.randomUUID().toString();
        }
    }

}
