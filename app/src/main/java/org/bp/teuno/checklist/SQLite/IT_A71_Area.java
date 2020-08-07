package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A71_Area {

    interface COLUMNAS_A71_Area {
        String ID = "ID";
        String ID_AREA = "ID_AREA";
        String ID_EVENTO = "ID_EVENTO";
        String DESCRIPCION = "DESCRIPCION";
        String HORA_INGRESO = "HORA_INGRESO";
        String HORA_SALIDA = "HORA_SALIDA";
        String ID_TURNO = "ID_TURNO";
        String RONDA = "RONDA";
        String ESTADO = "ESTADO";
        String USUARIO_INGRESA = "USUARIO_INGRESA";
        String USUARIO_MODIFICA = "USUARIO_MODIFICA";
        String FECHA_INGRESO = "FECHA_INGRESO";
        String FECHA_MODIFICACION = "FECHA_MODIFICACION";
    }

    public static class I_A71_Area implements COLUMNAS_A71_Area {
        public static String GENERAR_ID_A71_Area() {
            return "A71A-" + UUID.randomUUID().toString();
        }
    }

}
