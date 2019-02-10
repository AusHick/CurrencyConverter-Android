package com.austinhickey.lis4331.currencyconverterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Currency;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Currency USD = Currency.getInstance("USD");
		Currency CAD = Currency.getInstance("CAD");
		Currency EUR = Currency.getInstance("EUR");
		Currency MXN = Currency.getInstance("MXN");

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
}
