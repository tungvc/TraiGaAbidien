package com.abidien;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;

public class Main {

    public static void main(String[] args) {
        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost("https://m.facebook.com/");
        setDefaultCookieHeaders(httpPost);
        HttpResponse oHttpResponse = null;
        try {
            oHttpResponse = httpClient.execute(httpPost);

            String theString = IOUtils.toString(oHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            //System.out.println(theString);
            HashMap<String, String> sCookieValue = getCookieString(oHttpResponse);
            //System.out.println(sCookieValue);
            //System.out.println("fb_dtsg=" + getDTSG(theString));

            // login
            httpPost = new HttpPost("https://m.facebook.com/login/async/?refsrc=https%3A%2F%2Fm.facebook.com%2Flogin.php&lwv=100");
            setDefaultCookieHeaders(httpPost);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", "100026873742388"));
            nameValuePairs.add(new BasicNameValuePair("pass", "codaumahoi"));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            oHttpResponse = httpClient.execute(httpPost);

            String sResponse = IOUtils.toString(oHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            //System.out.println(sResponse);

            sCookieValue.putAll(getCookieString(oHttpResponse));
            System.out.println(sCookieValue);
            System.out.println();
            String sCookieToSubmit = mapToString(sCookieValue);
            System.out.println(sCookieToSubmit);
            System.out.println();

            //System.out.println("fb_dtsg=" + getDTSG(sResponse));

            // Get dtsg
            HttpGet httpGet = new HttpGet("https://facebook.com/");
            setDefaultCookieHeaders(httpGet);
            httpGet.addHeader("Cookie", sCookieToSubmit);

            oHttpResponse = httpClient.execute(httpGet);
            sResponse = IOUtils.toString(oHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            System.out.println(getDTSG(sResponse));

            System.out.println(sResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static void setDefaultCookieHeaders(HttpRequestBase oRequest) {
        oRequest.addHeader("Origin", "https://www.facebook.com");
        oRequest.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
    }

    public static HashMap<String, String> getCookieString(HttpResponse oResponse) {
        HashMap<String, String> oResult = new HashMap<>();
        for (Header header : oResponse.getHeaders("Set-Cookie")) {
            List<HttpCookie> httpCookies = HttpCookie.parse(header.getValue());

            for (HttpCookie httpCookie : httpCookies) {
                oResult.put(httpCookie.getName(), httpCookie.getValue());
            }
//            if (!sResult.isEmpty()) {
//                sResult += "; ";
//            }
//            sResult += httpCookie.getName() + "=" + httpCookie.getValue();
        }
        return oResult;
    }


    public static String mapToString(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("; ");
            }
            String value = map.get(key);
            stringBuilder.append((key != null ? key : ""));
            stringBuilder.append("=");
            stringBuilder.append(value != null ? value : "");
        }

        return stringBuilder.toString();
    }

    public static String getDTSG(String _sInput) {
        Pattern p = Pattern.compile("name=\"fb_dtsg\" value=\"(.*?)\"");
        Matcher m = p.matcher(_sInput);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
}
