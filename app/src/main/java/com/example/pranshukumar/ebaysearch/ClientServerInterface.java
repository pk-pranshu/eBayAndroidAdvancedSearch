
/**
 * Created by pranshukumar on 4/19/15.
 */
package com.example.pranshukumar.ebaysearch;
import android.app.Activity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
/* this class helps to get data from server*/
public class ClientServerInterface {
    //input stream deals with bytes
    static InputStream is = null;
    static JSONObject jobj = null;
    static String json = "";
    //constructor
    public ClientServerInterface(){

    }
    //this method returns json object.
    public String makeHttpRequest(String url){
//http client helps to send and receive data
        DefaultHttpClient httpclient = new DefaultHttpClient();
//our request method is post
        HttpPost httppost = new HttpPost(url);
        try {
//get the response
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity httpentity = httpresponse.getEntity();
// get the content and store it into inputstream object.
            is = httpentity.getContent();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
//convert byte-stream to character-stream.
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while((line = reader.readLine())!=null){
                    sb.append(line+"\n");

                }
//close the input stream
                is.close();
                json = sb.toString();
                try {
                    jobj = new JSONObject(json);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
}