package org.bp.teuno.checklist.Modelo;

public class Parametros {

    public String ID;
    public String TEMP_PAS_MINIMA;
    public String TEMP_PAS_MAXIMA;
    public String HUME_PAS_MINIMA;
    public String HUME_PAS_MAXIMA;
    public String TEMP_SUM_MINIMA;
    public String TEMP_SUM_MAXIMA;
    public String TEMP_RET_MINIMA;
    public String TEMP_RET_MAXIMA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public Parametros(String ID, String TEMP_PAS_MINIMA, String TEMP_PAS_MAXIMA, String HUME_PAS_MINIMA, String HUME_PAS_MAXIMA, String TEMP_SUM_MINIMA, String TEMP_SUM_MAXIMA, String TEMP_RET_MINIMA, String TEMP_RET_MAXIMA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.TEMP_PAS_MINIMA = TEMP_PAS_MINIMA;
        this.TEMP_PAS_MAXIMA = TEMP_PAS_MAXIMA;
        this.HUME_PAS_MINIMA = HUME_PAS_MINIMA;
        this.HUME_PAS_MAXIMA = HUME_PAS_MAXIMA;
        this.TEMP_SUM_MINIMA = TEMP_SUM_MINIMA;
        this.TEMP_SUM_MAXIMA = TEMP_SUM_MAXIMA;
        this.TEMP_RET_MINIMA = TEMP_RET_MINIMA;
        this.TEMP_RET_MAXIMA = TEMP_RET_MAXIMA;
        this.ESTADO = ESTADO;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.USUARIO_MODIFICA = USUARIO_MODIFICA;
        this.FECHA_INGRESO = FECHA_INGRESO;
        this.FECHA_MODIFICACION = FECHA_MODIFICACION;
    }

    public Parametros(String ID, String TEMP_PAS_MINIMA, String TEMP_PAS_MAXIMA, String HUME_PAS_MINIMA, String HUME_PAS_MAXIMA, String TEMP_SUM_MINIMA, String TEMP_SUM_MAXIMA, String TEMP_RET_MINIMA, String TEMP_RET_MAXIMA, String USUARIO_INGRESA, String FECHA_INGRESO) {
        this.ID = ID;
        this.TEMP_PAS_MINIMA = TEMP_PAS_MINIMA;
        this.TEMP_PAS_MAXIMA = TEMP_PAS_MAXIMA;
        this.HUME_PAS_MINIMA = HUME_PAS_MINIMA;
        this.HUME_PAS_MAXIMA = HUME_PAS_MAXIMA;
        this.TEMP_SUM_MINIMA = TEMP_SUM_MINIMA;
        this.TEMP_SUM_MAXIMA = TEMP_SUM_MAXIMA;
        this.TEMP_RET_MINIMA = TEMP_RET_MINIMA;
        this.TEMP_RET_MAXIMA = TEMP_RET_MAXIMA;
        this.USUARIO_INGRESA = USUARIO_INGRESA;
        this.FECHA_INGRESO = FECHA_INGRESO;
    }


    public Parametros() {
    }

    public String getHUME_PAS_MINIMA() {
        return HUME_PAS_MINIMA;
    }

    public void setHUME_PAS_MINIMA(String HUME_PAS_MINIMA) {
        this.HUME_PAS_MINIMA = HUME_PAS_MINIMA;
    }

    public Parametros(String ID, String TEMP_PAS_MINIMA, String TEMP_PAS_MAXIMA, String HUME_PAS_MINIMA, String HUME_PAS_MAXIMA, String TEMP_SUM_MINIMA, String TEMP_SUM_MAXIMA, String TEMP_RET_MINIMA, String TEMP_RET_MAXIMA, String ESTADO) {
        this.ID = ID;
        this.TEMP_PAS_MINIMA = TEMP_PAS_MINIMA;
        this.TEMP_PAS_MAXIMA = TEMP_PAS_MAXIMA;
        this.HUME_PAS_MINIMA = HUME_PAS_MINIMA;
        this.HUME_PAS_MAXIMA = HUME_PAS_MAXIMA;
        this.TEMP_SUM_MINIMA = TEMP_SUM_MINIMA;
        this.TEMP_SUM_MAXIMA = TEMP_SUM_MAXIMA;
        this.TEMP_RET_MINIMA = TEMP_RET_MINIMA;
        this.TEMP_RET_MAXIMA = TEMP_RET_MAXIMA;
        this.ESTADO = ESTADO;
    }

    public Parametros(String ID, String TEMP_PAS_MINIMA, String TEMP_PAS_MAXIMA, String HUME_PAS_MINIMA, String HUME_PAS_MAXIMA, String TEMP_SUM_MINIMA, String TEMP_SUM_MAXIMA, String TEMP_RET_MINIMA, String TEMP_RET_MAXIMA) {
        this.ID = ID;
        this.TEMP_PAS_MINIMA = TEMP_PAS_MINIMA;
        this.TEMP_PAS_MAXIMA = TEMP_PAS_MAXIMA;
        this.HUME_PAS_MINIMA = HUME_PAS_MINIMA;
        this.HUME_PAS_MAXIMA = HUME_PAS_MAXIMA;
        this.TEMP_SUM_MINIMA = TEMP_SUM_MINIMA;
        this.TEMP_SUM_MAXIMA = TEMP_SUM_MAXIMA;
        this.TEMP_RET_MINIMA = TEMP_RET_MINIMA;
        this.TEMP_RET_MAXIMA = TEMP_RET_MAXIMA;
    }

    public String getHUME_PAS_MAXIMA() {
        return HUME_PAS_MAXIMA;
    }

    public void setHUME_PAS_MAXIMA(String HUME_PAS_MAXIMA) {
        this.HUME_PAS_MAXIMA = HUME_PAS_MAXIMA;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTEMP_PAS_MINIMA() {
        return TEMP_PAS_MINIMA;
    }

    public void setTEMP_PAS_MINIMA(String TEMP_PAS_MINIMA) {
        this.TEMP_PAS_MINIMA = TEMP_PAS_MINIMA;
    }

    public String getTEMP_PAS_MAXIMA() {
        return TEMP_PAS_MAXIMA;
    }

    public void setTEMP_PAS_MAXIMA(String TEMP_PAS_MAXIMA) {
        this.TEMP_PAS_MAXIMA = TEMP_PAS_MAXIMA;
    }

    public String getTEMP_SUM_MINIMA() {
        return TEMP_SUM_MINIMA;
    }

    public void setTEMP_SUM_MINIMA(String TEMP_SUM_MINIMA) {
        this.TEMP_SUM_MINIMA = TEMP_SUM_MINIMA;
    }

    public String getTEMP_SUM_MAXIMA() {
        return TEMP_SUM_MAXIMA;
    }

    public void setTEMP_SUM_MAXIMA(String TEMP_SUM_MAXIMA) {
        this.TEMP_SUM_MAXIMA = TEMP_SUM_MAXIMA;
    }

    public String getTEMP_RET_MINIMA() {
        return TEMP_RET_MINIMA;
    }

    public void setTEMP_RET_MINIMA(String TEMP_RET_MINIMA) {
        this.TEMP_RET_MINIMA = TEMP_RET_MINIMA;
    }

    public String getTEMP_RET_MAXIMA() {
        return TEMP_RET_MAXIMA;
    }

    public void setTEMP_RET_MAXIMA(String TEMP_RET_MAXIMA) {
        this.TEMP_RET_MAXIMA = TEMP_RET_MAXIMA;
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
