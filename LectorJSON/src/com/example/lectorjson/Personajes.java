package com.example.lectorjson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class Personajes {

	private String nombre;
	private String profesion;
	private String equipo;
	private String Fuerza;
	private String Fortaleza;
	private String Espiritu;
	private String URLimagen;
	public Bitmap imagen;
	


	public Personajes() {
		super();
	}
	
	public String getEquipo() {
		return equipo;
	}
	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getProfesion() {
		return profesion;
	}
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	
	public String getFuerza() {
		return Fuerza;
	}
	public void setFuerza(String fuerza) {
		Fuerza = fuerza;
	}
	public String getFortaleza() {
		return Fortaleza;
	}
	public void setFortaleza(String fortaleza) {
		Fortaleza = fortaleza;
	}
	public String getEspiritu() {
		return Espiritu;
	}
	public void setEspiritu(String espiritu) {
		Espiritu = espiritu;
	}
	public String getURLimagen() {
		return URLimagen;
	}

	public void setURLimagen(String uRLimagen) {
		URLimagen = uRLimagen;
	}
	public Bitmap getImagen() {
		// TODO Auto-generated method stub
		return imagen;
	}
}