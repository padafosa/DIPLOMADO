package com.example.lectorjson;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {

	private TextView mostrar;
	private Button buscar;
	private ListView listaa;
	String searchTerm ;
    //DIRECCION JSON DE CONEXION
    String URL = "http://pflores.byethost4.com/conectar.json";
	
	Activity a;
	Context context;
	static ArrayList<Personajes> lista;
	JSONArray pers;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = new ArrayList<Personajes>();
        a=this;
        context=getApplicationContext();
        listaa = (ListView) findViewById(R.id.listViewLista);
        buscar = (Button) findViewById(R.id.busqueda);

    	buscar.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new GetContacts(listaa).execute();
			}   		
    	});
    	
	}
	private class GetContacts extends AsyncTask<Void, Void, Void> {
		 ListView list;
        private ProgressDialog pDialog;
	      public GetContacts(ListView listaa) {
			this.list=listaa;
		}

		@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(MainActivity.this);
	            pDialog.setMessage("Getting Data ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	      }
 
        @Override
        protected Void doInBackground(Void... arg0) {

            JSONParser sh = new JSONParser();
 
            String jsonStr = sh.makeServiceCall(URL, JSONParser.GET);

 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    pers = jsonObj.getJSONArray("Personajes");
 
                    // looping through All Equipos
                    for (int i = 0; i < pers.length(); i++) {
                        JSONObject c = pers.getJSONObject(i);
                        
                        String equipo = c.getString("Documento");
                        String name = c.getString("Nombre");
                        String direccion = c.getString("Direccion");
                        String imagen = c.getString("image");

                        Personajes e=new Personajes();
                        e.setURLimagen(imagen);
                        e.setNombre(name);
                        e.setEquipo(equipo);
                        e.setDireccion(direccion);
                        // adding contact to contact list
                        lista.add(e);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Esta habiendo problemas para cargar la informacion de clientes");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing()){
                pDialog.dismiss();
        	}
        	new CargarListTask().execute();
    }
//HILO PARA CARGAR LOS DATOS EN EL LISTVIEW
class CargarListTask extends AsyncTask<Void,String,Adapter>{
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	    }
	  
	    protected Adapter doInBackground(Void... arg0) {

	        try{

	        }catch(Exception ex){
	            ex.printStackTrace();
	        }
	  
	        Adapter adaptador = new Adapter(a,lista);
	        return adaptador;
	    }
	  

	    protected void onPostExecute(Adapter result) {
	        super.onPostExecute(result);
	        listaa.setAdapter(result);

	    }
	}
	}
}