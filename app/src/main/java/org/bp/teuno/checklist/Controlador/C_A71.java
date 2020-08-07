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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.bp.teuno.checklist.Modelo.A71_Area;
import org.bp.teuno.checklist.Modelo.A71_Pasillo;
import org.bp.teuno.checklist.Modelo.Area;
import org.bp.teuno.checklist.Modelo.Pasillo;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_A71_Pasillo;
import org.bp.teuno.checklist.SQLite.IT_Area;
import org.bp.teuno.checklist.SQLite.IT_Pasillo;
import org.bp.teuno.checklist.SQLite.IT_Turno;
import org.bp.teuno.checklist.UI.Crud_A71;
import org.bp.teuno.checklist.UI.Crud_Area;
import org.bp.teuno.checklist.UI.Crud_Inicio_Sesion;
import org.bp.teuno.checklist.UI.Crud_Pasillo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import org.bp.teuno.checklist.Modelo.A71_Chiller;
//import org.bp.teuno.checklist.UI.Crud_A71_Chiller;


public class C_A71 extends AppCompatActivity {

    private final long interval = 1 * 1000;
    String Tdo_A71_Pasillo = "", Tdo_A71_Area = "", Luminosidad = "", Luminosidad_A = "", Luminosidad_B = "", Luminosidad_C = "", Ruido = "", Ruido_A = "", Ruido_B = "", Ruido_C = "", Hora_Ingreso = "", Hora_Salida = "", Turno = "", Evento = "", Descripcion = "";
    int Ronda = 0;
    String Tdo_Pasillo_Frio = "", Tdo_Pasillo_Caliente = "", Id_Pasillo = "", Nombre_Pasillo = "", Tdo_Tipo_Pasillo = "", A1 = "", A2 = "", A3 = "", B1 = "", B2 = "", B3 = "", C1 = "", C2 = "", C3 = "";
    String Tdo_Area = "", Id_Area = "", Nombre_Area = "", Tdo_Tipo_Area = "", Tdo_Aire = "", Id_Evento, Nombre_Evento, Tdo_Tipo_Evento, Tdo_Evento;
    LinearLayout lPasillos, lPasillosFrios, lPasillosCalientes, lAreas;
    TextView txMensaje1, txMensaje2, txMensaje3;
    LayoutParams lFilasPasillosParams, lFilasAreasParams, spEspacio1Params, spEspacio2Params, etLuminosidadAParams, etPasilloParams, etAreaParams, etLuminosidadBParams, etLuminosidadCParams, etRuidoAParams, etRuidoBParams, etRuidoCParams, etLuminosidadParams, etRuidoParams, etEventoParams, etDescripcionParams, lEspacio1Params, lEspacio2Params;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabGuardar, fabA34, fabA56, fabA58;
    String isMenuA56 = "1";
    ArrayList<A71_Pasillo> ListaA71Pasillo = new ArrayList<A71_Pasillo>();
    ArrayList<A71_Area> ListaA71Area = new ArrayList<A71_Area>();
    ArrayList<Pasillo> ListaPasilloFrio = new ArrayList<Pasillo>();
    ArrayList<Pasillo> ListaPasilloCaliente = new ArrayList<Pasillo>();
    ArrayList<Area> ListaArea = new ArrayList<Area>();
    ArrayList<Area> ListaEvento = new ArrayList<Area>();
    ArrayList<String> ListaRonda = new ArrayList<String>();
    Cursor T_A71_Pasillo, T_Evento, T_Pasillo, T_Area;
    Crud_A71 Datos1;
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
        setContentView(R.layout.l_a71);

        mContext = getApplicationContext();
        mActivity = this;
        mLinearLayout = (CoordinatorLayout) findViewById(R.id.rl);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo_1 = bundle.getString("Id_Grupo_Operador").toString();

        lPasillos = (LinearLayout) findViewById(R.id.lPasillos);
        lPasillosFrios = (LinearLayout) findViewById(R.id.lPasillosFrios);
        lPasillosCalientes = (LinearLayout) findViewById(R.id.lPasillosCalientes);
        lAreas = (LinearLayout) findViewById(R.id.lAreas);
        txMensaje1 = (TextView) findViewById(R.id.txMensaje1);
        txMensaje2 = (TextView) findViewById(R.id.txMensaje2);
        txMensaje3 = (TextView) findViewById(R.id.txMensaje3);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabA34 = (FloatingActionButton) findViewById(R.id.fabA34);
        fabA56 = (FloatingActionButton) findViewById(R.id.fabA56);
        fabA58 = (FloatingActionButton) findViewById(R.id.fabA58);

