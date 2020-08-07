package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A34_Xcom {

    interface COLUMNAS_A34_Xcom {
        String ID = "ID";
        String ID_XCOM = "ID_XCOM";
        String ALERTA = "ALERTA";
        String VOLTAJE_RACK = "VOLTAJE_RACK";
        String A_C = "A_C";
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

    public static class I_A34_Xcom implements COLUMNAS_A34_Xcom {
        public static String GENERAR_ID_A34_Xcom() {
            return "A34X-" + UUID.randomUUID().toString();
        }
    }

}
