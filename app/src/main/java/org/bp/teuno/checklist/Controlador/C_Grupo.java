package org.bp.teuno.checklist.Controlador;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.bp.teuno.checklist.Modelo.Grupo;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Grupo;
import org.bp.teuno.checklist.UI.Crud_Grupo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Grupo extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Grupo, etNombre_Grupo, etPinSeguridad;
    TextInputLayout tilId_Grupo, tilNombre_Grupo, tilPinSeguridad;
    CheckBox chkFilas, chkClientes, chkAlertas, chkTipoAlertas, chkColorAlertas, chkCantidadUR, chkPasillos, chkChillers, chkAreas, chkGrupos, chkRacks, chkEquipos, chkPinSeguridad, chkVersion, chkParametros;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Grupo Datos;
    String Tdo_Grupo, Id_Grupo, Nombre_Grupo, Id_Grupo_Temporal = "", Pin_Seguridad = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    int Bandera = 0, anterior = 0, siguiente = 0;
    Cursor T_Grupo;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    String ischeckFilas, ischeckClientes, ischeckAlertas, ischeckTipoAlertas, ischeckColorAlertas, ischeckCantidadUR, ischeckPasillos, ischeckChillers, ischeckAreas, ischeckGrupos, ischeckRacks, ischeckEquipos, ischeckPinSeguridad, ischeckVersion, ischeckParametros;
    ArrayList<Grupo> ListaGrupo = new ArrayList<Grupo>();
    CountDownTimer countDownTimer;
    private String Tdo_Grupo_1;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_grupo);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabNuevo = (FloatingActionButton) findViewById(R.id.fabNuevo);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabGuardar.setEnabled(false);
        fabBuscar = (FloatingActionButton) findViewById(R.id.fabBuscar);
        fabModificar = (FloatingActionButton) findViewById(R.id.fabModificar);
        fabLimpiar = (FloatingActionButton) findViewById(R.id.fabLimpiar);
        fabModificar.setEnabled(false);
        fabDesactivar = (FloatingActionButton) findViewById(R.id.fabDesactivar);
        fabDesactivar.setEnabled(false);
        fabAtras = (FloatingActionButton) findViewById(R.id.fabAtras);
        fabAtras.setEnabled(false);
        fabSiguiente = (FloatingActionButton) findViewById(R.id.fabSiguiente);
        fabSiguiente.setEnabled(false);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo_1 = bundle.getString("Id_Grupo_Operador").toString();

        etId_Grupo = (EditText) findViewById(R.id.etId_Grupo);
        etNombre_Grupo = (EditText) findViewById(R.id.etNombre_Grupo);
        etPinSeguridad = (EditText) findViewById(R.id.etPinSeguridad);
        tilId_Grupo = (TextInputLayout) findViewById(R.id.tilId_Grupo);
        tilNombre_Grupo = (TextInputLayout) findViewById(R.id.tilNombre_Grupo);
        tilPinSeguridad = (TextInputLayout) findViewById(R.id.tilPinSeguridad);

        etPinSeguridad.setEnabled(false);

        chkFilas = (CheckBox) findViewById(R.id.chkFilas);
        chkClientes = (CheckBox) findViewById(R.id.chkClientes);
        chkCantidadUR = (CheckBox) findViewById(R.id.chkCantidadUR);
        chkTipoAlertas = (CheckBox) findViewById(R.id.chkTipoAlertas);
        chkColorAlertas = (CheckBox) findViewById(R.id.chkColorAlertas);
        chkAlertas = (CheckBox) findViewById(R.id.chkAlertas);
        chkPasillos = (CheckBox) findViewById(R.id.chkPasillos);
        chkChillers = (CheckBox) findViewById(R.id.chkChillers);
        chkAreas = (CheckBox) findViewById(R.id.chkAreas);
        chkGrupos = (CheckBox) findViewById(R.id.chkGrupos);
        chkRacks = (CheckBox) findViewById(R.id.chkRacks);
        chkEquipos = (CheckBox) findViewById(R.id.chkEquipos);
        chkPinSeguridad = (CheckBox) findViewById(R.id.chkPinSeguridad);
        chkVersion = (CheckBox) findViewById(R.id.chkVersion);
        chkParametros = (CheckBox) findViewById(R.id.chkParametros);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        chkFilas.setChecked(false);
        chkClientes.setChecked(false);
        chkCantidadUR.setChecked(false);
        chkTipoAlertas.setChecked(false);
        chkColorAlertas.setChecked(false);
        chkAlertas.setChecked(false);
        chkRacks.setChecked(false);
        chkEquipos.setChecked(false);
        chkPasillos.setChecked(false);
        chkChillers.setChecked(false);
        chkAreas.setChecked(false);
        chkGrupos.setChecked(false);
        chkVersion.setChecked(false);
        chkParametros.setChecked(false);

        chkPinSeguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    etPinSeguridad.setEnabled(true);
                    etPinSeguridad.setText("");
                    etPinSeguridad.setError(null);
                } else {
                    etPinSeguridad.setEnabled(false);
                    etPinSeguridad.setText("");
                    etPinSeguridad.setError(null);
                }
            }
        });

        etId_Grupo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Grupo.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Grupo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Grupo.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPinSeguridad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Grupo.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bandera = 0;
                LIMPIARDATOS();
                ACTIVARDATOS(0);
            }
        });

        fabModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bandera = 1;
                ACTIVARDATOS(3);
            }
        });

        fabLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListaGrupo = new ArrayList<Grupo>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Grupo.matches("")) {
                    etId_Grupo.setError("Ingrese el ID del grupo");
                    etId_Grupo.requestFocus();
                    return;
                }
                if (Nombre_Grupo.matches("") && !Id_Grupo.equals("-1")) {
                    etNombre_Grupo.setError("Ingrese el nombre del grupo");
                    etNombre_Grupo.requestFocus();
                    return;
                }
                if (ischeckPinSeguridad.equals("S") && Pin_Seguridad.equals("")) {
                    etPinSeguridad.setError("Ingrese el PIN de seguridad");
                    etPinSeguridad.requestFocus();
                    return;
                }
                if (Existe_Grupo() == true && Bandera == 0) {
                    etId_Grupo.setError("Ya existe un grupo registrado con este ID, elija otro");
                    etId_Grupo.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Grupo.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Grupo.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_GRUPO(new Grupo(null, Id_Grupo, Nombre_Grupo, Pin_Seguridad, ischeckFilas, ischeckClientes, ischeckCantidadUR, ischeckColorAlertas, ischeckTipoAlertas, ischeckRacks, ischeckEquipos, ischeckAlertas, ischeckPasillos, ischeckChillers, ischeckAreas, ischeckGrupos, ischeckVersion, ischeckParametros, "A", Id_Operador, "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Grupo.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.MODIFICAR_GRUPO(new Grupo(Tdo_Grupo, Id_Grupo, Nombre_Grupo, Pin_Seguridad, ischeckFilas, ischeckClientes, ischeckCantidadUR, ischeckColorAlertas, ischeckTipoAlertas, ischeckRacks, ischeckEquipos, ischeckAlertas, ischeckPasillos, ischeckChillers, ischeckAreas, ischeckGrupos, ischeckVersion, ischeckParametros, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            Toast.makeText(getApplicationContext(), "El registro se guardó correctamente", Toast.LENGTH_LONG).show();
                                            LIMPIARDATOS();
                                            ACTIVARDATOS(1);
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

        fabBuscar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                String nombre_grupo = "";
                if (!Nombre_Grupo.equals("")) {
                    nombre_grupo = "%" + Nombre_Grupo + "%";
                } else {
                    nombre_grupo = "";
                }
                Datos = Crud_Grupo.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Grupo = Datos.CONSULTA_GENERAL_GRUPO_POR_VALORES(Id_Grupo, nombre_grupo);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Grupo.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaGrupo = new ArrayList<Grupo>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
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
                int P_USUARIO_INGRESA = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.FECHA_INGRESO);
                ListaGrupo = new ArrayList<Grupo>();
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
                    Usuario_Ingresa = T_Grupo.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Grupo.getString(P_FECHA_INGRESO);
                    Grupo grupo = new Grupo(Tdo_Grupo, Id_Grupo, Nombre_Grupo, Pin_Seguridad, ischeckFilas, ischeckClientes, ischeckCantidadUR, ischeckColorAlertas, ischeckTipoAlertas, ischeckRacks, ischeckEquipos, ischeckAlertas, ischeckPasillos, ischeckChillers, ischeckAreas, ischeckGrupos, ischeckVersion, ischeckParametros);
                    ListaGrupo.add(grupo);
                }
                LIMPIARDATOS();
                Tdo_Grupo = ListaGrupo.get(0).getID();
                Id_Grupo = ListaGrupo.get(0).getID_GRUPO();
                Nombre_Grupo = ListaGrupo.get(0).getNOMBRE_GRUPO();
                Id_Grupo_Temporal = Id_Grupo;
                etId_Grupo.setText(Id_Grupo);
                etNombre_Grupo.setText(Nombre_Grupo);
                Pin_Seguridad = ListaGrupo.get(0).getPIN_SEGURIDAD();
                if (!Pin_Seguridad.equals("")) {
                    etPinSeguridad.setText(Pin_Seguridad);
                    etPinSeguridad.setEnabled(true);
                    chkPinSeguridad.setChecked(true);
                } else {
                    etPinSeguridad.setEnabled(false);
                    chkPinSeguridad.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_FILA().equals("S")) {
                    ischeckFilas = "S";
                    chkFilas.setChecked(true);
                } else {
                    ischeckFilas = "N";
                    chkFilas.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_CLIENTE().equals("S")) {
                    ischeckClientes = "S";
                    chkClientes.setChecked(true);
                } else {
                    ischeckClientes = "N";
                    chkClientes.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_CANTIDAD_UR().equals("S")) {
                    ischeckCantidadUR = "S";
                    chkCantidadUR.setChecked(true);
                } else {
                    ischeckCantidadUR = "N";
                    chkCantidadUR.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_RACK().equals("S")) {
                    ischeckRacks = "S";
                    chkRacks.setChecked(true);
                } else {
                    ischeckRacks = "N";
                    chkRacks.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_EQUIPO().equals("S")) {
                    ischeckEquipos = "S";
                    chkEquipos.setChecked(true);
                } else {
                    ischeckEquipos = "N";
                    chkEquipos.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_ALERTA().equals("S")) {
                    ischeckAlertas = "S";
                    chkAlertas.setChecked(true);
                } else {
                    ischeckAlertas = "N";
                    chkAlertas.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_TIPO_ALERTA().equals("S")) {
                    ischeckTipoAlertas = "S";
                    chkTipoAlertas.setChecked(true);
                } else {
                    ischeckTipoAlertas = "N";
                    chkTipoAlertas.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_COLOR_ALERTA().equals("S")) {
                    ischeckColorAlertas = "S";
                    chkColorAlertas.setChecked(true);
                } else {
                    ischeckColorAlertas = "N";
                    chkColorAlertas.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_PASILLO().equals("S")) {
                    ischeckPasillos = "S";
                    chkPasillos.setChecked(true);
                } else {
                    ischeckPasillos = "N";
                    chkPasillos.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_CHILLER().equals("S")) {
                    ischeckChillers = "S";
                    chkChillers.setChecked(true);
                } else {
                    ischeckChillers = "N";
                    chkChillers.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_AREA().equals("S")) {
                    ischeckAreas = "S";
                    chkAreas.setChecked(true);
                } else {
                    ischeckAreas = "N";
                    chkAreas.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_GRUPO().equals("S")) {
                    ischeckGrupos = "S";
                    chkGrupos.setChecked(true);
                } else {
                    ischeckGrupos = "N";
                    chkGrupos.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_VERSION().equals("S")) {
                    ischeckVersion = "S";
                    chkVersion.setChecked(true);
                } else {
                    ischeckVersion = "N";
                    chkVersion.setChecked(false);
                }
                if (ListaGrupo.get(0).getPERMISO_PARAMETROS().equals("S")) {
                    ischeckParametros = "S";
                    chkParametros.setChecked(true);
                } else {
                    ischeckParametros = "N";
                    chkParametros.setChecked(false);
                }
                Usuario_Ingresa = ListaGrupo.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaGrupo.get(0).getFECHA_INGRESO();
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Grupo.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Grupo.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            Datos.DESACTIVAR_GRUPO(new Grupo(Tdo_Grupo, Id_Grupo, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                            Datos.GETBD().setTransactionSuccessful();
                                        } finally {
                                            Datos.GETBD().endTransaction();
                                        }
                                        Toast.makeText(getApplicationContext(), "El registro se desactivó correctamente", Toast.LENGTH_LONG).show();
                                        LIMPIARDATOS();
                                        ACTIVARDATOS(1);
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
        });

        fabAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaGrupo.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Grupo = ListaGrupo.get(anterior).ID;
                Id_Grupo = ListaGrupo.get(anterior).ID_GRUPO;
                Id_Grupo_Temporal = Id_Grupo;
                Nombre_Grupo = ListaGrupo.get(anterior).NOMBRE_GRUPO;
                etId_Grupo.setText(Id_Grupo);
                etNombre_Grupo.setText(Nombre_Grupo);
                Pin_Seguridad = ListaGrupo.get(anterior).getPIN_SEGURIDAD();
                if (!Pin_Seguridad.equals("")) {
                    etPinSeguridad.setText(Pin_Seguridad);
                    etPinSeguridad.setEnabled(true);
                    chkPinSeguridad.setChecked(true);
                } else {
                    etPinSeguridad.setEnabled(false);
                    chkPinSeguridad.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_FILA().equals("S")) {
                    ischeckFilas = "S";
                    chkFilas.setChecked(true);
                } else {
                    ischeckFilas = "N";
                    chkFilas.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_CLIENTE().equals("S")) {
                    ischeckClientes = "S";
                    chkClientes.setChecked(true);
                } else {
                    ischeckClientes = "N";
                    chkClientes.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_CANTIDAD_UR().equals("S")) {
                    ischeckCantidadUR = "S";
                    chkCantidadUR.setChecked(true);
                } else {
                    ischeckCantidadUR = "N";
                    chkCantidadUR.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_RACK().equals("S")) {
                    ischeckRacks = "S";
                    chkRacks.setChecked(true);
                } else {
                    ischeckRacks = "N";
                    chkRacks.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_EQUIPO().equals("S")) {
                    ischeckEquipos = "S";
                    chkEquipos.setChecked(true);
                } else {
                    ischeckEquipos = "N";
                    chkEquipos.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_ALERTA().equals("S")) {
                    ischeckAlertas = "S";
                    chkAlertas.setChecked(true);
                } else {
                    ischeckAlertas = "N";
                    chkAlertas.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_TIPO_ALERTA().equals("S")) {
                    ischeckTipoAlertas = "S";
                    chkTipoAlertas.setChecked(true);
                } else {
                    ischeckTipoAlertas = "N";
                    chkTipoAlertas.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_COLOR_ALERTA().equals("S")) {
                    ischeckColorAlertas = "S";
                    chkColorAlertas.setChecked(true);
                } else {
                    ischeckColorAlertas = "N";
                    chkColorAlertas.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_PASILLO().equals("S")) {
                    ischeckPasillos = "S";
                    chkPasillos.setChecked(true);
                } else {
                    ischeckPasillos = "N";
                    chkPasillos.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_CHILLER().equals("S")) {
                    ischeckChillers = "S";
                    chkChillers.setChecked(true);
                } else {
                    ischeckChillers = "N";
                    chkChillers.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_AREA().equals("S")) {
                    ischeckAreas = "S";
                    chkAreas.setChecked(true);
                } else {
                    ischeckAreas = "N";
                    chkAreas.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_GRUPO().equals("S")) {
                    ischeckGrupos = "S";
                    chkGrupos.setChecked(true);
                } else {
                    ischeckGrupos = "N";
                    chkGrupos.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_VERSION().equals("S")) {
                    ischeckVersion = "S";
                    chkVersion.setChecked(true);
                } else {
                    ischeckVersion = "N";
                    chkVersion.setChecked(false);
                }
                if (ListaGrupo.get(anterior).getPERMISO_PARAMETROS().equals("S")) {
                    ischeckParametros = "S";
                    chkParametros.setChecked(true);
                } else {
                    ischeckParametros = "N";
                    chkParametros.setChecked(false);
                }
                Usuario_Ingresa = ListaGrupo.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaGrupo.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaGrupo.size() == 0) {
                    return;
                }
                if (siguiente == ListaGrupo.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Grupo = ListaGrupo.get(siguiente).ID;
                Id_Grupo = ListaGrupo.get(siguiente).ID_GRUPO;
                Id_Grupo_Temporal = Id_Grupo;
                Nombre_Grupo = ListaGrupo.get(siguiente).NOMBRE_GRUPO;
                etId_Grupo.setText(Id_Grupo);
                etNombre_Grupo.setText(Nombre_Grupo);
                Pin_Seguridad = ListaGrupo.get(siguiente).getPIN_SEGURIDAD();
                if (!Pin_Seguridad.equals("")) {
                    etPinSeguridad.setText(Pin_Seguridad);
                    etPinSeguridad.setEnabled(true);
                    chkPinSeguridad.setChecked(true);
                } else {
                    etPinSeguridad.setEnabled(false);
                    chkPinSeguridad.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_FILA().equals("S")) {
                    ischeckFilas = "S";
                    chkFilas.setChecked(true);
                } else {
                    ischeckFilas = "N";
                    chkFilas.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_CLIENTE().equals("S")) {
                    ischeckClientes = "S";
                    chkClientes.setChecked(true);
                } else {
                    ischeckClientes = "N";
                    chkClientes.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_CANTIDAD_UR().equals("S")) {
                    ischeckCantidadUR = "S";
                    chkCantidadUR.setChecked(true);
                } else {
                    ischeckCantidadUR = "N";
                    chkCantidadUR.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_RACK().equals("S")) {
                    ischeckRacks = "S";
                    chkRacks.setChecked(true);
                } else {
                    ischeckRacks = "N";
                    chkRacks.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_EQUIPO().equals("S")) {
                    ischeckEquipos = "S";
                    chkEquipos.setChecked(true);
                } else {
                    ischeckEquipos = "N";
                    chkEquipos.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_ALERTA().equals("S")) {
                    ischeckAlertas = "S";
                    chkAlertas.setChecked(true);
                } else {
                    ischeckAlertas = "N";
                    chkAlertas.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_TIPO_ALERTA().equals("S")) {
                    ischeckTipoAlertas = "S";
                    chkTipoAlertas.setChecked(true);
                } else {
                    ischeckTipoAlertas = "N";
                    chkTipoAlertas.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_COLOR_ALERTA().equals("S")) {
                    ischeckColorAlertas = "S";
                    chkColorAlertas.setChecked(true);
                } else {
                    ischeckColorAlertas = "N";
                    chkColorAlertas.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_PASILLO().equals("S")) {
                    ischeckPasillos = "S";
                    chkPasillos.setChecked(true);
                } else {
                    ischeckPasillos = "N";
                    chkPasillos.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_CHILLER().equals("S")) {
                    ischeckChillers = "S";
                    chkChillers.setChecked(true);
                } else {
                    ischeckChillers = "N";
                    chkChillers.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_AREA().equals("S")) {
                    ischeckAreas = "S";
                    chkAreas.setChecked(true);
                } else {
                    ischeckAreas = "N";
                    chkAreas.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_GRUPO().equals("S")) {
                    ischeckGrupos = "S";
                    chkGrupos.setChecked(true);
                } else {
                    ischeckGrupos = "N";
                    chkGrupos.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_VERSION().equals("S")) {
                    ischeckVersion = "S";
                    chkVersion.setChecked(true);
                } else {
                    ischeckVersion = "N";
                    chkVersion.setChecked(false);
                }
                if (ListaGrupo.get(siguiente).getPERMISO_PARAMETROS().equals("S")) {
                    ischeckParametros = "S";
                    chkParametros.setChecked(true);
                } else {
                    ischeckParametros = "N";
                    chkParametros.setChecked(false);
                }
                Usuario_Ingresa = ListaGrupo.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaGrupo.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Grupo.this, C_Menu_Principal.class);
                intent.putExtra("Id_Operador", Id_Operador);
                intent.putExtra("Nombre_Operador", Nombre_Operador);
                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                intent.putExtra("Turno_Operador", Turno_Operador);
                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo_1);
                startActivity(intent);
                finish();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
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

    public boolean Existe_Grupo() {
        Datos = Crud_Grupo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Grupo = Datos.CONSULTA_GENERAL_GRUPO_POR_ID(Id_Grupo);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Grupo.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Grupo = "";
        Id_Grupo = "";
        Id_Grupo_Temporal = "";
        anterior = 0;
        siguiente = 0;
        etId_Grupo.setText("");
        etNombre_Grupo.setText("");
        etPinSeguridad.setText("");
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
        chkFilas.setChecked(false);
        chkClientes.setChecked(false);
        chkCantidadUR.setChecked(false);
        chkTipoAlertas.setChecked(false);
        chkColorAlertas.setChecked(false);
        chkAlertas.setChecked(false);
        chkRacks.setChecked(false);
        chkEquipos.setChecked(false);
        chkPasillos.setChecked(false);
        chkChillers.setChecked(false);
        chkAreas.setChecked(false);
        chkGrupos.setChecked(false);
        chkPinSeguridad.setChecked(false);
        chkVersion.setChecked(false);
        chkParametros.setChecked(false);
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            etId_Grupo.setEnabled(true);
            etId_Grupo.requestFocus();
            etNombre_Grupo.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Grupo.setEnabled(true);
            etId_Grupo.requestFocus();
            etNombre_Grupo.setEnabled(true);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaGrupo.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Grupo.setEnabled(true);
            etId_Grupo.requestFocus();
            etNombre_Grupo.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Grupo.setEnabled(false);
            etId_Grupo.setText(Id_Grupo);
            etNombre_Grupo.requestFocus();
            etNombre_Grupo.setEnabled(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Obtener_Datos() {
        Id_Grupo = etId_Grupo.getText().toString();
        Nombre_Grupo = etNombre_Grupo.getText().toString().toUpperCase();
        if (chkFilas.isChecked()) {
            ischeckFilas = "S";
        } else {
            ischeckFilas = "N";
        }
        if (chkClientes.isChecked()) {
            ischeckClientes = "S";
        } else {
            ischeckClientes = "N";
        }
        if (chkTipoAlertas.isChecked()) {
            ischeckTipoAlertas = "S";
        } else {
            ischeckTipoAlertas = "N";
        }
        if (chkColorAlertas.isChecked()) {
            ischeckColorAlertas = "S";
        } else {
            ischeckColorAlertas = "N";
        }
        if (chkAlertas.isChecked()) {
            ischeckAlertas = "S";
        } else {
            ischeckAlertas = "N";
        }
        if (chkPasillos.isChecked()) {
            ischeckPasillos = "S";
        } else {
            ischeckPasillos = "N";
        }
        if (chkChillers.isChecked()) {
            ischeckChillers = "S";
        } else {
            ischeckChillers = "N";
        }
        if (chkCantidadUR.isChecked()) {
            ischeckCantidadUR = "S";
        } else {
            ischeckCantidadUR = "N";
        }
        if (chkGrupos.isChecked()) {
            ischeckGrupos = "S";
        } else {
            ischeckGrupos = "N";
        }
        if (chkRacks.isChecked()) {
            ischeckRacks = "S";
        } else {
            ischeckRacks = "N";
        }
        if (chkEquipos.isChecked()) {
            ischeckEquipos = "S";
        } else {
            ischeckEquipos = "N";
        }
        if (chkAreas.isChecked()) {
            ischeckAreas = "S";
        } else {
            ischeckAreas = "N";
        }
        if (chkPinSeguridad.isChecked()) {
            ischeckPinSeguridad = "S";
            Pin_Seguridad = etPinSeguridad.getText().toString();
        } else {
            ischeckPinSeguridad = "N";
            Pin_Seguridad = "";
        }
        if (chkVersion.isChecked()) {
            ischeckVersion = "S";
        } else {
            ischeckVersion = "N";
        }
        if (chkParametros.isChecked()) {
            ischeckParametros = "S";
        } else {
            ischeckParametros = "N";
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

}