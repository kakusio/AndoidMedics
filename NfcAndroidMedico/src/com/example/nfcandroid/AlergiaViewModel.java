package com.example.nfcandroid;

import java.sql.Date;

import org.json.JSONException;
import org.json.JSONObject;


public class AlergiaViewModel {
	public Date Fecha;
	public String ElemmentoAlergico;
	public String TipoDeAlergia;
	public String Comentarios;
	
	public AlergiaViewModel(JSONObject json) {
		try {
			Fecha = new Date(json.getLong("Fecha"));
			ElemmentoAlergico = json.getString("ElemmentoAlergico");
			TipoDeAlergia = json.getString("TipoDeAlergia");
			Comentarios = json.getString("Comentarios");
		} catch (JSONException e) {
		}
	}

	public AlergiaViewModel() {
	}
}
