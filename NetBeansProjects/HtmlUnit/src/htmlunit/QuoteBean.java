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
    
        private final String ticker;
        private final String time;
        private final double price;
        private final int volume;

        public QuoteBean(final String ticker, final String time, final double price, final int volume) {
            this.ticker = ticker;
            this.time = time;
            this.price = price;
            this.volume = volume;
        }

        @Override
        public int compareTo(QuoteBean o) {
            return this.time.compareTo(o.getTime());

        }

        public String getTicker() {
            return ticker;
        }

        public String getTime() {
            return time;
        }

        public double getPrice() {
            return price;
        }

        public int getVolumne() {
            return volume;
        }

}
