package com.example.nfcandroid;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class PostToUrl extends AsyncTask<Void, Void, String> {
	String URL;
	String IpAdd;
	
	public PostToUrl(String URL, String IpAdd) {
		this.URL = URL;
		this.IpAdd = IpAdd;
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
			String encrypt = new Secure().encrypt(encoded, "password");
			encryptEncode = URLEncoder.encode(encrypt.toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
		} catch (Exception e) {
		}
		HttpPost httpPost = new HttpPost("http://" + IpAdd
				+ ":4001/api/user?url=" + encryptEncode);
		String text = null;
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				text = getASCIIContentFromEntity(entity);
			}
		} catch (Exception e) {
		}
		return text;
	}

	protected void onPostExecute(String results) {
		if (results != null) {
			JSONObject json;
			String text;
			try {
				json = new JSONObject(results);
				text = json.get("Nombres").toString();
			} catch (JSONException e) {
			}
			
//			status.setText(results);
//			agregarPariente.setVisibility(View.VISIBLE);
//			agregarExamen.setVisibility(View.VISIBLE);
//			editarPerfil.setVisibility(View.VISIBLE);
//			desligarMedico.setVisibility(View.VISIBLE);
		}
	}
}