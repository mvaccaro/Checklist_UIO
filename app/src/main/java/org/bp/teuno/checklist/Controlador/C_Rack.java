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

import org.bp.teuno.checklist.Modelo.Cantidad_Ur;
import org.bp.teuno.checklist.Modelo.Cliente;
import org.bp.teuno.checklist.Modelo.Fila;
import org.bp.teuno.checklist.Modelo.Rack;
import org.bp.teuno.checklist.Modelo.Rack_Ur;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Cantidad_Ur;
import org.bp.teuno.checklist.SQLite.IT_Cliente;
import org.bp.teuno.checklist.SQLite.IT_Fila;
import org.bp.teuno.checklist.SQLite.IT_Rack;
import org.bp.teuno.checklist.UI.Crud_Cantidad_Ur;
import org.bp.teuno.checklist.UI.Crud_Cliente;
import org.bp.teuno.checklist.UI.Crud_Equipo;
import org.bp.teuno.checklist.UI.Crud_Fila;
import org.bp.teuno.checklist.UI.Crud_Rack;
import org.bp.teuno.checklist.UI.Crud_Rack_Ur;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Rack extends AppCompatActivity {

    private final long interval = 1 * 1000;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabLimpiar, fabDesactivar, fabAtras, fabSiguiente;
    EditText etID_Rack, etMarca, etModelo, etSerie, etCodigoBanco;
    TextInputLayout tilID_Rack, tilMarca, tilModelo, tilSerie, tilCodigoBanco;
    MaterialBetterSpinner msFila, msCliente, msUR;
    Crud_Rack DATOS;
    String Tdo_Rack = "", ID_Fila = "", ID_Rack = "", Id_Cliente = "", Marca = "", Modelo = "", Serie = "", CodigoBanco = "", Usuario_Ingresa = "", Fecha_Ingreso = ""; //PARA EL USO DE Racks
    String Tdo_Cliente = "", Nombre_Cliente = ""; // PARA CARGAR LA LISTA DE Clientes
    String Tdo_UR = "", ID_UR = "", Cantidad_UR = "", Tdo_UR_Temporal = ""; // PARA CARGAR LA LISTA DE Cantidad_UR
    String Tdo_Fila = "", Nombre_Fila = ""; // PARA CARGAR LA LISTA DE Filas
    int Bandera1 = 0, anterior = 0, siguiente = 0;
    Rack Rack;
    ArrayList<Fila> ListaFila = new ArrayList<Fila>();
    ArrayList<Rack> ListaRack = new ArrayList<Rack>();
    ArrayList<Cliente> ListaCliente = new ArrayList<Cliente>();
    ArrayList<Cantidad_Ur> ListaUR = new ArrayList<Cantidad_Ur>();
    Cursor T_Rack, T_Fila, T_Cliente, T_UR;
    String Id_Operador = "";
    String Nombre_Operador, Tdo_Turno, Turno_Operador, Access;
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    private ImageView ImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_rack);

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
        fabDesactivar = (FloatingActionButton) findViewById(R.id.fabDesactivar);
        fabDesactivar.setEnabled(false);
        fabAtras = (FloatingActionButton) findViewById(R.id.fabAtras);
        fabAtras.setEnabled(false);
        fabSiguiente = (FloatingActionButton) findViewById(R.id.fabSiguiente);
        fabSiguiente.setEnabled(false);
        fabModificar.setEnabled(false);

        fabLimpiar = (FloatingActionButton) findViewById(R.id.fabLimpiar);

        //btnLimpiarPantalla = (Button) findViewById(R.id.btnLimpiarPantalla);

        etID_Rack = (EditText) findViewById(R.id.etID_Rack);
        etMarca = (EditText) findViewById(R.id.etMarca);
        etModelo = (EditText) findViewById(R.id.etModelo);
        etSerie = (EditText) findViewById(R.id.etSerie);
        etCodigoBanco = (EditText) findViewById(R.id.etCodigoBanco);

        tilID_Rack = (TextInputLayout) findViewById(R.id.tilID_Rack);
        tilMarca = (TextInputLayout) findViewById(R.id.tilMarca);
        tilModelo = (TextInputLayout) findViewById(R.id.tilModelo);
        tilSerie = (TextInputLayout) findViewById(R.id.tilSerie);
        tilCodigoBanco = (TextInputLayout) findViewById(R.id.tilCodigoBanco);


        msFila = (MaterialBetterSpinner) findViewById(R.id.msFila);
        msCliente = (MaterialBetterSpinner) findViewById(R.id.msCliente);
        msUR = (MaterialBetterSpinner) findViewById(R.id.msUR);

        msFila.setText("");
        msUR.setText("");
        msCliente.setText("");

        msFila.setHint("* Fila");
        msUR.setHint("* Cantidad de UR");
        msCliente.setHint("* Cliente");

        msFila.setEnabled(false);
        msUR.setEnabled(false);
        msCliente.setEnabled(false);
        msFila.setClickable(false);
        msUR.setClickable(false);
        msCliente.setClickable(false);

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");

        etID_Rack.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilID_Rack.setError(null);
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
        CARGAR_CLIENTES();
        CARGAR_UR();

        msFila.setFocusableInTouchMode(false);
        msUR.setFocusableInTouchMode(false);
        msCliente.setFocusableInTouchMode(false);

        msFila.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaFila.size() > 0) {
                    Tdo_Fila = ListaFila.get(position).getID();
                    ID_Fila = ListaFila.get(position).getID_FILA();
                    Nombre_Fila = ListaFila.get(position).getNOMBRE_FILA();
                }
                Log.i("Posicion", String.valueOf(position));
            }

        });

        msCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaCliente.size() > 0) {
                    Tdo_Cliente = ListaCliente.get(position).getID();
                    Id_Cliente = ListaCliente.get(position).getID_CLIENTE();
                }
            }

        });

        msUR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaUR.size() > 0) {
                    Tdo_UR = ListaUR.get(position).getID();
                    ID_UR = ListaUR.get(position).getID_UR();
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
                ListaRack = new ArrayList<>();
                ACTIVAR_DATOS(1);
            }
        });

        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OBTENER_DATOS();
                if (ID_Rack.equals("")) {
                    etID_Rack.setError("Ingrese el nombre del Rack");
                    etID_Rack.requestFocus();
                    return;
                }
                if (Tdo_Fila.equals("")) {
                    msFila.setError("Seleccione la fila");
                    msFila.requestFocus();
                    return;
                }
                if (Tdo_UR.equals("")) {
                    msUR.setError("Seleccione la Ur");
                    msUR.requestFocus();
                    return;
                }
                if (Tdo_Cliente.equals("")) {
                    msCliente.setError("Seleccione el cliente");
                    msCliente.requestFocus();
                    return;
                }
                if (YA_EXISTE_RACK_CON_MISMO_NOMBRE() == true && Bandera1 == 0) {
                    etID_Rack.setError("Ya existe un Rack registrado con este nombre, registre otro");
                    etID_Rack.requestFocus();
                    return;
                }
                if (RACK_TIENE_EQUIPOS() == true && Bandera1 == 1 && !Tdo_UR_Temporal.equals(Tdo_UR)) {
                    msUR.setError("El rack tiene equipos registrados, elimine primero los equipos, antes de cambiar la cantidad de Ur");
                    msUR.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Rack.this);
                    builder.setTitle("Guardar Registro")
                            .setIcon(R.drawable.alerta1)
                            .setMessage("¿Desea guardar el registro?")
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera1 == 0 && !Tdo_UR.matches("")) {
                                                DATOS = Crud_Rack.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    DATOS.GETBD().beginTransaction();
                                                    long insertedResult = DATOS.INSERTAR_RACK(new Rack(null, ID_Rack.toUpperCase(), Tdo_Fila, Tdo_UR, Tdo_Cliente, Marca, Modelo, Serie, CodigoBanco, "A", Id_Operador, "", getDateTime(), ""));
                                                    if (insertedResult == -1) {
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                                                    }
                                                    DATOS.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    DATOS.GETBD().endTransaction();
                                                }
                                                CREAR_URS_EN_RACK();
                                                Toast.makeText(getApplicationContext(), "El registro se guardó correctamente", Toast.LENGTH_LONG).show();
                                                LIMPIAR_DATOS_1();
                                                LIMPIAR_DATOS_2();
                                                ACTIVAR_DATOS(1);
                                            }
                                            if (Bandera1 == 1 && !Tdo_UR.matches("")) {
                                                DATOS = Crud_Rack.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    DATOS.GETBD().beginTransaction();
                                                    long updatedResult = DATOS.MODIFICAR_RACK(new Rack(Tdo_Rack, ID_Rack.toUpperCase(), Tdo_Fila, Tdo_UR, Tdo_Cliente, Marca, Modelo, Serie, CodigoBanco, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                                    if (updatedResult == -1) {
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                                                    }
                                                    DATOS.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    DATOS.GETBD().endTransaction();
                                                }
                                                if (!Tdo_UR_Temporal.equals(Tdo_UR)) {
                                                    ELIMINAR_URS_EN_RACK();
                                                    CREAR_URS_EN_RACK();
                                                }
                                                Toast.makeText(getApplicationContext(), "El registro se guardó correctamente", Toast.LENGTH_LONG).show();
                                                LIMPIAR_DATOS_1();
                                                LIMPIAR_DATOS_2();
                                                ACTIVAR_DATOS(1);
                                            }
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
                String Rack = "";
                if (!ID_Rack.equals("")) {
                    Rack = "%" + ID_Rack + "%";
                } else {
                    Rack = "";
                }
                DATOS = Crud_Rack.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    DATOS.GETBD().beginTransaction();
                    T_Rack = DATOS.CONSULTA_GENERAL_RACK_POR_VALORES(Rack, Tdo_Fila, Tdo_Cliente, Tdo_UR);
                    DATOS.GETBD().setTransactionSuccessful();
                } finally {
                    DATOS.GETBD().endTransaction();
                }
                if (T_Rack.moveToFirst() == false) {
                    //el cursor está vacío
                    Toast.makeText(getApplicationContext(), "No existen datos para mostrar", Toast.LENGTH_LONG).show();
                    LIMPIAR_DATOS_1();
                    LIMPIAR_DATOS_2();
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
                if (RACK_TIENE_EQUIPOS() == true) {
                    etID_Rack.setError("El rack tiene equipos registrados, elimine primero los equipos, antes de desactivarlo");
                    etID_Rack.requestFocus();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Rack.this);
                builder.setTitle("Desactivar Registro")
                        .setMessage("¿Desea desactivar el registro?")
                        .setIcon(R.drawable.alerta1)
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DATOS = Crud_Rack.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            DATOS.GETBD().beginTransaction();
                                            long updatedResult = DATOS.DESACTIVAR_RACK(new Rack(Tdo_Rack, ID_Rack, ID_Fila, ID_UR, Id_Cliente, Marca, Modelo, Serie, CodigoBanco, "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                            if (updatedResult == -1) {
                                                throw new SQLException(String.format("Se generó un error al desactivar el registro en la base de datos"));
                                            }
                                            DATOS.GETBD().setTransactionSuccessful();
                                        } finally {
                                            DATOS.GETBD().endTransaction();
                                        }
                                        Toast.makeText(getApplicationContext(), "El registro se desactivó correctamente", Toast.LENGTH_LONG).show();
                                        LIMPIAR_DATOS_1();
                                        LIMPIAR_DATOS_2();
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
                if (ListaRack.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Rack = ListaRack.get(anterior).ID;
                ID_Rack = ListaRack.get(anterior).NOMBRE_RACK;
                etID_Rack.setText(ID_Rack);
                Tdo_Fila = ListaRack.get(anterior).ID_FILA;
                for (int i = 0; i < ListaFila.size(); i++) {
                    if (Tdo_Fila.equals(ListaFila.get(i).getID())) {
                        msFila.setText(ListaFila.get(i).getNOMBRE_FILA());
                        i = ListaFila.size();
                    }
                }
                Tdo_UR = ListaRack.get(anterior).ID_UR;
                Tdo_UR_Temporal = Tdo_UR;
                for (int i = 0; i < ListaUR.size(); i++) {
                    if (Tdo_UR.equals(ListaUR.get(i).getID())) {
                        msUR.setText(ListaUR.get(i).getCANTIDAD_UR());
                        i = ListaUR.size();
                    }
                }
                Tdo_Cliente = ListaRack.get(anterior).ID_CLIENTE;
                for (int i = 0; i < ListaCliente.size(); i++) {
                    if (Tdo_Cliente.equals(ListaCliente.get(i).getID())) {
                        msCliente.setText(ListaCliente.get(i).getNOMBRE_CLIENTE());
                        i = ListaCliente.size();
                    }
                }
                etMarca.setText(ListaRack.get(anterior).MARCA);
                etModelo.setText(ListaRack.get(anterior).MODELO);
                etSerie.setText(ListaRack.get(anterior).SERIE);
                etCodigoBanco.setText(ListaRack.get(anterior).CODIGO_BANCO);
                Usuario_Ingresa = ListaRack.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaRack.get(anterior).getFECHA_INGRESO();
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaRack.size() == 0) {
                    return;
                }
                if (siguiente == ListaRack.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Rack = ListaRack.get(siguiente).ID;
                ID_Rack = ListaRack.get(siguiente).NOMBRE_RACK;
                etID_Rack.setText(ID_Rack);
                Tdo_Fila = ListaRack.get(siguiente).ID_FILA;
                for (int i = 0; i < ListaFila.size(); i++) {
                    if (Tdo_Fila.equals(ListaFila.get(i).getID())) {
                        msFila.setText(ListaFila.get(i).getNOMBRE_FILA());
                        i = ListaFila.size();
                    }
                }
                Tdo_UR = ListaRack.get(siguiente).ID_UR;
                Tdo_UR_Temporal = Tdo_UR;
                for (int i = 0; i < ListaUR.size(); i++) {
                    if (Tdo_UR.equals(ListaUR.get(i).getID())) {
                        msUR.setText(ListaUR.get(i).getCANTIDAD_UR());
                        i = ListaUR.size();
                    }
                }
                Tdo_Cliente = ListaRack.get(siguiente).ID_CLIENTE;
                for (int i = 0; i < ListaCliente.size(); i++) {
                    if (Tdo_Cliente.equals(ListaCliente.get(i).getID())) {
                        msCliente.setText(ListaCliente.get(i).getNOMBRE_CLIENTE());
                        i = ListaCliente.size();
                    }
                }
                etMarca.setText(ListaRack.get(siguiente).MARCA);
                etModelo.setText(ListaRack.get(siguiente).MODELO);
                etSerie.setText(ListaRack.get(siguiente).SERIE);
                etCodigoBanco.setText(ListaRack.get(siguiente).CODIGO_BANCO);
                Usuario_Ingresa = ListaRack.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaRack.get(siguiente).getFECHA_INGRESO();
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
                Intent intent = new Intent(C_Rack.this, C_Menu_Principal.class);
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

    public boolean YA_EXISTE_RACK_CON_MISMO_NOMBRE() {
        DATOS = Crud_Rack.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Rack = DATOS.CONSULTA_GENERAL_POR_RACK(ID_Rack.toUpperCase());
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Rack.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        } else {
            return true;
        }
    }

    public boolean RACK_TIENE_EQUIPOS() {
        Crud_Equipo DATOS;
        Cursor T_Equipo;
        DATOS = Crud_Equipo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Equipo = DATOS.CONSULTA_GENERAL_EQUIPO_POR_RACK(Tdo_Rack);
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

    public void CREAR_URS_EN_RACK() {
        Crud_Cantidad_Ur DATOS;
        DATOS = Crud_Cantidad_Ur.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_UR = DATOS.CONSULTA_GENERAL_UR_POR_ID(Tdo_UR);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_UR.moveToFirst() == false) {
            //el cursor está vacío
            return;
        }
        int P_Cantidad_UR = T_UR.getColumnIndex(IT_Cantidad_Ur.I_UR.CANTIDAD_UR);
        Cantidad_UR = T_UR.getString(P_Cantidad_UR);
        Crud_Rack_Ur DATOS1;
        DATOS1 = Crud_Rack_Ur.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS1.GETBD().beginTransaction();
            for (int i = 1; i <= Integer.valueOf(Cantidad_UR); i++) {
                if (Bandera1 == 0) {
                    long insertedResult = DATOS1.INSERTAR_RACK_UR(new Rack_Ur(null, Tdo_Rack, String.valueOf(i), "A", Id_Operador, "", getDateTime(), ""));
                    if (insertedResult == -1) {
                        throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                    }
                }
                if (Bandera1 == 1) {
                    long insertedResult = DATOS1.INSERTAR_RACK_UR(new Rack_Ur(null, Tdo_Rack, String.valueOf(i), "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                    if (insertedResult == -1) {
                        throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                    }
                }
            }
            DATOS1.GETBD().setTransactionSuccessful();
        } finally {
            DATOS1.GETBD().endTransaction();
        }
    }

    public void ELIMINAR_URS_EN_RACK() {
        Crud_Rack_Ur DATOS;
        DATOS = Crud_Rack_Ur.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            long deletedResult = DATOS.ELIMINAR_RACK_UR(Tdo_Rack);
            if (deletedResult == -1) {
                throw new SQLException(String.format("Se generó un error al eliminar el registro en la base de datos"));
            }
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
    }


    public void MOSTRAR_DATOS_BUSCADO() {
        LIMPIAR_DATOS_1();
        LIMPIAR_DATOS_2();
        int P_TDO = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID);
        int P_ID_Rack = T_Rack.getColumnIndex(IT_Rack.I_RACK.NOMBRE_RACK);
        int P_Fila = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID_FILA);
        int P_UR = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID_CANTIDAD_UR);
        int P_Id_Cliente = T_Rack.getColumnIndex(IT_Rack.I_RACK.ID_CLIENTE);
        int P_Marca = T_Rack.getColumnIndex(IT_Rack.I_RACK.MARCA);
        int P_Modelo = T_Rack.getColumnIndex(IT_Rack.I_RACK.MODELO);
        int P_Serie = T_Rack.getColumnIndex(IT_Rack.I_RACK.SERIE);
        int P_CodigoBanco = T_Rack.getColumnIndex(IT_Rack.I_RACK.CODIGO_BANCO);
        int P_USUARIO_INGRESA = T_Fila.getColumnIndex(IT_Rack.I_RACK.USUARIO_INGRESA);
        int P_FECHA_INGRESO = T_Fila.getColumnIndex(IT_Rack.I_RACK.FECHA_INGRESO);
        ListaRack = new ArrayList<Rack>();
        for (T_Rack.moveToFirst(); !T_Rack.isAfterLast(); T_Rack.moveToNext()) {
            Tdo_Rack = T_Rack.getString(P_TDO);
            ID_Rack = T_Rack.getString(P_ID_Rack);
            Tdo_Fila = T_Rack.getString(P_Fila);
            Tdo_UR = T_Rack.getString(P_UR);
            Tdo_Cliente = T_Rack.getString(P_Id_Cliente);
            Marca = T_Rack.getString(P_Marca);
            Modelo = T_Rack.getString(P_Modelo);
            Serie = T_Rack.getString(P_Serie);
            CodigoBanco = T_Rack.getString(P_CodigoBanco);
            Usuario_Ingresa = T_Rack.getString(P_USUARIO_INGRESA);
            Fecha_Ingreso = T_Rack.getString(P_FECHA_INGRESO);
            Rack = new Rack(Tdo_Rack, ID_Rack, Tdo_Fila, Tdo_UR, Tdo_Cliente, Marca, Modelo, Serie, CodigoBanco, Usuario_Ingresa, Fecha_Ingreso);
            ListaRack.add(Rack);
        }
        if (ListaRack.size() == 0) {
            return;
        }
        Tdo_Rack = ListaRack.get(0).ID;
        ID_Rack = ListaRack.get(0).NOMBRE_RACK;
        etID_Rack.setText(ID_Rack);
        Tdo_Fila = ListaRack.get(0).ID_FILA;
        for (int i = 0; i < ListaFila.size(); i++) {
            if (Tdo_Fila.equals(ListaFila.get(i).getID())) {
                msFila.setText(ListaFila.get(i).getNOMBRE_FILA());
                i = ListaFila.size();
            }
        }
        Tdo_UR = ListaRack.get(0).ID_UR;
        Tdo_UR_Temporal = Tdo_UR;
        for (int i = 0; i < ListaUR.size(); i++) {
            if (Tdo_UR.equals(ListaUR.get(i).getID())) {
                msUR.setText(ListaUR.get(i).getCANTIDAD_UR());
                i = ListaUR.size();
            }
        }
        Tdo_Cliente = ListaRack.get(0).ID_CLIENTE;
        for (int i = 0; i < ListaCliente.size(); i++) {
            if (Tdo_Cliente.equals(ListaCliente.get(i).getID())) {
                msCliente.setText(ListaCliente.get(i).getNOMBRE_CLIENTE());
                i = ListaCliente.size();
            }
        }
        etMarca.setText(ListaRack.get(0).MARCA);
        etModelo.setText(ListaRack.get(0).MODELO);
        etSerie.setText(ListaRack.get(0).SERIE);
        etCodigoBanco.setText(ListaRack.get(0).CODIGO_BANCO);
        Usuario_Ingresa = ListaRack.get(0).getUSUARIO_INGRESA();
        Fecha_Ingreso = ListaRack.get(0).getFECHA_INGRESO();
    }

    public void LIMPIAR_DATOS_1() {
        msFila.setText("");
        msUR.setText("");
        msCliente.setText("");
        msFila.setHint("* Fila");
        msUR.setHint("* Cantidad de UR");
        msCliente.setHint("* Cliente");
        etID_Rack.setText("");
        etID_Rack.requestFocus();
        etMarca.setText("");
        etModelo.setText("");
        etSerie.setText("");
        etCodigoBanco.setText("");
        Bandera1 = 0;
        anterior = 0;
        siguiente = 0;
    }

    public void LIMPIAR_DATOS_2() {
        Tdo_Rack = "";
        Tdo_Fila = "";
        Tdo_UR = "";
        Tdo_Cliente = "";
        ID_Fila = "";
        ID_Rack = "";
        ID_UR = "";
        Id_Cliente = "";
        Nombre_Cliente = "";
        Nombre_Fila = "";
        Cantidad_UR = "";
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
            msCliente.setEnabled(true);
            msFila.setClickable(true);
            msUR.setClickable(true);
            msCliente.setClickable(true);
            if (ListaFila.size() > 0) {
                msFila.setFocusableInTouchMode(true);
            }

            if (ListaUR.size() > 0) {
                msUR.setFocusableInTouchMode(true);
            }

            if (ListaCliente.size() > 0) {
                msCliente.setFocusableInTouchMode(true);
            }
            etID_Rack.setEnabled(true);
            etID_Rack.requestFocus();
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
            msCliente.setEnabled(false);
            msFila.setClickable(false);
            msUR.setClickable(false);
            msCliente.setClickable(false);
            msFila.setFocusableInTouchMode(false);
            msUR.setFocusableInTouchMode(false);
            msCliente.setFocusableInTouchMode(false);
            etID_Rack.setEnabled(true);
            etID_Rack.requestFocus();
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
            if (ListaRack.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            msFila.setEnabled(false);
            msUR.setEnabled(false);
            msCliente.setEnabled(false);
            msFila.setClickable(false);
            msUR.setClickable(false);
            msCliente.setClickable(false);
            msFila.setFocusableInTouchMode(false);
            msUR.setFocusableInTouchMode(false);
            msCliente.setFocusableInTouchMode(false);
            etID_Rack.setEnabled(true);
            etID_Rack.requestFocus();
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
            msCliente.setEnabled(true);
            msFila.setClickable(true);
            msUR.setClickable(true);
            msCliente.setClickable(true);
            msFila.setFocusableInTouchMode(true);
            msUR.setFocusableInTouchMode(true);
            msCliente.setFocusableInTouchMode(true);
            etID_Rack.setEnabled(false);
            etID_Rack.setText(ID_Rack);
            etMarca.setEnabled(true);
            etModelo.setEnabled(true);
            etSerie.setEnabled(true);
            etCodigoBanco.setEnabled(true);
        }
    }

    public void OBTENER_DATOS() {
        ID_Rack = etID_Rack.getText().toString().toUpperCase();
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
        int P_ID_Fila = T_Fila.getColumnIndex(IT_Fila.I_FILA.ID_FILA);
        int P_Nombre_Fila = T_Fila.getColumnIndex(IT_Fila.I_FILA.NOMBRE_FILA);
        ListaFila = new ArrayList<Fila>();
        for (T_Fila.moveToFirst(); !T_Fila.isAfterLast(); T_Fila.moveToNext()) {
            Tdo_Fila = T_Fila.getString(P_TDO);
            ID_Fila = T_Fila.getString(P_ID_Fila);
            Nombre_Fila = T_Fila.getString(P_Nombre_Fila);
            Fila Fila = new Fila(Tdo_Fila, ID_Fila, Nombre_Fila);
            ListaFila.add(Fila);
        }
        Tdo_Fila = "";
        ID_Fila = "";
        Nombre_Fila = "";
        ArrayAdapter<Fila> Adapter = new ArrayAdapter<Fila>(this, android.R.layout.simple_spinner_dropdown_item, ListaFila);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msFila.setAdapter(Adapter);
    }

    public void CARGAR_CLIENTES() {
        Crud_Cliente DATOS;
        DATOS = Crud_Cliente.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            String cliente = "";
            cliente = Access.equals("BP") ? "%BP%" : "%DN%";
            T_Cliente = DATOS.CONSULTA_GENERAL_CLIENTE_POR_CLIENTE(cliente);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Cliente.moveToFirst() == false) {
            //el cursor está vacío
            return;
        }
        int P_TDO = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.ID);
        int P_Id_Cliente = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.ID_CLIENTE);
        int P_Nombre_Cliente = T_Cliente.getColumnIndex(IT_Cliente.I_CLIENTE.NOMBRE_CLIENTE);
        ListaCliente = new ArrayList<Cliente>();
        for (T_Cliente.moveToFirst(); !T_Cliente.isAfterLast(); T_Cliente.moveToNext()) {
            Tdo_Cliente = T_Cliente.getString(P_TDO);
            Id_Cliente = T_Cliente.getString(P_Id_Cliente);
            Nombre_Cliente = T_Cliente.getString(P_Nombre_Cliente);
            Cliente Cliente = new Cliente(Tdo_Cliente, Id_Cliente, Nombre_Cliente);
            ListaCliente.add(Cliente);
        }
        Tdo_Cliente = "";
        Id_Cliente = "";
        Nombre_Cliente = "";
        ArrayAdapter<Cliente> Adapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_spinner_dropdown_item, ListaCliente);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msCliente.setAdapter(Adapter);
    }

    public void CARGAR_UR() {
        Crud_Cantidad_Ur DATOS;
        DATOS = Crud_Cantidad_Ur.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_UR = DATOS.CONSULTA_GENERAL_UR();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_UR.moveToFirst() == false) {
            //el cursor está vacío
            return;
        }
        int P_TDO = T_UR.getColumnIndex(IT_Cantidad_Ur.I_UR.ID);
        int P_ID_UR = T_UR.getColumnIndex(IT_Cantidad_Ur.I_UR.ID_UR);
        int P_Cantidad_UR = T_UR.getColumnIndex(IT_Cantidad_Ur.I_UR.CANTIDAD_UR);
        ListaUR = new ArrayList<Cantidad_Ur>();
        for (T_UR.moveToFirst(); !T_UR.isAfterLast(); T_UR.moveToNext()) {
            Tdo_UR = T_UR.getString(P_TDO);
            ID_UR = T_UR.getString(P_ID_UR);
            Cantidad_UR = T_UR.getString(P_Cantidad_UR);
            Cantidad_Ur UR = new Cantidad_Ur(Tdo_UR, ID_UR, Cantidad_UR);
            ListaUR.add(UR);
        }
        Tdo_UR = "";
        ID_UR = "";
        Cantidad_UR = "";
        ArrayAdapter<Cantidad_Ur> Adapter = new ArrayAdapter<Cantidad_Ur>(this, android.R.layout.simple_spinner_dropdown_item, ListaUR);
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