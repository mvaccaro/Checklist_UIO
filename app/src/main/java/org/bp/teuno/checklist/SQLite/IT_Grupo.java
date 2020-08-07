package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Grupo {

    interface COLUMNAS_GRUPO {
        String ID = "ID";
        String ID_GRUPO = "ID_GRUPO";
        String NOMBRE_GRUPO = "NOMBRE_GRUPO";
        String PIN_SEGURIDAD = "PIN_SEGURIDAD";
        String PERMISO_FILA = "PERMISO_FILA";
        String PERMISO_CLIENTE = "PERMISO_CLIENTE";
        String PERMISO_CANTIDAD_UR = "PERMISO_CANTIDAD_UR";
        String PERMISO_COLOR_ALERTA = "PERMISO_COLOR_ALERTA";
        String PERMISO_TIPO_ALERTA = "PERMISO_TIPO_ALERTA";
        String PERMISO_RACK = "PERMISO_RACK";
        String PERMISO_EQUIPO = "PERMISO_EQUIPO";
        String PERMISO_ALERTA = "PERMISO_ALERTA";
        String PERMISO_PASILLO = "PERMISO_PASILLO";
        String PERMISO_CHILLER = "PERMISO_CHILLER";
        String PERMISO_AREA = "PERMISO_AREA";
        String PERMISO_GRUPO = "PERMISO_GRUPO";
        String PERMISO_VERSION = "PERMISO_VERSION";
        String PERMISO_PARAMETROS = "PERMISO_PARAMETROS";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_GRUPO implements COLUMNAS_GRUPO {
        public static String GENERAR_ID_GRUPO() {
            return "GRP-" + UUID.randomUUID().toString();
        }
    }

}
