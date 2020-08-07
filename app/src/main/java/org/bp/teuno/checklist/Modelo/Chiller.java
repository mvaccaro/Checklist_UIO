package org.bp.teuno.checklist.Modelo;

public class Chiller {

    public String ID;
    public String ID_CHILLER;
    public String NOMBRE_CHILLER;
    public String MARCA;
    public String MODELO;
    public String SERIE;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Chiller(String ID, String ID_CHILLER, String NOMBRE_CHILLER, String MARCA, String MODELO, String SERIE, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_CHILLER = ID_CHILLER;
        this.NOMBRE_CHILLER = NOMBRE_CHILLER;
        this.MARCA = MARCA;
        this.MODELO = MODELO;
        this.SERIE = SERIE;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Chiller(String ID, String ID_CHILLER, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_CHILLER = ID_CHILLER;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Chiller(String ID, String NOMBRE_CHILLER) {
        this.ID = ID;
        this.NOMBRE_CHILLER = NOMBRE_CHILLER;
    }

    public Chiller(String ID, String ID_CHILLER, String NOMBRE_CHILLER) {
        this.ID = ID;
        this.ID_CHILLER = ID_CHILLER;
        this.NOMBRE_CHILLER = NOMBRE_CHILLER;
    }

    public String getMARCA() {
        return MARCA;
    }

    public void setMARCA(String MARCA) {
        this.MARCA = MARCA;
    }

    public String getMODELO() {
        return MODELO;
    }

    public void setMODELO(String MODELO) {
        this.MODELO = MODELO;
    }

    public String getSERIE() {
        return SERIE;
    }

    public void setSERIE(String SERIE) {
        this.SERIE = SERIE;
    }

    public Chiller(String ID, String ID_CHILLER, String NOMBRE_CHILLER, String MARCA, String MODELO, String SERIE, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_CHILLER = ID_CHILLER;
        this.NOMBRE_CHILLER = NOMBRE_CHILLER;
        this.MARCA = MARCA;
        this.MODELO = MODELO;
        this.SERIE = SERIE;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_CHILLER() {
        return ID_CHILLER;
    }

    public void setID_CHILLER(String ID_CHILLER) {
        this.ID_CHILLER = ID_CHILLER;
    }

    public String getNOMBRE_CHILLER() {
        return NOMBRE_CHILLER;
    }

    public void setNOMBRE_CHILLER(String NOMBRE_CHILLER) {
        this.NOMBRE_CHILLER = NOMBRE_CHILLER;
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
        if (this.ID_CHILLER.equals("-1")) {
            return this.NOMBRE_CHILLER;
        } else {
            return this.NOMBRE_CHILLER;
        }
    }

    public Chiller() {
        /*CONSTRUCTOR VACIO*/
    }

}
