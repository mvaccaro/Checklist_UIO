package org.bp.teuno.checklist.Modelo;

public class Cantidad_Ur {

    public String ID;
    public String ID_UR;
    public String CANTIDAD_UR;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Cantidad_Ur(String ID, String ID_UR, String CANTIDAD_UR, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_UR = ID_UR;
        this.CANTIDAD_UR = CANTIDAD_UR;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Cantidad_Ur(String ID, String ID_UR, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_UR = ID_UR;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Cantidad_Ur(String ID, String ID_UR, String CANTIDAD_UR, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.ID_UR = ID_UR;
        this.CANTIDAD_UR = CANTIDAD_UR;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }

    public Cantidad_Ur(String ID, String ID_UR, String CANTIDAD_UR) {
        this.ID = ID;
        this.ID_UR = ID_UR;
        this.CANTIDAD_UR = CANTIDAD_UR;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_UR() {
        return ID_UR;
    }

    public void setID_UR(String ID_UR) {
        this.ID_UR = ID_UR;
    }

    public String getCANTIDAD_UR() {
        return CANTIDAD_UR;
    }

    public void setCANTIDAD_UR(String CANTIDAD_UR) {
        this.CANTIDAD_UR = CANTIDAD_UR;
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
        if (this.ID_UR.equals("-1")) {
            return this.CANTIDAD_UR;
        } else {
            return this.CANTIDAD_UR;
        }
    }

    public Cantidad_Ur() {
        /*CONSTRUCTOR VACIO*/
    }

}
