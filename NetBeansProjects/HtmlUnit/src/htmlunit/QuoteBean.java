/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

/**
 *
 * @author Jacob
 */
public class QuoteBean implements Comparable<QuoteBean> {

    private final String symbol;
    private final String time;
    private final String changeInPct;
    private final double price;
    private final int volume;

    public QuoteBean(final String ticker, final String time, final double price, final String volume) {
        this.symbol = ticker;
        this.time = time;
        this.price = price;
        this.volume = 9;
        this.changeInPct = volume;
    }

    @Override
    public int compareTo(QuoteBean o) {
        return this.time.compareTo(o.getTime());

    }

    public String getSymbol() {
        return symbol;
    }

    public String getTime() {
        return time;
    }

    public double getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public String getChangeInPct() {
        return this.changeInPct;
    }

    @Override
    public String toString() {
        String str = "";
        str += getSymbol() + " - "
                + getPrice() + " - "
                + getChangeInPct() + " - "
                + getTime() + " - ";
        return str;
    }
}
