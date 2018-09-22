package main.java.com.abidien;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameMain {
    public static long lStartTime;
    public static void main(String[] args) {
        lStartTime = System.currentTimeMillis();
        HttpClient oHttpClient = HttpClientBuilder.create().build();
        //String sCookie = "datr=pKSmW1sjGuvbRYbsCSPK8KiH; reg_fb_ref=deleted; c_user=100026873742388; reg_fb_gate=deleted; xs=11%3AW8xTnstUf9SiAw%3A2%3A1537647781%3A19718%3A6252; fr=1BwxAJiTpldLVc9KU.AWWL57ebjG3sLEzn0CY8R6oEh_E.BbpqSk.9a.AAA.0.0.BbpqSl.AWVNoPty; pl=n; sb=pKSmW9GKYeAMwsIPenJRW51u";
        String sCookie = "fr=1tOZnlr62VQvnXjd6.AWWqo28hzvxI0Yjijw9JSZAgCCQ.Bbk6ef._i.AAA.0.0.BbprGh.AWX_nUnB;datr=n6eTW_sixQCqvXpz58poufVP;c_user=100011630985466;sb=h2ukWwFPqhrfIE53QZ3ZApDb;xs=40%3AtNvPKEm8Q16vyA%3A2%3A1537502091%3A15937%3A6261;pl=n;";

        //String sFb_dtsg = "AQFix49LiZca:AQEhH-dHCfya";
        String sFb_dtsg = "AQHqIIe8iwW6:AQFEI0D-7Gz0";

        String sPageID = "620626984937388";
        String sOldName = "Vintage Tattoo 1";
        String sNewName = "Xăm Hình Chuyên Nghiệp";

        new Thread(){
            public void run(){
                changeFanpageName(sPageID, sCookie, sFb_dtsg, sOldName);
            }
        }.start();

        try {
            Thread.sleep(100);
            for (int i = 0; i < 2; i ++) {
                new Thread() {
                    public void run() {
                        changeFanpageName(sPageID, sCookie, sFb_dtsg, sNewName);
                    }
                }.start();
            }
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void changeFanpageName(String _sPageID, String _sCookie, String _sFb_dtsg, String _sNewName) {
        long lInit = System.currentTimeMillis() - lStartTime;
        HttpClient oHttpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("https://www.facebook.com/pages/rename/submit/?page_id=" + _sPageID + "&dpr=1");
        setDefaultCookieHeaders(httpPost);
        httpPost.addHeader("Cookie", _sCookie);

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("__user", "100011630985466"));
        nameValuePairs.add(new BasicNameValuePair("requested_name", _sNewName));
        nameValuePairs.add(new BasicNameValuePair("fb_dtsg", _sFb_dtsg));
        nameValuePairs.add(new BasicNameValuePair("__a", "1"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Charset.forName("UTF-8")));
        HttpResponse oHttpResponse = null;
        try {
            System.out.println(lInit + " - " + (System.currentTimeMillis() - lStartTime));
            oHttpResponse = oHttpClient.execute(httpPost);
            String sResponse = IOUtils.toString(oHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            System.out.println(StringEscapeUtils.unescapeJava(sResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultCookieHeaders(HttpRequestBase oRequest) {
        oRequest.addHeader("Origin", "https://www.facebook.com");
        oRequest.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
        oRequest.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
    }

    public static String getDTSG(String _sInput) {
        Pattern p = Pattern.compile("\"dtsg\":\\{\"token\":\"(.*?)\"");
        Matcher m = p.matcher(_sInput);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
}
