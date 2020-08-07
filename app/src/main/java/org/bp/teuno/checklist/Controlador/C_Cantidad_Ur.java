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

import org.bp.teuno.checklist.Modelo.Cantidad_Ur;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Cantidad_Ur;
import org.bp.teuno.checklist.UI.Crud_Cantidad_Ur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Cantidad_Ur extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Cantidad_Ur, etNombre_Cantidad_Ur;
    TextInputLayout tilId_Cantidad_Ur, tilNombre_Cantidad_Ur;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Cantidad_Ur Datos;
    String Tdo_Cantidad_Ur, Id_Cantidad_Ur, Nombre_Cantidad_Ur, Id_Cantidad_Ur_Temporal, Usuario_Ingresa = "", Fecha_Ingreso = "";
    int Bandera, anterior = 0, siguiente = 0;
    Cursor T_Cantidad_Ur;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    CountDownTimer countDownTimer;
    ArrayList<Cantidad_Ur> ListaCantidad_Ur = new ArrayList<Cantidad_Ur>();
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_cantidad_ur);

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

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();

        etId_Cantidad_Ur = (EditText) findViewById(R.id.etId_Cantidad_Ur);
        etNombre_Cantidad_Ur = (EditText) findViewById(R.id.etNombre_Cantidad_Ur);

        tilId_Cantidad_Ur = (TextInputLayout) findViewById(R.id.tilId_Cantidad_Ur);
        tilNombre_Cantidad_Ur = (TextInputLayout) findViewById(R.id.tilNombre_Cantidad_Ur);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etId_Cantidad_Ur.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Cantidad_Ur.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Cantidad_Ur.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Cantidad_Ur.setError(null);
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
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Cantidad_Ur.matches("")) {
                    etId_Cantidad_Ur.setError("Ingrese el ID de la cantidad de UR");
                    etId_Cantidad_Ur.requestFocus();
                    return;
                }
                if (Nombre_Cantidad_Ur.matches("") && !Id_Cantidad_Ur.equals("-1")) {
                    etNombre_Cantidad_Ur.setError("Ingrese la cantidad de UR");
                    etNombre_Cantidad_Ur.requestFocus();
                    return;
                }
                if (Existe_Cantidad_Ur() == true && Bandera == 0) {
                    etId_Cantidad_Ur.setError("Ya existe una cantidad de UR registrada con este ID, elija otro");
                    etId_Cantidad_Ur.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Cantidad_Ur.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Cantidad_Ur.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_CANTIDAD_UR(new Cantidad_Ur(null, Id_Cantidad_Ur, Nombre_Cantidad_Ur, "A", Id_Operador, "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Cantidad_Ur.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.MODIFICAR_CANTIDAD_UR(new Cantidad_Ur(Tdo_Cantidad_Ur, Id_Cantidad_Ur, Nombre_Cantidad_Ur, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                String nombre_cantidad_ur = "";
                if (!Nombre_Cantidad_Ur.equals("")) {
                    nombre_cantidad_ur = "%" + Nombre_Cantidad_Ur + "%";
                } else {
                    nombre_cantidad_ur = "";
                }
                Datos = Crud_Cantidad_Ur.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Cantidad_Ur = Datos.CONSULTA_GENERAL_CANTIDAD_UR_POR_VALORES(Id_Cantidad_Ur, nombre_cantidad_ur);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Cantidad_Ur.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaCantidad_Ur = new ArrayList<Cantidad_Ur>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Cantidad_Ur = T_Cantidad_Ur.getColumnIndex(IT_Cantidad_Ur.I_UR.ID);
                int P_ID_Cantidad_Ur = T_Cantidad_Ur.getColumnIndex(IT_Cantidad_Ur.I_UR.ID_UR);
                int P_Cantidad_Ur = T_Cantidad_Ur.getColumnIndex(IT_Cantidad_Ur.I_UR.CANTIDAD_UR);
                int P_USUARIO_INGRESA = T_Cantidad_Ur.getColumnIndex(IT_Cantidad_Ur.I_UR.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Cantidad_Ur.getColumnIndex(IT_Cantidad_Ur.I_UR.FECHA_INGRESO);
                ListaCantidad_Ur = new ArrayList<Cantidad_Ur>();
                for (T_Cantidad_Ur.moveToFirst(); !T_Cantidad_Ur.isAfterLast(); T_Cantidad_Ur.moveToNext()) {
                    Tdo_Cantidad_Ur = T_Cantidad_Ur.getString(P_Tdo_Cantidad_Ur);
                    Id_Cantidad_Ur = T_Cantidad_Ur.getString(P_ID_Cantidad_Ur);
                    Nombre_Cantidad_Ur = T_Cantidad_Ur.getString(P_Cantidad_Ur);
                    Usuario_Ingresa = T_Cantidad_Ur.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Cantidad_Ur.getString(P_FECHA_INGRESO);
                    Cantidad_Ur cantidad_ur = new Cantidad_Ur(Tdo_Cantidad_Ur, Id_Cantidad_Ur, Nombre_Cantidad_Ur, Usuario_Ingresa, Fecha_Ingreso);
                    ListaCantidad_Ur.add(cantidad_ur);
                }
                LIMPIARDATOS();
                Tdo_Cantidad_Ur = ListaCantidad_Ur.get(0).getID();
                Id_Cantidad_Ur = ListaCantidad_Ur.get(0).getID_UR();
                Nombre_Cantidad_Ur = ListaCantidad_Ur.get(0).getCANTIDAD_UR();
                Id_Cantidad_Ur_Temporal = Id_Cantidad_Ur;
                etId_Cantidad_Ur.setText(Id_Cantidad_Ur);
                etNombre_Cantidad_Ur.setText(Nombre_Cantidad_Ur);
                Usuario_Ingresa = ListaCantidad_Ur.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaCantidad_Ur.get(0).getFECHA_INGRESO();
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Cantidad_Ur.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Cantidad_Ur.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            Datos.DESACTIVAR_CANTIDAD_UR(new Cantidad_Ur(Tdo_Cantidad_Ur, Id_Cantidad_Ur, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                if (ListaCantidad_Ur.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Cantidad_Ur = ListaCantidad_Ur.get(anterior).ID;
                Id_Cantidad_Ur = ListaCantidad_Ur.get(anterior).ID_UR;
                Id_Cantidad_Ur_Temporal = Id_Cantidad_Ur;
                Nombre_Cantidad_Ur = ListaCantidad_Ur.get(anterior).CANTIDAD_UR;
                etId_Cantidad_Ur.setText(Id_Cantidad_Ur);
                etNombre_Cantidad_Ur.setText(Nombre_Cantidad_Ur);
                Usuario_Ingresa = ListaCantidad_Ur.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaCantidad_Ur.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaCantidad_Ur.size() == 0) {
                    return;
                }
                if (siguiente == ListaCantidad_Ur.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Cantidad_Ur = ListaCantidad_Ur.get(siguiente).ID;
                Id_Cantidad_Ur = ListaCantidad_Ur.get(siguiente).ID_UR;
                Id_Cantidad_Ur_Temporal = Id_Cantidad_Ur;
                Nombre_Cantidad_Ur = ListaCantidad_Ur.get(siguiente).CANTIDAD_UR;
                etId_Cantidad_Ur.setText(Id_Cantidad_Ur);
                etNombre_Cantidad_Ur.setText(Nombre_Cantidad_Ur);
                Usuario_Ingresa = ListaCantidad_Ur.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaCantidad_Ur.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Cantidad_Ur.this, C_Menu_Principal.class);
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

    public boolean Existe_Cantidad_Ur() {
        Datos = Crud_Cantidad_Ur.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Cantidad_Ur = Datos.CONSULTA_GENERAL_UR_POR_ID_UR(Id_Cantidad_Ur);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Cantidad_Ur.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Cantidad_Ur = "";
        Id_Cantidad_Ur = "";
        Id_Cantidad_Ur_Temporal = "";
        anterior = 0;
        siguiente = 0;
        etId_Cantidad_Ur.setText("");
        etNombre_Cantidad_Ur.setText("");
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Cantidad_Ur.setEnabled(true);
            etId_Cantidad_Ur.requestFocus();
            etNombre_Cantidad_Ur.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Cantidad_Ur.setEnabled(true);
            etId_Cantidad_Ur.requestFocus();
            etNombre_Cantidad_Ur.setEnabled(true);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaCantidad_Ur.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Cantidad_Ur.setEnabled(true);
            etId_Cantidad_Ur.requestFocus();
            etNombre_Cantidad_Ur.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Cantidad_Ur.setEnabled(false);
            etId_Cantidad_Ur.setText(Id_Cantidad_Ur_Temporal);
            etNombre_Cantidad_Ur.setEnabled(true);
            etNombre_Cantidad_Ur.requestFocus();
        }
    }

    public void Obtener_Datos() {
        Id_Cantidad_Ur = etId_Cantidad_Ur.getText().toString();
        Nombre_Cantidad_Ur = etNombre_Cantidad_Ur.getText().toString();
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