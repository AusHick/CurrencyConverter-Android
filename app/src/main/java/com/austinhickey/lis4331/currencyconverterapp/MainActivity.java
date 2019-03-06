package com.austinhickey.lis4331.currencyconverterapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		List<CurrencyContainer> currencyContainerList = new ArrayList<>(); //stoopid java

		RecyclerView currenciesList = findViewById(R.id.rvCurrencies);
		final CurrencyAdapter cAdapter = new CurrencyAdapter(currencyContainerList);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		currenciesList.setLayoutManager(llm);
		currenciesList.setItemAnimator(new DefaultItemAnimator());
		currenciesList.setAdapter(cAdapter);
		currenciesList.setHasFixedSize(true);

		try {
			JSONObject currencyData = new JSONObject(getIntent().getStringExtra("CurrencyJSON"));
			final JSONObject rates = currencyData.getJSONObject("rates");
			Iterator<String> it = rates.keys();

			Log.d("JSON Size", "Parsing " + rates.length() + " objects");

			while(it.hasNext())
			{
				String k = it.next();
				if(k.equals("USD")) //TODO: programatically get the base currency
					continue;
                currencyContainerList.add(new CurrencyContainer(k,rates.getDouble(k), new File(getCacheDir(),k.substring(0,2).toLowerCase())));
				Log.d(k,rates.getString(k));
			}

			cAdapter.notifyDataSetChanged();
		} catch(JSONException e) {
			Log.e("JSON Error","Failed to reparse JSON data! " + e.toString());
		}

		((EditText)findViewById(R.id.editUSD)).addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				Double temp = s.toString().isEmpty() ? 1.00 : Double.parseDouble(s.toString());
				Log.d("Change","New value is " + temp);
				cAdapter.updateBaseCurrency(temp);
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
