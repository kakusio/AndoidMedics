package com.example.nfcandroid;

import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MedicsClientesData {
	public NfcAdapter mNfcAdapter;
	
	public TextView status;
	public TextView PacienteName;
	
	public Button agregarPaciente;
	public Button editarPerfil;
	public Button buttonPacientesMedico;
	
	public boolean isConnected;
	public Persona user;
	public PendingIntent mNfcPendingIntent;
	public IntentFilter[] mNdefExchangeFilters;

	public MedicsClientesData(boolean isConnected, String rfidId, Persona user) {
		this.isConnected = isConnected;
		this.user = user;
		this.user.idPersona = rfidId;
	}
}