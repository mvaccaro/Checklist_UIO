package org.bp.teuno.checklist.Controlador;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.bp.teuno.checklist.Modelo.Alerta;
import org.bp.teuno.checklist.Modelo.Color_Alerta;
import org.bp.teuno.checklist.Modelo.Equipo;
import org.bp.teuno.checklist.Modelo.Fila;
import org.bp.teuno.checklist.Modelo.Rack;
import org.bp.teuno.checklist.Modelo.Rack_Ur;
import org.bp.teuno.checklist.Modelo.Tipo_Alerta;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Alerta;
import org.bp.teuno.checklist.SQLite.IT_Color_Alerta;
import org.bp.teuno.checklist.SQLite.IT_Equipo;
import org.bp.teuno.checklist.SQLite.IT_Fila;
import org.bp.teuno.checklist.SQLite.IT_Rack;
import org.bp.teuno.checklist.SQLite.IT_Rack_Ur;
import org.bp.teuno.checklist.SQLite.IT_Tipo_Alerta;
import org.bp.teuno.checklist.UI.Crud_Alerta;
import org.bp.teuno.checklist.UI.Crud_Color_Alerta;
import org.bp.teuno.checklist.UI.Crud_Equipo;
import org.bp.teuno.checklist.UI.Crud_Fila;
import org.bp.teuno.checklist.UI.Crud_Rack;
import org.bp.teuno.checklist.UI.Crud_Rack_Ur;
import org.bp.teuno.checklist.UI.Crud_Tipo_Alerta;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Alerta extends AppCompatActivity {

    /**
     * Constantes para identificar la acci—n realizada (tomar una fotograf’a
     * o bien seleccionarla de la galer’a)
     */
    private static final int SELECT_PICTURE = 2;
    private final long interval = 1 * 1000;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabGuardar, fabBuscar, fabModificar, fabAtras, fabSiguiente, fabAtras1, fabSiguiente1, fabDesactivar, fabLimpiar, fabSubirFoto, fabVerFoto;
    EditText etID_Equipo, etMarca, etModelo, etSerie, etComentario, etTicket;
    TextInputLayout tilID_Equipo, tilMarca, tilModelo, tilSerie, tilComentario, tilTicket;
    MaterialBetterSpinner msFila, msRack, msUR, msTipoAlerta, msColorAlerta;
    Crud_Alerta DATOS;
    String Tdo_Alerta = "", ID_Fila = "", ID_Rack = "", ID_UR = "", ID_Equipo = "", Comentario = "", Ticket = "", Usuario_Ingresa = "", Fecha_Ingreso = "";
    String Tdo_Equipo = "", Nombre_Equipo = "", Marca = "", Modelo = "", Serie = "";
    String Tdo_Rack = "", Nombre_Rack = "";  //PARA EL USO DE Racks
    String Tdo_UR = "", Tdo_UR_Temporal = "", Nombre_UR = "", Nombre_UR_Temporal; // PARA CARGAR LA LISTA DE Cantidad_UR
    String Tdo_Fila = "", Id_Fila = "", Nombre_Fila = ""; // PARA CARGAR LA LISTA DE Filas
    String Tdo_Tipo_Alerta = "", ID_Tipo_Alerta = "", Nombre_Tipo_Alerta = "";
    String Tdo_Color_Alerta = "", ID_Color_Alerta = "", Nombre_Color_Alerta = "";
    int Bandera1 = 0, anterior = 0, siguiente = 0, anterior1 = 0, siguiente1 = 0;
    Alerta Alerta;
    ArrayList<Alerta> ListaAlerta = new ArrayList<Alerta>();
    ArrayList<Equipo> ListaEquipo = new ArrayList<Equipo>();
    ArrayList<Fila> ListaFila = new ArrayList<Fila>();
    ArrayList<Rack> ListaRack = new ArrayList<Rack>();
    ArrayList<Rack_Ur> ListaUR = new ArrayList<Rack_Ur>();
    ArrayList<Tipo_Alerta> ListaTipoAlerta = new ArrayList<Tipo_Alerta>();
    ArrayList<Color_Alerta> ListaColorAlerta = new ArrayList<Color_Alerta>();
    Cursor T_Alerta, T_Equipo, T_Fila, T_Rack, T_UR, T_Tipo_Alerta, T_Color_Alerta;
    String Id_Operador = "", Nombre_Operador, Tdo_Turno, Turno_Operador, isMenuA56, Access;
    int bandera = 0;
    CountDownTimer countDownTimer;
    private Bitmap bitmap;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME
    /**
     * Variable que define el nombre para el archivo donde escribiremos
     * la fotograf’a de tama–o completo al tomarla.
     */
    private PopupWindow mPopupWindow;
    private RelativeLayout mRelativeLayout;
    private Context mContext;
    private Activity mActivity;

    private ImageView Image, ImageView2;

    private byte[] photo;

    private String timeA56 = "", tiempo_inicio = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_alerta);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        mContext = getApplicationContext();
        mActivity = this;
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);

        Image = (ImageView) findViewById(R.id.imgView1);
        ImageView2 = (ImageView) findViewById(R.id.imageView2);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        isMenuA56 = bundle.getString("IsMenuA56").toString();
        Access = bundle.getString("Access").toString();
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();
        timeA56 = bundle.getString("Tiempo_A56").toString();

        tiempo_inicio = getDateTime();

        if (Access.equals("BP")) {
            ImageView2.setImageResource(R.drawable.bp2);
        } else {
            ImageView2.setImageResource(R.drawable.dn2);
        }

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabNuevo = (FloatingActionButton) findViewById(R.id.fabNuevo);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabGuardar.setEnabled(false);
        fabBuscar = (FloatingActionButton) findViewById(R.id.fabBuscar);
        fabModificar = (FloatingActionButton) findViewById(R.id.fabModificar);
        fabModificar.setEnabled(false);
        fabAtras = (FloatingActionButton) findViewById(R.id.fabAtras);
        fabAtras.setEnabled(false);
        fabSiguiente = (FloatingActionButton) findViewById(R.id.fabSiguiente);
        fabSiguiente.setEnabled(false);
        fabAtras1 = (FloatingActionButton) findViewById(R.id.fabAtras1);
        fabAtras1.setEnabled(false);
        fabSiguiente1 = (FloatingActionButton) findViewById(R.id.fabSiguiente1);
        fabSiguiente1.setEnabled(false);
        fabDesactivar = (FloatingActionButton) findViewById(R.id.fabDesactivar);
        fabDesactivar.setEnabled(false);
        fabLimpiar = (FloatingActionButton) findViewById(R.id.fabLimpiar);

        fabSubirFoto = (FloatingActionButton) findViewById(R.id.fabSubirFoto);
        fabSubirFoto.setEnabled(false);
        fabVerFoto = (FloatingActionButton) findViewById(R.id.fabVerFoto);
        fabVerFoto.setEnabled(false);

        etID_Equipo = (EditText) findViewById(R.id.etID_Equipo);
        etMarca = (EditText) findViewById(R.id.etMarca);
        etModelo = (EditText) findViewById(R.id.etModelo);
        etSerie = (EditText) findViewById(R.id.etSerie);
        etComentario = (EditText) findViewById(R.id.etComentario);
        etTicket = (EditText) findViewById(R.id.etTicket);

        tilID_Equipo = (TextInputLayout) findViewById(R.id.tilID_Equipo);
        tilMarca = (TextInputLayout) findViewById(R.id.tilMarca);
        tilModelo = (TextInputLayout) findViewById(R.id.tilModelo);
        tilSerie = (TextInputLayout) findViewById(R.id.tilSerie);
        tilComentario = (TextInputLayout) findViewById(R.id.tilComentario);
        tilTicket = (TextInputLayout) findViewById(R.id.tilTicket);

        msFila = (MaterialBetterSpinner) findViewById(R.id.msFila);
        msRack = (MaterialBetterSpinner) findViewById(R.id.msRack);
        msUR = (MaterialBetterSpinner) findViewById(R.id.msUR);
        msTipoAlerta = (MaterialBetterSpinner) findViewById(R.id.msTipoAlerta);
        msColorAlerta = (MaterialBetterSpinner) findViewById(R.id.msColorAlerta);

        msFila.setText("");
        msUR.setText("");
        msRack.setText("");
        msTipoAlerta.setText("");
        msColorAlerta.setText("");

        msFila.setHint("* Fila");
        msRack.setHint("* Rack");
        msUR.setHint("* UR");
        msTipoAlerta.setHint("* Tipo de la alerta");
        msColorAlerta.setHint("* Color de la alerta");

        msFila.setEnabled(false);
        msUR.setEnabled(false);
        msRack.setEnabled(false);
        msTipoAlerta.setEnabled(false);
        msColorAlerta.setEnabled(false);

        msFila.setClickable(false);
        msUR.setClickable(false);
        msRack.setClickable(false);
        msTipoAlerta.setClickable(false);
        msColorAlerta.setClickable(false);

        msFila.setFocusableInTouchMode(false);
        msUR.setFocusableInTouchMode(false);
        msRack.setFocusableInTouchMode(false);
        msTipoAlerta.setFocusableInTouchMode(false);
        msColorAlerta.setFocusableInTouchMode(false);

        etComentario.requestFocus();

        TooltipCompat.setTooltipText(fabNuevo, "Nuevo");
        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabBuscar, "Buscar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");
        TooltipCompat.setTooltipText(fabLimpiar, "Limpiar");
        TooltipCompat.setTooltipText(fabAtras, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente, "Siguiente");
        TooltipCompat.setTooltipText(fabAtras1, "Anterior");
        TooltipCompat.setTooltipText(fabSiguiente1, "Siguiente");
        TooltipCompat.setTooltipText(fabDesactivar, "Desactivar");
        TooltipCompat.setTooltipText(fabSubirFoto, "Cargar Foto");
        TooltipCompat.setTooltipText(fabVerFoto, "Ver Foto");

        /*METODO PARA CARGAR LAS LISTAS EN LOS SPINNER*/
        CARGAR_TIPO_ALERTAS();
        CARGAR_COLOR_ALERTAS();
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
                    etID_Equipo.setText("");
                    etMarca.setText("");
                    etModelo.setText("");
                    etSerie.setText("");
                    Tdo_Equipo = "";
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
                    etID_Equipo.setText("");
                    etMarca.setText("");
                    etModelo.setText("");
                    etSerie.setText("");
                    Tdo_Equipo = "";
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
                    CARGAR_EQUIPO("1");
                    if (ListaEquipo.size() > 0) {
                        Tdo_Equipo = ListaEquipo.get(0).getID();
                        etID_Equipo.setText(ListaEquipo.get(0).getNOMBRE_EQUIPO());
                        etMarca.setText(ListaEquipo.get(0).getMARCA());
                        etModelo.setText(ListaEquipo.get(0).getMODELO());
                        etSerie.setText(ListaEquipo.get(0).getSERIE());
                    } else {
                        Tdo_Equipo = "";
                        etID_Equipo.setText("");
                        etMarca.setText("");
                        etModelo.setText("");
                        etSerie.setText("");
                    }
                }
            }
        });

        msTipoAlerta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaTipoAlerta.size() > 0) {
                    Tdo_Tipo_Alerta = ListaTipoAlerta.get(position).getID();
                    ID_Tipo_Alerta = ListaTipoAlerta.get(position).getID_TIPO_ALERTA();
                    Nombre_Tipo_Alerta = ListaTipoAlerta.get(position).getNOMBRE_TIPO_ALERTA();
                }
            }
        });

        msColorAlerta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (ListaColorAlerta.size() > 0) {
                    Tdo_Color_Alerta = ListaColorAlerta.get(position).getID();
                    ID_Color_Alerta = ListaColorAlerta.get(position).getID_COLOR_ALERTA();
                    Nombre_Color_Alerta = ListaColorAlerta.get(position).getNOMBRE_COLOR_ALERTA();
                }
            }
        });

        /**
         * Para todos los casos es necesario un intent, si accesamos la c‡mara con la acci—n
         * ACTION_IMAGE_CAPTURE, si accesamos la galer’a con la acci—n ACTION_PICK.
         * En el caso de la vista previa (thumbnail) no se necesita m‡s que el intent,
         * el c—digo e iniciar la actividad. Por eso inicializamos las variables intent y
         * code con los valores necesarios para el caso del thumbnail, as’ si ese es el
         * bot—n seleccionado no validamos nada en un if.
         */

        /**
         * Si la opci—n seleccionada es fotograf’a completa, necesitamos un archivo donde
         * guardarla
         */

        fabSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                int code = SELECT_PICTURE;
                /**
                 * Luego, con todo preparado iniciamos la actividad correspondiente.
                 */
                startActivityForResult(
                        Intent.createChooser(intent, "Seleccione una imagen"),
                        SELECT_PICTURE);
            }
        });

        fabVerFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandera = bandera + 1;
                if (bandera == 1) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View customView = inflater.inflate(R.layout.l_foto, null);

                    mPopupWindow = new PopupWindow(
                            customView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    // Set an elevation value for popup window
                    // Call requires API level 21
                    if (Build.VERSION.SDK_INT >= 21) {
                        mPopupWindow.setElevation(5.0f);
                    }

                    // Get a reference for the custom view close button
                    ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                    ImageButton rotarButton = (ImageButton) customView.findViewById(R.id.ib_rotar);
                    final ImageView image = (ImageView) customView.findViewById(R.id.imgView);

                    Drawable dr = ((ImageView) Image).getDrawable();
                    final Bitmap bitmap = ((BitmapDrawable) dr.getCurrent()).getBitmap();

                    if (bitmap != null) {
                        image.setImageBitmap(bitmap);
                    } else {
                        bandera = 0;
                        Toast.makeText(getApplicationContext(), "Debe primero cargar la foto", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Set a click listener for the popup window close button
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Dismiss the popup window
                            mPopupWindow.dismiss();
                            bandera = 0;
                        }
                    });

                    rotarButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rotarImagen(bitmap, image);
                        }
                    });

                    mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 0);
                }
            }
        });

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bandera1 = 0;
                LIMPIAR_DATOS_1();
                LIMPIAR_DATOS_2();
                LIMPIAR_DATOS_3();
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

        fabDesactivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(C_Alerta.this);
                builder.setTitle("Desactivar Registro")
                        .setIcon(R.drawable.alerta1)
                        .setMessage("¿Desea desactivar el registro?")
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DATOS = Crud_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                                        try {
                                            DATOS.GETBD().beginTransaction();
                                            long updatedResult = DATOS.DESACTIVAR_ALERTA(new Alerta(Tdo_Alerta, Comentario.toUpperCase(), "I", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                            if (updatedResult == -1) {
                                                throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                                            }
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
                if (Tdo_Equipo.equals("")) {
                    msUR.setError("No hay un equipo en la UR seleccionada, escoja otra");
                    msUR.requestFocus();
                    return;
                }
                if (Tdo_Tipo_Alerta.equals("")) {
                    msTipoAlerta.setError("Seleccione el tipo de alerta");
                    msTipoAlerta.requestFocus();
                    return;
                }
                if (Tdo_Color_Alerta.equals("")) {
                    msColorAlerta.setError("Seleccione el color de la alerta");
                    msColorAlerta.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Alerta.this);
                    builder.setTitle("Guardar Registro")
                            .setIcon(R.drawable.alerta1)
                            .setMessage("¿Desea guardar el registro?")
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera1 == 0) {
                                                DATOS = Crud_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    DATOS.GETBD().beginTransaction();
                                                    String cliente = "";
                                                    cliente = Access.equals("BP") ? "BP" : "DN";
                                                    long insertedResult = DATOS.INSERTAR_ALERTA(new Alerta(null, Tdo_Fila, Tdo_Rack, Tdo_UR, Tdo_Equipo, Tdo_Tipo_Alerta, Tdo_Color_Alerta, Comentario, Ticket, photo, "A", Id_Operador, "", getDateTime(), ""), cliente);
                                                    if (insertedResult == -1) {
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                                                    }
                                                    DATOS.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    DATOS.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera1 == 1) {
                                                DATOS = Crud_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    DATOS.GETBD().beginTransaction();
                                                    long updatedResult = DATOS.MODIFICAR_ALERTA(new Alerta(Tdo_Alerta, Tdo_Fila, Tdo_Rack, Tdo_UR, Tdo_Equipo, Tdo_Tipo_Alerta, Tdo_Color_Alerta, Comentario, Ticket, photo, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                                    if (updatedResult == -1) {
                                                        throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
                                                    }
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
                Image.invalidate();
                Image.setImageBitmap(null);
                OBTENER_DATOS();
                DATOS = Crud_Alerta.OBTENER_INSTANCIA(getApplicationContext());
                try {
                    DATOS.GETBD().beginTransaction();
                    T_Alerta = DATOS.CONSULTA_GENERAL_POR_CONDICION(Tdo_Fila, Tdo_Rack, Tdo_UR, Tdo_Equipo, "A", 1);
                    DATOS.GETBD().setTransactionSuccessful();
                } finally {
                    DATOS.GETBD().endTransaction();
                }
                if (T_Alerta.moveToFirst() == false) {
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

        fabAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaAlerta.size() == 0) {
                    return;
                }
                if (anterior == 0) {
                    return;
                }
                anterior = siguiente - 1;
                siguiente--;
                Tdo_Alerta = ListaAlerta.get(anterior).ID;
                Tdo_Fila = ListaAlerta.get(anterior).ID_FILA;
                Tdo_Rack = ListaAlerta.get(anterior).ID_RACK;
                Tdo_UR = ListaAlerta.get(anterior).UR;
                Tdo_UR_Temporal = Tdo_UR;
                for (int i = 0; i < ListaUR.size(); i++) {
                    if (Tdo_UR.equals(ListaUR.get(i).getID())) {
                        Nombre_UR_Temporal = ListaUR.get(i).getUR();
                        i = ListaUR.size();
                    }
                }
                Tdo_Equipo = ListaAlerta.get(anterior).ID_EQUIPO;
                Tdo_Tipo_Alerta = ListaAlerta.get(anterior).ID_TIPO_ALERTA;
                for (int i = 0; i < ListaTipoAlerta.size(); i++) {
                    if (Tdo_Tipo_Alerta.equals(ListaTipoAlerta.get(i).getID())) {
                        msTipoAlerta.setText(ListaTipoAlerta.get(i).NOMBRE_TIPO_ALERTA);
                        i = ListaTipoAlerta.size();
                    }
                }
                Tdo_Color_Alerta = ListaAlerta.get(anterior).ID_COLOR_ALERTA;
                for (int i = 0; i < ListaColorAlerta.size(); i++) {
                    if (Tdo_Color_Alerta.equals(ListaColorAlerta.get(i).getID())) {
                        msColorAlerta.setText(ListaColorAlerta.get(i).NOMBRE_COLOR_ALERTA);
                        i = ListaColorAlerta.size();
                    }
                }
                etComentario.setText(ListaAlerta.get(anterior).COMENTARIO);
                etTicket.setText(ListaAlerta.get(anterior).TICKET_EXTERNO);

                if (ListaAlerta.get(anterior).IMAGEN != null) {
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(ListaAlerta.get(anterior).IMAGEN);
                    Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                    Image.setImageBitmap(theImage);
                } else {
                    Image.invalidate();
                    Image.setImageBitmap(null);
                }

                Usuario_Ingresa = ListaAlerta.get(anterior).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaAlerta.get(anterior).getFECHA_INGRESO();
            }
        });

        fabAtras1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaEquipo.size() == 0) {
                    return;
                }
                if (anterior1 == 0) {
                    return;
                }
                anterior1 = siguiente1 - 1;
                siguiente1--;
                Tdo_Equipo = ListaEquipo.get(anterior1).ID;
                etID_Equipo.setText(ListaEquipo.get(anterior1).NOMBRE_EQUIPO);
                etMarca.setText(ListaEquipo.get(anterior1).MARCA);
                etModelo.setText(ListaEquipo.get(anterior1).MODELO);
                etSerie.setText(ListaEquipo.get(anterior1).SERIE);
            }
        });

        fabSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaAlerta.size() == 0) {
                    return;
                }
                if (siguiente == ListaAlerta.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente = anterior + 1;
                anterior++;
                Tdo_Alerta = ListaAlerta.get(siguiente).ID;
                Tdo_Fila = ListaAlerta.get(siguiente).ID_FILA;
                Tdo_Rack = ListaAlerta.get(siguiente).ID_RACK;
                Tdo_UR = ListaAlerta.get(siguiente).UR;
                Tdo_UR_Temporal = Tdo_UR;
                for (int i = 0; i < ListaUR.size(); i++) {
                    if (Tdo_UR.equals(ListaUR.get(i).getID())) {
                        Nombre_UR_Temporal = ListaUR.get(i).getUR();
                        i = ListaUR.size();
                    }
                }
                Tdo_Equipo = ListaAlerta.get(siguiente).ID_EQUIPO;
                Tdo_Tipo_Alerta = ListaAlerta.get(siguiente).ID_TIPO_ALERTA;
                for (int i = 0; i < ListaTipoAlerta.size(); i++) {
                    if (Tdo_Tipo_Alerta.equals(ListaTipoAlerta.get(i).getID())) {
                        msTipoAlerta.setText(ListaTipoAlerta.get(i).NOMBRE_TIPO_ALERTA);
                        i = ListaTipoAlerta.size();
                    }
                }
                Tdo_Color_Alerta = ListaAlerta.get(siguiente).ID_COLOR_ALERTA;
                for (int i = 0; i < ListaColorAlerta.size(); i++) {
                    if (Tdo_Color_Alerta.equals(ListaColorAlerta.get(i).getID())) {
                        msColorAlerta.setText(ListaColorAlerta.get(i).NOMBRE_COLOR_ALERTA);
                        i = ListaColorAlerta.size();
                    }
                }
                etComentario.setText(ListaAlerta.get(siguiente).COMENTARIO);
                etTicket.setText(ListaAlerta.get(siguiente).TICKET_EXTERNO);

                if (ListaAlerta.get(siguiente).IMAGEN != null) {
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(ListaAlerta.get(siguiente).IMAGEN);
                    Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                    Image.setImageBitmap(theImage);
                } else {
                    Image.invalidate();
                    Image.setImageBitmap(null);
                }

                Usuario_Ingresa = ListaAlerta.get(siguiente).getUSUARIO_INGRESA();
                Fecha_Ingreso = ListaAlerta.get(siguiente).getFECHA_INGRESO();
            }
        });

        fabSiguiente1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListaEquipo.size() == 0) {
                    return;
                }
                if (siguiente1 == ListaEquipo.size() - 1) {
                    Toast.makeText(getApplicationContext(), "Ya no existen mas registros ", Toast.LENGTH_LONG).show();
                    return;
                }
                siguiente1 = anterior1 + 1;
                anterior1++;
                Tdo_Equipo = ListaEquipo.get(siguiente1).ID;
                etID_Equipo.setText(ListaEquipo.get(siguiente1).NOMBRE_EQUIPO);
                etMarca.setText(ListaEquipo.get(siguiente1).MARCA);
                etModelo.setText(ListaEquipo.get(siguiente1).MODELO);
                etSerie.setText(ListaEquipo.get(siguiente1).SERIE);
            }
        });

        fabLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LIMPIAR_DATOS_1();
                LIMPIAR_DATOS_2();
                LIMPIAR_DATOS_3();
                ListaAlerta = new ArrayList<>();
                ACTIVAR_DATOS(1);
            }
        });

    }

    /**
     * Funci—n que se ejecuta cuando concluye el intent en el que se solicita una imagen
     * ya sea de la c‡mara o de la galer’a
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        /**
         * Se revisa si la imagen viene de la c‡mara (TAKE_PICTURE) o de la galer’a (SELECT_PICTURE)
         */
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;
        String filePath = null;
        switch (requestCode) {
            case SELECT_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath = selectedImage.getPath();
                    if (requestCode == SELECT_PICTURE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            // Transformamos la URI de la imagen a inputStream y este a un Bitmap
                            bitmap = BitmapFactory.decodeStream(imageStream);
                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista
                            if (bitmap != null) {
                                Bitmap bitmap1 = redimensionarImagenMaximo(bitmap, 800, 600);
                                Image.setImageBitmap(bitmap1);
                            }
                        }
                    }
                }
                break;
        }
    }

    private void rotarImagen(Bitmap bitmap, ImageView image) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        image.setImageBitmap(rotated);
    }

    @Override
    public void onBackPressed() {
        if (isMenuA56.equals("1")) {
            AlertDialog.Builder myBulid = new AlertDialog.Builder(this);
            myBulid.setTitle("Regresar");
            myBulid.setIcon(R.drawable.salir);
            myBulid.setMessage("¿Desea regresar a la pantalla del menú principal?");
            myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(C_Alerta.this, C_Menu_Principal.class);
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
        } else if (isMenuA56.equals("0")) {
            AlertDialog.Builder myBulid = new AlertDialog.Builder(this);
            myBulid.setTitle("Regresar");
            myBulid.setIcon(R.drawable.salir);
            myBulid.setMessage("¿Desea regresar al formulario A.56?");
            myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(C_Alerta.this, C_A56.class);
                    intent.putExtra("Id_Operador", Id_Operador);
                    intent.putExtra("Nombre_Operador", Nombre_Operador);
                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                    intent.putExtra("Turno_Operador", Turno_Operador);
                    intent.putExtra("IsMenuA56", isMenuA56);
                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                    intent.putExtra("Access", Access);
                    try {
                        intent.putExtra("Tiempo_A56", String.valueOf(Integer.valueOf(timeA56) + Integer.valueOf(getTimeAlerta())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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
    }

    public void MOSTRAR_DATOS_BUSCADO() {
        LIMPIAR_DATOS_1();
        LIMPIAR_DATOS_2();
        int P_TDO = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID);
        int P_ID_Fila = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_FILA);
        int P_ID_Rack = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_RACK);
        int P_ID_UR = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_RACK_UR);
        int P_ID_Equipo = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_EQUIPO);
        int P_ID_Tipo_Alerta = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_TIPO_ALERTA);
        int P_ID_Color_Alerta = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.ID_COLOR_ALERTA);
        int P_Comentario = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.COMENTARIO);
        int P_Ticket = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.TICKET_EXTERNO);
        int P_Imagen = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.IMAGEN);
        int P_USUARIO_INGRESA = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.USUARIO_INGRESA);
        int P_FECHA_INGRESO = T_Alerta.getColumnIndex(IT_Alerta.I_ALERTA.FECHA_INGRESO);
        ListaAlerta = new ArrayList<Alerta>();
        for (T_Alerta.moveToFirst(); !T_Alerta.isAfterLast(); T_Alerta.moveToNext()) {
            Tdo_Alerta = T_Alerta.getString(P_TDO);
            Tdo_Fila = T_Alerta.getString(P_ID_Fila);
            Tdo_Rack = T_Alerta.getString(P_ID_Rack);
            Tdo_UR = T_Alerta.getString(P_ID_UR);
            Tdo_Equipo = T_Alerta.getString(P_ID_Equipo);
            Tdo_Tipo_Alerta = T_Alerta.getString(P_ID_Tipo_Alerta);
            Tdo_Color_Alerta = T_Alerta.getString(P_ID_Color_Alerta);
            Comentario = T_Alerta.getString(P_Comentario);
            Ticket = T_Alerta.getString(P_Ticket);
            photo = T_Alerta.getBlob(P_Imagen);
            Usuario_Ingresa = T_Alerta.getString(P_USUARIO_INGRESA);
            Fecha_Ingreso = T_Alerta.getString(P_FECHA_INGRESO);
            Alerta = new Alerta(Tdo_Alerta, Tdo_Fila, Tdo_Rack, Tdo_UR, Tdo_Equipo, Tdo_Tipo_Alerta, Tdo_Color_Alerta, Comentario, Ticket, photo, Usuario_Ingresa, Fecha_Ingreso);
            ListaAlerta.add(Alerta);
        }
        if (ListaAlerta.size() == 0) {
            return;
        }
        Tdo_Alerta = ListaAlerta.get(0).ID;

        Tdo_Fila = ListaAlerta.get(0).ID_FILA;
        for (int i = 0; i < ListaFila.size(); i++) {
            if (Tdo_Fila.equals(ListaFila.get(i).getID())) {
                msFila.setText(ListaFila.get(i).getNOMBRE_FILA());
                i = ListaFila.size();
            }
        }
        Tdo_Rack = ListaAlerta.get(0).ID_RACK;
        for (int i = 0; i < ListaRack.size(); i++) {
            if (Tdo_Rack.equals(ListaRack.get(i).getID())) {
                msRack.setText(ListaRack.get(i).getNOMBRE_RACK());
                i = ListaRack.size();
            }
        }
        Tdo_UR = ListaAlerta.get(0).UR;
        Tdo_UR_Temporal = Tdo_UR;
        for (int i = 0; i < ListaUR.size(); i++) {
            if (Tdo_UR.equals(ListaUR.get(i).getID())) {
                Nombre_UR_Temporal = ListaUR.get(i).getUR();
                i = ListaUR.size();
            }
        }
        Tdo_Equipo = ListaAlerta.get(0).ID_EQUIPO;
        for (int i = 0; i < ListaEquipo.size(); i++) {
            if (Tdo_Equipo.equals(ListaEquipo.get(i).getID())) {
                etID_Equipo.setText(ListaEquipo.get(i).getNOMBRE_EQUIPO());
                etMarca.setText(ListaEquipo.get(i).getMARCA());
                etModelo.setText(ListaEquipo.get(i).getMODELO());
                etSerie.setText(ListaEquipo.get(i).getSERIE());
                i = ListaEquipo.size();
            }
        }
        Tdo_Tipo_Alerta = ListaAlerta.get(0).getID_TIPO_ALERTA();
        for (int i = 0; i < ListaTipoAlerta.size(); i++) {
            if (Tdo_Tipo_Alerta.equals(ListaTipoAlerta.get(i).getID())) {
                msTipoAlerta.setText(ListaTipoAlerta.get(i).getNOMBRE_TIPO_ALERTA());
                i = ListaTipoAlerta.size();
            }
        }
        Tdo_Color_Alerta = ListaAlerta.get(0).getID_COLOR_ALERTA();
        for (int i = 0; i < ListaColorAlerta.size(); i++) {
            if (Tdo_Color_Alerta.equals(ListaColorAlerta.get(i).getID())) {
                msColorAlerta.setText(ListaColorAlerta.get(i).getNOMBRE_COLOR_ALERTA());
                i = ListaColorAlerta.size();
            }
        }
        etComentario.setText(ListaAlerta.get(0).getCOMENTARIO());
        etTicket.setText(ListaAlerta.get(0).getTICKET_EXTERNO());

        if (ListaAlerta.get(0).getIMAGEN() != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(ListaAlerta.get(0).getIMAGEN());
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            Image.setImageBitmap(theImage);
        } else {
            Image.invalidate();
            Image.setImageBitmap(null);
        }

        Usuario_Ingresa = ListaAlerta.get(0).getUSUARIO_INGRESA();
        Fecha_Ingreso = ListaAlerta.get(0).getFECHA_INGRESO();
    }

    public void LIMPIAR_DATOS_1() {
        msFila.setText("");
        msFila.requestFocus();
        msUR.setText("");
        msRack.setText("");
        msTipoAlerta.setText("");
        msColorAlerta.setText("");
        msFila.setHint("* Fila");
        msUR.setHint("* UR");
        msRack.setHint("* Rack");
        msTipoAlerta.setHint("* Tipo de la alerta");
        msColorAlerta.setHint("* Color de la alerta");
        etID_Equipo.setText("");
        etMarca.setText("");
        etModelo.setText("");
        etSerie.setText("");
        etComentario.setText("");
        etTicket.setText("");
        Bandera1 = 0;
        anterior = 0;
        siguiente = 0;
        anterior1 = 0;
        siguiente1 = 0;
        bitmap = null;
        Image.invalidate();
        Image.setImageBitmap(null);
        fabSiguiente1.setEnabled(false);
        fabAtras1.setEnabled(false);
    }

    public void LIMPIAR_DATOS_2() {
        Tdo_Alerta = "";
        Tdo_Equipo = "";
        Tdo_Rack = "";
        Tdo_Fila = "";
        Tdo_UR = "";
        Tdo_Tipo_Alerta = "";
        Tdo_Color_Alerta = "";
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
            fabModificar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabDesactivar.setEnabled(false);
            msFila.setEnabled(true);
            msUR.setEnabled(true);
            msRack.setEnabled(true);
            msTipoAlerta.setEnabled(true);
            msColorAlerta.setEnabled(true);
            msFila.setClickable(true);
            msUR.setClickable(true);
            msRack.setClickable(true);
            msTipoAlerta.setClickable(true);
            msColorAlerta.setClickable(true);
            fabSubirFoto.setEnabled(true);
            fabVerFoto.setEnabled(true);
            if (ListaFila.size() > 0) {
                msFila.setFocusableInTouchMode(true);
            }

            if (ListaUR.size() > 0) {
                msUR.setFocusableInTouchMode(true);
            }

            if (ListaRack.size() > 0) {
                msRack.setFocusableInTouchMode(true);
            }
            if (ListaTipoAlerta.size() > 0) {
                msTipoAlerta.setFocusableInTouchMode(true);
            }
            if (ListaColorAlerta.size() > 0) {
                msColorAlerta.setFocusableInTouchMode(true);
            }
            etComentario.setEnabled(true);
            etTicket.setEnabled(true);
        }
        if (OPCION == 1) {
            /*CUANDO SE PRESIONA GUARDAR*/
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSubirFoto.setEnabled(false);
            fabVerFoto.setEnabled(false);
            msFila.setEnabled(false);
            msUR.setEnabled(false);
            msRack.setEnabled(false);
            msTipoAlerta.setEnabled(false);
            msColorAlerta.setEnabled(false);
            msFila.setClickable(false);
            msUR.setClickable(false);
            msRack.setClickable(false);
            msTipoAlerta.setClickable(false);
            msColorAlerta.setClickable(false);
            msFila.setFocusableInTouchMode(false);
            msUR.setFocusableInTouchMode(false);
            msRack.setFocusableInTouchMode(false);
            msTipoAlerta.setFocusableInTouchMode(false);
            msColorAlerta.setFocusableInTouchMode(false);
            etComentario.setEnabled(false);
            etTicket.setEnabled(false);
        }
        if (OPCION == 2) {
            /*CUANDO SE PRESIONA BUSCAR*/
            fabNuevo.setEnabled(true);
            fabBuscar.setEnabled(true);
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            fabSubirFoto.setEnabled(true);
            fabVerFoto.setEnabled(true);
            if (ListaAlerta.size() > 1) {
                fabSiguiente.setEnabled(true);
                fabAtras.setEnabled(true);
            } else {
                fabSiguiente.setEnabled(false);
                fabAtras.setEnabled(false);
            }
            fabDesactivar.setEnabled(true);
            msFila.setEnabled(false);
            msUR.setEnabled(false);
            msRack.setEnabled(false);
            msTipoAlerta.setEnabled(false);
            msColorAlerta.setEnabled(false);
            msFila.setClickable(false);
            msUR.setClickable(false);
            msRack.setClickable(false);
            msTipoAlerta.setClickable(false);
            msColorAlerta.setClickable(false);
            msFila.setFocusableInTouchMode(false);
            msUR.setFocusableInTouchMode(false);
            msRack.setFocusableInTouchMode(false);
            msTipoAlerta.setFocusableInTouchMode(false);
            msColorAlerta.setFocusableInTouchMode(false);
            etComentario.setEnabled(false);
            etTicket.setEnabled(false);
        }
        if (OPCION == 3) {
            /*CUANDO SE PRESIONA MODIFICAR*/
            fabBuscar.setEnabled(false);
            fabGuardar.setEnabled(true);
            fabNuevo.setEnabled(false);
            fabModificar.setEnabled(false);
            fabSiguiente.setEnabled(false);
            fabAtras.setEnabled(false);
            fabDesactivar.setEnabled(false);
            fabSubirFoto.setEnabled(true);
            fabVerFoto.setEnabled(true);
            msFila.setEnabled(true);
            msUR.setEnabled(true);
            msRack.setEnabled(true);
            msTipoAlerta.setEnabled(true);
            msColorAlerta.setEnabled(true);
            msFila.setClickable(true);
            msUR.setClickable(true);
            msRack.setClickable(true);
            msTipoAlerta.setClickable(true);
            msColorAlerta.setClickable(true);
            msFila.setFocusableInTouchMode(true);
            msUR.setFocusableInTouchMode(true);
            Tdo_UR = Tdo_UR_Temporal;
            msUR.setText(Nombre_UR_Temporal);
            if(ListaEquipo.size()>1) {
                CARGAR_EQUIPO("0");
            }else{
                CARGAR_EQUIPO("1");
            }
            msRack.setFocusableInTouchMode(true);
            msTipoAlerta.setFocusableInTouchMode(true);
            msColorAlerta.setFocusableInTouchMode(true);
            etComentario.setEnabled(true);
            etTicket.setEnabled(true);
        }
    }

    public void OBTENER_DATOS() {
        Ticket = etTicket.getText().toString().toUpperCase();
        Comentario = etComentario.getText().toString().toUpperCase();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Drawable dr = ((ImageView) Image).getDrawable();
        Bitmap bitmap = ((BitmapDrawable) dr.getCurrent()).getBitmap();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            photo = baos.toByteArray();
        } else {
            photo = null;
        }

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

    public void CARGAR_EQUIPO(String opcion) {
        Crud_Equipo DATOS;
        DATOS = Crud_Equipo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            if(opcion.equals("1")) {
                T_Equipo = DATOS.CONSULTA_GENERAL_POR_CONDICION(Tdo_Fila, Tdo_Rack, Tdo_UR, 1);
            }else{
                T_Equipo = DATOS.CONSULTA_GENERAL_EQUIPO_POR_ID(Tdo_Equipo);
            }
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Equipo.moveToFirst() == false) {
            //el cursor está vacío
            ListaEquipo = new ArrayList<Equipo>();
            Tdo_Equipo = "";
            Nombre_Equipo = "";
            Marca = "";
            Modelo = "";
            Serie = "";
            return;
        }
        int P_TDO = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.ID);
        int P_Nombre_Equipo = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.NOMBRE_EQUIPO);
        int P_Marca = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.MARCA);
        int P_Modelo = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.MODELO);
        int P_Serie = T_Equipo.getColumnIndex(IT_Equipo.I_EQUIPO.SERIE);
        ListaEquipo = new ArrayList<Equipo>();
        for (T_Equipo.moveToFirst(); !T_Equipo.isAfterLast(); T_Equipo.moveToNext()) {
            Tdo_Equipo = T_Equipo.getString(P_TDO);
            Nombre_Equipo = T_Equipo.getString(P_Nombre_Equipo);
            Marca = T_Equipo.getString(P_Marca);
            Modelo = T_Equipo.getString(P_Modelo);
            Serie = T_Equipo.getString(P_Serie);
            Equipo Equipo = new Equipo(Tdo_Equipo, Nombre_Equipo, Marca, Modelo, Serie);
            ListaEquipo.add(Equipo);
        }
        if (ListaEquipo.size() > 1) {
            fabAtras1.setEnabled(true);
            fabSiguiente1.setEnabled(true);
        } else {
            fabAtras1.setEnabled(false);
            fabSiguiente1.setEnabled(false);
        }
        Tdo_Equipo = ListaEquipo.get(0).getID();
        etID_Equipo.setText(ListaEquipo.get(0).getNOMBRE_EQUIPO());
        etMarca.setText(ListaEquipo.get(0).getMARCA());
        etModelo.setText(ListaEquipo.get(0).getMODELO());
        etSerie.setText(ListaEquipo.get(0).getSERIE());
    }


    public void CARGAR_TIPO_ALERTAS() {
        Crud_Tipo_Alerta DATOS;
        DATOS = Crud_Tipo_Alerta.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Tipo_Alerta = DATOS.CONSULTA_GENERAL_TIPO_ALERTA();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Tipo_Alerta.moveToFirst() == false) {
            //el cursor está vacío
            return;
        }
        int P_TDO = T_Tipo_Alerta.getColumnIndex(IT_Tipo_Alerta.I_TIPO_ALERTA.ID);
        int P_ID_Tipo_Alerta = T_Tipo_Alerta.getColumnIndex(IT_Tipo_Alerta.I_TIPO_ALERTA.ID_TIPO_ALERTA);
        int P_Nombre_Tipo_Alerta = T_Tipo_Alerta.getColumnIndex(IT_Tipo_Alerta.I_TIPO_ALERTA.NOMBRE_TIPO_ALERTA);
        ListaTipoAlerta = new ArrayList<Tipo_Alerta>();
        for (T_Tipo_Alerta.moveToFirst(); !T_Tipo_Alerta.isAfterLast(); T_Tipo_Alerta.moveToNext()) {
            Tdo_Tipo_Alerta = T_Tipo_Alerta.getString(P_TDO);
            ID_Tipo_Alerta = T_Tipo_Alerta.getString(P_ID_Tipo_Alerta);
            Nombre_Tipo_Alerta = T_Tipo_Alerta.getString(P_Nombre_Tipo_Alerta);
            Tipo_Alerta Tipo_Alerta = new Tipo_Alerta(Tdo_Tipo_Alerta, ID_Tipo_Alerta, Nombre_Tipo_Alerta);
            ListaTipoAlerta.add(Tipo_Alerta);
        }
        ArrayAdapter<Tipo_Alerta> Adapter = new ArrayAdapter<Tipo_Alerta>(this, android.R.layout.simple_spinner_dropdown_item, ListaTipoAlerta);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msTipoAlerta.setAdapter(Adapter);
    }

    public void CARGAR_COLOR_ALERTAS() {
        Crud_Color_Alerta DATOS;
        DATOS = Crud_Color_Alerta.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Color_Alerta = DATOS.CONSULTA_GENERAL_COLOR_ALERTA();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Color_Alerta.moveToFirst() == false) {
            //el cursor está vacío
            return;
        }
        int P_TDO = T_Color_Alerta.getColumnIndex(IT_Color_Alerta.I_COLOR_ALERTA.ID);
        int P_ID_Color_Alerta = T_Color_Alerta.getColumnIndex(IT_Color_Alerta.I_COLOR_ALERTA.ID_COLOR_ALERTA);
        int P_Nombre_Color_Alerta = T_Color_Alerta.getColumnIndex(IT_Color_Alerta.I_COLOR_ALERTA.NOMBRE_COLOR_ALERTA);
        ListaColorAlerta = new ArrayList<Color_Alerta>();
        for (T_Color_Alerta.moveToFirst(); !T_Color_Alerta.isAfterLast(); T_Color_Alerta.moveToNext()) {
            Tdo_Color_Alerta = T_Color_Alerta.getString(P_TDO);
            ID_Color_Alerta = T_Color_Alerta.getString(P_ID_Color_Alerta);
            Nombre_Color_Alerta = T_Color_Alerta.getString(P_Nombre_Color_Alerta);
            Color_Alerta Color_Alerta = new Color_Alerta(Tdo_Color_Alerta, ID_Color_Alerta, Nombre_Color_Alerta);
            ListaColorAlerta.add(Color_Alerta);
        }
        ArrayAdapter<Color_Alerta> Adapter = new ArrayAdapter<Color_Alerta>(this, android.R.layout.simple_spinner_dropdown_item, ListaColorAlerta);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        msColorAlerta.setAdapter(Adapter);
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

    private String getTimeAlerta() throws ParseException {
        DateFormat Format = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm", Locale.getDefault());
        Date hora1 = Format.parse(tiempo_inicio);
        Date hora2 = Format.parse(getDateTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        long lantes = hora1.getTime();
        long lahora = hora2.getTime();
        long diferencia = (lahora - lantes);
        calendar.setTimeInMillis(diferencia);
        return dateFormat.format(calendar.getTime());
    }

    private String Nombre_Foto() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy-HH-mm", Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, 0);
        return dateFormat.format(calendar.getTime());
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth) {
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

}