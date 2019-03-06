package com.austinhickey.lis4331.currencyconverterapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
	private List<CurrencyContainer> currencyList;
	private Double baseCurrencyValue = 1.0; //this shouldnt be here really, but its 7am with no sleep

	public class ViewHolder extends RecyclerView.ViewHolder {
		public TextView txtCurrencyName;
		public TextView txtCurrencyAmt;
		public ImageView imgCurrencyFlag;

		public ViewHolder(View v) {
			super(v);
			txtCurrencyName = v.findViewById(R.id.textViewCurrency);
			txtCurrencyAmt = v.findViewById(R.id.textViewTotal);
			imgCurrencyFlag = v.findViewById(R.id.imageViewFlag);
		}
	}

	public CurrencyAdapter(List<CurrencyContainer> ccl) {
		this.currencyList = ccl;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int pos) {
		CurrencyContainer c = currencyList.get(pos);

		holder.txtCurrencyAmt.setText(c.getCurrency().getSymbol() + " " + new DecimalFormat("#,###.##").format(c.getValue() * baseCurrencyValue));
		holder.txtCurrencyName.setText(c.getCurrencyName());
		holder.imgCurrencyFlag.setImageBitmap(c.getBitmap());
		holder.imgCurrencyFlag.postInvalidate();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_currency_view, parent, false);
		return new ViewHolder(itemView);
	}

	@Override
	public int getItemCount() {
		return currencyList.size();
	}

	public void updateBaseCurrency(double b) {
		baseCurrencyValue = b;
		this.notifyDataSetChanged();
	}
}
