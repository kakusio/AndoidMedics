package com.example.nfcandroid;

import org.json.JSONException;
import org.json.JSONObject;

public class Analisis {
	public String Descripcion;
	public String idAnalisis;
	
	public Analisis(JSONObject json) {
		try {
			Descripcion = json.getString("Descripcion");
			idAnalisis = json.getString("idAnalisis");
		} catch (JSONException e) {
		}
	}

	public Analisis() {
	}
}
