package com.example.nfcandroid.Activities;

import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nfcandroid.AlergiaViewModel;
import com.example.nfcandroid.DescripcionComentViewModel;
import com.example.nfcandroid.DescripcionViewModel;
import com.example.nfcandroid.Historial;
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
		
	}
	

	public void setHistorial(Historial historial) {		
        SeparatedListAdapter adapter = new SeparatedListAdapter(this);

		AlergiaAdapter alergiaAdapter = new AlergiaAdapter(this, R.layout.alergia_view_row, historial.AntecedentesAlergico);
		DescriptionAdapter toxicosAdapter = new DescriptionAdapter(this, R.layout.description_view_row, historial.AntecedentesToxicos);
		DescriptionAdapter hereditariasAdapter = new DescriptionAdapter(this, R.layout.description_view_row, historial.EnfermedadesHereditarias);
		DescriptionComentarioAdapter procedimientosAdapter = new DescriptionComentarioAdapter(this, R.layout.description_coment_view_row, historial.AntecedentesProcedimientos);
		DescriptionComentarioAdapter enfermedadesAdapter = new DescriptionComentarioAdapter(this, R.layout.description_coment_view_row, historial.AntecedentesEnfermedades);

        adapter.addSection("Antecedentes Toxicos", toxicosAdapter);
        adapter.addSection("Antecedentes Alergico", alergiaAdapter);
        adapter.addSection("Antecedentes Procedimientos", procedimientosAdapter);
        adapter.addSection("Antecedentes Enfermedades", enfermedadesAdapter);
        adapter.addSection("Enfermedades Hereditarias", hereditariasAdapter);
        
        ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(adapter);
		
		
		TextView tv = (TextView)this.findViewById(R.id.nombreUsuario);
		tv.setText(historial.Nombres + " " + historial.Apellidos);
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
	        TextView fecha = (TextView) v.findViewById(R.id.fecha);
	        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
	        TextView comentarios = (TextView) v.findViewById(R.id.comentarios);
			comentarios.setText("Fecha: " + element.GetShotDate());
	        descripcion.setText("Descripcion: " + element.Descripcion);
			fecha.setText("Comentarios: " + element.Comentarios);
			return v;	
		}	
	}
}
