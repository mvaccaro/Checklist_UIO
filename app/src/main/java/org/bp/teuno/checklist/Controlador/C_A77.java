package org.bp.teuno.checklist.Controlador;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabItem;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.bp.teuno.checklist.Modelo.A77_Aire;
import org.bp.teuno.checklist.Modelo.A77_Area;
import org.bp.teuno.checklist.Modelo.A77_Pasillo;
import org.bp.teuno.checklist.Modelo.Area;
import org.bp.teuno.checklist.Modelo.Pasillo;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_A77_Pasillo;
import org.bp.teuno.checklist.SQLite.IT_Area;
import org.bp.teuno.checklist.SQLite.IT_Pasillo;
import org.bp.teuno.checklist.SQLite.IT_Turno;
import org.bp.teuno.checklist.UI.Crud_A77;
import org.bp.teuno.checklist.UI.Crud_Area;
import org.bp.teuno.checklist.UI.Crud_Inicio_Sesion;
import org.bp.teuno.checklist.UI.Crud_Pasillo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import org.bp.teuno.checklist.Modelo.A77_Chiller;
//import org.bp.teuno.checklist.UI.Crud_A77_Chiller;


public class C_A77 extends AppCompatActivity {

    private final long interval = 1 * 1000;
    String Tdo_A77_Pasillo = "", Tdo_A77_Area = "", Luminosidad = "", Luminosidad_A = "", Luminosidad_B = "", Luminosidad_C = "", Ruido = "", Ruido_A = "", Ruido_B = "", Ruido_C = "", Hora_Ingreso = "", Hora_Salida = "", Turno = "", Evento = "", Descripcion = "";
    int Ronda = 0;
    String Tdo_Pasillo_Frio = "", Tdo_Pasillo_Caliente = "", Id_Pasillo = "", Nombre_Pasillo = "", Tdo_Tipo_Pasillo = "", A1 = "", A2 = "", A3 = "", B1 = "", B2 = "", B3 = "", C1 = "", C2 = "", C3 = "";
    String Tdo_Area = "", Id_Area = "", Nombre_Area = "", Tdo_Tipo_Area = "", Tdo_Aire = "";
    LinearLayout lPasillos, lPasillosFrios, lPasillosCalientes, lAreas, lAire;
    TextView txMensaje1, txMensaje2, txMensaje3, txMensaje4;
    LayoutParams lFilasPasillosParams, lFilasAreasParams, etLuminosidadAParams, etPasilloParams, etAreaParams, etLuminosidadBParams, etLuminosidadCParams, etRuidoAParams, etRuidoBParams, etRuidoCParams, etLuminosidadParams, etRuidoParams, etEventoParams, etDescripcionParams, lFilasAireParams, etA1Params, etA2Params, etA3Params, etB1Params, etB2Params, etB3Params, etC1Params, etC2Params, etC3Params;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabGuardar, fabA34, fabA56, fabA71;
    TabItem Tab1;
    ArrayList<A77_Pasillo> ListaA77Pasillo = new ArrayList<A77_Pasillo>();
    ArrayList<A77_Area> ListaA77Area = new ArrayList<A77_Area>();
    ArrayList<A77_Aire> ListaA77Aire = new ArrayList<A77_Aire>();
    ArrayList<Pasillo> ListaPasilloFrio = new ArrayList<Pasillo>();
    ArrayList<Pasillo> ListaPasilloCaliente = new ArrayList<Pasillo>();
    ArrayList<Area> ListaArea = new ArrayList<Area>();
    ArrayList<String> ListaRonda = new ArrayList<String>();
    Cursor T_A77_Pasillo, T_A77_Area, T_Pasillo, T_Area;
    Crud_A77 Datos1;
    String Id_Operador = "", Nombre_Operador, Tdo_Turno, Turno_Operador;
    CountDownTimer countDownTimer;
    private String Tdo_Grupo_1;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME
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
        setContentView(R.layout.l_a77);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo_1 = bundle.getString("Id_Grupo_Operador").toString();

