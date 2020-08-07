package org.bp.teuno.checklist.Controlador;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.bp.teuno.checklist.Modelo.A34_Busway;
import org.bp.teuno.checklist.Modelo.A34_Tanque_Ecaro;
import org.bp.teuno.checklist.Modelo.A34_Uma;
import org.bp.teuno.checklist.Modelo.A34_Ups;
import org.bp.teuno.checklist.Modelo.A34_Xcom;
import org.bp.teuno.checklist.Modelo.Area;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_A34_Uma;
import org.bp.teuno.checklist.SQLite.IT_Area;
import org.bp.teuno.checklist.SQLite.IT_Turno;
import org.bp.teuno.checklist.UI.Crud_A34;
import org.bp.teuno.checklist.UI.Crud_Area;
import org.bp.teuno.checklist.UI.Crud_Inicio_Sesion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import org.bp.teuno.checklist.Modelo.A34_Chiller;
//import org.bp.teuno.checklist.UI.Crud_A34_Chiller;


public class C_A34 extends AppCompatActivity {

    private final long interval = 1 * 1000;
    String Temperatura = "", Humedad = "", Ruido_A = "", Ruido_B = "", Ruido_C = "", Hora_Ingreso = "", Hora_Salida = "", Turno = "", Evento = "", Descripcion = "";
    int Ronda = 0;
    String Tdo_Uma = "", Tdo_Ups = "", Tdo_Busway = "", Tdo_Tanque = "", Tdo_Xcom = "";
    String Tdo_Area = "", Id_Area = "", Nombre_Area = "", Tdo_Tipo_Area = "", L1 = "", L2 = "", L3 = "", Bateria = "", Voltaje = "", Corriente = "", Potencia = "";
    LinearLayout lUMA, lUPSA, lUPSB, lBUSWAY, lTANQUE, lXCOM;
    TextView txMensaje1, txMensaje2, txMensaje3, txMensaje4, txMensaje5, txMensaje6;
    LayoutParams lFilasAreasParams, etAreaParams, etTemperaturaParams, etHumedadParams, etVoltajeParams, etCorrienteParams, etPotenciaParams, swPDUParams, etL1Params, etL2Params, etL3Params, etBateriaParams, swColorParams, swMedicionParams, swAlertaParams;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabGuardar, fabA71, fabA56, fabA58;
    String isMenuA56 = "1";
    ArrayList<A34_Uma> ListaA34Uma = new ArrayList<A34_Uma>();
    ArrayList<A34_Ups> ListaA34Ups = new ArrayList<A34_Ups>();
    ArrayList<A34_Busway> ListaA34Busway = new ArrayList<A34_Busway>();
    ArrayList<A34_Tanque_Ecaro> ListaA34Tanque = new ArrayList<A34_Tanque_Ecaro>();
    ArrayList<A34_Xcom> ListaA34Xcom = new ArrayList<A34_Xcom>();
    ArrayList<Area> ListaUma = new ArrayList<Area>();
    ArrayList<Area> ListaUpsA = new ArrayList<Area>();
    ArrayList<Area> ListaUpsB = new ArrayList<Area>();
    ArrayList<Area> ListaBusway = new ArrayList<Area>();
    ArrayList<Area> ListaTanque = new ArrayList<Area>();
    ArrayList<Area> ListaXcom = new ArrayList<Area>();
    ArrayList<String> ListaRonda = new ArrayList<String>();
    Cursor T_A34_Uma, T_Evento, T_Pasillo, T_Area;
    Crud_A34 Datos1;
    String Id_Operador = "", Nombre_Operador, Tdo_Turno, Turno_Operador;
    CountDownTimer countDownTimer;
    int bandera = 0;
    private String Tdo_Grupo_1;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME
    private CoordinatorLayout mLinearLayout;
    private Context mContext;
    private Activity mActivity;
    private PopupWindow mPopupWindow;
    Crud_Inicio_Sesion DATOS;
    Cursor T_Turno;
    String Tdo_Turno_1;

    public static int getThemeAccentColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Double.parseDouble(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    public static String convertNumeric(String cadena) {
        String conversion;
        double numero;
        try {
            numero = Double.parseDouble(cadena);
            conversion = String.valueOf(numero);
        } catch (NumberFormatException excepcion) {
            conversion = "";
        }
        return conversion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_a34);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        mContext = getApplicationContext();
        mActivity = this;
        mLinearLayout = (CoordinatorLayout) findViewById(R.id.rl);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo_1 = bundle.getString("Id_Grupo_Operador").toString();

        lUMA = (LinearLayout) findViewById(R.id.lUMA);
        lUPSA = (LinearLayout) findViewById(R.id.lUPSA);
        lUPSB = (LinearLayout) findViewById(R.id.lUPSB);
        lBUSWAY = (LinearLayout) findViewById(R.id.lBUSWAY);
        lTANQUE = (LinearLayout) findViewById(R.id.lTANQUE);
        lXCOM = (LinearLayout) findViewById(R.id.lXCOM);

        txMensaje1 = (TextView) findViewById(R.id.txMensaje1);
        txMensaje2 = (TextView) findViewById(R.id.txMensaje2);
        txMensaje3 = (TextView) findViewById(R.id.txMensaje3);
        txMensaje4 = (TextView) findViewById(R.id.txMensaje4);
        txMensaje5 = (TextView) findViewById(R.id.txMensaje5);
        txMensaje6 = (TextView) findViewById(R.id.txMensaje6);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabA71 = (FloatingActionButton) findViewById(R.id.fabA71);
        fabA56 = (FloatingActionButton) findViewById(R.id.fabA56);
        fabA58 = (FloatingActionButton) findViewById(R.id.fabA58);

        Hora_Ingreso = getTime();

        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabA71, "Formulario A.71");
        TooltipCompat.setTooltipText(fabA56, "Formulario A.56");
        TooltipCompat.setTooltipText(fabA58, "Formulario A.58");

        CARGAR_UMAS();
        CARGAR_UPSA();
        CARGAR_UPSB();
        CARGAR_BUSWAY();
        CARGAR_TANQUE_ECARO();
        CARGAR_XCOM();
        CARGAR_LISTA_RONDAS();
        DIBUJAR_FILAS_DE_UMAS_EN_LAYOUT();
        DIBUJAR_FILAS_DE_UPS_A_EN_LAYOUT();
        DIBUJAR_FILAS_DE_UPS_B_EN_LAYOUT();
        DIBUJAR_FILAS_DE_BUSWAY_EN_LAYOUT();
        DIBUJAR_FILAS_DE_XCOM_EN_LAYOUT();
        DIBUJAR_FILAS_DE_TANQUE_ECARO_EN_LAYOUT();

        if (ListaUma.size() == 0) {
            txMensaje1.setText("No existen umas activas");
        } else if (ListaUma.size() == 1) {
            txMensaje1.setText("Se encontró " + String.valueOf(ListaUma.size()) + " uma activa");
        } else {
            txMensaje1.setText("Se encontraron " + String.valueOf(ListaUma.size()) + " umas activas");
        }

        if (ListaUpsA.size() == 0) {
            txMensaje1.setText("No existen ups activos");
        } else if (ListaUpsA.size() == 1) {
            txMensaje2.setText("Se encontró " + String.valueOf(ListaUpsA.size()) + " ups activo");
        } else {
            txMensaje2.setText("Se encontraron " + String.valueOf(ListaUpsA.size()) + " ups activos");
        }

        if (ListaUpsB.size() == 0) {
            txMensaje3.setText("No existen ups activos");
        } else if (ListaUpsB.size() == 1) {
            txMensaje3.setText("Se encontró " + String.valueOf(ListaUpsB.size()) + " ups activo");
        } else {
            txMensaje3.setText("Se encontraron " + String.valueOf(ListaUpsB.size()) + " ups activos");
        }

        if (ListaBusway.size() == 0) {
            txMensaje4.setText("No existen busway activos");
        } else if (ListaBusway.size() == 1) {
            txMensaje4.setText("Se encontró " + String.valueOf(ListaBusway.size()) + " busway activo");
        } else {
            txMensaje4.setText("Se encontraron " + String.valueOf(ListaBusway.size()) + " busway activos");
        }

        if (ListaTanque.size() == 0) {
            txMensaje5.setText("No existen tanques activos");
        } else if (ListaTanque.size() == 1) {
            txMensaje5.setText("Se encontró " + String.valueOf(ListaTanque.size()) + " tanque activo");
        } else {
            txMensaje5.setText("Se encontraron " + String.valueOf(ListaTanque.size()) + " tanques activos");
        }

        if (ListaXcom.size() == 0) {
            txMensaje6.setText("No existen XCOM activos");
        } else if (ListaXcom.size() == 1) {
            txMensaje6.setText("Se encontró " + String.valueOf(ListaXcom.size()) + " XCOM activo");
        } else {
            txMensaje6.setText("Se encontraron " + String.valueOf(ListaXcom.size()) + " XCOM activos");
        }

