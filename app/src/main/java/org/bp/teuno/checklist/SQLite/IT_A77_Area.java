package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A77_Area {

    interface COLUMNAS_A77_Area {
        String ID = "ID";
        String ID_AREA = "ID_AREA";
        String LUMINOSIDAD = "LUMINOSIDAD";
        String RUIDO = "RUIDO";
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

    public static class I_A77_Area implements COLUMNAS_A77_Area {
        public static String GENERAR_ID_A77_Area() {
            return "A77A-" + UUID.randomUUID().toString();
        }
    }

}
