package org.bp.teuno.checklist.Modelo;

public class A34_Ups {

    public String ID;
    public String ID_UPS;
    public String MEDICION;
    public String L1;
    public String L2;
    public String L3;
    public String BATERIA;
    public String ALERTA;
    public String HORA_INGRESO;
    public String HORA_SALIDA;
    public String ID_TURNO;
    public String RONDA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public A34_Ups(String ID, String ID_UPS, String MEDICION, String l1, String l2, String l3, String BATERIA, String ALERTA, String HORA_INGRESO, String HORA_SALIDA, String ID_TURNO, String RONDA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_UPS = ID_UPS;
        this.MEDICION = MEDICION;
        L1 = l1;
        L2 = l2;
        L3 = l3;
        this.BATERIA = BATERIA;
        this.ALERTA = ALERTA;
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

    public String getID_UPS() {
        return ID_UPS;
    }

    public void setID_UPS(String ID_UPS) {
        this.ID_UPS = ID_UPS;
    }

    public String getMEDICION() {
        return MEDICION;
    }

    public void setMEDICION(String MEDICION) {
        this.MEDICION = MEDICION;
    }

    public String getL1() {
        return L1;
    }

    public void setL1(String l1) {
        L1 = l1;
    }

    public String getL2() {
        return L2;
    }

    public void setL2(String l2) {
        L2 = l2;
    }

    public String getL3() {
        return L3;
    }

    public void setL3(String l3) {
        L3 = l3;
    }

    public String getBATERIA() {
        return BATERIA;
    }

    public void setBATERIA(String BATERIA) {
        this.BATERIA = BATERIA;
    }

    public String getALERTA() {
        return ALERTA;
    }

    public void setALERTA(String ALERTA) {
        this.ALERTA = ALERTA;
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