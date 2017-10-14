/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Jacob
 */
public enum QuoteHandler {
    INSTANCE;

    public final boolean DEBUG = true;

    public String retreiveCurrentQuote(String... tickers) {
        if (tickers.length == 1) {
            for (String temp : tickers) {
                tickers = temp.split(",");
            }
        }
        String returnString = "QUOTEHANDLER: ";
        Map<String, QuoteList> quotes = retreiveCurrentQuote(true, tickers);
        QuoteList thisList = null;
        for (String key : quotes.keySet()) {
            thisList = quotes.get(key);
            returnString += "/n " + thisList.toString();
        }
        return returnString;
    }

    public Map<String, QuoteList> retreiveCurrentQuote(boolean test, String... tickers) {
        Map<String, QuoteList> quoteData;
        final XmlPage page;
        String URL = buildYQLReques(tickers);

        try (final WebClient webClient = new WebClient()) {
            page = webClient.getPage(URL);

            quoteData = parseYQSResponse(page.asXml());

            QuoteList quoteList;
            for (String key : quoteData.keySet()) {
                quoteList = quoteData.get(key);
                System.out.println(quoteList.toString());
            }

        } catch (Exception ex) {
            quoteData = null;
            Logger.getLogger(HtmlUnit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quoteData;
    }

    public Map<String, QuoteList> parseYQSResponse(String xml) throws Exception {
        Map<String, QuoteList> symbolMap = new HashMap<>();
        QuoteList quoteList;
        QuoteBean currentQuote;
        Element quoteElement;
        String sym, time;
        Double price;
        int vol;
        String percentChange;

        int countQuote = 0;
        NodeList nodes = selectNodesFromXML(xml, "quote");
        while (nodes.item(countQuote) != null) {

            /* Extract a single quote element from XML. */
            quoteElement = (Element) nodes.item(countQuote++);

            /* Create new QuoteBean from data in quote element. */
            sym = getStringDataFromTag(quoteElement, "Symbol");
            price = Double.parseDouble(getStringDataFromTag(quoteElement, "LastTradePriceOnly"));
            percentChange = getStringDataFromTag(quoteElement, "ChangeinPercent");
//            vol = Integer.parseInt(getStringDataFromTag(quoteElement, "ChangeinPercent"));
            currentQuote = new QuoteBean(sym, DatabaseConnector.INSTANCE.getFormattedTimestamp(), price, percentChange);

            /* Add new QuoteBean to the appropriate QuoteList. */
            quoteList = symbolMap.get(currentQuote.getSymbol());
            if (quoteList == null) {
                quoteList = new QuoteList(currentQuote.getSymbol());
                symbolMap.put(currentQuote.getSymbol(), quoteList);
            }
            quoteList.addQuote(currentQuote);
        }

        return symbolMap;
    }

    private NodeList selectNodesFromXML(String xml, String elementTag) throws Exception {
        DEBUG_PRINT("XML in: " + xml);
        NodeList nodes = null;
        InputSource inStream = new InputSource();
        inStream.setCharacterStream(new StringReader(xml));
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = dBuilder.parse(inStream);
        nodes = doc.getElementsByTagName(elementTag);
        return nodes;
    }

    private static String getStringDataFromTag(Element e, String tagName) {
        Element tagEl = (Element) e.getElementsByTagName(tagName).item(0);
        Node child = tagEl.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData().trim();
        }
        return "";
    }

    private String buildYQSRequest(String ticker) {
        String url = "";
        ticker = ticker.toUpperCase();
        url = "https://query.yahooapis.com/v1/public/yql?q="
                + "select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22" + ticker + "%22)"
                //                + "&diagnostics=true"
                + "&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        return url;
    }

    private String buildYQLReques(String... st) {
        String url = "";
        List<String> tickers = new ArrayList<String>(Arrays.asList(st));
        url += "https://query.yahooapis.com/v1/public/yql?q=";
        url += buildYQLQuery(tickers);
        url += "&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";;
        return url;
    }

    private String buildYQLQuery(List<String> tickers) {
        String query = "";
        query += "select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20";
        query += "(";
        for (String tick : tickers) {
            query += "%22";
            query += tick;
            query += "%22";
            query += "%2C";
        }
        //take off the last comma
        query = query.substring(0, query.length() - 3);
        query += ")";
        return query;
    }

    /**
     * Prints out input String if DEBUG flag is true;
     */
    private void DEBUG_PRINT(String toPrint) {
        toPrint = toPrint != null ? toPrint : "";
        if (DEBUG) {
            System.out.println("@DEBUG@: " + toPrint);
        }
    }

}
