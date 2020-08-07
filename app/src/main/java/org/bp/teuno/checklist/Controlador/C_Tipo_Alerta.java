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

import org.bp.teuno.checklist.Modelo.Tipo_Alerta;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Tipo_Alerta;
import org.bp.teuno.checklist.UI.Crud_Tipo_Alerta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Tipo_Alerta extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Tipo_Alerta, etNombre_Tipo_Alerta;
    TextInputLayout tilId_Tipo_Alerta, tilNombre_Tipo_Alerta;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Tipo_Alerta Datos;
    String Tdo_Tipo_Alerta, Id_Tipo_Alerta, Nombre_Tipo_Alerta, Id_Tipo_Alerta_Temporal = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    int Bandera = 0, anterior = 0, siguiente = 0;
    Cursor T_Tipo_Alerta;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    ArrayList<Tipo_Alerta> ListaTipo_Alerta = new ArrayList<Tipo_Alerta>();
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_tipo_alerta);

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
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();


        etId_Tipo_Alerta = (EditText) findViewById(R.id.etId_Tipo_Alerta);
        etNombre_Tipo_Alerta = (EditText) findViewById(R.id.etNombre_Tipo_Alerta);
        tilId_Tipo_Alerta = (TextInputLayout) findViewById(R.id.tilId_Tipo_Alerta);
        tilNombre_Tipo_Alerta = (TextInputLayout) findViewById(R.id.tilNombre_Tipo_Alerta);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etId_Tipo_Alerta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Tipo_Alerta.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Tipo_Alerta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Tipo_Alerta.setError(null);
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
                ListaTipo_Alerta = new ArrayList<Tipo_Alerta>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Tipo_Alerta.matches("")) {
                    etId_Tipo_Alerta.setError("Ingrese el ID del tipo de alerta");
                    etId_Tipo_Alerta.requestFocus();
                    return;
                }
                if (Nombre_Tipo_Alerta.matches("") && !Id_Tipo_Alerta.equals("-1")) {
                    etNombre_Tipo_Alerta.setError("Ingrese el nombre del tipo de alerta");
                    etNombre_Tipo_Alerta.requestFocus();
                    return;
                }
                if (Existe_Tipo_Alerta() == true && Bandera == 0) {
                    etId_Tipo_Alerta.setError("Ya existe un tipo de alerta registrado con este ID, elija otro");
                    etId_Tipo_Alerta.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Tipo_Alerta.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Tipo_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    long insertedResult = Datos.INSERTAR_TIPO_ALERTA(new Tipo_Alerta(null, Id_Tipo_Alerta, Nombre_Tipo_Alerta, "A", Id_Operador, "", getDateTime(), ""));
                                                    if (insertedResult == -1) {
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                                                    }
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Tipo_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    long updatedResult = Datos.MODIFICAR_TIPO_ALERTA(new Tipo_Alerta(Tdo_Tipo_Alerta, Id_Tipo_Alerta, Nombre_Tipo_Alerta, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                                    if (updatedResult == -1) {
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
                String nombre_tipo_alerta = "";
                if (!Nombre_Tipo_Alerta.equals("")) {
                    nombre_tipo_alerta = "%" + Nombre_Tipo_Alerta + "%";
                } else {
                    nombre_tipo_alerta = "";
                }
                Datos = Crud_Tipo_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Tipo_Alerta = Datos.CONSULTA_GENERAL_TIPO_ALERTA_POR_VALORES(Id_Tipo_Alerta, nombre_tipo_alerta);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Tipo_Alerta.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaTipo_Alerta = new ArrayList<Tipo_Alerta>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Tipo_Alerta = T_Tipo_Alerta.getColumnIndex(IT_Tipo_Alerta.I_TIPO_ALERTA.ID);
                int P_ID_TIPO_ALERTA = T_Tipo_Alerta.getColumnIndex(IT_Tipo_Alerta.I_TIPO_ALERTA.ID_TIPO_ALERTA);
                int P_NOMBRE_TIPO_ALERTA = T_Tipo_Alerta.getColumnIndex(IT_Tipo_Alerta.I_TIPO_ALERTA.NOMBRE_TIPO_ALERTA);
                int P_USUARIO_INGRESA = T_Tipo_Alerta.getColumnIndex(IT_Tipo_Alerta.I_TIPO_ALERTA.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Tipo_Alerta.getColumnIndex(IT_Tipo_Alerta.I_TIPO_ALERTA.FECHA_INGRESO);
                ListaTipo_Alerta = new ArrayList<Tipo_Alerta>();
                for (T_Tipo_Alerta.moveToFirst(); !T_Tipo_Alerta.isAfterLast(); T_Tipo_Alerta.moveToNext()) {
                    Tdo_Tipo_Alerta = T_Tipo_Alerta.getString(P_Tdo_Tipo_Alerta);
                    Id_Tipo_Alerta = T_Tipo_Alerta.getString(P_ID_TIPO_ALERTA);
                    Nombre_Tipo_Alerta = T_Tipo_Alerta.getString(P_NOMBRE_TIPO_ALERTA);
                    Usuario_Ingresa = T_Tipo_Alerta.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Tipo_Alerta.getString(P_FECHA_INGRESO);
                    Tipo_Alerta tipo_alerta = new Tipo_Alerta(Tdo_Tipo_Alerta, Id_Tipo_Alerta, Nombre_Tipo_Alerta, Usuario_Ingresa, Fecha_Ingreso);
                    ListaTipo_Alerta.add(tipo_alerta);
                }
                LIMPIARDATOS();
                Tdo_Tipo_Alerta = ListaTipo_Alerta.get(0).getID();
                Id_Tipo_Alerta = ListaTipo_Alerta.get(0).getID_TIPO_ALERTA();
                Nombre_Tipo_Alerta = ListaTipo_Alerta.get(0).getNOMBRE_TIPO_ALERTA();
                Id_Tipo_Alerta_Temporal = Id_Tipo_Alerta;
                etId_Tipo_Alerta.setText(Id_Tipo_Alerta);
                etNombre_Tipo_Alerta.setText(Nombre_Tipo_Alerta);
                Usuario_Ingresa = ListaTipo_Alerta.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaTipo_Alerta.get(0).getFECHA_INGRESO();
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Tipo_Alerta.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Tipo_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            long updatedResult = Datos.DESACTIVAR_TIPO_ALERTA(new Tipo_Alerta(Tdo_Tipo_Alerta, Id_Tipo_Alerta, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                            if (updatedResult == -1) {
                                                throw new SQLException(String.format("Se generó un error al desactivar el registro en la base de datos"));
                                            }
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
                if (ListaTipo_Alerta.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Tipo_Alerta = ListaTipo_Alerta.get(anterior).ID;
                Id_Tipo_Alerta = ListaTipo_Alerta.get(anterior).ID_TIPO_ALERTA;
                Id_Tipo_Alerta_Temporal = Id_Tipo_Alerta;
                Nombre_Tipo_Alerta = ListaTipo_Alerta.get(anterior).NOMBRE_TIPO_ALERTA;
                etId_Tipo_Alerta.setText(Id_Tipo_Alerta);
                etNombre_Tipo_Alerta.setText(Nombre_Tipo_Alerta);
                Usuario_Ingresa = ListaTipo_Alerta.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaTipo_Alerta.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaTipo_Alerta.size() == 0) {
                    return;
                }
                if (siguiente == ListaTipo_Alerta.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Tipo_Alerta = ListaTipo_Alerta.get(siguiente).ID;
                Id_Tipo_Alerta = ListaTipo_Alerta.get(siguiente).ID_TIPO_ALERTA;
                Id_Tipo_Alerta_Temporal = Id_Tipo_Alerta;
                Nombre_Tipo_Alerta = ListaTipo_Alerta.get(siguiente).NOMBRE_TIPO_ALERTA;
                etId_Tipo_Alerta.setText(Id_Tipo_Alerta);
                etNombre_Tipo_Alerta.setText(Nombre_Tipo_Alerta);
                Usuario_Ingresa = ListaTipo_Alerta.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaTipo_Alerta.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Tipo_Alerta.this, C_Menu_Principal.class);
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

    public boolean Existe_Tipo_Alerta() {
        Datos = Crud_Tipo_Alerta.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Tipo_Alerta = Datos.CONSULTA_GENERAL_TIPO_ALERTA_POR_ID(Id_Tipo_Alerta);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Tipo_Alerta.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Tipo_Alerta = "";
        Id_Tipo_Alerta = "";
        Id_Tipo_Alerta_Temporal = "";
        anterior = 0;
        siguiente = 0;
        etId_Tipo_Alerta.setText("");
        etNombre_Tipo_Alerta.setText("");
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            etId_Tipo_Alerta.setEnabled(true);
            etId_Tipo_Alerta.requestFocus();
            etNombre_Tipo_Alerta.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Tipo_Alerta.setEnabled(true);
            etId_Tipo_Alerta.requestFocus();
            etNombre_Tipo_Alerta.setEnabled(true);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaTipo_Alerta.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Tipo_Alerta.setEnabled(true);
            etId_Tipo_Alerta.requestFocus();
            etNombre_Tipo_Alerta.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Tipo_Alerta.setEnabled(false);
            etId_Tipo_Alerta.setText(Id_Tipo_Alerta_Temporal);
            etNombre_Tipo_Alerta.requestFocus();
            etNombre_Tipo_Alerta.setEnabled(true);
        }
    }

    public void Obtener_Datos() {
        Id_Tipo_Alerta = etId_Tipo_Alerta.getText().toString();
        Nombre_Tipo_Alerta = etNombre_Tipo_Alerta.getText().toString().toUpperCase();
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