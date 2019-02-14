package com.austinhickey.lis4331.currencyconverterapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Currency;
import java.util.Locale;

public class CurrencyContainer {
	private Currency currency;
	private Double value;
	private Locale locale;
	private File image;

	public CurrencyContainer(String c, Double v, File f) {
		this.currency = Currency.getInstance(c);
		this.value = v;
		for(String cc : Locale.getISOCountries()) {
			if(this.currency.getCurrencyCode().contains(cc)) {
				this.locale = new Locale("",cc);
			}
		}
		this.image = f;
	}

	public Bitmap getBitmap() {
		try {
			FileInputStream in = new FileInputStream(new File(image.toURI()));
			return BitmapFactory.decodeStream(in);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public String getCurrencyName() {
		return currency.getDisplayName();
	}

	public Currency getCurrency() {
		return currency;
	}

	public String getCountryISO() {
		return locale.getCountry();
	}

	public Double getValue() {
		return value;
	}

	public Double exchange(Double b) {
		return b * value;
	}
}
