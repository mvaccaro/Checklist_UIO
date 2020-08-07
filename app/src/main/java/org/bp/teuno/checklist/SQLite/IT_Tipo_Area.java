package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Tipo_Area {

    interface COLUMNAS_TIPO_AREA {
        String ID = "ID";
        String ID_TIPO_AREA = "ID_TIPO_AREA";
        String NOMBRE_TIPO_AREA = "NOMBRE_TIPO_AREA";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_TIPO_AREA implements COLUMNAS_TIPO_AREA {
        public static String GENERAR_ID_TIPO_AREA() {
            return "TAR-" + UUID.randomUUID().toString();
        }
    }

}
