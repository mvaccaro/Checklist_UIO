package org.bp.teuno.checklist.Modelo;

public class A77_Aire {

    public String ID;
    public String ID_PASILLO;
    public String A1;
    public String A2;
    public String A3;
    public String B1;
    public String B2;
    public String B3;
    public String C1;
    public String C2;
    public String C3;
    public String HORA_INGRESO;
    public String HORA_SALIDA;
    public String ID_TURNO;
    public String RONDA;
    public String ESTADO;
    public String USUARIO_INGRESA;
    public String USUARIO_MODIFICA;
    public String FECHA_INGRESO;
    public String FECHA_MODIFICACION;

    public A77_Aire(String ID, String ID_PASILLO, String a1, String a2, String a3, String b1, String b2, String b3, String c1, String c2, String c3, String HORA_INGRESO, String HORA_SALIDA, String ID_TURNO, String RONDA, String ESTADO, String USUARIO_INGRESA, String USUARIO_MODIFICA, String FECHA_INGRESO, String FECHA_MODIFICACION) {
        this.ID = ID;
        this.ID_PASILLO = ID_PASILLO;
        A1 = a1;
        A2 = a2;
        A3 = a3;
        B1 = b1;
        B2 = b2;
        B3 = b3;
        C1 = c1;
        C2 = c2;
        C3 = c3;
        this.HORA_INGRESO = HORA_INGRESO;
        this.HORA_SALIDA = HORA_SALIDA;
        this.ID_TURNO = ID_TURNO;
        this.RONDA = RONDA;
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

    public String getID_PASILLO() {
        return ID_PASILLO;
    }

    public void setID_PASILLO(String ID_PASILLO) {
        this.ID_PASILLO = ID_PASILLO;
    }

    public String getA1() {
        return A1;
    }

    public void setA1(String a1) {
        A1 = a1;
    }

    public String getA2() {
        return A2;
    }

    public void setA2(String a2) {
        A2 = a2;
    }

    public String getA3() {
        return A3;
    }

    public void setA3(String a3) {
        A3 = a3;
    }

    public String getB1() {
        return B1;
    }

    public void setB1(String b1) {
        B1 = b1;
    }

    public String getB2() {
        return B2;
    }

    public void setB2(String b2) {
        B2 = b2;
    }

    public String getB3() {
        return B3;
    }

    public void setB3(String b3) {
        B3 = b3;
    }

    public String getC1() {
        return C1;
    }

    public void setC1(String c1) {
        C1 = c1;
    }

    public String getC2() {
        return C2;
    }

    public void setC2(String c2) {
        C2 = c2;
    }

    public String getC3() {
        return C3;
    }

    public void setC3(String c3) {
        C3 = c3;
    }

    public String getHORA_INGRESO() {
        return HORA_INGRESO;
    }

    public void setHORA_INGRESO(String HORA_INGRESO) {
        this.HORA_INGRESO = HORA_INGRESO;
    }

    public String getHORA_SALIDA() {
        return HORA_SALIDA;
    }

    public void setHORA_SALIDA(String HORA_SALIDA) {
        this.HORA_SALIDA = HORA_SALIDA;
    }

    public String getID_TURNO() {
        return ID_TURNO;
    }

    public void setID_TURNO(String ID_TURNO) {
        this.ID_TURNO = ID_TURNO;
    }

    public String getRONDA() {
        return RONDA;
    }

    public void setRONDA(String RONDA) {
        this.RONDA = RONDA;
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