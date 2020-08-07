package org.bp.teuno.checklist.Controlador;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.TooltipCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.bp.teuno.checklist.Modelo.Turno;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Operador;
import org.bp.teuno.checklist.SQLite.IT_Turno;
import org.bp.teuno.checklist.SQLite.IT_Version;
import org.bp.teuno.checklist.UI.Crud_Inicio_Sesion;
import org.bp.teuno.checklist.UI.Crud_Turno;
import org.bp.teuno.checklist.UI.Crud_Version;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class C_Inicio_Sesion extends AppCompatActivity {

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    Button btnIngresar;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabNuevo, fabSalir, fabAcerca;
    TextInputLayout tilId_Operador;
    EditText etId_Operador;
    String Tdo_Operador, Tdo_Turno, Id_Operador, Turno, Primer_Nombre, Segundo_Nombre, Primer_Apellido, Segundo_Apellido, Usuario_Ingresa, Tdo_Grupo;
    String Hora_Inicio, Hora_Fin;
    Crud_Inicio_Sesion DATOS;
    Cursor T_Operador, T_Turno;
    String Tdo_Version, Nombre_Version, Mensaje, Colaboradores;
    int bandera = 0;
    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;
    private int PERMISO_ESCRITURA = 1;
    private int PERMISO_CAMARA = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_inicio_sesion);

        mContext = getApplicationContext();
        mActivity = this;
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);


        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        btnIngresar = (Button) findViewById(R.id.btnIngreso);
        fabNuevo = (FloatingActionButton) findViewById(R.id.fabNuevo);
        fabSalir = (FloatingActionButton) findViewById(R.id.fabSalir);
        fabAcerca = (FloatingActionButton) findViewById(R.id.fabAcerca);

        tilId_Operador = (TextInputLayout) findViewById(R.id.tilId_Operador);
        etId_Operador = (EditText) findViewById(R.id.etId_Operador);

        TooltipCompat.setTooltipText(fabNuevo, "Mantenimiento de Operadoress");
        TooltipCompat.setTooltipText(fabSalir, "Salir");
        TooltipCompat.setTooltipText(fabAcerca, "Acerca de");

        etId_Operador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilId_Operador.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        verifyPermission();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PERMISO_ESCRITURA == 0) {
                    Toast.makeText(C_Inicio_Sesion.this, "Se deben conceder permisos de escritura a la aplicación", Toast.LENGTH_SHORT).show();
                    verifyPermission();
                    return;
                }
                if (PERMISO_CAMARA == 0) {
                    Toast.makeText(C_Inicio_Sesion.this, "Se deben conceder permisos para acceder a la cámara del dispositivo", Toast.LENGTH_SHORT).show();
                    verifyPermission();
                    return;
                }
                if (EXISTENTURNOSCREADOS() == false) {
                    ELIMINARTURNOS();
                    CREARTURNOS();
                }
                OBTENERDATOS();
                if (Id_Operador.equals("")) {
                    tilId_Operador.setError("");
                    etId_Operador.setError("Ingrese el ID del Operador");
                    etId_Operador.requestFocus();
                    return;
                }
                if (OBTENERTURNO() == false) {
                    Toast.makeText(getApplicationContext(), "No se encuentran registrados los turnos en el sistema, contactese con el administrador", Toast.LENGTH_LONG).show();
                    return;
                }
                if (OBTENERUSUARIO() == false) {
                    tilId_Operador.setError("");
                    etId_Operador.setError("El operador no se encuentra registrado en la aplicación");
                    etId_Operador.setText("");
                    etId_Operador.requestFocus();
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), "Bienvenid@: " + Primer_Nombre + " " + Primer_Apellido + " a la aplicación Checklist Automático", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(C_Inicio_Sesion.this, C_Menu_Principal.class);
                    intent.putExtra("Id_Operador", Id_Operador);
                    intent.putExtra("Nombre_Operador", Primer_Nombre + " " + Segundo_Nombre + " " + Primer_Apellido + " " + Segundo_Apellido);
                    intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                    intent.putExtra("Turno_Operador", Turno);
                    intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                    startActivity(intent);
                    finish();
                }
            }
        });

        fabNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PERMISO_ESCRITURA == 0) {
                    Toast.makeText(C_Inicio_Sesion.this, "Se deben conceder permisos de escritura a la aplicación", Toast.LENGTH_SHORT).show();
                    verifyPermission();
                    return;
                }
                if (PERMISO_CAMARA == 0) {
                    Toast.makeText(C_Inicio_Sesion.this, "Se deben conceder permisos para acceder a la cámara del dispositivo", Toast.LENGTH_SHORT).show();
                    verifyPermission();
                    return;
                }
                Intent intent = new Intent(C_Inicio_Sesion.this, C_Operador.class);
                startActivity(intent);
                finish();
            }
        });

        fabAcerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PERMISO_ESCRITURA == 0) {
                    Toast.makeText(C_Inicio_Sesion.this, "Se deben conceder permisos de escritura a la aplicación", Toast.LENGTH_SHORT).show();
                    verifyPermission();
                    return;
                }
                if (PERMISO_CAMARA == 0) {
                    Toast.makeText(C_Inicio_Sesion.this, "Se deben conceder permisos para acceder a la cámara del dispositivo", Toast.LENGTH_SHORT).show();
                    verifyPermission();
                    return;
                }
                OBTENERVERSION();
                bandera = bandera + 1;
                if (bandera == 1) {
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                    View customView = inflater.inflate(R.layout.l_acerca, null);

                    mPopupWindow = new PopupWindow(
                            customView,
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT
                    );

                    // Set an elevation value for popup window
                    // Call requires API level 21
                    if (Build.VERSION.SDK_INT >= 21) {
                        mPopupWindow.setElevation(5.0f);
                    }

                    // Get a reference for the custom view close button
                    ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
                    TextView txtMensaje = (TextView) customView.findViewById(R.id.txtMensaje);
                    TextView txtColaboradores = (TextView) customView.findViewById(R.id.txtColaboradores);
                    TextView txtVersion = (TextView) customView.findViewById(R.id.txtVersion);

                    txtVersion.setText("Versión: " + Nombre_Version);
                    txtMensaje.setText(Mensaje);

                    String[] textElements = Colaboradores.split(",");
                    String textColaboradores = "";
                    for (int i = 0; i < textElements.length; i++) {
                        textColaboradores = textColaboradores + textElements[i] + "\n";
                    }

                    txtColaboradores.setText(textColaboradores);

                    // Set a click listener for the popup window close button
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Dismiss the popup window
                            mPopupWindow.dismiss();
                            bandera = 0;
                        }
                    });

                    mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, 50);
                }
            }
        });

        fabSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void verifyPermission() {
        int permsRequestCode = 100;
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        int accessFinePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission == PackageManager.PERMISSION_GRANTED && accessFinePermission == PackageManager.PERMISSION_GRANTED) {
            //se realiza metodo si es necesario...
            PERMISO_ESCRITURA = 1;
            PERMISO_CAMARA = 1;
        } else {
            ActivityCompat.requestPermissions(this, perms, permsRequestCode);
            PERMISO_ESCRITURA = 0;
            PERMISO_CAMARA = 0;
            verifyPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    permissionGranted();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    permissionRejected();
                }
                return;
            }
            case CAMERA_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    permissionGranted();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    permissionRejected();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void permissionGranted() {
        Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
    }

    private void permissionRejected() {
        Toast.makeText(this, "Permiso revocado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder myBulid = new AlertDialog.Builder(this);
        myBulid.setTitle("Salir");
        myBulid.setIcon(R.drawable.salir);
        myBulid.setMessage("¿Desea salir de la aplicación?");
        myBulid.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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

    public void OBTENERDATOS() {
        Id_Operador = tilId_Operador.getEditText().getText().toString();
    }

    public boolean OBTENERUSUARIO() {
        DATOS = Crud_Inicio_Sesion.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Operador = DATOS.CONSULTA_GENERAL_OPERADOR_POR_ID(Id_Operador);
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Operador.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        }
        int P_Tdo_Operador = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.ID);
        int P_ID_OPERADOR = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.ID_OPERADOR);
        int P_Primer_Nombre = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.PRIMER_NOMBRE);
        int P_Segundo_Nombre = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.SEGUNDO_NOMBRE);
        int P_Primer_Apellido = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.PRIMER_APELLIDO);
        int P_Segundo_Apellido = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.SEGUNDO_APELLIDO);
        int P_Usuario_Ingresa = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.USUARIO_INGRESA);
        int P_Tdo_Grupo = T_Operador.getColumnIndex(IT_Operador.I_OPERADOR.ID_GRUPO);
        for (T_Operador.moveToFirst(); !T_Operador.isAfterLast(); T_Operador.moveToNext()) {
            Tdo_Operador = T_Operador.getString(P_Tdo_Operador);
            Id_Operador = T_Operador.getString(P_ID_OPERADOR);
            Primer_Nombre = T_Operador.getString(P_Primer_Nombre);
            Segundo_Nombre = T_Operador.getString(P_Segundo_Nombre);
            Primer_Apellido = T_Operador.getString(P_Primer_Apellido);
            Segundo_Apellido = T_Operador.getString(P_Segundo_Apellido);
            Usuario_Ingresa = T_Operador.getString(P_Usuario_Ingresa);
            Tdo_Grupo = T_Operador.getString(P_Tdo_Grupo);
        }
        if (!Tdo_Operador.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean OBTENERTURNO() {
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
            return false;
        }
        int P_Tdo_Turno = T_Turno.getColumnIndex(IT_Turno.I_TURNO.ID);
        int P_Turno = T_Turno.getColumnIndex(IT_Turno.I_TURNO.TURNO);
        int P_Hora_Inicio = T_Turno.getColumnIndex(IT_Turno.I_TURNO.HORA_INICIO);
        int P_Hora_Fin = T_Turno.getColumnIndex(IT_Turno.I_TURNO.HORA_FIN);
        for (T_Turno.moveToFirst(); !T_Turno.isAfterLast(); T_Turno.moveToNext()) {
            Tdo_Turno = T_Turno.getString(P_Tdo_Turno);
            Turno = T_Turno.getString(P_Turno);
            Hora_Inicio = T_Turno.getString(P_Hora_Inicio);
            Hora_Fin = T_Turno.getString(P_Hora_Fin);
        }
        if (!Tdo_Turno.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean EXISTENTURNOSCREADOS() {
        Crud_Inicio_Sesion Datos = Crud_Inicio_Sesion.OBTENER_INSTANCIA(getApplicationContext());
        Cursor T = null;
        String ID = "";
        try {
            Datos.GETBD().beginTransaction();
            T = Datos.CONSULTA_TURNOS();
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T.moveToFirst() == false) {
            //el cursor está vacío
            return false;
        }
        ArrayList<Turno> Lista = new ArrayList<Turno>();
        int P_ID = T.getColumnIndex(IT_Turno.I_TURNO.ID);
        for (T.moveToFirst(); !T.isAfterLast(); T.moveToNext()) {
            ID = T.getString(P_ID);
            Lista.add(new Turno(ID));
        }
        if (Lista.size() == 3) {
            return true;
        } else {
            return false;
        }
    }

    public void ELIMINARTURNOS() {
        Crud_Turno Datos = Crud_Turno.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            long deletedResult = Datos.ELIMINAR_TURNO();
            if (deletedResult == -1) {
                throw new SQLException(String.format("Se generó un error al eliminar el registro en la base de datos"));
            }
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
    }

    public void CREARTURNOS() {
        Crud_Turno Datos = Crud_Turno.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            long insertedResult = 0;
            insertedResult = Datos.INSERTAR_TURNO(new Turno(null, "T1", "00:00", "07:59", "A", "1620", "", getDateTime(), ""));
            if (insertedResult == -1) {
                throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
            }
            insertedResult = Datos.INSERTAR_TURNO(new Turno(null, "T2", "08:00", "15:59", "A", "1620", "", getDateTime(), ""));
            if (insertedResult == -1) {
                throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
            }
            insertedResult = Datos.INSERTAR_TURNO(new Turno(null, "T3", "16:00", "23:59", "A", "1620", "", getDateTime(), ""));
            if (insertedResult == -1) {
                throw new SQLException(String.format("Se generó un error al guardar el registro en la base de datos"));
            }
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
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

    public void OBTENERVERSION() {
        Cursor T_Version;
        Crud_Version DATOS = Crud_Version.OBTENER_INSTANCIA(getApplicationContext());
        try {
            DATOS.GETBD().beginTransaction();
            T_Version = DATOS.CONSULTA_GENERAL_VERSION();
            DATOS.GETBD().setTransactionSuccessful();
        } finally {
            DATOS.GETBD().endTransaction();
        }
        if (T_Version.moveToFirst() == false) {
            //el cursor está vacío
            return;
        }
        int P_Tdo_Version = T_Version.getColumnIndex(IT_Version.I_VERSION.ID);
        int P_Version = T_Version.getColumnIndex(IT_Version.I_VERSION.NOMBRE_VERSION);
        int P_Mensaje = T_Version.getColumnIndex(IT_Version.I_VERSION.MENSAJE);
        int P_Colaboradores = T_Version.getColumnIndex(IT_Version.I_VERSION.COLABORADORES);
        for (T_Version.moveToFirst(); !T_Version.isAfterLast(); T_Version.moveToNext()) {
            Tdo_Version = T_Version.getString(P_Tdo_Version);
            Nombre_Version = T_Version.getString(P_Version);
            Mensaje = T_Version.getString(P_Mensaje);
            Colaboradores = T_Version.getString(P_Colaboradores);
        }
    }


}
