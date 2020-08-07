package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A98 {

    interface COLUMNAS_A98 {
        String ID = "ID";
        String ID_FILA = "ID_FILA";
        String ID_RACK = "ID_RACK";
        String ID_AREA = "ID_AREA";
        String INICIO = "INICIO";
        String FIN = "FIN";
        String OBSERVACIONES = "OBSERVACIONES";
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

    public static class I_A98 implements COLUMNAS_A98 {
        public static String GENERAR_ID_A98() {
            return "A98-" + UUID.randomUUID().toString();
        }
    }

}
