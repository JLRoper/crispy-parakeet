/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import java.io.StringReader;
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

    public void retreiveCurrentQuote(String... tickers) {
        final XmlPage page;
        String URL = buildYQSRequest(tickers[0]);

        try (final WebClient webClient = new WebClient()) {
            page = webClient.getPage(URL);

            Map<String, QuoteList> quoteData = parseYQSResponse(page.asXml());

        } catch (Exception ex) {
            Logger.getLogger(HtmlUnit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, QuoteList> parseYQSResponse(String xml) throws Exception {
        DEBUG_PRINT(xml);

        InputSource inStream = new InputSource();
        inStream.setCharacterStream(new StringReader(xml));

        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = dBuilder.parse(inStream);

        NodeList nodes = doc.getElementsByTagName("quote");
        Map<String, QuoteList> symbolMap = new HashMap<>();
        QuoteList quoteList = new QuoteList();

        String sym, time;
        String prevSym = "";
        Double price;
        int vol;
        Element quoteElement;
        int countQuote = 0;
        while (nodes.item(countQuote) != null) {
            quoteElement = (Element) nodes.item(countQuote);

            sym = getStringDataFromTag(quoteElement, "Symbol");
            if(!prevSym.equals(sym)){
                quoteList = new QuoteList();
            }
                
            
            price = Double.parseDouble(getStringDataFromTag(quoteElement, "LastTradePriceOnly"));
            vol = Integer.parseInt(getStringDataFromTag(quoteElement, "LastTradePriceOnly"));

            quoteList.add(new QuoteBean("", "", price, vol));

            countQuote++;
        }

        return symbolMap;
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

    private static String getStringDataFromSingleElement(Element e) {
        Node child = e.getFirstChild();
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
                + "select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22" + ticker + "%22)"
                //                + "&diagnostics=true"
                + "&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        return url;
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
