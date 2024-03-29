package com.example.nfcandroid;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.nfcandroid.Utility.Tools;

import android.text.format.DateFormat;



public class DescripcionComentViewModel {
	public Date Fecha;
	public String Descripcion;
	public String Comentarios;
	
	public DescripcionComentViewModel(JSONObject json) {
		try {
			Fecha =  Tools.JsonDateToDate(json.getString("Fecha"));
			Descripcion = json.getString("Descripcion");
			Comentarios = json.getString("Comentarios");
		} catch (JSONException e) {
		}
	}

	public DescripcionComentViewModel() {
	}
	public CharSequence GetShotDate() {
		if (Fecha != null)	return DateFormat.format("dd-MMMM-yyyy", Fecha); 
		return "";
	}
}