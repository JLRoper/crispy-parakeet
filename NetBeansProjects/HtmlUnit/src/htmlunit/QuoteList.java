/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jacob
 */
public class QuoteList extends AbstractList<QuoteBean> {

    String symbol;
    List<QuoteBean> list;

    public QuoteList(String symbol) {
        this.symbol = symbol;
        list = new ArrayList<>();
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean addQuote(QuoteBean quote) {
        return add(quote);
    }

    @Override
    public boolean add(QuoteBean bean) {
        if (symbol == null) {
            symbol = bean.getSymbol();
        } else if (!bean.getSymbol().equals(symbol)) {
            throw new RuntimeException("Tried to add quote to a quote list for a different company");
        }
        return list.add(bean);
    }

    @Override
    public QuoteBean get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public String toString() {
        String str = "";
        for (QuoteBean bean : list) {
            str += bean.toString() + "\n";
        }
        return str;
    }

    @Override
    public QuoteList clone() throws CloneNotSupportedException {
        return (QuoteList) super.clone();
    }

}
