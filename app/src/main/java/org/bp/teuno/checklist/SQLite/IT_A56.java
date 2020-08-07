package org.bp.teuno.checklist.SQLite;

import java.util.UUID;

public class IT_A56 {

    interface COLUMNAS_A56 {
        String ID = "ID";
        String ID_ALERTA = "ID_ALERTA";
        String A = "A";
        String C = "C";
        String PDUA = "PDUA";
        String PDUB = "PDUB";
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

    public static class I_A56 implements COLUMNAS_A56 {
        public static String GENERAR_ID_A56(String CLIENTE) {
            if (CLIENTE.equals("BP")) {
                return "A56BP-" + UUID.randomUUID().toString();
            } else {
                return "A56DN-" + UUID.randomUUID().toString();
            }
        }
    }

}
