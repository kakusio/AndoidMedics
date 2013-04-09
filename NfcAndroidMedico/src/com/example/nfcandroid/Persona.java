package com.example.nfcandroid;

import org.json.JSONException;
import org.json.JSONObject;

public class Persona {
	public String idPersona;
	public String Nombres;
	public String Apellidos;
	public String Sexo;
	public String Cedula;
	public String FechaDeNacimiento;
	public String Email;
	public String TelefonoResidencial;
	public String TelefonoCelular;
	public String idProvincia;
	public String Direccion;
	public String TipoDeSangre;
	public String FechaDeDefuncion;
	public String Username;
	public String Password;
	
	public Persona(JSONObject json) {
		try {
			idPersona = json.get("idPersona").toString();
			Nombres = json.get("Nombres").toString();
			Apellidos = json.get("Apellidos").toString();
			Sexo = json.get("Sexo").toString();
			Cedula = json.get("Cedula").toString();
			FechaDeNacimiento = json.get("FechaDeNacimiento").toString();
			Email = json.get("Email").toString();
			TelefonoResidencial = json.get("TelefonoResidencial").toString();
			TelefonoCelular = json.get("TelefonoCelular").toString();
			idProvincia = json.get("idProvincia").toString();
			TipoDeSangre = json.get("TipoDeSangre").toString();
			FechaDeDefuncion = json.get("FechaDeDefuncion").toString();
			Username = json.get("Username").toString();
			Password = json.get("Password").toString();
		} catch (JSONException e) {
		}
	}

	public Persona() {
	}
}