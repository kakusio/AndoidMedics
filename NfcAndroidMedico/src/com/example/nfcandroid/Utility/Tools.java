package com.example.nfcandroid.Utility;

import java.util.Date;

public class Tools {
	public static Date JsonDateToDate(String jsonDate){
	    int start = jsonDate.indexOf("(");
	    int end = jsonDate.indexOf(")");
	    String numeric_string = jsonDate.substring(start+1, end);
	    long long_date = Long.valueOf(numeric_string);
	    return new Date(long_date);
	}
}