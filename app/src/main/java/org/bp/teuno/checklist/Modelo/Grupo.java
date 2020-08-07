package org.bp.teuno.checklist.Modelo;

public class Grupo {

    public String ID;
    public String ID_GRUPO;
    public String NOMBRE_GRUPO;
    public String PIN_SEGURIDAD;
    public String PERMISO_FILA;
    public String PERMISO_CLIENTE;
    public String PERMISO_CANTIDAD_UR;
    public String PERMISO_COLOR_ALERTA;
    public String PERMISO_TIPO_ALERTA;
    public String PERMISO_RACK;
    public String PERMISO_EQUIPO;
    public String PERMISO_ALERTA;
    public String PERMISO_PASILLO;
    public String PERMISO_CHILLER;
    public String PERMISO_AREA;
    public String PERMISO_GRUPO;
    public String PERMISO_VERSION;
    public String PERMISO_PARAMETROS;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Grupo(String ID, String ID_GRUPO, String NOMBRE_GRUPO, String PIN_SEGURIDAD, String PERMISO_FILA, String PERMISO_CLIENTE, String PERMISO_CANTIDAD_UR, String PERMISO_COLOR_ALERTA, String PERMISO_TIPO_ALERTA, String PERMISO_RACK, String PERMISO_EQUIPO, String PERMISO_ALERTA, String PERMISO_PASILLO, String PERMISO_CHILLER, String PERMISO_AREA, String PERMISO_GRUPO, String PERMISO_VERSION, String PERMISO_PARAMETROS, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_GRUPO = ID_GRUPO;
        this.NOMBRE_GRUPO = NOMBRE_GRUPO;
        this.PIN_SEGURIDAD = PIN_SEGURIDAD;
        this.PERMISO_FILA = PERMISO_FILA;
        this.PERMISO_CLIENTE = PERMISO_CLIENTE;
        this.PERMISO_CANTIDAD_UR = PERMISO_CANTIDAD_UR;
        this.PERMISO_COLOR_ALERTA = PERMISO_COLOR_ALERTA;
        this.PERMISO_TIPO_ALERTA = PERMISO_TIPO_ALERTA;
        this.PERMISO_RACK = PERMISO_RACK;
        this.PERMISO_EQUIPO = PERMISO_EQUIPO;
        this.PERMISO_ALERTA = PERMISO_ALERTA;
        this.PERMISO_PASILLO = PERMISO_PASILLO;
        this.PERMISO_CHILLER = PERMISO_CHILLER;
        this.PERMISO_AREA = PERMISO_AREA;
        this.PERMISO_GRUPO = PERMISO_GRUPO;
        this.PERMISO_VERSION = PERMISO_VERSION;
        this.PERMISO_PARAMETROS = PERMISO_PARAMETROS;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Grupo(String ID, String ID_GRUPO, String NOMBRE_GRUPO, String PIN_SEGURIDAD, String PERMISO_FILA, String PERMISO_CLIENTE, String PERMISO_CANTIDAD_UR, String PERMISO_COLOR_ALERTA, String PERMISO_TIPO_ALERTA, String PERMISO_RACK, String PERMISO_EQUIPO, String PERMISO_ALERTA, String PERMISO_PASILLO, String PERMISO_CHILLER, String PERMISO_AREA, String PERMISO_GRUPO, String PERMISO_VERSION, String PERMISO_PARAMETROS) {
        this.ID = ID;
        this.ID_GRUPO = ID_GRUPO;
        this.NOMBRE_GRUPO = NOMBRE_GRUPO;
        this.PIN_SEGURIDAD = PIN_SEGURIDAD;
        this.PERMISO_FILA = PERMISO_FILA;
        this.PERMISO_CLIENTE = PERMISO_CLIENTE;
        this.PERMISO_CANTIDAD_UR = PERMISO_CANTIDAD_UR;
        this.PERMISO_COLOR_ALERTA = PERMISO_COLOR_ALERTA;
        this.PERMISO_TIPO_ALERTA = PERMISO_TIPO_ALERTA;
        this.PERMISO_RACK = PERMISO_RACK;
        this.PERMISO_EQUIPO = PERMISO_EQUIPO;
        this.PERMISO_ALERTA = PERMISO_ALERTA;
        this.PERMISO_PASILLO = PERMISO_PASILLO;
        this.PERMISO_CHILLER = PERMISO_CHILLER;
        this.PERMISO_AREA = PERMISO_AREA;
        this.PERMISO_GRUPO = PERMISO_GRUPO;
        this.PERMISO_VERSION = PERMISO_VERSION;
        this.PERMISO_PARAMETROS = PERMISO_PARAMETROS;
    }

    public Grupo(String ID, String ID_GRUPO, String NOMBRE_GRUPO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_GRUPO = ID_GRUPO;
        this.NOMBRE_GRUPO = NOMBRE_GRUPO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Grupo(String ID, String ID_GRUPO, String NOMBRE_GRUPO) {
        this.ID = ID;
        this.ID_GRUPO = ID_GRUPO;
        this.NOMBRE_GRUPO = NOMBRE_GRUPO;
    }

    public Grupo(String ID, String ID_GRUPO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_GRUPO = ID_GRUPO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Grupo() {
        /*CONSTRUCTOR VACIO*/
    }

    public String getPERMISO_VERSION() {
        return PERMISO_VERSION;
    }

    public void setPERMISO_VERSION(String PERMISO_VERSION) {
        this.PERMISO_VERSION = PERMISO_VERSION;
    }

    public String getPERMISO_PARAMETROS() {
        return PERMISO_PARAMETROS;
    }

    public void setPERMISO_PARAMETROS(String PERMISO_PARAMETROS) {
        this.PERMISO_PARAMETROS = PERMISO_PARAMETROS;
    }

    public String getPIN_SEGURIDAD() {
        return PIN_SEGURIDAD;
    }

    public void setPIN_SEGURIDAD(String PIN_SEGURIDAD) {
        this.PIN_SEGURIDAD = PIN_SEGURIDAD;
    }

    public String getPERMISO_EQUIPO() {
        return PERMISO_EQUIPO;
    }

    public void setPERMISO_EQUIPO(String PERMISO_EQUIPO) {
        this.PERMISO_EQUIPO = PERMISO_EQUIPO;
    }

    public String getPERMISO_GRUPO() {
        return PERMISO_GRUPO;
    }

    public void setPERMISO_GRUPO(String PERMISO_GRUPO) {
        this.PERMISO_GRUPO = PERMISO_GRUPO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_GRUPO() {
        return ID_GRUPO;
    }

    public void setID_GRUPO(String ID_GRUPO) {
        this.ID_GRUPO = ID_GRUPO;
    }

    public String getNOMBRE_GRUPO() {
        return NOMBRE_GRUPO;
    }

    public void setNOMBRE_GRUPO(String NOMBRE_GRUPO) {
        this.NOMBRE_GRUPO = NOMBRE_GRUPO;
    }

    public String getPERMISO_FILA() {
        return PERMISO_FILA;
    }

    public void setPERMISO_FILA(String PERMISO_FILA) {
        this.PERMISO_FILA = PERMISO_FILA;
    }

    public String getPERMISO_CLIENTE() {
        return PERMISO_CLIENTE;
    }

    public void setPERMISO_CLIENTE(String PERMISO_CLIENTE) {
        this.PERMISO_CLIENTE = PERMISO_CLIENTE;
    }

    public String getPERMISO_CANTIDAD_UR() {
        return PERMISO_CANTIDAD_UR;
    }

    public void setPERMISO_CANTIDAD_UR(String PERMISO_CANTIDAD_UR) {
        this.PERMISO_CANTIDAD_UR = PERMISO_CANTIDAD_UR;
    }

    public String getPERMISO_COLOR_ALERTA() {
        return PERMISO_COLOR_ALERTA;
    }

    public void setPERMISO_COLOR_ALERTA(String PERMISO_COLOR_ALERTA) {
        this.PERMISO_COLOR_ALERTA = PERMISO_COLOR_ALERTA;
    }

    public String getPERMISO_TIPO_ALERTA() {
        return PERMISO_TIPO_ALERTA;
    }

    public void setPERMISO_TIPO_ALERTA(String PERMISO_TIPO_ALERTA) {
        this.PERMISO_TIPO_ALERTA = PERMISO_TIPO_ALERTA;
    }

    public String getPERMISO_RACK() {
        return PERMISO_RACK;
    }

    public void setPERMISO_RACK(String PERMISO_RACK) {
        this.PERMISO_RACK = PERMISO_RACK;
    }

    public String getPERMISO_ALERTA() {
        return PERMISO_ALERTA;
    }

    public void setPERMISO_ALERTA(String PERMISO_ALERTA) {
        this.PERMISO_ALERTA = PERMISO_ALERTA;
    }

    public String getPERMISO_PASILLO() {
        return PERMISO_PASILLO;
    }

    public void setPERMISO_PASILLO(String PERMISO_PASILLO) {
        this.PERMISO_PASILLO = PERMISO_PASILLO;
    }

    public String getPERMISO_CHILLER() {
        return PERMISO_CHILLER;
    }

    public void setPERMISO_CHILLER(String PERMISO_CHILLER) {
        this.PERMISO_CHILLER = PERMISO_CHILLER;
    }

    public String getPERMISO_AREA() {
        return PERMISO_AREA;
    }

    public void setPERMISO_AREA(String PERMISO_AREA) {
        this.PERMISO_AREA = PERMISO_AREA;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getUSUARIO_INGRESA() {
        return USUARIO_INGRESA;
    }

    public void setUSUARIO_INGRESA(String USUARIO_INGRESA) {
        this.USUARIO_INGRESA = USUARIO_INGRESA;
    }

    public String getUSUARIO_MODIFICA() {
        return USUARIO_MODIFICA;
    }

    public void setUSUARIO_MODIFICA(String USUARIO_MODIFICA) {
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
    }

    public String getFECHA_INGRESO() {
        return FECHA_INGRESO;
    }

    public void setFECHA_INGRESO(String FECHA_INGRESO) {
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public String getFECHA_MODIFICACION() {
        return FECHA_MODIFICACION;
    }

    public void setFECHA_MODIFICACION(String FECHA_MODIFICACION) {
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public String toString() {
        if (this.ID_GRUPO.equals("-1")) {
            return this.NOMBRE_GRUPO;
        } else {
            return this.NOMBRE_GRUPO;
        }
    }

}
