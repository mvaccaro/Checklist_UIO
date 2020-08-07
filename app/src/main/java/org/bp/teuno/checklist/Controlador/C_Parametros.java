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

import org.bp.teuno.checklist.Modelo.Parametros;
import org.bp.teuno.checklist.R;
import org.bp.teuno.checklist.SQLite.IT_Parametros;
import org.bp.teuno.checklist.UI.Crud_Parametros;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class C_Parametros extends AppCompatActivity {

    private final long interval = 1 * 1000;
    EditText etTemperaturaPasilloMinima, etTemperaturaPasilloMaxima, etHumedadPasilloMinima, etHumedadPasilloMaxima, etTemperaturaSuministroMinima, etTemperaturaSuministroMaxima, etTemperaturaRetornoMinima, etTemperaturaRetornoMaxima;
    TextInputLayout tilTemperaturaPasilloMinima, tilTemperaturaPasilloMaxima, tilHumedadPasilloMinima, tilHumedadPasilloMaxima, tilTemperaturaSuministroMinima, tilTemperaturaSuministroMaxima, tilTemperaturaRetornoMinima, tilTemperaturaRetornoMaxima;
    FloatingActionsMenu fabMenu;
    FloatingActionButton fabGuardar, fabModificar;
    Crud_Parametros Datos;
    String Tdo_Parametros, TemperaturaPasilloMinima, TemperaturaPasilloMaxima, HumedadPasilloMinima, HumedadPasilloMaxima, Usuario_Ingresa = "", Fecha_Ingreso = "", TemperaturaSuministroMinima, TemperaturaSuministroMaxima, TemperaturaRetornoMinima, TemperaturaRetornoMaxima;
    int Bandera = 0;
    Cursor T_Parametros;
    String Id_Operador, Nombre_Operador, Tdo_Turno, Turno_Operador;
    CountDownTimer countDownTimer;
    private String Tdo_Grupo;
    private long startTime = 10 * 60 * 1000; // 10 MINS IDLE TIME

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_parametros);

        //countDownTimer = new MyCountDownTimer(startTime, interval);

        fabMenu = (FloatingActionsMenu) findViewById(R.id.fabMenu);
        fabGuardar = (FloatingActionButton) findViewById(R.id.fabGuardar);
        fabGuardar.setEnabled(false);
        fabModificar = (FloatingActionButton) findViewById(R.id.fabModificar);
        fabModificar.setEnabled(true);

        Bundle bundle = getIntent().getExtras();
        Id_Operador = bundle.getString("Id_Operador").toString();
        Nombre_Operador = bundle.getString("Nombre_Operador").toString();
        Tdo_Turno = bundle.getString("Id_Turno_Operador").toString();
        Turno_Operador = bundle.getString("Turno_Operador").toString();
        Tdo_Grupo = bundle.getString("Id_Grupo_Operador").toString();


        etTemperaturaPasilloMinima = (EditText) findViewById(R.id.etTemperaturaPasilloMinima);
        etTemperaturaPasilloMaxima = (EditText) findViewById(R.id.etTemperaturaPasilloMaxima);
        tilTemperaturaPasilloMinima = (TextInputLayout) findViewById(R.id.tilTemperaturaPasilloMinima);
        tilTemperaturaPasilloMaxima = (TextInputLayout) findViewById(R.id.tilTemperaturaPasilloMaxima);

        etHumedadPasilloMinima = (EditText) findViewById(R.id.etHumedadPasilloMinima);
        etHumedadPasilloMaxima = (EditText) findViewById(R.id.etHumedadPasilloMaxima);
        tilHumedadPasilloMinima = (TextInputLayout) findViewById(R.id.tilHumedadPasilloMinima);
        tilHumedadPasilloMaxima = (TextInputLayout) findViewById(R.id.tilHumedadPasilloMaxima);

        etTemperaturaSuministroMinima = (EditText) findViewById(R.id.etTemperaturaSuninistroMinima);
        etTemperaturaSuministroMaxima = (EditText) findViewById(R.id.etTemperaturaSuministroMaxima);
        tilTemperaturaSuministroMinima = (TextInputLayout) findViewById(R.id.tilTemperaturaSuministroMinima);
        tilTemperaturaSuministroMaxima = (TextInputLayout) findViewById(R.id.tilTemperaturaSuministroMaxima);

        etTemperaturaRetornoMinima = (EditText) findViewById(R.id.etTemperaturaRetornoMinima);
        etTemperaturaRetornoMaxima = (EditText) findViewById(R.id.etTemperaturaRetornoMaxima);
        tilTemperaturaRetornoMinima = (TextInputLayout) findViewById(R.id.tilTemperaturaRetornoMinima);
        tilTemperaturaRetornoMaxima = (TextInputLayout) findViewById(R.id.tilTemperaturaRetornoMaxima);

        TooltipCompat.setTooltipText(fabGuardar, "Guardar");
        TooltipCompat.setTooltipText(fabModificar, "Modificar");

        etTemperaturaPasilloMinima.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilTemperaturaPasilloMinima.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etTemperaturaPasilloMaxima.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilTemperaturaPasilloMaxima.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CARGAR_DATOS();

        fabModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bandera = 1;
                ACTIVARDATOS(3);
            }
        });


        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Obtener_Datos();
                if (TemperaturaPasilloMinima.matches("")) {
                    etTemperaturaPasilloMinima.setError("Ingrese la temperatura mínima de los pasillos fríos");
                    etTemperaturaPasilloMinima.requestFocus();
                    return;
                }
                if (TemperaturaPasilloMaxima.matches("")) {
                    etTemperaturaPasilloMaxima.setError("Ingrese la temperatura máxima de los pasillos fríos");
                    etTemperaturaPasilloMaxima.requestFocus();
                    return;
                }
                if (HumedadPasilloMinima.matches("")) {
                    etHumedadPasilloMinima.setError("Ingrese la humedad mínima de los pasillos fríos");
                    etHumedadPasilloMinima.requestFocus();
                    return;
                }
                if (HumedadPasilloMaxima.matches("")) {
                    etHumedadPasilloMaxima.setError("Ingrese la humedad máxima de los pasillos fríos");
                    etHumedadPasilloMaxima.requestFocus();
                    return;
                }
                if (TemperaturaSuministroMinima.matches("")) {
                    etTemperaturaSuministroMinima.setError("Ingrese la temperatura mínima de suministro");
                    etTemperaturaSuministroMinima.requestFocus();
                    return;
                }
                if (TemperaturaSuministroMaxima.matches("")) {
                    etTemperaturaSuministroMaxima.setError("Ingrese la temperatura máxima de suministro");
                    etTemperaturaSuministroMaxima.requestFocus();
                    return;
                }
                if (TemperaturaRetornoMinima.matches("")) {
                    etTemperaturaRetornoMinima.setError("Ingrese la temperatura mínima de retorno");
                    etTemperaturaRetornoMinima.requestFocus();
                    return;
                }
                if (TemperaturaRetornoMaxima.matches("")) {
                    etTemperaturaRetornoMaxima.setError("Ingrese la temperatura máxima de retorno");
                    etTemperaturaRetornoMaxima.requestFocus();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(C_Parametros.this);
                    builder.setTitle("Guardar Registro")
                            .setMessage("¿Desea guardar el registro?")
                            .setIcon(R.drawable.alerta1)
                            .setPositiveButton("Aceptar",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Bandera == 0) {
                                                Datos = Crud_Parametros.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.INSERTAR_PARAMETROS(new Parametros(null, TemperaturaPasilloMinima, TemperaturaPasilloMaxima, HumedadPasilloMinima, HumedadPasilloMaxima, TemperaturaSuministroMinima, TemperaturaSuministroMaxima, TemperaturaRetornoMinima, TemperaturaRetornoMaxima, "A", Id_Operador, "", getDateTime(), ""));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            if (Bandera == 1) {
                                                Datos = Crud_Parametros.OBTENER_INSTANCIA(getApplicationContext());
                                                try {
                                                    Datos.GETBD().beginTransaction();
                                                    Datos.MODIFICAR_PARAMETROS(new Parametros(Tdo_Parametros, TemperaturaPasilloMinima, TemperaturaPasilloMaxima, HumedadPasilloMinima, HumedadPasilloMaxima, TemperaturaSuministroMinima, TemperaturaSuministroMaxima, TemperaturaRetornoMinima, TemperaturaRetornoMaxima, "A", Usuario_Ingresa, Id_Operador, Fecha_Ingreso, getDateTime()));
                                                    Datos.GETBD().setTransactionSuccessful();
                                                } finally {
                                                    Datos.GETBD().endTransaction();
                                                }
                                            }
                                            Toast.makeText(getApplicationContext(), "El registro se guardó correctamente", Toast.LENGTH_LONG).show();
                                            ACTIVARDATOS(1);
                                            CARGAR_DATOS();
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
                Intent intent = new Intent(C_Parametros.this, C_Menu_Principal.class);
                intent.putExtra("Id_Operador", Id_Operador);
                intent.putExtra("Nombre_Operador", Nombre_Operador);
                intent.putExtra("Id_Turno_Operador", Tdo_Turno);
                intent.putExtra("Turno_Operador", Turno_Operador);
                intent.putExtra("Id_Grupo_Operador", Tdo_Grupo);
                startActivity(intent);
                finish();
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

    public void CARGAR_DATOS() {
        Datos = Crud_Parametros.OBTENER_INSTANCIA(getApplicationContext());
        try {
            Datos.GETBD().beginTransaction();
            T_Parametros = Datos.CONSULTA_GENERAL_PARAMETROS();
            Datos.GETBD().setTransactionSuccessful();
        } finally {
            Datos.GETBD().endTransaction();
        }
        if (T_Parametros.moveToFirst() == false) {
            //el cursor está vacío
            return;
        }
        int P_Tdo_Parametros = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.ID);
        int P_TEMP_PAS_MINIMA = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_PAS_MINIMA);
        int P_TEMP_PAS_MAXIMA = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_PAS_MAXIMA);
        int P_HUME_PAS_MINIMA = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.HUME_PAS_MINIMA);
        int P_HUME_PAS_MAXIMA = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.HUME_PAS_MAXIMA);
        int P_TEMP_SUM_MINIMA = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_SUM_MINIMA);
        int P_TEMP_SUM_MAXIMA = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_SUM_MAXIMA);
        int P_TEMP_RET_MINIMA = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_RET_MINIMA);
        int P_TEMP_RET_MAXIMA = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.TEMP_RET_MAXIMA);
        int P_USUARIO_INGRESA = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.USUARIO_INGRESA);
        int P_FECHA_INGRESO = T_Parametros.getColumnIndex(IT_Parametros.I_PARAMETROS.FECHA_INGRESO);
        for (T_Parametros.moveToFirst(); !T_Parametros.isAfterLast(); T_Parametros.moveToNext()) {
            Tdo_Parametros = T_Parametros.getString(P_Tdo_Parametros);
            TemperaturaPasilloMinima = T_Parametros.getString(P_TEMP_PAS_MINIMA);
            TemperaturaPasilloMaxima = T_Parametros.getString(P_TEMP_PAS_MAXIMA);
            HumedadPasilloMinima = T_Parametros.getString(P_HUME_PAS_MINIMA);
            HumedadPasilloMaxima = T_Parametros.getString(P_HUME_PAS_MAXIMA);
            TemperaturaSuministroMinima = T_Parametros.getString(P_TEMP_SUM_MINIMA);
            TemperaturaSuministroMaxima = T_Parametros.getString(P_TEMP_SUM_MAXIMA);
            TemperaturaRetornoMinima = T_Parametros.getString(P_TEMP_RET_MINIMA);
            TemperaturaRetornoMaxima = T_Parametros.getString(P_TEMP_RET_MAXIMA);
            Usuario_Ingresa = T_Parametros.getString(P_USUARIO_INGRESA);
            Fecha_Ingreso = T_Parametros.getString(P_FECHA_INGRESO);
            Parametros parametros = new Parametros(Tdo_Parametros, TemperaturaPasilloMinima, TemperaturaPasilloMaxima, HumedadPasilloMinima, HumedadPasilloMaxima, TemperaturaSuministroMinima, TemperaturaSuministroMaxima, TemperaturaRetornoMinima, TemperaturaRetornoMaxima, Usuario_Ingresa, Fecha_Ingreso);
        }
        etTemperaturaPasilloMinima.setText(TemperaturaPasilloMinima);
        etTemperaturaPasilloMaxima.setText(TemperaturaPasilloMaxima);
        etHumedadPasilloMinima.setText(HumedadPasilloMinima);
        etHumedadPasilloMaxima.setText(HumedadPasilloMaxima);
        etTemperaturaSuministroMinima.setText(TemperaturaSuministroMinima);
        etTemperaturaSuministroMaxima.setText(TemperaturaSuministroMaxima);
        etTemperaturaRetornoMinima.setText(TemperaturaRetornoMinima);
        etTemperaturaRetornoMaxima.setText(TemperaturaRetornoMaxima);
    }

    public void ACTIVARDATOS(int OPCION) {
        if (OPCION == 0) {
            fabGuardar.setEnabled(true);
            fabModificar.setEnabled(false);
        }
        if (OPCION == 1) {
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
            etTemperaturaPasilloMinima.setEnabled(false);
            etTemperaturaPasilloMinima.requestFocus();
            etTemperaturaPasilloMaxima.setEnabled(false);
            etHumedadPasilloMinima.setEnabled(false);
            etHumedadPasilloMaxima.setEnabled(false);
            etTemperaturaSuministroMinima.setEnabled(false);
            etTemperaturaSuministroMaxima.setEnabled(false);
            etTemperaturaRetornoMinima.setEnabled(false);
            etTemperaturaRetornoMaxima.setEnabled(false);
        }
        if (OPCION == 2) {
            fabGuardar.setEnabled(false);
            fabModificar.setEnabled(true);
        }
        if (OPCION == 3) {
            fabGuardar.setEnabled(true);
            fabModificar.setEnabled(false);
            etTemperaturaPasilloMinima.setEnabled(true);
            etTemperaturaPasilloMinima.requestFocus();
            etTemperaturaPasilloMaxima.setEnabled(true);
            etHumedadPasilloMinima.setEnabled(true);
            etHumedadPasilloMaxima.setEnabled(true);
            etTemperaturaSuministroMinima.setEnabled(true);
            etTemperaturaSuministroMaxima.setEnabled(true);
            etTemperaturaRetornoMinima.setEnabled(true);
            etTemperaturaRetornoMaxima.setEnabled(true);
        }
    }

    public void Obtener_Datos() {
        TemperaturaPasilloMinima = etTemperaturaPasilloMinima.getText().toString();
        TemperaturaPasilloMaxima = etTemperaturaPasilloMaxima.getText().toString();
        HumedadPasilloMinima = etHumedadPasilloMinima.getText().toString();
        HumedadPasilloMaxima = etHumedadPasilloMaxima.getText().toString();
        TemperaturaSuministroMinima = etTemperaturaSuministroMinima.getText().toString();
        TemperaturaSuministroMaxima = etTemperaturaSuministroMaxima.getText().toString();
        TemperaturaRetornoMinima = etTemperaturaRetornoMinima.getText().toString();
        TemperaturaRetornoMaxima = etTemperaturaRetornoMaxima.getText().toString();
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