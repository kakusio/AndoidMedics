package com.example.nfcandroid;


import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.nfcandroid.Utility.Tools;

import android.text.format.DateFormat;



public class SocioEconomicoViewModel {
	public Date Fecha;
	public String Notas;
	
	public SocioEconomicoViewModel(JSONObject json) {
		try {
			Fecha =  Tools.JsonDateToDate(json.getString("Fecha"));
			Notas = json.getString("Notas");
		} catch (JSONException e) {
		}
	}

	public SocioEconomicoViewModel() {
	}
	public CharSequence GetShotDate() {
		if (Fecha != null)	return DateFormat.format("dd-MMMM-yyyy", Fecha); 
		return "";
	}
}
