package com.austinhickey.lis4331.currencyconverterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		final String currencyURL = "https://api.exchangeratesapi.io/latest?base=USD";

		RequestQueue mRequestQueue = Volley.newRequestQueue(this);

		Log.d("JSON","Requesting from "+currencyURL);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currencyURL, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d("JSON",response.toString());
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("JSON","Failed to get JSON from " + currencyURL);
			}
		});

		mRequestQueue.add(jsonObjectRequest);

		mRequestQueue.stop();
	}
}
