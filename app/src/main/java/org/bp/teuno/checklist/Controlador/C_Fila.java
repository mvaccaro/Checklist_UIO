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

import org.bp.teuno.checklist.Modelo.Fila;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Fila;
import org.bp.teuno.checklist.UI.Crud_Fila;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Fila extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Fila, etNombre_Fila;
    TextInputLayout tilId_Fila, tilNombre_Fila;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Fila Datos;
    String Tdo_Fila, Id_Fila, Nombre_Fila, Id_Fila_Temporal = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    int Bandera = 0, anterior = 0, siguiente = 0;
    Cursor T_Fila;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    ArrayList<Fila> ListaFila = new ArrayList<Fila>();
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_fila);

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


        etId_Fila = (EditText) findViewById(R.id.etId_Fila);
        etNombre_Fila = (EditText) findViewById(R.id.etNombre_Fila);
        tilId_Fila = (TextInputLayout) findViewById(R.id.tilId_Fila);
        tilNombre_Fila = (TextInputLayout) findViewById(R.id.tilNombre_Fila);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etId_Fila.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Fila.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Fila.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Fila.setError(null);
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
                ListaFila = new ArrayList<Fila>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Fila.matches("")) {
                    etId_Fila.setError("Ingrese el ID de la fila");
                    etId_Fila.requestFocus();
                    return;
                }
                if (Nombre_Fila.matches("") && !Id_Fila.equals("-1")) {
                    etNombre_Fila.setError("Ingrese el nombre de la fila");
                    etNombre_Fila.requestFocus();
                    return;
                }
                if (Existe_Fila() == true && Bandera == 0) {
                    etId_Fila.setError("Ya existe una fila registrada con este ID, elija otro");
                    etId_Fila.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Fila.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Fila.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    long insertedResult = Datos.INSERTAR_FILA(new Fila(null, Id_Fila, Nombre_Fila, "A", Id_Operador, "", getDateTime(), ""));
                                                    if (insertedResult == -1) {
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                                                    }
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Fila.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    long updatedResult = Datos.MODIFICAR_FILA(new Fila(Tdo_Fila, Id_Fila, Nombre_Fila, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                String nombre_fila = "";
                if (!Nombre_Fila.equals("")) {
                    nombre_fila = "%" + Nombre_Fila + "%";
                } else {
                    nombre_fila = "";
                }
                Datos = Crud_Fila.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Fila = Datos.CONSULTA_GENERAL_FILA_POR_VALORES(Id_Fila, nombre_fila);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Fila.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaFila = new ArrayList<Fila>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Fila = T_Fila.getColumnIndex(IT_Fila.I_FILA.ID);
                int P_ID_FILA = T_Fila.getColumnIndex(IT_Fila.I_FILA.ID_FILA);
                int P_NOMBRE_FILA = T_Fila.getColumnIndex(IT_Fila.I_FILA.NOMBRE_FILA);
                int P_USUARIO_INGRESA = T_Fila.getColumnIndex(IT_Fila.I_FILA.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Fila.getColumnIndex(IT_Fila.I_FILA.FECHA_INGRESO);
                ListaFila = new ArrayList<Fila>();
                for (T_Fila.moveToFirst(); !T_Fila.isAfterLast(); T_Fila.moveToNext()) {
                    Tdo_Fila = T_Fila.getString(P_Tdo_Fila);
                    Id_Fila = T_Fila.getString(P_ID_FILA);
                    Nombre_Fila = T_Fila.getString(P_NOMBRE_FILA);
                    Usuario_Ingresa = T_Fila.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Fila.getString(P_FECHA_INGRESO);
                    Fila fila = new Fila(Tdo_Fila, Id_Fila, Nombre_Fila, Usuario_Ingresa, Fecha_Ingreso);
                    ListaFila.add(fila);
                }
                LIMPIARDATOS();
                Tdo_Fila = ListaFila.get(0).getID();
                Id_Fila = ListaFila.get(0).getID_FILA();
                Nombre_Fila = ListaFila.get(0).getNOMBRE_FILA();
                Usuario_Ingresa = ListaFila.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaFila.get(0).getFECHA_INGRESO();
                Id_Fila_Temporal = Id_Fila;
                etId_Fila.setText(Id_Fila);
                etNombre_Fila.setText(Nombre_Fila);
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Fila.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Fila.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            long updatedResult = Datos.DESACTIVAR_FILA(new Fila(Tdo_Fila, Id_Fila, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                if (ListaFila.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Fila = ListaFila.get(anterior).ID;
                Id_Fila = ListaFila.get(anterior).ID_FILA;
                Id_Fila_Temporal = Id_Fila;
                Nombre_Fila = ListaFila.get(anterior).NOMBRE_FILA;
                etId_Fila.setText(Id_Fila);
                etNombre_Fila.setText(Nombre_Fila);
                Usuario_Ingresa = ListaFila.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaFila.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaFila.size() == 0) {
                    return;
                }
                if (siguiente == ListaFila.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Fila = ListaFila.get(siguiente).ID;
                Id_Fila = ListaFila.get(siguiente).ID_FILA;
                Id_Fila_Temporal = Id_Fila;
                Nombre_Fila = ListaFila.get(siguiente).NOMBRE_FILA;
                etId_Fila.setText(Id_Fila);
                etNombre_Fila.setText(Nombre_Fila);
                Usuario_Ingresa = ListaFila.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaFila.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Fila.this, C_Menu_Principal.class);
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

    public boolean Existe_Fila() {
        Datos = Crud_Fila.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Fila = Datos.CONSULTA_GENERAL_FILA_POR_ID(Id_Fila);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Fila.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Fila = "";
        Id_Fila = "";
        Id_Fila_Temporal = "";
        anterior = 0;
        siguiente = 0;
        etId_Fila.setText("");
        etNombre_Fila.setText("");
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            etId_Fila.setEnabled(true);
            etId_Fila.requestFocus();
            etNombre_Fila.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Fila.setEnabled(true);
            etId_Fila.requestFocus();
            etNombre_Fila.setEnabled(true);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaFila.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Fila.setEnabled(true);
            etId_Fila.requestFocus();
            etNombre_Fila.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Fila.setEnabled(false);
            etId_Fila.setText(Id_Fila_Temporal);
            etNombre_Fila.requestFocus();
            etNombre_Fila.setEnabled(true);
        }
    }

    public void Obtener_Datos() {
        Id_Fila = etId_Fila.getText().toString();
        Nombre_Fila = etNombre_Fila.getText().toString().toUpperCase();
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