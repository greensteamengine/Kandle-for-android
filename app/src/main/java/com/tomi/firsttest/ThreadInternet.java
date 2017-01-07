package com.tomi.firsttest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

    String text = "nothing";
    HttpURLConnection http = null;
    InputStream is = null;
    String urlString = "";
    String nickUrl = "";
    int len = 2000;
    ArrayList<String> htmlCode =  new ArrayList<>();
    public ThreadInternet(int len) {
        this.len = len;
    }
    //ak nezadame dlzku, nacita sa cela stranka
    public ThreadInternet() {
    }

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
        Reader reader = null;
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
        return is;
    }

    public BufferedReader returnBuffer(){
        return new BufferedReader(new InputStreamReader(is));
    }

    /**
     * Connect to the server and download data, make string from it.
     *
     * Max length of sting is 2000 characters.
     */

    public static InputStream getHtml(String url) throws IOException {
        // Build and set timeout values for the request.
        URLConnection connection = (new URL(url)).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        // Read and store the result line by line then return the entire string.
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder html = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            html.append(line);
        }
        in.close();

        return in;
    }
    /*
    @Override
    public void run() {
        try {
            //  URL url = new URL(urlString);
           // URL url = new URL("https://candle.fmph.uniba.sk/ucitelia/Michal-Forisek");
            URLConnection connection = (new URL("https://candle.fmph.uniba.sk/ucitelia/Michal-Forisek")).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            // Read and store the result line by line then return the entire string.
             is = connection.getInputStream();
           // BufferedReader reader = new BufferedReader(new InputStreamReader(is));
           // StringBuilder html = new StringBuilder();
           // for (String line; (line = reader.readLine()) != null; ) {
           //     html.append(line);
           // }
            is.close();
            Log.v("code", "here i am");


        } catch (Throwable  e) {
            //  Log.w("Debug the problem", e.toString());
            Log.v("Debug message", "-"+e.toString()+"-");
            text = Integer.toString(-1);
        }
        Log.v("Thread", "finished?");
        // Thread.currentThread().interrupt();
    }


*/


    @Override
    public void run() {
        try {


          //  URL url = new URL(urlString);
            URL url = new URL("https://candle.fmph.uniba.sk/ucitelia/Michal-Forisek");

            Log.v("code", "at least it tried");

            // bezpecnost este prekonzultujeme.
            if (url.getProtocol().toLowerCase().equals("https")) {
                trustAllHosts();
                HttpsURLConnection https = (HttpsURLConnection) url
                        .openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                http = https;
               // https.disconnect();

                Log.v("code", "at least it tried and got here");

            } else {
                http = (HttpURLConnection) url.openConnection();
            }

            http.connect();
            is = http.getInputStream();
            InputStream is2 = http.getInputStream();
           // is = url.openStream();
            String line;
            BufferedReader r = new BufferedReader(new InputStreamReader(is2));
            while ((line=r.readLine()) != null) {
                htmlCode.add(line);
                Log.v("line", htmlCode.get(htmlCode.size()-1));
            }

            text = readIt(is, len);
            http.disconnect();

            Log.v("html", text);


        } catch (Throwable  e) {
          //  Log.w("Debug the problem", e.toString());
            Log.v("Debug message", "-"+e.toString()+"-");
            text = Integer.toString(-1);
        }
        Log.v("Thread", "finished?");
       // Thread.currentThread().interrupt();
    }

}

