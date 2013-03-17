package com.example.nfcandroid;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class MedicsClientes extends Activity {
	private static final String TAG = "stickynotes";
	public MedicsClientesData data = new MedicsClientesData(true, "10.0.0.10", "c06c7e2f-1805-4257-a0dc-c8e4f175ae71", new Persona());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proyect);
		data.toastPeticionExitosa =  Toast.makeText(this, "Petici�n Exitosa", Toast.LENGTH_SHORT);
		data.status = (TextView) findViewById(R.id.status);
		data.PacienteName = (TextView) findViewById(R.id.doctorName);

		InicializarBotonesPrincipales();

		data.mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		data.mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		IntentFilter ndefDetected = new IntentFilter( NfcAdapter.ACTION_NDEF_DISCOVERED);
		try {
			ndefDetected.addDataType("text/plain");
		} catch (MalformedMimeTypeException e) {
		}
		data.mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
		
		//TODO: comentar
		new ShowMesage(this,data.IpAdd,data.user.idPersona, data.user.idPersona, data.user)
		.Loging( data.status,data.PacienteName, data.agregarPariente, data.agregarExamen, data.editarPerfil, data.desligarMedico );
		data.user.idPersona = data.user.idPersona;
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_proyect, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Sticky notes received from Android
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			NdefMessage[] messages = getNdefMessages(getIntent());
			byte[] payload = messages[0].getRecords()[0].getPayload();
			data.status.setText(new String(payload));
			setIntent(new Intent()); // Consume this intent.
		}
		enableNdefExchangeMode();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		data.mNfcAdapter.disableForegroundNdefPush(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// NDEF exchange mode
		if ( NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
			NdefMessage[] msgs = getNdefMessages(intent);
			promptForContent(msgs[0]);
		}
	}

	private void promptForContent(final NdefMessage msg) {
		data.user.idPersona = new String(msg.getRecords()[0].getPayload());
		if (data.user.idPersona != "" && !data.user.idPersona.equals(data.user.idPersona)){
			new ShowMesage(this,data.IpAdd,data.user.idPersona, data.user.idPersona, data.user)
				.AddPariente(data.toastPeticionExitosa);
		} else{			
			new ShowMesage(this,data.IpAdd,data.user.idPersona, data.user.idPersona, data.user)
				.Loging( data.status,data.PacienteName, data.agregarPariente, data.agregarExamen, data.editarPerfil, data.desligarMedico );
			data.user.idPersona = data.user.idPersona;
		}
	}

	private NdefMessage getNoteAsNdef() {
		byte[] textBytes = data.user.idPersona.getBytes();
		NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA, "text/plain".getBytes(), new byte[] {}, textBytes);
		return new NdefMessage(new NdefRecord[] { textRecord });
	}

	NdefMessage[] getNdefMessages(Intent intent) {
		// Parse the intent
		NdefMessage[] msgs = null;
		String action = intent.getAction();
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)	|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			} else {
				// Unknown tag type
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
				NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
				msgs = new NdefMessage[] { msg };
			}
		} else {
			Log.d(TAG, "Unknown intent.");
			finish();
		}
		return msgs;
	}

	@SuppressWarnings("deprecation")
	private void enableNdefExchangeMode() {
		data.mNfcAdapter.enableForegroundNdefPush(this, getNoteAsNdef());
		data.mNfcAdapter.enableForegroundDispatch(this, data.mNfcPendingIntent, data.mNdefExchangeFilters, null);
	}
	

	public void InicializarBotonesPrincipales() {
		((TextView) findViewById(R.id.status)).setText(data.status.getText());
		((TextView) findViewById(R.id.doctorName)).setText(data.PacienteName.getText());

		InicializarAgregarPariente();
		InicializarAgregarExamen();
		InicializarEditarPerfil();
		InicializarDesligarMedico();
	}
	
	public void InicializarAgregarExamen() {
		data.agregarExamen = (Button) findViewById(R.id.buttonAgregarExamen);
		data.agregarExamen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setContentView(R.layout.add_examen);
	
				final Button salirAddExamen = (Button) findViewById(R.id.salirAddExamen);
				salirAddExamen.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						VisibleMain();
					}
				});
				
				final Button buttonEnviarMedidas = (Button) findViewById(R.id.buttonEnviarMedidas);
				buttonEnviarMedidas.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						TextView editTipoMedida = (TextView) findViewById(R.id.editTipoMedida);
						TextView editValorMedido = (TextView) findViewById(R.id.editValorMedido);
						String URL = "/api/examenes" 
								+ "?IdPersona=" + data.user.idPersona 
								+ "&idAnalisis=" + editTipoMedida.getText().toString() 
								+ "&ValorMedido=" + editValorMedido.getText().toString();
						new PostToUrl(URL, data.IpAdd, data.toastPeticionExitosa).execute();
					}
				});
			}
		});		
	}

	public void InicializarAgregarPariente() {
		data.agregarPariente = (Button) findViewById(R.id.buttonAgregarPariente);
		data.agregarPariente.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setContentView(R.layout.add_pariente);

				data.textStatusAddPariente = (TextView) findViewById(R.id.textStatusAddPariente);				
				final Button salirAddPariente = (Button) findViewById(R.id.salirAddPariente);
				salirAddPariente.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						VisibleMain();
					}
				});
			}
		});
	}
	
	public void InicializarEditarPerfil() {
		data.editarPerfil = (Button) findViewById(R.id.buttonEditarPerfil);
		data.editarPerfil.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setContentView(R.layout.edit_profile);
				final TextView editEmailAddress = (TextView) findViewById(R.id.editEmailAddress);
				final TextView editTelefonoCasa = (TextView) findViewById(R.id.editTelefonoCasa);
				final TextView editTelefonoCelular = (TextView) findViewById(R.id.editTelefonoCelular);
				final TextView editDireccion = (TextView) findViewById(R.id.editDireccion);
				
				editEmailAddress.setText(data.user.Email);
				editTelefonoCasa.setText(data.user.TelefonoResidencial);
				editTelefonoCelular.setText(data.user.TelefonoCelular);
				editDireccion.setText(data.user.Direccion);
				
				final Button salirEditProfile = (Button) findViewById(R.id.salirEditProfile);
				salirEditProfile.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						VisibleMain();
					}
				});
				final Button buttonEnviarProfile = (Button) findViewById(R.id.buttonEnviarProfile);
				buttonEnviarProfile.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						data.user.Email = editEmailAddress.getText().toString();
						data.user.TelefonoResidencial = editTelefonoCasa.getText().toString();
						data.user.TelefonoCelular = editTelefonoCelular.getText().toString();
						data.user.Direccion = editDireccion.getText().toString();
						
						String URL = "/api/personas/" + data.user.idPersona
							+  "?Email=" + data.user.Email 
							+ "&TelefonoResidencial=" + data.user.TelefonoResidencial
							+ "&TelefonoCelular=" + data.user.TelefonoCelular
							+ "&Direccion=" + data.user.Direccion;
							
						new PutToUrl(URL, data.IpAdd, data.toastPeticionExitosa).execute();
					}
				});
				
			}
		});
	}
	
	public void InicializarDesligarMedico() {
		data.desligarMedico = (Button) findViewById(R.id.buttonDeligarMedico);
		data.desligarMedico.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setContentView(R.layout.remove_medic);

				final ExpandableListView expandableListViewMedicos = (ExpandableListView) findViewById(R.id.expandableListViewMedicos);
				final Button salirRemoveMedic = (Button) findViewById(R.id.salirRemoveMedic);
				salirRemoveMedic.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						VisibleMain();
					}
				});
			}
		});
	}
	
	public void VisibleMain() {
		setContentView(R.layout.activity_proyect);
		InicializarBotonesPrincipales();
		data.agregarPariente.setVisibility(View.VISIBLE);
		data.agregarExamen.setVisibility(View.VISIBLE);
		data.editarPerfil.setVisibility(View.VISIBLE);
		data.desligarMedico.setVisibility(View.VISIBLE);
		
	}
}
