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

import org.bp.teuno.checklist.Modelo.Color_Alerta;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Color_Alerta;
import org.bp.teuno.checklist.UI.Crud_Color_Alerta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Color_Alerta extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Color_Alerta, etNombre_Color_Alerta;
    TextInputLayout tilId_Color_Alerta, tilNombre_Color_Alerta;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Color_Alerta Datos;
    String Tdo_Color_Alerta, Id_Color_Alerta, Nombre_Color_Alerta, Id_Color_Alerta_Temporal = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    int Bandera = 0, anterior = 0, siguiente = 0;
    Cursor T_Color_Alerta;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    ArrayList<Color_Alerta> ListaColor_Alerta = new ArrayList<Color_Alerta>();
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_color_alerta);

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


        etId_Color_Alerta = (EditText) findViewById(R.id.etId_Color_Alerta);
        etNombre_Color_Alerta = (EditText) findViewById(R.id.etNombre_Color_Alerta);
        tilId_Color_Alerta = (TextInputLayout) findViewById(R.id.tilId_Color_Alerta);
        tilNombre_Color_Alerta = (TextInputLayout) findViewById(R.id.tilNombre_Color_Alerta);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etId_Color_Alerta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Color_Alerta.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Color_Alerta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Color_Alerta.setError(null);
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
                ListaColor_Alerta = new ArrayList<Color_Alerta>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Color_Alerta.matches("")) {
                    etId_Color_Alerta.setError("Ingrese el ID del color de alerta");
                    etId_Color_Alerta.requestFocus();
                    return;
                }
                if (Nombre_Color_Alerta.matches("") && !Id_Color_Alerta.equals("-1")) {
                    etNombre_Color_Alerta.setError("Ingrese el nombre del color de alerta");
                    etNombre_Color_Alerta.requestFocus();
                    return;
                }
                if (Existe_Color_Alerta() == true && Bandera == 0) {
                    etId_Color_Alerta.setError("Ya existe un color de alerta registrado con este ID, elija otro");
                    etId_Color_Alerta.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Color_Alerta.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Color_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_COLOR_ALERTA(new Color_Alerta(null, Id_Color_Alerta, Nombre_Color_Alerta, "A", Id_Operador, "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Color_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.MODIFICAR_COLOR_ALERTA(new Color_Alerta(Tdo_Color_Alerta, Id_Color_Alerta, Nombre_Color_Alerta, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                String nombre_color_alerta = "";
                if (!Nombre_Color_Alerta.equals("")) {
                    nombre_color_alerta = "%" + Nombre_Color_Alerta + "%";
                } else {
                    nombre_color_alerta = "";
                }
                Datos = Crud_Color_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Color_Alerta = Datos.CONSULTA_GENERAL_COLOR_ALERTA_POR_VALORES(Id_Color_Alerta, nombre_color_alerta);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Color_Alerta.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaColor_Alerta = new ArrayList<Color_Alerta>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Color_Alerta = T_Color_Alerta.getColumnIndex(IT_Color_Alerta.I_COLOR_ALERTA.ID);
                int P_ID_COLOR_ALERTA = T_Color_Alerta.getColumnIndex(IT_Color_Alerta.I_COLOR_ALERTA.ID_COLOR_ALERTA);
                int P_NOMBRE_COLOR_ALERTA = T_Color_Alerta.getColumnIndex(IT_Color_Alerta.I_COLOR_ALERTA.NOMBRE_COLOR_ALERTA);
                int P_USUARIO_INGRESA = T_Color_Alerta.getColumnIndex(IT_Color_Alerta.I_COLOR_ALERTA.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Color_Alerta.getColumnIndex(IT_Color_Alerta.I_COLOR_ALERTA.FECHA_INGRESO);
                ListaColor_Alerta = new ArrayList<Color_Alerta>();
                for (T_Color_Alerta.moveToFirst(); !T_Color_Alerta.isAfterLast(); T_Color_Alerta.moveToNext()) {
                    Tdo_Color_Alerta = T_Color_Alerta.getString(P_Tdo_Color_Alerta);
                    Id_Color_Alerta = T_Color_Alerta.getString(P_ID_COLOR_ALERTA);
                    Nombre_Color_Alerta = T_Color_Alerta.getString(P_NOMBRE_COLOR_ALERTA);
                    Usuario_Ingresa = T_Color_Alerta.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Color_Alerta.getString(P_FECHA_INGRESO);
                    Color_Alerta color_alerta = new Color_Alerta(Tdo_Color_Alerta, Id_Color_Alerta, Nombre_Color_Alerta, Usuario_Ingresa, Fecha_Ingreso);
                    ListaColor_Alerta.add(color_alerta);
                }
                LIMPIARDATOS();
                Tdo_Color_Alerta = ListaColor_Alerta.get(0).getID();
                Id_Color_Alerta = ListaColor_Alerta.get(0).getID_COLOR_ALERTA();
                Nombre_Color_Alerta = ListaColor_Alerta.get(0).getNOMBRE_COLOR_ALERTA();
                Id_Color_Alerta_Temporal = Id_Color_Alerta;
                etId_Color_Alerta.setText(Id_Color_Alerta);
                etNombre_Color_Alerta.setText(Nombre_Color_Alerta);
                Usuario_Ingresa = ListaColor_Alerta.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaColor_Alerta.get(0).getFECHA_INGRESO();
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Color_Alerta.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Color_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            Datos.DESACTIVAR_COLOR_ALERTA(new Color_Alerta(Tdo_Color_Alerta, Id_Color_Alerta, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                if (ListaColor_Alerta.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Color_Alerta = ListaColor_Alerta.get(anterior).ID;
                Id_Color_Alerta = ListaColor_Alerta.get(anterior).ID_COLOR_ALERTA;
                Id_Color_Alerta_Temporal = Id_Color_Alerta;
                Nombre_Color_Alerta = ListaColor_Alerta.get(anterior).NOMBRE_COLOR_ALERTA;
                etId_Color_Alerta.setText(Id_Color_Alerta);
                etNombre_Color_Alerta.setText(Nombre_Color_Alerta);
                Usuario_Ingresa = ListaColor_Alerta.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaColor_Alerta.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaColor_Alerta.size() == 0) {
                    return;
                }
                if (siguiente == ListaColor_Alerta.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Color_Alerta = ListaColor_Alerta.get(siguiente).ID;
                Id_Color_Alerta = ListaColor_Alerta.get(siguiente).ID_COLOR_ALERTA;
                Id_Color_Alerta_Temporal = Id_Color_Alerta;
                Nombre_Color_Alerta = ListaColor_Alerta.get(siguiente).NOMBRE_COLOR_ALERTA;
                etId_Color_Alerta.setText(Id_Color_Alerta);
                etNombre_Color_Alerta.setText(Nombre_Color_Alerta);
                Usuario_Ingresa = ListaColor_Alerta.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaColor_Alerta.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Color_Alerta.this, C_Menu_Principal.class);
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

    public boolean Existe_Color_Alerta() {
        Datos = Crud_Color_Alerta.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Color_Alerta = Datos.CONSULTA_GENERAL_COLOR_ALERTA_POR_ID(Id_Color_Alerta);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Color_Alerta.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Color_Alerta = "";
        Id_Color_Alerta = "";
        Id_Color_Alerta_Temporal = "";
        anterior = 0;
        siguiente = 0;
        etId_Color_Alerta.setText("");
        etNombre_Color_Alerta.setText("");
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            etId_Color_Alerta.setEnabled(true);
            etId_Color_Alerta.requestFocus();
            etNombre_Color_Alerta.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Color_Alerta.setEnabled(true);
            etId_Color_Alerta.requestFocus();
            etNombre_Color_Alerta.setEnabled(true);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaColor_Alerta.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Color_Alerta.setEnabled(true);
            etId_Color_Alerta.requestFocus();
            etNombre_Color_Alerta.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Color_Alerta.setEnabled(false);
            etId_Color_Alerta.setText(Id_Color_Alerta_Temporal);
            etNombre_Color_Alerta.requestFocus();
            etNombre_Color_Alerta.setEnabled(true);
        }
    }

    public void Obtener_Datos() {
        Id_Color_Alerta = etId_Color_Alerta.getText().toString();
        Nombre_Color_Alerta = etNombre_Color_Alerta.getText().toString().toUpperCase();
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