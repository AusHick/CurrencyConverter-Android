package com.austinhickey.lis4331.currencyconverterapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class SplashActivity extends AppCompatActivity {
	private TextView progressText;
	private RequestQueue mRequestQueue;
	private final String currencyURL = "https://api.exchangeratesapi.io/latest?base=USD";
	static int requestsPending = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		this.progressText = findViewById(R.id.textProgress);
		this.mRequestQueue = Volley.newRequestQueue(this);

		fetchExchangeRates();
	}

	private void fetchExchangeRates() {
		Log.d("JSON","Requesting from "+currencyURL);

		progressText.setText("Requesting current exchange rates...");

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, currencyURL, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				Log.d("JSON",response.toString());
				progressText.setText("Preparing exchange rates...");
				fetchFlagImages(response.toString());
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				progressText.setText("Error! Failed to obtain exchange rates...");
				Log.e("JSON","Failed to get JSON from " + currencyURL + " | " + error.toString());
			}
		});

		mRequestQueue.add(jsonObjectRequest);
	}

	private void fetchFlagImages(String jsonData) {
		final Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("CurrencyJSON",jsonData);

		progressText.setText("Fetching flag images...");

		try {
			JSONObject exchangeData = new JSONObject(jsonData);
			final Iterator<String> it = exchangeData.getJSONObject("rates").keys();

			while(it.hasNext())
			{
				String currencyCode = it.next();    //ISO 4217 Currency Code
				final String countryCode = currencyCode.substring(0,2);   //ISO 3166 Country Code (first two letters of Currency Code are the Country Code)

				ImageRequest flagImageRequest = new ImageRequest("https://github.com/hjnilsson/country-flags/raw/master/png100px/" + countryCode.toLowerCase() + ".png", new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						Log.d("Flags","Got Flag");
						try {
							File saveFile = new File(getBaseContext().getCacheDir(), countryCode.toLowerCase());
							if(saveFile == null) {
								Log.e("Flags", "File null");
							}
							FileOutputStream out = new FileOutputStream(saveFile);
							response.compress(Bitmap.CompressFormat.PNG,100,out);
							out.flush();
							out.close();
							Log.d("Flags","Saved " + countryCode + " to " + saveFile.getAbsolutePath());
							requestsPending--;
							if(requestsPending <= 0) {
								Log.d("Flags", "This is the last flag");
								startActivity(intent);
							}
						} catch(FileNotFoundException e) {
							Log.e("Flags", "Couldn't write flag file");
						} catch (IOException e) {
							Log.e("Flags","Failed to do something filey");
						}
					}
				}, 0, 0, null, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("Flags","Failed to get flag");
					}
				});

				requestsPending++;
				mRequestQueue.add(flagImageRequest);
			}
		} catch (JSONException e) {
			Log.e("JSON","Failed to read exchange rate data!");
		}
	}
}
