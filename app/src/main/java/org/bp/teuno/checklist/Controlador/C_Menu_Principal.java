package org.bp.teuno.checklist.Controlador;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.bp.teuno.checklist.Lista.CustomExpandableListAdapter;
import org.bp.teuno.checklist.Lista.ExpandableListDataPump;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Grupo;
import org.bp.teuno.checklist.SQLite.IT_Turno;
import org.bp.teuno.checklist.UI.Crud_Grupo;
import org.bp.teuno.checklist.UI.Crud_Inicio_Sesion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class C_Menu_Principal extends AppCompatActivity {

    private final long interval = 1 * 1000;
    ExpandableListView elvLista;
    ImageView imageOperador;
    TextView txtNombre_Operador, txtTurno;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Tdo_Turno_1, Turno_Operador, isMenuA56;
    String Tdo_Grupo, Id_Grupo, Nombre_Grupo, Id_Grupo_Temporal = "", Pin_Seguridad = "";
    String ischeckFilas, ischeckClientes, ischeckAlertas, ischeckTipoAlertas, ischeckColorAlertas, ischeckCantidadUR, ischeckPasillos, ischeckChillers, ischeckAreas, ischeckGrupos, ischeckRacks, ischeckEquipos, ischeckPinSeguridad, ischeckVersion, ischeckParametros;
    Cursor T_Grupo;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    CountDownTimer countDownTimer;
    int bandera = 0;
    private LinearLayout mLinearLayout;
    private Context mContext;
    private Activity mActivity;
    private PopupWindow mPopupWindow;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME
    Crud_Inicio_Sesion DATOS;
    Cursor T_Turno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_menu_principal);

        mContext = getApplicationContext();
        mActivity = this;
        mLinearLayout = (LinearLayout) findViewById(R.id.rl);

        //countDownTimer = new MyCountDownTimer(startTime, interval);
        //onUserInteraction();

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();

        elvLista = (ExpandableListView) findViewById(R.id.elvLista);
        imageOperador = (ImageView) findViewById(R.id.imageOperador);
        txtNombre_Operador = (TextView) findViewById(R.id.txtNombre_Operador);
        txtTurno = (TextView) findViewById(R.id.txtTurno);

        txtNombre_Operador.setText(Nombre_Operador);
        txtTurno.setText("Turno: " + Turno_Operador);

        if (!Nombre_Operador.equals("JOSE GOMEZ")) {
            //imageOperador.setImageResource(R.drawable.ic_person_white_18dp);
        }

        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        elvLista.setAdapter(expandableListAdapter);

        OBTENER_PERMISOS();

        elvLista.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("1.- Mantenimiento de Filas") && ischeckFilas.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Fila.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Filas", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("1.- Mantenimiento de Filas") && ischeckFilas.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("2.- Mantenimiento de Cantidad de UR") && ischeckCantidadUR.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Cantidad_Ur.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de UR", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("2.- Mantenimiento de Cantidad de UR") && ischeckCantidadUR.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("3-  Mantenimiento de Clientes") && ischeckClientes.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Cliente.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Clientes", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("3-  Mantenimiento de Clientes") && ischeckClientes.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("4.- Mantenimiento de Tipo de Alerta") && ischeckTipoAlertas.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Tipo_Alerta.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Tipo de Alertas", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("4.- Mantenimiento de Tipo de Alerta") && ischeckTipoAlertas.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("5.- Mantenimiento de Color de Alerta") && ischeckColorAlertas.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Color_Alerta.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Color de Alertas", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("5.- Mantenimiento de Color de Alerta") && ischeckColorAlertas.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("6.- Mantenimiento de Rack") && ischeckRacks.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        bandera = bandera + 1;
                        final Intent intent = new Intent(C_Menu_Principal.this, C_Rack.class);
                        if (bandera == 1) {
                            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.l_toast, null);

                            mPopupWindow = new PopupWindow(
                                    customView,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );

                            if (Build.VERSION.SDK_INT >= 21) {
                                mPopupWindow.setElevation(5.0f);
                            }

                            Button btnBP = (Button) customView.findViewById(R.id.btnBP);
                            Button btnDN = (Button) customView.findViewById(R.id.btnDN);
                            TextView txtMensaje = (TextView) customView.findViewById(R.id.txtMensaje);
                            ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                            txtMensaje.setText("Mantenimiento de Rack");

                            btnBP.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    isMenuA56 = "1";
                                    intent.putExtra("Id_Operador", Id_Operador);
                                    intent.putExtra("Nombre_Operador", Nombre_Operador);
                                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                    intent.putExtra("Turno_Operador", Turno_Operador);
                                    intent.putExtra("IsMenuA56", isMenuA56);
                                    intent.putExtra("Access", "BP");
                                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(
                                            getApplicationContext(), "Ingreso correcto al mantenimiento de Alertas para Banco del Pichincha", Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });

                            btnDN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    isMenuA56 = "1";
                                    intent.putExtra("Id_Operador", Id_Operador);
                                    intent.putExtra("Nombre_Operador", Nombre_Operador);
                                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                    intent.putExtra("Turno_Operador", Turno_Operador);
                                    intent.putExtra("IsMenuA56", isMenuA56);
                                    intent.putExtra("Access", "DN");
                                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(
                                            getApplicationContext(), "Ingreso correcto al mantenimiento de Alertas para Diners Club", Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });

                            closeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Dismiss the popup window
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                }
                            });

                            mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);

                        }
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("6.- Mantenimiento de Rack") && ischeckRacks.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("7.- Mantenimiento de Equipos") && ischeckEquipos.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        bandera = bandera + 1;
                        final Intent intent = new Intent(C_Menu_Principal.this, C_Equipo.class);
                        if (bandera == 1) {
                            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.l_toast, null);

                            mPopupWindow = new PopupWindow(
                                    customView,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );

                            if (Build.VERSION.SDK_INT >= 21) {
                                mPopupWindow.setElevation(5.0f);
                            }

                            Button btnBP = (Button) customView.findViewById(R.id.btnBP);
                            Button btnDN = (Button) customView.findViewById(R.id.btnDN);
                            TextView txtMensaje = (TextView) customView.findViewById(R.id.txtMensaje);
                            ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                            txtMensaje.setText("Mantenimiento de Equipos");

                            btnBP.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    isMenuA56 = "1";
                                    intent.putExtra("Id_Operador", Id_Operador);
                                    intent.putExtra("Nombre_Operador", Nombre_Operador);
                                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                    intent.putExtra("Turno_Operador", Turno_Operador);
                                    intent.putExtra("IsMenuA56", isMenuA56);
                                    intent.putExtra("Access", "BP");
                                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(
                                            getApplicationContext(), "Ingreso correcto al mantenimiento de Alertas para Banco del Pichincha", Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });

                            btnDN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    isMenuA56 = "1";
                                    intent.putExtra("Id_Operador", Id_Operador);
                                    intent.putExtra("Nombre_Operador", Nombre_Operador);
                                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                    intent.putExtra("Turno_Operador", Turno_Operador);
                                    intent.putExtra("IsMenuA56", isMenuA56);
                                    intent.putExtra("Access", "DN");
                                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(
                                            getApplicationContext(), "Ingreso correcto al mantenimiento de Alertas para Diners Club", Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });

                            closeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Dismiss the popup window
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                }
                            });

                            mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);

                        }
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("7.- Mantenimiento de Equipos") && ischeckEquipos.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("8.- Mantenimiento de Alertas") && ischeckAlertas.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        bandera = bandera + 1;
                        final Intent intent = new Intent(C_Menu_Principal.this, C_Alerta.class);
                        if (bandera == 1) {
                            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.l_toast, null);

                            mPopupWindow = new PopupWindow(
                                    customView,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );

                            if (Build.VERSION.SDK_INT >= 21) {
                                mPopupWindow.setElevation(5.0f);
                            }

                            Button btnBP = (Button) customView.findViewById(R.id.btnBP);
                            Button btnDN = (Button) customView.findViewById(R.id.btnDN);
                            TextView txtMensaje = (TextView) customView.findViewById(R.id.txtMensaje);
                            ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                            txtMensaje.setText("Mantenimiento de Alertas");

                            btnBP.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    isMenuA56 = "1";
                                    intent.putExtra("Id_Operador", Id_Operador);
                                    intent.putExtra("Nombre_Operador", Nombre_Operador);
                                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                    intent.putExtra("Turno_Operador", Turno_Operador);
                                    intent.putExtra("IsMenuA56", isMenuA56);
                                    intent.putExtra("Access", "BP");
                                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                                    intent.putExtra("Tiempo_A56", "0");
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(
                                            getApplicationContext(), "Ingreso correcto al mantenimiento de Alertas para Banco del Pichincha", Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });

                            btnDN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    isMenuA56 = "1";
                                    intent.putExtra("Id_Operador", Id_Operador);
                                    intent.putExtra("Nombre_Operador", Nombre_Operador);
                                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                    intent.putExtra("Turno_Operador", Turno_Operador);
                                    intent.putExtra("IsMenuA56", isMenuA56);
                                    intent.putExtra("Access", "DN");
                                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                                    intent.putExtra("Tiempo_A56", "0");
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(
                                            getApplicationContext(), "Ingreso correcto al mantenimiento de Alertas para Diners Club", Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });

                            closeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Dismiss the popup window
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                }
                            });

                            mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);

                        }
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("8.- Mantenimiento de Alertas") && ischeckAlertas.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("9.- Mantenimiento de Grupos y Permisos") && ischeckGrupos.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Grupo.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Grupos", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("9.- Mantenimiento de Grupos y Permisos") && ischeckGrupos.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("10.- Mantenimiento de Pasillos") && ischeckPasillos.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Pasillo.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Pasillos", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("10.- Mantenimiento de Pasillos") && ischeckPasillos.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("11.- Mantenimiento de Areas") && ischeckAreas.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Area.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Áreas", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("11.- Mantenimiento de Areas") && ischeckAreas.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("12.- Mantenimiento de Chillers") && ischeckChillers.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Chiller.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Chillers", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("12.- Mantenimiento de Chillers") && ischeckChillers.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("13.- Mantenimiento de Versiones") && ischeckVersion.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Version.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Versiones", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("13.- Mantenimiento de Versiones") && ischeckVersion.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("14.- Mantenimiento de Parámetros") && ischeckParametros.equals("S")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al mantenimiento", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_Parametros.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al mantenimiento de Parámetros", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("14.- Mantenimiento de Parámetros") && ischeckParametros.equals("N")) {
                    Toast.makeText(
                            getApplicationContext(), "No tiene permisos para acceder al mantenimiento", Toast.LENGTH_SHORT
                    ).show();
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("1.- Formulario A.34 - Checklist de sala de máquinas")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al formulario", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        isMenuA56 = "1";
                        Intent intent = new Intent(C_Menu_Principal.this, C_A34.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al formulario A.34", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("2.- Formulario A.56/FD.56 - Checklist monitoreo de equipos de sala útil")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al formulario", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        bandera = bandera + 1;
                        final Intent intent = new Intent(C_Menu_Principal.this, C_A56.class);
                        if (bandera == 1) {
                            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                            View customView = inflater.inflate(R.layout.l_toast, null);

                            mPopupWindow = new PopupWindow(
                                    customView,
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );

                            if (Build.VERSION.SDK_INT >= 21) {
                                mPopupWindow.setElevation(5.0f);
                            }

                            Button btnBP = (Button) customView.findViewById(R.id.btnBP);
                            Button btnDN = (Button) customView.findViewById(R.id.btnDN);
                            TextView txtMensaje = (TextView) customView.findViewById(R.id.txtMensaje);
                            ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                            txtMensaje.setText("Formulario A.56/FD.56 - Checklist monitoreo de equipos de sala útil");

                            btnBP.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    isMenuA56 = "1";
                                    intent.putExtra("Id_Operador", Id_Operador);
                                    intent.putExtra("Nombre_Operador", Nombre_Operador);
                                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                    intent.putExtra("Turno_Operador", Turno_Operador);
                                    intent.putExtra("IsMenuA56", isMenuA56);
                                    intent.putExtra("Access", "BP");
                                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                                    intent.putExtra("Tiempo_A56", "0");
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(
                                            getApplicationContext(), "Ingreso correcto al formulario A.56", Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });

                            btnDN.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    isMenuA56 = "1";
                                    intent.putExtra("Id_Operador", Id_Operador);
                                    intent.putExtra("Nombre_Operador", Nombre_Operador);
                                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                    intent.putExtra("Turno_Operador", Turno_Operador);
                                    intent.putExtra("IsMenuA56", isMenuA56);
                                    intent.putExtra("Access", "DN");
                                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                                    intent.putExtra("Tiempo_A56", "0");
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(
                                            getApplicationContext(), "Ingreso correcto al formulario FD.56", Toast.LENGTH_SHORT
                                    ).show();
                                }
                            });

                            closeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Dismiss the popup window
                                    mPopupWindow.dismiss();
                                    bandera = 0;
                                }
                            });

                            mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);

                        }
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("3.- Formulario A.58 - Humedad pasillos fríos y chillers en sala útil")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al formulario", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_A58.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al formulario A.58", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("4.- Formulario A.71 - Checklist estado infraestructura")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al formulario", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_A71.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al formulario A.71", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("5.- Formulario A.76 - Chequeo capacidad racks")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al formulario", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        final Intent intent = new Intent(C_Menu_Principal.this, C_A76.class);
                        AlertDialog.Builder myBulid = new AlertDialog.Builder(C_Menu_Principal.this);
                        myBulid.setTitle("Formulario A.76");
                        myBulid.setIcon(R.drawable.rack);
                        myBulid.setMessage("¿Desea ingresar al formulario A.76?");
                        myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent.putExtra("Id_Operador", Id_Operador);
                                intent.putExtra("Nombre_Operador", Nombre_Operador);
                                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                                intent.putExtra("Turno_Operador", Turno_Operador);
                                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                                startActivity(intent);
                                finish();
                                Toast.makeText(
                                        getApplicationContext(), "Ingreso correcto al formulario A.76", Toast.LENGTH_SHORT
                                ).show();
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
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("6.- Formulario A.77 - Chequeo luminosidad y ruido")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al formulario", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_A77.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al formulario A.77", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                if (expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).equals("7.- Formulario A.98 - Checklist validación de estado de racks")) {
                    OBTENERTURNO();
                    if(!Tdo_Turno.equals(Tdo_Turno_1)){
                        Toast.makeText(
                                getApplicationContext(), "Se encuentra iniciada la sesión del turno anterior, inicie una nueva sesión antes de ingresar al formulario", Toast.LENGTH_SHORT
                        ).show();
                    }else {
                        Intent intent = new Intent(C_Menu_Principal.this, C_A98.class);
                        intent.putExtra("Id_Operador", Id_Operador);
                        intent.putExtra("Nombre_Operador", Nombre_Operador);
                        intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                        intent.putExtra("Turno_Operador", Turno_Operador);
                        intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                        startActivity(intent);
                        finish();
                        Toast.makeText(
                                getApplicationContext(), "Ingreso correcto al formulario A.98", Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                return false;
            }
        });

    }

    @Override
    public void onUserInteraction() {

        super.onUserInteraction();

        //Reset the timer on user interaction...
        //countDownTimer.cancel();
        //countDownTimer.start();

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder myBulid = new AlertDialog.Builder(this);
        myBulid.setTitle("Regresar");
        myBulid.setIcon(R.drawable.salir);
        myBulid.setMessage("¿Desea regresar a la pantalla de inicio de sesión?");
        myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(C_Menu_Principal.this, C_Inicio_Sesion.class);
                startActivity(intent);
                finish();
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

    public void OBTENER_PERMISOS() {
        Crud_Grupo DATOS;
        DATOS = Crud_Grupo.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Grupo = DATOS.CONSULTA_GENERAL_GRUPO_ID(Tdo_Grupo);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Grupo.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Grupo = "";
            Id_Grupo = "";
            Id_Grupo_Temporal = "";
            ischeckFilas = "";
            ischeckClientes = "";
            ischeckCantidadUR = "";
            ischeckAlertas = "";
            ischeckTipoAlertas = "";
            ischeckColorAlertas = "";
            ischeckRacks = "";
            ischeckEquipos = "";
            ischeckChillers = "";
            ischeckPasillos = "";
            ischeckAreas = "";
            ischeckGrupos = "";
            ischeckVersion = "";
            ischeckParametros = "";
            return;
        }
        int P_Tdo_Grupo = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.ID);
        int P_ID_GRUPO = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.ID_GRUPO);
        int P_NOMBRE_GRUPO = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.NOMBRE_GRUPO);
        int P_Pin_Seguridad = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PIN_SEGURIDAD);
        int P_Filas = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_FILA);
        int P_Clientes = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_CLIENTE);
        int P_Cantidad_UR = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_CANTIDAD_UR);
        int P_Rack = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_RACK);
        int P_Equipo = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_EQUIPO);
        int P_Alerta = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_ALERTA);
        int P_Tipo_Alerta = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_TIPO_ALERTA);
        int P_Color_Alerta = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_COLOR_ALERTA);
        int P_Pasillo = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_PASILLO);
        int P_Chiller = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_CHILLER);
        int P_Area = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_AREA);
        int P_Grupo = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_GRUPO);
        int P_Version = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_VERSION);
        int P_Parametros = T_Grupo.getColumnIndex(IT_Grupo.I_GRUPO.PERMISO_PARAMETROS);
        for (T_Grupo.moveToFirst(); !T_Grupo.isAfterLast(); T_Grupo.moveToNext()) {
            Tdo_Grupo = T_Grupo.getString(P_Tdo_Grupo);
            Id_Grupo = T_Grupo.getString(P_ID_GRUPO);
            Nombre_Grupo = T_Grupo.getString(P_NOMBRE_GRUPO);
            Pin_Seguridad = T_Grupo.getString(P_Pin_Seguridad);
            ischeckFilas = T_Grupo.getString(P_Filas);
            ischeckClientes = T_Grupo.getString(P_Clientes);
            ischeckCantidadUR = T_Grupo.getString(P_Cantidad_UR);
            ischeckRacks = T_Grupo.getString(P_Rack);
            ischeckEquipos = T_Grupo.getString(P_Equipo);
            ischeckAlertas = T_Grupo.getString(P_Alerta);
            ischeckTipoAlertas = T_Grupo.getString(P_Tipo_Alerta);
            ischeckColorAlertas = T_Grupo.getString(P_Color_Alerta);
            ischeckPasillos = T_Grupo.getString(P_Pasillo);
            ischeckChillers = T_Grupo.getString(P_Chiller);
            ischeckAreas = T_Grupo.getString(P_Area);
            ischeckGrupos = T_Grupo.getString(P_Grupo);
            ischeckVersion = T_Grupo.getString(P_Version);
            ischeckParametros = T_Grupo.getString(P_Parametros);
        }
    }

    public void OBTENERTURNO() {
        DATOS = Crud_Inicio_Sesion.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Turno = DATOS.CONSULTA_GENERAL_TURNO_POR_HORA();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Turno.moveToFirst() == false) {
            //el cursor está vacío
            Tdo_Turno_1 = "";
        }
        int P_Tdo_Turno = T_Turno.getColumnIndex(IT_Turno.I_TURNO.ID);
        for (T_Turno.moveToFirst(); !T_Turno.isAfterLast(); T_Turno.moveToNext()) {
            Tdo_Turno_1 = T_Turno.getString(P_Tdo_Turno);
        }
    }

}
