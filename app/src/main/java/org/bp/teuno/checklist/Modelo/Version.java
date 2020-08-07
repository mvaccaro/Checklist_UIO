package org.bp.teuno.checklist.Modelo;

public class Version {

    public String ID;
    public String ID_VERSION;
    public String NOMBRE_VERSION;
    public String MENSAJE;
    public String COLABORADORES;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Version(String ID, String ID_VERSION, String NOMBRE_VERSION, String MENSAJE, String COLABORADORES, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_VERSION = ID_VERSION;
        this.NOMBRE_VERSION = NOMBRE_VERSION;
        this.MENSAJE = MENSAJE;
        this.COLABORADORES = COLABORADORES;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Version(String ID, String ID_VERSION, String NOMBRE_VERSION, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_VERSION = ID_VERSION;
        this.NOMBRE_VERSION = NOMBRE_VERSION;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public String getMENSAJE() {
        return MENSAJE;
    }

    public void setMENSAJE(String MENSAJE) {
        this.MENSAJE = MENSAJE;
    }

    public String getCOLABORADORES() {
        return COLABORADORES;
    }

    public void setCOLABORADORES(String COLABORADORES) {
        this.COLABORADORES = COLABORADORES;
    }

    public Version(String ID, String NOMBRE_VERSION) {
        this.ID = ID;
        this.NOMBRE_VERSION = NOMBRE_VERSION;
    }

    public Version(String ID, String ID_VERSION, String NOMBRE_VERSION) {
        this.ID = ID;
        this.ID_VERSION = ID_VERSION;
        this.NOMBRE_VERSION = NOMBRE_VERSION;
    }

    public Version(String ID, String ID_VERSION, String NOMBRE_VERSION, String MENSAJE, String COLABORADORES, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_VERSION = ID_VERSION;
        this.NOMBRE_VERSION = NOMBRE_VERSION;
        this.MENSAJE = MENSAJE;
        this.COLABORADORES = COLABORADORES;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_VERSION() {
        return ID_VERSION;
    }

    public void setID_VERSION(String ID_VERSION) {
        this.ID_VERSION = ID_VERSION;
    }

    public String getNOMBRE_VERSION() {
        return NOMBRE_VERSION;
    }

    public void setNOMBRE_VERSION(String NOMBRE_VERSION) {
        this.NOMBRE_VERSION = NOMBRE_VERSION;
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
        if (this.ID_VERSION.equals("-1")) {
            return this.NOMBRE_VERSION;
        } else {
            return this.NOMBRE_VERSION;
        }
    }

    public Version() {
        /*CONSTRUCTOR VACIO*/
    }

}