        fabA71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENERTURNO();
                if (!Tdo_Turno.equals(Tdo_Turno_1)) {
                    Toast.makeText(
                            getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A34.this);
                    myBulid.setTitle("Formulario A.71");
                    myBulid.setIcon(R.drawable.luminosidad1);
                    myBulid.setMessage("¿Desea ingresar al formulario A.71?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(C_A34.this, C_A71.class);
                            intent.putExtra("Id_Operador", Id_Operador);
                            intent.putExtra("Nombre_Operador", Nombre_Operador);
                            intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                            intent.putExtra("Turno_Operador", Turno_Operador);
                            intent.putExtra("IsMenuA56", isMenuA56);
                            intent.putExtra("Id_Grupo_Operador", Tdo_Grupo_1);
                            startActivity(intent);
                            finish();
                        }
                    });
                    myBulid.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            return;
                        }
                    });
                    AlertDialog dialog = myBulid.create();
                    dialog.show();
                }
            }
        });

        fabA56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENERTURNO();
                if (!Tdo_Turno.equals(Tdo_Turno_1)) {
                    Toast.makeText(
                            getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    bandera = bandera + 1;
                    final Intent intent = new Intent(C_A34.this, C_A56.class);
                    if (bandera == 1) {
                        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                        View customView = inflater.inflate(R.layout.l_toast, null);

                        mPopupWindow = new PopupWindow(
                                customView,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );

                        if (Build.VERSION.SDK_INT >= 21) {
                            mPopupWindow.setElevation(5.0f);
                        }

                        Button btnBP = (Button) customView.findViewById(R.id.btnBP);
                        Button btnDN = (Button) customView.findViewById(R.id.btnDN);
                        TextView txtMensaje = (TextView) customView.findViewById(R.id.txtMensaje);
                        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                        txtMensaje.setText("Formulario A.56/FD.56 - Checklist monitoreo de equipos de sala útil");

                        btnBP.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                isMenuA56 = "1";
                                intent.putExtra("Id_Operador", Id_Operador);
                                intent.putExtra("Nombre_Operador", Nombre_Operador);
                                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                intent.putExtra("Turno_Operador", Turno_Operador);
                                intent.putExtra("IsMenuA56", isMenuA56);
                                intent.putExtra("Access", "BP");
                                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo_1);
                                intent.putExtra("Tiempo_A56", "0");
                                mPopupWindow.dismiss();
                                bandera = 0;
                                startActivity(intent);
                                finish();
                                Toast.makeText(
                                        getApplicationContext(), "Ingreso correcto al formulario A.56", Toast.LENGTH_SHORT
                                ).show();
                            }
                        });

                        btnDN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                isMenuA56 = "1";
                                intent.putExtra("Id_Operador", Id_Operador);
                                intent.putExtra("Nombre_Operador", Nombre_Operador);
                                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                intent.putExtra("Turno_Operador", Turno_Operador);
                                intent.putExtra("IsMenuA56", isMenuA56);
                                intent.putExtra("Access", "DN");
                                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo_1);
                                intent.putExtra("Tiempo_A56", "0");
                                mPopupWindow.dismiss();
                                bandera = 0;
                                startActivity(intent);
                                finish();
                                Toast.makeText(
                                        getApplicationContext(), "Ingreso correcto al formulario FD.56", Toast.LENGTH_SHORT
                                ).show();
                            }
                        });

                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Dismiss the popup window
                                mPopupWindow.dismiss();
                                bandera = 0;
                            }
                        });

                        mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);
                    }
                }
            }
        });

        fabA58.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENERTURNO();
                if (!Tdo_Turno.equals(Tdo_Turno_1)) {
                    Toast.makeText(
                            getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A34.this);
                    myBulid.setTitle("Formulario A.58");
                    myBulid.setIcon(R.drawable.temperatura1);
                    myBulid.setMessage("¿Desea ingresar al formulario A.58?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(C_A34.this, C_A58.class);
                            intent.putExtra("Id_Operador", Id_Operador);
                            intent.putExtra("Nombre_Operador", Nombre_Operador);
                            intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                            intent.putExtra("Turno_Operador", Turno_Operador);
                            intent.putExtra("IsMenuA56", isMenuA56);
                            intent.putExtra("Id_Grupo_Operador", Tdo_Grupo_1);
                            startActivity(intent);
                            finish();
                        }
                    });
                    myBulid.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            return;
                        }
                    });
                    AlertDialog dialog = myBulid.create();
                    dialog.show();
                }
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENERTURNO();
                if (!Tdo_Turno.equals(Tdo_Turno_1)) {
                    Toast.makeText(
                            getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    if (lUMA.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen umas activas", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (lUPSA.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen UPS del lado A activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (lUPSB.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen UPS del lado B activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (lBUSWAY.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen busway activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (lTANQUE.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen tanques activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_UMAS() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_BUSWAY() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_UPS_A() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_UPS_B() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_CORRECTOS_UMAS() == false) {
                        return;
                    }
                /*if (VALIDAR_DATOS_CORRECTOS_BUSWAY() == false) {
                    return;
                }*/
                    if (VALIDAR_DATOS_CORRECTOS_UPS_A() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_CORRECTOS_UPS_B() == false) {
                        return;
                    }
                    if (VALIDAR_TANQUES_ECARO() == false) {
                        return;
                    }
                    Ronda = CONSULTAR_RONDA() + 1;
                    Hora_Salida = getTime();
                    LinearLayout ly1, ly2, ly3, ly4;
                    ListaA34Uma = new ArrayList<A34_Uma>();
                    String medicion = "", alerta = "";
                    for (int i = 0; i < lUMA.getChildCount(); i++) {
                        ly1 = (LinearLayout) lUMA.getChildAt(i);
                        Tdo_Uma = ly1.getTag().toString();
                        final Switch swMedicion = (Switch) ly1.getChildAt(0);
                        final TextInputLayout tilTemperatura = (TextInputLayout) ly1.getChildAt(2);
                        final TextInputLayout tilHumedad = (TextInputLayout) ly1.getChildAt(3);
                        final Switch swAlerta = (Switch) ly1.getChildAt(4);
                        Temperatura = (tilTemperatura.getEditText().getText().toString().equals("-") || tilTemperatura.getEditText().getText().toString().equals("")) ? "0" : tilTemperatura.getEditText().getText().toString();
                        Humedad = (tilHumedad.getEditText().getText().toString().equals("-") || tilHumedad.getEditText().getText().toString().equals("")) ? "0" : tilHumedad.getEditText().getText().toString();
                        if (swMedicion.isChecked()) {
                            medicion = "S";
                        } else {
                            medicion = "N";
                        }
                        if (swAlerta.isChecked()) {
                            alerta = "S";
                        } else {
                            alerta = "N";
                        }
                        A34_Uma a34_uma = new A34_Uma("", Tdo_Uma, medicion, Temperatura, Humedad, alerta, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA34Uma.add(a34_uma);
                    }
                    ListaA34Ups = new ArrayList<A34_Ups>();
                    for (int i = 0; i < lUPSA.getChildCount(); i++) {
                        ly2 = (LinearLayout) lUPSA.getChildAt(i);
                        Tdo_Ups = ly2.getTag().toString();
                        final Switch swMedicion = (Switch) ly2.getChildAt(0);
                        final TextInputLayout tilL1 = (TextInputLayout) ly2.getChildAt(2);
                        final TextInputLayout tilL2 = (TextInputLayout) ly2.getChildAt(3);
                        final TextInputLayout tilL3 = (TextInputLayout) ly2.getChildAt(4);
                        final TextInputLayout tilBateria = (TextInputLayout) ly2.getChildAt(5);
                        final Switch swAlerta = (Switch) ly2.getChildAt(6);
                        L1 = tilL1.getEditText().getText().toString().equals("") ? "0" : tilL1.getEditText().getText().toString();
                        L2 = tilL2.getEditText().getText().toString().equals("") ? "0" : tilL2.getEditText().getText().toString();
                        L3 = tilL3.getEditText().getText().toString().equals("") ? "0" : tilL3.getEditText().getText().toString();
                        Bateria = tilBateria.getEditText().getText().toString().equals("") ? "0" : tilBateria.getEditText().getText().toString();
                        if (swMedicion.isChecked()) {
                            medicion = "E";
                        } else {
                            medicion = "A";
                        }
                        if (swAlerta.isChecked()) {
                            alerta = "S";
                        } else {
                            alerta = "N";
                        }
                        A34_Ups a34_ups = new A34_Ups("", Tdo_Ups, medicion, L1, L2, L3, Bateria, alerta, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA34Ups.add(a34_ups);
                    }
                    for (int i = 0; i < lUPSB.getChildCount(); i++) {
                        ly2 = (LinearLayout) lUPSB.getChildAt(i);
                        Tdo_Ups = ly2.getTag().toString();
                        final Switch swMedicion = (Switch) ly2.getChildAt(0);
                        final TextInputLayout tilL1 = (TextInputLayout) ly2.getChildAt(2);
                        final TextInputLayout tilL2 = (TextInputLayout) ly2.getChildAt(3);
                        final TextInputLayout tilL3 = (TextInputLayout) ly2.getChildAt(4);
                        final TextInputLayout tilBateria = (TextInputLayout) ly2.getChildAt(5);
                        final Switch swAlerta = (Switch) ly2.getChildAt(6);
                        L1 = tilL1.getEditText().getText().toString().equals("") ? "0" : tilL1.getEditText().getText().toString();
                        L2 = tilL2.getEditText().getText().toString().equals("") ? "0" : tilL2.getEditText().getText().toString();
                        L3 = tilL3.getEditText().getText().toString().equals("") ? "0" : tilL3.getEditText().getText().toString();
                        Bateria = tilBateria.getEditText().getText().toString().equals("") ? "0" : tilBateria.getEditText().getText().toString();
                        if (swMedicion.isChecked()) {
                            medicion = "E";
                        } else {
                            medicion = "A";
                        }
                        if (swAlerta.isChecked()) {
                            alerta = "S";
                        } else {
                            alerta = "N";
                        }
                        A34_Ups a34_ups = new A34_Ups("", Tdo_Ups, medicion, L1, L2, L3, Bateria, alerta, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA34Ups.add(a34_ups);
                    }
                    ListaA34Busway = new ArrayList<A34_Busway>();
                    for (int i = 0; i < lBUSWAY.getChildCount(); i++) {
                        ly3 = (LinearLayout) lBUSWAY.getChildAt(i);
                        Tdo_Busway = ly3.getTag().toString();
                        final TextInputLayout tilVoltaje = (TextInputLayout) ly3.getChildAt(1);
                        final TextInputLayout tilCorriente = (TextInputLayout) ly3.getChildAt(2);
                        final TextInputLayout tilPotencia = (TextInputLayout) ly3.getChildAt(3);
                        final Switch swPDU = (Switch) ly3.getChildAt(4);
                        Voltaje = tilVoltaje.getEditText().getText().toString().equals("") ? "0" : tilVoltaje.getEditText().getText().toString();
                        Corriente = tilCorriente.getEditText().getText().toString().equals("") ? "0" : tilCorriente.getEditText().getText().toString();
                        Potencia = tilPotencia.getEditText().getText().toString().equals("") ? "0" : tilPotencia.getEditText().getText().toString();
                        if (swPDU.isChecked()) {
                            medicion = "E";
                        } else {
                            medicion = "A";
                        }
                        A34_Busway a34_busway = new A34_Busway("", Tdo_Busway, Voltaje, Corriente, Potencia, medicion, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA34Busway.add(a34_busway);
                    }
                    ListaA34Tanque = new ArrayList<A34_Tanque_Ecaro>();
                    for (int i = 0; i < lTANQUE.getChildCount(); i++) {
                        ly4 = (LinearLayout) lTANQUE.getChildAt(i);
                        Tdo_Tanque = ly4.getTag().toString();
                        final Switch swRojo = (Switch) ly4.getChildAt(1);
                        final Switch swNaranja = (Switch) ly4.getChildAt(2);
                        final Switch swVerde = (Switch) ly4.getChildAt(3);
                        if (swRojo.isChecked()) {
                            medicion = "R";
                        }
                        if (swNaranja.isChecked()) {
                            medicion = "N";
                        }
                        if (swVerde.isChecked()) {
                            medicion = "V";
                        }
                        A34_Tanque_Ecaro a34_tanque_ecaro = new A34_Tanque_Ecaro("", Tdo_Tanque, medicion, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA34Tanque.add(a34_tanque_ecaro);
                    }
                    ListaA34Xcom = new ArrayList<A34_Xcom>();
                    String alerta1 = "", voltaje = "", ac = "";
                    for (int i = 0; i < lXCOM.getChildCount(); i++) {
                        ly4 = (LinearLayout) lXCOM.getChildAt(i);
                        Tdo_Xcom = ly4.getTag().toString();
                        final Switch swAlerta = (Switch) ly4.getChildAt(1);
                        final Switch swVoltaje = (Switch) ly4.getChildAt(2);
                        final Switch swAC = (Switch) ly4.getChildAt(3);
                        if (swAlerta.isChecked()) {
                            alerta1 = "!";
                        } else {
                            alerta1 = "N";
                        }
                        if (swVoltaje.isChecked()) {
                            voltaje = "E";
                        } else {
                            voltaje = "A";
                        }
                        if (swAC.isChecked()) {
                            ac = "E";
                        } else {
                            ac = "A";
                        }
                        A34_Xcom a34_xcom = new A34_Xcom("", Tdo_Xcom, alerta1, voltaje, ac, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA34Xcom.add(a34_xcom);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_A34.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            long insertedResult1 = 0, insertedResult2 = 0, insertedResult3 = 0, insertedResult4 = 0, insertedResult5 = 0;
                                            Datos1 = Crud_A34.OBTENER_INSTANCIA(getApplicationContext());
                                            try {
                                                Datos1.GETBD().beginTransaction();
                                                for (int i = 0; i < ListaA34Uma.size(); i++) {
                                                    insertedResult1 = Datos1.INSERTAR_A34_UMA(ListaA34Uma.get(i));
                                                    if (insertedResult1 == -1) {
                                                        Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                    }
                                                }
                                                if (insertedResult1 != -1) {
                                                    for (int i = 0; i < ListaA34Ups.size(); i++) {
                                                        insertedResult2 = Datos1.INSERTAR_A34_UPS(ListaA34Ups.get(i));
                                                        if (insertedResult2 == -1) {
                                                            Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                            throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                        }
                                                    }
                                                }
                                                if (insertedResult1 != -1 && insertedResult2 != -1) {
                                                    for (int i = 0; i < ListaA34Busway.size(); i++) {
                                                        insertedResult3 = Datos1.INSERTAR_A34_BUSWAY(ListaA34Busway.get(i));
                                                        if (insertedResult3 == -1) {
                                                            Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                            throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                        }
                                                    }
                                                }
                                                if (insertedResult1 != -1 && insertedResult2 != -1 && insertedResult3 != -1) {
                                                    for (int i = 0; i < ListaA34Tanque.size(); i++) {
                                                        insertedResult4 = Datos1.INSERTAR_A34_TANQUE_ECARO(ListaA34Tanque.get(i));
                                                        if (insertedResult4 == -1) {
                                                            Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                            throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                        }
                                                    }
                                                }
                                                if (insertedResult1 != -1 && insertedResult2 != -1 && insertedResult3 != -1 && insertedResult4 != -1) {
                                                    for (int i = 0; i < ListaA34Xcom.size(); i++) {
                                                        insertedResult5 = Datos1.INSERTAR_A34_XCOM(ListaA34Xcom.get(i));
                                                        if (insertedResult5 == -1) {
                                                            Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                            throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                        }
                                                    }
                                                }
                                                Datos1.GETBD().setTransactionSuccessful();
                                            } catch (SQLException e) {
                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                            } finally {
                                                Datos1.GETBD().endTransaction();
                                            }
                                            Toast.makeText(getApplicationContext(), "El registro se guardó correctamente", Toast.LENGTH_LONG).show();
                                            LIMPIAR_DATOS();
                                        }
                                    })
                            .setNegativeButton("Cancelar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            return;
                                        }
                                    });
                    builder.show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder myBulid = new AlertDialog.Builder(this);
        myBulid.setTitle("Regresar");
        myBulid.setIcon(R.drawable.salir);
        myBulid.setMessage("¿Desea regresar a la pantalla del menú principal?");
        myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(C_A34.this, C_Menu_Principal.class);
                intent.putExtra("Id_Operador", Id_Operador);
                intent.putExtra("Nombre_Operador", Nombre_Operador);
                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                intent.putExtra("Turno_Operador", Turno_Operador);
                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo_1);
                startActivity(intent);
                finish();
            }
        });
        myBulid.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                return;
            }
        });
        AlertDialog dialog = myBulid.create();
        dialog.show();
    }

    public boolean VALIDAR_DATOS_VACIOS_UMAS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lUMA.getChildCount(); i++) {
            ly = (LinearLayout) lUMA.getChildAt(i);
            final Switch swMedicion = (Switch) ly.getChildAt(0);
            final TextInputLayout tilTemperatura = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilHumedad = (TextInputLayout) ly.getChildAt(3);
            if (swMedicion.isChecked()) {
                if (tilTemperatura.getEditText().getText().toString().equals("")) {
                    tilTemperatura.requestFocus();
                    tilTemperatura.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lUMA.getChildCount();
                }
                if (ly.getTag().equals("ARE-77") || ly.getTag().equals("ARE-80")) {
                    if (tilHumedad.getEditText().getText().toString().equals("")) {
                        tilHumedad.requestFocus();
                        tilHumedad.getEditText().setError("Este campo no puede estar vacio");
                        condicion = 0;
                        i = lUMA.getChildCount();
                    }
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_UMAS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lUMA.getChildCount(); i++) {
            ly = (LinearLayout) lUMA.getChildAt(i);
            final Switch swMedicion = (Switch) ly.getChildAt(0);
            final TextInputLayout tilTemperatura = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilHumedad = (TextInputLayout) ly.getChildAt(3);
            if (swMedicion.isChecked()) {
                if (tilTemperatura.getEditText().getText().toString().equals("0")) {
                    tilTemperatura.requestFocus();
                    tilTemperatura.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lUMA.getChildCount();
                }
                if (ly.getTag().equals("ARE-77") || ly.getTag().equals("ARE-80")) {
                    if (tilHumedad.getEditText().getText().toString().equals("0")) {
                        tilHumedad.requestFocus();
                        tilHumedad.getEditText().setError("El valor ingresado no puede ser 0");
                        condicion = 0;
                        i = lUMA.getChildCount();
                    }
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_VACIOS_UPS_A() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lUPSA.getChildCount(); i++) {
            ly = (LinearLayout) lUPSA.getChildAt(i);
            final Switch swMedicion = (Switch) ly.getChildAt(0);
            final TextInputLayout tilL1 = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilL2 = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilL3 = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilBateria = (TextInputLayout) ly.getChildAt(5);
            if (swMedicion.isChecked()) {
                if (tilL1.getEditText().getText().toString().equals("")) {
                    tilL1.requestFocus();
                    tilL1.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lUPSA.getChildCount();
                }
                if (tilL1.getEditText().getText().toString().equals("")) {
                    tilL2.requestFocus();
                    tilL2.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lUPSA.getChildCount();
                }
                if (tilL3.getEditText().getText().toString().equals("")) {
                    tilL3.requestFocus();
                    tilL3.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lUPSA.getChildCount();
                }
                if (tilBateria.getEditText().getText().toString().equals("")) {
                    tilBateria.requestFocus();
                    tilBateria.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lUPSA.getChildCount();
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_UPS_A() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lUPSA.getChildCount(); i++) {
            ly = (LinearLayout) lUPSA.getChildAt(i);
            final Switch swMedicion = (Switch) ly.getChildAt(0);
            final TextInputLayout tilL1 = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilL2 = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilL3 = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilBateria = (TextInputLayout) ly.getChildAt(5);
            if (swMedicion.isChecked()) {
                if (tilL1.getEditText().getText().toString().equals("0")) {
                    tilL1.requestFocus();
                    tilL1.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lUPSA.getChildCount();
                }
                if (tilL1.getEditText().getText().toString().equals("0")) {
                    tilL2.requestFocus();
                    tilL2.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lUPSA.getChildCount();
                }
                if (tilL3.getEditText().getText().toString().equals("0")) {
                    tilL3.requestFocus();
                    tilL3.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lUPSA.getChildCount();
                }
                if (tilBateria.getEditText().getText().toString().equals("0")) {
                    tilBateria.requestFocus();
                    tilBateria.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lUPSA.getChildCount();
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_VACIOS_UPS_B() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lUPSB.getChildCount(); i++) {
            ly = (LinearLayout) lUPSB.getChildAt(i);
            final Switch swMedicion = (Switch) ly.getChildAt(0);
            final TextInputLayout tilL1 = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilL2 = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilL3 = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilBateria = (TextInputLayout) ly.getChildAt(5);
            if (swMedicion.isChecked()) {
                if (tilL1.getEditText().getText().toString().equals("")) {
                    tilL1.requestFocus();
                    tilL1.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lUPSB.getChildCount();
                }
                if (tilL1.getEditText().getText().toString().equals("")) {
                    tilL2.requestFocus();
                    tilL2.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lUPSB.getChildCount();
                }
                if (tilL3.getEditText().getText().toString().equals("")) {
                    tilL3.requestFocus();
                    tilL3.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lUPSB.getChildCount();
                }
                if (tilBateria.getEditText().getText().toString().equals("")) {
                    tilBateria.requestFocus();
                    tilBateria.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lUPSB.getChildCount();
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_UPS_B() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lUPSB.getChildCount(); i++) {
            ly = (LinearLayout) lUPSB.getChildAt(i);
            final Switch swMedicion = (Switch) ly.getChildAt(0);
            final TextInputLayout tilL1 = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilL2 = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilL3 = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilBateria = (TextInputLayout) ly.getChildAt(5);
            if (swMedicion.isChecked()) {
                if (tilL1.getEditText().getText().toString().equals("0")) {
                    tilL1.requestFocus();
                    tilL1.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lUPSB.getChildCount();
                }
                if (tilL1.getEditText().getText().toString().equals("0")) {
                    tilL2.requestFocus();
                    tilL2.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lUPSB.getChildCount();
                }
                if (tilL3.getEditText().getText().toString().equals("0")) {
                    tilL3.requestFocus();
                    tilL3.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lUPSB.getChildCount();
                }
                if (tilBateria.getEditText().getText().toString().equals("0")) {
                    tilBateria.requestFocus();
                    tilBateria.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lUPSB.getChildCount();
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_VACIOS_BUSWAY() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lBUSWAY.getChildCount(); i++) {
            ly = (LinearLayout) lBUSWAY.getChildAt(i);
            final TextInputLayout tilVoltaje = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilCorriente = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilPotencia = (TextInputLayout) ly.getChildAt(3);
            final Switch swPDU = (Switch) ly.getChildAt(4);
            if (swPDU.isChecked()) {
                if (tilVoltaje.getEditText().getText().toString().equals("")) {
                    tilVoltaje.requestFocus();
                    tilVoltaje.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lBUSWAY.getChildCount();
                }
                if (tilCorriente.getEditText().getText().toString().equals("")) {
                    tilCorriente.requestFocus();
                    tilCorriente.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lBUSWAY.getChildCount();
                }
                if (tilPotencia.getEditText().getText().toString().equals("")) {
                    tilPotencia.requestFocus();
                    tilPotencia.getEditText().setError("Este campo no puede estar vacio");
                    condicion = 0;
                    i = lBUSWAY.getChildCount();
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_BUSWAY() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lBUSWAY.getChildCount(); i++) {
            ly = (LinearLayout) lBUSWAY.getChildAt(i);
            final TextInputLayout tilVoltaje = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilCorriente = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilPotencia = (TextInputLayout) ly.getChildAt(3);
            final Switch swPDU = (Switch) ly.getChildAt(4);
            if (swPDU.isChecked()) {
                if (tilVoltaje.getEditText().getText().toString().equals("0")) {
                    tilVoltaje.requestFocus();
                    tilVoltaje.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lBUSWAY.getChildCount();
                }
                if (tilCorriente.getEditText().getText().toString().equals("0")) {
                    tilCorriente.requestFocus();
                    tilCorriente.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lBUSWAY.getChildCount();
                }
                if (tilPotencia.getEditText().getText().toString().equals("0")) {
                    tilPotencia.requestFocus();
                    tilPotencia.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lBUSWAY.getChildCount();
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_TANQUES_ECARO() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lTANQUE.getChildCount(); i++) {
            ly = (LinearLayout) lTANQUE.getChildAt(i);
            final Switch swRojo = (Switch) ly.getChildAt(1);
            final Switch swNaranja = (Switch) ly.getChildAt(2);
            final Switch swVerde = (Switch) ly.getChildAt(3);
            if (!swRojo.isChecked() && !swNaranja.isChecked() && !swVerde.isChecked()) {
                Toast.makeText(getApplicationContext(), String.format("Seleccione un color para el tanque"), Toast.LENGTH_LONG).show();
                condicion = 0;
                i = lTANQUE.getChildCount();

            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public int CONSULTAR_RONDA() {
        int ronda = 0;
        Crud_A34 DATOS;
        DATOS = Crud_A34.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_A34_Uma = DATOS.CONSULTA_GENERAL_RONDA(Tdo_Turno, getDate());
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_A34_Uma.moveToFirst() == false) {
            //el cursor está vacío
            ronda = 0;
        } else {
            int P_Ronda = T_A34_Uma.getColumnIndex(IT_A34_Uma.I_A34_Uma.RONDA);
            for (T_A34_Uma.moveToFirst(); !T_A34_Uma.isAfterLast(); T_A34_Uma.moveToNext()) {
                for (int i = 0; i < ListaRonda.size(); i++) {
                    if (T_A34_Uma.getString(P_Ronda).equals(ListaRonda.get(i).toString())) {
                        ronda = Integer.valueOf(ListaRonda.get(i).toString());
                    }
                }
            }
        }
        return ronda;
    }

    public void LIMPIAR_DATOS() {
        Tdo_Uma = "";
        Tdo_Ups = "";
        Tdo_Busway = "";
        Tdo_Tanque = "";
        Tdo_Area = "";
        LinearLayout ly1;
        ListaA34Uma = new ArrayList<A34_Uma>();
        ListaA34Ups = new ArrayList<A34_Ups>();
        ListaA34Busway = new ArrayList<A34_Busway>();
        ListaA34Tanque = new ArrayList<A34_Tanque_Ecaro>();
        for (int i = 0; i < lUMA.getChildCount(); i++) {
            ly1 = (LinearLayout) lUMA.getChildAt(i);
            final Switch swMedicion = (Switch) ly1.getChildAt(0);
            final TextInputLayout tilTemperatura = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilHumedad = (TextInputLayout) ly1.getChildAt(3);
            final Switch swAlerta = (Switch) ly1.getChildAt(4);
            tilTemperatura.getEditText().setText("-");
            if (ly1.getTag().equals("ARE-77") || ly1.getTag().equals("ARE-80")) {
                tilHumedad.getEditText().setText("-");
            } else {
                tilHumedad.getEditText().setText("-");
            }
            tilTemperatura.getEditText().setError(null);
            tilHumedad.getEditText().setError(null);
            tilTemperatura.getEditText().setEnabled(false);
            tilHumedad.getEditText().setEnabled(false);
            swMedicion.setChecked(false);
            swAlerta.setChecked(false);
        }
        for (int i = 0; i < lUPSA.getChildCount(); i++) {
            ly1 = (LinearLayout) lUPSA.getChildAt(i);
            final Switch swMedicion = (Switch) ly1.getChildAt(0);
            final TextInputLayout tilL1 = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilL2 = (TextInputLayout) ly1.getChildAt(3);
            final TextInputLayout tilL3 = (TextInputLayout) ly1.getChildAt(4);
            final TextInputLayout tilBateria = (TextInputLayout) ly1.getChildAt(5);
            final Switch swAlerta = (Switch) ly1.getChildAt(6);
            tilL1.getEditText().setText("");
            tilL2.getEditText().setText("");
            tilL3.getEditText().setText("");
            tilBateria.getEditText().setText("");
            tilL1.getEditText().setError(null);
            tilL2.getEditText().setError(null);
            tilL3.getEditText().setError(null);
            tilBateria.getEditText().setError(null);
            tilL1.getEditText().setEnabled(false);
            tilL2.getEditText().setEnabled(false);
            tilL3.getEditText().setEnabled(false);
            tilBateria.getEditText().setEnabled(false);
            swMedicion.setChecked(false);
            swAlerta.setChecked(false);
        }
        for (int i = 0; i < lUPSB.getChildCount(); i++) {
            ly1 = (LinearLayout) lUPSB.getChildAt(i);
            final Switch swMedicion = (Switch) ly1.getChildAt(0);
            final TextInputLayout tilL1 = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilL2 = (TextInputLayout) ly1.getChildAt(3);
            final TextInputLayout tilL3 = (TextInputLayout) ly1.getChildAt(4);
            final TextInputLayout tilBateria = (TextInputLayout) ly1.getChildAt(5);
            final Switch swAlerta = (Switch) ly1.getChildAt(6);
            tilL1.getEditText().setText("");
            tilL2.getEditText().setText("");
            tilL3.getEditText().setText("");
            tilBateria.getEditText().setText("");
            tilL1.getEditText().setError(null);
            tilL2.getEditText().setError(null);
            tilL3.getEditText().setError(null);
            tilBateria.getEditText().setError(null);
            tilL1.getEditText().setEnabled(false);
            tilL2.getEditText().setEnabled(false);
            tilL3.getEditText().setEnabled(false);
            tilBateria.getEditText().setEnabled(false);
            swMedicion.setChecked(false);
            swAlerta.setChecked(false);
        }
        for (int i = 0; i < lBUSWAY.getChildCount(); i++) {
            ly1 = (LinearLayout) lBUSWAY.getChildAt(i);
            final TextInputLayout tilVoltaje = (TextInputLayout) ly1.getChildAt(1);
            final TextInputLayout tilCorriente = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilPotencia = (TextInputLayout) ly1.getChildAt(3);
            final Switch swPDU = (Switch) ly1.getChildAt(4);
            tilVoltaje.getEditText().setText("");
            tilCorriente.getEditText().setText("");
            tilPotencia.getEditText().setText("");
            tilVoltaje.getEditText().setEnabled(false);
            tilCorriente.getEditText().setEnabled(false);
            tilPotencia.getEditText().setEnabled(false);
            tilVoltaje.getEditText().setError(null);
            tilCorriente.getEditText().setError(null);
            tilPotencia.getEditText().setError(null);
            swPDU.setChecked(true);
            swPDU.setEnabled(true);
        }
        for (int i = 0; i < lTANQUE.getChildCount(); i++) {
            ly1 = (LinearLayout) lTANQUE.getChildAt(i);
            final Switch swRojo = (Switch) ly1.getChildAt(1);
            final Switch swNaranja = (Switch) ly1.getChildAt(2);
            final Switch swVerde = (Switch) ly1.getChildAt(3);
            swRojo.setChecked(false);
            swNaranja.setChecked(false);
            swVerde.setChecked(true);
        }
        for (int i = 0; i < lXCOM.getChildCount(); i++) {
            ly1 = (LinearLayout) lXCOM.getChildAt(i);
            final Switch swAlerta = (Switch) ly1.getChildAt(1);
            final Switch swVoltaje = (Switch) ly1.getChildAt(2);
            final Switch swAC = (Switch) ly1.getChildAt(3);
            swAlerta.setChecked(false);
            swVoltaje.setChecked(false);
            swAC.setChecked(false);
        }
    }

    public void DIBUJAR_FILAS_DE_UMAS_EN_LAYOUT() {
        if (ListaUma.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaUma.size(); i++) {

            final LinearLayout lFilasAreas = new LinearLayout(this);

            final TextInputLayout tilArea = new TextInputLayout(this);
            final TextInputLayout tilTemperatura = new TextInputLayout(this);
            final TextInputLayout tilHumedad = new TextInputLayout(this);

            final EditText etArea = new EditText(this);
            final EditText etTemperatura = new EditText(this);
            final EditText etHumedad = new EditText(this);

            final Switch swMedicion = new Switch(this);
            final Switch swAlerta = new Switch(this);

            lFilasAreasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etTemperaturaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etHumedadParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            swMedicionParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            swAlertaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasAreas.setLayoutParams(lFilasAreasParams);
            lFilasAreas.setOrientation(LinearLayout.HORIZONTAL);
            lFilasAreas.setTag(ListaUma.get(i).getID());
            lFilasAreas.setId(i);

            swMedicionParams.gravity = Gravity.CENTER;
            swMedicionParams.width = 100;
            swMedicion.setLayoutParams(swMedicionParams);
            swMedicion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            swMedicion.setTextColor(getThemeAccentColor(this));
            swMedicion.setText("N-S");
            swMedicion.setEnabled(true);
            swMedicion.setSelected(false);

            etAreaParams.gravity = Gravity.CENTER;
            etAreaParams.width = 200;
            etArea.setLayoutParams(etAreaParams);
            //etArea.setHint("UMA");
            etArea.setText(ListaUma.get(i).getNOMBRE_AREA());
            etArea.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etArea.setTextColor(getThemeAccentColor(this));
            etArea.setEnabled(false);

            etTemperaturaParams.gravity = Gravity.CENTER;
            etTemperaturaParams.width = 150;
            etTemperatura.setLayoutParams(etTemperaturaParams);
            //etTemperatura.setHint("Temperatura");
            etTemperatura.setText("-");
            etTemperatura.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etTemperatura.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
            etTemperatura.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etTemperatura.setTextColor(getThemeAccentColor(this));
            etTemperatura.setEnabled(false);

            etHumedadParams.gravity = Gravity.CENTER;
            etHumedadParams.width = 150;
            etHumedad.setLayoutParams(etHumedadParams);
            etHumedad.setTag(i);
            //etHumedad.setHint("Humedad");
            if (ListaUma.get(i).getID().equals("ARE-77") || ListaUma.get(i).getID().equals("ARE-80")) {
                etHumedad.setText("-");
            } else {
                etHumedad.setText("-");
            }
            etHumedad.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etHumedad.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
            etHumedad.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etHumedad.setTextColor(getThemeAccentColor(this));
            etHumedad.setEnabled(false);

            swAlertaParams.gravity = Gravity.CENTER;
            swAlertaParams.width = 150;
            swAlerta.setLayoutParams(swAlertaParams);
            swAlerta.setEnabled(true);
            swAlerta.setSelected(false);

            swMedicion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((Switch) v).isChecked()) {
                        etTemperatura.setEnabled(true);
                        etHumedad.setEnabled(true);
                        etTemperatura.setText("");
                        if (ListaUma.get(Integer.valueOf(etHumedad.getTag().toString())).getID().equals("ARE-77") || ListaUma.get(Integer.valueOf(etHumedad.getTag().toString())).getID().equals("ARE-80")) {
                            etHumedad.setText("");
                            etHumedad.setEnabled(true);
                        } else {
                            etHumedad.setText("-");
                            etHumedad.setEnabled(false);
                        }
                        etTemperatura.setError(null);
                        etHumedad.setError(null);
                        etTemperatura.requestFocus();
                    } else {
                        etTemperatura.setEnabled(false);
                        etHumedad.setEnabled(false);
                        etTemperatura.setText("-");
                        if (ListaUma.get(Integer.valueOf(etHumedad.getTag().toString())).getID().equals("ARE-77") || ListaUma.get(Integer.valueOf(etHumedad.getTag().toString())).getID().equals("ARE-80")) {
                            etHumedad.setText("-");
                            etHumedad.setEnabled(false);
                        } else {
                            etHumedad.setText("-");
                            etHumedad.setEnabled(false);
                        }
                        etTemperatura.setError(null);
                        etHumedad.setError(null);
                        etTemperatura.requestFocus();
                    }
                }
            });


            tilArea.addView(etArea);
            tilTemperatura.addView(etTemperatura);
            tilHumedad.addView(etHumedad);

            lFilasAreas.addView(swMedicion);
            lFilasAreas.addView(tilArea);
            lFilasAreas.addView(tilTemperatura);
            lFilasAreas.addView(tilHumedad);
            lFilasAreas.addView(swAlerta);

            lUMA.addView(lFilasAreas);

        }
    }

    public void DIBUJAR_FILAS_DE_UPS_A_EN_LAYOUT() {
        if (ListaUpsA.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaUpsA.size(); i++) {

            if (ListaUpsA.get(i).getTIPO_AREA().equals("TAR-9")) {

                final LinearLayout lFilasAreas = new LinearLayout(this);

                final TextInputLayout tilArea = new TextInputLayout(this);
                final TextInputLayout tilL1 = new TextInputLayout(this);
                final TextInputLayout tilL2 = new TextInputLayout(this);
                final TextInputLayout tilL3 = new TextInputLayout(this);
                final TextInputLayout tilBateria = new TextInputLayout(this);

                final EditText etArea = new EditText(this);
                final EditText etL1 = new EditText(this);
                final EditText etL2 = new EditText(this);
                final EditText etL3 = new EditText(this);
                final EditText etBateria = new EditText(this);

                final Switch swMedicion = new Switch(this);
                final Switch swAlerta = new Switch(this);

                lFilasAreasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                etAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                etL1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                etL2Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                etL3Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                etBateriaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                swMedicionParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                swAlertaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

                lFilasAreas.setLayoutParams(lFilasAreasParams);
                lFilasAreas.setOrientation(LinearLayout.HORIZONTAL);
                lFilasAreas.setTag(ListaUpsA.get(i).getID());
                lFilasAreas.setId(i);

                swMedicionParams.gravity = Gravity.CENTER;
                swMedicionParams.width = 100;
                swMedicion.setLayoutParams(swMedicionParams);
                swMedicion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                swMedicion.setTextColor(getThemeAccentColor(this));
                swMedicion.setText("A-E");
                swMedicion.setEnabled(true);
                swMedicion.setSelected(false);

                etAreaParams.gravity = Gravity.CENTER;
                etAreaParams.width = 100;
                etArea.setLayoutParams(etAreaParams);
                //etArea.setHint("UPS");
                etArea.setText(ListaUpsA.get(i).getNOMBRE_AREA());
                etArea.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etArea.setTextColor(getThemeAccentColor(this));
                etArea.setEnabled(false);

                etL1Params.gravity = Gravity.CENTER;
                etL1Params.width = 100;
                etL1.setLayoutParams(etL1Params);
                //etL1.setHint("L1");
                etL1.setText("");
                etL1.setInputType(InputType.TYPE_CLASS_NUMBER);
                etL1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                etL1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etL1.setTextColor(getThemeAccentColor(this));
                etL1.setEnabled(false);

                etL2Params.gravity = Gravity.CENTER;
                etL2Params.width = 100;
                etL2.setLayoutParams(etL2Params);
                //etL2.setHint("L2");
                etL2.setText("");
                etL2.setInputType(InputType.TYPE_CLASS_NUMBER);
                etL2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                etL2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etL2.setTextColor(getThemeAccentColor(this));
                etL2.setEnabled(false);

                etL3Params.gravity = Gravity.CENTER;
                etL3Params.width = 100;
                etL3.setLayoutParams(etL3Params);
                //etL3.setHint("L3");
                etL3.setText("");
                etL3.setInputType(InputType.TYPE_CLASS_NUMBER);
                etL3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                etL3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etL3.setTextColor(getThemeAccentColor(this));
                etL3.setEnabled(false);

                etBateriaParams.gravity = Gravity.CENTER;
                etBateriaParams.width = 100;
                etBateria.setLayoutParams(etBateriaParams);
                //etBateria.setHint("Bateria");
                etBateria.setText("");
                etBateria.setInputType(InputType.TYPE_CLASS_NUMBER);
                etBateria.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                etBateria.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etBateria.setTextColor(getThemeAccentColor(this));
                etBateria.setEnabled(false);

                swAlertaParams.gravity = Gravity.CENTER;
                swAlertaParams.width = 100;
                swAlerta.setLayoutParams(swAlertaParams);
                swAlerta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                swAlerta.setTextColor(getThemeAccentColor(this));
                swAlerta.setText("N-!");
                swAlerta.setEnabled(true);
                swAlerta.setSelected(false);

                swMedicion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Switch) v).isChecked()) {
                            etL1.setEnabled(true);
                            etL2.setEnabled(true);
                            etL3.setEnabled(true);
                            etBateria.setEnabled(true);
                            etL1.setText("");
                            etL2.setText("");
                            etL3.setText("");
                            etBateria.setText("");
                            etL1.setError(null);
                            etL2.setError(null);
                            etL3.setError(null);
                            etBateria.setError(null);
                            etL1.requestFocus();
                        } else {
                            etL1.setEnabled(false);
                            etL2.setEnabled(false);
                            etL3.setEnabled(false);
                            etBateria.setEnabled(false);
                            etL1.setText("");
                            etL2.setText("");
                            etL3.setText("");
                            etBateria.setText("");
                            etL1.setError(null);
                            etL2.setError(null);
                            etL3.setError(null);
                            etBateria.setError(null);
                            etL1.requestFocus();
                        }
                    }
                });

                tilArea.addView(etArea);
                tilL1.addView(etL1);
                tilL2.addView(etL2);
                tilL3.addView(etL3);
                tilBateria.addView(etBateria);

                lFilasAreas.addView(swMedicion);
                lFilasAreas.addView(tilArea);
                lFilasAreas.addView(tilL1);
                lFilasAreas.addView(tilL2);
                lFilasAreas.addView(tilL3);
                lFilasAreas.addView(tilBateria);
                lFilasAreas.addView(swAlerta);

                lUPSA.addView(lFilasAreas);

            }
        }
    }

    public void DIBUJAR_FILAS_DE_UPS_B_EN_LAYOUT() {
        if (ListaUpsB.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaUpsB.size(); i++) {

            if (ListaUpsB.get(i).getTIPO_AREA().equals("TAR-10")) {

                final LinearLayout lFilasAreas = new LinearLayout(this);

                final TextInputLayout tilArea = new TextInputLayout(this);
                final TextInputLayout tilL1 = new TextInputLayout(this);
                final TextInputLayout tilL2 = new TextInputLayout(this);
                final TextInputLayout tilL3 = new TextInputLayout(this);
                final TextInputLayout tilBateria = new TextInputLayout(this);

                final EditText etArea = new EditText(this);
                final EditText etL1 = new EditText(this);
                final EditText etL2 = new EditText(this);
                final EditText etL3 = new EditText(this);
                final EditText etBateria = new EditText(this);

                final Switch swMedicion = new Switch(this);
                final Switch swAlerta = new Switch(this);

                lFilasAreasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                etAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                etL1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                etL2Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                etL3Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                etBateriaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                swMedicionParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                swAlertaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

                lFilasAreas.setLayoutParams(lFilasAreasParams);
                lFilasAreas.setOrientation(LinearLayout.HORIZONTAL);
                lFilasAreas.setTag(ListaUpsB.get(i).getID());
                lFilasAreas.setId(i);

                swMedicionParams.gravity = Gravity.CENTER;
                swMedicionParams.width = 100;
                swMedicion.setLayoutParams(swMedicionParams);
                swMedicion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                swMedicion.setTextColor(getThemeAccentColor(this));
                swMedicion.setText("A-E");
                swMedicion.setEnabled(true);
                swMedicion.setSelected(false);

                etAreaParams.gravity = Gravity.CENTER;
                etAreaParams.width = 100;
                etArea.setLayoutParams(etAreaParams);
                //etArea.setHint("UPS");
                etArea.setText(ListaUpsB.get(i).getNOMBRE_AREA());
                etArea.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etArea.setTextColor(getThemeAccentColor(this));
                etArea.setEnabled(false);

                etL1Params.gravity = Gravity.CENTER;
                etL1Params.width = 100;
                etL1.setLayoutParams(etL1Params);
                //etL1.setHint("L1");
                etL1.setText("");
                etL1.setInputType(InputType.TYPE_CLASS_NUMBER);
                etL1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                etL1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etL1.setTextColor(getThemeAccentColor(this));
                etL1.setEnabled(false);

                etL2Params.gravity = Gravity.CENTER;
                etL2Params.width = 100;
                etL2.setLayoutParams(etL2Params);
                //etL2.setHint("L2");
                etL2.setInputType(InputType.TYPE_CLASS_NUMBER);
                etL2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                etL2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etL2.setTextColor(getThemeAccentColor(this));
                etL2.setText("");
                etL2.setEnabled(false);

                etL3Params.gravity = Gravity.CENTER;
                etL3Params.width = 100;
                etL3.setLayoutParams(etL3Params);
                //etL3.setHint("L3");
                etL3.setText("");
                etL3.setInputType(InputType.TYPE_CLASS_NUMBER);
                etL3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                etL3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etL3.setTextColor(getThemeAccentColor(this));
                etL3.setEnabled(false);

                etBateriaParams.gravity = Gravity.CENTER;
                etBateriaParams.width = 100;
                etBateria.setLayoutParams(etBateriaParams);
                //etBateria.setHint("Bateria");
                etBateria.setText("");
                etBateria.setInputType(InputType.TYPE_CLASS_NUMBER);
                etBateria.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                etBateria.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etBateria.setTextColor(getThemeAccentColor(this));
                etBateria.setEnabled(false);

                swAlertaParams.gravity = Gravity.CENTER;
                swAlertaParams.width = 100;
                swAlerta.setLayoutParams(swAlertaParams);
                swAlerta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                swAlerta.setTextColor(getThemeAccentColor(this));
                swAlerta.setText("N-!");
                swAlerta.setEnabled(true);
                swAlerta.setSelected(false);

                swMedicion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Switch) v).isChecked()) {
                            etL1.setEnabled(true);
                            etL2.setEnabled(true);
                            etL3.setEnabled(true);
                            etBateria.setEnabled(true);
                            etL1.setText("");
                            etL2.setText("");
                            etL3.setText("");
                            etBateria.setText("");
                            etL1.setError(null);
                            etL2.setError(null);
                            etL3.setError(null);
                            etBateria.setError(null);
                            etL1.requestFocus();
                        } else {
                            etL1.setEnabled(false);
                            etL2.setEnabled(false);
                            etL3.setEnabled(false);
                            etBateria.setEnabled(false);
                            etL1.setText("");
                            etL2.setText("");
                            etL3.setText("");
                            etBateria.setText("");
                            etL1.setError(null);
                            etL2.setError(null);
                            etL3.setError(null);
                            etBateria.setError(null);
                            etL1.requestFocus();
                        }
                    }
                });

                tilArea.addView(etArea);
                tilL1.addView(etL1);
                tilL2.addView(etL2);
                tilL3.addView(etL3);
                tilBateria.addView(etBateria);

                lFilasAreas.addView(swMedicion);
                lFilasAreas.addView(tilArea);
                lFilasAreas.addView(tilL1);
                lFilasAreas.addView(tilL2);
                lFilasAreas.addView(tilL3);
                lFilasAreas.addView(tilBateria);
                lFilasAreas.addView(swAlerta);

                lUPSB.addView(lFilasAreas);

            }
        }
    }

    public void DIBUJAR_FILAS_DE_BUSWAY_EN_LAYOUT() {
        if (ListaBusway.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaBusway.size(); i++) {

            final LinearLayout lFilasAreas = new LinearLayout(this);

            final TextInputLayout tilArea = new TextInputLayout(this);
            final TextInputLayout tilVoltaje = new TextInputLayout(this);
            final TextInputLayout tilCorriente = new TextInputLayout(this);
            final TextInputLayout tilPotencia = new TextInputLayout(this);

            final EditText etArea = new EditText(this);
            final EditText etVoltaje = new EditText(this);
            final EditText etCorriente = new EditText(this);
            final EditText etPotencia = new EditText(this);

            final Switch swPDU = new Switch(this);

            lFilasAreasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etVoltajeParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etCorrienteParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etPotenciaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            swPDUParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasAreas.setLayoutParams(lFilasAreasParams);
            lFilasAreas.setOrientation(LinearLayout.HORIZONTAL);
            lFilasAreas.setTag(ListaBusway.get(i).getID());
            lFilasAreas.setId(i);

            etAreaParams.gravity = Gravity.CENTER;
            etAreaParams.width = 200;
            etArea.setLayoutParams(etAreaParams);
            //etArea.setHint("Busway");
            etArea.setText(ListaBusway.get(i).getNOMBRE_AREA());
            etArea.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etArea.setTextColor(getThemeAccentColor(this));
            etArea.setEnabled(false);

            etVoltajeParams.gravity = Gravity.CENTER;
            etVoltajeParams.width = 150;
            etVoltaje.setLayoutParams(etVoltajeParams);
            //etVoltaje.setHint("Voltaje");
            etVoltaje.setText("");
            etVoltaje.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etVoltaje.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            etVoltaje.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etVoltaje.setTextColor(getThemeAccentColor(this));
            etVoltaje.setEnabled(true);

            etCorrienteParams.gravity = Gravity.CENTER;
            etCorrienteParams.width = 150;
            etCorriente.setLayoutParams(etCorrienteParams);
            //etCorriente.setHint("Corriente");
            etCorriente.setText("");
            etCorriente.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etCorriente.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            etCorriente.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etCorriente.setTextColor(getThemeAccentColor(this));
            etCorriente.setEnabled(true);

            etPotenciaParams.gravity = Gravity.CENTER;
            etPotenciaParams.width = 150;
            etPotencia.setLayoutParams(etPotenciaParams);
            //etPotencia.setHint("Potencia");
            etPotencia.setText("");
            etPotencia.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etPotencia.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etPotencia.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            etPotencia.setTextColor(getThemeAccentColor(this));
            etPotencia.setEnabled(true);

            swPDUParams.gravity = Gravity.CENTER;
            swPDUParams.width = 100;
            swPDU.setLayoutParams(swPDUParams);
            swPDU.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            swPDU.setTextColor(getThemeAccentColor(this));
            swPDU.setText("A-E");
            swPDU.setEnabled(true);
            swPDU.setChecked(true);

            swPDU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((Switch) v).isChecked()) {
                        etVoltaje.setEnabled(true);
                        etCorriente.setEnabled(true);
                        etPotencia.setEnabled(true);
                        etVoltaje.setText("");
                        etCorriente.setText("");
                        etPotencia.setText("");
                        etVoltaje.setError(null);
                        etCorriente.setError(null);
                        etPotencia.setError(null);
                        etVoltaje.requestFocus();
                    } else {
                        etVoltaje.setEnabled(false);
                        etCorriente.setEnabled(false);
                        etPotencia.setEnabled(false);
                        etVoltaje.setText("");
                        etCorriente.setText("");
                        etPotencia.setText("");
                        etVoltaje.setError(null);
                        etCorriente.setError(null);
                        etPotencia.setError(null);
                        etVoltaje.requestFocus();
                    }
                }
            });

            tilArea.addView(etArea);
            tilVoltaje.addView(etVoltaje);
            tilCorriente.addView(etCorriente);
            tilPotencia.addView(etPotencia);

            lFilasAreas.addView(tilArea);
            lFilasAreas.addView(tilVoltaje);
            lFilasAreas.addView(tilCorriente);
            lFilasAreas.addView(tilPotencia);
            lFilasAreas.addView(swPDU);

            lBUSWAY.addView(lFilasAreas);

        }
    }

    public void DIBUJAR_FILAS_DE_TANQUE_ECARO_EN_LAYOUT() {
        if (ListaTanque.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaTanque.size(); i++) {

            final LinearLayout lFilasAreas = new LinearLayout(this);

            final TextInputLayout tilArea = new TextInputLayout(this);

            final EditText etArea = new EditText(this);
            final Switch swRojo = new Switch(this);
            final Switch swNaranja = new Switch(this);
            final Switch swVerde = new Switch(this);

            lFilasAreasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            swColorParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasAreas.setLayoutParams(lFilasAreasParams);
            lFilasAreas.setOrientation(LinearLayout.HORIZONTAL);
            lFilasAreas.setTag(ListaTanque.get(i).getID());
            lFilasAreas.setId(i);

            etAreaParams.gravity = Gravity.CENTER;
            etAreaParams.width = 150;
            etArea.setLayoutParams(etAreaParams);
            //etArea.setHint("Tanque");
            etArea.setText(ListaTanque.get(i).getNOMBRE_AREA());
            etArea.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etArea.setTextColor(getThemeAccentColor(this));
            etArea.setEnabled(false);

            swColorParams.gravity = Gravity.CENTER;
            swColorParams.width = 150;
            swRojo.setLayoutParams(swColorParams);
            swRojo.setEnabled(true);
            swRojo.setSelected(false);

            swColorParams.gravity = Gravity.CENTER;
            swColorParams.width = 150;
            swNaranja.setLayoutParams(swColorParams);
            swNaranja.setEnabled(true);
            swNaranja.setSelected(false);

            swColorParams.gravity = Gravity.CENTER;
            swColorParams.width = 150;
            swVerde.setLayoutParams(swColorParams);
            swVerde.setEnabled(true);
            swVerde.setChecked(true);

            swRojo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((Switch) v).isChecked()) {
                        swNaranja.setChecked(false);
                        swVerde.setChecked(false);
                    }
                }
            });

            swNaranja.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((Switch) v).isChecked()) {
                        swRojo.setChecked(false);
                        swVerde.setChecked(false);
                    }
                }
            });

            swVerde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((Switch) v).isChecked()) {
                        swRojo.setChecked(false);
                        swNaranja.setChecked(false);
                    }
                }
            });

            tilArea.addView(etArea);

            lFilasAreas.addView(tilArea);
            lFilasAreas.addView(swRojo);
            lFilasAreas.addView(swNaranja);
            lFilasAreas.addView(swVerde);

            lTANQUE.addView(lFilasAreas);

        }
    }

    public void DIBUJAR_FILAS_DE_XCOM_EN_LAYOUT() {
        if (ListaXcom.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaXcom.size(); i++) {

            final LinearLayout lFilasAreas = new LinearLayout(this);

            final TextInputLayout tilArea = new TextInputLayout(this);

            final EditText etArea = new EditText(this);
            final Switch swAlerta = new Switch(this);
            final Switch swVoltaje = new Switch(this);
            final Switch swAC = new Switch(this);

            lFilasAreasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            swColorParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasAreas.setLayoutParams(lFilasAreasParams);
            lFilasAreas.setOrientation(LinearLayout.HORIZONTAL);
            lFilasAreas.setTag(ListaXcom.get(i).getID());
            lFilasAreas.setId(i);

            etAreaParams.gravity = Gravity.CENTER;
            etAreaParams.width = 150;
            etArea.setLayoutParams(etAreaParams);
            //etArea.setHint("Xcom");
            etArea.setText(ListaXcom.get(i).getNOMBRE_AREA());
            etArea.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etArea.setTextColor(getThemeAccentColor(this));
            etArea.setEnabled(false);

            swColorParams.gravity = Gravity.CENTER;
            swColorParams.width = 100;
            swAlerta.setLayoutParams(swColorParams);
            swAlerta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            swAlerta.setTextColor(getThemeAccentColor(this));
            swAlerta.setText("N-!");
            swAlerta.setEnabled(true);
            swAlerta.setSelected(false);

            swColorParams.gravity = Gravity.CENTER;
            swColorParams.width = 100;
            swVoltaje.setLayoutParams(swColorParams);
            swVoltaje.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            swVoltaje.setTextColor(getThemeAccentColor(this));
            swVoltaje.setText("A-E");
            swVoltaje.setEnabled(true);
            swVoltaje.setSelected(false);

            swColorParams.gravity = Gravity.CENTER;
            swColorParams.width = 100;
            swAC.setLayoutParams(swColorParams);
            swAC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            swAC.setTextColor(getThemeAccentColor(this));
            swAC.setText("A-E");
            swAC.setEnabled(true);

            tilArea.addView(etArea);

            lFilasAreas.addView(tilArea);
            lFilasAreas.addView(swAlerta);
            lFilasAreas.addView(swVoltaje);
            lFilasAreas.addView(swAC);

            lXCOM.addView(lFilasAreas);

        }
    }

    public void CARGAR_UMAS() {
        Crud_Area DATOS;
        DATOS = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Area = DATOS.CONSULTA_GENERAL_UMA_A34();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Area.moveToFirst() == false) {
            //el cursor está vacío
            ListaUma = new ArrayList<Area>();
            Tdo_Area = "";
            Id_Area = "";
            Tdo_Tipo_Area = "";
            Nombre_Area = "";
            return;
        }
        int P_TDO = T_Area.getColumnIndex(IT_Area.I_AREA.ID);
        int P_Id_Area = T_Area.getColumnIndex(IT_Area.I_AREA.ID_AREA);
        int P_Tipo_Area = T_Area.getColumnIndex(IT_Area.I_AREA.TIPO_AREA);
        int P_Nombre_Area = T_Area.getColumnIndex(IT_Area.I_AREA.NOMBRE_AREA);
        ListaUma = new ArrayList<Area>();
        for (T_Area.moveToFirst(); !T_Area.isAfterLast(); T_Area.moveToNext()) {
            Tdo_Area = T_Area.getString(P_TDO);
            Id_Area = T_Area.getString(P_Id_Area);
            Tdo_Tipo_Area = T_Area.getString(P_Tipo_Area);
            Nombre_Area = T_Area.getString(P_Nombre_Area);
            Area Area = new Area(Tdo_Area, Id_Area, Nombre_Area, Tdo_Tipo_Area);
            ListaUma.add(Area);
        }
        Tdo_Area = "";
        Id_Area = "";
        Tdo_Tipo_Area = "";
        Nombre_Area = "";
    }

    public void CARGAR_UPSA() {
        Crud_Area DATOS;
        DATOS = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Area = DATOS.CONSULTA_GENERAL_UPSA_A34();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Area.moveToFirst() == false) {
            //el cursor está vacío
            ListaUpsA = new ArrayList<Area>();
            Tdo_Area = "";
            Id_Area = "";
            Tdo_Tipo_Area = "";
            Nombre_Area = "";
            return;
        }
        int P_TDO = T_Area.getColumnIndex(IT_Area.I_AREA.ID);
        int P_Id_Area = T_Area.getColumnIndex(IT_Area.I_AREA.ID_AREA);
        int P_Tipo_Area = T_Area.getColumnIndex(IT_Area.I_AREA.TIPO_AREA);
        int P_Nombre_Area = T_Area.getColumnIndex(IT_Area.I_AREA.NOMBRE_AREA);
        ListaUpsA = new ArrayList<Area>();
        for (T_Area.moveToFirst(); !T_Area.isAfterLast(); T_Area.moveToNext()) {
            Tdo_Area = T_Area.getString(P_TDO);
            Id_Area = T_Area.getString(P_Id_Area);
            Tdo_Tipo_Area = T_Area.getString(P_Tipo_Area);
            Nombre_Area = T_Area.getString(P_Nombre_Area);
            Area Area = new Area(Tdo_Area, Id_Area, Nombre_Area, Tdo_Tipo_Area);
            ListaUpsA.add(Area);
        }
        Tdo_Area = "";
        Id_Area = "";
        Tdo_Tipo_Area = "";
        Nombre_Area = "";
    }

    public void CARGAR_UPSB() {
        Crud_Area DATOS;
        DATOS = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Area = DATOS.CONSULTA_GENERAL_UPSB_A34();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Area.moveToFirst() == false) {
            //el cursor está vacío
            ListaUpsB = new ArrayList<Area>();
            Tdo_Area = "";
            Id_Area = "";
            Tdo_Tipo_Area = "";
            Nombre_Area = "";
            return;
        }
        int P_TDO = T_Area.getColumnIndex(IT_Area.I_AREA.ID);
        int P_Id_Area = T_Area.getColumnIndex(IT_Area.I_AREA.ID_AREA);
        int P_Tipo_Area = T_Area.getColumnIndex(IT_Area.I_AREA.TIPO_AREA);
        int P_Nombre_Area = T_Area.getColumnIndex(IT_Area.I_AREA.NOMBRE_AREA);
        ListaUpsB = new ArrayList<Area>();
        for (T_Area.moveToFirst(); !T_Area.isAfterLast(); T_Area.moveToNext()) {
            Tdo_Area = T_Area.getString(P_TDO);
            Id_Area = T_Area.getString(P_Id_Area);
            Tdo_Tipo_Area = T_Area.getString(P_Tipo_Area);
            Nombre_Area = T_Area.getString(P_Nombre_Area);
            Area Area = new Area(Tdo_Area, Id_Area, Nombre_Area, Tdo_Tipo_Area);
            ListaUpsB.add(Area);
        }
        Tdo_Area = "";
        Id_Area = "";
        Tdo_Tipo_Area = "";
        Nombre_Area = "";
    }

    public void CARGAR_BUSWAY() {
        Crud_Area DATOS;
        DATOS = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Area = DATOS.CONSULTA_GENERAL_BUSWAY_A34();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Area.moveToFirst() == false) {
            //el cursor está vacío
            ListaBusway = new ArrayList<Area>();
            Tdo_Area = "";
            Id_Area = "";
            Tdo_Tipo_Area = "";
            Nombre_Area = "";
            return;
        }
        int P_TDO = T_Area.getColumnIndex(IT_Area.I_AREA.ID);
        int P_Id_Area = T_Area.getColumnIndex(IT_Area.I_AREA.ID_AREA);
        int P_Tipo_Area = T_Area.getColumnIndex(IT_Area.I_AREA.TIPO_AREA);
        int P_Nombre_Area = T_Area.getColumnIndex(IT_Area.I_AREA.NOMBRE_AREA);
        ListaBusway = new ArrayList<Area>();
        for (T_Area.moveToFirst(); !T_Area.isAfterLast(); T_Area.moveToNext()) {
            Tdo_Area = T_Area.getString(P_TDO);
            Id_Area = T_Area.getString(P_Id_Area);
            Tdo_Tipo_Area = T_Area.getString(P_Tipo_Area);
            Nombre_Area = T_Area.getString(P_Nombre_Area);
            Area Area = new Area(Tdo_Area, Id_Area, Nombre_Area, Tdo_Tipo_Area);
            ListaBusway.add(Area);
        }
        Tdo_Area = "";
        Id_Area = "";
        Tdo_Tipo_Area = "";
        Nombre_Area = "";
    }

    public void CARGAR_TANQUE_ECARO() {
        Crud_Area DATOS;
        DATOS = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Area = DATOS.CONSULTA_GENERAL_TANQUE_ECARO_A34();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Area.moveToFirst() == false) {
            //el cursor está vacío
            ListaTanque = new ArrayList<Area>();
            Tdo_Area = "";
            Id_Area = "";
            Tdo_Tipo_Area = "";
            Nombre_Area = "";
            return;
        }
        int P_TDO = T_Area.getColumnIndex(IT_Area.I_AREA.ID);
        int P_Id_Area = T_Area.getColumnIndex(IT_Area.I_AREA.ID_AREA);
        int P_Tipo_Area = T_Area.getColumnIndex(IT_Area.I_AREA.TIPO_AREA);
        int P_Nombre_Area = T_Area.getColumnIndex(IT_Area.I_AREA.NOMBRE_AREA);
        ListaTanque = new ArrayList<Area>();
        for (T_Area.moveToFirst(); !T_Area.isAfterLast(); T_Area.moveToNext()) {
            Tdo_Area = T_Area.getString(P_TDO);
            Id_Area = T_Area.getString(P_Id_Area);
            Tdo_Tipo_Area = T_Area.getString(P_Tipo_Area);
            Nombre_Area = T_Area.getString(P_Nombre_Area);
            Area Area = new Area(Tdo_Area, Id_Area, Nombre_Area, Tdo_Tipo_Area);
            ListaTanque.add(Area);
        }
        Tdo_Area = "";
        Id_Area = "";
        Tdo_Tipo_Area = "";
        Nombre_Area = "";
    }

    public void CARGAR_XCOM() {
        Crud_Area DATOS;
        DATOS = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Area = DATOS.CONSULTA_GENERAL_XCOM_A34();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Area.moveToFirst() == false) {
            //el cursor está vacío
            ListaXcom = new ArrayList<Area>();
            Tdo_Area = "";
            Id_Area = "";
            Tdo_Tipo_Area = "";
            Nombre_Area = "";
            return;
        }
        int P_TDO = T_Area.getColumnIndex(IT_Area.I_AREA.ID);
        int P_Id_Area = T_Area.getColumnIndex(IT_Area.I_AREA.ID_AREA);
        int P_Tipo_Area = T_Area.getColumnIndex(IT_Area.I_AREA.TIPO_AREA);
        int P_Nombre_Area = T_Area.getColumnIndex(IT_Area.I_AREA.NOMBRE_AREA);
        ListaXcom = new ArrayList<Area>();
        for (T_Area.moveToFirst(); !T_Area.isAfterLast(); T_Area.moveToNext()) {
            Tdo_Area = T_Area.getString(P_TDO);
            Id_Area = T_Area.getString(P_Id_Area);
            Tdo_Tipo_Area = T_Area.getString(P_Tipo_Area);
            Nombre_Area = T_Area.getString(P_Nombre_Area);
            Area Area = new Area(Tdo_Area, Id_Area, Nombre_Area, Tdo_Tipo_Area);
            ListaXcom.add(Area);
        }
        Tdo_Area = "";
        Id_Area = "";
        Tdo_Tipo_Area = "";
        Nombre_Area = "";
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, 0);
        return dateFormat.format(calendar.getTime());
    }

    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, 0);
        return dateFormat.format(calendar.getTime());
    }

    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm", Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, 0);
        return dateFormat.format(calendar.getTime());
    }

    private String getDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd", Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, 0);
        return dateFormat.format(calendar.getTime());
    }

    public void CARGAR_LISTA_RONDAS() {
        for (int i = 0; i <= 100; i++) {
            ListaRonda.add(String.valueOf(i));
        }
    }

    public void OBTENERTURNO() {
        DATOS = Crud_Inicio_Sesion.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Turno = DATOS.CONSULTA_GENERAL_TURNO_POR_HORA();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Turno.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Turno_1 = "";
        }
        int P_Tdo_Turno = T_Turno.getColumnIndex(IT_Turno.I_TURNO.ID);
        for (T_Turno.moveToFirst(); !T_Turno.isAfterLast(); T_Turno.moveToNext()) {
            Tdo_Turno_1 = T_Turno.getString(P_Tdo_Turno);
        }
    }

}
