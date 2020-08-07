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

import org.bp.teuno.checklist.Modelo.Cliente;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Cliente;
import org.bp.teuno.checklist.UI.Crud_Cliente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Cliente extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Cliente, etNombre_Cliente;
    TextInputLayout tilId_Cliente, tilNombre_Cliente;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Cliente Datos;
    String Tdo_Cliente, Id_Cliente, Nombre_Cliente, Id_Cliente_Temporal = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    int Bandera = 0, anterior = 0, siguiente = 0;
    Cursor T_Cliente;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    ArrayList<Cliente> ListaCliente = new ArrayList<Cliente>();
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_cliente);

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


        etId_Cliente = (EditText) findViewById(R.id.etId_Cliente);
        etNombre_Cliente = (EditText) findViewById(R.id.etNombre_Cliente);
        tilId_Cliente = (TextInputLayout) findViewById(R.id.tilId_Cliente);
        tilNombre_Cliente = (TextInputLayout) findViewById(R.id.tilNombre_Cliente);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etId_Cliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Cliente.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Cliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Cliente.setError(null);
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
                ListaCliente = new ArrayList<Cliente>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Cliente.matches("")) {
                    etId_Cliente.setError("Ingrese el ID del cliente");
                    etId_Cliente.requestFocus();
                    return;
                }
                if (Nombre_Cliente.matches("") && !Id_Cliente.equals("-1")) {
                    etNombre_Cliente.setError("Ingrese el nombre del cliente");
                    etNombre_Cliente.requestFocus();
                    return;
                }
                if (Existe_Cliente() == true && Bandera == 0) {
                    etId_Cliente.setError("Ya existe un cliente registrado con este ID, elija otro");
                    etId_Cliente.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Cliente.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Cliente.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_CLIENTE(new Cliente(null, Id_Cliente, Nombre_Cliente, "A", Id_Operador, "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Cliente.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.MODIFICAR_CLIENTE(new Cliente(Tdo_Cliente, Id_Cliente, Nombre_Cliente, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                String nombre_cliente = "";
                if (!Nombre_Cliente.equals("")) {
                    nombre_cliente = "%" + Nombre_Cliente + "%";
                } else {
                    nombre_cliente = "";
                }
                Datos = Crud_Cliente.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Cliente = Datos.CONSULTA_GENERAL_CLIENTE_POR_VALORES(Id_Cliente, nombre_cliente);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Cliente.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaCliente = new ArrayList<Cliente>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Cliente = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.ID);
                int P_ID_CLIENTE = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.ID_CLIENTE);
                int P_NOMBRE_CLIENTE = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.NOMBRE_CLIENTE);
                int P_USUARIO_INGRESA = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.FECHA_INGRESO);
                ListaCliente = new ArrayList<Cliente>();
                for (T_Cliente.moveToFirst(); !T_Cliente.isAfterLast(); T_Cliente.moveToNext()) {
                    Tdo_Cliente = T_Cliente.getString(P_Tdo_Cliente);
                    Id_Cliente = T_Cliente.getString(P_ID_CLIENTE);
                    Nombre_Cliente = T_Cliente.getString(P_NOMBRE_CLIENTE);
                    Usuario_Ingresa = T_Cliente.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Cliente.getString(P_FECHA_INGRESO);
                    Cliente cliente = new Cliente(Tdo_Cliente, Id_Cliente, Nombre_Cliente, Usuario_Ingresa, Fecha_Ingreso);
                    ListaCliente.add(cliente);
                }
                LIMPIARDATOS();
                Tdo_Cliente = ListaCliente.get(0).getID();
                Id_Cliente = ListaCliente.get(0).getID_CLIENTE();
                Nombre_Cliente = ListaCliente.get(0).getNOMBRE_CLIENTE();
                Usuario_Ingresa = ListaCliente.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaCliente.get(0).getFECHA_INGRESO();
                Id_Cliente_Temporal = Id_Cliente;
                etId_Cliente.setText(Id_Cliente);
                etNombre_Cliente.setText(Nombre_Cliente);
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Cliente.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Cliente.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            Datos.DESACTIVAR_CLIENTE(new Cliente(Tdo_Cliente, Id_Cliente, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                if (ListaCliente.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Cliente = ListaCliente.get(anterior).ID;
                Id_Cliente = ListaCliente.get(anterior).ID_CLIENTE;
                Id_Cliente_Temporal = Id_Cliente;
                Nombre_Cliente = ListaCliente.get(anterior).NOMBRE_CLIENTE;
                etId_Cliente.setText(Id_Cliente);
                etNombre_Cliente.setText(Nombre_Cliente);
                Usuario_Ingresa = ListaCliente.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaCliente.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaCliente.size() == 0) {
                    return;
                }
                if (siguiente == ListaCliente.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Cliente = ListaCliente.get(siguiente).ID;
                Id_Cliente = ListaCliente.get(siguiente).ID_CLIENTE;
                Id_Cliente_Temporal = Id_Cliente;
                Nombre_Cliente = ListaCliente.get(siguiente).NOMBRE_CLIENTE;
                etId_Cliente.setText(Id_Cliente);
                etNombre_Cliente.setText(Nombre_Cliente);
                Usuario_Ingresa = ListaCliente.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaCliente.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Cliente.this, C_Menu_Principal.class);
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

    public boolean Existe_Cliente() {
        Datos = Crud_Cliente.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Cliente = Datos.CONSULTA_GENERAL_CLIENTE_POR_ID(Id_Cliente);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Cliente.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Cliente = "";
        Id_Cliente = "";
        Id_Cliente_Temporal = "";
        anterior = 0;
        siguiente = 0;
        etId_Cliente.setText("");
        etNombre_Cliente.setText("");
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            etId_Cliente.setEnabled(true);
            etId_Cliente.requestFocus();
            etNombre_Cliente.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Cliente.setEnabled(true);
            etId_Cliente.requestFocus();
            etNombre_Cliente.setEnabled(true);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaCliente.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Cliente.setEnabled(true);
            etId_Cliente.requestFocus();
            etNombre_Cliente.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Cliente.setEnabled(false);
            etId_Cliente.setText(Id_Cliente_Temporal);
            etNombre_Cliente.requestFocus();
            etNombre_Cliente.setEnabled(true);
        }
    }

    public void Obtener_Datos() {
        Id_Cliente = etId_Cliente.getText().toString();
        Nombre_Cliente = etNombre_Cliente.getText().toString().toUpperCase();
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