package com.example.nfcandroid.Utility;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.nfcandroid.Persona;
import com.example.nfcandroid.Activities.MisPacientesMedic;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AsyncWebMisPacientesMedicos extends AsyncTask<String, Integer, String>{
	private ProgressDialog progDialog;
	private MisPacientesMedic activity;
	
    public AsyncWebMisPacientesMedicos(MisPacientesMedic activity) {
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
    	ArrayList<Persona> medicos = new ArrayList<Persona>();
    	progDialog.dismiss();
        if (result.length() == 0) return;        
        try {
			JSONArray jarray_agendas = new JSONArray(result);
			for(int i=0; i<jarray_agendas.length(); i++) {
				JSONObject jobj_agenda = jarray_agendas.getJSONObject(i);	
				medicos.add( new Persona(jobj_agenda));
			}			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.activity.setPacientes(medicos);             
    }
}
