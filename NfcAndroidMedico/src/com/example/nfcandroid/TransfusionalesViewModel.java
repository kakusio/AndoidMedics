package com.example.nfcandroid;


import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.nfcandroid.Utility.Tools;

import android.text.format.DateFormat;



public class TransfusionalesViewModel {
	public Date Fecha;
	public String Notas;
	public String OrganosDescripcion;
	
	public TransfusionalesViewModel(JSONObject json) {
		try {
			Fecha =  Tools.JsonDateToDate(json.getString("Fecha"));
			Notas = json.getString("Notas");
			OrganosDescripcion = json.getString("OrganosDescripcion");
		} catch (JSONException e) {
		}
	}

	public TransfusionalesViewModel() {
	}
	public CharSequence GetShotDate() {
		if (Fecha != null)	return DateFormat.format("dd-MMMM-yyyy", Fecha); 
		return "";
	}
}
