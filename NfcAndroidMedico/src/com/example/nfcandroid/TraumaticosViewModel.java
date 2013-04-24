package com.example.nfcandroid;


import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.nfcandroid.Utility.Tools;

import android.text.format.DateFormat;



public class TraumaticosViewModel {
	public Date Fecha;
	public String Descripción;
	
	public TraumaticosViewModel(JSONObject json) {
		try {
			Fecha =  Tools.JsonDateToDate(json.getString("Fecha"));
			Descripción = json.getString("Descripción");
		} catch (JSONException e) {
		}
	}

	public TraumaticosViewModel() {
	}
	public CharSequence GetShotDate() {
		if (Fecha != null)	return DateFormat.format("dd-MMMM-yyyy", Fecha); 
		return "";
	}
}
