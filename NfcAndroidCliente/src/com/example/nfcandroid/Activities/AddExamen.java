package com.example.nfcandroid.Activities;

import java.net.URLEncoder;
import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nfcandroid.Analisis;
import com.example.nfcandroid.R;
import com.example.nfcandroid.StaticData;
import com.example.nfcandroid.Utility.AddExamenOParientePostToUrl;
import com.example.nfcandroid.Utility.AsyncWebAddExamen;
import com.example.nfcandroid.Utility.Secure;

public class AddExamen extends CustomActivityClass {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_examen);
		
		final Button buttonEnviarMedidas = (Button) findViewById(R.id.buttonEnviarMedidas);
		buttonEnviarMedidas.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup); 
				int selected = radioGroup.getCheckedRadioButtonId();
				RadioButton editTipoMedida = (RadioButton) findViewById(selected);
				
				TextView editValorMedido = (TextView) findViewById(R.id.editValorMedido);
				String URL = "/api/examenes" 
						+ "?IdPersona=" + getIntent().getExtras().getString("idPersona") 
						+ "&idAnalisis=" + editTipoMedida.getContentDescription().toString() 
						+ "&ValorMedido=" + editValorMedido.getText().toString();
				new AddExamenOParientePostToUrl(URL, AddExamen.this).execute();
			}
		});		
		
		String Password = getIntent().getExtras().getString("Password");
		String Username = getIntent().getExtras().getString("Username");
		String URL = "/api/analisis";		
		String encoded = URL.replaceAll("en", "");
		String encryptEncode = "";
		try {
			String encrypt = new Secure().encrypt(encoded, Password);
			encryptEncode = URLEncoder.encode(encrypt.toString(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String agenda_url = "http://" + StaticData.IpAdd	+ ":4001/api/user?id=" + Username + "&url=" + encryptEncode;
		new AsyncWebAddExamen(AddExamen.this).execute(agenda_url);
	}
	
	public void SetRadioButtons(ArrayList<Analisis> analisis ) {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		for (int i = 0; i < analisis.size(); i++) {
			RadioButton radioButton =new RadioButton(this);
			radioButton.setText(analisis.get(i).Descripcion);
			radioButton.setContentDescription(analisis.get(i).idAnalisis);
			radioButton.setId(200 + i);
			radioGroup.addView(radioButton);           
		  }   
	}
}
