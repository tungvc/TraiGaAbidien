package abidien.FanpageRenaming.bo;

import org.apache.commons.codec.digest.DigestUtils;
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
import java.net.HttpCookie;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.http.message.BasicNameValuePair;

public class FacebookCookie_BO {

    public static class FacebookCookie {
        public String msDtsg;
        public String msCookie;

        public FacebookCookie(String _sDtsg, String _sCookie) {
            msCookie = _sCookie;
            msDtsg = _sDtsg;
        }
    }

    public static final String API_KEY = "882a8490361da98702bf97a021ddc14d";
    public static final String API_SECRET = "62f8ce9f74b12f84c123cc23437a4a32";

    public static FacebookCookie getCookie(String _sUser, String _sPass) {
        FacebookCookie oResult = null;

        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost("https://m.facebook.com/");
        setDefaultCookieHeaders(httpPost);
        HttpResponse oHttpResponse = null;
        try {
            oHttpResponse = httpClient.execute(httpPost);
            HashMap<String, String> sCookieValue = getCookieString(oHttpResponse);
            String sResponse = IOUtils.toString(oHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);

            // login
            httpPost = new HttpPost("https://m.facebook.com/login/async/?refsrc=https%3A%2F%2Fm.facebook.com%2Flogin.php&lwv=100");
            setDefaultCookieHeaders(httpPost);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", _sUser));
            nameValuePairs.add(new BasicNameValuePair("pass", _sPass));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            oHttpResponse = httpClient.execute(httpPost);
            sResponse = IOUtils.toString(oHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);

            sCookieValue.putAll(getCookieString(oHttpResponse));
            String sCookieToSubmit = mapToString(sCookieValue);
            System.out.println(sCookieToSubmit);

            // Get dtsg
            HttpGet httpGet = new HttpGet("https://facebook.com/");
            setDefaultCookieHeaders(httpGet);
            httpGet.addHeader("Cookie", sCookieToSubmit);

            oHttpResponse = httpClient.execute(httpGet);
            sResponse = IOUtils.toString(oHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            System.out.println(getDTSG(sResponse));

            oResult = new FacebookCookie(getDTSG(sResponse), sCookieToSubmit);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return oResult;
    }

    public static void main1(String[] args) {
        getCookieByApp("bnmnazhd@emltmp.com", "codaumahoi1");
    }

    public static void getCookieByApp(String _sUser, String _sPass) {
        ArrayList<String> oParams = new ArrayList<>();
        oParams.add("api_key=" + API_KEY);
        oParams.add("credentials_type=password");
        oParams.add("email=" + _sUser);
        oParams.add("format=JSON");
        oParams.add("generate_machine_id=1");
        oParams.add("generate_session_cookies=1");
        oParams.add("locale=vi_VN");
        oParams.add("method=auth.login");
        oParams.add("password=" + _sPass);
        oParams.add("return_ssl_resources=0");
        oParams.add("v=1.0");
        addSign(oParams);

        HttpClient oHttpClient = new DefaultHttpClient();
        HttpGet oHttpGet = new HttpGet("https://api.facebook.com/restserver.php?" + String.join("&", oParams));
        setDefaultCookieHeaders(oHttpGet);

        HttpResponse oHttpResponse = null;
        try {
            oHttpResponse = oHttpClient.execute(oHttpGet);

            String sResponse = IOUtils.toString(oHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            System.out.println(sResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addSign(List<String> _oParams) {
        Collections.sort(_oParams);
        String sSign = DigestUtils.md5Hex(String.join("", _oParams) + API_SECRET);
        _oParams.add("sig=" + sSign);
    }

    public static void setDefaultCookieHeaders(HttpRequestBase oRequest) {
        oRequest.addHeader("Origin", "https://www.facebook.com");
        oRequest.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        oRequest.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
    }

    public static HashMap<String, String> getCookieString(HttpResponse oResponse) {
        HashMap<String, String> oResult = new HashMap<>();
        for (Header header : oResponse.getHeaders("Set-Cookie")) {
            List<HttpCookie> httpCookies = HttpCookie.parse(header.getValue());

            for (HttpCookie httpCookie : httpCookies) {
                oResult.put(httpCookie.getName(), httpCookie.getValue());
            }
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
