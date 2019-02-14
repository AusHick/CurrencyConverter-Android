package com.austinhickey.lis4331.currencyconverterapp;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final JSONObject currencyData;
		try {
			currencyData = new JSONObject(getIntent().getStringExtra("CurrencyJSON"));
			final JSONObject rates = currencyData.getJSONObject("rates");
			Iterator<String> it = rates.keys();
            HashMap<Currency,Double> currencyRates = new HashMap<>();

			Log.d("JSON Size", "Parsing " + rates.length() + " objects");

			while(it.hasNext())
			{
				String k = it.next();
				if(k.equals("USD")) //TODO: programatically get the base currency
					continue;
                currencyRates.put(Currency.getInstance(k),rates.getDouble(k));
				Log.d(k,rates.getString(k));
			}
		} catch(JSONException e) {
			Log.e("JSON Error","Failed to reparse JSON data! " + e.toString());
		}

		RecyclerView currenciesList = findViewById(R.id.rvCurrencies);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		currenciesList.setLayoutManager(llm);
		currenciesList.setHasFixedSize(true);

		CurrencyAdapter cAdapter = new CurrencyAdapter

		((EditText)findViewById(R.id.editUSD)).addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}
/*
	public static Drawable GetFlagImage(String country) {
	    try {
	        Currency.getInstance(country);
            for(String c : Locale.getISOCountries())
                new Locale("",c); //TODO: Pull flag
            InputStream is = new URL("https://raw.githubusercontent.com/emcrisostomo/flags/master/png/256/" + country + ".png")
        }
    }
*/
}
