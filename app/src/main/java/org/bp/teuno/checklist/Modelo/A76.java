package org.bp.teuno.checklist.Modelo;

public class A76 {

    public String ID;
    public String ID_FILA;
    public String ID_RACK;
    public String ID_CANTIDAD_UR;
    public String UR_OCUPADAS;
    public String UR_DISPONIBLES;
    public String HORA_INGRESO;
    public String HORA_SALIDA;
    public String ID_TURNO;
    public String RONDA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public A76(String ID, String ID_FILA, String ID_RACK, String ID_CANTIDAD_UR, String UR_OCUPADAS, String UR_DISPONIBLES, String HORA_INGRESO, String HORA_SALIDA, String ID_TURNO, String RONDA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.ID_CANTIDAD_UR = ID_CANTIDAD_UR;
        this.UR_OCUPADAS = UR_OCUPADAS;
        this.UR_DISPONIBLES = UR_DISPONIBLES;
        this.HORA_INGRESO = HORA_INGRESO;
        this.HORA_SALIDA = HORA_SALIDA;
        this.ID_TURNO = ID_TURNO;
        this.RONDA = RONDA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public A76(String ID, String ID_FILA, String ID_RACK, String UR_OCUPADAS, String UR_DISPONIBLES, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR_OCUPADAS = UR_OCUPADAS;
        this.UR_DISPONIBLES = UR_DISPONIBLES;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public A76(String ID_FILA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID_FILA = ID_FILA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_FILA() {
        return ID_FILA;
    }

    public void setID_FILA(String ID_FILA) {
        this.ID_FILA = ID_FILA;
    }

    public String getID_RACK() {
        return ID_RACK;
    }

    public void setID_RACK(String ID_RACK) {
        this.ID_RACK = ID_RACK;
    }

    public String getID_CANTIDAD_UR() {
        return ID_CANTIDAD_UR;
    }

    public void setID_CANTIDAD_UR(String ID_CANTIDAD_UR) {
        this.ID_CANTIDAD_UR = ID_CANTIDAD_UR;
    }

    public String getUR_OCUPADAS() {
        return UR_OCUPADAS;
    }

    public void setUR_OCUPADAS(String UR_OCUPADAS) {
        this.UR_OCUPADAS = UR_OCUPADAS;
    }

    public String getUR_DISPONIBLES() {
        return UR_DISPONIBLES;
    }

    public void setUR_DISPONIBLES(String UR_DISPONIBLES) {
        this.UR_DISPONIBLES = UR_DISPONIBLES;
    }

    public String getHORA_INGRESO() {
        return HORA_INGRESO;
    }

    public void setHORA_INGRESO(String HORA_INGRESO) {
        this.HORA_INGRESO = HORA_INGRESO;
    }

    public String getHORA_SALIDA() {
        return HORA_SALIDA;
    }

    public void setHORA_SALIDA(String HORA_SALIDA) {
        this.HORA_SALIDA = HORA_SALIDA;
    }

    public String getID_TURNO() {
        return ID_TURNO;
    }

    public void setID_TURNO(String ID_TURNO) {
        this.ID_TURNO = ID_TURNO;
    }

    public String getRONDA() {
        return RONDA;
    }

    public void setRONDA(String RONDA) {
        this.RONDA = RONDA;
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
}