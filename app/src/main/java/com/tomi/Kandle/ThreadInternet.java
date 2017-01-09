package com.tomi.Kandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import android.util.Log;

/**
 * Trieda,ktorá sťahuje dáta z internetu podľa nicku, volaná zvyčajne
 * konštruktorom DataStorageInternet.
 */

//TODO check all and remake!!!!!!!!!!!!!!!!!!!!!!!!!!

public class ThreadInternet extends Thread {

    private String text = "nothing";
    private String urlString = "";

    private HttpURLConnection http = null;
    private InputStream inputStream = null;

    private int len = 2000;
    private ArrayList<String> htmlCode =  new ArrayList<>();


    public void urlSet(String urlString){
        this.urlString = urlString;
    }

    // always verify the host - dont check for certificate
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * Trust every server - dont check for any certificate
     */
    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changing InputStream to string.
     */
    public String readIt(InputStream stream, int len) throws IOException,
            UnsupportedEncodingException {
        Reader reader;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    /**
     * Return string with data.
     */


    public ArrayList<String> getDataFromInternet() {
        return htmlCode;
    }

    public InputStream getIS(){
        return inputStream;
    }


    @Override
    public void run() {
        try {


            URL urlString = new URL(this.urlString);

          //  Log.v("code", "at least it tried");

            if (urlString.getProtocol().toLowerCase().equals("https")) {
                trustAllHosts();
                HttpsURLConnection https = (HttpsURLConnection) urlString
                        .openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                http = https;

                Log.v("code", "runing thread");

            } else {
                http = (HttpURLConnection) urlString.openConnection();
            }

            http.connect();
            inputStream = http.getInputStream();
            InputStream inputstream2 = http.getInputStream();
            String line;
            BufferedReader r = new BufferedReader(new InputStreamReader(inputstream2));
            while ((line=r.readLine()) != null) {
                htmlCode.add(line);
            }

            text = readIt(inputStream, len);
            http.disconnect();

        } catch (Throwable  e) {
           // Log.v("Debug message", "-"+e.toString()+"-");
            text = Integer.toString(-1);
        }
       // Log.v("Thread", "finished?");
    }
}

