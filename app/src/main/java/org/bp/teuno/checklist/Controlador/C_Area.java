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

import org.bp.teuno.checklist.Modelo.Area;
import org.bp.teuno.checklist.Modelo.Tipo_Area;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Area;
import org.bp.teuno.checklist.SQLite.IT_Tipo_Area;
import org.bp.teuno.checklist.UI.Crud_Area;
import org.bp.teuno.checklist.UI.Crud_Tipo_Area;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Area extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etId_Area, etNombre_Area;
    TextInputLayout tilId_Area, tilNombre_Area;
    MaterialBetterSpinner msTipo;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    Crud_Area Datos;
    String Tdo_Area, Id_Area, Nombre_Area, Id_Area_Temporal = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    int Bandera = 0, anterior = 0, siguiente = 0;
    Cursor T_Area, T_Tipo_Area;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    String Tdo_Tipo_Area = "", Id_Tipo_Area = "", Nombre_Tipo_Area = "";
    ArrayList<Area> ListaArea = new ArrayList<Area>();
    ArrayList<Tipo_Area> ListaTipoArea = new ArrayList<Tipo_Area>();
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_area);

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


        etId_Area = (EditText) findViewById(R.id.etId_Area);
        etNombre_Area = (EditText) findViewById(R.id.etNombre_Area);
        tilId_Area = (TextInputLayout) findViewById(R.id.tilId_Area);
        tilNombre_Area = (TextInputLayout) findViewById(R.id.tilNombre_Area);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etId_Area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Area.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etNombre_Area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNombre_Area.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CARGAR_TIPO_AREAS();

        msTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaTipoArea.size() > 0) {
                    Tdo_Tipo_Area = ListaTipoArea.get(position).getID();
                    Id_Tipo_Area = ListaTipoArea.get(position).getID_TIPO_AREA();
                    Nombre_Tipo_Area = ListaTipoArea.get(position).getNOMBRE_TIPO_AREA();
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
                ListaArea = new ArrayList<Area>();
                LIMPIARDATOS();
                ACTIVARDATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (Id_Area.matches("")) {
                    etId_Area.setError("Ingrese el ID del area");
                    etId_Area.requestFocus();
                    return;
                }
                if (Nombre_Area.matches("") && !Id_Area.equals("-1")) {
                    etNombre_Area.setError("Ingrese el nombre del area");
                    etNombre_Area.requestFocus();
                    return;
                }
                if (Existe_Area() == true && Bandera == 0) {
                    etId_Area.setError("Ya existe un area registrada con este ID, elija otro");
                    etId_Area.requestFocus();
                    return;
                }
                if (Tdo_Tipo_Area.equals("")) {
                    msTipo.setError("Seleccione un tipo");
                    msTipo.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Area.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_AREA(new Area(null, Id_Area, Nombre_Area, Tdo_Tipo_Area, "A", Id_Operador, "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.MODIFICAR_AREA(new Area(Tdo_Area, Id_Area, Nombre_Area, Tdo_Tipo_Area, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                String nombre_area = "";
                if (!Nombre_Area.equals("")) {
                    nombre_area = "%" + Nombre_Area + "%";
                } else {
                    nombre_area = "";
                }
                Datos = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    Datos.GETBD().beginTransaction();
                    T_Area = Datos.CONSULTA_GENERAL_AREA_POR_VALORES(Id_Area, nombre_area, Tdo_Tipo_Area);
                    Datos.GETBD().setTransactionSuccessful();
                } finally {
                    Datos.GETBD().endTransaction();
                }
                if (T_Area.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    ListaArea = new ArrayList<Area>();
                    LIMPIARDATOS();
                    ACTIVARDATOS(1);
                    return;
                }
                int P_Tdo_Area = T_Area.getColumnIndex(IT_Area.I_AREA.ID);
                int P_ID_AREA = T_Area.getColumnIndex(IT_Area.I_AREA.ID_AREA);
                int P_NOMBRE_AREA = T_Area.getColumnIndex(IT_Area.I_AREA.NOMBRE_AREA);
                int P_TIPO_AREA = T_Area.getColumnIndex(IT_Area.I_AREA.TIPO_AREA);
                int P_USUARIO_INGRESA = T_Area.getColumnIndex(IT_Area.I_AREA.USUARIO_INGRESA);
                int P_FECHA_INGRESO = T_Area.getColumnIndex(IT_Area.I_AREA.FECHA_INGRESO);
                ListaArea = new ArrayList<Area>();
                for (T_Area.moveToFirst(); !T_Area.isAfterLast(); T_Area.moveToNext()) {
                    Tdo_Area = T_Area.getString(P_Tdo_Area);
                    Id_Area = T_Area.getString(P_ID_AREA);
                    Nombre_Area = T_Area.getString(P_NOMBRE_AREA);
                    Tdo_Tipo_Area = T_Area.getString(P_TIPO_AREA);
                    Usuario_Ingresa = T_Area.getString(P_USUARIO_INGRESA);
                    Fecha_Ingreso = T_Area.getString(P_FECHA_INGRESO);
                    Area area = new Area(Tdo_Area, Id_Area, Nombre_Area, Tdo_Tipo_Area, Usuario_Ingresa, Fecha_Ingreso);
                    ListaArea.add(area);
                }
                LIMPIARDATOS();
                Tdo_Area = ListaArea.get(0).getID();
                Id_Area = ListaArea.get(0).getID_AREA();
                Nombre_Area = ListaArea.get(0).getNOMBRE_AREA();
                Id_Area_Temporal = Id_Area;
                etId_Area.setText(Id_Area);
                etNombre_Area.setText(Nombre_Area);
                Tdo_Tipo_Area = ListaArea.get(0).getTIPO_AREA();
                for (int i = 0; i < ListaTipoArea.size(); i++) {
                    if (Tdo_Tipo_Area.equals(ListaTipoArea.get(i).getID())) {
                        msTipo.setText(ListaTipoArea.get(i).getNOMBRE_TIPO_AREA());
                        i = ListaTipoArea.size();
                    }
                }
                Usuario_Ingresa = ListaArea.get(0).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaArea.get(0).getFECHA_INGRESO();
                ACTIVARDATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Area.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Datos = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            Datos.GETBD().beginTransaction();
                                            Datos.DESACTIVAR_AREA(new Area(Tdo_Area, Id_Area, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
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
                if (ListaArea.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Area = ListaArea.get(anterior).ID;
                Id_Area = ListaArea.get(anterior).ID_AREA;
                Id_Area_Temporal = Id_Area;
                Nombre_Area = ListaArea.get(anterior).NOMBRE_AREA;
                etId_Area.setText(Id_Area);
                etNombre_Area.setText(Nombre_Area);
                Tdo_Tipo_Area = ListaArea.get(anterior).getTIPO_AREA();
                for (int i = 0; i < ListaTipoArea.size(); i++) {
                    if (Tdo_Tipo_Area.equals(ListaTipoArea.get(i).getID())) {
                        msTipo.setText(ListaTipoArea.get(i).getNOMBRE_TIPO_AREA());
                        i = ListaTipoArea.size();
                    }
                }
                Usuario_Ingresa = ListaArea.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaArea.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaArea.size() == 0) {
                    return;
                }
                if (siguiente == ListaArea.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Area = ListaArea.get(siguiente).ID;
                Id_Area = ListaArea.get(siguiente).ID_AREA;
                Id_Area_Temporal = Id_Area;
                Nombre_Area = ListaArea.get(siguiente).NOMBRE_AREA;
                etId_Area.setText(Id_Area);
                etNombre_Area.setText(Nombre_Area);
                Tdo_Tipo_Area = ListaArea.get(siguiente).getTIPO_AREA();
                for (int i = 0; i < ListaTipoArea.size(); i++) {
                    if (Tdo_Tipo_Area.equals(ListaTipoArea.get(i).getID())) {
                        msTipo.setText(ListaTipoArea.get(i).getNOMBRE_TIPO_AREA());
                        i = ListaTipoArea.size();
                    }
                }
                Usuario_Ingresa = ListaArea.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaArea.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Area.this, C_Menu_Principal.class);
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

    public boolean Existe_Area() {
        Datos = Crud_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Area = Datos.CONSULTA_GENERAL_AREA_POR_ID(Id_Area);
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Area.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void LIMPIARDATOS() {
        Tdo_Area = "";
        Id_Area = "";
        Id_Area_Temporal = "";
        Tdo_Tipo_Area = "";
        Id_Tipo_Area = "";
        anterior = 0;
        siguiente = 0;
        etId_Area.setText("");
        etNombre_Area.setText("");
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
            etId_Area.setEnabled(true);
            etId_Area.requestFocus();
            etNombre_Area.setEnabled(true);
        }
        if (OPCION == 1) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            msTipo.setFocusableInTouchMode(false);
            fabAtras.setEnabled(false);
            etId_Area.setEnabled(true);
            etId_Area.requestFocus();
            etNombre_Area.setEnabled(true);
        }
        if (OPCION == 2) {
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaArea.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            etId_Area.setEnabled(true);
            etId_Area.requestFocus();
            msTipo.setFocusableInTouchMode(false);
            etNombre_Area.setEnabled(true);
        }
        if (OPCION == 3) {
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            etId_Area.setEnabled(false);
            etId_Area.setText(Id_Area_Temporal);
            msTipo.setFocusableInTouchMode(false);
            etNombre_Area.requestFocus();
            etNombre_Area.setEnabled(true);
            Tdo_Tipo_Area = "";
            msTipo.setText("");
            msTipo.setHint("* Tipo");
        }
    }

    public void Obtener_Datos() {
        Id_Area = etId_Area.getText().toString();
        Nombre_Area = etNombre_Area.getText().toString().toUpperCase();
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

    public void CARGAR_TIPO_AREAS() {
        Crud_Tipo_Area DATOS;
        DATOS = Crud_Tipo_Area.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Tipo_Area = DATOS.CONSULTA_GENERAL_TIPO_AREA();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Tipo_Area.moveToFirst() == false) {
            //el cursor está vacío
            ListaTipoArea = new ArrayList<Tipo_Area>();
            Tdo_Tipo_Area = "";
            Id_Tipo_Area = "";
            return;
        }
        int P_Tdo_Tipo_Area = T_Tipo_Area.getColumnIndex(IT_Tipo_Area.I_TIPO_AREA.ID);
        int P_ID_TIPO_AREA = T_Tipo_Area.getColumnIndex(IT_Tipo_Area.I_TIPO_AREA.ID_TIPO_AREA);
        int P_NOMBRE_TIPO_AREA = T_Tipo_Area.getColumnIndex(IT_Tipo_Area.I_TIPO_AREA.NOMBRE_TIPO_AREA);
        ListaTipoArea = new ArrayList<Tipo_Area>();
        for (T_Tipo_Area.moveToFirst(); !T_Tipo_Area.isAfterLast(); T_Tipo_Area.moveToNext()) {
            Tdo_Tipo_Area = T_Tipo_Area.getString(P_Tdo_Tipo_Area);
            Id_Tipo_Area = T_Tipo_Area.getString(P_ID_TIPO_AREA);
            Nombre_Tipo_Area = T_Tipo_Area.getString(P_NOMBRE_TIPO_AREA);
            Tipo_Area tipo_area = new Tipo_Area(Tdo_Tipo_Area, Id_Tipo_Area, Nombre_Tipo_Area);
            ListaTipoArea.add(tipo_area);
        }
        Tdo_Tipo_Area = ListaTipoArea.get(0).getID();
        Id_Tipo_Area = ListaTipoArea.get(0).getID_TIPO_AREA();
        Nombre_Tipo_Area = ListaTipoArea.get(0).getNOMBRE_TIPO_AREA();
        ArrayAdapter<Tipo_Area> Adapter = new ArrayAdapter<Tipo_Area>(this, android.R.layout.simple_spinner_dropdown_item, ListaTipoArea);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msTipo.setAdapter(Adapter);
    }


}