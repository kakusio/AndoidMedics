package com.example.nfcandroid.Activities;

import android.app.Activity;
import android.widget.Toast;

public class CustomActivityClass extends Activity{

	public void ToastPeticionExitosa() {
		Toast.makeText(this, "Petici�n Exitosa", Toast.LENGTH_SHORT).show();
	}

	public void SetVisibleMain(String results) {
	}

	public void ToastPeticionFallo(String results) {
		Toast.makeText(this, "Petici�n Fallida: " + results, Toast.LENGTH_SHORT).show();
	}

}
