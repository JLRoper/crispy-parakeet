/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import java.io.IOException;
import java.io.StringReader;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws Exception {
//        // TODO code application logic here
//        final XmlPage page;
//        String URL = new HtmlUnit().buildYQSStockURL("X");
//
//        try (final WebClient webClient = new WebClient()) {
//            page = webClient.getPage(URL);
////            System.out.println(page.getTitleText());
//            System.out.println(page.asXml());
//
//            new HtmlUnit().getFieldFromXML("Symbol", page.asXml());
//
//        } catch (IOException | FailingHttpStatusCodeException | ParserConfigurationException ex) {
//            Logger.getLogger(HtmlUnit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
    public static void main(String arg[]) throws Exception {

        QuoteBean bean1 = new QuoteBean("TST", "20170415223701", 12.34, 12456);
        QuoteBean bean2 = new QuoteBean("TST", "20170415223702", 12.24, 123456);
        QuoteBean bean3 = new QuoteBean("TST", "20170415223703", 12.26, 1234);
        QuoteBean bean4 = new QuoteBean("TST", "20170415223704", 12.30, 12356);

        QuoteList quotes = new QuoteList();
        quotes.add(bean3);
        quotes.add(bean2);
        quotes.add(bean4);
        quotes.add(bean1);
        System.out.println(quotes.toString());
        Comparator<QuoteBean> comp = new Comparator<QuoteBean>() {
            @Override
            public int compare(QuoteBean o1, QuoteBean o2) {
                return o1.compareTo(o2); //To change body of generated methods, choose Tools | Templates.
            }
        };
        quotes.sort(comp);

        System.out.println(quotes.toString());
//        QuoteHandler.INSTANCE.retreiveCurrentQuote("AMD");
////                TODO code application logic here
//        final XmlPage page;
//        String URL = new HtmlUnit().buildYQSStockURL("AMD");
//
//        try (final WebClient webClient = new WebClient()) {
//            page = webClient.getPage(URL);
//
//            System.out.println(page.asXml());
//
//            new HtmlUnit().getFieldFromXML("Symbol", page.asXml());
//
//        } catch (IOException | FailingHttpStatusCodeException | ParserConfigurationException ex) {
//            Logger.getLogger(HtmlUnit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String xmlRecords = "<data><employee><name>A</name>"
//                + "<title>Manager</title></employee></data>";
//        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//        InputSource is = new InputSource();
//        is.setCharacterStream(new StringReader(xmlRecords));
//
//        Document doc = db.parse(is);
//        NodeList nodes = doc.getElementsByTagName("employee");
//
//        for (int i = 0; i < nodes.getLength(); i++) {
//            Element element = (Element) nodes.item(i);
//
//            NodeList name = element.getElementsByTagName("name");
//            Element line = (Element) name.item(0);
//            System.out.println("Name: " + getCharacterDataFromElement(line));
//
//            NodeList title = element.getElementsByTagName("title");
//            line = (Element) title.item(0);
//            System.out.println("Title: " + getCharacterDataFromElement(line));
//        }
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
