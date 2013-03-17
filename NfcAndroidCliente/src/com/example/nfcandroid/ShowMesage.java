package com.example.nfcandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMesage {
	MedicsClientes proyect;
	protected String rfidId;
	protected String IpAdd;
	String LogginUser;
	Persona user;

	public ShowMesage(MedicsClientes proyect) {
		this.proyect = proyect;
	}

	public ShowMesage(MedicsClientes proyect, String IpAdd, String rfidId, final String LogginUser, final Persona user) {
		this.proyect = proyect;
		this.rfidId = rfidId;
		this.IpAdd = IpAdd;
		this.LogginUser = LogginUser;
		this.user = user;
	}

	public void Loging(final TextView status, final TextView doctorName, final Button agregarPariente, final Button agregarExamen, final Button editarPerfil, final Button desligarMedico) {
		new AlertDialog.Builder(proyect).setTitle("Enviar solicitud de ingreso?")
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						String URL = "/api/personas/" + rfidId;
						LogginUser = rfidId;
						new GetFromUrl(URL, IpAdd, doctorName, agregarPariente, agregarExamen, editarPerfil, desligarMedico, user).execute();
						status.setText("ID sent to: " + IpAdd);
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						status.setText("Esperando por una identificacion");
					}
				}).show();
	}

	public void AddPariente(final Toast toastPeticionExitosa) {
		new AlertDialog.Builder(proyect).setTitle("Desea registrar a esta persona como pariente?")
				.setPositiveButton("Si", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						String URL = "/api/personas/AddPariente?idPariente=" + LogginUser + "&idPersona=" + rfidId;
						new PostToUrl(URL, IpAdd, toastPeticionExitosa).execute();
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
					}
				}).show();
	}
}
