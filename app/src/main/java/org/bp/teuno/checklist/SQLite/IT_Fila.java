package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Fila {

    interface COLUMNAS_FILA {
        String ID = "ID";
        String ID_FILA = "ID_FILA";
        String NOMBRE_FILA = "NOMBRE_FILA";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_FILA implements COLUMNAS_FILA {
        public static String GENERAR_ID_FILA() {
            return "FLA-" + UUID.randomUUID().toString();
        }
    }

}
