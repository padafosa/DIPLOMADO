package com.domain.pablo.sia2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
//import android.widget.Adapter;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class MyActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private EditText et1, et2, et3, et4;
    private Cursor fila;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private String listar;

    private TextView mostrar;
    private Button buscar;
    private ListView listaa;
    private Integer contador_visitas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        et1 = (EditText) findViewById(R.id.et_dni);
        et2 = (EditText) findViewById(R.id.et_nombreyapellido);
        et3 = (EditText) findViewById(R.id.et_direccion);
        et4 = (EditText) findViewById(R.id.et_pedido);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    public void onNavigationDrawerItemSelected(int position) {
        //las posiciones inician desde 0

        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new Principal();
                break;
            case 1:
                fragment = new Productos();
                break;
            case 2:
                fragment = new Clientes();
                break;
            case 3:
                fragment = new Pedidos();
                break;
        }

        fragmentManager.beginTransaction()
        .replace(R.id.container, fragment)
        .commit();
    }

    public void onSectionAttached(int number) {

        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section0);
                break;
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            case 4:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    public void alta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"client", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        EditText text = (EditText)findViewById(R.id.et_dni);
        String dni = text.getText().toString();

        EditText text2 = (EditText)findViewById(R.id.et_nombreyapellido);
        String nombre = text2.getText().toString();

        EditText text3 = (EditText)findViewById(R.id.et_direccion);
        String direccion = text3.getText().toString();

        EditText text4 = (EditText)findViewById(R.id.et_pedido);
        String pedido = text4.getText().toString();

        Cursor fila = bd.rawQuery("select * from client where dni=" + dni, null);

        if(!fila.moveToFirst()) {  //devuelve true o false
            ContentValues registro = new ContentValues();  //es una clase para guardar datos

            registro.put("dni", dni);
            registro.put("nom", nombre);
            registro.put("dir", direccion);
            registro.put("ped", pedido);
            String[] args = new String[]{dni,nombre,direccion,pedido};
            bd.execSQL("INSERT INTO client (dni,nom,dir,ped) VALUES (?,?,?,?)", args);

            bd.close();

            ((EditText) findViewById(R.id.et_dni)).setText("");
            ((EditText) findViewById(R.id.et_nombreyapellido)).setText("");
            ((EditText) findViewById(R.id.et_direccion)).setText("");
            ((EditText) findViewById(R.id.et_pedido)).setText("");

            Toast.makeText(this, "Los datos fueron ingresados correctamente",Toast.LENGTH_SHORT).show();
        }
        else{
            bd.close();
            Toast.makeText(this, "La persona ya existe", Toast.LENGTH_SHORT).show();
        }
    }
    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"client", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.

        EditText text = (EditText)findViewById(R.id.et_dni);
        String dni = text.getText().toString();

        Cursor fila = bd.rawQuery(  //devuelve 0 o 1 fila //es una consulta
                "select dni,nom,dir,ped  from client where dni="+dni, null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            ((EditText) findViewById(R.id.et_dni)).setText("");
            ((EditText) findViewById(R.id.et_nombreyapellido)).setText("");
            ((EditText) findViewById(R.id.et_direccion)).setText("");
            ((EditText) findViewById(R.id.et_pedido)).setText("");
        } else
            Toast.makeText(this, "No existe una persona con dicho dni" ,
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }

    public void baja(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"client", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        EditText text = (EditText)findViewById(R.id.et_dni);
        String dni = text.getText().toString();

        Log.i("Borrar","dni="+dni);

        int cant = bd.delete("client","dni="+dni, null);
        bd.close();

        ((EditText) findViewById(R.id.et_dni)).setText("");
        ((EditText) findViewById(R.id.et_nombreyapellido)).setText("");
        ((EditText) findViewById(R.id.et_direccion)).setText("");
        ((EditText) findViewById(R.id.et_pedido)).setText("");

        if (cant == 1)
            Toast.makeText(this, "Se borró la persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe una persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }


    public void modificacion(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"client", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = ((EditText)findViewById(R.id.et_dni)).getText().toString();
        String nombre = ((EditText)findViewById(R.id.et_nombreyapellido)).getText().toString();
        String direccion = ((EditText)findViewById(R.id.et_direccion)).getText().toString();
        String pedido = ((EditText)findViewById(R.id.et_pedido)).getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("nom", nombre);
        registro.put("dir", direccion);
        registro.put("ped", pedido);

        int cant = bd.update("client", registro, "dni="+dni, null);
        bd.close();

        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe una persona con dicho documento",
                    Toast.LENGTH_SHORT).show();
    }


    public void inicio(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"client", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        fila = bd.rawQuery("select * from client order by dni asc", null);

        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            ((EditText) findViewById(R.id.et_dni)).setText(fila.getString(0));
            ((EditText) findViewById(R.id.et_nombreyapellido)).setText(fila.getString(1));
            ((EditText) findViewById(R.id.et_direccion)).setText(fila.getString(2));
            ((EditText) findViewById(R.id.et_pedido)).setText(fila.getString(3));
        } else
            Toast.makeText(this, "No hay registrados" ,
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void anterior(View view){
        try {

            if (!fila.isFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
                fila.moveToPrevious();
                ((EditText) findViewById(R.id.et_dni)).setText(fila.getString(0));
                ((EditText) findViewById(R.id.et_nombreyapellido)).setText(fila.getString(1));
                ((EditText) findViewById(R.id.et_direccion)).setText(fila.getString(2));
                ((EditText) findViewById(R.id.et_pedido)).setText(fila.getString(3));
            } else
                Toast.makeText(this, "Llego al principio de la tabla",
                        Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void siguiente(View view){
        try {

            if (!fila.isLast()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
                fila.moveToNext();
                ((EditText) findViewById(R.id.et_dni)).setText(fila.getString(0));
                ((EditText) findViewById(R.id.et_nombreyapellido)).setText(fila.getString(1));
                ((EditText) findViewById(R.id.et_direccion)).setText(fila.getString(2));
                ((EditText) findViewById(R.id.et_pedido)).setText(fila.getString(3));
            } else
                Toast.makeText(this, "Llego al final de la tabla",
                        Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fin(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"client", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select * from client order by dni asc", null);

        if (fila.moveToLast()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            ((EditText) findViewById(R.id.et_dni)).setText(fila.getString(0));
            ((EditText) findViewById(R.id.et_nombreyapellido)).setText(fila.getString(1));
            ((EditText) findViewById(R.id.et_direccion)).setText(fila.getString(2));
            ((EditText) findViewById(R.id.et_pedido)).setText(fila.getString(3));
        } else
            Toast.makeText(this, "No hay registrados" ,
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public  void listar (View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"client", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        contador_visitas = 1;
        Cursor fila = bd.rawQuery("select * from client order by dni asc", null);
        listar = "";

        if (fila.moveToFirst()) {
            do {
                listar = listar + "VISITA # "+contador_visitas + " ("+fila.getString(0)+")";
                listar = listar + "\n\n" + "NOMBRE CLIENTE :" + fila.getString(1);
                listar = listar + "\n\n" + "PEDIDO CLIENTE :" + fila.getString(3);
                listar = listar + "\n\n\n\n";
                contador_visitas=contador_visitas+1;
            } while(fila.moveToNext());

            ((EditText) findViewById(R.id.list_clientes)).setText(listar);
        }

    }

    public void onReset(View view){
        ((EditText) findViewById(R.id.et_dni)).setText("");
        ((EditText) findViewById(R.id.et_nombreyapellido)).setText("");
        ((EditText) findViewById(R.id.et_direccion)).setText("");
        ((EditText) findViewById(R.id.et_pedido)).setText("");
    }

    public void iniform(View view){
        Calendar c = Calendar.getInstance();

        int mY = c.get(Calendar.YEAR);
        int mM = c.get(Calendar.MONTH);
        int mD = c.get(Calendar.DATE);

        int mHour = c.get(Calendar.HOUR);
        int mMinute = c.get(Calendar.MINUTE);
        int mSecond = c.get(Calendar.SECOND);

        if (mM < 10) {
            if (mD < 10 ) {
                if (mHour < 10 ) {
                    if (mMinute < 10) {
                        ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "0" + mM + "0" + mD + "0" + mHour + "0" + mMinute+mSecond);
                    }
                    else {
                        ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "0" + mM + "0" + mD + "0" + mHour + "" + mMinute+mSecond);
                    }
                }
                else {
                    ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "0" + mM + "0" + mD + "" + mHour + "" + mMinute+mSecond);
                }

            }
            else {
                if (mHour < 10 ) {
                    if (mMinute < 10) {
                        ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "0" + mM + "" + mD + "0" + mHour + "0" + mMinute+mSecond);
                    }
                    else {
                        ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "0" + mM + "" + mD + "0" + mHour + "" + mMinute+mSecond);
                    }
                }
                else {
                    ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "0" + mM + "0" + mD + "" + mHour + "" + mMinute+mSecond);
                }
            }
        }
        else {
            if (mD < 10 ) {
                if (mHour < 10 ) {
                    if (mMinute < 10) {
                        ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "" + mM + "0" + mD + "0" + mHour + "0" + mMinute+mSecond);
                    }
                    else {
                        ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "" + mM + "0" + mD + "0" + mHour + "" + mMinute+mSecond);
                    }
                }
                else {
                    ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "" + mM + "0" + mD + "" + mHour + "" + mMinute+mSecond);
                }

            }
            else {
                ((EditText) findViewById(R.id.et_dni)).setText(""+mY + "" + mM + "" + mD + "" + mHour + "" + mMinute+mSecond);
            }
        }

        ((EditText) findViewById(R.id.et_pedido)).setText("Malarata=\nMalathion=\n Force=\n Karate=\n Trigard=\n");

    }
}