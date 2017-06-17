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
import org.xml.sax.SAXException;

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
        Map<String, QuoteList> symbolMap = new HashMap<>();
        QuoteList quoteList;
        QuoteBean currentQuote;
        Element quoteElement;
        String sym, time;
        Double price;
        int vol;

        int countQuote = 0;
        NodeList nodes = selectNodesFromXML(xml, "quote");
        while (nodes.item(countQuote) != null) {

            /* Extract a single quote element from XML. */
            quoteElement = (Element) nodes.item(countQuote++);

            /* Create new QuoteBean from data in quote element. */
            sym = getStringDataFromTag(quoteElement, "Symbol");
            price = Double.parseDouble(getStringDataFromTag(quoteElement, "LastTradePriceOnly"));
            vol = Integer.parseInt(getStringDataFromTag(quoteElement, "Volume"));
            currentQuote = new QuoteBean(sym, "", price, vol);

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
