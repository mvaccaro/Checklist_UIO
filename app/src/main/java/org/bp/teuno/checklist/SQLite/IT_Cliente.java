package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Cliente {

    interface COLUMNAS_CLIENTE {
        String ID = "ID";
        String ID_CLIENTE = "ID_CLIENTE";
        String NOMBRE_CLIENTE = "NOMBRE_CLIENTE";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_CLIENTE implements COLUMNAS_CLIENTE {
        public static String GENERAR_ID_CLIENTE() {
            return "CLI-" + UUID.randomUUID().toString();
        }
    }

}
