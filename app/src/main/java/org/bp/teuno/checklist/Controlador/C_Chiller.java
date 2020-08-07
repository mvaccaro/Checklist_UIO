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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.bp.teuno.checklist.Modelo.Chiller;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Chiller;
import org.bp.teuno.checklist.UI.Crud_Chiller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Chiller extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Chiller, etNombre_Chiller, etMarca, etModelo, etSerie;
    TextInputLayout tilId_Chiller, tilNombre_Chiller, tilMarca, tilModelo, tilSerie;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Chiller Datos;
    String Tdo_Chiller, Id_Chiller, Nombre_Chiller, Id_Chiller_Temporal = "", marca = "", modelo = "", serie = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    int Bandera = 0, anterior = 0, siguiente = 0;
    Cursor T_Chiller;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    ArrayList<Chiller> ListaChiller = new ArrayList<Chiller>();
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_chiller);

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


        etId_Chiller = (EditText) findViewById(R.id.etId_Chiller);
        etNombre_Chiller = (EditText) findViewById(R.id.etNombre_Chiller);
        etMarca = (EditText) findViewById(R.id.etMarca);
        etModelo = (EditText) findViewById(R.id.etModelo);
        etSerie = (EditText) findViewById(R.id.etSerie);

        tilId_Chiller = (TextInputLayout) findViewById(R.id.tilId_Chiller);
        tilNombre_Chiller = (TextInputLayout) findViewById(R.id.tilNombre_Chiller);
        tilMarca = (TextInputLayout) findViewById(R.id.tilMarca);
        tilModelo = (TextInputLayout) findViewById(R.id.tilModelo);
        tilSerie = (TextInputLayout) findViewById(R.id.tilSerie);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etId_Chiller.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Chiller.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Chiller.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Chiller.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etMarca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilMarca.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etModelo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilModelo.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSerie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilSerie.setError(null);
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
                ListaChiller = new ArrayList<Chiller>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Chiller.matches("")) {
                    etId_Chiller.setError("Ingrese el ID de la chiller");
                    etId_Chiller.requestFocus();
                    return;
                }
                if (Nombre_Chiller.matches("") && !Id_Chiller.equals("-1")) {
                    etNombre_Chiller.setError("Ingrese el nombre de la chiller");
                    etNombre_Chiller.requestFocus();
                    return;
                }
                if (Existe_Chiller() == true && Bandera == 0) {
                    etId_Chiller.setError("Ya existe una chiller registrada con este ID, elija otro");
                    etId_Chiller.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Chiller.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Chiller.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_CHILLER(new Chiller(null, Id_Chiller, Nombre_Chiller, marca, modelo, serie, "A", Id_Operador, "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Chiller.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.MODIFICAR_CHILLER(new Chiller(Tdo_Chiller, Id_Chiller, Nombre_Chiller, marca, modelo, serie, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                String nombre_chiller = "";
                if (!Nombre_Chiller.equals("")) {
                    nombre_chiller = "%" + Nombre_Chiller + "%";
                } else {
                    nombre_chiller = "";
                }
                Datos = Crud_Chiller.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Chiller = Datos.CONSULTA_GENERAL_CHILLER_POR_VALORES(Id_Chiller, nombre_chiller);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Chiller.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaChiller = new ArrayList<Chiller>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Chiller = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.ID);
                int P_ID_CHILLER = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.ID_CHILLER);
                int P_NOMBRE_CHILLER = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.NOMBRE_CHILLER);
                int P_MARCA = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.MARCA);
                int P_MODELO = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.MODELO);
                int P_SERIE = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.SERIE);
                int P_USUARIO_INGRESA = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Chiller.getColumnIndex(IT_Chiller.I_CHILLER.FECHA_INGRESO);
                ListaChiller = new ArrayList<Chiller>();
                for (T_Chiller.moveToFirst(); !T_Chiller.isAfterLast(); T_Chiller.moveToNext()) {
                    Tdo_Chiller = T_Chiller.getString(P_Tdo_Chiller);
                    Id_Chiller = T_Chiller.getString(P_ID_CHILLER);
                    Nombre_Chiller = T_Chiller.getString(P_NOMBRE_CHILLER);
                    marca = T_Chiller.getString(P_MARCA);
                    modelo = T_Chiller.getString(P_MODELO);
                    serie = T_Chiller.getString(P_SERIE);
                    Usuario_Ingresa = T_Chiller.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Chiller.getString(P_FECHA_INGRESO);
                    Chiller chiller = new Chiller(Tdo_Chiller, Id_Chiller, Nombre_Chiller, marca, modelo, serie, Usuario_Ingresa, Fecha_Ingreso);
                    ListaChiller.add(chiller);
                }
                LIMPIARDATOS();
                Tdo_Chiller = ListaChiller.get(0).getID();
                Id_Chiller = ListaChiller.get(0).getID_CHILLER();
                Nombre_Chiller = ListaChiller.get(0).getNOMBRE_CHILLER();
                marca = ListaChiller.get(0).getMARCA();
                modelo = ListaChiller.get(0).getMODELO();
                serie = ListaChiller.get(0).getSERIE();
                Id_Chiller_Temporal = Id_Chiller;
                etId_Chiller.setText(Id_Chiller);
                etNombre_Chiller.setText(Nombre_Chiller);
                etMarca.setText(marca);
                etModelo.setText(modelo);
                etSerie.setText(serie);
                Usuario_Ingresa = ListaChiller.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaChiller.get(0).getFECHA_INGRESO();
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Chiller.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Chiller.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            Datos.DESACTIVAR_CHILLER(new Chiller(Tdo_Chiller, Id_Chiller, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                if (ListaChiller.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Chiller = ListaChiller.get(anterior).ID;
                Id_Chiller = ListaChiller.get(anterior).ID_CHILLER;
                Id_Chiller_Temporal = Id_Chiller;
                Nombre_Chiller = ListaChiller.get(anterior).NOMBRE_CHILLER;
                etId_Chiller.setText(Id_Chiller);
                etNombre_Chiller.setText(Nombre_Chiller);
                marca = ListaChiller.get(anterior).getMARCA();
                modelo = ListaChiller.get(anterior).getMODELO();
                serie = ListaChiller.get(anterior).getSERIE();
                etMarca.setText(marca);
                etModelo.setText(modelo);
                etSerie.setText(serie);
                Usuario_Ingresa = ListaChiller.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaChiller.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaChiller.size() == 0) {
                    return;
                }
                if (siguiente == ListaChiller.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Chiller = ListaChiller.get(siguiente).ID;
                Id_Chiller = ListaChiller.get(siguiente).ID_CHILLER;
                Id_Chiller_Temporal = Id_Chiller;
                Nombre_Chiller = ListaChiller.get(siguiente).NOMBRE_CHILLER;
                etId_Chiller.setText(Id_Chiller);
                etNombre_Chiller.setText(Nombre_Chiller);
                marca = ListaChiller.get(siguiente).getMARCA();
                modelo = ListaChiller.get(siguiente).getMODELO();
                serie = ListaChiller.get(siguiente).getSERIE();
                etMarca.setText(marca);
                etModelo.setText(modelo);
                etSerie.setText(serie);
                Usuario_Ingresa = ListaChiller.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaChiller.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Chiller.this, C_Menu_Principal.class);
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

    public boolean Existe_Chiller() {
        Datos = Crud_Chiller.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Chiller = Datos.CONSULTA_GENERAL_CHILLER_POR_ID(Id_Chiller);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Chiller.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Chiller = "";
        Id_Chiller = "";
        Id_Chiller_Temporal = "";
        anterior = 0;
        siguiente = 0;
        etId_Chiller.setText("");
        etNombre_Chiller.setText("");
        etMarca.setText("");
        etModelo.setText("");
        etSerie.setText("");
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            etId_Chiller.setEnabled(true);
            etId_Chiller.requestFocus();
            etNombre_Chiller.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Chiller.setEnabled(true);
            etId_Chiller.requestFocus();
            etNombre_Chiller.setEnabled(true);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaChiller.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Chiller.setEnabled(true);
            etId_Chiller.requestFocus();
            etNombre_Chiller.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Chiller.setEnabled(false);
            etId_Chiller.setText(Id_Chiller_Temporal);
            etNombre_Chiller.requestFocus();
            etNombre_Chiller.setEnabled(true);
        }
    }

    public void Obtener_Datos() {
        marca = etMarca.getText().toString().toUpperCase();
        modelo = etModelo.getText().toString().toUpperCase();
        serie = etSerie.getText().toString().toUpperCase();
        Id_Chiller = etId_Chiller.getText().toString();
        Nombre_Chiller = etNombre_Chiller.getText().toString().toUpperCase();
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