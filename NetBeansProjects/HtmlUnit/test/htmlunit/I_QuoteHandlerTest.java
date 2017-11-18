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

    {
//System.setProperty("javax.net.ssl.trustStore", "C:/.keystore");
//System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//        System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
//        System.setProperty("javax.net.ssl.keyStore", "clientkeystore.jks");
//        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
    }

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
        try {
            I_QuoteHandlerTest.class.getResource("clientkeystore.jks").getFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String trustStore = System.getProperty("javax.net.ssl.keyStore");

        String storeLoc;
        storeLoc = System.getProperty("java.class.path");
        System.out.println("classpath: " + storeLoc);
        QuoteHandler.INSTANCE.retreiveCurrentQuote("AMD", "NVDA");
    }

    @Test
    public void builderURLTEST() {
        QuoteHandler.INSTANCE.encodeURLString("select * from testtable where symbol in ('AMD','NVDA')");
    }
}
