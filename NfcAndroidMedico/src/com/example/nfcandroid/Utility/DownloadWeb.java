package com.example.nfcandroid.Utility;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DownloadWeb {	
	private static final int HTTP_STATUS_OK = 200;

	public static class ApiException extends Exception {
		private static final long serialVersionUID = 1L;
		public ApiException (String msg){
			super (msg);
		}
		public ApiException (String msg, Throwable thr)	{
			super (msg, thr);
		}
	}

	public static synchronized String downloadFromServer (String... params)	throws ApiException	{
		String retval = null;
		String url = params[0]; 
	
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:19.0) Gecko/20100101 Firefox/19.0");
		try {
			HttpResponse response = client.execute(request);
			StatusLine status = response.getStatusLine();
			if (status.getStatusCode() != HTTP_STATUS_OK) throw new ApiException("Invalid response " + status.toString());

			HttpEntity entity = response.getEntity();
			retval = EntityUtils.toString(entity);
		} catch (Exception e) {	
			throw new ApiException("Problem connecting to the server " + e.getMessage(), e);
		}

		return retval;
	}
	
	public static Bitmap preLoadSrcBitmap(String image_URL){
		BitmapFactory.Options bmOptions;
		bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = 1;
		return LoadImage(image_URL, bmOptions);
	}
	
	private static Bitmap LoadImage(String URL, BitmapFactory.Options options)	{       
		Bitmap bitmap = null;
		InputStream in = null;       
		try {
			in = OpenHttpConnection(URL);
			bitmap = BitmapFactory.decodeStream(in, null, options);
			in.close();	
		} catch (IOException e1) {
		}
		
		return bitmap;               	
	}
	
	private static InputStream OpenHttpConnection(String strURL) throws IOException{
		InputStream inputStream = null;
		URL url = new URL(strURL);
		URLConnection conn = url.openConnection();
		
		try{
			HttpURLConnection httpConn = (HttpURLConnection)conn;
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = httpConn.getInputStream(); 	
			} 	
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
  
		return inputStream; 
	}
}


