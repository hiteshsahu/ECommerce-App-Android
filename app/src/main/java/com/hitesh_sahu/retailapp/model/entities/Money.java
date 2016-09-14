package com.hitesh_sahu.retailapp.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

public class Money {

	private static final Currency INR = Currency.getInstance(new Locale("en",
			"in"));
	private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

	private BigDecimal amount;
	private Currency currency;

	public static Money rupees(BigDecimal amount) {
		return new Money(amount, INR);
	}

	Money(BigDecimal amount, Currency currency) {
		this(amount, currency, DEFAULT_ROUNDING);
	}

	Money(BigDecimal amount, Currency currency, RoundingMode rounding) {
		this.amount = amount;
		this.currency = currency;

		this.amount = amount.setScale(currency.getDefaultFractionDigits(),
				rounding);
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return getCurrency().getSymbol() + " " + getAmount();
	}

	public String toString(Locale locale) {
		return getCurrency().getSymbol(locale) + " " + getAmount();
	}
}