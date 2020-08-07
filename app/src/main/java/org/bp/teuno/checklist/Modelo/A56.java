package org.bp.teuno.checklist.Modelo;

public class A56 {

    public String ID;
    public String ID_ALERTA;
    public String A;
    public String C;
    public String PDUA;
    public String PDUB;
    public String HORA_INGRESO;
    public String HORA_SALIDA;
    public String ID_TURNO;
    public String RONDA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public A56(String ID, String ID_ALERTA, String a, String c, String PDUA, String PDUB, String HORA_INGRESO, String HORA_SALIDA, String ID_TURNO, String RONDA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_ALERTA = ID_ALERTA;
        A = a;
        C = c;
        this.PDUA = PDUA;
        this.PDUB = PDUB;
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

    public String getID_ALERTA() {
        return ID_ALERTA;
    }

    public void setID_ALERTA(String ID_ALERTA) {
        this.ID_ALERTA = ID_ALERTA;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getPDUA() {
        return PDUA;
    }

    public void setPDUA(String PDUA) {
        this.PDUA = PDUA;
    }

    public String getPDUB() {
        return PDUB;
    }

    public void setPDUB(String PDUB) {
        this.PDUB = PDUB;
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
