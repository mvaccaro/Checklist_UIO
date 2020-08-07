package org.bp.teuno.checklist.Modelo;

public class Turno {

    public String ID;
    public String TURNO;
    public String HORA_INICIO;
    public String HORA_FIN;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Turno(String ID, String TURNO, String HORA_INICIO, String HORA_FIN, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.TURNO = TURNO;
        this.HORA_INICIO = HORA_INICIO;
        this.HORA_FIN = HORA_FIN;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Turno(String ID, String TURNO, String HORA_INICIO, String HORA_FIN) {
        this.ID = ID;
        this.TURNO = TURNO;
        this.HORA_INICIO = HORA_INICIO;
        this.HORA_FIN = HORA_FIN;
    }

    public Turno(String ID) {
        this.ID = ID;
    }

    public Turno() {
        /*CONSTRUCTOR VACIO*/
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTURNO() {
        return TURNO;
    }

    public void setTURNO(String TURNO) {
        this.TURNO = TURNO;
    }

    public String getHORA_INICIO() {
        return HORA_INICIO;
    }

    public void setHORA_INICIO(String HORA_INICIO) {
        this.HORA_INICIO = HORA_INICIO;
    }

    public String getHORA_FIN() {
        return HORA_FIN;
    }

    public void setHORA_FIN(String HORA_FIN) {
        this.HORA_FIN = HORA_FIN;
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
        if (this.TURNO.equals("-1")) {
            return this.TURNO;
        } else {
            return this.TURNO;
        }
    }

}
