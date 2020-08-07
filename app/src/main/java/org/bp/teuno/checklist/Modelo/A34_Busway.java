package org.bp.teuno.checklist.Modelo;

public class A34_Busway {

    public String ID;
    public String ID_BUSWAY;
    public String VOLTAJE;
    public String CORRIENTE;
    public String POTENCIA;
    public String PDU;
    public String HORA_INGRESO;
    public String HORA_SALIDA;
    public String ID_TURNO;
    public String RONDA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public A34_Busway(String ID, String ID_BUSWAY, String VOLTAJE, String CORRIENTE, String POTENCIA, String PDU, String HORA_INGRESO, String HORA_SALIDA, String ID_TURNO, String RONDA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_BUSWAY = ID_BUSWAY;
        this.VOLTAJE = VOLTAJE;
        this.CORRIENTE = CORRIENTE;
        this.POTENCIA = POTENCIA;
        this.PDU = PDU;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_BUSWAY() {
        return ID_BUSWAY;
    }

    public void setID_BUSWAY(String ID_BUSWAY) {
        this.ID_BUSWAY = ID_BUSWAY;
    }

    public String getVOLTAJE() {
        return VOLTAJE;
    }

    public void setVOLTAJE(String VOLTAJE) {
        this.VOLTAJE = VOLTAJE;
    }

    public String getCORRIENTE() {
        return CORRIENTE;
    }

    public void setCORRIENTE(String CORRIENTE) {
        this.CORRIENTE = CORRIENTE;
    }

    public String getPOTENCIA() {
        return POTENCIA;
    }

    public void setPOTENCIA(String POTENCIA) {
        this.POTENCIA = POTENCIA;
    }

    public String getPDU() {
        return PDU;
    }

    public void setPDU(String PDU) {
        this.PDU = PDU;
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