        Hora_Ingreso = getTime();

        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabA34, "Formulario A.34");
        TooltipCompat.setTooltipText(fabA56, "Formulario A.56");
        TooltipCompat.setTooltipText(fabA58, "Formulario A.58");

        CARGAR_PASILLOS_FRIOS();
        CARGAR_PASILLOS_CALIENTES();
        CARGAR_AREAS();
        CARGAR_EVENTOS();
        CARGAR_LISTA_RONDAS();
        DIBUJAR_FILAS_DE_PASILLOS_FRIOS_EN_LAYOUT();
        DIBUJAR_FILAS_DE_PASILLOS_CALIENTES_EN_LAYOUT();
        DIBUJAR_FILAS_DE_AREAS_EN_LAYOUT();

        if (ListaPasilloFrio.size() == 0) {
            txMensaje1.setText("No existen pasillos fríos activos");
        } else if (ListaPasilloFrio.size() == 1) {
            txMensaje1.setText("Se encontró " + String.valueOf(ListaPasilloFrio.size()) + " pasillo frío activo");
        } else {
            txMensaje1.setText("Se encontraron " + String.valueOf(ListaPasilloFrio.size()) + " pasillos fríos activos");
        }

        if (ListaPasilloCaliente.size() == 0) {
            txMensaje2.setText("No existen pasillos calientes activos");
        } else if (ListaPasilloCaliente.size() == 1) {
            txMensaje2.setText("Se encontró " + String.valueOf(ListaPasilloCaliente.size()) + " pasillo caliente activo");
        } else {
            txMensaje2.setText("Se encontraron " + String.valueOf(ListaPasilloCaliente.size()) + " pasillos calientes activos");
        }

        if (ListaArea.size() == 0) {
            txMensaje3.setText("No existen áreas activas");
        } else if (ListaArea.size() == 1) {
            txMensaje3.setText("Se encontró " + String.valueOf(ListaArea.size()) + " área activa");
        } else {
            txMensaje3.setText("Se encontraron " + String.valueOf(ListaArea.size()) + " áreas activas");
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
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A71.this);
                    myBulid.setTitle("Formulario A.34");
                    myBulid.setIcon(R.drawable.motor);
                    myBulid.setMessage("¿Desea ingresar al formulario A.34?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(C_A71.this, C_A34.class);
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
                    final Intent intent = new Intent(C_A71.this, C_A56.class);
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
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A71.this);
                    myBulid.setTitle("Formulario A.58");
                    myBulid.setIcon(R.drawable.temperatura1);
                    myBulid.setMessage("¿Desea ingresar al formulario A.58?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(C_A71.this, C_A58.class);
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
                    if (lPasillosFrios.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen pasillos fríos activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (lPasillosCalientes.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen pasillos calientes activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (lAreas.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen áreas activas", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_PASILLOS_FRIOS() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_PASILLOS_CALIENTES() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_CORRECTOS_PASILLOS_FRIOS() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_CORRECTOS_PASILLOS_CALIENTES() == false) {
                        return;
                    }
                /*if (VALIDAR_DATOS_CORRECTOS_AREAS() == false) {
                    return;
                }*/
                    Ronda = CONSULTAR_RONDA() + 1;
                    Hora_Salida = getTime();
                    LinearLayout ly1, ly2, ly3;
                    ListaA71Pasillo = new ArrayList<A71_Pasillo>();
                    for (int i = 0; i < lPasillosFrios.getChildCount(); i++) {
                        ly1 = (LinearLayout) lPasillosFrios.getChildAt(i);
                        Tdo_Pasillo_Frio = ly1.getTag().toString();
                        final TextInputLayout tilLuminosidadA = (TextInputLayout) ly1.getChildAt(2);
                        final TextInputLayout tilLuminosidadB = (TextInputLayout) ly1.getChildAt(3);
                        final TextInputLayout tilRuidoA = (TextInputLayout) ly1.getChildAt(5);
                        final TextInputLayout tilRuidoB = (TextInputLayout) ly1.getChildAt(6);
                        Luminosidad_A = tilLuminosidadA.getEditText().getText().toString();
                        Luminosidad_B = tilLuminosidadB.getEditText().getText().toString();
                        Ruido_A = tilRuidoA.getEditText().getText().toString();
                        Ruido_B = tilRuidoB.getEditText().getText().toString();
                        A71_Pasillo a71_pasillo = new A71_Pasillo("", Tdo_Pasillo_Frio, Luminosidad_A, Luminosidad_B, Ruido_A, Ruido_B, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA71Pasillo.add(a71_pasillo);
                    }
                    for (int i = 0; i < lPasillosCalientes.getChildCount(); i++) {
                        ly2 = (LinearLayout) lPasillosCalientes.getChildAt(i);
                        Tdo_Pasillo_Caliente = ly2.getTag().toString();
                        final TextInputLayout tilLuminosidadA = (TextInputLayout) ly2.getChildAt(2);
                        final TextInputLayout tilLuminosidadB = (TextInputLayout) ly2.getChildAt(3);
                        final TextInputLayout tilRuidoA = (TextInputLayout) ly2.getChildAt(5);
                        final TextInputLayout tilRuidoB = (TextInputLayout) ly2.getChildAt(6);
                        Luminosidad_A = tilLuminosidadA.getEditText().getText().toString();
                        Luminosidad_B = tilLuminosidadB.getEditText().getText().toString();
                        Ruido_A = tilRuidoA.getEditText().getText().toString();
                        Ruido_B = tilRuidoB.getEditText().getText().toString();
                        A71_Pasillo a71_pasillo = new A71_Pasillo("", Tdo_Pasillo_Caliente, Luminosidad_A, Luminosidad_B, Ruido_A, Ruido_B, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA71Pasillo.add(a71_pasillo);
                    }
                    ListaA71Area = new ArrayList<A71_Area>();
                    for (int i = 0; i < lAreas.getChildCount(); i++) {
                        ly3 = (LinearLayout) lAreas.getChildAt(i);
                        Tdo_Area = ly3.getTag().toString();
                        final MaterialBetterSpinner msEvento = (MaterialBetterSpinner) ly3.getChildAt(1);
                        final TextInputLayout tilDescripcion = (TextInputLayout) ly3.getChildAt(2);
                        for (int j = 0; j < ListaEvento.size(); j++) {
                            if (!msEvento.getText().toString().equals("")) {
                                if (msEvento.getText().toString().equals(ListaEvento.get(j).getNOMBRE_AREA().toString())) {
                                    Tdo_Evento = ListaEvento.get(j).getID();
                                    j = ListaEvento.size();
                                }
                            } else {
                                for (int k = 0; k < ListaEvento.size(); k++) {
                                    if (ListaEvento.get(k).getNOMBRE_AREA().equals("SIN NOVEDAD")) {
                                        Tdo_Evento = ListaEvento.get(k).ID;
                                        k = ListaEvento.size();
                                    }
                                }
                            }
                        }
                        Descripcion = tilDescripcion.getEditText().getText().toString().toUpperCase();
                        A71_Area a71_area = new A71_Area("", Tdo_Area, Tdo_Evento, Descripcion, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA71Area.add(a71_area);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_A71.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            long insertedResult1 = 0, insertedResult2 = 0, insertedResult3 = 0;
                                            Datos1 = Crud_A71.OBTENER_INSTANCIA(getApplicationContext());
                                            try {
                                                Datos1.GETBD().beginTransaction();
                                                for (int i = 0; i < ListaA71Pasillo.size(); i++) {
                                                    insertedResult1 = Datos1.INSERTAR_A71_PASILLO(ListaA71Pasillo.get(i));
                                                    if (insertedResult1 == -1) {
                                                        Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                    }
                                                }
                                                if (insertedResult1 != -1) {
                                                    for (int i = 0; i < ListaA71Area.size(); i++) {
                                                        insertedResult2 = Datos1.INSERTAR_A71_AREA(ListaA71Area.get(i));
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
                Intent intent = new Intent(C_A71.this, C_Menu_Principal.class);
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

    public boolean VALIDAR_DATOS_VACIOS_PASILLOS_FRIOS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosFrios.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosFrios.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly.getChildAt(6);
            if (tilLuminosidadA.getEditText().getText().toString().equals("")) {
                tilLuminosidadA.requestFocus();
                tilLuminosidadA.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (tilLuminosidadB.getEditText().getText().toString().equals("")) {
                tilLuminosidadB.requestFocus();
                tilLuminosidadB.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (tilRuidoA.getEditText().getText().toString().equals("")) {
                tilRuidoA.requestFocus();
                tilRuidoA.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (tilRuidoB.getEditText().getText().toString().equals("")) {
                tilRuidoB.requestFocus();
                tilRuidoB.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_PASILLOS_FRIOS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosFrios.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosFrios.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly.getChildAt(6);
            if (tilLuminosidadA.getEditText().getText().toString().equals("0")) {
                tilLuminosidadA.requestFocus();
                tilLuminosidadA.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (tilLuminosidadB.getEditText().getText().toString().equals("0")) {
                tilLuminosidadB.requestFocus();
                tilLuminosidadB.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (tilRuidoA.getEditText().getText().toString().equals("0")) {
                tilRuidoA.requestFocus();
                tilRuidoA.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (tilRuidoB.getEditText().getText().toString().equals("0")) {
                tilRuidoB.requestFocus();
                tilRuidoB.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_VACIOS_PASILLOS_CALIENTES() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosCalientes.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosCalientes.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly.getChildAt(6);
            if (tilLuminosidadA.getEditText().getText().toString().equals("")) {
                tilLuminosidadA.requestFocus();
                tilLuminosidadA.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (tilLuminosidadB.getEditText().getText().toString().equals("")) {
                tilLuminosidadB.requestFocus();
                tilLuminosidadB.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (tilRuidoA.getEditText().getText().toString().equals("")) {
                tilRuidoA.requestFocus();
                tilRuidoA.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (tilRuidoB.getEditText().getText().toString().equals("")) {
                tilRuidoB.requestFocus();
                tilRuidoB.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_PASILLOS_CALIENTES() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosCalientes.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosCalientes.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly.getChildAt(6);
            if (tilLuminosidadA.getEditText().getText().toString().equals("0")) {
                tilLuminosidadA.requestFocus();
                tilLuminosidadA.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (tilLuminosidadB.getEditText().getText().toString().equals("0")) {
                tilLuminosidadB.requestFocus();
                tilLuminosidadB.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (tilRuidoA.getEditText().getText().toString().equals("0")) {
                tilRuidoA.requestFocus();
                tilRuidoA.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (tilRuidoB.getEditText().getText().toString().equals("0")) {
                tilRuidoB.requestFocus();
                tilRuidoB.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_AREAS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly = (LinearLayout) lAreas.getChildAt(i);
            final MaterialBetterSpinner msEvento = (MaterialBetterSpinner) ly.getChildAt(1);
            final TextInputLayout tilDescripcion = (TextInputLayout) ly.getChildAt(2);
            String evento = msEvento.getText().toString();
            if (!(evento.matches("SIN NOVEDAD") || evento.matches("")) && tilDescripcion.getEditText().getText().toString().matches("")) {
                tilDescripcion.requestFocus();
                tilDescripcion.getEditText().setError("Registre una descripción del evento observado");
                condicion = 0;
                i = lAreas.getChildCount();
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
        Crud_A71 DATOS;
        DATOS = Crud_A71.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_A71_Pasillo = DATOS.CONSULTA_GENERAL_RONDA(Tdo_Turno, getDate());
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_A71_Pasillo.moveToFirst() == false) {
            //el cursor está vacío
            ronda = 0;
        } else {
            int P_Ronda = T_A71_Pasillo.getColumnIndex(IT_A71_Pasillo.I_A71_Pasillo.RONDA);
            for (T_A71_Pasillo.moveToFirst(); !T_A71_Pasillo.isAfterLast(); T_A71_Pasillo.moveToNext()) {
                for (int i = 0; i < ListaRonda.size(); i++) {
                    if (T_A71_Pasillo.getString(P_Ronda).equals(ListaRonda.get(i).toString())) {
                        ronda = Integer.valueOf(ListaRonda.get(i).toString());
                    }
                }
            }
        }
        return ronda;
    }

    public void LIMPIAR_DATOS() {
        Tdo_Pasillo_Frio = "";
        Tdo_Pasillo_Caliente = "";
        Tdo_A71_Pasillo = "";
        Tdo_A71_Area = "";
        Tdo_Area = "";
        Luminosidad_A = "";
        Luminosidad_B = "";
        Ruido_A = "";
        Ruido_B = "";
        LinearLayout ly1;
        ListaA71Pasillo = new ArrayList<A71_Pasillo>();
        ListaA71Area = new ArrayList<A71_Area>();
        for (int i = 0; i < lPasillosFrios.getChildCount(); i++) {
            ly1 = (LinearLayout) lPasillosFrios.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly1.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly1.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly1.getChildAt(6);
            tilLuminosidadA.getEditText().setText("");
            tilLuminosidadB.getEditText().setText("");
            tilRuidoA.getEditText().setText("");
            tilRuidoB.getEditText().setText("");
            tilLuminosidadA.getEditText().setError(null);
            tilLuminosidadB.getEditText().setError(null);
            tilRuidoA.getEditText().setError(null);
            tilRuidoB.getEditText().setError(null);
        }
        for (int i = 0; i < lPasillosCalientes.getChildCount(); i++) {
            ly1 = (LinearLayout) lPasillosCalientes.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly1.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly1.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly1.getChildAt(6);
            tilLuminosidadA.getEditText().setText("");
            tilLuminosidadB.getEditText().setText("");
            tilRuidoA.getEditText().setText("");
            tilRuidoB.getEditText().setText("");
            tilLuminosidadA.getEditText().setError(null);
            tilLuminosidadB.getEditText().setError(null);
            tilRuidoA.getEditText().setError(null);
            tilRuidoB.getEditText().setError(null);
        }
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly1 = (LinearLayout) lAreas.getChildAt(i);
            final MaterialBetterSpinner msEvento = (MaterialBetterSpinner) ly1.getChildAt(1);
            final TextInputLayout tilDescripcion = (TextInputLayout) ly1.getChildAt(2);
            //msEvento.setHint("Evento");
            msEvento.setText("");
            msEvento.setFocusableInTouchMode(false);
            msEvento.setError(null);
            tilDescripcion.getEditText().setText("");
            tilDescripcion.getEditText().setError(null);
        }
    }

    public void DIBUJAR_FILAS_DE_PASILLOS_FRIOS_EN_LAYOUT() {
        if (ListaPasilloFrio.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaPasilloFrio.size(); i++) {

            final LinearLayout lFilasPasillos = new LinearLayout(this);

            final TextInputLayout tilPasillo = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadA = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadB = new TextInputLayout(this);
            final TextInputLayout tilRuidoA = new TextInputLayout(this);
            final TextInputLayout tilRuidoB = new TextInputLayout(this);

            final Space spEspacio1 = new Space(this);
            final Space spEspacio2 = new Space(this);

            final EditText etPasillo = new EditText(this);
            final EditText etLuminosidadA = new EditText(this);
            final EditText etLuminosidadB = new EditText(this);
            final EditText etRuidoA = new EditText(this);
            final EditText etRuidoB = new EditText(this);

            lFilasPasillosParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            spEspacio1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etPasilloParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadBParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoBParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasPasillos.setLayoutParams(lFilasPasillosParams);
            lFilasPasillos.setOrientation(LinearLayout.HORIZONTAL);
            lFilasPasillos.setTag(ListaPasilloFrio.get(i).getID());
            lFilasPasillos.setId(i);

            etPasilloParams.gravity = Gravity.CENTER;
            etPasilloParams.width = 120;
            etPasillo.setLayoutParams(etPasilloParams);
            //etPasillo.setHint("Frío");
            etPasillo.setText(ListaPasilloFrio.get(i).getNOMBRE_PASILLO());
            etPasillo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etPasillo.setTextColor(getThemeAccentColor(this));
            etPasillo.setEnabled(false);

            spEspacio1Params.gravity = Gravity.CENTER;
            spEspacio1Params.width = 120;
            spEspacio1.setLayoutParams(spEspacio1Params);

            etLuminosidadAParams.gravity = Gravity.CENTER;
            etLuminosidadAParams.width = 120;
            etLuminosidadA.setLayoutParams(etLuminosidadAParams);
            etLuminosidadA.setId(i);
            //etLuminosidadA.setHint("A");
            etLuminosidadA.setText("");
            etLuminosidadA.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadA.setTextColor(getThemeAccentColor(this));
            etLuminosidadA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etLuminosidadBParams.gravity = Gravity.CENTER;
            etLuminosidadBParams.width = 120;
            etLuminosidadB.setLayoutParams(etLuminosidadBParams);
            etLuminosidadB.setId(i);
            //etLuminosidadB.setHint("B");
            etLuminosidadB.setText("");
            etLuminosidadB.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadB.setTextColor(getThemeAccentColor(this));
            etLuminosidadB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            spEspacio1Params.gravity = Gravity.CENTER;
            spEspacio1Params.width = 120;
            spEspacio2.setLayoutParams(spEspacio1Params);

            etRuidoAParams.gravity = Gravity.CENTER;
            etRuidoAParams.width = 120;
            etRuidoA.setLayoutParams(etRuidoAParams);
            etRuidoA.setId(i);
            //etRuidoA.setHint("A");
            etRuidoA.setText("");
            etRuidoA.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoA.setTextColor(getThemeAccentColor(this));
            etRuidoA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            etRuidoBParams.gravity = Gravity.CENTER;
            etRuidoBParams.width = 120;
            etRuidoB.setLayoutParams(etRuidoBParams);
            etRuidoB.setId(i);
            //etRuidoB.setHint("B");
            etRuidoB.setText("");
            etRuidoB.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoB.setTextColor(getThemeAccentColor(this));
            etRuidoB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            tilPasillo.addView(etPasillo);
            tilLuminosidadA.addView(etLuminosidadA);
            tilLuminosidadB.addView(etLuminosidadB);
            tilRuidoA.addView(etRuidoA);
            tilRuidoB.addView(etRuidoB);

            lFilasPasillos.addView(tilPasillo);
            lFilasPasillos.addView(spEspacio1);
            lFilasPasillos.addView(tilLuminosidadA);
            lFilasPasillos.addView(tilLuminosidadB);
            lFilasPasillos.addView(spEspacio2);
            lFilasPasillos.addView(tilRuidoA);
            lFilasPasillos.addView(tilRuidoB);

            lPasillosFrios.addView(lFilasPasillos);

        }
    }

    public void DIBUJAR_FILAS_DE_PASILLOS_CALIENTES_EN_LAYOUT() {
        if (ListaPasilloCaliente.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaPasilloCaliente.size(); i++) {

            final LinearLayout lFilasPasillos = new LinearLayout(this);

            final TextInputLayout tilPasillo = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadA = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadB = new TextInputLayout(this);
            final TextInputLayout tilRuidoA = new TextInputLayout(this);
            final TextInputLayout tilRuidoB = new TextInputLayout(this);

            final Space spEspacio1 = new Space(this);
            final Space spEspacio2 = new Space(this);

            final EditText etPasillo = new EditText(this);
            final EditText etLuminosidadA = new EditText(this);
            final EditText etLuminosidadB = new EditText(this);
            final EditText etRuidoA = new EditText(this);
            final EditText etRuidoB = new EditText(this);

            lFilasPasillosParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            spEspacio1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etPasilloParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadBParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoBParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasPasillos.setLayoutParams(lFilasPasillosParams);
            lFilasPasillos.setOrientation(LinearLayout.HORIZONTAL);
            lFilasPasillos.setTag(ListaPasilloCaliente.get(i).getID());
            lFilasPasillos.setId(i);

            etPasilloParams.gravity = Gravity.CENTER;
            etPasilloParams.width = 120;
            etPasillo.setLayoutParams(etPasilloParams);
            //etPasillo.setHint("Caliente");
            etPasillo.setText(ListaPasilloCaliente.get(i).getNOMBRE_PASILLO());
            etPasillo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etPasillo.setTextColor(getThemeAccentColor(this));
            etPasillo.setEnabled(false);

            spEspacio1Params.gravity = Gravity.CENTER;
            spEspacio1Params.width = 120;
            spEspacio1.setLayoutParams(spEspacio1Params);

            etLuminosidadAParams.gravity = Gravity.CENTER;
            etLuminosidadAParams.width = 120;
            etLuminosidadA.setLayoutParams(etLuminosidadAParams);
            etLuminosidadA.setId(i);
            //etLuminosidadA.setHint("A");
            etLuminosidadA.setText("");
            etLuminosidadA.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadA.setTextColor(getThemeAccentColor(this));
            etLuminosidadA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etLuminosidadBParams.gravity = Gravity.CENTER;
            etLuminosidadBParams.width = 120;
            etLuminosidadB.setLayoutParams(etLuminosidadBParams);
            etLuminosidadB.setId(i);
            //etLuminosidadB.setHint("B");
            etLuminosidadB.setText("");
            etLuminosidadB.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadB.setTextColor(getThemeAccentColor(this));
            etLuminosidadB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            spEspacio1Params.gravity = Gravity.CENTER;
            spEspacio1Params.width = 120;
            spEspacio2.setLayoutParams(spEspacio1Params);

            etRuidoAParams.gravity = Gravity.CENTER;
            etRuidoAParams.width = 120;
            etRuidoA.setLayoutParams(etRuidoAParams);
            etRuidoA.setId(i);
            //etRuidoA.setHint("A");
            etRuidoA.setText("");
            etRuidoA.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoA.setTextColor(getThemeAccentColor(this));
            etRuidoA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            etRuidoBParams.gravity = Gravity.CENTER;
            etRuidoBParams.width = 120;
            etRuidoB.setLayoutParams(etRuidoBParams);
            etRuidoB.setId(i);
            //etRuidoB.setHint("B");
            etRuidoB.setText("");
            etRuidoB.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoB.setTextColor(getThemeAccentColor(this));
            etRuidoB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            tilPasillo.addView(etPasillo);
            tilLuminosidadA.addView(etLuminosidadA);
            tilLuminosidadB.addView(etLuminosidadB);
            tilRuidoA.addView(etRuidoA);
            tilRuidoB.addView(etRuidoB);

            lFilasPasillos.addView(tilPasillo);
            lFilasPasillos.addView(spEspacio1);
            lFilasPasillos.addView(tilLuminosidadA);
            lFilasPasillos.addView(tilLuminosidadB);
            lFilasPasillos.addView(spEspacio2);
            lFilasPasillos.addView(tilRuidoA);
            lFilasPasillos.addView(tilRuidoB);

            lPasillosCalientes.addView(lFilasPasillos);

        }
    }

    public void DIBUJAR_FILAS_DE_AREAS_EN_LAYOUT() {
        if (ListaArea.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaArea.size(); i++) {

            final LinearLayout lFilasAreas = new LinearLayout(this);

            final TextInputLayout tilArea = new TextInputLayout(this);
            final TextInputLayout tilDescripcion = new TextInputLayout(this);

            final EditText etArea = new EditText(this);
            final MaterialBetterSpinner msEvento = new MaterialBetterSpinner(this);
            final EditText etDescripcion = new EditText(this);

            lFilasAreasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etEventoParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etDescripcionParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasAreas.setLayoutParams(lFilasAreasParams);
            lFilasAreas.setOrientation(LinearLayout.HORIZONTAL);
            lFilasAreas.setTag(ListaArea.get(i).getID());
            lFilasAreas.setId(i);

            etAreaParams.gravity = Gravity.CENTER;
            etAreaParams.width = 200;
            etArea.setLayoutParams(etAreaParams);
            //etArea.setHint("Área");
            etArea.setText(ListaArea.get(i).getNOMBRE_AREA());
            etArea.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etArea.setTextColor(getThemeAccentColor(this));
            etArea.setEnabled(false);

            etEventoParams.gravity = Gravity.CENTER;
            etEventoParams.width = 300;
            msEvento.setLayoutParams(etEventoParams);
            //msEvento.setId(i);
            msEvento.setHint("Evento");
            msEvento.setFocusableInTouchMode(false);
            msEvento.setText("");
            msEvento.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            msEvento.setTextColor(getThemeAccentColor(this));

            ArrayAdapter<Area> Adapter = new ArrayAdapter<Area>(C_A71.this, android.R.layout.simple_spinner_dropdown_item, ListaEvento);
            Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            msEvento.setAdapter(Adapter);

            etDescripcionParams.gravity = Gravity.CENTER;
            etDescripcionParams.width = 300;
            etDescripcion.setLayoutParams(etDescripcionParams);
            etDescripcion.setId(i);
            //etDescripcion.setHint("Descripción");
            etDescripcion.setText("");
            etDescripcion.setInputType(InputType.TYPE_CLASS_TEXT);
            etDescripcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etDescripcion.setTextColor(getThemeAccentColor(this));
            etDescripcion.setEnabled(false);

            msEvento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    if (ListaEvento.size() > 0) {
                        if (!ListaEvento.get(position).getNOMBRE_AREA().equals("SIN NOVEDAD")) {
                            etDescripcion.setText("");
                            etDescripcion.setError(null);
                            etDescripcion.setEnabled(true);
                        } else {
                            etDescripcion.setText("");
                            etDescripcion.setError(null);
                            etDescripcion.setEnabled(false);
                        }
                    }
                }
            });

            tilArea.addView(etArea);
            tilDescripcion.addView(etDescripcion);

            lFilasAreas.addView(tilArea);
            lFilasAreas.addView(msEvento);
            lFilasAreas.addView(tilDescripcion);

            lAreas.addView(lFilasAreas);

        }
    }

    public void CARGAR_PASILLOS_FRIOS() {
        Crud_Pasillo DATOS;
        DATOS = Crud_Pasillo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Pasillo = DATOS.CONSULTA_GENERAL_PASILLO_FRIO();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Pasillo.moveToFirst() == false) {
            //el cursor está vacío
            ListaPasilloFrio = new ArrayList<Pasillo>();
            Tdo_Pasillo_Frio = "";
            Id_Pasillo = "";
            Nombre_Pasillo = "";
            Tdo_Tipo_Pasillo = "";
            return;
        }
        int P_TDO = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.ID);
        int P_Id_Pasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.ID_PASILLO);
        int P_Nombre_Pasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.NOMBRE_PASILLO);
        int P_Id_TipoPasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.TIPO_PASILLO);
        ListaPasilloFrio = new ArrayList<Pasillo>();
        for (T_Pasillo.moveToFirst(); !T_Pasillo.isAfterLast(); T_Pasillo.moveToNext()) {
            Tdo_Pasillo_Frio = T_Pasillo.getString(P_TDO);
            Id_Pasillo = T_Pasillo.getString(P_Id_Pasillo);
            Nombre_Pasillo = T_Pasillo.getString(P_Nombre_Pasillo);
            Tdo_Tipo_Pasillo = T_Pasillo.getString(P_Id_TipoPasillo);
            Pasillo Pasillo = new Pasillo(Tdo_Pasillo_Frio, Id_Pasillo, Nombre_Pasillo, Tdo_Tipo_Pasillo);
            ListaPasilloFrio.add(Pasillo);
        }
        Tdo_Pasillo_Frio = "";
        Id_Pasillo = "";
        Nombre_Pasillo = "";
        Tdo_Tipo_Pasillo = "";
    }

    public void CARGAR_PASILLOS_CALIENTES() {
        Crud_Pasillo DATOS;
        DATOS = Crud_Pasillo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Pasillo = DATOS.CONSULTA_GENERAL_PASILLO_CALIENTE();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Pasillo.moveToFirst() == false) {
            //el cursor está vacío
            ListaPasilloCaliente = new ArrayList<Pasillo>();
            Tdo_Pasillo_Caliente = "";
            Id_Pasillo = "";
            Nombre_Pasillo = "";
            Tdo_Tipo_Pasillo = "";
            return;
        }
        int P_TDO = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.ID);
        int P_Id_Pasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.ID_PASILLO);
        int P_Nombre_Pasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.NOMBRE_PASILLO);
        int P_Id_TipoPasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.TIPO_PASILLO);
        ListaPasilloCaliente = new ArrayList<Pasillo>();
        for (T_Pasillo.moveToFirst(); !T_Pasillo.isAfterLast(); T_Pasillo.moveToNext()) {
            Tdo_Pasillo_Caliente = T_Pasillo.getString(P_TDO);
            Id_Pasillo = T_Pasillo.getString(P_Id_Pasillo);
            Nombre_Pasillo = T_Pasillo.getString(P_Nombre_Pasillo);
            Tdo_Tipo_Pasillo = T_Pasillo.getString(P_Id_TipoPasillo);
            Pasillo Pasillo = new Pasillo(Tdo_Pasillo_Caliente, Id_Pasillo, Nombre_Pasillo, Tdo_Tipo_Pasillo);
            ListaPasilloCaliente.add(Pasillo);
        }
        Tdo_Pasillo_Caliente = "";
        Id_Pasillo = "";
        Nombre_Pasillo = "";
        Tdo_Tipo_Pasillo = "";
    }

    public void CARGAR_AREAS() {
        Crud_Area DATOS;
        DATOS = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Area = DATOS.CONSULTA_GENERAL_AREA_A71();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Area.moveToFirst() == false) {
            //el cursor está vacío
            ListaArea = new ArrayList<Area>();
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
        ListaArea = new ArrayList<Area>();
        for (T_Area.moveToFirst(); !T_Area.isAfterLast(); T_Area.moveToNext()) {
            Tdo_Area = T_Area.getString(P_TDO);
            Id_Area = T_Area.getString(P_Id_Area);
            Tdo_Tipo_Area = T_Area.getString(P_Tipo_Area);
            Nombre_Area = T_Area.getString(P_Nombre_Area);
            Area Area = new Area(Tdo_Area, Id_Area, Nombre_Area, Tdo_Tipo_Area);
            ListaArea.add(Area);
        }
        Tdo_Area = "";
        Id_Area = "";
        Tdo_Tipo_Area = "";
        Nombre_Area = "";
    }

    public void CARGAR_EVENTOS() {
        Crud_Area DATOS;
        DATOS = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Evento = DATOS.CONSULTA_GENERAL_EVENTO_A71();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Evento.moveToFirst() == false) {
            //el cursor está vacío
            ListaEvento = new ArrayList<Area>();
            Tdo_Evento = "";
            Id_Evento = "";
            Tdo_Tipo_Evento = "";
            Nombre_Evento = "";
            return;
        }
        int P_TDO = T_Evento.getColumnIndex(IT_Area.I_AREA.ID);
        int P_Id_Evento = T_Evento.getColumnIndex(IT_Area.I_AREA.ID_AREA);
        int P_Tipo_Evento = T_Evento.getColumnIndex(IT_Area.I_AREA.TIPO_AREA);
        int P_Nombre_Evento = T_Evento.getColumnIndex(IT_Area.I_AREA.NOMBRE_AREA);
        ListaEvento = new ArrayList<Area>();
        for (T_Evento.moveToFirst(); !T_Evento.isAfterLast(); T_Evento.moveToNext()) {
            Tdo_Evento = T_Evento.getString(P_TDO);
            Id_Evento = T_Evento.getString(P_Id_Evento);
            Tdo_Tipo_Evento = T_Evento.getString(P_Tipo_Evento);
            Nombre_Evento = T_Evento.getString(P_Nombre_Evento);
            Area Evento = new Area(Tdo_Evento, Id_Evento, Nombre_Evento, Tdo_Tipo_Evento);
            ListaEvento.add(Evento);
        }
        Tdo_Evento = "";
        Id_Evento = "";
        Tdo_Tipo_Evento = "";
        Nombre_Evento = "";
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
