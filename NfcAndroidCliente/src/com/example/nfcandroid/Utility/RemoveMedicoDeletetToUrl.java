package com.example.nfcandroid.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

import com.example.nfcandroid.StaticData;
import com.example.nfcandroid.Activities.CustomActivityClass;

public class RemoveMedicoDeletetToUrl extends AsyncTask<Void, Void, String> {
	String URL;
	CustomActivityClass medics;
	public RemoveMedicoDeletetToUrl(String URL, final CustomActivityClass medics) {
		this.URL = URL;
		this.medics = medics;
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
			String encrypt = new Secure().encrypt(encoded, StaticData.Password);
			encryptEncode = URLEncoder.encode(encrypt.toString(), "utf-8");
		} catch (Exception e) {
		}
		HttpDelete httpDelete = new HttpDelete("http://" + StaticData.IpAdd + ":4001/api/user?id=" + StaticData.Id + "&url=" + encryptEncode);
		String text = null;
		try {
			HttpResponse response = httpClient.execute(httpDelete);
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
		if (results.contains("correctamente")) {
			medics.ToastPeticionExitosa();
		}
		else {
			medics.ToastPeticionFallo(results);
		}
	}
}
