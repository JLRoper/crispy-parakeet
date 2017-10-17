/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import java.io.StringReader;
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
public class HtmlUnit {

    public static void main(String arg[]) throws Exception {
        QuoteHandler.INSTANCE.retreiveCurrentQuote("MU");
    }

    public String buildYQSStockURL(String ticker) {
        String url = "";
        ticker = ticker.toUpperCase();
        url = "https://query.yahooapis.com/v1/public/yql?q="
                + "select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22" + ticker + "%22)"
                //                + "&diagnostics=true"
                + "&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        return url;
    }

    public String getFieldFromXML(String tagName, String xml) throws Exception {
        String returnString = "";

        InputSource inStream = new InputSource();
        inStream.setCharacterStream(new StringReader(xml));

        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = dBuilder.parse(inStream);

        NodeList nodes = doc.getElementsByTagName("quote");
        Element element = (Element) nodes.item(0);

        NodeList name = element.getElementsByTagName("Symbol");
        Element line = (Element) name.item(0);
        System.out.println("Symbol: " + getCharacterDataFromElement(line));
        name = element.getElementsByTagName("LastTradePriceOnly");
        line = (Element) name.item(0);
        System.out.println("LastTradePriceOnly: " + getCharacterDataFromElement(line));
        name = element.getElementsByTagName("Volume");
        line = (Element) name.item(0);
        System.out.println("Volume: " + getCharacterDataFromElement(line));

        return returnString;
    }

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData().trim();
        }
        return "";
    }

}
