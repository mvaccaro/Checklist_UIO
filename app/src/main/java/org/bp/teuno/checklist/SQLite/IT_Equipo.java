package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Equipo {

    interface COLUMNAS_EQUIPO {
        String ID = "ID";
        String ID_FILA = "ID_FILA";
        String ID_RACK = "ID_RACK";
        String ID_RACK_UR = "ID_RACK_UR";
        String NOMBRE_EQUIPO = "NOMBRE_EQUIPO";
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

    public static class I_EQUIPO implements COLUMNAS_EQUIPO {
        public static String GENERAR_ID_EQUIPO() {
            return "EQP-" + UUID.randomUUID().toString();
        }
    }

}
