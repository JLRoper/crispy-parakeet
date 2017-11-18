package htmlunit;

import java.net.URL;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.security.KeyStore;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public enum HttpsClient {
    ALPHA_VANTAGE(
            HttpsClient.JDK_1_8_92__CLIENTKEYSTORE,
            "https://www.alphavantage.co/"),
    ROBINHOOD(
            HttpsClient.JDK_1_8_144__CACERTS,
            "https://api.robinhood.com/");

    //Set these in properties file.
    public static final String JDK_1_8_92__CLIENTKEYSTORE 
            = "C:\\Program Files\\Java\\jdk1.8.0_92\\jre\\bin\\clientkeystore";
    public static final String JDK_1_8_144__CACERTS 
            = "C:\\Program Files (x86)\\Java\\jre1.8.0_144\\lib\\security\\cacerts";
    private static JSONParser jsonParser = new JSONParser();
    private Path truststorePath;
    private URL baseURL;
    private SSLSocketFactory SSL_SOCKET_FACTORY;

    private HttpsClient(String trustCert, String baseURL) {
        setTrustStore(trustCert);
        setBaseURL(baseURL);
        initializeSocketFactory();
    }

    private void setTrustStore(String trustCert) {
        this.truststorePath = Paths.get(trustCert);
    }

    private void setBaseURL(String baseURL) {
        try {
            this.baseURL = new URL(baseURL);
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpsClient.class.getName()).log(Level.SEVERE, null, ex);
            this.baseURL = null;
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            String rawText = ROBINHOOD.submitHttpsRequestGetRaw("quotes/NVDA/");
            JSONObject data = (JSONObject) jsonParser.parse(rawText);
            System.out.println(data.toJSONString());
        } catch (Exception ex) {
            Logger.getLogger(HttpsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String submitHttpsRequestGetRaw(String reqURL) throws Exception {
        return submitHttpsRequest(reqURL).toJSONString();
    }

    public JSONObject submitHttpsRequest(String reqURL) throws Exception {
        String responseString = "";
        try {
            URL toSend = new URL(this.baseURL.toString() + reqURL);

            HttpsURLConnection con = (HttpsURLConnection) toSend.openConnection();

            con.setSSLSocketFactory(SSL_SOCKET_FACTORY);

            responseString = getContentAsString(con);

            con.disconnect();
        } catch (Exception e) {
            throw new Exception("Error submitting HTTPS Request ", e);
        }
        return (JSONObject) jsonParser.parse(responseString);
    }

    private String getContentAsString(HttpsURLConnection con) {
        StringBuilder responseContent = new StringBuilder();
        if (con != null) {
            try {
                System.out.println(con.getResponseCode());
                System.out.println(con.getResponseMessage());
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String input;
                while ((input = br.readLine()) != null) {
                    responseContent.append(input);
                    System.out.println(input);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent.toString();
    }

    private void initializeSocketFactory() {
        try {
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(new FileInputStream(truststorePath.toFile()), "changeit".toCharArray());
            TrustManagerFactory TRUST_MANGER = TrustManagerFactory.getInstance("SunX509");
            TRUST_MANGER.init(trustStore);
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, TRUST_MANGER.getTrustManagers(), null);
            SSL_SOCKET_FACTORY = sc.getSocketFactory();
        } catch (Throwable x) {
            Logger.getLogger(HttpsClient.class.getName()).log(Level.SEVERE, null, x);
        }
    }
}
