package org.bp.teuno.checklist.Modelo;

public class Tipo_Alerta {

    public String ID;
    public String ID_TIPO_ALERTA;
    public String NOMBRE_TIPO_ALERTA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Tipo_Alerta(String ID, String ID_TIPO_ALERTA, String NOMBRE_TIPO_ALERTA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.NOMBRE_TIPO_ALERTA = NOMBRE_TIPO_ALERTA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Tipo_Alerta(String ID, String ID_TIPO_ALERTA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Tipo_Alerta(String ID, String ID_TIPO_ALERTA, String NOMBRE_TIPO_ALERTA, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.NOMBRE_TIPO_ALERTA = NOMBRE_TIPO_ALERTA;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public Tipo_Alerta(String ID, String ID_TIPO_ALERTA, String NOMBRE_TIPO_ALERTA) {
        this.ID = ID;
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
        this.NOMBRE_TIPO_ALERTA = NOMBRE_TIPO_ALERTA;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_TIPO_ALERTA() {
        return ID_TIPO_ALERTA;
    }

    public void setID_TIPO_ALERTA(String ID_TIPO_ALERTA) {
        this.ID_TIPO_ALERTA = ID_TIPO_ALERTA;
    }

    public String getNOMBRE_TIPO_ALERTA() {
        return NOMBRE_TIPO_ALERTA;
    }

    public void setNOMBRE_TIPO_ALERTA(String NOMBRE_TIPO_ALERTA) {
        this.NOMBRE_TIPO_ALERTA = NOMBRE_TIPO_ALERTA;
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
        if (this.ID_TIPO_ALERTA.equals("-1")) {
            return this.NOMBRE_TIPO_ALERTA;
        } else {
            return this.NOMBRE_TIPO_ALERTA;
        }
    }

    public Tipo_Alerta() {
        /*CONSTRUCTOR VACIO*/
    }

}
