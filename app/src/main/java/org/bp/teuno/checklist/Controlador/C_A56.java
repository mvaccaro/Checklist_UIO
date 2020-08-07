package org.bp.teuno.checklist.Controlador;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.bp.teuno.checklist.Modelo.A56;
import org.bp.teuno.checklist.Modelo.Alerta;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_A56;
import org.bp.teuno.checklist.SQLite.IT_Alerta;
import org.bp.teuno.checklist.SQLite.IT_Equipo;
import org.bp.teuno.checklist.SQLite.IT_Fila;
import org.bp.teuno.checklist.SQLite.IT_Grupo;
import org.bp.teuno.checklist.SQLite.IT_Rack;
import org.bp.teuno.checklist.SQLite.IT_Rack_Ur;
import org.bp.teuno.checklist.SQLite.IT_Turno;
import org.bp.teuno.checklist.UI.Crud_A56;
import org.bp.teuno.checklist.UI.Crud_Alerta;
import org.bp.teuno.checklist.UI.Crud_Equipo;
import org.bp.teuno.checklist.UI.Crud_Fila;
import org.bp.teuno.checklist.UI.Crud_Grupo;
import org.bp.teuno.checklist.UI.Crud_Inicio_Sesion;
import org.bp.teuno.checklist.UI.Crud_Rack;
import org.bp.teuno.checklist.UI.Crud_Rack_Ur;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class C_A56 extends AppCompatActivity {

    private final long interval = 1 * 1000;
    String Tdo_A56 = "", A = "", C = "", PDUA = "", PDUB = "", Hora_Ingreso = "", Hora_Salida = "", Turno = "";
    int Ronda = 0;
    String Tdo_Alerta = "", ID_Fila = "", ID_Rack = "", ID_UR = "", ID_Equipo = "", Tipo_Alerta = "", Color_Alerta = "", Comentario = "", Ticket = "", Fecha_Ingreso = "", Usuario_Ingresa = "";
    String Tdo_Equipo = "", Nombre_Equipo = "", Marca = "", Modelo = "", Serie = "";
    String Tdo_Rack = "", Nombre_Rack = "";  //PARA EL USO DE Racks
    String Tdo_Rack_Ur = "", Nombre_Rack_Ur = "";  //PARA EL USO DE Racks
    String Tdo_Fila = "", Id_Fila = "", Nombre_Fila = ""; // PARA CARGAR LA LISTA DE Filas
    LinearLayout lAlertas;
    TextView txMensaje, txtMensaje;
    LayoutParams lFilasAlertasParams, btnMensajeParams, btnDesactivarParams, etFilaParams, etRackParams, etUrParams, etDescripcionParams, etAParams, etCParams, etPDUAParams, etPDUBParams;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabGuardar, fabAlertas, fabA58, fabA71, fabA34;
    ArrayList<A56> ListaA56 = new ArrayList<A56>();
    ArrayList<Alerta> ListaAlerta = new ArrayList<Alerta>();
    ArrayList<String> ListaRonda = new ArrayList<String>();
    String Tdo_Grupo, Id_Grupo, Nombre_Grupo, Id_Grupo_Temporal = "", Pin_Seguridad = "";
    String ischeckFilas, ischeckClientes, ischeckAlertas, ischeckTipoAlertas, ischeckColorAlertas, ischeckCantidadUR, ischeckPasillos, ischeckChillers, ischeckAreas, ischeckGrupos, ischeckRacks, ischeckEquipos, ischeckVersion, ischeckParametros;
    Cursor T_Alerta, T_Equipo, T_Fila, T_Rack, T_A56, T_Grupo, T_Rack_Ur;
    Crud_A56 Datos;
    int bandera = 0;
    String Id_Operador = "", Nombre_Operador, Tdo_Turno, Turno_Operador, isMenuA56, Access;
    CountDownTimer countDownTimer;
    private Bitmap bitmap;
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    private Context mContext;
    private Activity mActivity;
    private ImageView Image;
    private byte[] photo;
    private String Tdo_Grupo_1;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME
    private String H_inicio = "", timeA56 = "";
    Crud_Inicio_Sesion DATOS;
    Cursor T_Turno;
    String Tdo_Turno_1;

    private ImageView ImageView2;

    public static int getThemeAccentColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public static boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
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
            numero = Integer.parseInt(cadena);
            conversion = String.valueOf(numero);
        } catch (NumberFormatException excepcion) {
            conversion = "";
        }
        return conversion;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_a56);

        //countDownTimer = new MyCountDownTimer(startTime, interval);
        //onUserInteraction();

        mContext = getApplicationContext();
        mActivity = this;
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);

        Image = (ImageView) findViewById(R.id.imgView1);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        isMenuA56 = bundle.getString("IsMenuA56").toString();
        Tdo_Grupo_1 = bundle.getString("Id_Grupo_Operador").toString();
        Access = bundle.getString("Access").toString();
        timeA56 = bundle.getString("Tiempo_A56").toString();

        ImageView2 = (ImageView) findViewById(R.id.imageView2);
        txtMensaje = (TextView) findViewById(R.id.txtMensaje);

        if (Access.equals("BP")) {
            ImageView2.setImageResource(R.drawable.bp2);
            txtMensaje.setText("Formulario A.56");
        } else {
            ImageView2.setImageResource(R.drawable.dn2);
            txtMensaje.setText("Formulario FD.56");
        }


        lAlertas = (LinearLayout) findViewById(R.id.lAlertas);
        txMensaje = (TextView) findViewById(R.id.txMensaje);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabAlertas = (FloatingActionButton) findViewById(R.id.fabAlertas);
        fabA34 = (FloatingActionButton) findViewById(R.id.fabA34);
        fabA58 = (FloatingActionButton) findViewById(R.id.fabA58);
        fabA71 = (FloatingActionButton) findViewById(R.id.fabA71);

        H_inicio = getDateTime();
        Hora_Ingreso = isMenuA56.equals("0") ? getDateTimeAlerta() : getTime();

        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabAlertas, "Mantenimiento de Alertas");
        TooltipCompat.setTooltipText(fabA34, "Formulario A.34");
        TooltipCompat.setTooltipText(fabA58, "Formulario A.58");
        TooltipCompat.setTooltipText(fabA71, "Formulario A.71");

        CARGAR_ALERTAS();
        CARGAR_LISTA_RONDAS();
        OBTENER_PERMISOS();

        DIBUJAR_FILAS_EN_LAYOUT();

        if (ListaAlerta.size() == 0) {
            txMensaje.setText("No existen alertas activas");
        } else if (ListaAlerta.size() == 1) {
            txMensaje.setText("Se encontró " + String.valueOf(ListaAlerta.size()) + " alerta activa");
        } else {
            txMensaje.setText("Se encontraron " + String.valueOf(ListaAlerta.size()) + " alertas activas");
        }

        fabAlertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ischeckAlertas.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                    return;
                } else {
                    OBTENERTURNO();
                    if (!Tdo_Turno.equals(Tdo_Turno_1)) {
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        isMenuA56 = "0";
                        AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A56.this);
                        myBulid.setTitle("Mantenimiento de alertas");
                        myBulid.setIcon(R.drawable.salir);
                        myBulid.setMessage("¿Desea ingresar al mantenimiento de alertas?");
                        myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(C_A56.this, C_Alerta.class);
                                intent.putExtra("Id_Operador", Id_Operador);
                                intent.putExtra("Nombre_Operador", Nombre_Operador);
                                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                intent.putExtra("Turno_Operador", Turno_Operador);
                                intent.putExtra("IsMenuA56", isMenuA56);
                                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo_1);
                                intent.putExtra("Access", Access);
                                try {
                                    intent.putExtra("Tiempo_A56", getTimeA56());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            /*try {
                                Toast.makeText(getApplicationContext(), "Resta de horas: "+getTimeA56(), Toast.LENGTH_LONG).show();
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }*/
                                startActivity(intent);
                                finish();
                                LIMPIAR_DATOS();
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
            }
        });

        fabA34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENERTURNO();
                if (!Tdo_Turno.equals(Tdo_Turno_1)) {
                    Toast.makeText(
                            getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A56.this);
                    myBulid.setTitle("Formulario A.34");
                    myBulid.setIcon(R.drawable.motor);
                    myBulid.setMessage("¿Desea ingresar al formulario A.34?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(C_A56.this, C_A34.class);
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
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A56.this);
                    myBulid.setTitle("Formulario A.58");
                    myBulid.setIcon(R.drawable.temperatura1);
                    myBulid.setMessage("¿Desea ingresar al formulario A.58?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(C_A56.this, C_A58.class);
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
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A56.this);
                    myBulid.setTitle("Formulario A.71");
                    myBulid.setIcon(R.drawable.luminosidad1);
                    myBulid.setMessage("¿Desea ingresar al formulario A.71?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(C_A56.this, C_A71.class);
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
                    if (lAlertas.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen alertas activas", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS() == false) {
                        Toast.makeText(getApplicationContext(), "Debe ingresar al menos un valor antes de guardar", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (VALIDAR_DATOS_CORRECTOS() == false) {
                        return;
                    }
                    Ronda = CONSULTAR_RONDA() + 1;
                    LinearLayout ly;
                    ListaA56 = new ArrayList<A56>();
                    Hora_Salida = getTime();
                    for (int i = 0; i < lAlertas.getChildCount(); i++) {
                        ly = (LinearLayout) lAlertas.getChildAt(i);
                        Tdo_Alerta = ly.getTag().toString();
                        final TextInputLayout tilA = (TextInputLayout) ly.getChildAt(5);
                        final TextInputLayout tilC = (TextInputLayout) ly.getChildAt(6);
                        final TextInputLayout tilPDUA = (TextInputLayout) ly.getChildAt(7);
                        final TextInputLayout tilPDUB = (TextInputLayout) ly.getChildAt(8);
                        A = tilA.getEditText().getText().toString().equals("") ? "0" : tilA.getEditText().getText().toString();
                        C = tilC.getEditText().getText().toString().equals("") ? "0" : tilC.getEditText().getText().toString();
                        PDUA = tilPDUA.getEditText().getText().toString().equals("") ? "0" : tilPDUA.getEditText().getText().toString();
                        PDUB = tilPDUB.getEditText().getText().toString().equals("") ? "0" : tilPDUB.getEditText().getText().toString();
                        A56 a56 = new A56("", Tdo_Alerta, A, C, PDUA, PDUB, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA56.add(a56);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_A56.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Datos = Crud_A56.OBTENER_INSTANCIA(getApplicationContext());
                                            try {
                                                Datos.GETBD().beginTransaction();
                                                String cliente = "";
                                                cliente = Access.equals("BP") ? "BP" : "DN";
                                                for (int i = 0; i < ListaA56.size(); i++) {
                                                    long insertedResult = Datos.INSERTAR_A56(ListaA56.get(i), cliente);
                                                    if (insertedResult == -1) {
                                                        Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                    }
                                                }
                                                Datos.GETBD().setTransactionSuccessful();
                                            } finally {
                                                Datos.GETBD().endTransaction();
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
                Intent intent = new Intent(C_A56.this, C_Menu_Principal.class);
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

    /*public boolean VALIDAR_DATOS_VACIOS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAlertas.getChildCount(); i++) {
            ly = (LinearLayout) lAlertas.getChildAt(i);
            final TextInputLayout tilA = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilC = (TextInputLayout) ly.getChildAt(6);
            final TextInputLayout tilPDUA = (TextInputLayout) ly.getChildAt(7);
            final TextInputLayout tilPDUB = (TextInputLayout) ly.getChildAt(8);
            if (tilA.getEditText().getText().toString().equals("")) {
                tilA.requestFocus();
                tilA.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAlertas.getChildCount();
            }
            if (tilC.getEditText().getText().toString().equals("")) {
                tilC.requestFocus();
                tilC.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAlertas.getChildCount();
            }
            if (tilPDUA.getEditText().getText().toString().equals("")) {
                tilPDUA.requestFocus();
                tilPDUA.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAlertas.getChildCount();
            }
            if (tilPDUB.getEditText().getText().toString().equals("")) {
                tilPDUB.requestFocus();
                tilPDUB.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAlertas.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }*/

    public boolean VALIDAR_DATOS_VACIOS() {
        int condicion = 1;
        LinearLayout ly;
        int contador = 0;
        for (int i = 0; i < lAlertas.getChildCount(); i++) {
            ly = (LinearLayout) lAlertas.getChildAt(i);
            final TextInputLayout tilA = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilC = (TextInputLayout) ly.getChildAt(6);
            final TextInputLayout tilPDUA = (TextInputLayout) ly.getChildAt(7);
            final TextInputLayout tilPDUB = (TextInputLayout) ly.getChildAt(8);
            if (tilA.getEditText().getText().toString().equals("")) {
                contador++;
                i = lAlertas.getChildCount();
            }
            if (tilC.getEditText().getText().toString().equals("")) {
                contador++;
                i = lAlertas.getChildCount();
            }
            if (tilPDUA.getEditText().getText().toString().equals("")) {
                contador++;
                i = lAlertas.getChildCount();
            }
            if (tilPDUB.getEditText().getText().toString().equals("")) {
                contador++;
                i = lAlertas.getChildCount();
            }
        }
        if (contador == 4) {
            condicion = 0;
        } else {
            condicion = -1;
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAlertas.getChildCount(); i++) {
            ly = (LinearLayout) lAlertas.getChildAt(i);
            final TextInputLayout tilA = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilC = (TextInputLayout) ly.getChildAt(6);
            final TextInputLayout tilPDUA = (TextInputLayout) ly.getChildAt(7);
            final TextInputLayout tilPDUB = (TextInputLayout) ly.getChildAt(8);
            if (!tilA.getEditText().getText().toString().equals("")) {
                if (!(tilA.getEditText().getText().toString().equals("1") || tilA.getEditText().getText().toString().equals("0"))) {
                    tilA.requestFocus();
                    tilA.getEditText().setError("El valor ingresado debe ser 0 o 1");
                    condicion = 0;
                    i = lAlertas.getChildCount();
                }
            }
            if (!tilC.getEditText().getText().toString().equals("")) {
                if (!(tilC.getEditText().getText().toString().equals("1") || tilC.getEditText().getText().toString().equals("0"))) {
                    tilC.requestFocus();
                    tilC.getEditText().setError("El valor ingresado debe ser 0 o 1");
                    condicion = 0;
                    i = lAlertas.getChildCount();
                }
            }
            if (!tilPDUA.getEditText().getText().toString().equals("")) {
                if (!(tilPDUA.getEditText().getText().toString().equals("1") || tilPDUA.getEditText().getText().toString().equals("0"))) {
                    tilPDUA.requestFocus();
                    tilPDUA.getEditText().setError("El valor ingresado debe ser 0 o 1");
                    condicion = 0;
                    i = lAlertas.getChildCount();
                }
            }
            if (!tilPDUB.getEditText().getText().toString().equals("")) {
                if (!(tilPDUB.getEditText().getText().toString().equals("1") || tilPDUB.getEditText().getText().toString().equals("0"))) {
                    tilPDUB.requestFocus();
                    tilPDUB.getEditText().setError("El valor ingresado debe ser 0 o 1");
                    condicion = 0;
                    i = lAlertas.getChildCount();
                }
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
        Crud_A56 DATOS;
        DATOS = Crud_A56.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            if (Access.equals("BP")) {
                T_A56 = DATOS.CONSULTA_GENERAL_RONDA_BP(Tdo_Turno, getDate());
            } else {
                T_A56 = DATOS.CONSULTA_GENERAL_RONDA_DN(Tdo_Turno, getDate());
            }
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_A56.moveToFirst() == false) {
            //el cursor está vacío
            ronda = 0;
        } else {
            int P_Ronda = T_A56.getColumnIndex(IT_A56.I_A56.RONDA);
            for (T_A56.moveToFirst(); !T_A56.isAfterLast(); T_A56.moveToNext()) {
                for (int i = 0; i < ListaRonda.size(); i++) {
                    if (T_A56.getString(P_Ronda).equals(ListaRonda.get(i).toString())) {
                        ronda = Integer.valueOf(ListaRonda.get(i).toString());
                    }
                }
            }
        }
        return ronda;
    }

    public void LIMPIAR_DATOS() {
        Tdo_Alerta = "";
        A = "";
        C = "";
        PDUA = "";
        PDUB = "";
        LinearLayout ly;
        ListaA56 = new ArrayList<A56>();
        for (int i = 0; i < lAlertas.getChildCount(); i++) {
            ly = (LinearLayout) lAlertas.getChildAt(i);
            final TextInputLayout tilA = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilC = (TextInputLayout) ly.getChildAt(6);
            final TextInputLayout tilPDUA = (TextInputLayout) ly.getChildAt(7);
            final TextInputLayout tilPDUB = (TextInputLayout) ly.getChildAt(8);
            tilA.getEditText().setText("");
            tilC.getEditText().setText("");
            tilPDUA.getEditText().setText("");
            tilPDUB.getEditText().setText("");
        }

    }

    public void DIBUJAR_FILAS_EN_LAYOUT() {

        if (ListaAlerta.size() == 0) {
            return;
        }

        for (int i = 0; i < ListaAlerta.size(); i++) {

            CARGAR_FILA(ListaAlerta.get(i).getID_FILA());
            CARGAR_RACK(ListaAlerta.get(i).getID_RACK());
            CARGAR_UR(ListaAlerta.get(i).getUR());
            CARGAR_EQUIPO(ListaAlerta.get(i).getID_EQUIPO());

            final LinearLayout lFilasAlertas = new LinearLayout(this);

            final Button btnMensaje = new Button(this);
            final Button btnDesactivar = new Button(this);

            final TextInputLayout tilFila = new TextInputLayout(this);
            final TextInputLayout tilRack = new TextInputLayout(this);
            final TextInputLayout tilUr = new TextInputLayout(this);
            final TextInputLayout tilDescripcion = new TextInputLayout(this);
            final TextInputLayout tilA = new TextInputLayout(this);
            final TextInputLayout tilC = new TextInputLayout(this);
            final TextInputLayout tilPDUA = new TextInputLayout(this);
            final TextInputLayout tilPDUB = new TextInputLayout(this);

            final EditText etFila = new EditText(this);
            final EditText etRack = new EditText(this);
            final EditText etUr = new EditText(this);
            final EditText etDescripcion = new EditText(this);
            final EditText etA = new EditText(this);
            final EditText etC = new EditText(this);
            final EditText etPDUA = new EditText(this);
            final EditText etPDUB = new EditText(this);

            lFilasAlertasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            btnMensajeParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            btnDesactivarParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etFilaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRackParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etUrParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etDescripcionParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etCParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etPDUAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etPDUBParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasAlertas.setLayoutParams(lFilasAlertasParams);
            lFilasAlertas.setOrientation(LinearLayout.HORIZONTAL);
            lFilasAlertas.setTag(ListaAlerta.get(i).getID());
            lFilasAlertas.setId(i);

            btnMensajeParams.gravity = Gravity.CENTER;
            btnMensajeParams.width = 50;
            btnMensaje.setLayoutParams(btnMensajeParams);
            btnMensaje.setText("!");
            btnMensaje.setTag(i);

            btnDesactivarParams.gravity = Gravity.CENTER;
            btnDesactivarParams.width = 50;
            btnDesactivar.setLayoutParams(btnDesactivarParams);
            //btnDesactivar.setText("X");
            btnDesactivar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.desactivar, 0, 0, 0);
            btnDesactivar.setTag(i);

            etFilaParams.gravity = Gravity.CENTER;
            etFilaParams.width = 80;
            etFila.setLayoutParams(etFilaParams);
            etFila.setTag(ListaAlerta.get(i).getID_FILA());
            //etFila.setHint("Fila");
            etFila.setText(Nombre_Fila);
            etFila.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etFila.setTextColor(getThemeAccentColor(this));
            etFila.setEnabled(false);

            etRackParams.gravity = Gravity.CENTER;
            etRackParams.width = 100;
            etRack.setLayoutParams(etRackParams);
            etRack.setTag(ListaAlerta.get(i).getID_RACK());
            //etRack.setHint("Rack");
            etRack.setText(Nombre_Rack);
            etRack.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRack.setTextColor(getThemeAccentColor(this));
            etRack.setEnabled(false);

            etUrParams.gravity = Gravity.CENTER;
            etUrParams.width = 50;
            etUr.setLayoutParams(etUrParams);
            etUr.setTag(Tdo_Rack_Ur);
            //etUr.setHint("Ur");
            etUr.setText(Nombre_Rack_Ur);
            etUr.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etUr.setTextColor(getThemeAccentColor(this));
            etUr.setEnabled(false);

            etDescripcionParams.gravity = Gravity.CENTER;
            etDescripcionParams.width = 200;
            etDescripcion.setLayoutParams(etDescripcionParams);
            etDescripcion.setTag(ListaAlerta.get(i).getID_EQUIPO());
            //etDescripcion.setHint("Nombre equipo");
            etDescripcion.setText(Nombre_Equipo);
            etDescripcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etDescripcion.setTextColor(getThemeAccentColor(this));
            etDescripcion.setEnabled(false);

            etAParams.gravity = Gravity.CENTER;
            etAParams.width = 50;
            etA.setLayoutParams(etAParams);
            etA.setId(i);
            //etA.setHint("A");
            etA.setText("");
            etA.setInputType(InputType.TYPE_CLASS_NUMBER);
            etA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etA.setTextColor(getThemeAccentColor(this));
            etA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            etCParams.gravity = Gravity.CENTER;
            etCParams.width = 50;
            etC.setLayoutParams(etCParams);
            //etC.setHint("C");
            etC.setText("");
            etC.setInputType(InputType.TYPE_CLASS_NUMBER);
            etC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etC.setTextColor(getThemeAccentColor(this));
            etC.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            etPDUAParams.gravity = Gravity.CENTER;
            etPDUAParams.width = 50;
            etPDUA.setLayoutParams(etPDUAParams);
            //etPDUA.setHint("PDUA");
            etPDUA.setText("");
            etPDUA.setInputType(InputType.TYPE_CLASS_NUMBER);
            etPDUA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etPDUA.setTextColor(getThemeAccentColor(this));
            etPDUA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            etPDUBParams.gravity = Gravity.CENTER;
            etPDUBParams.width = 50;
            etPDUB.setLayoutParams(etPDUBParams);
            //etPDUB.setHint("PDUB");
            etPDUB.setText("");
            etPDUB.setInputType(InputType.TYPE_CLASS_NUMBER);
            etPDUB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etPDUB.setTextColor(getThemeAccentColor(this));
            etPDUB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});

            tilFila.addView(etFila);
            tilRack.addView(etRack);
            tilUr.addView(etUr);
            tilDescripcion.addView(etDescripcion);
            tilA.addView(etA);
            tilC.addView(etC);
            tilPDUA.addView(etPDUA);
            tilPDUB.addView(etPDUB);

            etA.requestFocus();

            btnMensaje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bandera = bandera + 1;
                    if (ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getIMAGEN() != null) {
                        Toast.makeText(getApplicationContext(), "ID = "+Integer.valueOf(btnMensaje.getTag().toString()), Toast.LENGTH_LONG).show();
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getIMAGEN());
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        Image.setImageBitmap(theImage);
                    } else {
                        Image.invalidate();
                        Image.setImageBitmap(null);
                    }
                    if (bandera == 1) {
                        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                        View customView = inflater.inflate(R.layout.l_foto, null);

                        mPopupWindow = new PopupWindow(
                                customView,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );

                        // Set an elevation value for popup window
                        // Call requires API level 21
                        if (Build.VERSION.SDK_INT >= 21) {
                            mPopupWindow.setElevation(5.0f);
                        }

                        // Get a reference for the custom view close button
                        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                        ImageButton rotarButton = (ImageButton) customView.findViewById(R.id.ib_rotar);
                        TextView txComenatario = (TextView) customView.findViewById(R.id.txComentario);
                        TextView txTicket = (TextView) customView.findViewById(R.id.txTicket);
                        final ImageView image = (ImageView) customView.findViewById(R.id.imgView);

                        Drawable dr = ((ImageView) Image).getDrawable();
                        final Bitmap bitmap = ((BitmapDrawable) dr.getCurrent()).getBitmap();

                        if (bitmap != null) {
                            image.setImageBitmap(bitmap);
                            if (!ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getTICKET_EXTERNO().equals("")) {
                                txTicket.setVisibility(View.VISIBLE);
                                txTicket.setText("Ticket OTRS: " + ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getTICKET_EXTERNO());
                                //Toast.makeText(getApplicationContext(), "Ticket OTRS: " + ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getTICKET_EXTERNO(), Toast.LENGTH_LONG).show();
                            } else {
                                txTicket.setVisibility(View.VISIBLE);
                                txTicket.setText("No se registró ticket OTRS");
                                //Toast.makeText(getApplicationContext(), "No se registró ticket OTRS", Toast.LENGTH_LONG).show();
                            }
                            if (!ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getCOMENTARIO().equals("")) {
                                txComenatario.setVisibility(View.VISIBLE);
                                txComenatario.setText("Comentario: " + ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getCOMENTARIO());
                                //Toast.makeText(getApplicationContext(), "Comentario: " + ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getCOMENTARIO(), Toast.LENGTH_LONG).show();
                            } else {
                                txComenatario.setVisibility(View.VISIBLE);
                                txComenatario.setText("No se registró comentario");
                                //Toast.makeText(getApplicationContext(), "No se registró comentario", Toast.LENGTH_LONG).show();
                            }
                        }
                        if (bitmap == null) {
                            //bandera = 0;
                            Toast.makeText(getApplicationContext(), "ID = "+Integer.valueOf(btnMensaje.getTag().toString()), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "No se cargó foto", Toast.LENGTH_LONG).show();
                            image.setImageBitmap(bitmap);
                            if (!ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getTICKET_EXTERNO().equals("")) {
                                txTicket.setVisibility(View.VISIBLE);
                                txTicket.setText("Ticket OTRS: " + ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getTICKET_EXTERNO());
                                //Toast.makeText(getApplicationContext(), "Ticket OTRS: " + ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getTICKET_EXTERNO(), Toast.LENGTH_LONG).show();
                            } else {
                                txTicket.setVisibility(View.VISIBLE);
                                txTicket.setText("No se registró ticket OTRS");
                                //Toast.makeText(getApplicationContext(), "No se registró ticket OTRS", Toast.LENGTH_LONG).show();
                            }
                            if (!ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getCOMENTARIO().equals("")) {
                                txComenatario.setVisibility(View.VISIBLE);
                                txComenatario.setText("Comentario: " + ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getCOMENTARIO());
                                //Toast.makeText(getApplicationContext(), "Comentario: " + ListaAlerta.get(Integer.valueOf(btnMensaje.getTag().toString())).getCOMENTARIO(), Toast.LENGTH_LONG).show();
                            } else {
                                txComenatario.setVisibility(View.VISIBLE);
                                txComenatario.setText("No se registró comentario");
                                //Toast.makeText(getApplicationContext(), "No se registró comentario", Toast.LENGTH_LONG).show();
                            }
                            //return;
                        }

                        // Set a click listener for the popup window close button
                        closeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Dismiss the popup window
                                mPopupWindow.dismiss();
                                bandera = 0;
                            }
                        });

                        rotarButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (bitmap != null) {
                                    rotarImagen(bitmap, image);
                                }
                            }
                        });

                        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
                    }
                }
            });

            btnDesactivar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "ID = "+Integer.valueOf(btnDesactivar.getTag().toString()), Toast.LENGTH_LONG).show();
                    AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A56.this);
                    myBulid.setTitle("Desactivar Alerta");
                    myBulid.setIcon(R.drawable.desactivar);
                    myBulid.setMessage("¿Desea desactivar la alerta?");
                    myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Crud_Alerta DATOS;
                            DATOS = Crud_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                            try {
                                DATOS.GETBD().beginTransaction();
                                long updatedResult = DATOS.DESACTIVAR_ALERTA(new Alerta(ListaAlerta.get(Integer.valueOf(btnDesactivar.getTag().toString())).getID(), ListaAlerta.get(Integer.valueOf(btnDesactivar.getTag().toString())).getCOMENTARIO(), "I", ListaAlerta.get(Integer.valueOf(btnDesactivar.getTag().toString())).getUSUARIO_INGRESA(), Id_Operador, ListaAlerta.get(Integer.valueOf(btnDesactivar.getTag().toString())).getFECHA_INGRESO(), getDateTime()));
                                if (updatedResult == -1) {
                                    throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                                }
                                DATOS.GETBD().setTransactionSuccessful();
                            } finally {
                                DATOS.GETBD().endTransaction();
                            }
                            Toast.makeText(getApplicationContext(), "El registro se desactivó correctamente", Toast.LENGTH_LONG).show();
                            lAlertas.removeView((LinearLayout) btnDesactivar.getParent());
                            if (lAlertas.getChildCount() == 0) {
                                txMensaje.setText("No existen alertas activas");
                            } else if (lAlertas.getChildCount() == 1) {
                                txMensaje.setText("Se encontró " + String.valueOf(lAlertas.getChildCount()) + " alerta activa");
                            } else {
                                txMensaje.setText("Se encontraron " + String.valueOf(lAlertas.getChildCount()) + " alertas activas");
                            }
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
            });

            lFilasAlertas.addView(btnMensaje);
            lFilasAlertas.addView(tilFila);
            lFilasAlertas.addView(tilRack);
            lFilasAlertas.addView(tilUr);
            lFilasAlertas.addView(tilDescripcion);
            lFilasAlertas.addView(tilA);
            lFilasAlertas.addView(tilC);
            lFilasAlertas.addView(tilPDUA);
            lFilasAlertas.addView(tilPDUB);
            lFilasAlertas.addView(btnDesactivar);

            lAlertas.addView(lFilasAlertas);

        }

    }

    public void ELIMINAR_FILAS_EN_LAYOUT() {

        for (int i = 0; i < lAlertas.getChildCount(); i++) {
            lAlertas.removeAllViews();
        }
        ListaAlerta = new ArrayList<Alerta>();
    }

    private void rotarImagen(Bitmap bitmap, ImageView image) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        image.setImageBitmap(rotated);
    }

    public void CARGAR_FILA(String ID_Fila) {
        Crud_Fila DATOS;
        DATOS = Crud_Fila.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Fila = DATOS.CONSULTA_GENERAL_FILA_ID(ID_Fila);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Fila.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Fila = "";
            Id_Fila = "";
            Nombre_Fila = "";
            return;
        }
        int P_TDO = T_Fila.getColumnIndex(IT_Fila.I_FILA.ID);
        int P_Id_Fila = T_Fila.getColumnIndex(IT_Fila.I_FILA.ID_FILA);
        int P_Nombre_Fila = T_Fila.getColumnIndex(IT_Fila.I_FILA.NOMBRE_FILA);
        for (T_Fila.moveToFirst(); !T_Fila.isAfterLast(); T_Fila.moveToNext()) {
            Tdo_Fila = T_Fila.getString(P_TDO);
            Id_Fila = T_Fila.getString(P_Id_Fila);
            Nombre_Fila = T_Fila.getString(P_Nombre_Fila);
        }
    }

    public void CARGAR_RACK(String ID_Rack) {
        Crud_Rack DATOS;
        DATOS = Crud_Rack.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Rack = DATOS.CONSULTA_GENERAL_POR_ID(ID_Rack);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Rack.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Rack = "";
            Nombre_Rack = "";
            return;
        }
        int P_TDO = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID);
        int P_Nombre_Rack = T_Rack.getColumnIndex(IT_Rack.I_RACK.NOMBRE_RACK);
        for (T_Rack.moveToFirst(); !T_Rack.isAfterLast(); T_Rack.moveToNext()) {
            Tdo_Rack = T_Rack.getString(P_TDO);
            Nombre_Rack = T_Rack.getString(P_Nombre_Rack);
        }
    }

    public void CARGAR_UR(String ID_Ur) {
        Crud_Rack_Ur DATOS;
        DATOS = Crud_Rack_Ur.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Rack_Ur = DATOS.CONSULTA_GENERAL_RACK_UR_POR_ID(ID_Ur);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Rack_Ur.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Rack_Ur = "";
            Nombre_Rack_Ur = "";
            return;
        }
        int P_TDO = T_Rack_Ur.getColumnIndex(IT_Rack_Ur.I_RACK_UR.ID);
        int P_Nombre_Rack_Ur = T_Rack_Ur.getColumnIndex(IT_Rack_Ur.I_RACK_UR.UR);
        for (T_Rack_Ur.moveToFirst(); !T_Rack_Ur.isAfterLast(); T_Rack_Ur.moveToNext()) {
            Tdo_Rack_Ur = T_Rack_Ur.getString(P_TDO);
            Nombre_Rack_Ur = T_Rack_Ur.getString(P_Nombre_Rack_Ur);
        }
    }

    public void CARGAR_EQUIPO(String ID_Equipo) {
        Crud_Equipo DATOS;
        DATOS = Crud_Equipo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Equipo = DATOS.CONSULTA_GENERAL_EQUIPO_POR_ID(ID_Equipo);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Equipo.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Equipo = "";
            Nombre_Equipo = "";
            Marca = "";
            Modelo = "";
            Serie = "";
            return;
        }
        int P_TDO = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.ID);
        int P_Nombre_Equipo = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.NOMBRE_EQUIPO);
        int P_Marca = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.MARCA);
        int P_Modelo = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.MODELO);
        int P_Serie = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.SERIE);
        for (T_Equipo.moveToFirst(); !T_Equipo.isAfterLast(); T_Equipo.moveToNext()) {
            Tdo_Equipo = T_Equipo.getString(P_TDO);
            Nombre_Equipo = T_Equipo.getString(P_Nombre_Equipo);
            Marca = T_Equipo.getString(P_Marca);
            Modelo = T_Equipo.getString(P_Modelo);
            Serie = T_Equipo.getString(P_Serie);
        }
    }

    public void CARGAR_ALERTAS() {
        Crud_Alerta DATOS;
        DATOS = Crud_Alerta.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            String cliente = "";
            cliente = Access.equals("BP") ? "%ALEBP%" : "%ALEDN%";
            T_Alerta = DATOS.CONSULTA_GENERAL_ALERTA_POR_CLIENTE("A", cliente);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Alerta.moveToFirst() == false) {
            //el cursor está vacío
            ListaAlerta = new ArrayList<Alerta>();
            Tdo_Alerta = "";
            ID_Rack = "";
            ID_Fila = "";
            ID_Equipo = "";
            ID_UR = "";
            Comentario = "";
            Ticket = "";
            Fecha_Ingreso = "";
            Usuario_Ingresa = "";
            return;
        }
        int P_TDO = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID);
        int P_Id_Fila = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_FILA);
        int P_Id_Rack = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_RACK);
        int P_Id_Ur = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_RACK_UR);
        int P_Id_Equipo = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_EQUIPO);
        int P_Id_TipoAlerta = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_TIPO_ALERTA);
        int P_Id_ColorAlerta = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_COLOR_ALERTA);
        int P_Comentario = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.COMENTARIO);
        int P_Ticket = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.TICKET_EXTERNO);
        int P_Imagen = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.IMAGEN);
        int P_Fecha_Ingreso = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.FECHA_INGRESO);
        int P_Usuario_Ingresa = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.USUARIO_INGRESA);
        ListaAlerta = new ArrayList<Alerta>();
        for (T_Alerta.moveToFirst(); !T_Alerta.isAfterLast(); T_Alerta.moveToNext()) {
            Tdo_Alerta = T_Alerta.getString(P_TDO);
            ID_Fila = T_Alerta.getString(P_Id_Fila);
            ID_Rack = T_Alerta.getString(P_Id_Rack);
            ID_UR = T_Alerta.getString(P_Id_Ur);
            ID_Equipo = T_Alerta.getString(P_Id_Equipo);
            Tipo_Alerta = T_Alerta.getString(P_Id_TipoAlerta);
            Color_Alerta = T_Alerta.getString(P_Id_ColorAlerta);
            Comentario = T_Alerta.getString(P_Comentario);
            Ticket = T_Alerta.getString(P_Ticket);
            photo = T_Alerta.getBlob(P_Imagen);
            Fecha_Ingreso = T_Alerta.getString(P_Fecha_Ingreso);
            Usuario_Ingresa = T_Alerta.getString(P_Usuario_Ingresa);
            Alerta Alerta = new Alerta(Tdo_Alerta, ID_Fila, ID_Rack, ID_UR, ID_Equipo, Tipo_Alerta, Color_Alerta, Comentario, Ticket, photo, Usuario_Ingresa, Fecha_Ingreso);
            ListaAlerta.add(Alerta);
        }
    }

    private String getTimeA56() throws ParseException {
        DateFormat Format = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm", Locale.getDefault());
        Date hora1 = Format.parse(H_inicio);
        Date hora2 = Format.parse(getDateTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        long lantes = hora1.getTime();
        long lahora = hora2.getTime();
        long diferencia = (lahora - lantes);
        calendar.setTimeInMillis(diferencia);
        return dateFormat.format(calendar.getTime());
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

    private String getDateTimeAlerta() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm", Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.MINUTE, Integer.valueOf(timeA56)*-1);
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

    public void OBTENER_PERMISOS() {
        Crud_Grupo DATOS;
        DATOS = Crud_Grupo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Grupo = DATOS.CONSULTA_GENERAL_GRUPO_ID(Tdo_Grupo_1);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Grupo.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Grupo = "";
            Id_Grupo = "";
            Id_Grupo_Temporal = "";
            ischeckFilas = "";
            ischeckClientes = "";
            ischeckCantidadUR = "";
            ischeckAlertas = "";
            ischeckTipoAlertas = "";
            ischeckColorAlertas = "";
            ischeckRacks = "";
            ischeckEquipos = "";
            ischeckChillers = "";
            ischeckPasillos = "";
            ischeckAreas = "";
            ischeckGrupos = "";
            ischeckVersion = "";
            ischeckParametros = "";
            return;
        }
        int P_Tdo_Grupo = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.ID);
        int P_ID_GRUPO = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.ID_GRUPO);
        int P_NOMBRE_GRUPO = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.NOMBRE_GRUPO);
        int P_Pin_Seguridad = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PIN_SEGURIDAD);
        int P_Filas = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_FILA);
        int P_Clientes = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_CLIENTE);
        int P_Cantidad_UR = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_CANTIDAD_UR);
        int P_Rack = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_RACK);
        int P_Equipo = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_EQUIPO);
        int P_Alerta = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_ALERTA);
        int P_Tipo_Alerta = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_TIPO_ALERTA);
        int P_Color_Alerta = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_COLOR_ALERTA);
        int P_Pasillo = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_PASILLO);
        int P_Chiller = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_CHILLER);
        int P_Area = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_AREA);
        int P_Grupo = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_GRUPO);
        int P_Version = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_VERSION);
        int P_Parametros = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_PARAMETROS);
        for (T_Grupo.moveToFirst(); !T_Grupo.isAfterLast(); T_Grupo.moveToNext()) {
            Tdo_Grupo = T_Grupo.getString(P_Tdo_Grupo);
            Id_Grupo = T_Grupo.getString(P_ID_GRUPO);
            Nombre_Grupo = T_Grupo.getString(P_NOMBRE_GRUPO);
            Pin_Seguridad = T_Grupo.getString(P_Pin_Seguridad);
            ischeckFilas = T_Grupo.getString(P_Filas);
            ischeckClientes = T_Grupo.getString(P_Clientes);
            ischeckCantidadUR = T_Grupo.getString(P_Cantidad_UR);
            ischeckRacks = T_Grupo.getString(P_Rack);
            ischeckEquipos = T_Grupo.getString(P_Equipo);
            ischeckAlertas = T_Grupo.getString(P_Alerta);
            ischeckTipoAlertas = T_Grupo.getString(P_Tipo_Alerta);
            ischeckColorAlertas = T_Grupo.getString(P_Color_Alerta);
            ischeckPasillos = T_Grupo.getString(P_Pasillo);
            ischeckChillers = T_Grupo.getString(P_Chiller);
            ischeckAreas = T_Grupo.getString(P_Area);
            ischeckGrupos = T_Grupo.getString(P_Grupo);
            ischeckVersion = T_Grupo.getString(P_Version);
            ischeckParametros = T_Grupo.getString(P_Parametros);
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
