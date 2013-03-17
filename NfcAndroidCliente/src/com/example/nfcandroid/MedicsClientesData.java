package com.example.nfcandroid;

import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MedicsClientesData {
	public NfcAdapter mNfcAdapter;
	
	public TextView status;
	public TextView PacienteName;
	public TextView textStatusAddPariente;
	
	public Button agregarPariente;
	public Button agregarExamen;
	public Button editarPerfil;
	public Button desligarMedico;
	
	public boolean isConnected;
	public String IpAdd;
	public Persona user;
	public PendingIntent mNfcPendingIntent;
	public IntentFilter[] mNdefExchangeFilters;
	public Toast toastPeticionExitosa;

	public MedicsClientesData(boolean isConnected, String IpAdd, String rfidId, Persona user) {
		this.isConnected = isConnected;
		this.IpAdd = IpAdd;
		this.user = user;
		this.user.idPersona = rfidId;
	}
}