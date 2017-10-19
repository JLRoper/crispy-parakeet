/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
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
        Map<String, QuoteList> quotes
                = QuoteHandler.INSTANCE.retreiveCurrentQuote(true, tickers);
        QuoteList thisList = null;
        for (String key : quotes.keySet()) {
            thisList = quotes.get(key);
            returnString += "/n " + thisList.toString();
        }
        return returnString;
    }

    public Map<String, QuoteList> retreiveCurrentQuote(
            boolean test,
            String... tickers) {
        Map<String, QuoteList> quoteData;
        final XmlPage page;
        String URL = buildHttpURL(tickers);

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

    private String buildHttpURL(String... st) {
        String url = "";
        List<String> tickers = new ArrayList<>(Arrays.asList(st));
        url += "https://query.yahooapis.com/v1/public/yql?q=";
        url += buildYQLQuery(tickers);
        url += "&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";;
        return url;
    }

    private String buildYQLQuery(List<String> syms) {
        StringBuilder urlYQL = new StringBuilder();
        urlYQL.append("select * from yahoo.finance.quotes where symbol in ");
        urlYQL.append("(");
        syms.stream().forEach((sym) -> {
            urlYQL.append("%22").append(sym).append("%22,");
        });
        StringBuilder returnYQL = new StringBuilder(urlYQL.toString().substring(0, urlYQL.length() -1));
        returnYQL.append(");");
        return returnYQL.toString();
    }

    /**
     * All URLs must be encoded in this format before being sent.
     *
     * @param inputURL String form of URL to be encoded.
     * @return String Encoded form of inputURL. Input object is returned if
     * unable to encode.
     */
    protected String encodeURLString(
            String inputURL) {
        if (inputURL == null
                || inputURL.trim().isEmpty()) {
            return inputURL;
        }
        String outputURL = "";
        try {
            outputURL = java.net.URLEncoder.encode(inputURL, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QuoteHandler.class.getName()).
                    log(
                            Level.SEVERE,
                            "Unable to encode URL String...  "
                            + inputURL,
                            ex);
        }
        return outputURL;
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
