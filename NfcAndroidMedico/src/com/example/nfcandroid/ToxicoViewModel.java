package com.example.nfcandroid;


import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.nfcandroid.Utility.Tools;

import android.text.format.DateFormat;



public class ToxicoViewModel {
	public Date Fecha;
	public String ToxicosDescripcion;
	
	public ToxicoViewModel(JSONObject json) {
		try {
			Fecha =  Tools.JsonDateToDate(json.getString("Fecha"));
			ToxicosDescripcion = json.getString("ToxicosDescripcion");
		} catch (JSONException e) {
		}
	}

	public ToxicoViewModel() {
	}
	public CharSequence GetShotDate() {
		if (Fecha != null)	return DateFormat.format("dd-MMMM-yyyy", Fecha); 
		return "";
	}
}
