/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import org.junit.Test;

/**
 *
 * @author Jacob
 */
public class I_QuoteHandlerTest {

    @Test
    public void test2() {
        double amount = 0.0;
        double addedAmount = 0.0;
        double fromPaycheck = 500.0;
        double dividen;
        for (int i = 1; i <= 12 * 16; i++) {
            addedAmount = fromPaycheck + (dividen = (amount * 0.014));
            amount = amount + addedAmount;
            System.out.println("@@@@@Iteration: " + i);
            System.out.println("Dividen value: " + dividen);
            System.out.println("Added value: " + addedAmount);
            System.out.println("Total value: " + amount);
        }
    }

    @Test
    public void retreiveCurrentQuote() {
        QuoteHandler.INSTANCE.retreiveCurrentQuote("AMD", "NVDA");
    }

    @Test
    public void builderURLTEST() {
        QuoteHandler.INSTANCE.encodeURLString("select * from testtable where symbol in ('AMD','NVDA')");
    }
}
