package com.tomi.Kandle;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Vicko on 11. 1. 2017.
 */

public class MyThread extends Thread {



    MyParser myParser;  //= new MyParser();

    BufferedReader bufferedReader;

    public MyThread(MyParser parser){
        this.myParser = parser;
    }



    private String urlString;

    private HttpsURLConnection http = null;
    private InputStream inputStream = null;

/*
//TODO have to use this to initialise data
    public void setData(String type, String name){
        String searchType;
        this.name = name;
        this.type = type;

        switch(type){

            case("ucitelia"): searchType = "showTeachers"; break;
            case("miestnosti"):searchType = "showRooms"; break;
            default: System.out.println("wrong type, can not add to parse HTML"); searchType = "";

        }

            urlString = "https://candle.fmph.uniba.sk/?"+searchType+"="+name;
    }
    */

    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };


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





    @Override
    public void run() {

        try {
            urlString = myParser.getUrlForFristHtml();
            URL urlString = new URL(this.urlString);

            trustAllHosts();

            http = (HttpsURLConnection) urlString
                    .openConnection();
            http.setHostnameVerifier(DO_NOT_VERIFY);
            http.connect();

            inputStream = http.getInputStream();


            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

          // ArrayList<String> lineOfHtml =  myParser.parsehtml(bufferedReader);
            myParser.parsehtml(bufferedReader);
            http.disconnect();

           // parser.returnParsedData(htmlCode);

            if(myParser.moreResults()){

               // myParser.returnPossibleChoises(lineOfHtml, type);

            }else{
               // String newUrlForTxt = ;

                urlString =  new URL(myParser.getUrlForTxt());
                http = (HttpsURLConnection) urlString
                        .openConnection();
                http.setHostnameVerifier(DO_NOT_VERIFY);
                http.connect();
                inputStream = http.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                myParser.parsetxt(bufferedReader);
            }

        } catch (Throwable  e) {
           // text = Integer.toString(-1);
        }
    }
}
