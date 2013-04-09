package com.example.nfcandroid.Utility;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.nfcandroid.Historial;
import com.example.nfcandroid.Activities.HistorialMedic;

public class AsyncWebHistorialMedicos extends AsyncTask<String, Integer, String>{
	private ProgressDialog progDialog;
	private HistorialMedic activity;
	
    public AsyncWebHistorialMedicos(HistorialMedic activity) {
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
    	Historial historial = new Historial();
    	progDialog.dismiss();
        if (result.length() == 0) return;        
        try {
        	JSONObject jobj = new JSONObject(result);
			historial = new Historial(jobj);		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.activity.setHistorial(historial);             
    }
}
