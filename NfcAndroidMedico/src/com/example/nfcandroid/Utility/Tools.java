package com.example.nfcandroid.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class Tools {
	public static Date JsonDateToDate(String jsonDate){
	    int start = jsonDate.indexOf("(");
	    int end = jsonDate.indexOf(")");
	    String numeric_string = jsonDate.substring(start+1, end);
	    long long_date = Long.valueOf(numeric_string);
	    return new Date(long_date);
	}
	
	public static String[] getStringListFromJsom( JSONArray jArray) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < jArray.length(); i++) {
			try {
				list.add(jArray.getString(i) );
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		String[] arr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
 		}
		
		return arr;
	}
}
