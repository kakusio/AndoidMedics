package com.example.nfcandroid;

import org.json.JSONException;
import org.json.JSONObject;


public class AlergiaViewModel {
	public String ElemmentoAlergico;
	public String TipoDeAlergia;
	public String Comentarios;
	
	public AlergiaViewModel(JSONObject json) {
		try {
			ElemmentoAlergico = json.getString("ElemmentoAlergico");
			TipoDeAlergia = json.getString("TipoDeAlergia");
			Comentarios = json.getString("Comentarios");
		} catch (JSONException e) {
		}
	}

	public AlergiaViewModel() {
	}
}
