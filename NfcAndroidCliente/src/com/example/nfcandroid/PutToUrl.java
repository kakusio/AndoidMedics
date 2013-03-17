package com.example.nfcandroid;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.widget.Toast;

public class PutToUrl extends AsyncTask<Void, Void, String> {
	String URL;
	String IpAdd;
	StaticData static_data = new StaticData();
	Toast toastPeticionExitosa;
	public PutToUrl(String URL, String IpAdd, final Toast toastPeticionExitosa) {
		this.URL = URL;
		this.IpAdd = IpAdd;
		this.toastPeticionExitosa = toastPeticionExitosa;
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
		HttpPut httpPut = new HttpPut("http://" + IpAdd + ":4001/api/user?id=" + static_data.Id + "&url=" + encryptEncode);
		String text = null;
		try {
			HttpResponse response = httpClient.execute(httpPut);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				text = getASCIIContentFromEntity(entity);
			}
		} catch (Exception e) {
		}
		return text;
	}

	protected void onPostExecute(String results) {
		if (results.contains("correctamente")) {
			toastPeticionExitosa.show();
//			JSONObject json;
//			String text;
//			try {
//				json = new JSONObject(results);
//				text = json.get("Nombres").toString();
//			} catch (JSONException e) {
//			}
		}
	}
}
