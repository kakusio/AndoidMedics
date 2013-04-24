package com.example.nfcandroid.Activities;

import java.net.URLEncoder;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nfcandroid.Persona;
import com.example.nfcandroid.R;
import com.example.nfcandroid.StaticData;
import com.example.nfcandroid.Utility.AsyncWebMisPacientesMedicos;
import com.example.nfcandroid.Utility.Secure;

public class MisPacientesMedic extends Activity {
	String Password;
	String Username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mis_pacientes_medic);
		
		String idPersona = getIntent().getExtras().getString("idPersona");
		Password = getIntent().getExtras().getString("Password");
		Username = getIntent().getExtras().getString("Username");
		
		
		String URL = "/api/personas/GetPacientes?id=" + idPersona;		
		String encoded = URL.replaceAll("en", "");
		String encryptEncode = "";
		try {
			String encrypt = new Secure().encrypt(encoded, Password);
			encryptEncode = URLEncoder.encode(encrypt.toString(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String agenda_url = "http://" + StaticData.IpAdd	+ ":4001/api/user?id=" + Username + "&url=" + encryptEncode;
		
		
		
		
		new AsyncWebMisPacientesMedicos(MisPacientesMedic.this).execute(agenda_url);
		
	}
	
	public class MyCustomAdapter extends ArrayAdapter<String> {
		ArrayList<Persona> pacientes;
		public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Persona> pacientes) {
			super(context, textViewResourceId, new String[pacientes.size()]);
			this.pacientes =pacientes;
		}
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {			
			View row = convertView;
	        if(row==null){
				LayoutInflater inflater=getLayoutInflater();
				row=inflater.inflate(R.layout.row_mis_pacientes, parent, false);	
			}   
	        TextView text=(TextView)row.findViewById(R.id.text);			
			text.setText(pacientes.get(position).Nombres);
			text.setOnClickListener(new OnClickListener() {				
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(), HistorialMedic.class);
					intent.putExtra("idPersona", pacientes.get(position).idPersona);
					intent.putExtra("Password", Password);
					intent.putExtra("Username", Username);
					startActivity(intent);
				}
			});
			return row;	
		}	
	}

	public void setPacientes(ArrayList<Persona> pacientes) {
		MyCustomAdapter listAdaper = new MyCustomAdapter(MisPacientesMedic.this, R.layout.row_mis_pacientes, pacientes);
		ListView expandableListViewMedicos = (ListView) findViewById(R.id.listViewMedicos);
		expandableListViewMedicos.setAdapter(listAdaper);
	}
}
