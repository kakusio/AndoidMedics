package com.example.nfcandroid.Activities;

import java.net.URLEncoder;
import java.util.List;

import android.app.TabActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.nfcandroid.AlergiaViewModel;
import com.example.nfcandroid.DescripcionComentViewModel;
import com.example.nfcandroid.EnfermedadHereditariaViewModel;
import com.example.nfcandroid.Historial;
import com.example.nfcandroid.Persona;
import com.example.nfcandroid.R;
import com.example.nfcandroid.SocioEconomicoViewModel;
import com.example.nfcandroid.StaticData;
import com.example.nfcandroid.ToxicoViewModel;
import com.example.nfcandroid.TraumaticosViewModel;
import com.example.nfcandroid.Utility.AsyncWebHistorialMedicos;
import com.example.nfcandroid.Utility.Secure;

public class HistorialMedic extends TabActivity {
	String idPersona;
	LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historial_medico_main);
		inflater = getLayoutInflater();
		
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


	public void setHistorial(final Historial historial) {		
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		
		TabHost.TabSpec tab1 = tabHost.newTabSpec("perosonales");
	    tab1.setIndicator("Perfil");
	    tab1.setContent(new TabHost.TabContentFactory() {
	        public View createTabContent(String tag) {
	            ListView list_view_datos = new ListView(HistorialMedic.this);
	            ArrayAdapter<String> datosPersonalesAdapter = new ArrayAdapter<String>(HistorialMedic.this,R.layout.row_simple_view, getDatosPersonales(historial.DatosPersonales));
	            TextView text_header = (TextView) inflater.inflate(R.layout.list_header, list_view_datos, false);
	            text_header.setText("Perfil");
				list_view_datos.addHeaderView(text_header);				
	            list_view_datos.setAdapter(datosPersonalesAdapter);
	            return list_view_datos;
	        }
	    });
		
		TabHost.TabSpec tab2 = tabHost.newTabSpec("enfermedades");
	    tab2.setIndicator("Enfermedad");
	    tab2.setContent(new TabHost.TabContentFactory() {
	        public View createTabContent(String tag) {
	            ListView list_view_datos = new ListView(HistorialMedic.this);
	            DescriptionComentarioAdapter enfermedadesAdapter = new DescriptionComentarioAdapter(HistorialMedic.this, R.layout.row_description_coment_view, historial.Enfermedades);
	            TextView text_header = (TextView) inflater.inflate(R.layout.list_header, list_view_datos, false);
	            text_header.setText("Enfermedad");
				list_view_datos.addHeaderView(text_header);				
	            list_view_datos.setAdapter(enfermedadesAdapter);
	            return list_view_datos;
	        }
	    });
		
		TabHost.TabSpec tab3 = tabHost.newTabSpec("alergias");
	    tab3.setIndicator("Alergia");
	    tab3.setContent(new TabHost.TabContentFactory() {
	        public View createTabContent(String tag) {
	            ListView list_view_datos = new ListView(HistorialMedic.this);
	            AlergiaAdapter alergiaAdapter = new AlergiaAdapter(HistorialMedic.this, R.layout.row_alergia_view, historial.Alergias);
	            TextView text_header = (TextView) inflater.inflate(R.layout.list_header, list_view_datos, false);
	            text_header.setText("Alergia");
				list_view_datos.addHeaderView(text_header);				
	            list_view_datos.setAdapter(alergiaAdapter);
	            return list_view_datos;
	        }
	    });
		
		TabHost.TabSpec tab4 = tabHost.newTabSpec("economicos");
	    tab4.setIndicator("Social");
	    tab4.setContent(new TabHost.TabContentFactory() {
	        public View createTabContent(String tag) {
	            ListView list_view_datos = new ListView(HistorialMedic.this);
	            SocioEconomicoAdapter socioEconomicoAdapter = new SocioEconomicoAdapter(HistorialMedic.this, R.layout.row_economicos_view, historial.Socioeconomicos);
	            TextView text_header = (TextView) inflater.inflate(R.layout.list_header, list_view_datos, false);
	            text_header.setText("Social");
				list_view_datos.addHeaderView(text_header);				
	            list_view_datos.setAdapter(socioEconomicoAdapter);
	            return list_view_datos;
	        }
	    });
		
		TabHost.TabSpec tab5 = tabHost.newTabSpec("hereditarias");
	    tab5.setIndicator("Familiar");
	    tab5.setContent(new TabHost.TabContentFactory() {
	        public View createTabContent(String tag) {
	            ListView list_view_datos = new ListView(HistorialMedic.this);
	            HereditariasAdapter hereditariasAdapter = new HereditariasAdapter(HistorialMedic.this, R.layout.row_hereditarias_view, historial.EnfermedadesHereditarias);
	            TextView text_header = (TextView) inflater.inflate(R.layout.list_header, list_view_datos, false);
	            text_header.setText("Familiar");
				list_view_datos.addHeaderView(text_header);				
	            list_view_datos.setAdapter(hereditariasAdapter);
	            return list_view_datos;
	        }
	    });
		
		TabHost.TabSpec tab6 = tabHost.newTabSpec("procedimientos");
	    tab6.setIndicator("Procedimiento");
	    tab6.setContent(new TabHost.TabContentFactory() {
	        public View createTabContent(String tag) {
	            ListView list_view_datos = new ListView(HistorialMedic.this);
	            DescriptionComentarioAdapter procedimientosAdapter = new DescriptionComentarioAdapter(HistorialMedic.this, R.layout.row_description_coment_view, historial.Procedimientos);
	            TextView text_header = (TextView) inflater.inflate(R.layout.list_header, list_view_datos, false);
	            text_header.setText("Procedimiento");
				list_view_datos.addHeaderView(text_header);				
	            list_view_datos.setAdapter(procedimientosAdapter);
	            return list_view_datos;
	        }
	    });
		
		TabHost.TabSpec tab7 = tabHost.newTabSpec("toxicos");
	    tab7.setIndicator("Toxico");
	    tab7.setContent(new TabHost.TabContentFactory() {
	        public View createTabContent(String tag) {
	            ListView list_view_datos = new ListView(HistorialMedic.this);
	            ToxicoAdapter toxicosAdapter = new ToxicoAdapter(HistorialMedic.this, R.layout.row_toxico_view, historial.Toxicos);
	            TextView text_header = (TextView) inflater.inflate(R.layout.list_header, list_view_datos, false);
	            text_header.setText("Toxico");
				list_view_datos.addHeaderView(text_header);				
	            list_view_datos.setAdapter(toxicosAdapter);
	            return list_view_datos;
	        }
	    });
		
		TabHost.TabSpec tab8 = tabHost.newTabSpec("traumaticos");
	    tab8.setIndicator("Traumas");
	    tab8.setContent(new TabHost.TabContentFactory() {
	        public View createTabContent(String tag) {
	            ListView list_view_datos = new ListView(HistorialMedic.this);
	            TraumasAdapter traumasAdapter = new TraumasAdapter(HistorialMedic.this, R.layout.row_traumaticos_view, historial.Traumaticos);
	            TextView text_header = (TextView) inflater.inflate(R.layout.list_header, list_view_datos, false);
	            text_header.setText("Traumas");
				list_view_datos.addHeaderView(text_header);				
	            list_view_datos.setAdapter(traumasAdapter);
	            return list_view_datos;
	        }
	    });
	    
	    tabHost.addTab(tab1);
	    tabHost.addTab(tab2);
	    tabHost.addTab(tab3);
	    tabHost.addTab(tab4);
	    tabHost.addTab(tab5);
	    tabHost.addTab(tab6);
	    tabHost.addTab(tab7);
	    tabHost.addTab(tab8);
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
				v=inflater.inflate(R.layout.row_alergia_view, parent, false);	
			}
	        AlergiaViewModel element = antecedentesAlergico.get(position);
			TextView alergia = (TextView) v.findViewById(R.id.alergia);
			TextView comentarios = (TextView) v.findViewById(R.id.comentarios);
			
			alergia.setText("Alergia: " + element.TipoDeAlergia + ", " + element.ElemmentoAlergico);
			comentarios.setText("Comentarios: " + element.Comentarios);
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
				v=inflater.inflate(R.layout.row_description_coment_view, parent, false);	
			}
	        DescripcionComentViewModel element = antecedentes.get(position);
	        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
	        TextView fecha = (TextView) v.findViewById(R.id.fecha);
	        TextView comentarios = (TextView) v.findViewById(R.id.comentarios);
	        
	        descripcion.setText("Descripción: " + element.Descripcion);
	        comentarios.setText("Comentarios: " + element.Comentarios);
	        fecha.setText("Fecha: " + element.GetShotDate());
			return v;	
		}	
	}
	
	public class SocioEconomicoAdapter extends ArrayAdapter<String> {
		 List<SocioEconomicoViewModel> economico;
		public SocioEconomicoAdapter(HistorialMedic context, int alergiaViewRow, List<SocioEconomicoViewModel> economico) {
			super(context, alergiaViewRow, new String[economico.size()]);
			this.economico = economico;
		}
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {			
			View v = convertView;
	        if(v==null){
				v=inflater.inflate(R.layout.row_economicos_view, parent, false);	
			}
	        SocioEconomicoViewModel element = economico.get(position);
	        
	        TextView fecha = (TextView) v.findViewById(R.id.fecha);	        
	        TextView notas = (TextView) v.findViewById(R.id.notas);	        
	        fecha.setText("Fecha: " + element.GetShotDate());
	        notas.setText("Descripción: " + element.Notas);
			return v;	
		}	
	}
	
	public class HereditariasAdapter extends ArrayAdapter<String> {
		 List<EnfermedadHereditariaViewModel> hereditaria;
		public HereditariasAdapter(HistorialMedic context, int alergiaViewRow, List<EnfermedadHereditariaViewModel> hereditaria) {
			super(context, alergiaViewRow, new String[hereditaria.size()]);
			this.hereditaria = hereditaria;
		}
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {			
			View v = convertView;
	        if(v==null){
				v=inflater.inflate(R.layout.row_hereditarias_view, parent, false);	
			}
	        EnfermedadHereditariaViewModel element = hereditaria.get(position);
	        
	        TextView enfermedadDescripcion = (TextView) v.findViewById(R.id.enfermedadDescripcion);	        
	        TextView familiar = (TextView) v.findViewById(R.id.familiar);	        
	        enfermedadDescripcion.setText("Descripcion: " + element.EnfermedadesDescripcion);
	        familiar.setText("Relacion: " + element.Familiar);
			return v;	
		}	
	}
	
	public class ToxicoAdapter extends ArrayAdapter<String> {
		 List<ToxicoViewModel> antecedentes;
		public ToxicoAdapter(HistorialMedic context, int alergiaViewRow, List<ToxicoViewModel> antecedentes) {
			super(context, alergiaViewRow, new String[antecedentes.size()]);
			this.antecedentes = antecedentes;
		}
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {			
			View v = convertView;
	        if(v==null){
				v=inflater.inflate(R.layout.row_toxico_view, parent, false);	
			}
	        ToxicoViewModel element = antecedentes.get(position);
	        
	        TextView feha = (TextView) v.findViewById(R.id.feha);
	        TextView toxicoDescripcion = (TextView) v.findViewById(R.id.toxicoDescripcion);
	        feha.setText("Fecha: " + element.GetShotDate());
	        toxicoDescripcion.setText("Descripción: " + element.ToxicosDescripcion);
			return v;	
		}	
	}
	
	public class TraumasAdapter extends ArrayAdapter<String> {
		 List<TraumaticosViewModel> antecedentes;
		public TraumasAdapter(HistorialMedic context, int alergiaViewRow, List<TraumaticosViewModel> antecedentes) {
			super(context, alergiaViewRow, new String[antecedentes.size()]);
			this.antecedentes = antecedentes;
		}
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {			
			View v = convertView;
	        if(v==null){
				v=inflater.inflate(R.layout.row_traumaticos_view, parent, false);	
			}
	        TraumaticosViewModel element = antecedentes.get(position);
	        
	        TextView fecha = (TextView) v.findViewById(R.id.fecha);
	        TextView descripcion = (TextView) v.findViewById(R.id.descripcion);
	        fecha.setText("Fecha: " + element.GetShotDate());
			descripcion.setText("Descripción: " + element.Descripción);
			return v;	
		}	
	}
	
	private String[] getDatosPersonales(Persona datosPersonales){
		String[] datos = new String[15];
		datos[0] = "Nombre: " + datosPersonales.Nombres + " " + datosPersonales.Apellidos;
		datos[1] = "Sexo: " + datosPersonales.Sexo;
		datos[2] = "Edad: " + datosPersonales.Edad;
		datos[3] = "Telefono: " + datosPersonales.TelefonoResidencial;
		datos[4] = "Email: " + datosPersonales.Email;
		datos[5] = "Cedula: " + datosPersonales.Cedula;
		datos[6] = "Direccion: " + datosPersonales.Direccion;
		datos[7] = "Tipo De Sangre: " + datosPersonales.TipoDeSangre;
		datos[8] = "Religion: " + datosPersonales.Religion;
		datos[9] = "Ocupacion: " + datosPersonales.Ocupacion;
		datos[10] = "Estado Civil: " + datosPersonales.EstadoCivil;
		datos[11] = "Nivel Educacion: " + datosPersonales.NivelEducacion;
		datos[12] = "Raza: " + datosPersonales.Raza;
		datos[13] = "Peso: " + datosPersonales.Peso;
		datos[14] = "Altura: " + datosPersonales.Altura;
		return datos;
	}
}
