package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Rack {

    interface COLUMNAS_RACK {
        String ID = "ID";
        String NOMBRE_RACK = "NOMBRE_RACK";
        String ID_FILA = "ID_FILA";
        String ID_CANTIDAD_UR = "ID_CANTIDAD_UR";
        String ID_CLIENTE = "ID_CLIENTE";
        String MARCA = "MARCA";
        String MODELO = "MODELO";
        String SERIE = "SERIE";
        String CODIGO_BANCO = "CODIGO_BANCO";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_RACK implements COLUMNAS_RACK {
        public static String GENERAR_ID_RACK() {
            return "RCK-" + UUID.randomUUID().toString();
        }
    }

}
