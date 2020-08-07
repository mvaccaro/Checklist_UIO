package org.bp.teuno.checklist.Controlador;

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
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.bp.teuno.checklist.Modelo.Version;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Version;
import org.bp.teuno.checklist.UI.Crud_Version;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Version extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Version, etNombre_Version, etMensaje, etColaboradores;
    TextInputLayout tilId_Version, tilNombre_Version, tilMensaje, tilColaboradores;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Version Datos;
    String Tdo_Version, Id_Version, Nombre_Version, Id_Version_Temporal = "", Usuario_Ingresa = "", Fecha_Ingreso = "", mensaje = "", colaboradores = "";
    int Bandera = 0, anterior = 0, siguiente = 0;
    Cursor T_Version;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    ArrayList<Version> ListaVersion = new ArrayList<Version>();
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_version);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabNuevo = (FloatingActionButton) findViewById(R.id.fabNuevo);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabGuardar.setEnabled(false);
        fabBuscar = (FloatingActionButton) findViewById(R.id.fabBuscar);
        fabModificar = (FloatingActionButton) findViewById(R.id.fabModificar);
        fabLimpiar = (FloatingActionButton) findViewById(R.id.fabLimpiar);
        fabModificar.setEnabled(true);
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
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();


        etId_Version = (EditText) findViewById(R.id.etId_Version);
        etNombre_Version = (EditText) findViewById(R.id.etNombre_Version);
        etMensaje = (EditText) findViewById(R.id.etMensaje);
        etColaboradores = (EditText) findViewById(R.id.etColaboradores);
        tilId_Version = (TextInputLayout) findViewById(R.id.tilId_Version);
        tilNombre_Version = (TextInputLayout) findViewById(R.id.tilNombre_Version);
        tilMensaje = (TextInputLayout) findViewById(R.id.tilMensaje);
        tilColaboradores = (TextInputLayout) findViewById(R.id.tilColaboradores);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etId_Version.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Version.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Version.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Version.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etMensaje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilMensaje.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etColaboradores.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilColaboradores.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        MOSTRAR_DATOS();

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
                ListaVersion = new ArrayList<Version>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Version.matches("")) {
                    etId_Version.setError("Ingrese el ID de la versión");
                    etId_Version.requestFocus();
                    return;
                }
                if (Nombre_Version.matches("") && !Id_Version.equals("-1")) {
                    etNombre_Version.setError("Ingrese el nombre de la versión");
                    etNombre_Version.requestFocus();
                    return;
                }
                if (Existe_Version() == true && Bandera == 1) {
                    etNombre_Version.setError("Ya existe una versión registrada con este nombre, elija otro");
                    etNombre_Version.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Version.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Version.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_VERSION(new Version(null, Id_Version, Nombre_Version, mensaje, colaboradores, "A", Id_Operador, "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Version.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    long updatedResult = Datos.DESACTIVAR_VERSION_ANTERIOR(new Version(Tdo_Version, Id_Version, Nombre_Version, mensaje, colaboradores, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                                    if (updatedResult == -1) {
                                                        throw new SQLException(String.format("Se generó un error al desactivar el registro en la base de datos"));
                                                    }
                                                    long insertedResult = Datos.INSERTAR_VERSION(new Version(null, Id_Version, Nombre_Version, mensaje, colaboradores, "A", Id_Operador, "", getDateTime(), ""));
                                                    if (insertedResult == -1) {
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                                                    }
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            Toast.makeText(getApplicationContext(), "El registro se guardó correctamente", Toast.LENGTH_LONG).show();
                                            LIMPIARDATOS();
                                            ACTIVARDATOS(1);
                                            MOSTRAR_DATOS();
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
                Obtener_Datos();
                String nombre_version = "";
                if (!Nombre_Version.equals("")) {
                    nombre_version = "%" + Nombre_Version + "%";
                } else {
                    nombre_version = "";
                }
                Datos = Crud_Version.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Version = Datos.CONSULTA_GENERAL_VERSION_POR_VALORES(Id_Version, nombre_version);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Version.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaVersion = new ArrayList<Version>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Version = T_Version.getColumnIndex(IT_Version.I_VERSION.ID);
                int P_ID_VERSION = T_Version.getColumnIndex(IT_Version.I_VERSION.ID_VERSION);
                int P_NOMBRE_VERSION = T_Version.getColumnIndex(IT_Version.I_VERSION.NOMBRE_VERSION);
                int P_MENSAJE = T_Version.getColumnIndex(IT_Version.I_VERSION.MENSAJE);
                int P_COLABORADORES = T_Version.getColumnIndex(IT_Version.I_VERSION.COLABORADORES);
                int P_USUARIO_INGRESA = T_Version.getColumnIndex(IT_Version.I_VERSION.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Version.getColumnIndex(IT_Version.I_VERSION.FECHA_INGRESO);
                ListaVersion = new ArrayList<Version>();
                for (T_Version.moveToFirst(); !T_Version.isAfterLast(); T_Version.moveToNext()) {
                    Tdo_Version = T_Version.getString(P_Tdo_Version);
                    Id_Version = T_Version.getString(P_ID_VERSION);
                    Nombre_Version = T_Version.getString(P_NOMBRE_VERSION);
                    Usuario_Ingresa = T_Version.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Version.getString(P_FECHA_INGRESO);
                    mensaje = T_Version.getString(P_MENSAJE);
                    colaboradores = T_Version.getString(P_COLABORADORES);
                    Version version = new Version(Tdo_Version, Id_Version, Nombre_Version, mensaje, colaboradores, Usuario_Ingresa, Fecha_Ingreso);
                    ListaVersion.add(version);
                }
                LIMPIARDATOS();
                Tdo_Version = ListaVersion.get(0).getID();
                Id_Version = ListaVersion.get(0).getID_VERSION();
                Nombre_Version = ListaVersion.get(0).getNOMBRE_VERSION();
                Usuario_Ingresa = ListaVersion.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaVersion.get(0).getFECHA_INGRESO();
                mensaje = ListaVersion.get(0).getMENSAJE();
                colaboradores = ListaVersion.get(0).getCOLABORADORES();
                Id_Version_Temporal = Id_Version;
                etId_Version.setText(Id_Version);
                etNombre_Version.setText(Nombre_Version);
                etMensaje.setText(mensaje);
                etColaboradores.setText(colaboradores);
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Version.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Version.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            Datos.DESACTIVAR_VERSION(new Version(Tdo_Version, Id_Version, "I", "", Id_Operador, "", getDateTime()));
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
                if (ListaVersion.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Version = ListaVersion.get(anterior).ID;
                Id_Version = ListaVersion.get(anterior).ID_VERSION;
                Id_Version_Temporal = Id_Version;
                Nombre_Version = ListaVersion.get(anterior).NOMBRE_VERSION;
                etId_Version.setText(Id_Version);
                etNombre_Version.setText(Nombre_Version);
                Usuario_Ingresa = ListaVersion.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaVersion.get(anterior).getFECHA_INGRESO();
                mensaje = ListaVersion.get(anterior).getMENSAJE();
                colaboradores = ListaVersion.get(anterior).getCOLABORADORES();
                etMensaje.setText(mensaje);
                etColaboradores.setText(colaboradores);
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaVersion.size() == 0) {
                    return;
                }
                if (siguiente == ListaVersion.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Version = ListaVersion.get(siguiente).ID;
                Id_Version = ListaVersion.get(siguiente).ID_VERSION;
                Id_Version_Temporal = Id_Version;
                Nombre_Version = ListaVersion.get(siguiente).NOMBRE_VERSION;
                etId_Version.setText(Id_Version);
                etNombre_Version.setText(Nombre_Version);
                Usuario_Ingresa = ListaVersion.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaVersion.get(siguiente).getFECHA_INGRESO();
                mensaje = ListaVersion.get(siguiente).getMENSAJE();
                colaboradores = ListaVersion.get(siguiente).getCOLABORADORES();
                etMensaje.setText(mensaje);
                etColaboradores.setText(colaboradores);
            }
        });
    }

    public void MOSTRAR_DATOS() {
        Datos = Crud_Version.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Version = Datos.CONSULTA_GENERAL_VERSION();
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Version.moveToFirst() == false) {
            //el cursor está vacío
            Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
            return;
        }
        int P_Tdo_Version = T_Version.getColumnIndex(IT_Version.I_VERSION.ID);
        int P_ID_VERSION = T_Version.getColumnIndex(IT_Version.I_VERSION.ID_VERSION);
        int P_NOMBRE_VERSION = T_Version.getColumnIndex(IT_Version.I_VERSION.NOMBRE_VERSION);
        int P_MENSAJE = T_Version.getColumnIndex(IT_Version.I_VERSION.MENSAJE);
        int P_COLABORADORES = T_Version.getColumnIndex(IT_Version.I_VERSION.COLABORADORES);
        int P_USUARIO_INGRESA = T_Version.getColumnIndex(IT_Version.I_VERSION.USUARIO_INGRESA);
        int P_FECHA_INGRESO = T_Version.getColumnIndex(IT_Version.I_VERSION.FECHA_INGRESO);
        ListaVersion = new ArrayList<Version>();
        for (T_Version.moveToFirst(); !T_Version.isAfterLast(); T_Version.moveToNext()) {
            Tdo_Version = T_Version.getString(P_Tdo_Version);
            Id_Version = T_Version.getString(P_ID_VERSION);
            Nombre_Version = T_Version.getString(P_NOMBRE_VERSION);
            Usuario_Ingresa = T_Version.getString(P_USUARIO_INGRESA);
            Fecha_Ingreso = T_Version.getString(P_FECHA_INGRESO);
            mensaje = T_Version.getString(P_MENSAJE);
            colaboradores = T_Version.getString(P_COLABORADORES);
            Version version = new Version(Tdo_Version, Id_Version, Nombre_Version, mensaje, colaboradores, Usuario_Ingresa, Fecha_Ingreso);
        }
        Id_Version_Temporal = Id_Version;
        etId_Version.setText(Id_Version);
        etNombre_Version.setText(Nombre_Version);
        etMensaje.setText(mensaje);
        etColaboradores.setText(colaboradores);
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
                Intent intent = new Intent(C_Version.this, C_Menu_Principal.class);
                intent.putExtra("Id_Operador", Id_Operador);
                intent.putExtra("Nombre_Operador", Nombre_Operador);
                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                intent.putExtra("Turno_Operador", Turno_Operador);
                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
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

    public boolean Existe_Version() {
        Datos = Crud_Version.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Version = Datos.CONSULTA_GENERAL_VERSION_POR_NOMBRE(Nombre_Version);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Version.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Bandera = 0;
        Tdo_Version = "";
        Id_Version = "";
        Id_Version_Temporal = "";
        anterior = 0;
        siguiente = 0;
        etId_Version.setText("");
        etNombre_Version.setText("");
        etMensaje.setText("");
        etColaboradores.setText("");
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            etId_Version.setEnabled(true);
            etId_Version.requestFocus();
            etNombre_Version.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Version.setEnabled(false);
            etId_Version.requestFocus();
            etNombre_Version.setEnabled(false);
            etMensaje.setEnabled(false);
            etColaboradores.setEnabled(false);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaVersion.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Version.setEnabled(true);
            etId_Version.requestFocus();
            etNombre_Version.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Version.setEnabled(true);
            etId_Version.requestFocus();
            etNombre_Version.setEnabled(true);
            etMensaje.setEnabled(true);
            etColaboradores.setEnabled(true);
        }
    }

    public void Obtener_Datos() {
        Id_Version = etId_Version.getText().toString();
        Nombre_Version = etNombre_Version.getText().toString().toUpperCase();
        mensaje = etMensaje.getText().toString();
        colaboradores = etColaboradores.getText().toString();
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