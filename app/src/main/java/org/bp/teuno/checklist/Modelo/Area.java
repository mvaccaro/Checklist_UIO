package org.bp.teuno.checklist.Modelo;

public class Area {

    public String ID;
    public String ID_AREA;
    public String NOMBRE_AREA;
    public String TIPO_AREA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Area(String ID, String ID_AREA, String NOMBRE_AREA, String TIPO_AREA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_AREA = ID_AREA;
        this.NOMBRE_AREA = NOMBRE_AREA;
        this.TIPO_AREA = TIPO_AREA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Area(String ID, String ID_AREA, String NOMBRE_AREA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_AREA = ID_AREA;
        this.NOMBRE_AREA = NOMBRE_AREA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Area(String ID, String ID_AREA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_AREA = ID_AREA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public String getTIPO_AREA() {
        return TIPO_AREA;
    }

    public void setTIPO_AREA(String TIPO_AREA) {
        this.TIPO_AREA = TIPO_AREA;
    }

    public Area(String ID, String NOMBRE_AREA) {
        this.ID = ID;
        this.NOMBRE_AREA = NOMBRE_AREA;
    }

    public Area(String ID, String ID_AREA, String NOMBRE_AREA, String TIPO_AREA) {
        this.ID = ID;
        this.ID_AREA = ID_AREA;
        this.NOMBRE_AREA = NOMBRE_AREA;
        this.TIPO_AREA = TIPO_AREA;
    }

    public Area(String ID, String ID_AREA, String NOMBRE_AREA, String TIPO_AREA, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_AREA = ID_AREA;
        this.NOMBRE_AREA = NOMBRE_AREA;
        this.TIPO_AREA = TIPO_AREA;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_AREA() {
        return ID_AREA;
    }

    public void setID_AREA(String ID_AREA) {
        this.ID_AREA = ID_AREA;
    }

    public String getNOMBRE_AREA() {
        return NOMBRE_AREA;
    }

    public void setNOMBRE_AREA(String NOMBRE_AREA) {
        this.NOMBRE_AREA = NOMBRE_AREA;
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
        if (this.ID_AREA.equals("-1")) {
            return this.NOMBRE_AREA;
        } else {
            return this.NOMBRE_AREA;
        }
    }

    public Area() {
        /*CONSTRUCTOR VACIO*/
    }

}
