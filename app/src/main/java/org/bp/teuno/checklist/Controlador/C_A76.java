package org.bp.teuno.checklist.Controlador;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.bp.teuno.checklist.Modelo.A76;
import org.bp.teuno.checklist.Modelo.Cantidad_Ur;
import org.bp.teuno.checklist.Modelo.Cliente;
import org.bp.teuno.checklist.Modelo.Fila;
import org.bp.teuno.checklist.Modelo.Rack;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_A76;
import org.bp.teuno.checklist.SQLite.IT_Cantidad_Ur;
import org.bp.teuno.checklist.SQLite.IT_Cliente;
import org.bp.teuno.checklist.SQLite.IT_Fila;
import org.bp.teuno.checklist.SQLite.IT_Rack;
import org.bp.teuno.checklist.SQLite.IT_Turno;
import org.bp.teuno.checklist.UI.Crud_A76;
import org.bp.teuno.checklist.UI.Crud_Cantidad_Ur;
import org.bp.teuno.checklist.UI.Crud_Cliente;
import org.bp.teuno.checklist.UI.Crud_Fila;
import org.bp.teuno.checklist.UI.Crud_Inicio_Sesion;
import org.bp.teuno.checklist.UI.Crud_Rack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class C_A76 extends AppCompatActivity {

    private final long interval = 1 * 1000;
    String Tdo_A76 = "", Ur_Ocupada = "", Ur_Disponible = "", Hora_Ingreso = "", Hora_Salida = "", Turno = "", Usuario_Ingresa = "", Fecha_Ingeso = "";
    int Ronda = 0;
    String Tdo_Rack = "", Nombre_Rack = "";  //PARA EL USO DE Racks
    String Tdo_Fila = "", Id_Fila = "", Nombre_Fila = "", Tdo_Fila_Temporal = "", Id_Fila_Temporal = "", Nombre_Fila_Temporal = ""; // PARA CARGAR LA LISTA DE Filas
    String Tdo_Ur = "", Id_Ur = "", Capacidad_Ur = "";
    String Tdo_Cliente = "", Id_Cliente = "", Nombre_Cliente = "";
    LinearLayout lRacks;
    TextView txMensaje;
    LayoutParams lFilasRacksParams, btnMensajeParams, etRackParams, etClienteParams, etCapacidadURParams, etUrOcupadasParams, etUrDisponiblesParams;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabGuardar;
    MaterialBetterSpinner msFila;
    ArrayList<A76> ListaA76 = new ArrayList<A76>();
    ArrayList<Rack> ListaRack = new ArrayList<Rack>();
    ArrayList<Fila> ListaFila = new ArrayList<Fila>();
    ArrayList<String> ListaRonda = new ArrayList<String>();
    Cursor T_Fila, T_Rack, T_UR, T_A76, T_Cliente;
    Crud_A76 Datos;
    String Id_Operador = "", Nombre_Operador, Tdo_Turno, Turno_Operador, isMenuA76;
    int bandera = 0, position_Temporal = 0;
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME
    Crud_Inicio_Sesion DATOS;
    Cursor T_Turno;
    String Tdo_Turno_1;

    public static int getThemeAccentColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_a76);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();

        lRacks = (LinearLayout) findViewById(R.id.lRacks);
        txMensaje = (TextView) findViewById(R.id.txMensaje);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);

        msFila = (MaterialBetterSpinner) findViewById(R.id.msFila);

        msFila.setText("");
        msFila.setHint("* Fila");
        msFila.setFocusableInTouchMode(false);

        Hora_Ingreso = getTime();

        TooltipCompat.setTooltipText(fabGuardar, "Guardar");

        txMensaje.setText("Seleccione una fila para continuar");

        CARGAR_FILAS();
        CARGAR_LISTA_RONDAS();

        msFila.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                if (ListaFila.size() > 0) {
                    if (bandera == 0) {
                        Tdo_Fila = ListaFila.get(position).getID();
                        Id_Fila = ListaFila.get(position).getID_FILA();
                        Nombre_Fila = ListaFila.get(position).getNOMBRE_FILA();
                        Tdo_Fila_Temporal = Tdo_Fila;
                        Id_Fila_Temporal = Id_Fila;
                        Nombre_Fila_Temporal = Nombre_Fila;
                        ELIMINAR_FILAS_EN_LAYOUT();
                        CARGAR_RACKS(Tdo_Fila);
                        DIBUJAR_FILAS_EN_LAYOUT();
                        if (ListaRack.size() == 0) {
                            txMensaje.setText("No existen racks activos para la fila seleccionada");
                        } else if (ListaRack.size() == 1) {
                            txMensaje.setText("Se encontró " + String.valueOf(ListaRack.size()) + " rack activo en la fila seleccionada");
                            CARGAR_DATOS();
                        } else {
                            txMensaje.setText("Se encontraron " + String.valueOf(ListaRack.size()) + " racks activos en la fila seleccionada");
                            CARGAR_DATOS();
                        }
                    }
                    if (bandera == 1 && EXISTEN_DATOS_INGRESADOS_DE_SEMANA_ACTUAL(Tdo_Fila_Temporal) == false) {
                        Tdo_Fila = ListaFila.get(position).getID();
                        Id_Fila = ListaFila.get(position).getID_FILA();
                        Nombre_Fila = ListaFila.get(position).getNOMBRE_FILA();
                        final AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A76.this);
                        myBulid.setTitle("Selección de filas")
                                .setIcon(R.drawable.fila1)
                                .setMessage("No ha guardado los valores de esta semana para la fila seleccionada, ¿Desea cambiar de fila?")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Tdo_Fila_Temporal = Tdo_Fila;
                                        Id_Fila_Temporal = Id_Fila;
                                        Nombre_Fila_Temporal = Nombre_Fila;
                                        ELIMINAR_FILAS_EN_LAYOUT();
                                        CARGAR_RACKS(Tdo_Fila);
                                        DIBUJAR_FILAS_EN_LAYOUT();
                                        if (ListaRack.size() == 0) {
                                            txMensaje.setText("No existen racks activos para la fila seleccionada");
                                        } else if (ListaRack.size() == 1) {
                                            txMensaje.setText("Se encontró " + String.valueOf(ListaRack.size()) + " rack activo en la fila seleccionada");
                                            CARGAR_DATOS();
                                        } else {
                                            txMensaje.setText("Se encontraron " + String.valueOf(ListaRack.size()) + " racks activos en la fila seleccionada");
                                            CARGAR_DATOS();
                                        }
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Tdo_Fila = Tdo_Fila_Temporal;
                                        Id_Fila = Id_Fila_Temporal;
                                        Nombre_Fila = Nombre_Fila_Temporal;
                                        msFila.setText("");
                                        ELIMINAR_FILAS_EN_LAYOUT();
                                        CARGAR_RACKS(Tdo_Fila);
                                        DIBUJAR_FILAS_EN_LAYOUT();
                                        if (ListaRack.size() == 0) {
                                            txMensaje.setText("No existen racks activos para la fila seleccionada");
                                        } else if (ListaRack.size() == 1) {
                                            txMensaje.setText("Se encontró " + String.valueOf(ListaRack.size()) + " rack activo en la fila seleccionada");
                                            CARGAR_DATOS();
                                        } else {
                                            txMensaje.setText("Se encontraron " + String.valueOf(ListaRack.size()) + " racks activos en la fila seleccionada");
                                            CARGAR_DATOS();
                                        }
                                        dialog.cancel();
                                        return;
                                    }
                                });
                        myBulid.show();
                    }
                    if (bandera == 1 && EXISTEN_DATOS_INGRESADOS_DE_SEMANA_ACTUAL(Tdo_Fila_Temporal) == true) {
                        Tdo_Fila = ListaFila.get(position).getID();
                        Id_Fila = ListaFila.get(position).getID_FILA();
                        Nombre_Fila = ListaFila.get(position).getNOMBRE_FILA();
                        Tdo_Fila_Temporal = Tdo_Fila;
                        Id_Fila_Temporal = Id_Fila;
                        Nombre_Fila_Temporal = Nombre_Fila;
                        ELIMINAR_FILAS_EN_LAYOUT();
                        CARGAR_RACKS(Tdo_Fila);
                        DIBUJAR_FILAS_EN_LAYOUT();
                        if (ListaRack.size() == 0) {
                            txMensaje.setText("No existen racks activos para la fila seleccionada");
                        } else if (ListaRack.size() == 1) {
                            txMensaje.setText("Se encontró " + String.valueOf(ListaRack.size()) + " rack activo en la fila seleccionada");
                            CARGAR_DATOS();
                        } else {
                            txMensaje.setText("Se encontraron " + String.valueOf(ListaRack.size()) + " racks activos en la fila seleccionada");
                            CARGAR_DATOS();
                        }
                        return;
                    }
                    bandera = 1;
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
                    if (lRacks.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen racks activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS() == false) {
                        return;
                    }
                    if (VALIDAR_DATOS_CORRECTOS() == false) {
                        return;
                    }
                    if (EXISTEN_DATOS_INGRESADOS_DE_SEMANA_ACTUAL(Tdo_Fila) == true) {
                        AlertDialog.Builder myBulid = new AlertDialog.Builder(C_A76.this);
                        myBulid.setTitle("Guardar Registro")
                                .setIcon(R.drawable.alerta1)
                                .setMessage("Ya existen datos registrados en esta semana para la fila seleccionada, ¿Desea guardar el registro?")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Ronda = CONSULTAR_RONDA() + 1;
                                        LinearLayout ly;
                                        ListaA76 = new ArrayList<A76>();
                                        Hora_Salida = getTime();
                                        for (int i = 0; i < lRacks.getChildCount(); i++) {
                                            ly = (LinearLayout) lRacks.getChildAt(i);
                                            Tdo_Rack = ly.getTag().toString();
                                            final TextInputLayout tilCapacidadUR = (TextInputLayout) ly.getChildAt(2);
                                            final TextInputLayout tilUROcupadas = (TextInputLayout) ly.getChildAt(3);
                                            final TextInputLayout tilURDisponibles = (TextInputLayout) ly.getChildAt(4);
                                            Tdo_Ur = tilCapacidadUR.getEditText().getTag().toString();
                                            Ur_Ocupada = tilUROcupadas.getEditText().getText().toString().equals("") ? "0" : tilUROcupadas.getEditText().getText().toString();
                                            Ur_Disponible = tilURDisponibles.getEditText().getText().toString().equals("") ? "0" : tilURDisponibles.getEditText().getText().toString();
                                            A76 a76 = new A76("", Tdo_Fila, Tdo_Rack, Tdo_Ur, Ur_Ocupada, Ur_Disponible, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                                            ListaA76.add(a76);
                                        }
                                        if (EXISTEN_DATOS_INGRESADOS_DE_SEMANA_ANTERIOR() == true) {
                                            PROCESAR_DATOS_SEMANA_ANTERIOR();
                                        } else {
                                            PROCESAR_DATOS_SEMANA_ACTUAL();
                                        }
                                        Toast.makeText(getApplicationContext(), "El registro se guardó correctamente", Toast.LENGTH_LONG).show();
                                        LIMPIAR_DATOS();
                                        bandera = 0;
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        return;
                                    }
                                });
                        myBulid.show();
                    }
                    if (EXISTEN_DATOS_INGRESADOS_DE_SEMANA_ACTUAL(Tdo_Fila) == false) {
                        Ronda = CONSULTAR_RONDA() + 1;
                        LinearLayout ly;
                        ListaA76 = new ArrayList<A76>();
                        Hora_Salida = getTime();
                        for (int i = 0; i < lRacks.getChildCount(); i++) {
                            ly = (LinearLayout) lRacks.getChildAt(i);
                            Tdo_Rack = ly.getTag().toString();
                            final TextInputLayout tilCapacidadUR = (TextInputLayout) ly.getChildAt(2);
                            final TextInputLayout tilUROcupadas = (TextInputLayout) ly.getChildAt(3);
                            final TextInputLayout tilURDisponibles = (TextInputLayout) ly.getChildAt(4);
                            Tdo_Ur = tilCapacidadUR.getEditText().getTag().toString();
                            Ur_Ocupada = tilUROcupadas.getEditText().getText().toString().equals("") ? "0" : tilUROcupadas.getEditText().getText().toString();
                            Ur_Disponible = tilURDisponibles.getEditText().getText().toString().equals("") ? "0" : tilURDisponibles.getEditText().getText().toString();
                            A76 a76 = new A76("", Tdo_Fila, Tdo_Rack, Tdo_Ur, Ur_Ocupada, Ur_Disponible, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                            ListaA76.add(a76);
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(C_A76.this);
                        builder.setTitle("Guardar Registro")
                                .setMessage("¿Desea guardar el registro?")
                                .setIcon(R.drawable.alerta1)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (EXISTEN_DATOS_INGRESADOS_DE_SEMANA_ANTERIOR() == true) {
                                            PROCESAR_DATOS_SEMANA_ANTERIOR();
                                        } else {
                                            PROCESAR_DATOS_SEMANA_ACTUAL();
                                        }
                                        Toast.makeText(getApplicationContext(), "El registro se guardó correctamente", Toast.LENGTH_LONG).show();
                                        LIMPIAR_DATOS();
                                        bandera = 0;
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
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (EXISTEN_FILAS_PENDIENTES_DE_REGISTRAR() == true) {
            Toast.makeText(getApplicationContext(), "Existen todavía filas pendientes de registrar esta semana", Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder myBulid = new AlertDialog.Builder(this);
        myBulid.setTitle("Regresar");
        myBulid.setIcon(R.drawable.salir);
        myBulid.setMessage("¿Desea regresar a la pantalla del menú principal?");
        myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(C_A76.this, C_Menu_Principal.class);
                intent.putExtra("Id_Operador", Id_Operador);
                intent.putExtra("Nombre_Operador", Nombre_Operador);
                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                intent.putExtra("Turno_Operador", Turno_Operador);
                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
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

    public boolean VALIDAR_DATOS_VACIOS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lRacks.getChildCount(); i++) {
            ly = (LinearLayout) lRacks.getChildAt(i);
            final TextInputLayout tilUROcupadas = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilURDisponibles = (TextInputLayout) ly.getChildAt(4);
            if (tilUROcupadas.getEditText().getText().toString().equals("")) {
                tilUROcupadas.requestFocus();
                tilUROcupadas.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lRacks.getChildCount();
            }
            if (tilURDisponibles.getEditText().getText().toString().equals("")) {
                tilURDisponibles.requestFocus();
                tilURDisponibles.getEditText().setError("Este campo no puede estar vacío");
                condicion = 0;
                i = lRacks.getChildCount();
            }
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
        for (int i = 0; i < lRacks.getChildCount(); i++) {
            ly = (LinearLayout) lRacks.getChildAt(i);
            final TextInputLayout tilCapacidadUR = (TextInputLayout) ly.getChildAt(2);
            final TextInputLayout tilUROcupadas = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilURDisponibles = (TextInputLayout) ly.getChildAt(4);
            int Suma = 0;
            Suma = Integer.valueOf(tilUROcupadas.getEditText().getText().toString()) + Integer.valueOf(tilURDisponibles.getEditText().getText().toString());
            if ((Suma != Integer.valueOf(tilCapacidadUR.getEditText().getText().toString()))) {
                tilCapacidadUR.requestFocus();
                tilCapacidadUR.getEditText().setError("Revise la suma de las Ur ocupadas y disponibles");
                condicion = 0;
                i = lRacks.getChildCount();
            } else {
                tilCapacidadUR.getEditText().setError(null);
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
        Crud_A76 DATOS;
        DATOS = Crud_A76.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_A76 = DATOS.CONSULTA_GENERAL_RONDA(Tdo_Turno, getDate());
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_A76.moveToFirst() == false) {
            //el cursor está vacío
            ronda = 0;
        } else {
            int P_Ronda = T_A76.getColumnIndex(IT_A76.I_A76.RONDA);
            for (T_A76.moveToFirst(); !T_A76.isAfterLast(); T_A76.moveToNext()) {
                for (int i = 0; i < ListaRonda.size(); i++) {
                    if (T_A76.getString(P_Ronda).equals(ListaRonda.get(i).toString())) {
                        ronda = Integer.valueOf(ListaRonda.get(i).toString());
                    }
                }
            }
        }
        return ronda;
    }

    public void CARGAR_DATOS() {
        Crud_A76 DATOS;
        DATOS = Crud_A76.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_A76 = DATOS.CONSULTA_GENERAL_A76_POR_FILA(Tdo_Fila);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_A76.moveToFirst() == false) {
            //el cursor está vacío
            ListaA76 = new ArrayList<A76>();
            return;
        }
        String tdo_a76 = "", tdo_fila = "", tdo_rack = "", ur_disponible = "", ur_ocupada = "", usuario_ingresa = "", fecha_ingreso = "";
        int P_Tdo = T_A76.getColumnIndex(IT_A76.I_A76.ID);
        int P_Id_Fila = T_A76.getColumnIndex(IT_A76.I_A76.ID_FILA);
        int P_Id_Rack = T_A76.getColumnIndex(IT_A76.I_A76.ID_RACK);
        int P_Ur_Disponibles = T_A76.getColumnIndex(IT_A76.I_A76.UR_DISPONIBLES);
        int P_Ur_Ocupadas = T_A76.getColumnIndex(IT_A76.I_A76.UR_OCUPADAS);
        int P_Usuario_Ingresa = T_A76.getColumnIndex(IT_A76.I_A76.USUARIO_INGRESA);
        int P_Fecha_Ingeso = T_A76.getColumnIndex(IT_A76.I_A76.FECHA_INGRESO);
        for (T_A76.moveToFirst(); !T_A76.isAfterLast(); T_A76.moveToNext()) {
            tdo_a76 = T_A76.getString(P_Tdo);
            tdo_fila = T_A76.getString(P_Id_Fila);
            tdo_rack = T_A76.getString(P_Id_Rack);
            ur_disponible = T_A76.getString(P_Ur_Disponibles);
            ur_ocupada = T_A76.getString(P_Ur_Ocupadas);
            usuario_ingresa = T_A76.getString(P_Usuario_Ingresa);
            fecha_ingreso = T_A76.getString(P_Fecha_Ingeso);
            A76 a76 = new A76(tdo_a76, tdo_fila, tdo_rack, ur_ocupada, ur_disponible, usuario_ingresa, fecha_ingreso);
            ListaA76.add(a76);
        }
        LinearLayout ly;
        for (int i = 0; i < lRacks.getChildCount(); i++) {
            ly = (LinearLayout) lRacks.getChildAt(i);
            final TextInputLayout tilRack = (TextInputLayout) ly.getChildAt(0);
            final TextInputLayout tilUROcupadas = (TextInputLayout) ly.getChildAt(3);
            final TextInputLayout tilURDisponibles = (TextInputLayout) ly.getChildAt(4);
            for (int j = 0; j < ListaA76.size(); j++) {
                if (tilRack.getEditText().getTag().equals(ListaA76.get(j).getID_RACK())) {
                    tilURDisponibles.getEditText().setText(ListaA76.get(j).getUR_DISPONIBLES());
                    tilUROcupadas.getEditText().setText(ListaA76.get(j).getUR_OCUPADAS());
                    j = ListaA76.size();
                }
            }
        }
    }

    public boolean EXISTEN_FILAS_PENDIENTES_DE_REGISTRAR() {
        Datos = Crud_A76.OBTENER_INSTANCIA(getApplicationContext());
        Cursor T_A76;
        try {
            Datos.GETBD().beginTransaction();
            T_A76 = Datos.EXISTEN_FILAS_PENDIENTES_DE_REGISTRAR(getDate());
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_A76.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public boolean EXISTEN_DATOS_INGRESADOS_DE_SEMANA_ANTERIOR() {
        Datos = Crud_A76.OBTENER_INSTANCIA(getApplicationContext());
        Cursor T_A76;
        try {
            Datos.GETBD().beginTransaction();
            T_A76 = Datos.EXISTEN_DATOS_SEMANA_ANTERIOR(Tdo_Fila, getDate());
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_A76.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public boolean EXISTEN_DATOS_INGRESADOS_DE_SEMANA_ACTUAL(String Tdo_Fila) {
        Datos = Crud_A76.OBTENER_INSTANCIA(getApplicationContext());
        Cursor T_A76;
        try {
            Datos.GETBD().beginTransaction();
            T_A76 = Datos.EXISTEN_DATOS_SEMANA_ACTUAL(Tdo_Fila, getDate());
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_A76.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void PROCESAR_DATOS_SEMANA_ANTERIOR() {
        Datos = Crud_A76.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            long updatedResult = Datos.DESACTIVAR_DATOS_SEMANA_ANTERIOR(Tdo_Fila, getDate());
            if (updatedResult == -1) {
                Toast.makeText(getApplicationContext(), String.format("Se generó un error al desactivar el registro en la base de datos"), Toast.LENGTH_LONG).show();
                throw new SQLException(String.format("Se generó un error al desactivar el registro en la base de datos"));
            }
            if (updatedResult != -1) {
                for (int i = 0; i < ListaA76.size(); i++) {
                    long insertedResult = Datos.INSERTAR_A76(ListaA76.get(i));
                    if (insertedResult == -1) {
                        Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                        throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                    }
                }
            }
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
    }

    public void PROCESAR_DATOS_SEMANA_ACTUAL() {
        long deletedResult = 0;
        Datos = Crud_A76.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            if (EXISTEN_DATOS_INGRESADOS_DE_SEMANA_ACTUAL(Tdo_Fila) == true) {
                deletedResult = Datos.ELIMINAR_DATOS_INGRESADOS(Tdo_Fila, getDate());
                if (deletedResult == -1) {
                    throw new SQLException(String.format("Se generó un error al eliminar el registro en la base de datos"));
                }
            }
            if (deletedResult != -1) {
                for (int i = 0; i < ListaA76.size(); i++) {
                    long insertedResult = Datos.INSERTAR_A76(ListaA76.get(i));
                    if (insertedResult == -1) {
                        throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                    }
                }
            }
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
    }

    public void LIMPIAR_DATOS() {
        Tdo_Fila = "";
        Tdo_Rack = "";
        Tdo_Ur = "";
        Capacidad_Ur = "";
        Ur_Ocupada = "";
        Ur_Disponible = "";
        ListaA76 = new ArrayList<A76>();
        ELIMINAR_FILAS_EN_LAYOUT();
        msFila.setText("");
        //msFila.setText("Fila");
        txMensaje.setText("Seleccione una fila para continuar");

    }

    public void ELIMINAR_FILAS_EN_LAYOUT() {

        for (int i = 0; i < lRacks.getChildCount(); i++) {
            lRacks.removeAllViews();
        }
        ListaRack = new ArrayList<Rack>();
    }

    public void DIBUJAR_FILAS_EN_LAYOUT() {

        if (ListaRack.size() == 0) {
            return;
        }

        for (int i = 0; i < ListaRack.size(); i++) {

            final LinearLayout lFilasRacks = new LinearLayout(this);

            final Button btnMensaje = new Button(this);

            final TextInputLayout tilRack = new TextInputLayout(this);
            final TextInputLayout tilCliente = new TextInputLayout(this);
            final TextInputLayout tilCapacidadUR = new TextInputLayout(this);
            final TextInputLayout tilUROcupadas = new TextInputLayout(this);
            final TextInputLayout tilURDisponibles = new TextInputLayout(this);

            final EditText etRack = new EditText(this);
            final EditText etCliente = new EditText(this);
            final EditText etCapacidadUR = new EditText(this);
            final EditText etUROcupadas = new EditText(this);
            final EditText etURDisponibles = new EditText(this);

            lFilasRacksParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etRackParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etClienteParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etCapacidadURParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etUrOcupadasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etUrDisponiblesParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasRacks.setLayoutParams(lFilasRacksParams);
            lFilasRacks.setOrientation(LinearLayout.HORIZONTAL);
            lFilasRacks.setTag(ListaRack.get(i).getID());
            lFilasRacks.setId(i);

            etRackParams.gravity = Gravity.CENTER;
            etRackParams.width = 100;
            etRack.setLayoutParams(etRackParams);
            etRack.setTag(ListaRack.get(i).getID());
            //etRack.setHint("Rack");
            etRack.setText(ListaRack.get(i).getNOMBRE_RACK());
            etRack.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etRack.setTextColor(getThemeAccentColor(this));
            etRack.setEnabled(false);

            CARGAR_UR(ListaRack.get(i).getID_UR());

            etClienteParams.gravity = Gravity.CENTER;
            etClienteParams.width = 220;
            etCliente.setLayoutParams(etClienteParams);
            String nombre = ListaRack.get(i).getID_CLIENTE();
            CARGAR_CLIENTE(nombre);
            etCliente.setText(Nombre_Cliente);
            //etCliente.setHint("Capacidad");
            etCliente.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etCliente.setTextColor(getThemeAccentColor(this));
            etCliente.setEnabled(false);

            etCapacidadURParams.gravity = Gravity.CENTER;
            etCapacidadURParams.width = 100;
            etCapacidadUR.setLayoutParams(etCapacidadURParams);
            etCapacidadUR.setId(i);
            etCapacidadUR.setTag(ListaRack.get(i).getID_UR());
            //etCapacidadUR.setHint("Capacidad");
            etCapacidadUR.setText(Capacidad_Ur);
            etCapacidadUR.setInputType(InputType.TYPE_CLASS_NUMBER);
            etCapacidadUR.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etCapacidadUR.setTextColor(getThemeAccentColor(this));
            etCapacidadUR.setEnabled(false);

            etUrOcupadasParams.gravity = Gravity.CENTER;
            etUrOcupadasParams.width = 100;
            etUROcupadas.setLayoutParams(etUrOcupadasParams);
            //etUROcupadas.setHint("Ocupadas");
            etUROcupadas.setText("");
            etUROcupadas.setInputType(InputType.TYPE_CLASS_NUMBER);
            etUROcupadas.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etUROcupadas.setTextColor(getThemeAccentColor(this));
            etUROcupadas.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

            etUrDisponiblesParams.gravity = Gravity.CENTER;
            etUrDisponiblesParams.width = 180;
            etURDisponibles.setLayoutParams(etUrDisponiblesParams);
            //etURDisponibles.setHint("Disponibles");
            etURDisponibles.setText("");
            etURDisponibles.setInputType(InputType.TYPE_CLASS_NUMBER);
            etURDisponibles.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etURDisponibles.setTextColor(getThemeAccentColor(this));
            etURDisponibles.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

            etUROcupadas.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    etCapacidadUR.setError(null);
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (etUROcupadas.getText() == null) {
                        return;
                    }
                    if (etUROcupadas.getText().toString().equals("")) {
                        int resultado = 0;
                        resultado = Integer.parseInt(etCapacidadUR.getText().toString()) - 0;
                        etURDisponibles.setText(String.valueOf(resultado));
                        return;
                    }
                    if (Integer.parseInt(etCapacidadUR.getText().toString()) >= Integer.parseInt(etUROcupadas.getText().toString())) {
                        int resultado = 0;
                        resultado = Integer.parseInt(etCapacidadUR.getText().toString()) - Integer.parseInt(etUROcupadas.getText().toString());
                        etURDisponibles.setText(String.valueOf(resultado));
                    } else {
                        etUROcupadas.setText(etCapacidadUR.getText().toString());
                        etURDisponibles.setText("0");
                        return;
                    }

                }
            });

            etURDisponibles.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    etCapacidadUR.setError(null);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            tilRack.addView(etRack);
            tilCliente.addView(etCliente);
            tilCapacidadUR.addView(etCapacidadUR);
            tilUROcupadas.addView(etUROcupadas);
            tilURDisponibles.addView(etURDisponibles);

            etUROcupadas.requestFocus();

            lFilasRacks.addView(tilRack);
            lFilasRacks.addView(tilCliente);
            lFilasRacks.addView(tilCapacidadUR);
            lFilasRacks.addView(tilUROcupadas);
            lFilasRacks.addView(tilURDisponibles);

            lRacks.addView(lFilasRacks);

        }

    }

    public void CARGAR_FILAS() {
        Crud_Fila DATOS;
        DATOS = Crud_Fila.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Fila = DATOS.CONSULTA_GENERAL_FILA();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Fila.moveToFirst() == false) {
            //el cursor está vacío
            ListaFila = new ArrayList<Fila>();
            Tdo_Fila = "";
            Id_Fila = "";
            Nombre_Fila = "";
            return;
        }
        int P_TDO = T_Fila.getColumnIndex(IT_Fila.I_FILA.ID);
        int P_Id_Fila = T_Fila.getColumnIndex(IT_Fila.I_FILA.ID_FILA);
        int P_Nombre_Fila = T_Fila.getColumnIndex(IT_Fila.I_FILA.NOMBRE_FILA);
        ListaFila = new ArrayList<Fila>();
        String Tdo_Fila = "", Id_Fila = "", Nombre_Fila = "";
        for (T_Fila.moveToFirst(); !T_Fila.isAfterLast(); T_Fila.moveToNext()) {
            Tdo_Fila = T_Fila.getString(P_TDO);
            Id_Fila = T_Fila.getString(P_Id_Fila);
            Nombre_Fila = T_Fila.getString(P_Nombre_Fila);
            Fila Fila = new Fila(Tdo_Fila, Id_Fila, Nombre_Fila);
            ListaFila.add(Fila);
        }
        Tdo_Fila = "";
        Id_Fila = "";
        Nombre_Fila = "";
        ArrayAdapter<Fila> Adapter = new ArrayAdapter<Fila>(this, android.R.layout.simple_spinner_dropdown_item, ListaFila);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msFila.setAdapter(Adapter);
    }

    public void CARGAR_RACKS(String FILA) {
        Crud_Rack DATOS;
        DATOS = Crud_Rack.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Rack = DATOS.CONSULTA_GENERAL_POR_ID_FILA(FILA);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Rack.moveToFirst() == false) {
            //el cursor está vacío
            ListaRack = new ArrayList<Rack>();
            Tdo_Rack = "";
            Nombre_Rack = "";
            return;
        }
        int P_TDO = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID);
        int P_Nombre_Rack = T_Rack.getColumnIndex(IT_Rack.I_RACK.NOMBRE_RACK);
        int P_Id_Fila = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID_FILA);
        int P_Id_UR = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID_CANTIDAD_UR);
        int P_Id_Cliente = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID_CLIENTE);
        ListaRack = new ArrayList<Rack>();
        String Tdo_Rack = "", Tdo_Ur = "", Nombre_Rack = "", Tdo_Fila = "", Tdo_Cliente = "";
        for (T_Rack.moveToFirst(); !T_Rack.isAfterLast(); T_Rack.moveToNext()) {
            Tdo_Rack = T_Rack.getString(P_TDO);
            Nombre_Rack = T_Rack.getString(P_Nombre_Rack);
            Tdo_Fila = T_Rack.getString(P_Id_Fila);
            Tdo_Cliente = T_Rack.getString(P_Id_Cliente);
            Tdo_Ur = T_Rack.getString(P_Id_UR);
            Rack Rack = new Rack(Tdo_Rack, Nombre_Rack, Tdo_Fila, Tdo_Ur, Tdo_Cliente);
            ListaRack.add(Rack);
        }
        Tdo_Rack = "";
        Nombre_Rack = "";
    }

    public void CARGAR_UR(String ID) {
        Crud_Cantidad_Ur DATOS;
        DATOS = Crud_Cantidad_Ur.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_UR = DATOS.CONSULTA_GENERAL_UR_POR_ID(ID);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_UR.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Ur = "";
            Id_Ur = "";
            Capacidad_Ur = "";
            return;
        }
        int P_TDO = T_UR.getColumnIndex(IT_Cantidad_Ur.I_UR.ID);
        int P_ID_UR = T_UR.getColumnIndex(IT_Cantidad_Ur.I_UR.ID_UR);
        int P_Cantidad_UR = T_UR.getColumnIndex(IT_Cantidad_Ur.I_UR.CANTIDAD_UR);
        for (T_UR.moveToFirst(); !T_UR.isAfterLast(); T_UR.moveToNext()) {
            Tdo_Ur = T_UR.getString(P_TDO);
            Id_Ur = T_UR.getString(P_ID_UR);
            Capacidad_Ur = T_UR.getString(P_Cantidad_UR);
            Cantidad_Ur UR = new Cantidad_Ur(Tdo_Ur, Id_Ur, Capacidad_Ur);
        }
    }

    public void CARGAR_CLIENTE(String ID) {
        Crud_Cliente DATOS;
        DATOS = Crud_Cliente.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Cliente = DATOS.CONSULTA_GENERAL_CLIENTE_POR_ID_CLIENTE(ID);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Cliente.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Cliente = "";
            Id_Cliente = "";
            Nombre_Cliente = "";
            return;
        }
        int P_TDO = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.ID);
        int P_ID_Cliente = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.ID_CLIENTE);
        int P_Nombre_Cliente = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.NOMBRE_CLIENTE);
        for (T_Cliente.moveToFirst(); !T_Cliente.isAfterLast(); T_Cliente.moveToNext()) {
            Tdo_Cliente = T_Cliente.getString(P_TDO);
            Id_Cliente = T_Cliente.getString(P_ID_Cliente);
            Nombre_Cliente = T_Cliente.getString(P_Nombre_Cliente);
            Cliente Cliente = new Cliente(Tdo_Cliente, Id_Cliente, Nombre_Cliente);
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
