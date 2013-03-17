package com.example.nfcandroid;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetFromUrl extends AsyncTask<Void, Void, String> {
	String URL;
	String IpAdd;
	TextView doctorName;
	Button agregarPariente;
	Button agregarExamen;
	Button editarPerfil;
	Button desligarMedico;
	StaticData static_data = new StaticData();
	Persona user;
	
	public GetFromUrl(String URL, String IpAdd, final TextView doctorName, final Button agregarPariente, final Button agregarExamen, final Button editarPerfil, final Button desligarMedico, final Persona user) {
		this.URL = URL;
		this.IpAdd = IpAdd;
		this.doctorName = doctorName;
		this.agregarPariente = agregarPariente;
		this.agregarExamen = agregarExamen;
		this.editarPerfil = editarPerfil;
		this.desligarMedico = desligarMedico;
		this.user = user;
	}

	protected String getASCIIContentFromEntity(HttpEntity entity)
			throws IllegalStateException, IOException {
		InputStream in = entity.getContent();
		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n > 0) {
			byte[] b = new byte[4096];
			n = in.read(b);
			if (n > 0)
				out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	@Override
	protected String doInBackground(Void... params) {
		HttpClient httpClient = new DefaultHttpClient();
		String encoded = URL.replaceAll("en", "");
		String encryptEncode = "";
		try {
			String encrypt = new Secure().encrypt(encoded, static_data.Password);
			encryptEncode = URLEncoder.encode(encrypt.toString(), "utf-8");
		} catch (Exception e) {
		}
		HttpGet httpGet = new HttpGet("http://" + IpAdd	+ ":4001/api/user?id=" + static_data.Id + "&url=" + encryptEncode);
		String text = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				text = getASCIIContentFromEntity(entity);
			}
		} catch (Exception e) {
			text = e.getMessage();
		}
		return text;
	}

	protected void onPostExecute(String results) {
		if (results != null) {
			JSONObject json;
			try {
				json = new JSONObject(results);
				user.ParseFromJson(json);
				agregarPariente.setVisibility(View.VISIBLE);
				agregarExamen.setVisibility(View.VISIBLE);
				editarPerfil.setVisibility(View.VISIBLE);
				desligarMedico.setVisibility(View.VISIBLE);
				doctorName.setText("Bienvenido " + user.Nombres);
			} catch (JSONException e) {
				doctorName.setText(results);
			}
		}
	}
}
