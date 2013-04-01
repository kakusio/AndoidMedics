package com.example.nfcandroid.Activities;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nfcandroid.EditableInformationPersona;
import com.example.nfcandroid.R;
import com.example.nfcandroid.Utility.EditProfilePutToUrl;

public class EditProfile extends CustomActivityClass {

	private ArrayList<EditableInformationPersona> editable_information;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);
		editable_information = EditableInformationPersona.GetEditableInformation();
		String[] arr = new String[editable_information.size()];
		for (int i = 0; i < editable_information.size(); i++) {
			arr[i] = editable_information.get(i).getName();  
			editable_information.get(i).setValue(getIntent().getExtras().getString(editable_information.get(i).getName()));
		}

		MyCustomAdapter listAdaper = new MyCustomAdapter(EditProfile.this, R.layout.edit_row, arr);
		ListView editListView = (ListView) findViewById(R.id.editListView);
		editListView.setAdapter(listAdaper);
		
		final Button buttonEnviarProfile = (Button) findViewById(R.id.buttonEnviarProfile);
		buttonEnviarProfile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {				
				String URL = "/api/personas/" + getIntent().getExtras().getString("idPersona");
				for (int i = 0; i < editable_information.size(); i++) {
					TextView edit = (TextView) findViewById(editable_information.get(i).getId());
					if (i == 0) URL += "?";
					else URL += "&";
					URL += editable_information.get(i).getName() + "=" + edit.getText();
				}					
				new EditProfilePutToUrl(URL, EditProfile.this).execute();
			}
		});		
	}
	
	public class MyCustomAdapter extends ArrayAdapter<String> {
		String[] arr;
		public MyCustomAdapter(Context context, int textViewResourceId,	String[] arr) {
			super(context, textViewResourceId, arr);
			this.arr =arr;
		}
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {			
			View row = convertView;
	        if(row==null){
				LayoutInflater inflater=getLayoutInflater();
				row=inflater.inflate(R.layout.edit_row, parent, false);	
			}
	        String name = editable_information.get(position).getPront();
	        String value = editable_information.get(position).getValue();
	        int id = editable_information.get(position).getId();
	        
	        TextView text=(TextView)row.findViewById(R.id.text);
	        EditText edit=(EditText)row.findViewById(R.id.edit);
	        if (edit == null) edit = (EditText)row.findViewById(id);
			
			text.setText(name);
			edit.setText(value);
			edit.setId(id);

			return row;	
		}	
	}
}

