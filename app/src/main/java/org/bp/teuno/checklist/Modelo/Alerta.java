package org.bp.teuno.checklist.Modelo;

import java.sql.Blob;

public class Alerta {
    public String ID;
    public String ID_FILA;
    public String ID_RACK;
    public String UR;
    public String ID_EQUIPO;
    public String ID_TIPO_ALERTA;
    public String ID_COLOR_ALERTA;
    public String COMENTARIO;
    public String TICKET_EXTERNO;
    public byte[] IMAGEN;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Alerta(String ID, String ID_FILA, String ID_RACK, String UR, String ID_EQUIPO, String ID_TIPO_ALERTA, String ID_COLOR_ALERTA, String COMENTARIO, String TICKET_EXTERNO, byte[] IMAGEN, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.ID_EQUIPO = ID_EQUIPO;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.ID_COLOR_ALERTA = ID_COLOR_ALERTA;
        this.COMENTARIO = COMENTARIO;
        this.TICKET_EXTERNO = TICKET_EXTERNO;
        this.IMAGEN = IMAGEN;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Alerta(String ID, String ID_FILA, String ID_RACK, String UR, String ID_EQUIPO, String ID_TIPO_ALERTA, String ID_COLOR_ALERTA, String COMENTARIO, String TICKET_EXTERNO, byte[] IMAGEN, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.ID_EQUIPO = ID_EQUIPO;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.ID_COLOR_ALERTA = ID_COLOR_ALERTA;
        this.COMENTARIO = COMENTARIO;
        this.TICKET_EXTERNO = TICKET_EXTERNO;
        this.IMAGEN = IMAGEN;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public Alerta(String ID, String ID_FILA, String ID_RACK, String UR, String ID_EQUIPO, String ID_TIPO_ALERTA, String ID_COLOR_ALERTA, String COMENTARIO, String TICKET_EXTERNO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.ID_EQUIPO = ID_EQUIPO;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.ID_COLOR_ALERTA = ID_COLOR_ALERTA;
        this.COMENTARIO = COMENTARIO;
        this.TICKET_EXTERNO = TICKET_EXTERNO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Alerta(String ID, String COMENTARIO, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.COMENTARIO = COMENTARIO;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Alerta(String ID, String ID_FILA, String ID_RACK, String UR, String ID_EQUIPO, String ID_TIPO_ALERTA, String ID_COLOR_ALERTA, String COMENTARIO, String TICKET_EXTERNO) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.ID_EQUIPO = ID_EQUIPO;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.ID_COLOR_ALERTA = ID_COLOR_ALERTA;
        this.COMENTARIO = COMENTARIO;
        this.TICKET_EXTERNO = TICKET_EXTERNO;
    }

    public Alerta(String ID, String ID_FILA, String ID_RACK, String UR, String ID_EQUIPO, String ID_TIPO_ALERTA, String ID_COLOR_ALERTA, String COMENTARIO, String TICKET_EXTERNO, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.ID_EQUIPO = ID_EQUIPO;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.ID_COLOR_ALERTA = ID_COLOR_ALERTA;
        this.COMENTARIO = COMENTARIO;
        this.TICKET_EXTERNO = TICKET_EXTERNO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public Alerta(String ID, String ID_FILA, String ID_RACK, String UR, String ID_EQUIPO, String COMENTARIO) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.ID_EQUIPO = ID_EQUIPO;
        this.COMENTARIO = COMENTARIO;
    }

    public Alerta(String ID, String ID_FILA, String ID_RACK, String UR, String ID_EQUIPO, String ID_TIPO_ALERTA, String ID_COLOR_ALERTA, String COMENTARIO, String TICKET_EXTERNO, byte[] IMAGEN) {
        this.ID = ID;
        this.ID_FILA = ID_FILA;
        this.ID_RACK = ID_RACK;
        this.UR = UR;
        this.ID_EQUIPO = ID_EQUIPO;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.ID_COLOR_ALERTA = ID_COLOR_ALERTA;
        this.COMENTARIO = COMENTARIO;
        this.TICKET_EXTERNO = TICKET_EXTERNO;
        this.IMAGEN = IMAGEN;
    }

    public Alerta() {
        /*CONSTRUCTOR VACIO*/
    }

    public byte[] getIMAGEN() {
        return IMAGEN;
    }

    public void setIMAGEN(byte[] IMAGEN) {
        this.IMAGEN = IMAGEN;
    }

    public String getTICKET_EXTERNO() {
        return TICKET_EXTERNO;
    }

    public void setTICKET_EXTERNO(String TICKET_EXTERNO) {
        this.TICKET_EXTERNO = TICKET_EXTERNO;
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

    public String getID_EQUIPO() {
        return ID_EQUIPO;
    }

    public void setID_EQUIPO(String ID_EQUIPO) {
        this.ID_EQUIPO = ID_EQUIPO;
    }

    public String getID_TIPO_ALERTA() {
        return ID_TIPO_ALERTA;
    }

    public void setID_TIPO_ALERTA(String ID_TIPO_ALERTA) {
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
    }

    public String getID_COLOR_ALERTA() {
        return ID_COLOR_ALERTA;
    }

    public void setID_COLOR_ALERTA(String ID_COLOR_ALERTA) {
        this.ID_COLOR_ALERTA = ID_COLOR_ALERTA;
    }

    public String getCOMENTARIO() {
        return COMENTARIO;
    }

    public void setCOMENTARIO(String COMENTARIO) {
        this.COMENTARIO = COMENTARIO;
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
