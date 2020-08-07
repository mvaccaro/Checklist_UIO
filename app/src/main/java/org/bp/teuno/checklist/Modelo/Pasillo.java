package org.bp.teuno.checklist.Modelo;

public class Pasillo {

    public String ID;
    public String ID_PASILLO;
    public String NOMBRE_PASILLO;
    public String TIPO_PASILLO;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Pasillo(String ID, String ID_PASILLO, String NOMBRE_PASILLO, String TIPO_PASILLO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_PASILLO = ID_PASILLO;
        this.NOMBRE_PASILLO = NOMBRE_PASILLO;
        this.TIPO_PASILLO = TIPO_PASILLO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Pasillo(String ID, String ID_PASILLO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_PASILLO = ID_PASILLO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Pasillo(String ID, String ID_PASILLO, String NOMBRE_PASILLO, String TIPO_PASILLO) {
        this.ID = ID;
        this.ID_PASILLO = ID_PASILLO;
        this.NOMBRE_PASILLO = NOMBRE_PASILLO;
        this.TIPO_PASILLO = TIPO_PASILLO;
    }

    public String getTIPO_PASILLO() {
        return TIPO_PASILLO;
    }

    public void setTIPO_PASILLO(String TIPO_PASILLO) {
        this.TIPO_PASILLO = TIPO_PASILLO;
    }

    public Pasillo(String ID, String NOMBRE_PASILLO) {
        this.ID = ID;
        this.NOMBRE_PASILLO = NOMBRE_PASILLO;
    }

    public Pasillo(String ID, String ID_PASILLO, String NOMBRE_PASILLO, String TIPO_PASILLO, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_PASILLO = ID_PASILLO;
        this.NOMBRE_PASILLO = NOMBRE_PASILLO;
        this.TIPO_PASILLO = TIPO_PASILLO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_PASILLO() {
        return ID_PASILLO;
    }

    public void setID_PASILLO(String ID_PASILLO) {
        this.ID_PASILLO = ID_PASILLO;
    }

    public String getNOMBRE_PASILLO() {
        return NOMBRE_PASILLO;
    }

    public void setNOMBRE_PASILLO(String NOMBRE_PASILLO) {
        this.NOMBRE_PASILLO = NOMBRE_PASILLO;
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
        if (this.ID_PASILLO.equals("-1")) {
            return this.NOMBRE_PASILLO;
        } else {
            return this.NOMBRE_PASILLO;
        }
    }

    public Pasillo() {
        /*CONSTRUCTOR VACIO*/
    }

}
