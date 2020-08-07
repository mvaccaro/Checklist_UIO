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
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.bp.teuno.checklist.Modelo.A98;
import org.bp.teuno.checklist.Modelo.Area;
import org.bp.teuno.checklist.Modelo.Fila;
import org.bp.teuno.checklist.Modelo.Rack;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_A98;
import org.bp.teuno.checklist.SQLite.IT_Area;
import org.bp.teuno.checklist.SQLite.IT_Fila;
import org.bp.teuno.checklist.SQLite.IT_Rack;
import org.bp.teuno.checklist.SQLite.IT_Turno;
import org.bp.teuno.checklist.UI.Crud_A98;
import org.bp.teuno.checklist.UI.Crud_Area;
import org.bp.teuno.checklist.UI.Crud_Fila;
import org.bp.teuno.checklist.UI.Crud_Inicio_Sesion;
import org.bp.teuno.checklist.UI.Crud_Rack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class C_A98 extends AppCompatActivity {

    private final long interval = 1 * 1000;
    String Tdo_A98 = "", Inicio = "", Fin = "", Observaciones = "", Hora_Ingreso = "", Hora_Salida = "", Turno = "", Usuario_Ingresa = "", Fecha_Ingeso = "";
    int Ronda = 0;
    String Tdo_Rack = "", Nombre_Rack = "";  //PARA EL USO DE Racks
    String Tdo_Fila = "", Id_Fila = "", Nombre_Fila = "", Tdo_Fila_Temporal = "", Id_Fila_Temporal = "", Nombre_Fila_Temporal = ""; // PARA CARGAR LA LISTA DE Filas
    String Tdo_Area = "", Id_Area = "", Nombre_Area = "", Tdo_Tipo_Area = "";
    LinearLayout lAreas;
    TextView txMensaje;
    LayoutParams lFilasAreasParams, etAreaParams, etInicioParams, swInicioParams, etFinParams, swFinParams, etObservacionesParams;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabGuardar;
    MaterialBetterSpinner msFila, msRack;
    ArrayList<A98> ListaA98 = new ArrayList<A98>();
    ArrayList<Rack> ListaRack = new ArrayList<Rack>();
    ArrayList<Fila> ListaFila = new ArrayList<Fila>();
    ArrayList<Area> ListaArea = new ArrayList<Area>();
    ArrayList<String> ListaRonda = new ArrayList<String>();
    Cursor T_Fila, T_Rack, T_Area, T_A98;
    Crud_A98 Datos;
    String Id_Operador = "", Nombre_Operador, Tdo_Turno, Turno_Operador, isMenuA98;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_a98);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();

        lAreas = (LinearLayout) findViewById(R.id.lAreas);
        txMensaje = (TextView) findViewById(R.id.txMensaje);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);

        msFila = (MaterialBetterSpinner) findViewById(R.id.msFila);
        msRack = (MaterialBetterSpinner) findViewById(R.id.msRack);

        msFila.setText("");
        msFila.setHint("* Fila");
        msFila.setFocusableInTouchMode(false);

        msRack.setText("");
        msRack.setHint("* Rack");
        msRack.setFocusableInTouchMode(false);

        Hora_Ingreso = getTime();

        TooltipCompat.setTooltipText(fabGuardar, "Guardar");


        CARGAR_FILAS();
        CARGAR_AREAS();
        CARGAR_LISTA_RONDAS();
        DIBUJAR_FILAS_DE_AREAS();

        if (ListaArea.size() == 0) {
            txMensaje.setText("No existen detalles activos");
        } else if (ListaArea.size() == 1) {
            txMensaje.setText("Se encontró " + String.valueOf(ListaArea.size()) + " detalle activo");
        } else {
            txMensaje.setText("Se encontraron " + String.valueOf(ListaArea.size()) + " detalles activos");
        }

        msFila.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                if (ListaFila.size() > 0) {
                    Tdo_Fila = ListaFila.get(position).getID();
                    Id_Fila = ListaFila.get(position).getID_FILA();
                    Nombre_Fila = ListaFila.get(position).getNOMBRE_FILA();
                    msRack.setText("");
                    msRack.setHint("* Rack");
                    Tdo_Rack = "";
                    CARGAR_RACKS(Tdo_Fila);
                }
            }
        });

        msRack.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaRack.size() > 0) {
                    Tdo_Rack = ListaRack.get(position).getID();
                    Nombre_Rack = ListaRack.get(position).getNOMBRE_RACK();
                    if (EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION() == true) {
                        CARGAR_DATOS();
                    }
                    if (EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION() == false) {
                        LIMPIAR_DATOS_2();
                    }
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
                    if (lAreas.getChildCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No existen detalles activos", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (Tdo_Fila.equals("")) {
                        msFila.requestFocus();
                        msFila.setError("Seleccione una fila");
                        return;
                    }
                    if (Tdo_Rack.equals("")) {
                        msRack.requestFocus();
                        msRack.setError("Seleccione un rack");
                        return;
                    }
                    if (VALIDAR_DATOS_VACIOS_AREAS() == false) {
                        return;
                    }
                    Ronda = CONSULTAR_RONDA() + 1;
                    LinearLayout ly;
                    ListaA98 = new ArrayList<A98>();
                    Hora_Salida = getTime();
                    for (int i = 0; i < lAreas.getChildCount(); i++) {
                        ly = (LinearLayout) lAreas.getChildAt(i);
                        Tdo_Area = ly.getTag().toString();
                        if (ly.getTag().toString().equals(ListaArea.get(i).getID()) && ListaArea.get(i).getTIPO_AREA().equals("TAR-3")) {
                            final Switch swInicio = (Switch) ly.getChildAt(1);
                            final Switch swFin = (Switch) ly.getChildAt(2);
                            final TextInputLayout tilObservaciones = (TextInputLayout) ly.getChildAt(3);
                            if (swInicio.isChecked()) {
                                Inicio = "SI";
                            } else {
                                Inicio = "NO";
                            }
                            if (swFin.isChecked()) {
                                Fin = "SI";
                            } else {
                                Fin = "NO";
                            }
                            Observaciones = tilObservaciones.getEditText().getText().toString();
                        }
                        if (ly.getTag().toString().equals(ListaArea.get(i).getID()) && ListaArea.get(i).getTIPO_AREA().equals("TAR-4")) {
                            final TextInputLayout tilInicio = (TextInputLayout) ly.getChildAt(1);
                            final TextInputLayout tilFin = (TextInputLayout) ly.getChildAt(2);
                            final TextInputLayout tilObservaciones = (TextInputLayout) ly.getChildAt(3);
                            Inicio = tilInicio.getEditText().getText().toString();
                            Fin = tilFin.getEditText().getText().toString();
                            Observaciones = tilObservaciones.getEditText().getText().toString();
                        }
                        A98 a98 = null;
                        if (EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION() == false) {
                            a98 = new A98("", Tdo_Fila, Tdo_Rack, Tdo_Area, Inicio, Fin, Observaciones, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Id_Operador, "", getDateTime(), "");
                        }
                        if (EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION() == true) {
                            a98 = new A98("", Tdo_Fila, Tdo_Rack, Tdo_Area, Inicio, Fin, Observaciones, Hora_Ingreso, Hora_Salida, Tdo_Turno, String.valueOf(Ronda), "A", Usuario_Ingresa, Id_Operador, Fecha_Ingeso, getDateTime());
                        }
                        ListaA98.add(a98);
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_A98.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Datos = Crud_A98.OBTENER_INSTANCIA(getApplicationContext());
                                            if (EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION() == false) {
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    for (int i = 0; i < ListaA98.size(); i++) {
                                                        long insertedResult = Datos.INSERTAR_A98(ListaA98.get(i));
                                                        if (insertedResult == -1) {
                                                            Toast.makeText(getApplicationContext(), String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"), Toast.LENGTH_LONG).show();
                                                            throw new SQLException(String.format("Se generó un error al guardar el registro " + String.valueOf(i + 1) + " en la base de datos"));
                                                        }
                                                    }
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION() == true) {
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    long deletedResult = Datos.ELIMINAR_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION(Tdo_Fila, Tdo_Rack);
                                                    if (deletedResult == -1) {
                                                        Toast.makeText(getApplicationContext(), String.format("Se generó un error al eliminar el registro en la base de datos"), Toast.LENGTH_LONG).show();
                                                        throw new SQLException(String.format("Se generó un error al eliminar el registro en la base de datos"));
                                                    }
                                                    if (deletedResult != -1) {
                                                        for (int i = 0; i < ListaA98.size(); i++) {
                                                            long insertedResult = Datos.INSERTAR_A98(ListaA98.get(i));
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

    public boolean VALIDAR_DATOS_VACIOS_AREAS() {
        int condicion = 1;
        LinearLayout ly;
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly = (LinearLayout) lAreas.getChildAt(i);
            if (ly.getTag().toString().equals(ListaArea.get(i).getID()) && ListaArea.get(i).getTIPO_AREA().equals("TAR-4")) {
                final TextInputLayout tilInicio = (TextInputLayout) ly.getChildAt(1);
                final TextInputLayout tilFin = (TextInputLayout) ly.getChildAt(2);
                Inicio = tilInicio.getEditText().toString();
                Fin = tilFin.getEditText().getText().toString();
                if (EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION() == false) {
                    if (tilInicio.getEditText().getText().toString().equals("")) {
                        tilInicio.requestFocus();
                        tilInicio.getEditText().setError("Este campo no puede estar vacío");
                        condicion = 0;
                        i = lAreas.getChildCount();
                    }
                }
                if (EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION() == true) {
                    if (tilFin.getEditText().getText().toString().equals("")) {
                        tilFin.requestFocus();
                        tilFin.getEditText().setError("Este campo no puede estar vacío");
                        condicion = 0;
                        i = lAreas.getChildCount();
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder myBulid = new AlertDialog.Builder(this);
        myBulid.setTitle("Regresar");
        myBulid.setIcon(R.drawable.salir);
        myBulid.setMessage("¿Desea regresar a la pantalla del menú principal?");
        myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(C_A98.this, C_Menu_Principal.class);
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

    public boolean EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION() {
        Datos = Crud_A98.OBTENER_INSTANCIA(getApplicationContext());
        Cursor T_A98;
        try {
            Datos.GETBD().beginTransaction();
            T_A98 = Datos.EXISTE_DATOS_INGRESADOS_SIN_FINALIZAR_VALIDACION(Tdo_Fila, Tdo_Rack);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_A98.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public int CONSULTAR_RONDA() {
        int ronda = 0;
        Crud_A98 DATOS;
        DATOS = Crud_A98.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_A98 = DATOS.CONSULTA_GENERAL_RONDA(Tdo_Turno, getDate());
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_A98.moveToFirst() == false) {
            //el cursor está vacío
            ronda = 0;
        } else {
            int P_Ronda = T_A98.getColumnIndex(IT_A98.I_A98.RONDA);
            for (T_A98.moveToFirst(); !T_A98.isAfterLast(); T_A98.moveToNext()) {
                for (int i = 0; i < ListaRonda.size(); i++) {
                    if (T_A98.getString(P_Ronda).equals(ListaRonda.get(i).toString())) {
                        ronda = Integer.valueOf(ListaRonda.get(i).toString());
                    }
                }
            }
        }
        return ronda;
    }

    public void CARGAR_DATOS() {
        Crud_A98 DATOS;
        DATOS = Crud_A98.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_A98 = DATOS.CONSULTA_GENERAL_A98(Tdo_Fila, Tdo_Rack);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_A98.moveToFirst() == false) {
            //el cursor está vacío
            ListaA98 = new ArrayList<A98>();
            return;
        }
        String tdo_a98 = "", tdo_fila = "", tdo_rack = "", tdo_area = "", inicio = "", fin = "", observaciones = "", usuario_ingresa = "", fecha_ingreso = "";
        int P_Tdo = T_A98.getColumnIndex(IT_A98.I_A98.ID);
        int P_Id_Fila = T_A98.getColumnIndex(IT_A98.I_A98.ID_FILA);
        int P_Id_Rack = T_A98.getColumnIndex(IT_A98.I_A98.ID_RACK);
        int P_Id_Area = T_A98.getColumnIndex(IT_A98.I_A98.ID_AREA);
        int P_Inicio = T_A98.getColumnIndex(IT_A98.I_A98.INICIO);
        int P_Fin = T_A98.getColumnIndex(IT_A98.I_A98.FIN);
        int P_Observaciones = T_A98.getColumnIndex(IT_A98.I_A98.OBSERVACIONES);
        int P_Usuario_Ingresa = T_A98.getColumnIndex(IT_A98.I_A98.USUARIO_INGRESA);
        int P_Fecha_Ingeso = T_A98.getColumnIndex(IT_A98.I_A98.FECHA_INGRESO);
        for (T_A98.moveToFirst(); !T_A98.isAfterLast(); T_A98.moveToNext()) {
            tdo_a98 = T_A98.getString(P_Tdo);
            tdo_fila = T_A98.getString(P_Id_Fila);
            tdo_rack = T_A98.getString(P_Id_Rack);
            tdo_area = T_A98.getString(P_Id_Area);
            inicio = T_A98.getString(P_Inicio);
            fin = T_A98.getString(P_Fin);
            observaciones = T_A98.getString(P_Observaciones);
            usuario_ingresa = T_A98.getString(P_Usuario_Ingresa);
            Usuario_Ingresa = usuario_ingresa;
            fecha_ingreso = T_A98.getString(P_Fecha_Ingeso);
            Fecha_Ingeso = fecha_ingreso;
            A98 a98 = new A98(tdo_a98, tdo_fila, tdo_rack, tdo_area, inicio, fin, observaciones, usuario_ingresa, fecha_ingreso);
            ListaA98.add(a98);
        }
        LinearLayout ly;
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly = (LinearLayout) lAreas.getChildAt(i);
            if (ly.getTag().toString().equals(ListaArea.get(i).getID()) && ListaArea.get(i).getTIPO_AREA().equals("TAR-3")) {
                final Switch swInicio = (Switch) ly.getChildAt(1);
                final Switch swFin = (Switch) ly.getChildAt(2);
                final TextInputLayout tilObservaciones = (TextInputLayout) ly.getChildAt(3);
                for (int j = 0; j < ListaA98.size(); j++) {
                    if (ListaA98.get(j).getID_AREA().equals(ly.getTag().toString())) {
                        swInicio.setEnabled(false);
                        swInicio.setVisibility(View.INVISIBLE);
                        if (ListaA98.get(j).getINICIO().equals("SI")) {
                            swInicio.setChecked(true);
                        } else {
                            swInicio.setChecked(false);
                        }
                        swFin.setEnabled(true);
                        swFin.setVisibility(View.VISIBLE);
                        if (ListaA98.get(j).getFIN().equals("SI")) {
                            swFin.setChecked(true);
                        } else {
                            swFin.setChecked(false);
                        }
                        tilObservaciones.getEditText().setText(ListaA98.get(j).getOBSERVACIONES());
                        j = ListaA98.size();
                    }
                }
            }
            if (ly.getTag().toString().equals(ListaArea.get(i).getID()) && ListaArea.get(i).getTIPO_AREA().equals("TAR-4")) {
                final TextInputLayout tilInicio = (TextInputLayout) ly.getChildAt(1);
                final TextInputLayout tilFin = (TextInputLayout) ly.getChildAt(2);
                final TextInputLayout tilObservaciones = (TextInputLayout) ly.getChildAt(3);
                for (int j = 0; j < ListaA98.size(); j++) {
                    if (ListaA98.get(j).getID_AREA().equals(ly.getTag().toString())) {
                        tilInicio.getEditText().setText(ListaA98.get(j).getINICIO());
                        tilInicio.getEditText().setEnabled(false);
                        tilInicio.getEditText().setVisibility(View.INVISIBLE);
                        tilFin.getEditText().setText(ListaA98.get(j).getFIN());
                        tilFin.getEditText().setEnabled(true);
                        tilFin.getEditText().setVisibility(View.VISIBLE);
                        tilObservaciones.getEditText().setText(ListaA98.get(j).getOBSERVACIONES());
                        j = ListaA98.size();
                    }
                }
            }
        }

    }

    public void LIMPIAR_DATOS() {
        Tdo_Fila = "";
        Tdo_Rack = "";
        Tdo_Area = "";
        Inicio = "";
        Fin = "";
        Observaciones = "";
        Usuario_Ingresa = "";
        Fecha_Ingeso = "";
        ListaA98 = new ArrayList<A98>();
        msFila.setText("");
        msRack.setText("");
        LinearLayout ly1;
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly1 = (LinearLayout) lAreas.getChildAt(i);
            if (ly1.getTag().toString().equals(ListaArea.get(i).getID()) && ListaArea.get(i).getTIPO_AREA().equals("TAR-3")) {
                final Switch swInicio = (Switch) ly1.getChildAt(1);
                final Switch swFin = (Switch) ly1.getChildAt(2);
                final TextInputLayout tilObservaciones = (TextInputLayout) ly1.getChildAt(3);
                swInicio.setChecked(false);
                swInicio.setError(null);
                swInicio.setEnabled(true);
                swInicio.setVisibility(View.VISIBLE);
                swFin.setChecked(false);
                swFin.setError(null);
                swFin.setEnabled(false);
                swFin.setVisibility(View.INVISIBLE);
                tilObservaciones.getEditText().setText("");
                tilObservaciones.getEditText().setError(null);
            }
            if (ly1.getTag().toString().equals(ListaArea.get(i).getID()) && ListaArea.get(i).getTIPO_AREA().equals("TAR-4")) {
                final TextInputLayout tilInicio = (TextInputLayout) ly1.getChildAt(1);
                final TextInputLayout tilFin = (TextInputLayout) ly1.getChildAt(2);
                final TextInputLayout tilObservaciones = (TextInputLayout) ly1.getChildAt(3);
                tilInicio.getEditText().setText("");
                tilInicio.getEditText().setError(null);
                tilInicio.getEditText().setEnabled(true);
                tilInicio.getEditText().setVisibility(View.VISIBLE);
                tilFin.getEditText().setText("");
                tilFin.getEditText().setError(null);
                tilFin.getEditText().setEnabled(false);
                tilFin.getEditText().setVisibility(View.INVISIBLE);
                tilObservaciones.getEditText().setText("");
                tilObservaciones.getEditText().setError(null);
            }
        }
    }

    public void LIMPIAR_DATOS_2() {
        Tdo_Area = "";
        Inicio = "";
        Fin = "";
        Observaciones = "";
        Usuario_Ingresa = "";
        Fecha_Ingeso = "";
        ListaA98 = new ArrayList<A98>();
        LinearLayout ly1;
        for (int i = 0; i < lAreas.getChildCount(); i++) {
            ly1 = (LinearLayout) lAreas.getChildAt(i);
            if (ly1.getTag().toString().equals(ListaArea.get(i).getID()) && ListaArea.get(i).getTIPO_AREA().equals("TAR-3")) {
                final Switch swInicio = (Switch) ly1.getChildAt(1);
                final Switch swFin = (Switch) ly1.getChildAt(2);
                final TextInputLayout tilObservaciones = (TextInputLayout) ly1.getChildAt(3);
                swInicio.setChecked(false);
                swInicio.setError(null);
                swInicio.setEnabled(true);
                swInicio.setVisibility(View.VISIBLE);
                swFin.setChecked(false);
                swFin.setError(null);
                swFin.setEnabled(false);
                swFin.setVisibility(View.INVISIBLE);
                tilObservaciones.getEditText().setText("");
                tilObservaciones.getEditText().setError(null);
            }
            if (ly1.getTag().toString().equals(ListaArea.get(i).getID()) && ListaArea.get(i).getTIPO_AREA().equals("TAR-4")) {
                final TextInputLayout tilInicio = (TextInputLayout) ly1.getChildAt(1);
                final TextInputLayout tilFin = (TextInputLayout) ly1.getChildAt(2);
                final TextInputLayout tilObservaciones = (TextInputLayout) ly1.getChildAt(3);
                tilInicio.getEditText().setText("");
                tilInicio.getEditText().setError(null);
                tilInicio.getEditText().setEnabled(true);
                tilInicio.getEditText().setVisibility(View.VISIBLE);
                tilFin.getEditText().setText("");
                tilFin.getEditText().setError(null);
                tilFin.getEditText().setEnabled(false);
                tilFin.getEditText().setVisibility(View.INVISIBLE);
                tilObservaciones.getEditText().setText("");
                tilObservaciones.getEditText().setError(null);
            }
        }
    }

    public void DIBUJAR_FILAS_DE_AREAS() {
        if (ListaArea.size() == 0) {
            return;
        }
        for (int i = 0; i < ListaArea.size(); i++) {

            final LinearLayout lFilasAreas = new LinearLayout(this);

            final Switch swInicio;
            final Switch swFin;

            final TextInputLayout tilArea = new TextInputLayout(this);

            final TextInputLayout tilInicio;
            final TextInputLayout tilFin;

            final TextInputLayout tilObservaciones = new TextInputLayout(this);

            final EditText etArea = new EditText(this);

            final EditText etInicio;
            final EditText etFin;

            final EditText etObservaciones = new EditText(this);

            lFilasAreasParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            etAreaParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
            etObservacionesParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

            lFilasAreas.setLayoutParams(lFilasAreasParams);
            lFilasAreas.setOrientation(LinearLayout.HORIZONTAL);
            lFilasAreas.setTag(ListaArea.get(i).getID());
            lFilasAreas.setId(i);

            etAreaParams.gravity = Gravity.CENTER;
            etAreaParams.width = 300;
            etArea.setLayoutParams(etAreaParams);
            //etArea.setHint("Detalle - Categoría");
            etArea.setText(ListaArea.get(i).getNOMBRE_AREA());
            etArea.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etArea.setTextColor(getThemeAccentColor(this));
            etArea.setEnabled(false);

            etObservacionesParams.gravity = Gravity.CENTER;
            etObservacionesParams.width = 300;
            etObservaciones.setLayoutParams(etObservacionesParams);
            etObservaciones.setId(i);
            //etObservaciones.setHint("Observaciones");
            etObservaciones.setText("");
            etObservaciones.setInputType(InputType.TYPE_CLASS_TEXT);
            etObservaciones.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            etObservaciones.setTextColor(getThemeAccentColor(this));

            tilArea.addView(etArea);
            tilObservaciones.addView(etObservaciones);

            lFilasAreas.addView(tilArea);

            if (ListaArea.get(i).getTIPO_AREA().equals("TAR-3")) {
                swInicio = new Switch(this);
                swFin = new Switch(this);

                swInicioParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                swFinParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

                swInicioParams.gravity = Gravity.CENTER;
                swInicioParams.width = 120;
                swInicio.setLayoutParams(swInicioParams);
                swInicio.setId(i);
                swInicio.setInputType(InputType.TYPE_CLASS_TEXT);
                swInicio.setText("NO-SI");
                swInicio.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                swInicio.setTextColor(getThemeAccentColor(this));
                swInicio.setEnabled(true);

                swFinParams.gravity = Gravity.CENTER;
                swFinParams.width = 120;
                swFin.setLayoutParams(swFinParams);
                swFin.setId(i);
                swFin.setText("NO-SI");
                swFin.setInputType(InputType.TYPE_CLASS_TEXT);
                swFin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                swFin.setTextColor(getThemeAccentColor(this));
                swFin.setEnabled(false);
                swFin.setVisibility(View.INVISIBLE);

                lFilasAreas.addView(swInicio);
                lFilasAreas.addView(swFin);

            }
            if (ListaArea.get(i).getTIPO_AREA().equals("TAR-4")) {
                tilInicio = new TextInputLayout(this);
                tilFin = new TextInputLayout(this);

                etInicio = new EditText(this);
                etFin = new EditText(this);

                etInicioParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);
                etFinParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER | Gravity.CENTER_VERTICAL);

                etInicioParams.gravity = Gravity.CENTER;
                etInicioParams.width = 117;
                etInicio.setLayoutParams(etInicioParams);
                etInicio.setId(i);
                //etInicio.setHint("Inicio");
                etInicio.setText("");
                etInicio.setInputType(InputType.TYPE_CLASS_NUMBER);
                etInicio.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etInicio.setTextColor(getThemeAccentColor(this));
                etInicio.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                etInicio.setEnabled(true);

                etFinParams.gravity = Gravity.CENTER;
                etFinParams.width = 117;
                etFin.setLayoutParams(etFinParams);
                etFin.setId(i);
                //etFin.setHint("Fin");
                etFin.setText("");
                etFin.setInputType(InputType.TYPE_CLASS_NUMBER);
                etFin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                etFin.setTextColor(getThemeAccentColor(this));
                etFin.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
                etFin.setEnabled(false);
                etFin.setVisibility(View.INVISIBLE);

                tilInicio.addView(etInicio);
                tilFin.addView(etFin);

                lFilasAreas.addView(tilInicio);
                lFilasAreas.addView(tilFin);

            }

            lFilasAreas.addView(tilObservaciones);

            lAreas.addView(lFilasAreas);

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
        ListaRack = new ArrayList<Rack>();
        String Tdo_Rack = "", Tdo_Ur = "", Nombre_Rack = "", Tdo_Fila = "";
        for (T_Rack.moveToFirst(); !T_Rack.isAfterLast(); T_Rack.moveToNext()) {
            Tdo_Rack = T_Rack.getString(P_TDO);
            Nombre_Rack = T_Rack.getString(P_Nombre_Rack);
            Tdo_Fila = T_Rack.getString(P_Id_Fila);
            Tdo_Ur = T_Rack.getString(P_Id_UR);
            Rack Rack = new Rack(Tdo_Rack, Nombre_Rack, Tdo_Fila, Tdo_Ur);
            ListaRack.add(Rack);
        }
        Tdo_Rack = "";
        Nombre_Rack = "";
        ArrayAdapter<Rack> Adapter1 = new ArrayAdapter<Rack>(this, android.R.layout.simple_spinner_dropdown_item, ListaRack);
        Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msRack.setAdapter(Adapter1);
    }

    public void CARGAR_AREAS() {
        Crud_Area DATOS;
        DATOS = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Area = DATOS.CONSULTA_GENERAL_AREA_A98();
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



