package org.bp.teuno.checklist.Modelo;

public class Tipo_Pasillo {

    public String ID;
    public String ID_TIPO_PASILLO;
    public String NOMBRE_TIPO_PASILLO;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Tipo_Pasillo(String ID, String ID_TIPO_PASILLO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_TIPO_PASILLO = ID_TIPO_PASILLO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Tipo_Pasillo(String ID, String ID_TIPO_PASILLO, String NOMBRE_TIPO_PASILLO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_TIPO_PASILLO = ID_TIPO_PASILLO;
        this.NOMBRE_TIPO_PASILLO = NOMBRE_TIPO_PASILLO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Tipo_Pasillo(String ID, String NOMBRE_TIPO_PASILLO) {
        this.ID = ID;
        this.NOMBRE_TIPO_PASILLO = NOMBRE_TIPO_PASILLO;
    }

    public Tipo_Pasillo(String ID, String ID_TIPO_PASILLO, String NOMBRE_TIPO_PASILLO) {
        this.ID = ID;
        this.ID_TIPO_PASILLO = ID_TIPO_PASILLO;
        this.NOMBRE_TIPO_PASILLO = NOMBRE_TIPO_PASILLO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_TIPO_PASILLO() {
        return ID_TIPO_PASILLO;
    }

    public void setID_TIPO_PASILLO(String ID_TIPO_PASILLO) {
        this.ID_TIPO_PASILLO = ID_TIPO_PASILLO;
    }

    public String getNOMBRE_TIPO_PASILLO() {
        return NOMBRE_TIPO_PASILLO;
    }

    public void setNOMBRE_TIPO_PASILLO(String NOMBRE_TIPO_PASILLO) {
        this.NOMBRE_TIPO_PASILLO = NOMBRE_TIPO_PASILLO;
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
        if (this.ID_TIPO_PASILLO.equals("-1")) {
            return this.NOMBRE_TIPO_PASILLO;
        } else {
            return this.NOMBRE_TIPO_PASILLO;
        }
    }

    public Tipo_Pasillo() {
        /*CONSTRUCTOR VACIO*/
    }

}
