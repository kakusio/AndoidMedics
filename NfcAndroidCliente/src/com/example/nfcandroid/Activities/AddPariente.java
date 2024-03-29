package com.example.nfcandroid.Activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.nfcandroid.R;
import com.example.nfcandroid.Utility.ShowMesage;

public class AddPariente extends CustomActivityClass {

	private NfcAdapter mNfcAdapter;
	private PendingIntent mNfcPendingIntent;
	private IntentFilter[] mNdefExchangeFilters;
	private String LogginUser;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogginUser = getIntent().getExtras().getString("idPersona");
		setContentView(R.layout.add_pariente);
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		IntentFilter ndefDetected = new IntentFilter( NfcAdapter.ACTION_NDEF_DISCOVERED);
		try {
			ndefDetected.addDataType("text/plain");
		} catch (MalformedMimeTypeException e) {
		}
		mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
		

        initItemFilter();  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_proyect, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
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
		String idPersona = new String(msg.getRecords()[0].getPayload());
		String familiar = ((AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1)).getText().toString();
		new ShowMesage(this ,idPersona, LogginUser)
				.AddPariente(this, familiar);

	}

	private NdefMessage getNoteAsNdef() {
		byte[] textBytes = LogginUser.getBytes();
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
			finish();
		}
		return msgs;
	}

	@SuppressWarnings("deprecation")
	private void enableNdefExchangeMode() {
		mNfcAdapter.enableForegroundNdefPush(this, getNoteAsNdef());
		mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
	}
	
    // initialize AutocompleteTextView
 private void initItemFilter()
    {
        AutoCompleteTextView item_filter = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        ArrayAdapter<String> mCursorAdapter = new ArrayAdapter<String>(this,R.layout.row_simple_view, arr);
        item_filter.setAdapter(mCursorAdapter);
        item_filter.setThreshold(1);
    }   
 
 public String[] arr = {"Padre", "Madre", "Hijo", "Hija", "Abuelo", "Abuela","Nieto", "Nieta", 
		 "Primo", "Prima", "Tio", "Tia", "Sobrino", "Sobrina", "Hermano", "Hermana", "Pariente lejano"};

}