package com.hitesh_sahu.retailapp;

import com.hitesh_sahu.retailapp.model.entities.Money;

import org.junit.Test;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class MoneyUnitTest {

    @Test
    public void amountShouldBeConvertedToCurrency(){
        Integer amount = new Random().nextInt()*1000;
        Money m = Money.toCurrency(new BigDecimal(amount));
        assertTrue(m.toString(Locale.getDefault()).contains(amount.toString()));
    }

}
