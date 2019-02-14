package com.austinhickey.lis4331.currencyconverterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

		final Intent intent = new Intent(this, MainActivity.class);
		final TextView progressText = findViewById(R.id.textProgress);
		final String currencyURL = "https://api.exchangeratesapi.io/latest?base=USD";

		RequestQueue mRequestQueue = Volley.newRequestQueue(this);

		Log.d("JSON","Requesting from "+currencyURL);

		progressText.setText("Requesting current exchange rates...");

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currencyURL, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d("JSON",response.toString());
				progressText.setText("Preparing exchange rates...");

				intent.putExtra("CurrencyJSON", response.toString());
				startActivity(intent);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				progressText.setText("Error! Failed to obtain exchange rates...");
				Log.d("JSON","Failed to get JSON from " + currencyURL + " | " + error.toString());
			}
		});

		mRequestQueue.add(jsonObjectRequest);
	}
}
