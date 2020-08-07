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

import org.bp.teuno.checklist.Modelo.Pasillo;
import org.bp.teuno.checklist.Modelo.Tipo_Pasillo;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Pasillo;
import org.bp.teuno.checklist.SQLite.IT_Tipo_Pasillo;
import org.bp.teuno.checklist.UI.Crud_Pasillo;
import org.bp.teuno.checklist.UI.Crud_Tipo_Pasillo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Pasillo extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Pasillo, etNombre_Pasillo;
    TextInputLayout tilId_Pasillo, tilNombre_Pasillo;
    MaterialBetterSpinner msTipo;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Pasillo Datos;
    String Tdo_Pasillo, Id_Pasillo, Nombre_Pasillo, Id_Pasillo_Temporal = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    int Bandera = 0, anterior = 0, siguiente = 0;
    Cursor T_Pasillo, T_Tipo_Pasillo;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    String Tdo_Tipo_Pasillo = "", Id_Tipo_Pasillo = "", Nombre_Tipo_Pasillo = "";
    ArrayList<Pasillo> ListaPasillo = new ArrayList<Pasillo>();
    ArrayList<Tipo_Pasillo> ListaTipoPasillo = new ArrayList<Tipo_Pasillo>();
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_pasillo);

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

        msTipo = (MaterialBetterSpinner) findViewById(R.id.msTipo);

        msTipo.setText("");
        msTipo.setHint("* Tipo");
        msTipo.setEnabled(false);
        msTipo.setFocusableInTouchMode(false);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();


        etId_Pasillo = (EditText) findViewById(R.id.etId_Pasillo);
        etNombre_Pasillo = (EditText) findViewById(R.id.etNombre_Pasillo);
        tilId_Pasillo = (TextInputLayout) findViewById(R.id.tilId_Pasillo);
        tilNombre_Pasillo = (TextInputLayout) findViewById(R.id.tilNombre_Pasillo);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etId_Pasillo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Pasillo.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Pasillo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Pasillo.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CARGAR_TIPO_PASILLOS();

        msTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaTipoPasillo.size() > 0) {
                    Tdo_Tipo_Pasillo = ListaTipoPasillo.get(position).getID();
                    Id_Tipo_Pasillo = ListaTipoPasillo.get(position).getID_TIPO_PASILLO();
                    Nombre_Tipo_Pasillo = ListaTipoPasillo.get(position).getNOMBRE_TIPO_PASILLO();
                }
                Log.i("Posicion", String.valueOf(position));
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
                ListaPasillo = new ArrayList<Pasillo>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Pasillo.matches("")) {
                    etId_Pasillo.setError("Ingrese el ID del pasillo");
                    etId_Pasillo.requestFocus();
                    return;
                }
                if (Nombre_Pasillo.matches("") && !Id_Pasillo.equals("-1")) {
                    etNombre_Pasillo.setError("Ingrese el nombre del pasillo");
                    etNombre_Pasillo.requestFocus();
                    return;
                }
                if (Existe_Pasillo() == true && Bandera == 0) {
                    etId_Pasillo.setError("Ya existe un pasillo registrado con este ID, elija otro");
                    etId_Pasillo.requestFocus();
                    return;
                }
                if (Tdo_Tipo_Pasillo.equals("")) {
                    msTipo.setError("Seleccione un tipo");
                    msTipo.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Pasillo.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Pasillo.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_PASILLO(new Pasillo(null, Id_Pasillo, Nombre_Pasillo, Tdo_Tipo_Pasillo, "A", Id_Operador, "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Pasillo.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.MODIFICAR_PASILLO(new Pasillo(Tdo_Pasillo, Id_Pasillo, Nombre_Pasillo, Tdo_Tipo_Pasillo, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                String nombre_pasillo = "";
                if (!Nombre_Pasillo.equals("")) {
                    nombre_pasillo = "%" + Nombre_Pasillo + "%";
                } else {
                    nombre_pasillo = "";
                }
                Datos = Crud_Pasillo.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Pasillo = Datos.CONSULTA_GENERAL_PASILLO_POR_VALORES(Id_Pasillo, nombre_pasillo, Tdo_Tipo_Pasillo);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Pasillo.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen Datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaPasillo = new ArrayList<Pasillo>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Pasillo = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.ID);
                int P_ID_PASILLO = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.ID_PASILLO);
                int P_NOMBRE_PASILLO = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.NOMBRE_PASILLO);
                int P_TIPO_PASILLO = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.TIPO_PASILLO);
                int P_USUARIO_INGRESA = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Pasillo.getColumnIndex(IT_Pasillo.I_PASILLO.FECHA_INGRESO);
                ListaPasillo = new ArrayList<Pasillo>();
                for (T_Pasillo.moveToFirst(); !T_Pasillo.isAfterLast(); T_Pasillo.moveToNext()) {
                    Tdo_Pasillo = T_Pasillo.getString(P_Tdo_Pasillo);
                    Id_Pasillo = T_Pasillo.getString(P_ID_PASILLO);
                    Nombre_Pasillo = T_Pasillo.getString(P_NOMBRE_PASILLO);
                    Tdo_Tipo_Pasillo = T_Pasillo.getString(P_TIPO_PASILLO);
                    Usuario_Ingresa = T_Pasillo.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Pasillo.getString(P_FECHA_INGRESO);
                    Pasillo pasillo = new Pasillo(Tdo_Pasillo, Id_Pasillo, Nombre_Pasillo, Tdo_Tipo_Pasillo, Usuario_Ingresa, Fecha_Ingreso);
                    ListaPasillo.add(pasillo);
                }
                LIMPIARDATOS();
                Tdo_Pasillo = ListaPasillo.get(0).getID();
                Id_Pasillo = ListaPasillo.get(0).getID_PASILLO();
                Nombre_Pasillo = ListaPasillo.get(0).getNOMBRE_PASILLO();
                Id_Pasillo_Temporal = Id_Pasillo;
                etId_Pasillo.setText(Id_Pasillo);
                etNombre_Pasillo.setText(Nombre_Pasillo);
                Tdo_Tipo_Pasillo = ListaPasillo.get(0).getTIPO_PASILLO();
                for (int i = 0; i < ListaTipoPasillo.size(); i++) {
                    if (Tdo_Tipo_Pasillo.equals(ListaTipoPasillo.get(i).getID())) {
                        msTipo.setText(ListaTipoPasillo.get(i).getNOMBRE_TIPO_PASILLO());
                        i = ListaTipoPasillo.size();
                    }
                }
                Usuario_Ingresa = ListaPasillo.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaPasillo.get(0).getFECHA_INGRESO();
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Pasillo.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Pasillo.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            Datos.DESACTIVAR_PASILLO(new Pasillo(Tdo_Pasillo, Id_Pasillo, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                if (ListaPasillo.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Pasillo = ListaPasillo.get(anterior).ID;
                Id_Pasillo = ListaPasillo.get(anterior).ID_PASILLO;
                Id_Pasillo_Temporal = Id_Pasillo;
                Nombre_Pasillo = ListaPasillo.get(anterior).NOMBRE_PASILLO;
                etId_Pasillo.setText(Id_Pasillo);
                etNombre_Pasillo.setText(Nombre_Pasillo);
                Tdo_Tipo_Pasillo = ListaPasillo.get(anterior).getTIPO_PASILLO();
                for (int i = 0; i < ListaTipoPasillo.size(); i++) {
                    if (Tdo_Tipo_Pasillo.equals(ListaTipoPasillo.get(i).getID())) {
                        msTipo.setText(ListaTipoPasillo.get(i).getNOMBRE_TIPO_PASILLO());
                        i = ListaTipoPasillo.size();
                    }
                }
                Usuario_Ingresa = ListaPasillo.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaPasillo.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaPasillo.size() == 0) {
                    return;
                }
                if (siguiente == ListaPasillo.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Pasillo = ListaPasillo.get(siguiente).ID;
                Id_Pasillo = ListaPasillo.get(siguiente).ID_PASILLO;
                Id_Pasillo_Temporal = Id_Pasillo;
                Nombre_Pasillo = ListaPasillo.get(siguiente).NOMBRE_PASILLO;
                etId_Pasillo.setText(Id_Pasillo);
                etNombre_Pasillo.setText(Nombre_Pasillo);
                Tdo_Tipo_Pasillo = ListaPasillo.get(siguiente).getTIPO_PASILLO();
                for (int i = 0; i < ListaTipoPasillo.size(); i++) {
                    if (Tdo_Tipo_Pasillo.equals(ListaTipoPasillo.get(i).getID())) {
                        msTipo.setText(ListaTipoPasillo.get(i).getNOMBRE_TIPO_PASILLO());
                        i = ListaTipoPasillo.size();
                    }
                }
                Usuario_Ingresa = ListaPasillo.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaPasillo.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Pasillo.this, C_Menu_Principal.class);
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

    public boolean Existe_Pasillo() {
        Datos = Crud_Pasillo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Pasillo = Datos.CONSULTA_GENERAL_PASILLO_POR_ID(Id_Pasillo);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Pasillo.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Pasillo = "";
        Id_Pasillo = "";
        Id_Pasillo_Temporal = "";
        Tdo_Tipo_Pasillo = "";
        Id_Tipo_Pasillo = "";
        anterior = 0;
        siguiente = 0;
        etId_Pasillo.setText("");
        etNombre_Pasillo.setText("");
        msTipo.setText("");
        msTipo.setHint("* Tipo");
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            msTipo.setFocusableInTouchMode(false);
            etId_Pasillo.setEnabled(true);
            etId_Pasillo.requestFocus();
            etNombre_Pasillo.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Pasillo.setEnabled(true);
            msTipo.setFocusableInTouchMode(false);
            etId_Pasillo.requestFocus();
            etNombre_Pasillo.setEnabled(true);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaPasillo.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Pasillo.setEnabled(true);
            msTipo.setFocusableInTouchMode(false);
            etId_Pasillo.requestFocus();
            etNombre_Pasillo.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Pasillo.setEnabled(false);
            msTipo.setFocusableInTouchMode(false);
            etId_Pasillo.setText(Id_Pasillo_Temporal);
            etNombre_Pasillo.requestFocus();
            etNombre_Pasillo.setEnabled(true);
            Tdo_Tipo_Pasillo = "";
            msTipo.setText("");
            msTipo.setHint("* Tipo");
        }
    }

    public void Obtener_Datos() {
        Id_Pasillo = etId_Pasillo.getText().toString();
        Nombre_Pasillo = etNombre_Pasillo.getText().toString().toUpperCase();
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

    public void CARGAR_TIPO_PASILLOS() {
        Crud_Tipo_Pasillo DATOS;
        DATOS = Crud_Tipo_Pasillo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Tipo_Pasillo = DATOS.CONSULTA_GENERAL_TIPO_PASILLO();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Tipo_Pasillo.moveToFirst() == false) {
            //el cursor está vacío
            ListaTipoPasillo = new ArrayList<Tipo_Pasillo>();
            Tdo_Tipo_Pasillo = "";
            Id_Tipo_Pasillo = "";
            return;
        }
        int P_Tdo_Tipo_Pasillo = T_Tipo_Pasillo.getColumnIndex(IT_Tipo_Pasillo.I_TIPO_PASILLO.ID);
        int P_ID_TIPO_PASILLO = T_Tipo_Pasillo.getColumnIndex(IT_Tipo_Pasillo.I_TIPO_PASILLO.ID_TIPO_PASILLO);
        int P_NOMBRE_TIPO_PASILLO = T_Tipo_Pasillo.getColumnIndex(IT_Tipo_Pasillo.I_TIPO_PASILLO.NOMBRE_TIPO_PASILLO);
        ListaTipoPasillo = new ArrayList<Tipo_Pasillo>();
        for (T_Tipo_Pasillo.moveToFirst(); !T_Tipo_Pasillo.isAfterLast(); T_Tipo_Pasillo.moveToNext()) {
            Tdo_Tipo_Pasillo = T_Tipo_Pasillo.getString(P_Tdo_Tipo_Pasillo);
            Id_Tipo_Pasillo = T_Tipo_Pasillo.getString(P_ID_TIPO_PASILLO);
            Nombre_Tipo_Pasillo = T_Tipo_Pasillo.getString(P_NOMBRE_TIPO_PASILLO);
            Tipo_Pasillo tipo_pasillo = new Tipo_Pasillo(Tdo_Tipo_Pasillo, Id_Tipo_Pasillo, Nombre_Tipo_Pasillo);
            ListaTipoPasillo.add(tipo_pasillo);
        }
        Tdo_Tipo_Pasillo = ListaTipoPasillo.get(0).getID();
        Id_Tipo_Pasillo = ListaTipoPasillo.get(0).getID_TIPO_PASILLO();
        Nombre_Tipo_Pasillo = ListaTipoPasillo.get(0).getNOMBRE_TIPO_PASILLO();
        ArrayAdapter<Tipo_Pasillo> Adapter = new ArrayAdapter<Tipo_Pasillo>(this, android.R.layout.simple_spinner_dropdown_item, ListaTipoPasillo);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msTipo.setAdapter(Adapter);
    }

}