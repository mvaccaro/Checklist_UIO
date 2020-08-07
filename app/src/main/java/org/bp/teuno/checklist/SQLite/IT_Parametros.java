package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Parametros {

    interface COLUMNAS_PARAMETROS {
        String ID = "ID";
        String TEMP_PAS_MINIMA = "TEMP_PAS_MINIMA";
        String TEMP_PAS_MAXIMA = "TEMP_PAS_MAXIMA";
        String HUME_PAS_MINIMA = "HUME_PAS_MINIMA";
        String HUME_PAS_MAXIMA = "HUME_PAS_MAXIMA";
        String TEMP_SUM_MINIMA = "TEMP_SUM_MINIMA";
        String TEMP_SUM_MAXIMA = "TEMP_SUM_MAXIMA";
        String TEMP_RET_MINIMA = "TEMP_RET_MINIMA";
        String TEMP_RET_MAXIMA = "TEMP_RET_MAXIMA";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_PARAMETROS implements COLUMNAS_PARAMETROS {
        public static String GENERAR_ID_PARAMETROS() {
            return "PAR-" + UUID.randomUUID().toString();
        }
    }

}
