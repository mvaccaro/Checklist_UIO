package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A34_Tanque_Ecaro {

    interface COLUMNAS_A34_Tanque_Ecaro {
        String ID = "ID";
        String ID_TANQUE_ECARO = "ID_TANQUE_ECARO";
        String COLOR = "COLOR";
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

    public static class I_A34_Tanque_Ecaro implements COLUMNAS_A34_Tanque_Ecaro {
        public static String GENERAR_ID_A34_Tanque_Ecaro() {
            return "A34T-" + UUID.randomUUID().toString();
        }
    }

}
