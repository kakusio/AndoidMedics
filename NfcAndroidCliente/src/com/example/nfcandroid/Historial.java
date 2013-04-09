package com.example.nfcandroid;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Historial {
	public String idPersona;
	public String Nombres;
	public String Apellidos;
	public List<DescripcionViewModel> AntecedentesToxicos = new ArrayList<DescripcionViewModel>();
	public List<AlergiaViewModel> AntecedentesAlergico = new ArrayList<AlergiaViewModel>();
	public List<DescripcionComentViewModel> AntecedentesProcedimientos = new ArrayList<DescripcionComentViewModel>();
	public List<DescripcionComentViewModel> AntecedentesEnfermedades = new ArrayList<DescripcionComentViewModel>();
	public List<DescripcionViewModel> EnfermedadesHereditarias = new ArrayList<DescripcionViewModel>();
	
	public Historial(JSONObject json) {
		try {
			idPersona = json.getString("idPersona");
			Nombres = json.getString("Nombres");
			Apellidos = json.getString("Apellidos");
			JSONArray JA_toxicos = json.getJSONArray("AntecedentesToxicos");
			for(int i=0; i<JA_toxicos.length(); i++) {
				JSONObject jobj= JA_toxicos.getJSONObject(i);	
				AntecedentesToxicos.add( new DescripcionViewModel(jobj));
			}		
			JSONArray JA_alergicos = json.getJSONArray("AntecedentesAlergico");
			for(int i=0; i<JA_alergicos.length(); i++) {
				JSONObject jobj= JA_alergicos.getJSONObject(i);	
				AntecedentesAlergico.add( new AlergiaViewModel(jobj));
			}		
			JSONArray JA_procedimientos = json.getJSONArray("AntecedentesProcedimientos");
			for(int i=0; i<JA_procedimientos.length(); i++) {
				JSONObject jobj= JA_procedimientos.getJSONObject(i);	
				AntecedentesProcedimientos.add( new DescripcionComentViewModel(jobj));
			}		

			JSONArray JA_enfermedades = json.getJSONArray("AntecedentesEnfermedades");
			for(int i=0; i<JA_enfermedades.length(); i++) {
				JSONObject jobj= JA_enfermedades.getJSONObject(i);	
				AntecedentesEnfermedades.add( new DescripcionComentViewModel(jobj));
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
