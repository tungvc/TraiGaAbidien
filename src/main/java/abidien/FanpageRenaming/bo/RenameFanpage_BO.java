package abidien.FanpageRenaming.bo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenameFanpage_BO {

    public static String changeFanpageName(String _sFb_dtsg, String _sCookie, String _sPageID, String _sOldName, String _sNewName) {
        String sResult = null;
        HttpClient oHttpClient = HttpClientBuilder.create().build();
//        String sCookie = "fr=1tOZnlr62VQvnXjd6.AWWqo28hzvxI0Yjijw9JSZAgCCQ.Bbk6ef._i.AAA.0.0.BbprGh.AWX_nUnB;datr=n6eTW_sixQCqvXpz58poufVP;c_user=100011630985466;sb=h2ukWwFPqhrfIE53QZ3ZApDb;xs=40%3AtNvPKEm8Q16vyA%3A2%3A1537502091%3A15937%3A6261;pl=n;";
//        String sFb_dtsg = "AQHqIIe8iwW6:AQFEI0D-7Gz0";
//        String sPageID = "1850249518568185";
//        String sOldName = "Tien Ich 1";
//        String sNewName = "Điêu Khắc Thập Tam";

        ArrayList<Thread> oThreads = new ArrayList<>();
        ConcurrentLinkedQueue<String> oResult = new ConcurrentLinkedQueue();
        Thread oPreRenameThread = new Thread(){
            public void run(){
                String sResult = requestChangeFanpageName(_sPageID, _sCookie, _sFb_dtsg, _sOldName + " 1");
                oResult.offer(sResult);
            }
        };
        oPreRenameThread.start();
        oThreads.add(oPreRenameThread);

        try {
            Thread.sleep(100);
            for (int i = 0; i < 2; i ++) {
                Thread oRenameThread = new Thread() {
                    public void run() {
                        String sResult = requestChangeFanpageName(_sPageID, _sCookie, _sFb_dtsg, _sNewName);
                        oResult.offer(sResult);
                    }
                };

                oRenameThread.start();
                oThreads.add(oRenameThread);
            }

            for (Thread oThread : oThreads) {
                oThread.join();
            }

            sResult = String.join("\n", oResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sResult;
    }

    private static String requestChangeFanpageName(String _sPageID, String _sCookie, String _sFb_dtsg, String _sNewName) {
        String sResult = null;
        HttpClient oHttpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("https://www.facebook.com/pages/rename/submit/?page_id=" + _sPageID + "&dpr=1");
        FacebookCookie_BO.setDefaultCookieHeaders(httpPost);
        httpPost.addHeader("Cookie", _sCookie);

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("__user", "100011630985466"));
        nameValuePairs.add(new BasicNameValuePair("requested_name", _sNewName));
        nameValuePairs.add(new BasicNameValuePair("fb_dtsg", _sFb_dtsg));
        nameValuePairs.add(new BasicNameValuePair("__a", "1"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Charset.forName("UTF-8")));
        HttpResponse oHttpResponse = null;
        try {
            oHttpResponse = oHttpClient.execute(httpPost);
            String sResponse = IOUtils.toString(oHttpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
            System.out.println(StringEscapeUtils.unescapeJava(sResponse));
            sResult = StringEscapeUtils.unescapeJava(sResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sResult;
    }
}
