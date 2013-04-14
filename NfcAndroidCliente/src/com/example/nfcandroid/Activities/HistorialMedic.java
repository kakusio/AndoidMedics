package com.example.nfcandroid.Activities;

import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nfcandroid.AlergiaViewModel;
import com.example.nfcandroid.DescripcionComentViewModel;
import com.example.nfcandroid.DescripcionViewModel;
import com.example.nfcandroid.Historial;
import com.example.nfcandroid.Persona;
import com.example.nfcandroid.R;
import com.example.nfcandroid.StaticData;
import com.example.nfcandroid.Utility.AsyncWebHistorialMedicos;
import com.example.nfcandroid.Utility.Secure;
import com.example.nfcandroid.Utility.SeparatedListAdapter;

public class HistorialMedic extends Activity {
	String idPersona;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historial_medico);
		
		idPersona = getIntent().getExtras().getString("idPersona");
		String Password = getIntent().getExtras().getString("Password");
		String Username = getIntent().getExtras().getString("Username");
		
		
		String URL = "/api/personas/GetHistorial?id=" + idPersona;		
		String encoded = URL.replaceAll("en", "");
		String encryptEncode = "";
		try {
			String encrypt = new Secure().encrypt(encoded, Password);
			encryptEncode = URLEncoder.encode(encrypt.toString(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String historial_url = "http://" + StaticData.IpAdd	+ ":4001/api/user?id=" + Username + "&url=" + encryptEncode;		
		new AsyncWebHistorialMedicos(HistorialMedic.this).execute(historial_url);
		setAcordeonEvents();
		
	}


	public void setHistorial(Historial historial) {		
        SeparatedListAdapter patologicosAdapter = new SeparatedListAdapter(this);
        SeparatedListAdapter noPatologicosAdapter = new SeparatedListAdapter(this);
        SeparatedListAdapter heredoFamiliaresAdapter = new SeparatedListAdapter(this);
        SeparatedListAdapter socioEconomicosAdapter = new SeparatedListAdapter(this);

		AlergiaAdapter alergiaAdapter = new AlergiaAdapter(this, R.layout.alergia_view_row, historial.Alergias);
		DescriptionAdapter toxicosAdapter = new DescriptionAdapter(this, R.layout.description_view_row, historial.Toxicos);
		DescriptionAdapter hereditariasAdapter = new DescriptionAdapter(this, R.layout.description_view_row, historial.EnfermedadesHereditarias);
		DescriptionComentarioAdapter procedimientosAdapter = new DescriptionComentarioAdapter(this, R.layout.description_coment_view_row, historial.Procedimientos);
		DescriptionComentarioAdapter enfermedadesAdapter = new DescriptionComentarioAdapter(this, R.layout.description_coment_view_row, historial.Enfermedades);
        ArrayAdapter<String> datosPersonalesAdapter = new ArrayAdapter<String>(this,R.layout.simple_view_row, getDatosPersonales(historial.DatosPersonales));

		noPatologicosAdapter.addSection("Toxicos", toxicosAdapter);
		patologicosAdapter.addSection("Enfermedades", enfermedadesAdapter);
		patologicosAdapter.addSection("Procedimientos", procedimientosAdapter);
		patologicosAdapter.addSection("Alergias", alergiaAdapter);
		heredoFamiliaresAdapter.addSection("Enfermedades Hereditarias", hereditariasAdapter);
        
        ListView patologicosList = (ListView) findViewById(R.id.patologicosList);
        ListView noPatologicosList = (ListView) findViewById(R.id.noPatologicosList);
        ListView heredoFamiliaresList = (ListView) findViewById(R.id.heredoFamiliaresList);
        ListView socioEconomicosList = (ListView) findViewById(R.id.socioEconomicosList);
        ListView datosPersonalesList = (ListView) findViewById(R.id.datosPersonalesList);
        
        patologicosList.setAdapter(patologicosAdapter);
        noPatologicosList.setAdapter(noPatologicosAdapter);
        heredoFamiliaresList.setAdapter(heredoFamiliaresAdapter);
        socioEconomicosList.setAdapter(socioEconomicosAdapter);
        datosPersonalesList.setAdapter(datosPersonalesAdapter);
		
		
		TextView tv = (TextView)this.findViewById(R.id.nombreUsuario);
		tv.setText(historial.DatosPersonales.Nombres + " " + historial.DatosPersonales.Apellidos);
	}
	
	public class AlergiaAdapter extends ArrayAdapter<String> {
		List<AlergiaViewModel> antecedentesAlergico;
		public AlergiaAdapter(Context context, int textViewResourceId, List<AlergiaViewModel> antecedentesAlergico) {
			super(context, textViewResourceId, new String[antecedentesAlergico.size()]);
			this.antecedentesAlergico = antecedentesAlergico;
		}
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {			
			View v = convertView;
	        if(v==null){
				LayoutInflater inflater=getLayoutInflater();
				v=inflater.inflate(R.layout.alergia_view_row, parent, false);	
			}
	        AlergiaViewModel element = antecedentesAlergico.get(position);
	        TextView fecha = (TextView) v.findViewById(R.id.fecha);
			TextView alergia = (TextView) v.findViewById(R.id.alergia);
			TextView comentarios = (TextView) v.findViewById(R.id.comentarios);
			fecha.setText("Fecha: " + element.GetShotDate());
			alergia.setText("Alergia: " + element.TipoDeAlergia + ", " + element.ElemmentoAlergico);
			comentarios.setText("Comentarios: " + element.Comentarios);
			return v;	
		}	
	}
	
	public class DescriptionAdapter extends ArrayAdapter<String> {
		 List<DescripcionViewModel> antecedentes;
		public DescriptionAdapter(HistorialMedic context, int alergiaViewRow, List<DescripcionViewModel> antecedentes) {
			super(context, alergiaViewRow, new String[antecedentes.size()]);
			this.antecedentes = antecedentes;
		}
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {			
			View v = convertView;
	        if(v==null){
				LayoutInflater inflater=getLayoutInflater();
				v=inflater.inflate(R.layout.description_view_row, parent, false);	
			}
	        DescripcionViewModel element = antecedentes.get(position);
	        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
	        descripcion.setText("Descripcion: " + element.Descripcion);
			return v;	
		}	
	}
	
	public class DescriptionComentarioAdapter extends ArrayAdapter<String> {
		 List<DescripcionComentViewModel> antecedentes;
		public DescriptionComentarioAdapter(HistorialMedic context, int alergiaViewRow, List<DescripcionComentViewModel> antecedentes) {
			super(context, alergiaViewRow, new String[antecedentes.size()]);
			this.antecedentes = antecedentes;
		}
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {			
			View v = convertView;
	        if(v==null){
				LayoutInflater inflater=getLayoutInflater();
				v=inflater.inflate(R.layout.description_coment_view_row, parent, false);	
			}
	        DescripcionComentViewModel element = antecedentes.get(position);
	        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
	        TextView fecha = (TextView) v.findViewById(R.id.fecha);
	        TextView comentarios = (TextView) v.findViewById(R.id.comentarios);
	        
	        descripcion.setText("Descripcion: " + element.Descripcion);
			fecha.setText("Comentarios: " + element.Comentarios);
			comentarios.setText("Fecha: " + element.GetShotDate());
			return v;	
		}	
	}
	

	private void setAcordeonEvents() {
		TextView personales = (TextView) findViewById(R.id.Personales);
		LinearLayout personalesLayout = (LinearLayout) findViewById(R.id.personaleslayout);
		setVisibilityEvent(personales, personalesLayout);

		TextView heredoFamiliares = (TextView) findViewById(R.id.heredoFamiliares);
		LinearLayout heredoFamiliaresLayout = (LinearLayout) findViewById(R.id.heredoFamiliaresLayout);
		setVisibilityEvent(heredoFamiliares, heredoFamiliaresLayout);

		TextView socioEconomicos = (TextView) findViewById(R.id.socioEconomicos);
		LinearLayout socioEconomicosLayout = (LinearLayout) findViewById(R.id.socioEconomicosLayout);
		setVisibilityEvent(socioEconomicos, socioEconomicosLayout);

		TextView patologicos = (TextView) findViewById(R.id.patologicos);
		LinearLayout patologicosLayout = (LinearLayout) findViewById(R.id.patologicosLayout);
		setVisibilityEvent(patologicos,  patologicosLayout);

		TextView noPatologicos = (TextView) findViewById(R.id.noPatologicos);
		LinearLayout noPatologicosLayout = (LinearLayout) findViewById(R.id.noPatologicosLayout);
		setVisibilityEvent(noPatologicos, noPatologicosLayout);

		TextView datosPerosonalesTest = (TextView) findViewById(R.id.datosPerosonalesTest);
		LinearLayout datosPerosonalesLayout = (LinearLayout) findViewById(R.id.datosPerosonalesLayout);
		setVisibilityEvent(datosPerosonalesTest, datosPerosonalesLayout);

		TextView antecedentesTest = (TextView) findViewById(R.id.antecedentesTest);
		LinearLayout antecedentesLayout = (LinearLayout) findViewById(R.id.antecedentesLayout);
		setVisibilityEvent(antecedentesTest, antecedentesLayout);
	}
	
	private void setVisibilityEvent(final TextView test, final LinearLayout layout){
		layout.setVisibility(View.GONE);
		test.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if (layout.getVisibility() == View.VISIBLE) layout.setVisibility(View.GONE);
				else layout.setVisibility(View.VISIBLE);
				
			}
		});
	}
	
	private String[] getDatosPersonales(Persona datosPersonales){
		String[] datos = new String[14];
		datos[0] = "Sexo: " + datosPersonales.Sexo;
		datos[1] = "Edad: " + datosPersonales.Edad;
		datos[2] = "Telefono: " + datosPersonales.TelefonoResidencial;
		datos[3] = "Email: " + datosPersonales.Email;
		datos[4] = "Cedula: " + datosPersonales.Cedula;
		datos[5] = "Direccion: " + datosPersonales.Direccion;
		datos[6] = "Tipo De Sangre: " + datosPersonales.TipoDeSangre;
		datos[7] = "Religion: " + datosPersonales.Religion;
		datos[8] = "Ocupacion: " + datosPersonales.Ocupacion;
		datos[9] = "EstadoCivil: " + datosPersonales.EstadoCivil;
		datos[10] = "NivelEducacion: " + datosPersonales.NivelEducacion;
		datos[11] = "Raza: " + datosPersonales.Raza;
		datos[12] = "Peso: " + datosPersonales.Peso;
		datos[13] = "Altura: " + datosPersonales.Altura;
		return datos;
	}
}
