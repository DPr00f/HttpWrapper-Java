/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.joaolopes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Pink Donut <joaolopes at pinkdonut.net>
 */
public class HttpWrapper {
    public static enum Action{
        GET, POST
    }
    
    public static enum Type{
        Normal, Json, Ajax, Json_Ajax
    }
    
    public String fetch(String url, String data, Type type, Action action) throws MalformedURLException, IOException{
        String resultLines = "";
        URL _url;
        URLConnection urlConnection;
        OutputStreamWriter outStream = null;
        BufferedReader inStream;
        String body = data;
        
        
        // Create connection
        if (action == Action.GET) {
            if(body.length() > 0){
                url += "?" + body;
            }
        }
        _url = new URL(url);
        urlConnection = _url.openConnection();
        if(action == Action.GET){
            ((HttpURLConnection)urlConnection).setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
        }
        if(action == Action.POST){
            ((HttpURLConnection)urlConnection).setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        }
        System.setProperty("http.agent", "");
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.100 Safari/534.30");
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        
        //conn.setRequestProperty("Host", "steamcommunity.com");
        //conn.setRequestProperty("Referer", "http://steamcommunity.com/trade/1");
        
        if(type == Type.Json || type == Type.Json_Ajax){
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        }
        if (type == Type.Ajax || type == Type.Json_Ajax) {
            urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            urlConnection.setRequestProperty("X-Prototype-Version", "1.7");
        }
        
        if(action == Action.POST){
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Length", ""+ body.length());
            outStream = new OutputStreamWriter(urlConnection.getOutputStream());
            outStream.write(body);
            outStream.flush();
        }
        
        
        inStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String buffer;
        while((buffer = inStream.readLine()) != null) {
            resultLines = resultLines.concat(buffer + "\n");
        }
        
        
       
        inStream.close();
        if(outStream != null){
            outStream.close();
        }
        
        String headerName=null;
        for (int i=1; (headerName = urlConnection.getHeaderFieldKey(i))!=null; i++) {
            if (headerName.equals("Set-Cookie")) {                  
                String cookie = urlConnection.getHeaderField(i);
                System.out.println(cookie);
            }
        }
        
        return resultLines;
    }
}
