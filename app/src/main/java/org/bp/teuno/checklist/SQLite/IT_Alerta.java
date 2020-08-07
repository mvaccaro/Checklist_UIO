package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_Alerta {

    interface COLUMNAS_ALERTA {
        String ID = "ID";
        String ID_FILA = "ID_FILA";
        String ID_RACK = "ID_RACK";
        String ID_RACK_UR = "ID_RACK_UR";
        String ID_EQUIPO = "ID_EQUIPO";
        String ID_TIPO_ALERTA = "ID_TIPO_ALERTA";
        String ID_COLOR_ALERTA = "ID_COLOR_ALERTA";
        String COMENTARIO = "COMENTARIO";
        String TICKET_EXTERNO = "TICKET_EXTERNO";
        String IMAGEN = "IMAGEN";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_ALERTA implements COLUMNAS_ALERTA {
        public static String GENERAR_ID_ALERTA(String CLIENTE) {
            if (CLIENTE.equals("BP")) {
                return "ALEBP-" + UUID.randomUUID().toString();
            } else {
                return "ALEDN-" + UUID.randomUUID().toString();
            }
        }
    }

}
