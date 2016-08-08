
package com.example.expandablelistdemo.utils;

import android.util.Log;

public class Utils {
	
	public static void log(Object obj){
		if(obj == null){
			obj = "null";
		}
		Log.e("util", obj.toString());
	}
}
