package com.example.nfcandroid.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nfcandroid.R;
import com.example.nfcandroid.Utility.EditProfilePutToUrl;

public class EditProfile extends CustomActivityClass {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);
        final EditText editEmail=(EditText) findViewById(R.id.editEmail);
        final EditText editTelefono=(EditText) findViewById(R.id.editTelefono);
        final EditText editCelular=(EditText) findViewById(R.id.editCelular);
        final EditText editDireccion=(EditText) findViewById(R.id.editDireccion);
        
        editEmail.setText(getIntent().getExtras().getString("Email"));
        editTelefono.setText(getIntent().getExtras().getString("TelefonoResidencial"));
        editCelular.setText(getIntent().getExtras().getString("TelefonoCelular"));
        editDireccion.setText(getIntent().getExtras().getString("Direccion"));
		final Button buttonEnviarProfile = (Button) findViewById(R.id.buttonEnviarProfile);
		buttonEnviarProfile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {				
				String URL = "/api/personas/" + getIntent().getExtras().getString("idPersona");
				URL += "?" + "Email" + "=" + editEmail.getText();
				URL += "&" + "TelefonoResidencial" + "=" + editTelefono.getText();
				URL += "&" + "TelefonoCelular" + "=" + editCelular.getText();
				URL += "&" + "Direccion" + "=" + editDireccion.getText();
				new EditProfilePutToUrl(URL, EditProfile.this).execute();
			}
		});		
	}
}

