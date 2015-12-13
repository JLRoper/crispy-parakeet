/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlunit;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jacob
 */
public class HtmlUnit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("https://www.facebook.com");
            System.out.println(page.getTitleText());
        } catch (IOException ex) {
            Logger.getLogger(HtmlUnit.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FailingHttpStatusCodeException ex) {
            Logger.getLogger(HtmlUnit.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
