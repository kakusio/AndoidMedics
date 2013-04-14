package com.example.nfcandroid;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Historial {
	public Persona DatosPersonales;
	
	public List<DescripcionViewModel> Toxicos = new ArrayList<DescripcionViewModel>();
	public List<AlergiaViewModel> Alergias = new ArrayList<AlergiaViewModel>();
	public List<DescripcionComentViewModel> Procedimientos = new ArrayList<DescripcionComentViewModel>();
	public List<DescripcionComentViewModel> Enfermedades = new ArrayList<DescripcionComentViewModel>();
	public List<DescripcionViewModel> EnfermedadesHereditarias = new ArrayList<DescripcionViewModel>();
	
	public Historial(JSONObject json) {
		try {
			DatosPersonales = new Persona(json.getJSONObject("DatosPersonales"));
			
			JSONArray JA_toxicos = json.getJSONArray("Toxicos");
			for(int i=0; i<JA_toxicos.length(); i++) {
				JSONObject jobj= JA_toxicos.getJSONObject(i);	
				Toxicos.add( new DescripcionViewModel(jobj));
			}		
			JSONArray JA_alergicos = json.getJSONArray("Alergias");
			for(int i=0; i<JA_alergicos.length(); i++) {
				JSONObject jobj= JA_alergicos.getJSONObject(i);	
				Alergias.add( new AlergiaViewModel(jobj));
			}		
			JSONArray JA_procedimientos = json.getJSONArray("Procedimientos");
			for(int i=0; i<JA_procedimientos.length(); i++) {
				JSONObject jobj= JA_procedimientos.getJSONObject(i);	
				Procedimientos.add( new DescripcionComentViewModel(jobj));
			}		

			JSONArray JA_enfermedades = json.getJSONArray("Enfermedades");
			for(int i=0; i<JA_enfermedades.length(); i++) {
				JSONObject jobj= JA_enfermedades.getJSONObject(i);	
				Enfermedades.add( new DescripcionComentViewModel(jobj));
			}	
			JSONArray JA_hereditarias = json.getJSONArray("EnfermedadesHereditarias");
			for(int i=0; i<JA_hereditarias.length(); i++) {
				JSONObject jobj= JA_hereditarias.getJSONObject(i);	
				EnfermedadesHereditarias.add( new DescripcionViewModel(jobj));
			}		
		} catch (JSONException e) {
		}
	}

	public Historial() {
	}
}
