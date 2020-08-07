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
import android.widget.ImageView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.bp.teuno.checklist.Modelo.Equipo;
import org.bp.teuno.checklist.Modelo.Fila;
import org.bp.teuno.checklist.Modelo.Rack;
import org.bp.teuno.checklist.Modelo.Rack_Ur;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Equipo;
import org.bp.teuno.checklist.SQLite.IT_Fila;
import org.bp.teuno.checklist.SQLite.IT_Rack;
import org.bp.teuno.checklist.SQLite.IT_Rack_Ur;
import org.bp.teuno.checklist.UI.Crud_Alerta;
import org.bp.teuno.checklist.UI.Crud_Equipo;
import org.bp.teuno.checklist.UI.Crud_Fila;
import org.bp.teuno.checklist.UI.Crud_Rack;
import org.bp.teuno.checklist.UI.Crud_Rack_Ur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Equipo extends AppCompatActivity {

    private final long interval = 1 * 1000;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    EditText etID_Equipo, etMarca, etModelo, etSerie, etCodigoBanco;
    TextInputLayout tilID_Equipo, tilMarca, tilModelo, tilSerie, tilCodigoBanco;
    MaterialBetterSpinner msFila, msRack, msUR;
    Crud_Equipo DATOS;
    String Tdo_Equipo = "", ID_Fila = "", ID_Rack = "", ID_UR = "", ID_Equipo = "", Marca = "", Modelo = "", Serie = "", CodigoBanco = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    String Tdo_Rack = "", Nombre_Rack = "", Nombre_Rack_Temporal = "";  //PARA EL USO DE Racks
    String Tdo_UR = "", Tdo_UR_Temporal = "", Nombre_UR = "", Nombre_UR_Temporal = ""; // PARA CARGAR LA LISTA DE Cantidad_UR
    String Tdo_Fila = "", Id_Fila = "", Nombre_Fila = "", Nombre_Fila_Temporal = ""; // PARA CARGAR LA LISTA DE Filas
    int Bandera1 = 0, anterior = 0, siguiente = 0;
    Equipo Equipo;
    ArrayList<Equipo> ListaEquipo = new ArrayList<Equipo>();
    ArrayList<Fila> ListaFila = new ArrayList<Fila>();
    ArrayList<Rack> ListaRack = new ArrayList<Rack>();
    ArrayList<Rack_Ur> ListaUR = new ArrayList<Rack_Ur>();
    Cursor T_Equipo, T_Fila, T_Rack, T_UR;
    String Id_Operador = "", Nombre_Operador, Tdo_Turno, Turno_Operador, Access;
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    private ImageView ImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_equipo);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();
        Access = bundle.getString("Access").toString();

        ImageView2 = (ImageView) findViewById(R.id.imageView2);

        if(Access.equals("BP")){
            ImageView2.setImageResource(R.drawable.bp2);
        }else{
            ImageView2.setImageResource(R.drawable.dn2);
        }

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

        //btnLimpiarPantalla = (Button) findViewById(R.id.btnLimpiarPantalla);

        etID_Equipo = (EditText) findViewById(R.id.etID_Equipo);
        etMarca = (EditText) findViewById(R.id.etMarca);
        etModelo = (EditText) findViewById(R.id.etModelo);
        etSerie = (EditText) findViewById(R.id.etSerie);
        etCodigoBanco = (EditText) findViewById(R.id.etCodigoBanco);

        tilID_Equipo = (TextInputLayout) findViewById(R.id.tilID_Equipo);
        tilMarca = (TextInputLayout) findViewById(R.id.tilMarca);
        tilModelo = (TextInputLayout) findViewById(R.id.tilModelo);
        tilSerie = (TextInputLayout) findViewById(R.id.tilSerie);
        tilCodigoBanco = (TextInputLayout) findViewById(R.id.tilCodigoBanco);

        msFila = (MaterialBetterSpinner) findViewById(R.id.msFila);
        msRack = (MaterialBetterSpinner) findViewById(R.id.msRack);
        msUR = (MaterialBetterSpinner) findViewById(R.id.msUR);

        msFila.setText("");
        msUR.setText("");
        msRack.setText("");

        msFila.setHint("* Fila");
        msRack.setHint("* Rack");
        msUR.setHint("* UR");

        msFila.setEnabled(false);
        msUR.setEnabled(false);
        msRack.setEnabled(false);

        msFila.setClickable(false);
        msUR.setClickable(false);
        msRack.setClickable(false);

        msFila.setFocusableInTouchMode(false);
        msUR.setFocusableInTouchMode(false);
        msRack.setFocusableInTouchMode(false);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etID_Equipo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilID_Equipo.setError(null);
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

        etCodigoBanco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilCodigoBanco.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*METODO PARA CARGAR LAS LISTAS EN LOS SPINNER*/
        CARGAR_FILAS();

        msFila.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaFila.size() > 0) {
                    Tdo_Fila = ListaFila.get(position).getID();
                    Id_Fila = ListaFila.get(position).getID_FILA();
                    Nombre_Fila = ListaFila.get(position).getNOMBRE_FILA();
                    msRack.setText("");
                    msRack.setHint("* Rack");
                    msUR.setText("");
                    msUR.setHint("* UR");
                    Tdo_Rack = "";
                    Tdo_UR = "";
                    Tdo_UR_Temporal = "";
                    CARGAR_RACKS();
                }
                Log.i("Posicion", String.valueOf(position));
            }

        });

        msRack.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaRack.size() > 0) {
                    Tdo_Rack = ListaRack.get(position).getID();
                    Nombre_Rack = ListaRack.get(position).getNOMBRE_RACK();
                    msUR.setText("");
                    msUR.setHint("* UR");
                    Tdo_UR = "";
                    Tdo_UR_Temporal = "";
                    CARGAR_UR();
                }
            }

        });

        msUR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaUR.size() > 0) {
                    Tdo_UR = ListaUR.get(position).getID();
                    Nombre_UR = ListaUR.get(position).getUR();
                }
            }
        });

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bandera1 = 0;
                LIMPIAR_DATOS_1();
                LIMPIAR_DATOS_2();
                ACTIVAR_DATOS(0);
            }
        });

        fabModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bandera1 = 1;
                ACTIVAR_DATOS(3);
            }
        });

        fabLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LIMPIAR_DATOS_1();
                LIMPIAR_DATOS_2();
                LIMPIAR_DATOS_3();
                ListaEquipo = new ArrayList<>();
                ACTIVAR_DATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENER_DATOS();
                if (Tdo_Fila.equals("")) {
                    msFila.setError("Seleccione la fila");
                    msFila.requestFocus();
                    return;
                }
                if (Tdo_Rack.equals("")) {
                    msRack.setError("Seleccione el rack");
                    msRack.requestFocus();
                    return;
                }
                if (Tdo_UR.equals("")) {
                    msUR.setError("Seleccione la Ur");
                    msUR.requestFocus();
                    return;
                }
                if (ID_Equipo.equals("")) {
                    etID_Equipo.setError("Ingrese el nombre del equipo");
                    etID_Equipo.requestFocus();
                    return;
                }
                /*if (YA_EXISTE_EQUIPO_CON_MISMO_NOMBRE() == true && Bandera1 == 0) {
                    etID_Equipo.setError("Ya existe un equipo registrado con este nombre, registre otro");
                    etID_Equipo.requestFocus();
                    return;
                }*/
                /*if (YA_EXISTE_EQUIPO_EN_UR() == true && Bandera1 == 0) {
                    msUR.setError("Ya existe un equipo registrado en esta UR, elija otra");
                    msUR.requestFocus();
                    return;
                }
                if (YA_EXISTE_EQUIPO_EN_UR() == true && Bandera1 == 1 && !Tdo_UR_Temporal.equals(Tdo_UR)) {
                    msUR.setError("Ya existe un equipo registrado en esta UR, elija otra");
                    msUR.requestFocus();
                    return;
                }*/ else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Equipo.this);
                    builder.setTitle("Guardar Registro")
                            .setIcon(R.drawable.alerta1)
                            .setMessage("¿Desea guardar el registro?")
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera1 == 0) {
                                                DATOS = Crud_Equipo.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    DATOS.GETBD().beginTransaction();
                                                    Tdo_Rack = DATOS.INSERTAR_EQUIPO(new Equipo(null, Tdo_Fila, Tdo_Rack, Tdo_UR, ID_Equipo, Marca, Modelo, Serie, CodigoBanco, "A", Id_Operador, "", getDateTime(), ""));
                                                    DATOS.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    DATOS.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera1 == 1) {
                                                DATOS = Crud_Equipo.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    DATOS.GETBD().beginTransaction();
                                                    DATOS.MODIFICAR_EQUIPO(new Equipo(Tdo_Equipo, Tdo_Fila, Tdo_Rack, Tdo_UR, ID_Equipo, Marca, Modelo, Serie, CodigoBanco, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                                    DATOS.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    DATOS.GETBD().endTransaction();
                                                }
                                            }
                                            Toast.makeText(getApplicationContext(), "El registro se guardó correctamente", Toast.LENGTH_LONG).show();
                                            LIMPIAR_DATOS_1();
                                            LIMPIAR_DATOS_2();
                                            LIMPIAR_DATOS_3();
                                            ACTIVAR_DATOS(1);
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
                OBTENER_DATOS();
                String Equipo = "";
                if (!ID_Equipo.equals("")) {
                    Equipo = "%" + ID_Equipo + "%";
                } else {
                    Equipo = "";
                }
                DATOS = Crud_Equipo.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    DATOS.GETBD().beginTransaction();
                    T_Equipo = DATOS.CONSULTA_GENERAL_EQUIPO_POR_VALORES(Equipo, Tdo_Fila, Tdo_Rack, Tdo_UR);
                    DATOS.GETBD().setTransactionSuccessful();
                } finally {
                    DATOS.GETBD().endTransaction();
                }
                if (T_Equipo.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    LIMPIAR_DATOS_1();
                    LIMPIAR_DATOS_2();
                    LIMPIAR_DATOS_3();
                    ACTIVAR_DATOS(1);
                    return;
                }
                MOSTRAR_DATOS_BUSCADO();
                ACTIVAR_DATOS(2);
            }
        });

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EQUIPO_TIENE_ALERTAS() == true) {
                    etID_Equipo.setError("El equipo tiene alertas registradas, elimine primero las alertas, antes de desactivarlo");
                    etID_Equipo.requestFocus();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Equipo.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DATOS = Crud_Equipo.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            DATOS.GETBD().beginTransaction();
                                            DATOS.DESACTIVAR_EQUIPO(new Equipo(Tdo_Equipo, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                            DATOS.GETBD().setTransactionSuccessful();
                                        } finally {
                                            DATOS.GETBD().endTransaction();
                                        }
                                        Toast.makeText(getApplicationContext(), "El registro se desactivó correctamente", Toast.LENGTH_LONG).show();
                                        LIMPIAR_DATOS_1();
                                        LIMPIAR_DATOS_2();
                                        LIMPIAR_DATOS_3();
                                        ACTIVAR_DATOS(1);
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
                if (ListaEquipo.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Equipo = ListaEquipo.get(anterior).ID;
                ID_Equipo = ListaEquipo.get(anterior).NOMBRE_EQUIPO;
                etID_Equipo.setText(ID_Equipo);
                Tdo_Fila = ListaEquipo.get(anterior).ID_FILA;
                for (int i = 0; i < ListaFila.size(); i++) {
                    if (Tdo_Fila.equals(ListaFila.get(i).getID())) {
                        msFila.setText(ListaFila.get(i).getNOMBRE_FILA());
                        Nombre_Fila_Temporal = ListaFila.get(i).getNOMBRE_FILA();
                        i = ListaFila.size();
                    }
                }
                Tdo_Rack = ListaEquipo.get(anterior).ID_RACK;
                for (int i = 0; i < ListaRack.size(); i++) {
                    if (Tdo_Rack.equals(ListaRack.get(i).getID())) {
                        msRack.setText(ListaRack.get(i).getNOMBRE_RACK());
                        Nombre_Rack_Temporal = ListaRack.get(i).NOMBRE_RACK;
                        i = ListaRack.size();
                    }
                }
                Tdo_UR = ListaEquipo.get(anterior).UR;
                Tdo_UR_Temporal = Tdo_UR;
                for (int i = 0; i < ListaUR.size(); i++) {
                    if (Tdo_UR.equals(ListaUR.get(i).getID())) {
                        Nombre_UR_Temporal = ListaUR.get(i).getUR();
                        i = ListaUR.size();
                    }
                }
                etMarca.setText(ListaEquipo.get(anterior).MARCA);
                etModelo.setText(ListaEquipo.get(anterior).MODELO);
                etSerie.setText(ListaEquipo.get(anterior).SERIE);
                etCodigoBanco.setText(ListaEquipo.get(anterior).CODIGO_BANCO);
                Usuario_Ingresa = ListaEquipo.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaEquipo.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaEquipo.size() == 0) {
                    return;
                }
                if (siguiente == ListaEquipo.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Equipo = ListaEquipo.get(siguiente).ID;
                ID_Equipo = ListaEquipo.get(siguiente).NOMBRE_EQUIPO;
                etID_Equipo.setText(ID_Equipo);
                Tdo_Fila = ListaEquipo.get(siguiente).ID_FILA;
                for (int i = 0; i < ListaFila.size(); i++) {
                    if (Tdo_Fila.equals(ListaFila.get(i).getID())) {
                        msFila.setText(ListaFila.get(i).getNOMBRE_FILA());
                        Nombre_Fila_Temporal = ListaFila.get(i).getNOMBRE_FILA();
                        i = ListaFila.size();
                    }
                }
                Tdo_Rack = ListaEquipo.get(siguiente).ID_RACK;
                for (int i = 0; i < ListaRack.size(); i++) {
                    if (Tdo_Rack.equals(ListaRack.get(i).getID())) {
                        msRack.setText(ListaRack.get(i).getNOMBRE_RACK());
                        Nombre_Rack_Temporal = ListaRack.get(i).NOMBRE_RACK;
                        i = ListaRack.size();
                    }
                }
                Tdo_UR = ListaEquipo.get(siguiente).UR;
                Tdo_UR_Temporal = Tdo_UR;
                for (int i = 0; i < ListaUR.size(); i++) {
                    if (Tdo_UR.equals(ListaUR.get(i).getID())) {
                        Nombre_UR_Temporal = ListaUR.get(i).getUR();
                        i = ListaUR.size();
                    }
                }
                etMarca.setText(ListaEquipo.get(siguiente).MARCA);
                etModelo.setText(ListaEquipo.get(siguiente).MODELO);
                etSerie.setText(ListaEquipo.get(siguiente).SERIE);
                etCodigoBanco.setText(ListaEquipo.get(siguiente).CODIGO_BANCO);
                Usuario_Ingresa = ListaEquipo.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaEquipo.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Equipo.this, C_Menu_Principal.class);
                intent.putExtra("Id_Operador", Id_Operador);
                intent.putExtra("Nombre_Operador", Nombre_Operador);
                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                intent.putExtra("Turno_Operador", Turno_Operador);
                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                startActivity(intent);
                finish();
                LIMPIAR_DATOS_1();
                LIMPIAR_DATOS_2();
                ACTIVAR_DATOS(1);
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

    public boolean YA_EXISTE_EQUIPO_CON_MISMO_NOMBRE() {
        Crud_Equipo DATOS = Crud_Equipo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Equipo = DATOS.CONSULTA_GENERAL_POR_NOMBRE_EQUIPO(ID_Equipo.toUpperCase());
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Equipo.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public boolean YA_EXISTE_EQUIPO_EN_UR() {
        Crud_Equipo DATOS = Crud_Equipo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Equipo = DATOS.CONSULTA_GENERAL_EQUIPO_POR_ID_UR(Tdo_Rack, Tdo_UR);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Equipo.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public boolean EQUIPO_TIENE_ALERTAS() {
        Cursor T_Alerta;
        Crud_Alerta DATOS = Crud_Alerta.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Alerta = DATOS.CONSULTA_GENERAL_POR_EQUIPO(Tdo_Equipo);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Alerta.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public void MOSTRAR_DATOS_BUSCADO() {
        LIMPIAR_DATOS_1();
        LIMPIAR_DATOS_2();
        int P_TDO = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.ID);
        int P_ID_Fila = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.ID_FILA);
        int P_ID_RAck = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.ID_RACK);
        int P_ID_UR = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.ID_RACK_UR);
        int P_Nombre_Equipo = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.NOMBRE_EQUIPO);
        int P_Marca = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.MARCA);
        int P_Modelo = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.MODELO);
        int P_Serie = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.SERIE);
        int P_CodigoBanco = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.CODIGO_BANCO);
        int P_USUARIO_INGRESA = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.USUARIO_INGRESA);
        int P_FECHA_INGRESO = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.FECHA_INGRESO);
        ListaEquipo = new ArrayList<Equipo>();
        for (T_Equipo.moveToFirst(); !T_Equipo.isAfterLast(); T_Equipo.moveToNext()) {
            Tdo_Equipo = T_Equipo.getString(P_TDO);
            ID_Equipo = T_Equipo.getString(P_Nombre_Equipo);
            Tdo_Fila = T_Equipo.getString(P_ID_Fila);
            Tdo_UR = T_Equipo.getString(P_ID_UR);
            Tdo_Rack = T_Equipo.getString(P_ID_RAck);
            Marca = T_Equipo.getString(P_Marca);
            Modelo = T_Equipo.getString(P_Modelo);
            Serie = T_Equipo.getString(P_Serie);
            CodigoBanco = T_Equipo.getString(P_CodigoBanco);
            Usuario_Ingresa = T_Equipo.getString(P_USUARIO_INGRESA);
            Fecha_Ingreso = T_Equipo.getString(P_FECHA_INGRESO);
            Equipo = new Equipo(Tdo_Equipo, Tdo_Fila, Tdo_Rack, Tdo_UR, ID_Equipo, Marca, Modelo, Serie, CodigoBanco, Usuario_Ingresa, Fecha_Ingreso);
            ListaEquipo.add(Equipo);
        }
        if (ListaEquipo.size() == 0) {
            return;
        }
        Tdo_Equipo = ListaEquipo.get(0).ID;
        ID_Equipo = ListaEquipo.get(0).NOMBRE_EQUIPO;
        etID_Equipo.setText(ID_Equipo);
        Tdo_Fila = ListaEquipo.get(0).ID_FILA;
        for (int i = 0; i < ListaFila.size(); i++) {
            if (Tdo_Fila.equals(ListaFila.get(i).getID())) {
                msFila.setText(ListaFila.get(i).getNOMBRE_FILA());
                Nombre_Fila_Temporal = ListaFila.get(i).getNOMBRE_FILA();
                i = ListaFila.size();
            }
        }
        Tdo_Rack = ListaEquipo.get(0).ID_RACK;
        for (int i = 0; i < ListaRack.size(); i++) {
            if (Tdo_Rack.equals(ListaRack.get(i).getID())) {
                msRack.setText(ListaRack.get(i).getNOMBRE_RACK());
                Nombre_Rack_Temporal = ListaRack.get(i).NOMBRE_RACK;
                i = ListaRack.size();
            }
        }
        Tdo_UR = ListaEquipo.get(0).UR;
        Tdo_UR_Temporal = Tdo_UR;
        for (int i = 0; i < ListaUR.size(); i++) {
            if (Tdo_UR.equals(ListaUR.get(i).getID())) {
                Nombre_UR_Temporal = ListaUR.get(i).getUR();
                i = ListaUR.size();
            }
        }
        etMarca.setText(ListaEquipo.get(0).MARCA);
        etModelo.setText(ListaEquipo.get(0).MODELO);
        etSerie.setText(ListaEquipo.get(0).SERIE);
        etCodigoBanco.setText(ListaEquipo.get(0).CODIGO_BANCO);
        Usuario_Ingresa = ListaEquipo.get(0).getUSUARIO_INGRESA();
        Fecha_Ingreso = ListaEquipo.get(0).getFECHA_INGRESO();
    }

    public void LIMPIAR_DATOS_1() {
        msFila.setText("");
        msUR.setText("");
        msRack.setText("");
        msFila.setHint("* Fila");
        msUR.setHint("* UR");
        msRack.setHint("* Rack");
        etID_Equipo.setText("");
        etID_Equipo.requestFocus();
        etMarca.setText("");
        etModelo.setText("");
        etSerie.setText("");
        etCodigoBanco.setText("");
        Bandera1 = 0;
        anterior = 0;
        siguiente = 0;
    }

    public void LIMPIAR_DATOS_2() {
        Tdo_Equipo = "";
        Tdo_Rack = "";
        Tdo_Fila = "";
        Tdo_UR = "";
        Tdo_UR_Temporal = "";
        Nombre_Rack = "";
        Nombre_Fila = "";
        Nombre_UR = "";
        ID_Equipo = "";
        Id_Fila = "";
    }

    public void LIMPIAR_DATOS_3() {
        ListaRack = new ArrayList<Rack>();
        ArrayAdapter<Rack> Adapter1 = new ArrayAdapter<Rack>(this, android.R.layout.simple_spinner_dropdown_item, ListaRack);
        Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msRack.setAdapter(Adapter1);
        ListaUR = new ArrayList<Rack_Ur>();
        ArrayAdapter<Rack_Ur> Adapter2 = new ArrayAdapter<Rack_Ur>(this, android.R.layout.simple_spinner_dropdown_item, ListaUR);
        Adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msUR.setAdapter(Adapter2);
    }

    public void ACTIVAR_DATOS(int OPCION) {
        if (OPCION == 0) {
            /*CUANDO SE PRESIONA NUEVO*/
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            msFila.setEnabled(true);
            msUR.setEnabled(true);
            msRack.setEnabled(true);
            msFila.setClickable(true);
            msUR.setClickable(true);
            msRack.setClickable(true);
            if (ListaFila.size() > 0) {
                msFila.setFocusableInTouchMode(true);
            }

            if (ListaUR.size() > 0) {
                msUR.setFocusableInTouchMode(true);
            }

            if (ListaRack.size() > 0) {
                msRack.setFocusableInTouchMode(true);
            }
            etID_Equipo.setEnabled(true);
            etMarca.setEnabled(true);
            etModelo.setEnabled(true);
            etSerie.setEnabled(true);
            etCodigoBanco.setEnabled(true);
        }
        if (OPCION == 1) {
            /*CUANDO SE PRESIONA GUARDAR*/
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            msFila.setEnabled(false);
            msUR.setEnabled(false);
            msRack.setEnabled(false);
            msFila.setClickable(false);
            msUR.setClickable(false);
            msRack.setClickable(false);
            msFila.setFocusableInTouchMode(false);
            msUR.setFocusableInTouchMode(false);
            msRack.setFocusableInTouchMode(false);
            etID_Equipo.setEnabled(true);
            etMarca.setEnabled(false);
            etModelo.setEnabled(false);
            etSerie.setEnabled(false);
            etCodigoBanco.setEnabled(false);
        }
        if (OPCION == 2) {
            /*CUANDO SE PRESIONA BUSCAR*/
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabDesactivar.setEnabled(true);
            if (ListaEquipo.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            msFila.setEnabled(false);
            msUR.setEnabled(false);
            msRack.setEnabled(false);
            msFila.setClickable(false);
            msUR.setClickable(false);
            msRack.setClickable(false);
            msFila.setFocusableInTouchMode(false);
            msUR.setFocusableInTouchMode(false);
            msRack.setFocusableInTouchMode(false);
            etID_Equipo.setEnabled(true);
            etMarca.setEnabled(false);
            etModelo.setEnabled(false);
            etSerie.setEnabled(false);
            etCodigoBanco.setEnabled(false);
        }
        if (OPCION == 3) {
            /*CUANDO SE PRESIONA MODIFICAR*/
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            msFila.setEnabled(true);
            msUR.setEnabled(true);
            msRack.setEnabled(true);
            msFila.setClickable(true);
            msUR.setClickable(true);
            msRack.setClickable(true);
            msFila.setFocusableInTouchMode(true);
            msUR.setFocusableInTouchMode(true);
            msRack.setFocusableInTouchMode(true);
            msFila.setText(Nombre_Fila_Temporal);
            msRack.setText(Nombre_Rack_Temporal);
            Tdo_UR = Tdo_UR_Temporal;
            msUR.setText(Nombre_UR_Temporal);
            etID_Equipo.setEnabled(true);
            etID_Equipo.setText(ID_Equipo);
            etMarca.setEnabled(true);
            etModelo.setEnabled(true);
            etSerie.setEnabled(true);
            etCodigoBanco.setEnabled(true);
        }
    }

    public void OBTENER_DATOS() {
        ID_Equipo = etID_Equipo.getText().toString().toUpperCase();
        Marca = etMarca.getText().toString().toUpperCase();
        Modelo = etModelo.getText().toString().toUpperCase();
        Serie = etSerie.getText().toString().toUpperCase();
        CodigoBanco = etCodigoBanco.getText().toString().toUpperCase();
    }

    public void CARGAR_FILAS() {
        Crud_Fila DATOS;
        DATOS = Crud_Fila.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            String cliente = "";
            cliente = Access.equals("BP") ? "%BP%" : "%DN%";
            T_Fila = DATOS.CONSULTA_GENERAL_FILA_POR_CLIENTE(cliente);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Fila.moveToFirst() == false) {
            //el cursor está vacío
            return;
        }
        int P_TDO = T_Fila.getColumnIndex(IT_Fila.I_FILA.ID);
        int P_Id_Fila = T_Fila.getColumnIndex(IT_Fila.I_FILA.ID_FILA);
        int P_Nombre_Fila = T_Fila.getColumnIndex(IT_Fila.I_FILA.NOMBRE_FILA);
        ListaFila = new ArrayList<Fila>();
        for (T_Fila.moveToFirst(); !T_Fila.isAfterLast(); T_Fila.moveToNext()) {
            Tdo_Fila = T_Fila.getString(P_TDO);
            Id_Fila = T_Fila.getString(P_Id_Fila);
            Nombre_Fila = T_Fila.getString(P_Nombre_Fila);
            Fila Fila = new Fila(Tdo_Fila, Id_Fila, Nombre_Fila);
            ListaFila.add(Fila);
        }
        Tdo_Fila = "";
        Id_Fila = "";
        Nombre_Fila = "";
        ArrayAdapter<Fila> Adapter = new ArrayAdapter<Fila>(this, android.R.layout.simple_spinner_dropdown_item, ListaFila);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msFila.setAdapter(Adapter);
        ListaRack = new ArrayList<Rack>();
        ArrayAdapter<Rack> Adapter1 = new ArrayAdapter<Rack>(this, android.R.layout.simple_spinner_dropdown_item, ListaRack);
        Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msRack.setAdapter(Adapter1);
        ListaUR = new ArrayList<Rack_Ur>();
        ArrayAdapter<Rack_Ur> Adapter2 = new ArrayAdapter<Rack_Ur>(this, android.R.layout.simple_spinner_dropdown_item, ListaUR);
        Adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msUR.setAdapter(Adapter2);
    }

    public void CARGAR_RACKS() {
        Crud_Rack DATOS;
        DATOS = Crud_Rack.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Rack = DATOS.CONSULTA_GENERAL_POR_ID_FILA(Tdo_Fila);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Rack.moveToFirst() == false) {
            //el cursor está vacío
            ListaRack = new ArrayList<Rack>();
            ArrayAdapter<Rack> Adapter1 = new ArrayAdapter<Rack>(this, android.R.layout.simple_spinner_dropdown_item, ListaRack);
            Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            msRack.setAdapter(Adapter1);
            ListaUR = new ArrayList<Rack_Ur>();
            ArrayAdapter<Rack_Ur> Adapter2 = new ArrayAdapter<Rack_Ur>(this, android.R.layout.simple_spinner_dropdown_item, ListaUR);
            Adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            msUR.setAdapter(Adapter2);
            return;
        }
        int P_TDO = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID);
        int P_Nombre_Rack = T_Rack.getColumnIndex(IT_Rack.I_RACK.NOMBRE_RACK);
        ListaRack = new ArrayList<Rack>();
        for (T_Rack.moveToFirst(); !T_Rack.isAfterLast(); T_Rack.moveToNext()) {
            Tdo_Rack = T_Rack.getString(P_TDO);
            Nombre_Rack = T_Rack.getString(P_Nombre_Rack);
            Rack Rack = new Rack(Tdo_Rack, Nombre_Rack);
            ListaRack.add(Rack);
        }
        Tdo_Rack = "";
        Nombre_Rack = "";
        ArrayAdapter<Rack> Adapter1 = new ArrayAdapter<Rack>(this, android.R.layout.simple_spinner_dropdown_item, ListaRack);
        Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msRack.setAdapter(Adapter1);
        ListaUR = new ArrayList<Rack_Ur>();
        ArrayAdapter<Rack_Ur> Adapter2 = new ArrayAdapter<Rack_Ur>(this, android.R.layout.simple_spinner_dropdown_item, ListaUR);
        Adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msUR.setAdapter(Adapter2);
    }

    public void CARGAR_UR() {
        Crud_Rack_Ur DATOS;
        DATOS = Crud_Rack_Ur.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_UR = DATOS.CONSULTA_GENERAL_RACK_UR_POR_ID_RACK(Tdo_Rack);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_UR.moveToFirst() == false) {
            //el cursor está vacío
            ListaUR = new ArrayList<Rack_Ur>();
            ArrayAdapter<Rack_Ur> Adapter = new ArrayAdapter<Rack_Ur>(this, android.R.layout.simple_spinner_dropdown_item, ListaUR);
            Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            msUR.setAdapter(Adapter);
            return;
        }
        int P_TDO = T_UR.getColumnIndex(IT_Rack_Ur.I_RACK_UR.ID);
        int P_Nombre_UR = T_UR.getColumnIndex(IT_Rack_Ur.I_RACK_UR.UR);
        ListaUR = new ArrayList<Rack_Ur>();
        for (T_UR.moveToFirst(); !T_UR.isAfterLast(); T_UR.moveToNext()) {
            Tdo_UR = T_UR.getString(P_TDO);
            Nombre_UR = T_UR.getString(P_Nombre_UR);
            Rack_Ur UR = new Rack_Ur(Tdo_UR, Nombre_UR);
            ListaUR.add(UR);
        }
        Tdo_UR = "";
        Nombre_UR = "";
        ArrayAdapter<Rack_Ur> Adapter = new ArrayAdapter<Rack_Ur>(this, android.R.layout.simple_spinner_dropdown_item, ListaUR);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msUR.setAdapter(Adapter);
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