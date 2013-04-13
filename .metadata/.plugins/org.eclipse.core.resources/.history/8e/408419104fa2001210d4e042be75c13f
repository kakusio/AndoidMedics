package com.example.nfcandroid;

import java.sql.Date;

import org.json.JSONException;
import org.json.JSONObject;



public class DescripcionComentViewModel {
	public Date Fecha;
	public String Descripcion;
	public String Comentarios;
	
	public DescripcionComentViewModel(JSONObject json) {
		try {
			Fecha = new Date(json.getLong("Fecha"));
			Descripcion = json.getString("Descripcion");
			Comentarios = json.getString("Comentarios");
		} catch (JSONException e) {
		}
	}

	public DescripcionComentViewModel() {
	}
}
