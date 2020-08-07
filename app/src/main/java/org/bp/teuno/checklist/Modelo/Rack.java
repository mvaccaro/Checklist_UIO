package org.bp.teuno.checklist.Modelo;

public class Rack {
    public String ID;
    public String NOMBRE_RACK;
    public String ID_FILA;
    public String ID_UR;
    public String ID_CLIENTE;
    public String MARCA;
    public String MODELO;
    public String SERIE;
    public String CODIGO_BANCO;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Rack(String ID, String NOMBRE_RACK, String ID_FILA, String ID_UR, String ID_CLIENTE, String MARCA, String MODELO, String SERIE, String CODIGO_BANCO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.NOMBRE_RACK = NOMBRE_RACK;
        this.ID_FILA = ID_FILA;
        this.ID_UR = ID_UR;
        this.ID_CLIENTE = ID_CLIENTE;
        this.MARCA = MARCA;
        this.MODELO = MODELO;
        this.SERIE = SERIE;
        this.CODIGO_BANCO = CODIGO_BANCO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Rack(String ID, String NOMBRE_RACK, String ID_FILA, String ID_UR) {
        this.ID = ID;
        this.NOMBRE_RACK = NOMBRE_RACK;
        this.ID_FILA = ID_FILA;
        this.ID_UR = ID_UR;
    }

    public Rack(String ID, String NOMBRE_RACK, String ID_FILA, String ID_UR, String ID_CLIENTE) {
        this.ID = ID;
        this.NOMBRE_RACK = NOMBRE_RACK;
        this.ID_FILA = ID_FILA;
        this.ID_UR = ID_UR;
        this.ID_CLIENTE = ID_CLIENTE;
    }

    public Rack(String ID, String NOMBRE_RACK, String ID_FILA, String ID_UR, String ID_CLIENTE, String MARCA, String MODELO, String SERIE, String CODIGO_BANCO) {
        this.ID = ID;
        this.NOMBRE_RACK = NOMBRE_RACK;
        this.ID_FILA = ID_FILA;
        this.ID_UR = ID_UR;
        this.ID_CLIENTE = ID_CLIENTE;
        this.MARCA = MARCA;
        this.MODELO = MODELO;
        this.SERIE = SERIE;
        this.CODIGO_BANCO = CODIGO_BANCO;
    }

    public Rack(String ID, String NOMBRE_RACK, String ID_FILA, String ID_UR, String ID_CLIENTE, String MARCA, String MODELO, String SERIE, String CODIGO_BANCO, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.NOMBRE_RACK = NOMBRE_RACK;
        this.ID_FILA = ID_FILA;
        this.ID_UR = ID_UR;
        this.ID_CLIENTE = ID_CLIENTE;
        this.MARCA = MARCA;
        this.MODELO = MODELO;
        this.SERIE = SERIE;
        this.CODIGO_BANCO = CODIGO_BANCO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }



    public Rack() {
        /*CONSTRUCTOR VACIO*/
    }

    public Rack(String ID, String NOMBRE_RACK) {
        this.ID = ID;
        this.NOMBRE_RACK = NOMBRE_RACK;
    }

    public Rack(String ID, String NOMBRE_RACK, String ID_UR) {
        this.ID = ID;
        this.NOMBRE_RACK = NOMBRE_RACK;
        this.ID_UR = ID_UR;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNOMBRE_RACK() {
        return NOMBRE_RACK;
    }

    public void setNOMBRE_RACK(String NOMBRE_RACK) {
        this.NOMBRE_RACK = NOMBRE_RACK;
    }

    public String getID_FILA() {
        return ID_FILA;
    }

    public void setID_FILA(String ID_FILA) {
        this.ID_FILA = ID_FILA;
    }

    public String getID_UR() {
        return ID_UR;
    }

    public void setID_UR(String ID_UR) {
        this.ID_UR = ID_UR;
    }

    public String getID_CLIENTE() {
        return ID_CLIENTE;
    }

    public void setID_CLIENTE(String ID_CLIENTE) {
        this.ID_CLIENTE = ID_CLIENTE;
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

    public String getCODIGO_BANCO() {
        return CODIGO_BANCO;
    }

    public void setCODIGO_BANCO(String CODIGO_BANCO) {
        this.CODIGO_BANCO = CODIGO_BANCO;
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
        if (this.NOMBRE_RACK.equals("-1")) {
            return "";
        } else {
            return this.NOMBRE_RACK;
        }
    }

}
