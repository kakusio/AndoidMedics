package com.example.nfcandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;

public class ShowMesage {
	nfcAndroidDoctor proyect;
	protected String rfidId;
	protected String IpAdd;
	String LogginUser;

	public ShowMesage(nfcAndroidDoctor proyect) {
		this.proyect = proyect;
	}

	public ShowMesage(nfcAndroidDoctor proyect, String IpAdd, String rfidId, final String LogginUser) {
		this.proyect = proyect;
		this.rfidId = rfidId;
		this.IpAdd = IpAdd;
		this.LogginUser = LogginUser;
	}

	public void Loging(final TextView status, final TextView doctorName, final Button agregarPariente) {
		new AlertDialog.Builder(proyect).setTitle("Enviar NFC ID ?")
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						String URL = "/api/personas/" + rfidId;
						LogginUser = rfidId;
						new GetFromUrl(URL, IpAdd, doctorName, agregarPariente).execute();
						status.setText("ID sent to: " + IpAdd);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						status.setText("Esperando por un RFID");
					}
				}).show();
	}

	public void AddPariente(final TextView textStatusAddPariente) {
		new AlertDialog.Builder(proyect).setTitle("Desea registrar a esta persona como paciente?")
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						String URL = "/api/personas/AddPaciente?idMedico=" + LogginUser + "&idPaciente=" + rfidId;
						new PostToUrl(URL, LogginUser).execute();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				}).show();
	}
}
