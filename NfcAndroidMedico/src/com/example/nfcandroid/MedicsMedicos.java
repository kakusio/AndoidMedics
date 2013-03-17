package com.example.nfcandroid;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MedicsMedicos extends Activity {
	private static final String TAG = "stickynotes";
	NfcAdapter mNfcAdapter;
	TextView status;
	TextView doctorName;
	TextView IpAddressText;
	TextView textStatusAddPariente;

	Button agregarPariente;
	Button salirAddPariente;
	Button salirAddExamen;
	Button salirEditProfile;
	Button salirRemoveMedic;
	public String URL;
	public boolean isConnected = true;

	public String IpAdd = "";
	public String rfidId = "";
	public String LogginUser = "";

	PendingIntent mNfcPendingIntent;
	IntentFilter[] mNdefExchangeFilters;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proyect);

		IpAddressText = (TextView) findViewById(R.id.IpText);

		status = (TextView) findViewById(R.id.status);
		doctorName = (TextView) findViewById(R.id.doctorName);

		InicializarBotonesPrincipales();

		agregarPariente.setVisibility(View.INVISIBLE);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

		mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		IntentFilter ndefDetected = new IntentFilter(
				NfcAdapter.ACTION_NDEF_DISCOVERED);
		try {
			ndefDetected.addDataType("text/plain");
		} catch (MalformedMimeTypeException e) {
		}
		mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
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
			status.setText(new String(payload));
			setIntent(new Intent()); // Consume this intent.
		}
		enableNdefExchangeMode();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		mNfcAdapter.disableForegroundNdefPush(this);
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
		rfidId = new String(msg.getRecords()[0].getPayload());
		IpAdd = IpAddressText.getText().toString();
		if (LogginUser != "" && !LogginUser.equals(rfidId)){
			new ShowMesage(this,IpAdd,rfidId, LogginUser)
			.AddPariente();
		} else{			
			new ShowMesage(this,IpAdd,rfidId, LogginUser)
			.Loging( status,doctorName, agregarPariente);
			LogginUser = rfidId;
		}
	}

	private NdefMessage getNoteAsNdef() {
		byte[] textBytes = rfidId.getBytes();
		NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				"text/plain".getBytes(), new byte[] {}, textBytes);
		return new NdefMessage(new NdefRecord[] { textRecord });
	}

	NdefMessage[] getNdefMessages(Intent intent) {
		// Parse the intent
		NdefMessage[] msgs = null;
		String action = intent.getAction();
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
				|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			Parcelable[] rawMsgs = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
			} else {
				// Unknown tag type
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
						empty, empty, empty);
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
		mNfcAdapter.enableForegroundNdefPush(this, getNoteAsNdef());
		mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,
				mNdefExchangeFilters, null);
	}
	

	public void InicializarBotonesPrincipales() {
		((TextView) findViewById(R.id.IpText)).setText(IpAddressText.getText());
		((TextView) findViewById(R.id.status)).setText(status.getText());
		((TextView) findViewById(R.id.doctorName))
				.setText(doctorName.getText());

		agregarPariente = (Button) findViewById(R.id.buttonAgregarPariente);
		agregarPariente.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setContentView(R.layout.add_pariente);

				textStatusAddPariente = (TextView) findViewById(R.id.textStatusAddPariente);
				
				salirAddPariente = (Button) findViewById(R.id.salirAddPariente);
				salirAddPariente.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						setContentView(R.layout.activity_proyect);
						InicializarBotonesPrincipales();
					}
				});
			}
		});
	}
}
