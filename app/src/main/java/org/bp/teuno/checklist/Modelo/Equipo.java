package org.bp.teuno.checklist.Modelo;

public class Equipo {
    public String ID;
    public String ID_FILA;
    public String ID_RACK;
    public String UR;
    public String NOMBRE_EQUIPO;
    public String MARCA;
    public String MODELO;
    public String SERIE;
    public String CODIGO_BANCO;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Equipo(String ID, String ID_FILA, String ID_RACK, String UR, String NOMBRE_EQUIPO, String MARCA, String MODELO, String SERIE, String CODIGO_BANCO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.NOMBRE_EQUIPO = NOMBRE_EQUIPO;
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

    public Equipo(String ID, String NOMBRE_EQUIPO, String MARCA, String MODELO, String SERIE) {
        this.ID = ID;
        this.NOMBRE_EQUIPO = NOMBRE_EQUIPO;
        this.MARCA = MARCA;
        this.MODELO = MODELO;
        this.SERIE = SERIE;
    }

    public Equipo(String ID, String ID_FILA, String ID_RACK, String UR, String NOMBRE_EQUIPO, String MARCA, String MODELO, String SERIE, String CODIGO_BANCO) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.NOMBRE_EQUIPO = NOMBRE_EQUIPO;
        this.MARCA = MARCA;
        this.MODELO = MODELO;
        this.SERIE = SERIE;
        this.CODIGO_BANCO = CODIGO_BANCO;
    }

    public Equipo(String ID, String ID_FILA, String ID_RACK, String UR, String NOMBRE_EQUIPO, String MARCA, String MODELO, String SERIE, String CODIGO_BANCO, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.NOMBRE_EQUIPO = NOMBRE_EQUIPO;
        this.MARCA = MARCA;
        this.MODELO = MODELO;
        this.SERIE = SERIE;
        this.CODIGO_BANCO = CODIGO_BANCO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public Equipo(String ID, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
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

    public String getID_FILA() {
        return ID_FILA;
    }

    public void setID_FILA(String ID_FILA) {
        this.ID_FILA = ID_FILA;
    }

    public String getID_RACK() {
        return ID_RACK;
    }

    public void setID_RACK(String ID_RACK) {
        this.ID_RACK = ID_RACK;
    }

    public String getUR() {
        return UR;
    }

    public void setUR(String UR) {
        this.UR = UR;
    }

    public String getNOMBRE_EQUIPO() {
        return NOMBRE_EQUIPO;
    }

    public void setNOMBRE_EQUIPO(String NOMBRE_EQUIPO) {
        this.NOMBRE_EQUIPO = NOMBRE_EQUIPO;
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

    public Equipo() {
        /*CONSTRUCTOR VACIO*/
    }

}
