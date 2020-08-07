package org.bp.teuno.checklist.Modelo;

public class Operador {

    public String ID;
    public String ID_OPERADOR;
    public String PRIMER_NOMBRE;
    public String SEGUNDO_NOMBRE;
    public String PRIMER_APELLIDO;
    public String SEGUNDO_APELLIDO;
    public String ID_GRUPO;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Operador(String ID, String ID_OPERADOR, String PRIMER_NOMBRE, String SEGUNDO_NOMBRE, String PRIMER_APELLIDO, String SEGUNDO_APELLIDO, String ID_GRUPO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_OPERADOR = ID_OPERADOR;
        this.PRIMER_NOMBRE = PRIMER_NOMBRE;
        this.SEGUNDO_NOMBRE = SEGUNDO_NOMBRE;
        this.PRIMER_APELLIDO = PRIMER_APELLIDO;
        this.SEGUNDO_APELLIDO = SEGUNDO_APELLIDO;
        this.ID_GRUPO = ID_GRUPO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Operador(String ID, String ID_OPERADOR, String PRIMER_NOMBRE, String SEGUNDO_NOMBRE, String PRIMER_APELLIDO, String SEGUNDO_APELLIDO) {
        this.ID = ID;
        this.ID_OPERADOR = ID_OPERADOR;
        this.PRIMER_NOMBRE = PRIMER_NOMBRE;
        this.SEGUNDO_NOMBRE = SEGUNDO_NOMBRE;
        this.PRIMER_APELLIDO = PRIMER_APELLIDO;
        this.SEGUNDO_APELLIDO = SEGUNDO_APELLIDO;
    }

    public Operador(String ID, String ID_OPERADOR, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_OPERADOR = ID_OPERADOR;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Operador(String ID, String ID_OPERADOR, String PRIMER_NOMBRE, String SEGUNDO_NOMBRE, String PRIMER_APELLIDO, String SEGUNDO_APELLIDO, String ID_GRUPO, String ESTADO, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_OPERADOR = ID_OPERADOR;
        this.PRIMER_NOMBRE = PRIMER_NOMBRE;
        this.SEGUNDO_NOMBRE = SEGUNDO_NOMBRE;
        this.PRIMER_APELLIDO = PRIMER_APELLIDO;
        this.SEGUNDO_APELLIDO = SEGUNDO_APELLIDO;
        this.ID_GRUPO = ID_GRUPO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public Operador() {
        /*CONSTRUCTOR VACIO*/
    }

    public String getID_GRUPO() {
        return ID_GRUPO;
    }

    public void setID_GRUPO(String ID_GRUPO) {
        this.ID_GRUPO = ID_GRUPO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_OPERADOR() {
        return ID_OPERADOR;
    }

    public void setID_OPERADOR(String ID_OPERADOR) {
        this.ID_OPERADOR = ID_OPERADOR;
    }

    public String getPRIMER_NOMBRE() {
        return PRIMER_NOMBRE;
    }

    public void setPRIMER_NOMBRE(String PRIMER_NOMBRE) {
        this.PRIMER_NOMBRE = PRIMER_NOMBRE;
    }

    public String getSEGUNDO_NOMBRE() {
        return SEGUNDO_NOMBRE;
    }

    public void setSEGUNDO_NOMBRE(String SEGUNDO_NOMBRE) {
        this.SEGUNDO_NOMBRE = SEGUNDO_NOMBRE;
    }

    public String getPRIMER_APELLIDO() {
        return PRIMER_APELLIDO;
    }

    public void setPRIMER_APELLIDO(String PRIMER_APELLIDO) {
        this.PRIMER_APELLIDO = PRIMER_APELLIDO;
    }

    public String getSEGUNDO_APELLIDO() {
        return SEGUNDO_APELLIDO;
    }

    public void setSEGUNDO_APELLIDO(String SEGUNDO_APELLIDO) {
        this.SEGUNDO_APELLIDO = SEGUNDO_APELLIDO;
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
