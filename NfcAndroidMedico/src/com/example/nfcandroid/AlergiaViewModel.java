package com.example.nfcandroid;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateFormat;

import com.example.nfcandroid.Utility.Tools;


public class AlergiaViewModel {
	public Date Fecha;
	public String ElemmentoAlergico;
	public String TipoDeAlergia;
	public String Comentarios;
	
	public AlergiaViewModel(JSONObject json) {
		try {
			Fecha = Tools.JsonDateToDate(json.getString("Fecha"));
			ElemmentoAlergico = json.getString("ElemmentoAlergico");
			TipoDeAlergia = json.getString("TipoDeAlergia");
			Comentarios = json.getString("Comentarios");
		} catch (JSONException e) {
		}
	}

	public AlergiaViewModel() {
	}
	public CharSequence GetShotDate() {
		if (Fecha != null)	return DateFormat.format("dd-MMMM-yyyy", Fecha); 
		return "";
	}
}
