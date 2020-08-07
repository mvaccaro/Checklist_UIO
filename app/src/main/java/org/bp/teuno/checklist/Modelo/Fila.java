package org.bp.teuno.checklist.Modelo;

public class Fila {

    public String ID;
    public String ID_FILA;
    public String NOMBRE_FILA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Fila(String ID, String ID_FILA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Fila(String ID, String ID_FILA, String NOMBRE_FILA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.NOMBRE_FILA = NOMBRE_FILA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Fila(String ID, String NOMBRE_FILA) {
        this.ID = ID;
        this.NOMBRE_FILA = NOMBRE_FILA;
    }

    public Fila(String ID, String ID_FILA, String NOMBRE_FILA, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.NOMBRE_FILA = NOMBRE_FILA;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public Fila(String ID, String ID_FILA, String NOMBRE_FILA) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.NOMBRE_FILA = NOMBRE_FILA;
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

    public String getNOMBRE_FILA() {
        return NOMBRE_FILA;
    }

    public void setNOMBRE_FILA(String NOMBRE_FILA) {
        this.NOMBRE_FILA = NOMBRE_FILA;
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
        if (this.ID_FILA.equals("-1")) {
            return this.NOMBRE_FILA;
        } else {
            return this.NOMBRE_FILA;
        }
    }

    public Fila() {
        /*CONSTRUCTOR VACIO*/
    }

}
