package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Tipo_Alerta {

    interface COLUMNAS_TIPO_ALERTA {
        String ID = "ID";
        String ID_TIPO_ALERTA = "ID_TIPO_ALERTA";
        String NOMBRE_TIPO_ALERTA = "NOMBRE_TIPO_ALERTA";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_TIPO_ALERTA implements COLUMNAS_TIPO_ALERTA {
        public static String GENERAR_ID_TIPO_ALERTA() {
            return "TAL-" + UUID.randomUUID().toString();
        }
    }

}
