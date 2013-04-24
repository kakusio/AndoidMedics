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
	public String Edad;

	public String Religion;
	public String Ocupacion;
	public String EstadoCivil;
	public String LugarNacimmiento;
	public String NivelEducacion;
	public String Raza;
	public String Peso;
	public String Altura;
	public String UrlPath;
	
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
			Direccion =  json.get("Direccion").toString();
			TipoDeSangre = json.get("TipoDeSangre").toString();
			FechaDeDefuncion = json.get("FechaDeDefuncion").toString();
			Username = json.get("Username").toString();
			Password = json.get("Password").toString();
			Edad = json.get("Edad").toString();
			Religion = json.get("Religion").toString();
			Ocupacion = json.get("Ocupacion").toString();
			EstadoCivil = json.get("EstadoCivil").toString();
			LugarNacimmiento = json.get("LugarNacimmiento").toString();
			NivelEducacion = json.get("NivelEducacion").toString();
			Raza = json.get("Raza").toString();
			Peso = json.get("Peso").toString();
			Altura = json.get("Altura").toString();
			UrlPath = json.get("UrlPath").toString();
		} catch (JSONException e) {
		}
	}

	public Persona() {
	}
}
