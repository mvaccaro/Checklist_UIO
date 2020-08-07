package org.bp.teuno.checklist.Controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.bp.teuno.checklist.Modelo.Grupo;
import org.bp.teuno.checklist.Modelo.Operador;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Grupo;
import org.bp.teuno.checklist.SQLite.IT_Operador;
import org.bp.teuno.checklist.UI.Crud_Grupo;
import org.bp.teuno.checklist.UI.Crud_Operador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Operador extends AppCompatActivity {

    private final long interval = 1 * 1000;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    EditText etId_Operador, etPrimerNombre, etSegundoNombre, etPrimerApellido, etSegundoApellido, etPinSeguridad;
    TextInputLayout tilId_Operador, tilPrimerNombre, tilSegundoNombre, tilPrimerApellido, tilSegundoApellido, tilPinSeguridad;
    MaterialBetterSpinner msGrupo;
    Crud_Operador Datos;
    String Tdo_Operador, Id_Operador, Primer_Nombre, Segundo_Nombre, Primer_Apellido, Segundo_Apellido, Id_Operador_Temporal = "", Estado = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    Cursor T_Operador, T_Grupo;
    int Bandera = 0, anterior = 0, siguiente = 0;
    String Tdo_Grupo, Id_Grupo, Nombre_Grupo, Id_Grupo_Temporal = "", Pin_Seguridad = "";
    String ischeckFilas, ischeckClientes, ischeckAlertas, ischeckTipoAlertas, ischeckColorAlertas, ischeckCantidadUR, ischeckPasillos, ischeckChillers, ischeckAreas, ischeckGrupos, ischeckRacks, ischeckEquipos, ischeckPinSeguridad, ischeckVersion, ischeckParametros;
    ArrayList<Operador> ListaOperador = new ArrayList<Operador>();
    ArrayList<Grupo> ListaGrupo = new ArrayList<Grupo>();
    CountDownTimer countDownTimer;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_operador);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabNuevo = (FloatingActionButton) findViewById(R.id.fabNuevo);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabGuardar.setEnabled(false);
        fabBuscar = (FloatingActionButton) findViewById(R.id.fabBuscar);
        fabModificar = (FloatingActionButton) findViewById(R.id.fabModificar);
        fabModificar.setEnabled(false);
        fabLimpiar = (FloatingActionButton) findViewById(R.id.fabLimpiar);
        fabDesactivar = (FloatingActionButton) findViewById(R.id.fabDesactivar);
        fabDesactivar.setEnabled(false);
        fabAtras = (FloatingActionButton) findViewById(R.id.fabAtras);
        fabAtras.setEnabled(false);
        fabSiguiente = (FloatingActionButton) findViewById(R.id.fabSiguiente);
        fabSiguiente.setEnabled(false);

        etId_Operador = (EditText) findViewById(R.id.etId_Operador);
        etPrimerNombre = (EditText) findViewById(R.id.etPrimer_Nombre);
        etSegundoNombre = (EditText) findViewById(R.id.etSegundo_Nombre);
        etPrimerApellido = (EditText) findViewById(R.id.etPrimer_Apellido);
        etSegundoApellido = (EditText) findViewById(R.id.etSegundo_Apellido);
        etPinSeguridad = (EditText) findViewById(R.id.etPinSeguridad);

        tilId_Operador = (TextInputLayout) findViewById(R.id.tilId_Operador);
        tilPrimerNombre = (TextInputLayout) findViewById(R.id.tilPrimer_Nombre);
        tilSegundoNombre = (TextInputLayout) findViewById(R.id.tilSegundo_Nombre);
        tilPrimerApellido = (TextInputLayout) findViewById(R.id.tilPrimer_Apellido);
        tilSegundoApellido = (TextInputLayout) findViewById(R.id.tilSegundo_Apellido);
        tilPinSeguridad = (TextInputLayout) findViewById(R.id.tilPinSeguridad);

        msGrupo = (MaterialBetterSpinner) findViewById(R.id.msGrupo);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        msGrupo.setText("");
        msGrupo.setHint("* Grupo");
        msGrupo.setEnabled(false);
        msGrupo.setFocusableInTouchMode(false);

        CARGAR_GRUPOS();

        msGrupo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaGrupo.size() > 0) {
                    Tdo_Grupo = ListaGrupo.get(position).getID();
                    Id_Grupo = ListaGrupo.get(position).getID_GRUPO();
                    Nombre_Grupo = ListaGrupo.get(position).getNOMBRE_GRUPO();
                    Pin_Seguridad = ListaGrupo.get(position).getPIN_SEGURIDAD();
                    ischeckFilas = ListaGrupo.get(position).getPERMISO_FILA();
                    ischeckClientes = ListaGrupo.get(position).getPERMISO_CLIENTE();
                    ischeckCantidadUR = ListaGrupo.get(position).getPERMISO_CANTIDAD_UR();
                    ischeckRacks = ListaGrupo.get(position).getPERMISO_RACK();
                    ischeckEquipos = ListaGrupo.get(position).getPERMISO_CLIENTE();
                    ischeckAlertas = ListaGrupo.get(position).getPERMISO_ALERTA();
                    ischeckTipoAlertas = ListaGrupo.get(position).getPERMISO_TIPO_ALERTA();
                    ischeckColorAlertas = ListaGrupo.get(position).getPERMISO_COLOR_ALERTA();
                    ischeckPasillos = ListaGrupo.get(position).getPERMISO_PASILLO();
                    ischeckChillers = ListaGrupo.get(position).getPERMISO_CHILLER();
                    ischeckAreas = ListaGrupo.get(position).getPERMISO_AREA();
                    ischeckGrupos = ListaGrupo.get(position).getPERMISO_GRUPO();
                    if (!Pin_Seguridad.equals("")) {
                        tilPinSeguridad.setVisibility(View.VISIBLE);
                        etPinSeguridad.setVisibility(View.VISIBLE);
                        etPinSeguridad.setEnabled(true);
                        etPinSeguridad.setError(null);
                        etPinSeguridad.setText("");
                    } else {
                        tilPinSeguridad.setVisibility(View.INVISIBLE);
                        etPinSeguridad.setVisibility(View.INVISIBLE);
                        etPinSeguridad.setEnabled(false);
                        etPinSeguridad.setError(null);
                        etPinSeguridad.setText("");
                    }
                }
                Log.i("Posicion", String.valueOf(position));
            }

        });

        etId_Operador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Operador.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPrimerNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilPrimerNombre.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSegundoNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilSegundoNombre.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etPrimerApellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilPrimerApellido.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSegundoApellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilSegundoApellido.setError(null);
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
                tilPinSeguridad.setError(null);
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
                ListaOperador = new ArrayList<Operador>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENERDatos();
                if (Id_Operador.matches("")) {
                    tilId_Operador.setError("");
                    etId_Operador.setError("Debe indicar un ID para el inicio en la aplicación ");
                    etId_Operador.requestFocus();
                    return;
                }
                if (Primer_Nombre.matches("")) {
                    tilPrimerNombre.setError("");
                    etPrimerNombre.setError("Debe indicar su primer nombre para el registro de sus iniciales ");
                    etPrimerNombre.requestFocus();
                    return;
                }
                if (Primer_Apellido.matches("")) {
                    tilPrimerApellido.setError("");
                    etPrimerApellido.setError("Debe indicar su primer apellido para el registro de sus iniciales");
                    etPrimerApellido.requestFocus();
                    return;
                }
                if (Tdo_Grupo.equals("")) {
                    msGrupo.setError("Seleccione un grupo");
                    msGrupo.requestFocus();
                    return;
                }
                if (etPinSeguridad.isEnabled() && etPinSeguridad.getText().toString().equals("")) {
                    etPinSeguridad.setError("Ingrese el PIN de Seguridad");
                    etPinSeguridad.requestFocus();
                    return;
                }
                if (!etPinSeguridad.getText().toString().equals(Pin_Seguridad) && etPinSeguridad.isEnabled()) {
                    etPinSeguridad.setError("El PIN de seguridad es incorrecto");
                    etPinSeguridad.requestFocus();
                    return;
                }
                if (EXISTEOPERADORNUEVO() == true && Bandera == 0) {
                    tilId_Operador.setError("");
                    etId_Operador.setError("Ya existe un usuario registrado con este ID, elija otro");
                    etId_Operador.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Operador.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Operador.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_OPERADOR(new Operador(null, Id_Operador, Primer_Nombre, Segundo_Nombre, Primer_Apellido, Segundo_Apellido, Tdo_Grupo, "A", "1620", "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Operador.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.MODIFICAR_OPERADOR(new Operador(Tdo_Operador, Id_Operador, Primer_Nombre, Segundo_Nombre, Primer_Apellido, Segundo_Apellido, Tdo_Grupo, "A", Usuario_Ingresa, "1620", Fecha_Ingreso, getDateTime()));
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
            @Override
            public void onClick(View v) {
                OBTENERDatos();
                String primer_nombre = "", primer_apellido = "";
                if (!Primer_Nombre.equals("")) {
                    primer_nombre = "%" + Primer_Nombre + "%";
                } else {
                    primer_nombre = "";
                }
                if (!Primer_Apellido.equals("")) {
                    primer_apellido = "%" + Primer_Apellido + "%";
                } else {
                    primer_apellido = "";
                }
                Datos = Crud_Operador.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Operador = Datos.CONSULTA_GENERAL_OPERADOR_POR_VALORES(Id_Operador, primer_nombre, primer_apellido);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Operador.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaOperador = new ArrayList<Operador>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Operador = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.ID);
                int P_ID_OPERADOR = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.ID_OPERADOR);
                int P_Primer_Nombre = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.PRIMER_NOMBRE);
                int P_Segundo_Nombre = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.SEGUNDO_NOMBRE);
                int P_Primer_Apellido = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.PRIMER_APELLIDO);
                int P_Segundo_Apellido = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.SEGUNDO_APELLIDO);
                int P_Tdo_Grupo = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.ID_GRUPO);
                int P_Estado = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.ESTADO);
                int P_USUARIO_INGRESA = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.FECHA_INGRESO);
                ListaOperador = new ArrayList<Operador>();
                for (T_Operador.moveToFirst(); !T_Operador.isAfterLast(); T_Operador.moveToNext()) {
                    Tdo_Operador = T_Operador.getString(P_Tdo_Operador);
                    Id_Operador = T_Operador.getString(P_ID_OPERADOR);
                    Primer_Nombre = T_Operador.getString(P_Primer_Nombre);
                    Segundo_Nombre = T_Operador.getString(P_Segundo_Nombre);
                    Primer_Apellido = T_Operador.getString(P_Primer_Apellido);
                    Segundo_Apellido = T_Operador.getString(P_Segundo_Apellido);
                    Tdo_Grupo = T_Operador.getString(P_Tdo_Grupo);
                    Estado = T_Operador.getString(P_Estado);
                    Usuario_Ingresa = T_Operador.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Operador.getString(P_FECHA_INGRESO);
                    Operador operador = new Operador(Tdo_Operador, Id_Operador, Primer_Nombre, Segundo_Nombre, Primer_Apellido, Segundo_Apellido, Tdo_Grupo, Estado, Usuario_Ingresa, Fecha_Ingreso);
                    ListaOperador.add(operador);
                }
                LIMPIARDATOS();
                Tdo_Operador = ListaOperador.get(0).getID();
                etId_Operador.setText(ListaOperador.get(0).getID_OPERADOR());
                Id_Operador = ListaOperador.get(0).getID_OPERADOR();
                Id_Operador_Temporal = Id_Operador;
                etPrimerNombre.setText(ListaOperador.get(0).getPRIMER_NOMBRE());
                etSegundoNombre.setText(ListaOperador.get(0).getSEGUNDO_NOMBRE());
                etPrimerApellido.setText(ListaOperador.get(0).getPRIMER_APELLIDO());
                etSegundoApellido.setText(ListaOperador.get(0).getSEGUNDO_APELLIDO());
                Tdo_Grupo = ListaOperador.get(0).getID_GRUPO();
                for (int i = 0; i < ListaGrupo.size(); i++) {
                    if (Tdo_Grupo.equals(ListaGrupo.get(i).getID())) {
                        msGrupo.setText(ListaGrupo.get(i).getNOMBRE_GRUPO());
                        i = ListaGrupo.size();
                    }
                }
                for (int i = 0; i < ListaGrupo.size(); i++) {
                    if (Tdo_Grupo.equals(ListaGrupo.get(i).getID())) {
                        if (!ListaGrupo.get(i).getPIN_SEGURIDAD().equals("")) {
                            Pin_Seguridad = ListaGrupo.get(i).getPIN_SEGURIDAD();
                            tilPinSeguridad.setVisibility(View.VISIBLE);
                            etPinSeguridad.setVisibility(View.VISIBLE);
                            etPinSeguridad.setEnabled(true);
                            etPinSeguridad.setError(null);
                            etPinSeguridad.setHint("* PIN");
                            etPinSeguridad.setText("");
                        } else {
                            tilPinSeguridad.setVisibility(View.INVISIBLE);
                            etPinSeguridad.setVisibility(View.INVISIBLE);
                            etPinSeguridad.setEnabled(false);
                            etPinSeguridad.setError(null);
                            etPinSeguridad.setHint("* PIN");
                            etPinSeguridad.setText("");
                        }
                        i = ListaGrupo.size();
                    }
                }
                Usuario_Ingresa = ListaOperador.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaOperador.get(0).getFECHA_INGRESO();
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Operador.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Operador.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            Datos.DESACTIVAR_OPERADOR(new Operador(Tdo_Operador, Id_Operador, "I", Usuario_Ingresa, "1620", Fecha_Ingreso, getDateTime()));
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
                if (ListaOperador.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Operador = ListaOperador.get(anterior).ID;
                Id_Operador = ListaOperador.get(anterior).ID_OPERADOR;
                Id_Operador_Temporal = Id_Operador;
                Primer_Nombre = ListaOperador.get(anterior).PRIMER_NOMBRE;
                Segundo_Nombre = ListaOperador.get(anterior).SEGUNDO_NOMBRE;
                Primer_Apellido = ListaOperador.get(anterior).PRIMER_APELLIDO;
                Segundo_Apellido = ListaOperador.get(anterior).SEGUNDO_APELLIDO;
                etId_Operador.setText(Id_Operador);
                etPrimerNombre.setText(Primer_Nombre);
                etSegundoNombre.setText(Segundo_Nombre);
                etPrimerApellido.setText(Primer_Apellido);
                etSegundoApellido.setText(Segundo_Apellido);
                Tdo_Grupo = ListaOperador.get(anterior).getID_GRUPO();
                for (int i = 0; i < ListaGrupo.size(); i++) {
                    if (Tdo_Grupo.equals(ListaGrupo.get(i).getID())) {
                        msGrupo.setText(ListaGrupo.get(i).getNOMBRE_GRUPO());
                        i = ListaGrupo.size();
                    }
                }
                for (int i = 0; i < ListaGrupo.size(); i++) {
                    if (Tdo_Grupo.equals(ListaGrupo.get(i).getID())) {
                        if (!ListaGrupo.get(i).getPIN_SEGURIDAD().equals("")) {
                            Pin_Seguridad = ListaGrupo.get(i).getPIN_SEGURIDAD();
                            tilPinSeguridad.setVisibility(View.VISIBLE);
                            etPinSeguridad.setVisibility(View.VISIBLE);
                            etPinSeguridad.setEnabled(true);
                            etPinSeguridad.setError(null);
                            etPinSeguridad.setHint("* PIN");
                            etPinSeguridad.setText("");
                        } else {
                            tilPinSeguridad.setVisibility(View.INVISIBLE);
                            etPinSeguridad.setVisibility(View.INVISIBLE);
                            etPinSeguridad.setEnabled(false);
                            etPinSeguridad.setError(null);
                            etPinSeguridad.setHint("* PIN");
                            etPinSeguridad.setText("");
                        }
                        i = ListaGrupo.size();
                    }
                }
                Usuario_Ingresa = ListaOperador.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaOperador.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaOperador.size() == 0) {
                    return;
                }
                if (siguiente == ListaOperador.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Operador = ListaOperador.get(siguiente).ID;
                Id_Operador = ListaOperador.get(siguiente).ID_OPERADOR;
                Id_Operador_Temporal = Id_Operador;
                Primer_Nombre = ListaOperador.get(siguiente).PRIMER_NOMBRE;
                Segundo_Nombre = ListaOperador.get(siguiente).SEGUNDO_NOMBRE;
                Primer_Apellido = ListaOperador.get(siguiente).PRIMER_APELLIDO;
                Segundo_Apellido = ListaOperador.get(siguiente).SEGUNDO_APELLIDO;
                etId_Operador.setText(Id_Operador);
                etPrimerNombre.setText(Primer_Nombre);
                etSegundoNombre.setText(Segundo_Nombre);
                etPrimerApellido.setText(Primer_Apellido);
                etSegundoApellido.setText(Segundo_Apellido);
                Tdo_Grupo = ListaOperador.get(siguiente).getID_GRUPO();
                for (int i = 0; i < ListaGrupo.size(); i++) {
                    if (Tdo_Grupo.equals(ListaGrupo.get(i).getID())) {
                        msGrupo.setText(ListaGrupo.get(i).getNOMBRE_GRUPO());
                        i = ListaGrupo.size();
                    }
                }
                for (int i = 0; i < ListaGrupo.size(); i++) {
                    if (Tdo_Grupo.equals(ListaGrupo.get(i).getID())) {
                        if (!ListaGrupo.get(i).getPIN_SEGURIDAD().equals("")) {
                            Pin_Seguridad = ListaGrupo.get(i).getPIN_SEGURIDAD();
                            tilPinSeguridad.setVisibility(View.VISIBLE);
                            etPinSeguridad.setVisibility(View.VISIBLE);
                            etPinSeguridad.setEnabled(true);
                            etPinSeguridad.setError(null);
                            etPinSeguridad.setHint("* PIN");
                            etPinSeguridad.setText("");
                        } else {
                            tilPinSeguridad.setVisibility(View.INVISIBLE);
                            etPinSeguridad.setVisibility(View.INVISIBLE);
                            etPinSeguridad.setEnabled(false);
                            etPinSeguridad.setError(null);
                            etPinSeguridad.setHint("* PIN");
                            etPinSeguridad.setText("");
                        }
                        i = ListaGrupo.size();
                    }
                }
                Usuario_Ingresa = ListaOperador.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaOperador.get(siguiente).getFECHA_INGRESO();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder myBulid = new AlertDialog.Builder(this);
        myBulid.setTitle("Regresar");
        myBulid.setIcon(R.drawable.salir);
        myBulid.setMessage("¿Desea regresar a la pantalla de inicio de sesión?");
        myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(C_Operador.this, C_Inicio_Sesion.class);
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

    public boolean EXISTEOPERADORNUEVO() {
        Datos = Crud_Operador.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Operador = Datos.CONSULTA_GENERAL_OPERADOR_POR_ID(Id_Operador);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Operador.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Operador = "";
        Id_Operador = "";
        Id_Operador_Temporal = "";
        anterior = 0;
        siguiente = 0;
        etId_Operador.setText("");
        etId_Operador.requestFocus();
        etPrimerNombre.setText("");
        etSegundoNombre.setText("");
        etPrimerApellido.setText("");
        etSegundoApellido.setText("");
        Tdo_Grupo = "";
        msGrupo.setText("");
        msGrupo.setHint("* Grupo");
        tilPinSeguridad.setVisibility(View.INVISIBLE);
        etPinSeguridad.setVisibility(View.INVISIBLE);
        etPinSeguridad.setEnabled(false);
        etPinSeguridad.setError(null);
        etPinSeguridad.setText("");
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            //fabEliminar.setEnabled(false);
            etId_Operador.setEnabled(true);
            etId_Operador.requestFocus();
            etPrimerNombre.setEnabled(true);
            etSegundoNombre.setEnabled(true);
            msGrupo.setFocusableInTouchMode(false);
            etPrimerApellido.setEnabled(true);
            etSegundoApellido.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            //fabEliminar.setEnabled(false);
            msGrupo.setFocusableInTouchMode(false);
            etId_Operador.setEnabled(true);
            etId_Operador.requestFocus();
            etPrimerNombre.setEnabled(true);
            etSegundoNombre.setEnabled(false);
            etPrimerApellido.setEnabled(true);
            etSegundoApellido.setEnabled(false);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaOperador.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Operador.setEnabled(true);
            etId_Operador.requestFocus();
            etPrimerNombre.setEnabled(true);
            etSegundoNombre.setEnabled(false);
            msGrupo.setFocusableInTouchMode(false);
            etPrimerApellido.setEnabled(true);
            etSegundoApellido.setEnabled(false);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            //fabEliminar.setEnabled(false);
            etId_Operador.setEnabled(false);
            etId_Operador.setText(Id_Operador_Temporal);
            msGrupo.setFocusableInTouchMode(false);
            msGrupo.setText("");
            tilPinSeguridad.setVisibility(View.INVISIBLE);
            etPinSeguridad.setVisibility(View.INVISIBLE);
            etPinSeguridad.setEnabled(false);
            etPinSeguridad.setError(null);
            etPinSeguridad.setHint("* PIN");
            etPinSeguridad.setText("");
            etPrimerNombre.requestFocus();
            etPrimerNombre.setEnabled(true);
            etSegundoNombre.setEnabled(true);
            etPrimerApellido.setEnabled(true);
            etSegundoApellido.setEnabled(true);
        }
    }

    public void OBTENERDatos() {
        Id_Operador = etId_Operador.getText().toString();
        Primer_Nombre = etPrimerNombre.getText().toString().toUpperCase();
        Segundo_Nombre = etSegundoNombre.getText().toString().toUpperCase();
        Primer_Apellido = etPrimerApellido.getText().toString().toUpperCase();
        Segundo_Apellido = etSegundoApellido.getText().toString().toUpperCase();
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

    public void CARGAR_GRUPOS() {
        Crud_Grupo DATOS;
        DATOS = Crud_Grupo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Grupo = DATOS.CONSULTA_GENERAL_GRUPO();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Grupo.moveToFirst() == false) {
            //el cursor está vacío
            ListaGrupo = new ArrayList<Grupo>();
            Tdo_Grupo = "";
            Id_Grupo = "";
            Id_Grupo_Temporal = "";
            anterior = 0;
            siguiente = 0;
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
            Grupo grupo = new Grupo(Tdo_Grupo, Id_Grupo, Nombre_Grupo, Pin_Seguridad, ischeckFilas, ischeckClientes, ischeckCantidadUR, ischeckColorAlertas, ischeckTipoAlertas, ischeckRacks, ischeckEquipos, ischeckAlertas, ischeckPasillos, ischeckChillers, ischeckAreas, ischeckGrupos, ischeckVersion, ischeckParametros);
            ListaGrupo.add(grupo);
        }
        Tdo_Grupo = ListaGrupo.get(0).getID();
        Id_Grupo = ListaGrupo.get(0).getID_GRUPO();
        Nombre_Grupo = ListaGrupo.get(0).getNOMBRE_GRUPO();
        Pin_Seguridad = ListaGrupo.get(0).getPIN_SEGURIDAD();
        ischeckFilas = ListaGrupo.get(0).getPERMISO_FILA();
        ischeckClientes = ListaGrupo.get(0).getPERMISO_CLIENTE();
        ischeckCantidadUR = ListaGrupo.get(0).getPERMISO_CANTIDAD_UR();
        ischeckRacks = ListaGrupo.get(0).getPERMISO_RACK();
        ischeckEquipos = ListaGrupo.get(0).getPERMISO_CLIENTE();
        ischeckAlertas = ListaGrupo.get(0).getPERMISO_ALERTA();
        ischeckTipoAlertas = ListaGrupo.get(0).getPERMISO_TIPO_ALERTA();
        ischeckColorAlertas = ListaGrupo.get(0).getPERMISO_COLOR_ALERTA();
        ischeckPasillos = ListaGrupo.get(0).getPERMISO_PASILLO();
        ischeckChillers = ListaGrupo.get(0).getPERMISO_CHILLER();
        ischeckAreas = ListaGrupo.get(0).getPERMISO_AREA();
        ischeckGrupos = ListaGrupo.get(0).getPERMISO_GRUPO();
        ischeckVersion = ListaGrupo.get(0).getPERMISO_VERSION();
        ischeckParametros = ListaGrupo.get(0).getPERMISO_PARAMETROS();
        ArrayAdapter<Grupo> Adapter = new ArrayAdapter<Grupo>(this, android.R.layout.simple_spinner_dropdown_item, ListaGrupo);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msGrupo.setAdapter(Adapter);
    }

}