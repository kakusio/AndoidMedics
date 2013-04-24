package com.example.nfcandroid.Activities;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.nfcandroid.R;
import com.example.nfcandroid.Utility.RemoveMedicoDeletetToUrl;

public class RemoveMedicDetailsActivity extends CustomActivityClass {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.remove_medic_details);

		String Nombres = getIntent().getExtras().getString("Nombres");
		String Apellidos = getIntent().getExtras().getString("Apellidos");
		String id = getIntent().getExtras().getString("id");
		String idMedico = getIntent().getExtras().getString("idMedico");
		String[] InstitucionesNombre = getIntent().getExtras().getStringArray("InstitucionesNombre");
		String[] EspecialidadesDescripcion = getIntent().getExtras().getStringArray("EspecialidadesDescripcion");
				
		final String URL = "/api/personas/DeleteMedico?id=" + id + "&idMedico=" + idMedico;
		
		 TextView name=(TextView) findViewById(R.id.name);
	     TextView last_name=(TextView) findViewById(R.id.last_name);
	     TextView remove_medic=(TextView) findViewById(R.id.remove_medic);
	     TextView especialidadesTextView=(TextView) findViewById(R.id.especialidadesTextView);
	     TextView institucionesTextView=(TextView) findViewById(R.id.institucionesTextView);
//	     ImageView icon=(ImageView) findViewById(R.id.icon);
	     
	     name.setText(Nombres);
		 last_name.setText(Apellidos);	
	     
		 remove_medic.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				new RemoveMedicoDeletetToUrl(URL, RemoveMedicDetailsActivity.this).execute();
				
			}
		});
//		 icon.setImageBitmap((Bitmap) getIntent().getExtras().getParcelable("image_bitmap"));	
		 String especialidades = "Especialidades: ";
		 for (int i = 0; i < EspecialidadesDescripcion.length; i++) {
				especialidades += EspecialidadesDescripcion[i];
				if (i < EspecialidadesDescripcion.length) especialidades += ", ";
				else especialidades += ".";
		 } 
		 especialidadesTextView.setText(especialidades);
		 
		 String instituciones = "Instituciones: ";
		 for (int i = 0; i < InstitucionesNombre.length; i++) {
			 instituciones += InstitucionesNombre[i];
				if (i < InstitucionesNombre.length) instituciones += ", ";
				else instituciones += ".";
		 } 
		 institucionesTextView.setText(instituciones);
		 
	}

	public void openfile(String filePath) {
		String[] arr = filePath.split("\\.");
		String format = arr[arr.length-1];		
        File file = new File(filePath);
        if (file.exists()) {
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/" + format);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            try {
            	startActivity(intent);
            } 
            catch (Exception e) {
            	e.printStackTrace();
            }
        }
		
	}
}
