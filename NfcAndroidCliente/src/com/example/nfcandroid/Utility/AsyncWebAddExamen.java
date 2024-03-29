package com.example.nfcandroid.Utility;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.nfcandroid.Analisis;
import com.example.nfcandroid.Activities.AddExamen;

public class AsyncWebAddExamen extends AsyncTask<String, Integer, String>{
	private ProgressDialog progDialog;
	private AddExamen activity;
	
    public AsyncWebAddExamen(AddExamen activity) {
		super();
		this.activity = activity;
	}

	@Override
    protected void onPreExecute() {
        super.onPreExecute(); 
    	progDialog = ProgressDialog.show(this.activity, "Search", "Searching", true, false);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return DownloadWeb.downloadFromServer(params);
        } catch (Exception e) {
            return new String();
        }
    }
    
    @Override
    protected void onPostExecute(String result) 
    {    	
    	ArrayList<Analisis> analisis = new ArrayList<Analisis>();
    	progDialog.dismiss();
        if (result.length() == 0) return;        
        try {
			JSONArray jarray_agendas = new JSONArray(result);
			for(int i=0; i<jarray_agendas.length(); i++) {
				JSONObject jobj_agenda = jarray_agendas.getJSONObject(i);	
				analisis.add( new Analisis(jobj_agenda));
			}			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.activity.SetRadioButtons(analisis);             
    }
}
