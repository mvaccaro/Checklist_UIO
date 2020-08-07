package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A58_Chiller {

    interface COLUMNAS_A58_Chiller {
        String ID = "ID";
        String ID_CHILLER = "ID_CHILLER";
        String TEMP_SUMINISTRO = "TEMP_SUMINISTRO";
        String TEMP_RETORNO = "TEMP_RETORNO";
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

    public static class I_A58_Chiller implements COLUMNAS_A58_Chiller {
        public static String GENERAR_ID_A58_Chiller() {
            return "A58C-" + UUID.randomUUID().toString();
        }
    }

}
