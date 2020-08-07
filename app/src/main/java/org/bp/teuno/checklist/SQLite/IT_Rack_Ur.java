package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Rack_Ur {

    interface COLUMNAS_RACK_UR {
        String ID = "ID";
        String ID_RACK = "ID_RACK";
        String UR = "UR";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_RACK_UR implements COLUMNAS_RACK_UR {
        public static String GENERAR_ID_RACK_UR() {
            return "RUR-" + UUID.randomUUID().toString();
        }
    }

}
