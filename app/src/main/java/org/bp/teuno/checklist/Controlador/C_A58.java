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
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.bp.teuno.checklist.Modelo.A58_Chiller;
import org.bp.teuno.checklist.Modelo.A58_Pasillo;
import org.bp.teuno.checklist.Modelo.Chiller;
import org.bp.teuno.checklist.Modelo.Pasillo;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_A58_Pasillo;
import org.bp.teuno.checklist.SQLite.IT_Chiller;
import org.bp.teuno.checklist.SQLite.IT_Parametros;
import org.bp.teuno.checklist.SQLite.IT_Pasillo;
import org.bp.teuno.checklist.SQLite.IT_Turno;
import org.bp.teuno.checklist.UI.Crud_A58;
import org.bp.teuno.checklist.UI.Crud_Chiller;
import org.bp.teuno.checklist.UI.Crud_Inicio_Sesion;
import org.bp.teuno.checklist.UI.Crud_Parametros;
import org.bp.teuno.checklist.UI.Crud_Pasillo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class C_A58 extends AppCompatActivity {

    private final long interval = 1 * 1000;
    String Tdo_A58 = "", Temperatura = "", Temp_Sensor = "", Humedad = "", Temp_Suministro = "", Temp_Retorno = "", Hora_Ingreso = "", Hora_Salida = "", Turno = "";
    int Ronda = 0;
    String Tdo_Pasillo = "", Id_Pasillo = "", Nombre_Pasillo = "", Tdo_Tipo_Pasillo = "";
    String Tdo_Chiller = "", Id_Chiller = "", Nombre_Chiller = "";
    String Tdo_Parametro = "", TempMinima = "", TempMaxima = "", HumMinima = "", HumMaxima = "", TempSumMinima = "", TempSumMaxima = "", TempRetMinima = "", TempRetMaxima = "";
    String isMenuA56 = "1";
    LinearLayout lPasillos, lChillers;
    TextView txMensaje1, txMensaje2;
    LayoutParams lFilasPasillosParams, lFilasChillersParams, etPasilloParams, etChillerParams, etTemperaturaParams, etHumedadParams, etTempSuministroParams, etTempRetornoParams, checkParams;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabGuardar, fabA34, fabA56, fabA71;
    ArrayList<A58_Pasillo> ListaA58Pasillo = new ArrayList<A58_Pasillo>();
    ArrayList<A58_Chiller> ListaA58Chiller = new ArrayList<A58_Chiller>();
    ArrayList<Pasillo> ListaPasillo = new ArrayList<Pasillo>();
    ArrayList<Chiller> ListaChiller = new ArrayList<Chiller>();
    ArrayList<String> ListaRonda = new ArrayList<String>();
    Cursor T_A58_Pasillo, T_A58_Chiller, T_Pasillo, T_Chiller;
    Crud_A58 Datos1;
    String Id_Operador = "", Nombre_Operador, Tdo_Turno, Turno_Operador;
    CountDownTimer countDownTimer;
    int bandera = 0;
    Crud_Inicio_Sesion DATOS;
    Cursor T_Turno;
    String Tdo_Turno_1;
    private String Tdo_Grupo_1;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME
    private CoordinatorLayout mLinearLayout;
    private Context mContext;
    private Activity mActivity;
    private PopupWindow mPopupWindow;

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
        setContentView(R.layout.l_a58);

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

        lPasillos = (LinearLayout) findViewById(R.id.lPasillos);
        lChillers = (LinearLayout) findViewById(R.id.lChillers);
        txMensaje1 = (TextView) findViewById(R.id.txMensaje1);
        txMensaje2 = (TextView) findViewById(R.id.txMensaje2);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabA34 = (FloatingActionButton) findViewById(R.id.fabA34);
        fabA56 = (FloatingActionButton) findViewById(R.id.fabA56);
        fabA71 = (FloatingActionButton) findViewById(R.id.fabA71);

        Hora_Ingreso = getTime();

        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabA34, "Formulario A.34");
        TooltipCompat.setTooltipText(fabA56, "Formulario A.56");
        TooltipCompat.setTooltipText(fabA71, "Formulario A.71");

        CARGAR_PASILLOS();
        CARGAR_CHILLERS();
        CARGAR_PARAMETROS();
        CARGAR_LISTA_RONDAS();
        DIBUJAR_FILAS_DE_PASILLOS_EN_LAYOUT();
        DIBUJAR_FILAS_DE_CHILLERS_EN_LAYOUT();

        if (ListaPasillo.size() == 0) {
            txMensaje1.setText("No existen pasillos activos");
        } else if (ListaPasillo.size() == 1) {
            txMensaje1.setText("Se encontró " + String.valueOf(ListaPasillo.size()) + " pasillo activo");
        } else {
            txMensaje1.setText("Se encontraron " + String.valueOf(ListaPasillo.size()) + " pasillos activos");
        }

        if (ListaChiller.size() == 0) {
            txMensaje2.setText("No existen chillers activos");
        } else if (ListaChiller.size() == 1) {
            txMensaje2.setText("Se encontró " + String.valueOf(ListaChiller.size()) + " chiller activo");
        } else {
            txMensaje2.setText("Se encontraron " + String.valueOf(ListaChiller.size()) + " chillers activos");
        }

        fabA34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENERTURNO();
                if (!Tdo_Turno.equals(Tdo_Turno_1)) {
                    Toast.makeText(
                            getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A58.this);
                    myBulid.setTitle("Formulario A.34");
                    myBulid.setIcon(R.drawable.motor);
                    myBulid.setMessage("¿Desea ingresar al formulario A.34?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(C_A58.this, C_A34.class);
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
                    final Intent intent = new Intent(C_A58.this, C_A56.class);
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

        fabA71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENERTURNO();
                if (!Tdo_Turno.equals(Tdo_Turno_1)) {
                    Toast.makeText(
                            getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A58.this);
                    myBulid.setTitle("Formulario A.71");
                    myBulid.setIcon(R.drawable.luminosidad1);
                    myBulid.setMessage("¿Desea ingresar al formulario A.71?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(C_A58.this, C_A71.class);
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
                    if (lPasillos.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen pasillos activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (lChillers.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen chillers activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_PASILLOS() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_CORRECTOS_PASILLOS() == false) {
                        return;
                    }
                    if (SE_SELECCIONO_CHILLER() == false) {
                        return;
                    }
                    if (SE_SELECCIONO_VARIOS_CHILLER() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_CHILLERS() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_CORRECTOS_CHILLERS() == false) {
                        return;
                    }
                    Ronda = CONSULTAR_RONDA() + 1;
                    LinearLayout ly1, ly2;
                    ListaA58Pasillo = new ArrayList<A58_Pasillo>();
                    Hora_Salida = getTime();
                    for (int i = 0; i < lPasillos.getChildCount(); i++) {
                        ly1 = (LinearLayout) lPasillos.getChildAt(i);
                        Tdo_Pasillo = ly1.getTag().toString();
                        final TextInputLayout tilTemp_Sensor = (TextInputLayout) ly1.getChildAt(1);
                        final TextInputLayout tilTemperatura = (TextInputLayout) ly1.getChildAt(2);
                        final TextInputLayout tilHumedad = (TextInputLayout) ly1.getChildAt(3);
                        Temp_Sensor = tilTemp_Sensor.getEditText().getText().toString();
                        Temperatura = tilTemperatura.getEditText().getText().toString();
                        Humedad = tilHumedad.getEditText().getText().toString();
                        A58_Pasillo a58_pasillo = new A58_Pasillo("", Tdo_Pasillo, Temp_Sensor, Temperatura, Humedad, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA58Pasillo.add(a58_pasillo);
                    }
                    for (int i = 0; i < lChillers.getChildCount(); i++) {
                        ly2 = (LinearLayout) lChillers.getChildAt(i);
                        Tdo_Chiller = ly2.getTag().toString();
                        final TextInputLayout tilTempSuministro = (TextInputLayout) ly2.getChildAt(2);
                        final TextInputLayout tilTempRetorno = (TextInputLayout) ly2.getChildAt(3);
                        Temp_Suministro = tilTempSuministro.getEditText().getText().toString().equals("") ? "0" : tilTempSuministro.getEditText().getText().toString();
                        Temp_Retorno = tilTempRetorno.getEditText().getText().toString().equals("") ? "0" : tilTempRetorno.getEditText().getText().toString();
                        A58_Chiller a58_chiller = new A58_Chiller("", Tdo_Chiller, Temp_Suministro, Temp_Retorno, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA58Chiller.add(a58_chiller);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_A58.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Datos1 = Crud_A58.OBTENER_INSTANCIA(getApplicationContext());
                                            long insertedResult1 = 0, insertedResult2 = 0;
                                            try {
                                                Datos1.GETBD().beginTransaction();
                                                for (int i = 0; i < ListaA58Pasillo.size(); i++) {
                                                    insertedResult1 = Datos1.INSERTAR_A58_PASILLO(ListaA58Pasillo.get(i));
                                                    if (insertedResult1 == -1) {
                                                        Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                    }
                                                }
                                                if (insertedResult1 != -1) {
                                                    for (int i = 0; i < ListaA58Chiller.size(); i++) {
                                                        insertedResult2 = Datos1.INSERTAR_A58_CHILLER(ListaA58Chiller.get(i));
                                                        if (insertedResult2 == -1) {
                                                            Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                            throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                        }
                                                    }
                                                }
                                                Datos1.GETBD().setTransactionSuccessful();
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
                Intent intent = new Intent(C_A58.this, C_Menu_Principal.class);
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

    public boolean VALIDAR_DATOS_VACIOS_PASILLOS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillos.getChildCount(); i++) {
            ly = (LinearLayout) lPasillos.getChildAt(i);
            final TextInputLayout tilTemp_Sensor = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilTemperatura = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilHumedad = (TextInputLayout) ly.getChildAt(3);
            if (tilTemp_Sensor.getEditText().getText().toString().equals("")) {
                tilTemp_Sensor.requestFocus();
                tilTemp_Sensor.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillos.getChildCount();
            }
            if (tilTemperatura.getEditText().getText().toString().equals("")) {
                tilTemperatura.requestFocus();
                tilTemperatura.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillos.getChildCount();
            }
            if (tilHumedad.getEditText().getText().toString().equals("")) {
                tilHumedad.requestFocus();
                tilHumedad.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillos.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_PASILLOS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillos.getChildCount(); i++) {
            ly = (LinearLayout) lPasillos.getChildAt(i);
            final TextInputLayout tilTemp_Sensor = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilTemperatura = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilHumedad = (TextInputLayout) ly.getChildAt(3);
            if (!tilTemp_Sensor.getEditText().getText().toString().equals("")) {
                if (tilTemp_Sensor.getEditText().getText().toString().equals("0")) {
                    tilTemp_Sensor.requestFocus();
                    tilTemp_Sensor.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lPasillos.getChildCount();
                }
            }
            if (!tilTemperatura.getEditText().getText().toString().equals("")) {
                if (tilTemperatura.getEditText().getText().toString().equals("0")) {
                    tilTemperatura.requestFocus();
                    tilTemperatura.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lPasillos.getChildCount();
                }
            }
            if (!tilHumedad.getEditText().getText().toString().equals("")) {
                if (tilHumedad.getEditText().getText().toString().equals("0")) {
                    tilHumedad.requestFocus();
                    tilHumedad.getEditText().setError("El valor ingresado no puede ser 0");
                    condicion = 0;
                    i = lPasillos.getChildCount();
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_ES_NUMERO_PASILLOS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillos.getChildCount(); i++) {
            ly = (LinearLayout) lPasillos.getChildAt(i);
            final TextInputLayout tilTemperatura = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilHumedad = (TextInputLayout) ly.getChildAt(2);
            if (isNumeric(tilTemperatura.getEditText().getText().toString())) {
                tilTemperatura.requestFocus();
                tilTemperatura.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillos.getChildCount();
            }
            if (isNumeric(tilHumedad.getEditText().getText().toString())) {
                tilHumedad.requestFocus();
                tilHumedad.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillos.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_VACIOS_CHILLERS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lChillers.getChildCount(); i++) {
            ly = (LinearLayout) lChillers.getChildAt(i);
            final CheckBox check = (CheckBox) ly.getChildAt(0);
            final TextInputLayout tilTempSuministro = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilTempRetorno = (TextInputLayout) ly.getChildAt(3);
            if (check.isChecked()) {
                if (tilTempSuministro.getEditText().getText().toString().equals("")) {
                    tilTempSuministro.requestFocus();
                    tilTempSuministro.getEditText().setError("Este campo no puede estar vacío");
                    condicion = 0;
                    i = lChillers.getChildCount();
                }
                if (tilTempRetorno.getEditText().getText().toString().equals("")) {
                    tilTempRetorno.requestFocus();
                    tilTempRetorno.getEditText().setError("Este campo no puede estar vacío");
                    condicion = 0;
                    i = lChillers.getChildCount();
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_CHILLERS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lChillers.getChildCount(); i++) {
            ly = (LinearLayout) lChillers.getChildAt(i);
            final CheckBox check = (CheckBox) ly.getChildAt(0);
            final TextInputLayout tilTempSuministro = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilTempRetorno = (TextInputLayout) ly.getChildAt(3);
            if (check.isChecked()) {
                if (!tilTempSuministro.getEditText().getText().toString().equals("")) {
                    if (tilTempSuministro.getEditText().getText().toString().equals("0")) {
                        tilTempSuministro.requestFocus();
                        tilTempSuministro.getEditText().setError("El valor ingresado no puede ser 0");
                        condicion = 0;
                        i = lChillers.getChildCount();
                    }
                }
                if (!tilTempRetorno.getEditText().getText().toString().equals("")) {
                    if (tilTempRetorno.getEditText().getText().toString().equals("0")) {
                        tilTempRetorno.requestFocus();
                        tilTempRetorno.getEditText().setError("El valor ingresado no puede ser 0");
                        condicion = 0;
                        i = lChillers.getChildCount();
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

    public boolean VALIDAR_ES_NUMERO_CHILLERS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lChillers.getChildCount(); i++) {
            ly = (LinearLayout) lChillers.getChildAt(i);
            final CheckBox check = (CheckBox) ly.getChildAt(0);
            final TextInputLayout tilTempSuministro = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilTempRetorno = (TextInputLayout) ly.getChildAt(3);
            if (check.isChecked()) {
                if (isNumeric(tilTempSuministro.getEditText().getText().toString())) {
                    tilTempSuministro.requestFocus();
                    tilTempSuministro.getEditText().setError("La cantidad ingresada no es correcta");
                    condicion = 0;
                    i = lChillers.getChildCount();
                }
                if (isNumeric(tilTempRetorno.getEditText().getText().toString())) {
                    tilTempRetorno.requestFocus();
                    tilTempRetorno.getEditText().setError("La cantidad ingresada no es correcta");
                    condicion = 0;
                    i = lChillers.getChildCount();
                }
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean SE_SELECCIONO_CHILLER() {
        int contador = 0;
        LinearLayout ly;
        for (int i = 0; i < lChillers.getChildCount(); i++) {
            ly = (LinearLayout) lChillers.getChildAt(i);
            final CheckBox check = (CheckBox) ly.getChildAt(0);
            if (!check.isChecked()) {
                contador = contador + 1;
            }
        }
        if (contador == lChillers.getChildCount()) {
            Toast.makeText(getApplicationContext(), "Seleccione 1 Chiller de la lista", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean SE_SELECCIONO_VARIOS_CHILLER() {
        int contador = 0;
        LinearLayout ly;
        for (int i = 0; i < lChillers.getChildCount(); i++) {
            ly = (LinearLayout) lChillers.getChildAt(i);
            final CheckBox check = (CheckBox) ly.getChildAt(0);
            if (check.isChecked()) {
                contador = contador + 1;
            }
        }
        if (contador > 1) {
            Toast.makeText(getApplicationContext(), "Seleccione sólo 1 Chiller de la lista", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public int CONSULTAR_RONDA() {
        int ronda = 0;
        Crud_A58 DATOS;
        DATOS = Crud_A58.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_A58_Pasillo = DATOS.CONSULTA_GENERAL_RONDA(Tdo_Turno, getDate());
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_A58_Pasillo.moveToFirst() == false) {
            //el cursor está vacío
            ronda = 0;
        } else {
            int P_Ronda = T_A58_Pasillo.getColumnIndex(IT_A58_Pasillo.I_A58_Pasillo.RONDA);
            for (T_A58_Pasillo.moveToFirst(); !T_A58_Pasillo.isAfterLast(); T_A58_Pasillo.moveToNext()) {
                for (int i = 0; i < ListaRonda.size(); i++) {
                    if (T_A58_Pasillo.getString(P_Ronda).equals(ListaRonda.get(i).toString())) {
                        ronda = Integer.valueOf(ListaRonda.get(i).toString());
                    }
                }
            }
        }
        return ronda;
    }

    public void LIMPIAR_DATOS() {
        Tdo_Pasillo = "";
        Temperatura = "";
        Humedad = "";
        Temp_Suministro = "";
        Temp_Retorno = "";
        LinearLayout ly1, ly2;
        ListaA58Pasillo = new ArrayList<A58_Pasillo>();
        for (int i = 0; i < lPasillos.getChildCount(); i++) {
            ly1 = (LinearLayout) lPasillos.getChildAt(i);
            final TextInputLayout tilTemp_Sensor = (TextInputLayout) ly1.getChildAt(1);
            final TextInputLayout tilTemperatura = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilHumedad = (TextInputLayout) ly1.getChildAt(3);
            tilTemp_Sensor.getEditText().setText("");
            tilTemperatura.getEditText().setText("");
            tilHumedad.getEditText().setText("");
            tilTemp_Sensor.getEditText().setError(null);
            tilTemperatura.getEditText().setError(null);
            tilHumedad.getEditText().setError(null);
        }
        ListaA58Chiller = new ArrayList<A58_Chiller>();
        for (int i = 0; i < lChillers.getChildCount(); i++) {
            ly1 = (LinearLayout) lChillers.getChildAt(i);
            final CheckBox check = (CheckBox) ly1.getChildAt(0);
            final TextInputLayout tilTempSuministro = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilTempRetorno = (TextInputLayout) ly1.getChildAt(3);
            check.setChecked(false);
            tilTempSuministro.getEditText().setText("");
            tilTempRetorno.getEditText().setText("");
            tilTempSuministro.getEditText().setEnabled(false);
            tilTempRetorno.getEditText().setEnabled(false);
            tilTempSuministro.getEditText().setError(null);
            tilTempRetorno.getEditText().setError(null);
        }

    }

    public void DIBUJAR_FILAS_DE_PASILLOS_EN_LAYOUT() {
        if (ListaPasillo.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaPasillo.size(); i++) {

            final LinearLayout lFilasPasillos = new LinearLayout(this);

            final TextInputLayout tilPasillo = new TextInputLayout(this);
            final TextInputLayout tilTemperatura = new TextInputLayout(this);
            final TextInputLayout tilTemp_Sensor = new TextInputLayout(this);
            final TextInputLayout tilHumedad = new TextInputLayout(this);

            final EditText etPasillo = new EditText(this);
            final EditText etTemperatura = new EditText(this);
            final EditText etTemp_Sensor = new EditText(this);
            final EditText etHumedad = new EditText(this);

            lFilasPasillosParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etPasilloParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etTemperaturaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etHumedadParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasPasillos.setLayoutParams(lFilasPasillosParams);
            lFilasPasillos.setOrientation(LinearLayout.HORIZONTAL);
            lFilasPasillos.setTag(ListaPasillo.get(i).getID());
            lFilasPasillos.setId(i);

            etPasilloParams.gravity = Gravity.CENTER;
            etPasilloParams.width = 200;
            etPasillo.setLayoutParams(etPasilloParams);
            //etPasillo.setHint("Pasillo");
            etPasillo.setText(ListaPasillo.get(i).getNOMBRE_PASILLO());
            etPasillo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etPasillo.setTextColor(getThemeAccentColor(this));
            etPasillo.setEnabled(false);

            etTemperaturaParams.gravity = Gravity.CENTER;
            etTemperaturaParams.width = 200;
            etTemperatura.setLayoutParams(etTemperaturaParams);
            etTemperatura.setId(i);
            //etTemperatura.setHint("Temperatura");
            etTemperatura.setText("");
            etTemperatura.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etTemperatura.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etTemperatura.setTextColor(getThemeAccentColor(this));
            etTemperatura.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

            etTemp_Sensor.setLayoutParams(etTemperaturaParams);
            etTemp_Sensor.setId(i);
            //etTemp_Sensor.setHint("Temperatura");
            etTemp_Sensor.setText("");
            etTemp_Sensor.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etTemp_Sensor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etTemp_Sensor.setTextColor(getThemeAccentColor(this));
            etTemp_Sensor.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

            etHumedadParams.gravity = Gravity.CENTER;
            etHumedadParams.width = 200;
            etHumedad.setLayoutParams(etHumedadParams);
            //etHumedad.setHint("Húmedad");
            etHumedad.setText("");
            etHumedad.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etHumedad.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etHumedad.setTextColor(getThemeAccentColor(this));
            etHumedad.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});

            etTemperatura.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (etTemperatura.getText() == null) {
                        return;
                    }
                    if (etTemperatura.getText().toString().equals("")) {
                        return;
                    }
                    try {
                        if (Double.parseDouble(etTemperatura.getText().toString()) < Double.parseDouble(TempMinima)) {
                            etTemperatura.setError("Advertencia, la temperatura es muy baja");
                        }
                    } catch (java.lang.NumberFormatException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                    try {
                        if (Double.parseDouble(etTemperatura.getText().toString()) > Double.parseDouble(TempMaxima)) {
                            etTemperatura.setError("Advertencia, la temperatura es muy alta");
                        }
                    } catch (java.lang.NumberFormatException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                }
            });

            etHumedad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (etHumedad.getText() == null) {
                        return;
                    }
                    if (etHumedad.getText().toString().equals("")) {
                        return;
                    }
                    try {
                        if (Double.valueOf(etHumedad.getText().toString()).doubleValue() < Double.valueOf(HumMinima).doubleValue()) {
                            etHumedad.setError("Advertencia, la húmedad es muy baja");
                        }
                    } catch (java.lang.NumberFormatException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                    try {
                        if (Double.valueOf(etHumedad.getText().toString()).doubleValue() > Double.valueOf(HumMaxima).doubleValue()) {
                            etHumedad.setError("Advertencia, la húmedad es muy alta");
                        }
                    } catch (java.lang.NumberFormatException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                }
            });

            tilPasillo.addView(etPasillo);
            tilTemperatura.addView(etTemperatura);
            tilTemp_Sensor.addView(etTemp_Sensor);
            tilHumedad.addView(etHumedad);

            lFilasPasillos.addView(tilPasillo);
            lFilasPasillos.addView(tilTemp_Sensor);
            lFilasPasillos.addView(tilTemperatura);
            lFilasPasillos.addView(tilHumedad);

            lPasillos.addView(lFilasPasillos);

        }

    }

    public void DIBUJAR_FILAS_DE_CHILLERS_EN_LAYOUT() {
        if (ListaChiller.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaChiller.size(); i++) {

            final LinearLayout lFilasChillers = new LinearLayout(this);

            final CheckBox check = new CheckBox(this);

            final TextInputLayout tilChiller = new TextInputLayout(this);
            final TextInputLayout tilTempSuministro = new TextInputLayout(this);
            final TextInputLayout tilTempRetorno = new TextInputLayout(this);

            final EditText etChiller = new EditText(this);
            final EditText etTempSuministro = new EditText(this);
            final EditText etTempRetorno = new EditText(this);

            lFilasChillersParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            checkParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etChillerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etTempSuministroParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etTempRetornoParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasChillers.setLayoutParams(lFilasChillersParams);
            lFilasChillers.setOrientation(LinearLayout.HORIZONTAL);
            lFilasChillers.setTag(ListaChiller.get(i).getID());
            lFilasChillers.setId(i);

            checkParams.gravity = Gravity.CENTER;
            checkParams.width = 50;
            check.setLayoutParams(checkParams);
            check.setChecked(false);

            etChillerParams.gravity = Gravity.CENTER;
            etChillerParams.width = 200;
            etChiller.setLayoutParams(etChillerParams);
            //etChiller.setHint("Chiller");
            etChiller.setText(ListaChiller.get(i).getNOMBRE_CHILLER());
            etChiller.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etChiller.setTextColor(getThemeAccentColor(this));
            etChiller.setEnabled(false);

            etTempSuministroParams.gravity = Gravity.CENTER;
            etTempSuministroParams.width = 200;
            etTempSuministro.setLayoutParams(etTempSuministroParams);
            etTempSuministro.setId(i);
            //etTempSuministro.setHint("Temp. Suministro");
            etTempSuministro.setText("");
            etTempSuministro.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etTempSuministro.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etTempSuministro.setTextColor(getThemeAccentColor(this));
            etTempSuministro.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
            etTempSuministro.setEnabled(false);

            etTempRetornoParams.gravity = Gravity.CENTER;
            etTempRetornoParams.width = 200;
            etTempRetorno.setLayoutParams(etTempRetornoParams);
            //etTempRetorno.setHint("Temp. Retorno");
            etTempRetorno.setText("");
            etTempRetorno.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etTempRetorno.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etTempRetorno.setTextColor(getThemeAccentColor(this));
            etTempRetorno.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
            etTempRetorno.setEnabled(false);

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((CheckBox) v).isChecked()) {
                        etTempSuministro.setEnabled(true);
                        etTempRetorno.setEnabled(true);
                        etTempSuministro.setText("");
                        etTempRetorno.setText("");
                        etTempSuministro.setError(null);
                        etTempRetorno.setError(null);
                        etTempSuministro.requestFocus();
                    } else {
                        etTempSuministro.setEnabled(false);
                        etTempRetorno.setEnabled(false);
                        etTempSuministro.setText("");
                        etTempRetorno.setText("");
                        etTempSuministro.setError(null);
                        etTempRetorno.setError(null);
                    }
                }
            });

            etTempSuministro.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (etTempSuministro.getText() == null) {
                        return;
                    }
                    if (etTempSuministro.getText().toString().equals("")) {
                        return;
                    }
                    try {
                        if (Double.parseDouble(etTempSuministro.getText().toString()) < Double.parseDouble(TempSumMinima)) {
                            etTempSuministro.setError("Advertencia, la temperatura de suministro es muy baja");
                        }
                    } catch (java.lang.NumberFormatException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                    try {
                        if (Double.parseDouble(etTempSuministro.getText().toString()) > Double.parseDouble(TempSumMaxima)) {
                            etTempSuministro.setError("Advertencia, la temperatura de suministro es muy alta");
                        }
                    } catch (java.lang.NumberFormatException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                }
            });

            etTempRetorno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (etTempRetorno.getText() == null) {
                        return;
                    }
                    if (etTempRetorno.getText().toString().equals("")) {
                        return;
                    }
                    try {
                        if (Double.parseDouble(etTempRetorno.getText().toString()) < Double.parseDouble(TempRetMinima)) {
                            etTempRetorno.setError("Advertencia, la temperatura de retorno es muy baja");
                        }
                    } catch (java.lang.NumberFormatException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                    try {
                        if (Double.parseDouble(etTempRetorno.getText().toString()) > Double.parseDouble(TempRetMaxima)) {
                            etTempRetorno.setError("Advertencia, la temperatura de retorno es muy alta");
                        }
                    } catch (java.lang.NumberFormatException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                }
            });

            tilChiller.addView(etChiller);
            tilTempSuministro.addView(etTempSuministro);
            tilTempRetorno.addView(etTempRetorno);

            lFilasChillers.addView(check);
            lFilasChillers.addView(tilChiller);
            lFilasChillers.addView(tilTempSuministro);
            lFilasChillers.addView(tilTempRetorno);

            lChillers.addView(lFilasChillers);

        }

    }

    public void CARGAR_PASILLOS() {
        Crud_Pasillo DATOS;
        DATOS = Crud_Pasillo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Pasillo = DATOS.CONSULTA_GENERAL_PASILLO();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Pasillo.moveToFirst() == false) {
            //el cursor está vacío
            ListaPasillo = new ArrayList<Pasillo>();
            Tdo_Pasillo = "";
            Id_Pasillo = "";
            Nombre_Pasillo = "";
            Tdo_Tipo_Pasillo = "";
            return;
        }
        int P_TDO = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.ID);
        int P_Id_Pasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.ID_PASILLO);
        int P_Nombre_Pasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.NOMBRE_PASILLO);
        int P_Id_TipoPasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.TIPO_PASILLO);
        ListaPasillo = new ArrayList<Pasillo>();
        for (T_Pasillo.moveToFirst(); !T_Pasillo.isAfterLast(); T_Pasillo.moveToNext()) {
            Tdo_Pasillo = T_Pasillo.getString(P_TDO);
            Id_Pasillo = T_Pasillo.getString(P_Id_Pasillo);
            Nombre_Pasillo = T_Pasillo.getString(P_Nombre_Pasillo);
            Tdo_Tipo_Pasillo = T_Pasillo.getString(P_Id_TipoPasillo);
            Pasillo Pasillo = new Pasillo(Tdo_Pasillo, Id_Pasillo, Nombre_Pasillo, Tdo_Tipo_Pasillo);
            ListaPasillo.add(Pasillo);
        }
        Tdo_Pasillo = "";
        Id_Pasillo = "";
        Nombre_Pasillo = "";
        Tdo_Tipo_Pasillo = "";
    }

    public void CARGAR_CHILLERS() {
        Crud_Chiller DATOS;
        DATOS = Crud_Chiller.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Chiller = DATOS.CONSULTA_GENERAL_CHILLER();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Chiller.moveToFirst() == false) {
            //el cursor está vacío
            ListaChiller = new ArrayList<Chiller>();
            Tdo_Chiller = "";
            Id_Chiller = "";
            Nombre_Chiller = "";
            return;
        }
        int P_TDO = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.ID);
        int P_Id_Chiller = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.ID_CHILLER);
        int P_Nombre_Chiller = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.NOMBRE_CHILLER);
        ListaChiller = new ArrayList<Chiller>();
        for (T_Chiller.moveToFirst(); !T_Chiller.isAfterLast(); T_Chiller.moveToNext()) {
            Tdo_Chiller = T_Chiller.getString(P_TDO);
            Id_Chiller = T_Chiller.getString(P_Id_Chiller);
            Nombre_Chiller = T_Chiller.getString(P_Nombre_Chiller);
            Chiller Chiller = new Chiller(Tdo_Chiller, Id_Chiller, Nombre_Chiller);
            ListaChiller.add(Chiller);
        }
        Tdo_Chiller = "";
        Id_Chiller = "";
        Nombre_Chiller = "";
    }

    public void CARGAR_PARAMETROS() {
        Crud_Parametros DATOS;
        Cursor T_Parametro;
        DATOS = Crud_Parametros.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Parametro = DATOS.CONSULTA_GENERAL_PARAMETROS();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Parametro.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Parametro = "";
            TempMinima = "";
            TempMaxima = "";
            HumMinima = "";
            HumMaxima = "";
            TempSumMinima = "";
            TempSumMaxima = "";
            TempRetMinima = "";
            TempRetMaxima = "";
            return;
        }
        int P_TDO = T_Parametro.getColumnIndex(IT_Parametros.I_PARAMETROS.ID);
        int P_TempMinima = T_Parametro.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_PAS_MINIMA);
        int P_TempMaxima = T_Parametro.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_PAS_MAXIMA);
        int P_HumMinima = T_Parametro.getColumnIndex(IT_Parametros.I_PARAMETROS.HUME_PAS_MINIMA);
        int P_HumMaxima = T_Parametro.getColumnIndex(IT_Parametros.I_PARAMETROS.HUME_PAS_MAXIMA);
        int P_TempSumMinima = T_Parametro.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_SUM_MINIMA);
        int P_TempSumMaxima = T_Parametro.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_SUM_MAXIMA);
        int P_TempRetMinima = T_Parametro.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_RET_MINIMA);
        int P_TempRetMaxima = T_Parametro.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_RET_MAXIMA);
        for (T_Parametro.moveToFirst(); !T_Parametro.isAfterLast(); T_Parametro.moveToNext()) {
            Tdo_Parametro = T_Parametro.getString(P_TDO);
            TempMinima = T_Parametro.getString(P_TempMinima);
            TempMaxima = T_Parametro.getString(P_TempMaxima);
            HumMinima = T_Parametro.getString(P_HumMinima);
            HumMaxima = T_Parametro.getString(P_HumMaxima);
            TempSumMinima = T_Parametro.getString(P_TempSumMinima);
            TempSumMaxima = T_Parametro.getString(P_TempSumMaxima);
            TempRetMinima = T_Parametro.getString(P_TempRetMinima);
            TempRetMaxima = T_Parametro.getString(P_TempRetMaxima);
        }
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