        lPasillos = (LinearLayout) findViewById(R.id.lPasillos);
        lAire = (LinearLayout) findViewById(R.id.lAire);
        lPasillosFrios = (LinearLayout) findViewById(R.id.lPasillosFrios);
        lPasillosCalientes = (LinearLayout) findViewById(R.id.lPasillosCalientes);
        lAreas = (LinearLayout) findViewById(R.id.lAreas);
        txMensaje1 = (TextView) findViewById(R.id.txMensaje1);
        txMensaje2 = (TextView) findViewById(R.id.txMensaje2);
        txMensaje3 = (TextView) findViewById(R.id.txMensaje3);
        txMensaje4 = (TextView) findViewById(R.id.txMensaje4);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);

        Hora_Ingreso = getTime();

        TooltipCompat.setTooltipText(fabGuardar, "Guardar");

        CARGAR_PASILLOS_FRIOS();
        CARGAR_PASILLOS_CALIENTES();
        CARGAR_AREAS();
        CARGAR_LISTA_RONDAS();
        DIBUJAR_FILAS_DE_PASILLOS_FRIOS_FLUJO_AIRE_EN_LAYOUT();
        DIBUJAR_FILAS_DE_PASILLOS_FRIOS_LUMINOSIDAD_EN_LAYOUT();
        DIBUJAR_FILAS_DE_PASILLOS_CALIENTES_LUMINOSIDAD_EN_LAYOUT();
        DIBUJAR_FILAS_DE_AREAS_LUMINOSIDAD_EN_LAYOUT();

        if (ListaPasilloFrio.size() == 0) {
            txMensaje1.setText("No existen pasillos fríos activos");
            txMensaje4.setText("No existen pasillos fríos activos");
        } else if (ListaPasilloFrio.size() == 1) {
            txMensaje1.setText("Se encontró " + String.valueOf(ListaPasilloFrio.size()) + " pasillo frío activo");
            txMensaje4.setText("Se encontró " + String.valueOf(ListaPasilloFrio.size()) + " pasillo frío activo");
        } else {
            txMensaje1.setText("Se encontraron " + String.valueOf(ListaPasilloFrio.size()) + " pasillos fríos activos");
            txMensaje4.setText("Se encontraron " + String.valueOf(ListaPasilloFrio.size()) + " pasillos fríos activos");
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
                    if (VALIDAR_DATOS_VACIOS_PASILLOS_FRIOS_FLUJO_AIRE() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_PASILLOS_FRIOS_LUMINOSIDAD() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_PASILLOS_CALIENTES_LUMINOSIDAD() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_AREAS_LUMINOSIDAD() == false) {
                        return;
                    }
                /*if (VALIDAR_DATOS_CORRECTOS_PASILLOS_FRIOS_FLUJO_AIRE() == false) {
                    return;
                }
                if (VALIDAR_DATOS_CORRECTOS_PASILLOS_FRIOS_LUMINOSIDAD() == false) {
                    return;
                }
                if (VALIDAR_DATOS_CORRECTOS_PASILLOS_CALIENTES_LUMINOSIDAD() == false) {
                    return;
                }
                if (VALIDAR_DATOS_CORRECTOS_AREAS_LUMINOSIDAD() == false) {
                    return;
                }*/
                    Ronda = CONSULTAR_RONDA() + 1;
                    Hora_Salida = getTime();
                    ListaA77Aire = new ArrayList<A77_Aire>();
                    LinearLayout ly1, ly2, ly3, ly4;
                    for (int i = 0; i < lAire.getChildCount(); i++) {
                        ly4 = (LinearLayout) lAire.getChildAt(i);
                        Tdo_Aire = ly4.getTag().toString();
                        final TextInputLayout tilA1 = (TextInputLayout) ly4.getChildAt(1);
                        final TextInputLayout tilA2 = (TextInputLayout) ly4.getChildAt(2);
                        final TextInputLayout tilA3 = (TextInputLayout) ly4.getChildAt(3);
                        final TextInputLayout tilC1 = (TextInputLayout) ly4.getChildAt(4);
                        final TextInputLayout tilC2 = (TextInputLayout) ly4.getChildAt(5);
                        final TextInputLayout tilC3 = (TextInputLayout) ly4.getChildAt(6);
                        final TextInputLayout tilB1 = (TextInputLayout) ly4.getChildAt(7);
                        final TextInputLayout tilB2 = (TextInputLayout) ly4.getChildAt(8);
                        final TextInputLayout tilB3 = (TextInputLayout) ly4.getChildAt(9);
                        A1 = convertNumeric(tilA1.getEditText().getText().toString());
                        A2 = convertNumeric(tilA2.getEditText().getText().toString());
                        A3 = convertNumeric(tilA3.getEditText().getText().toString());
                        C1 = convertNumeric(tilC1.getEditText().getText().toString());
                        C2 = convertNumeric(tilC2.getEditText().getText().toString());
                        C3 = convertNumeric(tilC3.getEditText().getText().toString());
                        B1 = convertNumeric(tilB1.getEditText().getText().toString());
                        B2 = convertNumeric(tilB2.getEditText().getText().toString());
                        B3 = convertNumeric(tilB3.getEditText().getText().toString());
                        A77_Aire a77_aire = new A77_Aire("", Tdo_Aire, A1, A2, A3, B1, B2, B3, C1, C2, C3, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA77Aire.add(a77_aire);
                    }
                    ListaA77Pasillo = new ArrayList<A77_Pasillo>();
                    for (int i = 0; i < lPasillosFrios.getChildCount(); i++) {
                        ly1 = (LinearLayout) lPasillosFrios.getChildAt(i);
                        Tdo_Pasillo_Frio = ly1.getTag().toString();
                        final TextInputLayout tilLuminosidadA = (TextInputLayout) ly1.getChildAt(1);
                        final TextInputLayout tilLuminosidadC = (TextInputLayout) ly1.getChildAt(2);
                        final TextInputLayout tilLuminosidadB = (TextInputLayout) ly1.getChildAt(3);
                        final TextInputLayout tilRuidoA = (TextInputLayout) ly1.getChildAt(4);
                        final TextInputLayout tilRuidoC = (TextInputLayout) ly1.getChildAt(5);
                        final TextInputLayout tilRuidoB = (TextInputLayout) ly1.getChildAt(6);
                        final Switch tilEvento = (Switch) ly1.getChildAt(7);
                        final TextInputLayout tilDescripcion = (TextInputLayout) ly1.getChildAt(8);
                        Luminosidad_A = tilLuminosidadA.getEditText().getText().toString();
                        Luminosidad_C = tilLuminosidadC.getEditText().getText().toString();
                        Luminosidad_B = tilLuminosidadB.getEditText().getText().toString();
                        Ruido_A = tilRuidoA.getEditText().getText().toString();
                        Ruido_C = tilRuidoC.getEditText().getText().toString();
                        Ruido_B = tilRuidoB.getEditText().getText().toString();
                        if (tilEvento.isChecked()) {
                            Evento = "SI";
                        } else {
                            Evento = "NO";
                        }
                        Descripcion = tilDescripcion.getEditText().getText().toString().toUpperCase();
                        A77_Pasillo a77_pasillo = new A77_Pasillo("", Tdo_Pasillo_Frio, Luminosidad_A, Luminosidad_C, Luminosidad_B, Ruido_A, Ruido_C, Ruido_B, Evento, Descripcion, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA77Pasillo.add(a77_pasillo);
                    }
                    for (int i = 0; i < lPasillosCalientes.getChildCount(); i++) {
                        ly2 = (LinearLayout) lPasillosCalientes.getChildAt(i);
                        Tdo_Pasillo_Caliente = ly2.getTag().toString();
                        final TextInputLayout tilLuminosidadA = (TextInputLayout) ly2.getChildAt(1);
                        final TextInputLayout tilLuminosidadC = (TextInputLayout) ly2.getChildAt(2);
                        final TextInputLayout tilLuminosidadB = (TextInputLayout) ly2.getChildAt(3);
                        final TextInputLayout tilRuidoA = (TextInputLayout) ly2.getChildAt(4);
                        final TextInputLayout tilRuidoC = (TextInputLayout) ly2.getChildAt(5);
                        final TextInputLayout tilRuidoB = (TextInputLayout) ly2.getChildAt(6);
                        final Switch tilEvento = (Switch) ly2.getChildAt(7);
                        final TextInputLayout tilDescripcion = (TextInputLayout) ly2.getChildAt(8);
                        Luminosidad_A = tilLuminosidadA.getEditText().getText().toString();
                        Luminosidad_C = tilLuminosidadC.getEditText().getText().toString();
                        Luminosidad_B = tilLuminosidadB.getEditText().getText().toString();
                        Ruido_A = tilRuidoA.getEditText().getText().toString();
                        Ruido_C = tilRuidoC.getEditText().getText().toString();
                        Ruido_B = tilRuidoB.getEditText().getText().toString();
                        if (tilEvento.isChecked()) {
                            Evento = "SI";
                        } else {
                            Evento = "NO";
                        }
                        Descripcion = tilDescripcion.getEditText().getText().toString().toUpperCase();
                        A77_Pasillo a77_pasillo = new A77_Pasillo("", Tdo_Pasillo_Caliente, Luminosidad_A, Luminosidad_C, Luminosidad_B, Ruido_A, Ruido_C, Ruido_B, Evento, Descripcion, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA77Pasillo.add(a77_pasillo);
                    }
                    ListaA77Area = new ArrayList<A77_Area>();
                    for (int i = 0; i < lAreas.getChildCount(); i++) {
                        ly3 = (LinearLayout) lAreas.getChildAt(i);
                        Tdo_Area = ly3.getTag().toString();
                        final TextInputLayout tilLuminosidad = (TextInputLayout) ly3.getChildAt(1);
                        final TextInputLayout tilRuido = (TextInputLayout) ly3.getChildAt(2);
                        Luminosidad = tilLuminosidad.getEditText().getText().toString();
                        Ruido = tilRuido.getEditText().getText().toString();
                        A77_Area a77_area = new A77_Area("", Tdo_Area, Luminosidad, Ruido, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        ListaA77Area.add(a77_area);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_A77.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            long insertedResult1 = 0, insertedResult2 = 0, insertedResult3 = 0;
                                            Datos1 = Crud_A77.OBTENER_INSTANCIA(getApplicationContext());
                                            try {
                                                Datos1.GETBD().beginTransaction();
                                                for (int i = 0; i < ListaA77Pasillo.size(); i++) {
                                                    insertedResult1 = Datos1.INSERTAR_A77_PASILLO(ListaA77Pasillo.get(i));
                                                    if (insertedResult1 == -1) {
                                                        Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                    }
                                                }
                                                if (insertedResult1 != -1) {
                                                    for (int i = 0; i < ListaA77Area.size(); i++) {
                                                        insertedResult2 = Datos1.INSERTAR_A77_AREA(ListaA77Area.get(i));
                                                        if (insertedResult2 == -1) {
                                                            Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                            throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                        }
                                                    }
                                                }
                                                if (insertedResult1 != -1 && insertedResult2 != -1) {
                                                    for (int i = 0; i < ListaA77Aire.size(); i++) {
                                                        insertedResult3 = Datos1.INSERTAR_A77_AIRE(ListaA77Aire.get(i));
                                                        if (insertedResult3 == -1) {
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
                Intent intent = new Intent(C_A77.this, C_Menu_Principal.class);
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

    public boolean VALIDAR_DATOS_VACIOS_PASILLOS_FRIOS_FLUJO_AIRE() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAire.getChildCount(); i++) {
            ly = (LinearLayout) lAire.getChildAt(i);
            final TextInputLayout tilA1 = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilA2 = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilA3 = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilC1 = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilC2 = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilC3 = (TextInputLayout) ly.getChildAt(6);
            final TextInputLayout tilB1 = (TextInputLayout) ly.getChildAt(7);
            final TextInputLayout tilB2 = (TextInputLayout) ly.getChildAt(8);
            final TextInputLayout tilB3 = (TextInputLayout) ly.getChildAt(9);
            if (tilA1.getEditText().getText().toString().equals("")) {
                tilA1.requestFocus();
                tilA1.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilA2.getEditText().getText().toString().equals("")) {
                tilA2.requestFocus();
                tilA2.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilA3.getEditText().getText().toString().equals("")) {
                tilA3.requestFocus();
                tilA3.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilB1.getEditText().getText().toString().equals("")) {
                tilB1.requestFocus();
                tilB1.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilB2.getEditText().getText().toString().equals("")) {
                tilB2.requestFocus();
                tilB2.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilB3.getEditText().getText().toString().equals("")) {
                tilB3.requestFocus();
                tilB3.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilC1.getEditText().getText().toString().equals("")) {
                tilC1.requestFocus();
                tilC1.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilC2.getEditText().getText().toString().equals("")) {
                tilC2.requestFocus();
                tilC2.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilC3.getEditText().getText().toString().equals("")) {
                tilC3.requestFocus();
                tilC3.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAire.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_DATOS_CORRECTOS_PASILLOS_FRIOS_FLUJO_AIRE() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAire.getChildCount(); i++) {
            ly = (LinearLayout) lAire.getChildAt(i);
            final TextInputLayout tilA1 = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilA2 = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilA3 = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilC1 = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilC2 = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilC3 = (TextInputLayout) ly.getChildAt(6);
            final TextInputLayout tilB1 = (TextInputLayout) ly.getChildAt(7);
            final TextInputLayout tilB2 = (TextInputLayout) ly.getChildAt(8);
            final TextInputLayout tilB3 = (TextInputLayout) ly.getChildAt(9);
            if (tilA1.getEditText().getText().toString().equals("0")) {
                tilA1.requestFocus();
                tilA1.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilA2.getEditText().getText().toString().equals("0")) {
                tilA2.requestFocus();
                tilA2.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilA3.getEditText().getText().toString().equals("0")) {
                tilA3.requestFocus();
                tilA3.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilB1.getEditText().getText().toString().equals("0")) {
                tilB1.requestFocus();
                tilB1.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilB2.getEditText().getText().toString().equals("0")) {
                tilB2.requestFocus();
                tilB2.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilB3.getEditText().getText().toString().equals("0")) {
                tilB3.requestFocus();
                tilB3.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilC1.getEditText().getText().toString().equals("0")) {
                tilC1.requestFocus();
                tilC1.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilC2.getEditText().getText().toString().equals("0")) {
                tilC2.requestFocus();
                tilC2.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (tilC3.getEditText().getText().toString().equals("0")) {
                tilC3.requestFocus();
                tilC3.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAire.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean VALIDAR_ES_NUMERO_PASILLOS_FRIOS_FLUJO_AIRE() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAire.getChildCount(); i++) {
            ly = (LinearLayout) lAire.getChildAt(i);
            final TextInputLayout tilA1 = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilA2 = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilA3 = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilC1 = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilC2 = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilC3 = (TextInputLayout) ly.getChildAt(6);
            final TextInputLayout tilB1 = (TextInputLayout) ly.getChildAt(7);
            final TextInputLayout tilB2 = (TextInputLayout) ly.getChildAt(8);
            final TextInputLayout tilB3 = (TextInputLayout) ly.getChildAt(9);
            if (isNumeric(tilA1.getEditText().getText().toString())) {
                tilA1.requestFocus();
                tilA1.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (isNumeric(tilA2.getEditText().getText().toString())) {
                tilA2.requestFocus();
                tilA2.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (isNumeric(tilA3.getEditText().getText().toString())) {
                tilA3.requestFocus();
                tilA3.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (isNumeric(tilB1.getEditText().getText().toString())) {
                tilB1.requestFocus();
                tilB1.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (isNumeric(tilB2.getEditText().getText().toString())) {
                tilB2.requestFocus();
                tilB2.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (isNumeric(tilB3.getEditText().getText().toString())) {
                tilB3.requestFocus();
                tilB3.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (isNumeric(tilC1.getEditText().getText().toString())) {
                tilC1.requestFocus();
                tilC1.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (isNumeric(tilC2.getEditText().getText().toString())) {
                tilC2.requestFocus();
                tilC2.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAire.getChildCount();
            }
            if (isNumeric(tilC3.getEditText().getText().toString())) {
                tilC3.requestFocus();
                tilC3.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAire.getChildCount();
            }
        }
        if (condicion == 0) {
            return false;
        } else {
            return true;
        }
    }


    public boolean VALIDAR_DATOS_VACIOS_PASILLOS_FRIOS_LUMINOSIDAD() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosFrios.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosFrios.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilLuminosidadC = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilRuidoC = (TextInputLayout) ly.getChildAt(5);
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
            if (tilLuminosidadC.getEditText().getText().toString().equals("")) {
                tilLuminosidadC.requestFocus();
                tilLuminosidadC.getEditText().setError("Este campo no puede estar vacío");
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
            if (tilRuidoC.getEditText().getText().toString().equals("")) {
                tilRuidoC.requestFocus();
                tilRuidoC.getEditText().setError("Este campo no puede estar vacío");
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

    public boolean VALIDAR_DATOS_CORRECTOS_PASILLOS_FRIOS_LUMINOSIDAD() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosFrios.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosFrios.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilLuminosidadC = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilRuidoC = (TextInputLayout) ly.getChildAt(5);
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
            if (tilLuminosidadC.getEditText().getText().toString().equals("0")) {
                tilLuminosidadC.requestFocus();
                tilLuminosidadC.getEditText().setError("El valor ingresado no puede ser 0");
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
            if (tilRuidoC.getEditText().getText().toString().equals("0")) {
                tilRuidoC.requestFocus();
                tilRuidoC.getEditText().setError("El valor ingresado no puede ser 0");
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


    public boolean VALIDAR_ES_NUMERO_PASILLOS_FRIOS_LUMINOSIDAD() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosFrios.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosFrios.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilLuminosidadC = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilRuidoC = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly.getChildAt(6);
            if (isNumeric(tilLuminosidadA.getEditText().getText().toString())) {
                tilLuminosidadA.requestFocus();
                tilLuminosidadA.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (isNumeric(tilLuminosidadB.getEditText().getText().toString())) {
                tilLuminosidadB.requestFocus();
                tilLuminosidadB.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (isNumeric(tilLuminosidadC.getEditText().getText().toString())) {
                tilLuminosidadC.requestFocus();
                tilLuminosidadC.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (isNumeric(tilRuidoA.getEditText().getText().toString())) {
                tilRuidoA.requestFocus();
                tilRuidoA.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (isNumeric(tilRuidoB.getEditText().getText().toString())) {
                tilRuidoB.requestFocus();
                tilRuidoB.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosFrios.getChildCount();
            }
            if (isNumeric(tilRuidoC.getEditText().getText().toString())) {
                tilRuidoC.requestFocus();
                tilRuidoC.getEditText().setError("La cantidad ingresada no es correcta");
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

    public boolean VALIDAR_DATOS_VACIOS_PASILLOS_CALIENTES_LUMINOSIDAD() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosCalientes.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosCalientes.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilLuminosidadC = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilRuidoC = (TextInputLayout) ly.getChildAt(5);
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
            if (tilLuminosidadC.getEditText().getText().toString().equals("")) {
                tilLuminosidadC.requestFocus();
                tilLuminosidadC.getEditText().setError("Este campo no puede estar vacío");
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
            if (tilRuidoC.getEditText().getText().toString().equals("")) {
                tilRuidoC.requestFocus();
                tilRuidoC.getEditText().setError("Este campo no puede estar vacío");
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

    public boolean VALIDAR_DATOS_CORRECTOS_PASILLOS_CALIENTES_LUMINOSIDAD() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosCalientes.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosCalientes.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilLuminosidadC = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilRuidoC = (TextInputLayout) ly.getChildAt(5);
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
            if (tilLuminosidadC.getEditText().getText().toString().equals("0")) {
                tilLuminosidadC.requestFocus();
                tilLuminosidadC.getEditText().setError("El valor ingresado no puede ser 0");
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
            if (tilRuidoC.getEditText().getText().toString().equals("0")) {
                tilRuidoC.requestFocus();
                tilRuidoC.getEditText().setError("El valor ingresado no puede ser 0");
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

    public boolean VALIDAR_ES_NUMERO_PASILLOS_CALIENTES_LUMINOSIDAD() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lPasillosCalientes.getChildCount(); i++) {
            ly = (LinearLayout) lPasillosCalientes.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilLuminosidadC = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly.getChildAt(4);
            final TextInputLayout tilRuidoC = (TextInputLayout) ly.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly.getChildAt(6);
            if (isNumeric(tilLuminosidadA.getEditText().getText().toString())) {
                tilLuminosidadA.requestFocus();
                tilLuminosidadA.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (isNumeric(tilLuminosidadB.getEditText().getText().toString())) {
                tilLuminosidadB.requestFocus();
                tilLuminosidadB.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (isNumeric(tilLuminosidadC.getEditText().getText().toString())) {
                tilLuminosidadC.requestFocus();
                tilLuminosidadC.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (isNumeric(tilRuidoA.getEditText().getText().toString())) {
                tilRuidoA.requestFocus();
                tilRuidoA.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (isNumeric(tilRuidoB.getEditText().getText().toString())) {
                tilRuidoB.requestFocus();
                tilRuidoB.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lPasillosCalientes.getChildCount();
            }
            if (isNumeric(tilRuidoC.getEditText().getText().toString())) {
                tilRuidoC.requestFocus();
                tilRuidoC.getEditText().setError("La cantidad ingresada no es correcta");
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

    public boolean VALIDAR_DATOS_VACIOS_AREAS_LUMINOSIDAD() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly = (LinearLayout) lAreas.getChildAt(i);
            final TextInputLayout tilLuminosidad = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilRuido = (TextInputLayout) ly.getChildAt(2);
            if (tilLuminosidad.getEditText().getText().toString().equals("")) {
                tilLuminosidad.requestFocus();
                tilLuminosidad.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lAreas.getChildCount();
            }
            if (tilRuido.getEditText().getText().toString().equals("")) {
                tilRuido.requestFocus();
                tilRuido.getEditText().setError("Este campo no puede estar vacío");
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

    public boolean VALIDAR_DATOS_CORRECTOS_AREAS_LUMINOSIDAD() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly = (LinearLayout) lAreas.getChildAt(i);
            final TextInputLayout tilLuminosidad = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilRuido = (TextInputLayout) ly.getChildAt(2);
            if (tilLuminosidad.getEditText().getText().toString().equals("0")) {
                tilLuminosidad.requestFocus();
                tilLuminosidad.getEditText().setError("El valor ingresado no puede ser 0");
                condicion = 0;
                i = lAreas.getChildCount();
            }
            if (tilRuido.getEditText().getText().toString().equals("0")) {
                tilRuido.requestFocus();
                tilRuido.getEditText().setError("El valor ingresado no puede ser 0");
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

    public boolean VALIDAR_ES_NUMERO_AREAS_LUMINOSIDAD() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly = (LinearLayout) lAreas.getChildAt(i);
            final TextInputLayout tilLuminosidad = (TextInputLayout) ly.getChildAt(1);
            final TextInputLayout tilRuido = (TextInputLayout) ly.getChildAt(2);
            if (isNumeric(tilLuminosidad.getEditText().getText().toString())) {
                tilLuminosidad.requestFocus();
                tilLuminosidad.getEditText().setError("La cantidad ingresada no es correcta");
                condicion = 0;
                i = lAreas.getChildCount();
            }
            if (isNumeric(tilRuido.getEditText().getText().toString())) {
                tilRuido.requestFocus();
                tilRuido.getEditText().setError("La cantidad ingresada no es correcta");
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
        Crud_A77 DATOS;
        DATOS = Crud_A77.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_A77_Pasillo = DATOS.CONSULTA_GENERAL_RONDA(Tdo_Turno, getDate());
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_A77_Pasillo.moveToFirst() == false) {
            //el cursor está vacío
            ronda = 0;
        } else {
            int P_Ronda = T_A77_Pasillo.getColumnIndex(IT_A77_Pasillo.I_A77_Pasillo.RONDA);
            for (T_A77_Pasillo.moveToFirst(); !T_A77_Pasillo.isAfterLast(); T_A77_Pasillo.moveToNext()) {
                for (int i = 0; i < ListaRonda.size(); i++) {
                    if (T_A77_Pasillo.getString(P_Ronda).equals(ListaRonda.get(i).toString())) {
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
        Tdo_A77_Pasillo = "";
        Tdo_A77_Area = "";
        Tdo_Area = "";
        Luminosidad = "";
        Luminosidad_A = "";
        Luminosidad_B = "";
        Luminosidad_C = "";
        Ruido = "";
        Ruido_A = "";
        Ruido_B = "";
        Ruido_C = "";
        Evento = "";
        Descripcion = "";
        LinearLayout ly1;
        Tdo_Aire = "";
        A1 = "";
        A2 = "";
        A3 = "";
        B1 = "";
        B2 = "";
        B3 = "";
        C1 = "";
        C2 = "";
        C3 = "";
        ListaA77Pasillo = new ArrayList<A77_Pasillo>();
        ListaA77Aire = new ArrayList<A77_Aire>();
        ListaA77Area = new ArrayList<A77_Area>();
        for (int i = 0; i < lPasillosFrios.getChildCount(); i++) {
            ly1 = (LinearLayout) lPasillosFrios.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly1.getChildAt(1);
            final TextInputLayout tilLuminosidadC = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly1.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly1.getChildAt(4);
            final TextInputLayout tilRuidoC = (TextInputLayout) ly1.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly1.getChildAt(6);
            final Switch tilEvento = (Switch) ly1.getChildAt(7);
            final TextInputLayout tilDescripcion = (TextInputLayout) ly1.getChildAt(8);
            tilLuminosidadA.getEditText().setText("");
            tilLuminosidadC.getEditText().setText("");
            tilLuminosidadB.getEditText().setText("");
            tilRuidoA.getEditText().setText("");
            tilRuidoC.getEditText().setText("");
            tilRuidoB.getEditText().setText("");
            tilEvento.setChecked(false);
            tilDescripcion.getEditText().setText("");
            tilLuminosidadA.getEditText().setError(null);
            tilLuminosidadB.getEditText().setError(null);
            tilLuminosidadC.getEditText().setError(null);
            tilRuidoA.getEditText().setError(null);
            tilRuidoC.getEditText().setError(null);
            tilRuidoB.getEditText().setError(null);
        }
        for (int i = 0; i < lPasillosCalientes.getChildCount(); i++) {
            ly1 = (LinearLayout) lPasillosCalientes.getChildAt(i);
            final TextInputLayout tilLuminosidadA = (TextInputLayout) ly1.getChildAt(1);
            final TextInputLayout tilLuminosidadC = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilLuminosidadB = (TextInputLayout) ly1.getChildAt(3);
            final TextInputLayout tilRuidoA = (TextInputLayout) ly1.getChildAt(4);
            final TextInputLayout tilRuidoC = (TextInputLayout) ly1.getChildAt(5);
            final TextInputLayout tilRuidoB = (TextInputLayout) ly1.getChildAt(6);
            final Switch tilEvento = (Switch) ly1.getChildAt(7);
            final TextInputLayout tilDescripcion = (TextInputLayout) ly1.getChildAt(8);
            tilLuminosidadA.getEditText().setText("");
            tilLuminosidadC.getEditText().setText("");
            tilLuminosidadB.getEditText().setText("");
            tilRuidoA.getEditText().setText("");
            tilRuidoC.getEditText().setText("");
            tilRuidoB.getEditText().setText("");
            tilEvento.setChecked(false);
            tilDescripcion.getEditText().setText("");
            tilLuminosidadA.getEditText().setError(null);
            tilLuminosidadB.getEditText().setError(null);
            tilLuminosidadC.getEditText().setError(null);
            tilRuidoA.getEditText().setError(null);
            tilRuidoC.getEditText().setError(null);
            tilRuidoB.getEditText().setError(null);
        }
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly1 = (LinearLayout) lAreas.getChildAt(i);
            final TextInputLayout tilLuminosidad = (TextInputLayout) ly1.getChildAt(1);
            final TextInputLayout tilRuido = (TextInputLayout) ly1.getChildAt(2);
            tilLuminosidad.getEditText().setText("");
            tilLuminosidad.getEditText().setError(null);
            tilRuido.getEditText().setText("");
            tilRuido.getEditText().setError(null);
        }
        for (int i = 0; i < lAire.getChildCount(); i++) {
            ly1 = (LinearLayout) lAire.getChildAt(i);
            final TextInputLayout tilA1 = (TextInputLayout) ly1.getChildAt(1);
            final TextInputLayout tilA2 = (TextInputLayout) ly1.getChildAt(2);
            final TextInputLayout tilA3 = (TextInputLayout) ly1.getChildAt(3);
            final TextInputLayout tilC1 = (TextInputLayout) ly1.getChildAt(4);
            final TextInputLayout tilC2 = (TextInputLayout) ly1.getChildAt(5);
            final TextInputLayout tilC3 = (TextInputLayout) ly1.getChildAt(6);
            final TextInputLayout tilB1 = (TextInputLayout) ly1.getChildAt(7);
            final TextInputLayout tilB2 = (TextInputLayout) ly1.getChildAt(8);
            final TextInputLayout tilB3 = (TextInputLayout) ly1.getChildAt(9);
            tilA1.getEditText().setText("");
            tilA1.getEditText().setError(null);
            tilA2.getEditText().setText("");
            tilA2.getEditText().setError(null);
            tilA3.getEditText().setText("");
            tilA3.getEditText().setError(null);
            tilB1.getEditText().setText("");
            tilB1.getEditText().setError(null);
            tilB2.getEditText().setText("");
            tilB2.getEditText().setError(null);
            tilB3.getEditText().setText("");
            tilB3.getEditText().setError(null);
            tilC1.getEditText().setText("");
            tilC1.getEditText().setError(null);
            tilC2.getEditText().setText("");
            tilC2.getEditText().setError(null);
            tilC3.getEditText().setText("");
            tilC3.getEditText().setError(null);
        }
    }

    public void DIBUJAR_FILAS_DE_PASILLOS_FRIOS_FLUJO_AIRE_EN_LAYOUT() {
        if (ListaPasilloFrio.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaPasilloFrio.size(); i++) {

            final LinearLayout lFilasAire = new LinearLayout(this);

            final TextInputLayout tilPasillo = new TextInputLayout(this);
            final TextInputLayout tilA1 = new TextInputLayout(this);
            final TextInputLayout tilA2 = new TextInputLayout(this);
            final TextInputLayout tilA3 = new TextInputLayout(this);
            final TextInputLayout tilC1 = new TextInputLayout(this);
            final TextInputLayout tilC2 = new TextInputLayout(this);
            final TextInputLayout tilC3 = new TextInputLayout(this);
            final TextInputLayout tilB1 = new TextInputLayout(this);
            final TextInputLayout tilB2 = new TextInputLayout(this);
            final TextInputLayout tilB3 = new TextInputLayout(this);

            final EditText etPasillo = new EditText(this);
            final EditText etA1 = new EditText(this);
            final EditText etA2 = new EditText(this);
            final EditText etA3 = new EditText(this);
            final EditText etC1 = new EditText(this);
            final EditText etC2 = new EditText(this);
            final EditText etC3 = new EditText(this);
            final EditText etB1 = new EditText(this);
            final EditText etB2 = new EditText(this);
            final EditText etB3 = new EditText(this);

            lFilasAireParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etPasilloParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etA1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etA2Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etA3Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etC1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etC2Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etC3Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etB1Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etB2Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etB3Params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasAire.setLayoutParams(lFilasAireParams);
            lFilasAire.setOrientation(LinearLayout.HORIZONTAL);
            lFilasAire.setTag(ListaPasilloFrio.get(i).getID());
            lFilasAire.setId(i);

            etPasilloParams.gravity = Gravity.CENTER;
            etPasilloParams.width = 85;
            etPasillo.setLayoutParams(etPasilloParams);
            //etPasillo.setHint("Frío");
            etPasillo.setText(ListaPasilloFrio.get(i).getNOMBRE_PASILLO());
            etPasillo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etPasillo.setTextColor(getThemeAccentColor(this));
            etPasillo.setEnabled(false);

            etA1Params.gravity = Gravity.CENTER;
            etA1Params.width = 80;
            etA1.setLayoutParams(etA1Params);
            etA1.setId(i);
            //etA1.setHint("1 cm");
            etA1.setText("");
            etA1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etA1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etA1.setTextColor(getThemeAccentColor(this));
            etA1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etA2Params.gravity = Gravity.CENTER;
            etA2Params.width = 80;
            etA2.setLayoutParams(etA2Params);
            etA2.setId(i);
            //etA2.setHint("120 cm");
            etA2.setText("");
            etA2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etA2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etA2.setTextColor(getThemeAccentColor(this));
            etA2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etA3Params.gravity = Gravity.CENTER;
            etA3Params.width = 80;
            etA3.setLayoutParams(etA3Params);
            etA3.setId(i);
            //etA3.setHint("200 cm");
            etA3.setText("");
            etA3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etA3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etA3.setTextColor(getThemeAccentColor(this));
            etA3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etC1Params.gravity = Gravity.CENTER;
            etC1Params.width = 80;
            etC1.setLayoutParams(etC1Params);
            etC1.setId(i);
            //etC1.setHint("1 cm");
            etC1.setText("");
            etC1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etC1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etC1.setTextColor(getThemeAccentColor(this));
            etC1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etC2Params.gravity = Gravity.CENTER;
            etC2Params.width = 80;
            etC2.setLayoutParams(etC2Params);
            etC2.setId(i);
            //etC2.setHint("120 cm");
            etC2.setText("");
            etC2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etC2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etC2.setTextColor(getThemeAccentColor(this));
            etC2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etC3Params.gravity = Gravity.CENTER;
            etC3Params.width = 80;
            etC3.setLayoutParams(etC3Params);
            etC3.setId(i);
            //etC3.setHint("200 cm");
            etC3.setText("");
            etC3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etC3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etC3.setTextColor(getThemeAccentColor(this));
            etC3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etB1Params.gravity = Gravity.CENTER;
            etB1Params.width = 80;
            etB1.setLayoutParams(etB1Params);
            etB1.setId(i);
            //etB1.setHint("1 cm");
            etB1.setText("");
            etB1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etB1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etB1.setTextColor(getThemeAccentColor(this));
            etB1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etB2Params.gravity = Gravity.CENTER;
            etB2Params.width = 80;
            etB2.setLayoutParams(etB2Params);
            etB2.setId(i);
            //etB2.setHint("120 cm");
            etB2.setText("");
            etB2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etB2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etB2.setTextColor(getThemeAccentColor(this));
            etB2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etB3Params.gravity = Gravity.CENTER;
            etB3Params.width = 80;
            etB3.setLayoutParams(etB3Params);
            etB3.setId(i);
            //etB3.setHint("200 cm");
            etB3.setText("");
            etB3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etB3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etB3.setTextColor(getThemeAccentColor(this));
            etB3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            tilPasillo.addView(etPasillo);
            tilA1.addView(etA1);
            tilA2.addView(etA2);
            tilA3.addView(etA3);
            tilC1.addView(etC1);
            tilC2.addView(etC2);
            tilC3.addView(etC3);
            tilB1.addView(etB1);
            tilB2.addView(etB2);
            tilB3.addView(etB3);

            lFilasAire.addView(tilPasillo);
            lFilasAire.addView(tilA1);
            lFilasAire.addView(tilA2);
            lFilasAire.addView(tilA3);
            lFilasAire.addView(tilC1);
            lFilasAire.addView(tilC2);
            lFilasAire.addView(tilC3);
            lFilasAire.addView(tilB1);
            lFilasAire.addView(tilB2);
            lFilasAire.addView(tilB3);

            lAire.addView(lFilasAire);

        }
    }

    public void DIBUJAR_FILAS_DE_PASILLOS_FRIOS_LUMINOSIDAD_EN_LAYOUT() {
        if (ListaPasilloFrio.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaPasilloFrio.size(); i++) {

            final LinearLayout lFilasPasillos = new LinearLayout(this);

            final TextInputLayout tilPasillo = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadA = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadC = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadB = new TextInputLayout(this);
            final TextInputLayout tilRuidoA = new TextInputLayout(this);
            final TextInputLayout tilRuidoC = new TextInputLayout(this);
            final TextInputLayout tilRuidoB = new TextInputLayout(this);
            final TextInputLayout tilDescripcion = new TextInputLayout(this);

            final EditText etPasillo = new EditText(this);
            final EditText etLuminosidadA = new EditText(this);
            final EditText etLuminosidadC = new EditText(this);
            final EditText etLuminosidadB = new EditText(this);
            final EditText etRuidoA = new EditText(this);
            final EditText etRuidoC = new EditText(this);
            final EditText etRuidoB = new EditText(this);
            final Switch etEvento = new Switch(this);
            final EditText etDescripcion = new EditText(this);

            lFilasPasillosParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etPasilloParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadBParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadCParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoCParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoBParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etEventoParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etDescripcionParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasPasillos.setLayoutParams(lFilasPasillosParams);
            lFilasPasillos.setOrientation(LinearLayout.HORIZONTAL);
            lFilasPasillos.setTag(ListaPasilloFrio.get(i).getID());
            lFilasPasillos.setId(i);

            etPasilloParams.gravity = Gravity.CENTER;
            etPasilloParams.width = 85;
            etPasillo.setLayoutParams(etPasilloParams);
            //etPasillo.setHint("Frío");
            etPasillo.setText(ListaPasilloFrio.get(i).getNOMBRE_PASILLO());
            etPasillo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etPasillo.setTextColor(getThemeAccentColor(this));
            etPasillo.setEnabled(false);

            etLuminosidadAParams.gravity = Gravity.CENTER;
            etLuminosidadAParams.width = 70;
            etLuminosidadA.setLayoutParams(etLuminosidadAParams);
            etLuminosidadA.setId(i);
            //etLuminosidadA.setHint("A");
            etLuminosidadA.setText("");
            etLuminosidadA.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadA.setTextColor(getThemeAccentColor(this));
            etLuminosidadA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etLuminosidadCParams.gravity = Gravity.CENTER;
            etLuminosidadCParams.width = 70;
            etLuminosidadC.setLayoutParams(etLuminosidadCParams);
            etLuminosidadC.setId(i);
            //etLuminosidadC.setHint("Centro");
            etLuminosidadC.setText("");
            etLuminosidadC.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadC.setTextColor(getThemeAccentColor(this));
            etLuminosidadC.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etLuminosidadBParams.gravity = Gravity.CENTER;
            etLuminosidadBParams.width = 70;
            etLuminosidadB.setLayoutParams(etLuminosidadBParams);
            etLuminosidadB.setId(i);
            //etLuminosidadB.setHint("B");
            etLuminosidadB.setText("");
            etLuminosidadB.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadB.setTextColor(getThemeAccentColor(this));
            etLuminosidadB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etRuidoAParams.gravity = Gravity.CENTER;
            etRuidoAParams.width = 70;
            etRuidoA.setLayoutParams(etRuidoAParams);
            etRuidoA.setId(i);
            //etRuidoA.setHint("A");
            etRuidoA.setText("");
            etRuidoA.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoA.setTextColor(getThemeAccentColor(this));
            etRuidoA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            etRuidoCParams.gravity = Gravity.CENTER;
            etRuidoCParams.width = 70;
            etRuidoC.setLayoutParams(etRuidoCParams);
            etRuidoC.setId(i);
            //etRuidoC.setHint("Centro");
            etRuidoC.setText("");
            etRuidoC.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoC.setTextColor(getThemeAccentColor(this));
            etRuidoC.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            etRuidoBParams.gravity = Gravity.CENTER;
            etRuidoBParams.width = 70;
            etRuidoB.setLayoutParams(etRuidoBParams);
            etRuidoB.setId(i);
            //etRuidoB.setHint("B");
            etRuidoB.setText("");
            etRuidoB.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoB.setTextColor(getThemeAccentColor(this));
            etRuidoB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            etEventoParams.gravity = Gravity.CENTER;
            etEventoParams.width = 80;
            etEvento.setLayoutParams(etEventoParams);
            etEvento.setId(i);
            etEvento.setInputType(InputType.TYPE_CLASS_TEXT);
            etEvento.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etEvento.setTextColor(getThemeAccentColor(this));

            etDescripcionParams.gravity = Gravity.CENTER;
            etDescripcionParams.width = 100;
            etDescripcion.setLayoutParams(etDescripcionParams);
            etDescripcion.setId(i);
            //etDescripcion.setHint("Descripción");
            etDescripcion.setText("");
            etDescripcion.setInputType(InputType.TYPE_CLASS_TEXT);
            etDescripcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etDescripcion.setTextColor(getThemeAccentColor(this));

            tilPasillo.addView(etPasillo);
            tilLuminosidadA.addView(etLuminosidadA);
            tilLuminosidadC.addView(etLuminosidadC);
            tilLuminosidadB.addView(etLuminosidadB);
            tilRuidoA.addView(etRuidoA);
            tilRuidoC.addView(etRuidoC);
            tilRuidoB.addView(etRuidoB);
            tilDescripcion.addView(etDescripcion);

            lFilasPasillos.addView(tilPasillo);
            lFilasPasillos.addView(tilLuminosidadA);
            lFilasPasillos.addView(tilLuminosidadC);
            lFilasPasillos.addView(tilLuminosidadB);
            lFilasPasillos.addView(tilRuidoA);
            lFilasPasillos.addView(tilRuidoC);
            lFilasPasillos.addView(tilRuidoB);
            lFilasPasillos.addView(etEvento);
            lFilasPasillos.addView(tilDescripcion);

            lPasillosFrios.addView(lFilasPasillos);

        }
    }

    public void DIBUJAR_FILAS_DE_PASILLOS_CALIENTES_LUMINOSIDAD_EN_LAYOUT() {
        if (ListaPasilloCaliente.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaPasilloCaliente.size(); i++) {

            final LinearLayout lFilasPasillos = new LinearLayout(this);

            final TextInputLayout tilPasillo = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadA = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadC = new TextInputLayout(this);
            final TextInputLayout tilLuminosidadB = new TextInputLayout(this);
            final TextInputLayout tilRuidoA = new TextInputLayout(this);
            final TextInputLayout tilRuidoC = new TextInputLayout(this);
            final TextInputLayout tilRuidoB = new TextInputLayout(this);
            final TextInputLayout tilDescripcion = new TextInputLayout(this);

            final EditText etPasillo = new EditText(this);
            final EditText etLuminosidadA = new EditText(this);
            final EditText etLuminosidadC = new EditText(this);
            final EditText etLuminosidadB = new EditText(this);
            final EditText etRuidoA = new EditText(this);
            final EditText etRuidoC = new EditText(this);
            final EditText etRuidoB = new EditText(this);
            final Switch etEvento = new Switch(this);
            final EditText etDescripcion = new EditText(this);

            lFilasPasillosParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etPasilloParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadBParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadCParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoAParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoCParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoBParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etEventoParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etDescripcionParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasPasillos.setLayoutParams(lFilasPasillosParams);
            lFilasPasillos.setOrientation(LinearLayout.HORIZONTAL);
            lFilasPasillos.setTag(ListaPasilloCaliente.get(i).getID());
            lFilasPasillos.setId(i);

            etPasilloParams.gravity = Gravity.CENTER;
            etPasilloParams.width = 85;
            etPasillo.setLayoutParams(etPasilloParams);
            //etPasillo.setHint("Caliente");
            etPasillo.setText(ListaPasilloCaliente.get(i).getNOMBRE_PASILLO());
            etPasillo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etPasillo.setTextColor(getThemeAccentColor(this));
            etPasillo.setEnabled(false);

            etLuminosidadAParams.gravity = Gravity.CENTER;
            etLuminosidadAParams.width = 70;
            etLuminosidadA.setLayoutParams(etLuminosidadAParams);
            etLuminosidadA.setId(i);
            //etLuminosidadA.setHint("A");
            etLuminosidadA.setText("");
            etLuminosidadA.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadA.setTextColor(getThemeAccentColor(this));
            etLuminosidadA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etLuminosidadCParams.gravity = Gravity.CENTER;
            etLuminosidadCParams.width = 70;
            etLuminosidadC.setLayoutParams(etLuminosidadCParams);
            etLuminosidadC.setId(i);
            //etLuminosidadC.setHint("Centro");
            etLuminosidadC.setText("");
            etLuminosidadC.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadC.setTextColor(getThemeAccentColor(this));
            etLuminosidadC.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etLuminosidadBParams.gravity = Gravity.CENTER;
            etLuminosidadBParams.width = 70;
            etLuminosidadB.setLayoutParams(etLuminosidadBParams);
            etLuminosidadB.setId(i);
            //etLuminosidadB.setHint("B");
            etLuminosidadB.setText("");
            etLuminosidadB.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidadB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidadB.setTextColor(getThemeAccentColor(this));
            etLuminosidadB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etRuidoAParams.gravity = Gravity.CENTER;
            etRuidoAParams.width = 70;
            etRuidoA.setLayoutParams(etRuidoAParams);
            etRuidoA.setId(i);
            //etRuidoA.setHint("A");
            etRuidoA.setText("");
            etRuidoA.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoA.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoA.setTextColor(getThemeAccentColor(this));
            etRuidoA.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            etRuidoCParams.gravity = Gravity.CENTER;
            etRuidoCParams.width = 70;
            etRuidoC.setLayoutParams(etRuidoCParams);
            etRuidoC.setId(i);
            //etRuidoC.setHint("Centro");
            etRuidoC.setText("");
            etRuidoC.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoC.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoC.setTextColor(getThemeAccentColor(this));
            etRuidoC.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            etRuidoBParams.gravity = Gravity.CENTER;
            etRuidoBParams.width = 70;
            etRuidoB.setLayoutParams(etRuidoBParams);
            etRuidoB.setId(i);
            //etRuidoB.setHint("B");
            etRuidoB.setText("");
            etRuidoB.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuidoB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuidoB.setTextColor(getThemeAccentColor(this));
            etRuidoB.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            etEventoParams.gravity = Gravity.CENTER;
            etEventoParams.width = 80;
            etEvento.setLayoutParams(etEventoParams);
            etEvento.setId(i);
            etEvento.setInputType(InputType.TYPE_CLASS_TEXT);
            etEvento.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etEvento.setTextColor(getThemeAccentColor(this));

            etDescripcionParams.gravity = Gravity.CENTER;
            etDescripcionParams.width = 100;
            etDescripcion.setLayoutParams(etDescripcionParams);
            etDescripcion.setId(i);
            //etDescripcion.setHint("Descripción");
            etDescripcion.setText("");
            etDescripcion.setInputType(InputType.TYPE_CLASS_TEXT);
            etDescripcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etDescripcion.setTextColor(getThemeAccentColor(this));

            tilPasillo.addView(etPasillo);
            tilLuminosidadA.addView(etLuminosidadA);
            tilLuminosidadC.addView(etLuminosidadC);
            tilLuminosidadB.addView(etLuminosidadB);
            tilRuidoA.addView(etRuidoA);
            tilRuidoC.addView(etRuidoC);
            tilRuidoB.addView(etRuidoB);
            tilDescripcion.addView(etDescripcion);

            lFilasPasillos.addView(tilPasillo);
            lFilasPasillos.addView(tilLuminosidadA);
            lFilasPasillos.addView(tilLuminosidadC);
            lFilasPasillos.addView(tilLuminosidadB);
            lFilasPasillos.addView(tilRuidoA);
            lFilasPasillos.addView(tilRuidoC);
            lFilasPasillos.addView(tilRuidoB);
            lFilasPasillos.addView(etEvento);
            lFilasPasillos.addView(tilDescripcion);

            lPasillosCalientes.addView(lFilasPasillos);

        }
    }

    public void DIBUJAR_FILAS_DE_AREAS_LUMINOSIDAD_EN_LAYOUT() {
        if (ListaArea.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaArea.size(); i++) {

            final LinearLayout lFilasAreas = new LinearLayout(this);

            final TextInputLayout tilArea = new TextInputLayout(this);
            final TextInputLayout tilLuminosidad = new TextInputLayout(this);
            final TextInputLayout tilRuido = new TextInputLayout(this);

            final EditText etArea = new EditText(this);
            final EditText etLuminosidad = new EditText(this);
            final EditText etRuido = new EditText(this);

            lFilasAreasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etLuminosidadParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etRuidoParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

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

            etLuminosidadParams.gravity = Gravity.CENTER;
            etLuminosidadParams.width = 200;
            etLuminosidad.setLayoutParams(etLuminosidadParams);
            etLuminosidad.setId(i);
            //etLuminosidad.setHint("Luminosidad");
            etLuminosidad.setText("");
            etLuminosidad.setInputType(InputType.TYPE_CLASS_NUMBER);
            etLuminosidad.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etLuminosidad.setTextColor(getThemeAccentColor(this));
            etLuminosidad.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

            etRuidoParams.gravity = Gravity.CENTER;
            etRuidoParams.width = 200;
            etRuido.setLayoutParams(etRuidoParams);
            etRuido.setId(i);
            //etRuido.setHint("Ruido");
            etRuido.setText("");
            etRuido.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etRuido.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRuido.setTextColor(getThemeAccentColor(this));
            etRuido.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

            tilArea.addView(etArea);
            tilLuminosidad.addView(etLuminosidad);
            tilRuido.addView(etRuido);

            lFilasAreas.addView(tilArea);
            lFilasAreas.addView(tilLuminosidad);
            lFilasAreas.addView(tilRuido);

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
            T_Area = DATOS.CONSULTA_GENERAL_AREA();
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
