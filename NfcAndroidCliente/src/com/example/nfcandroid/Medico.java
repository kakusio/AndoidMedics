package com.example.nfcandroid;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.nfcandroid.Utility.Tools;

public class Medico extends Persona{
	public boolean IsMedico;
	public String[] InstitucionesNombre;
	public String[] EspecialidadesDescripcion;
	
	public Medico(JSONObject json) {
		try {
			idPersona = json.getString("idPersona");
			Nombres = json.getString("Nombres");
			Apellidos = json.getString("Apellidos");
			Sexo = json.getString("Sexo");
			Cedula = json.getString("Cedula");
			FechaDeNacimiento = json.getString("FechaDeNacimiento");
			Email = json.getString("Email");
			TelefonoResidencial = json.getString("TelefonoResidencial");
			TelefonoCelular = json.getString("TelefonoCelular");
			idProvincia = json.getString("idProvincia");
			TipoDeSangre = json.getString("TipoDeSangre");
			FechaDeDefuncion = json.getString("FechaDeDefuncion");
			Username = json.getString("Username");
			Password = json.getString("Password");
			Edad = json.getString("Edad");
			Religion = json.getString("Religion");
			Ocupacion = json.getString("Ocupacion");
			EstadoCivil = json.getString("EstadoCivil");
			LugarNacimmiento = json.getString("LugarNacimmiento");
			NivelEducacion = json.getString("NivelEducacion");
			Raza = json.getString("Raza");
			Peso = json.getString("Peso");
			Altura = json.getString("Altura");
			UrlPath = json.getString("UrlPath");
			IsMedico = json.getBoolean("IsMedico");
			
			InstitucionesNombre = Tools.getStringListFromJsom(json.getJSONArray("InstitucionesNombre"));
			EspecialidadesDescripcion = Tools.getStringListFromJsom(json.getJSONArray("EspecialidadesDescripcion"));
		} catch (JSONException e) {
		}
	}

	public Medico() {
	}
}
