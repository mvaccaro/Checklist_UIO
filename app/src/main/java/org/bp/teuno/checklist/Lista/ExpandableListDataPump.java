package org.bp.teuno.checklist.Lista;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> formularios = new ArrayList<String>();
        formularios.add("1.- Formulario A.34 - Checklist de sala de máquinas");
        formularios.add("2.- Formulario A.56/FD.56 - Checklist monitoreo de equipos de sala útil");
        formularios.add("3.- Formulario A.58 - Humedad pasillos fríos y chillers en sala útil");
        formularios.add("4.- Formulario A.71 - Checklist estado infraestructura");
        formularios.add("5.- Formulario A.76 - Chequeo capacidad racks");
        formularios.add("6.- Formulario A.77 - Chequeo luminosidad y ruido");
        formularios.add("7.- Formulario A.98 - Checklist validación de estado de racks");

        List<String> mantenimientos = new ArrayList<String>();
        mantenimientos.add("1.- Mantenimiento de Filas");
        mantenimientos.add("2.- Mantenimiento de Cantidad de UR");
        mantenimientos.add("3-  Mantenimiento de Clientes");
        mantenimientos.add("4.- Mantenimiento de Tipo de Alerta");
        mantenimientos.add("5.- Mantenimiento de Color de Alerta");
        mantenimientos.add("6.- Mantenimiento de Rack");
        mantenimientos.add("7.- Mantenimiento de Equipos");
        mantenimientos.add("8.- Mantenimiento de Alertas");
        mantenimientos.add("9.- Mantenimiento de Grupos y Permisos");
        mantenimientos.add("10.- Mantenimiento de Pasillos");
        mantenimientos.add("11.- Mantenimiento de Areas");
        mantenimientos.add("12.- Mantenimiento de Chillers");
        mantenimientos.add("13.- Mantenimiento de Versiones");
        mantenimientos.add("14.- Mantenimiento de Parámetros");

        expandableListDetail.put("FORMULARIOS", formularios);
        expandableListDetail.put("MANTENIMIENTOS", mantenimientos);
        return expandableListDetail;
    }
}
