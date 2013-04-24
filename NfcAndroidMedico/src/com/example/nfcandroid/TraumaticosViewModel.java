package com.example.nfcandroid;


import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.nfcandroid.Utility.Tools;

import android.text.format.DateFormat;



public class TraumaticosViewModel {
	public Date Fecha;
	public String Descripci�n;
	
	public TraumaticosViewModel(JSONObject json) {
		try {
			Fecha =  Tools.JsonDateToDate(json.getString("Fecha"));
			Descripci�n = json.getString("Descripci�n");
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
