package com.example.nfcandroid;


import org.json.JSONException;
import org.json.JSONObject;



public class EnfermedadHereditariaViewModel {
	public String EnfermedadesDescripcion;
	public String Familiar;
	
	public EnfermedadHereditariaViewModel(JSONObject json) {
		try {
			EnfermedadesDescripcion = json.getString("EnfermedadesDescripcion");
			Familiar = json.getString("Familiar");
		} catch (JSONException e) {
		}
	}

	public EnfermedadHereditariaViewModel() {
	}
}
