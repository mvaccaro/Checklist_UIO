package org.bp.teuno.checklist.Modelo;

public class Tipo_Area {

    public String ID;
    public String ID_TIPO_AREA;
    public String NOMBRE_TIPO_AREA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Tipo_Area(String ID, String ID_TIPO_AREA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_TIPO_AREA = ID_TIPO_AREA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Tipo_Area(String ID, String ID_TIPO_AREA, String NOMBRE_TIPO_AREA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_TIPO_AREA = ID_TIPO_AREA;
        this.NOMBRE_TIPO_AREA = NOMBRE_TIPO_AREA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Tipo_Area(String ID, String NOMBRE_TIPO_AREA) {
        this.ID = ID;
        this.NOMBRE_TIPO_AREA = NOMBRE_TIPO_AREA;
    }

    public Tipo_Area(String ID, String ID_TIPO_AREA, String NOMBRE_TIPO_AREA) {
        this.ID = ID;
        this.ID_TIPO_AREA = ID_TIPO_AREA;
        this.NOMBRE_TIPO_AREA = NOMBRE_TIPO_AREA;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_TIPO_AREA() {
        return ID_TIPO_AREA;
    }

    public void setID_TIPO_AREA(String ID_TIPO_AREA) {
        this.ID_TIPO_AREA = ID_TIPO_AREA;
    }

    public String getNOMBRE_TIPO_AREA() {
        return NOMBRE_TIPO_AREA;
    }

    public void setNOMBRE_TIPO_AREA(String NOMBRE_TIPO_AREA) {
        this.NOMBRE_TIPO_AREA = NOMBRE_TIPO_AREA;
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
        if (this.ID_TIPO_AREA.equals("-1")) {
            return this.NOMBRE_TIPO_AREA;
        } else {
            return this.NOMBRE_TIPO_AREA;
        }
    }

    public Tipo_Area() {
        /*CONSTRUCTOR VACIO*/
    }

}
