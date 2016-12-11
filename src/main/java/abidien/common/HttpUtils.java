package abidien.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.UUID;

/**
 * Created by ABIDIEN on 10/12/2016.
 */
public class HttpUtils {
    public static CloseableHttpClient defaultHttpClient = HttpClientBuilder.create().build();

    public static String execute(String url) {
        return execute(defaultHttpClient, url);
    }

    public static String execute(HttpClient httpClient, String url) {
        HttpGet httpGet = new HttpGet(url);
        /*if (useProxy && Config.useProxy){
            val proxy = new HttpHost(Config.proxyHost, Config.proxyPort, "http")
            httpGet.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy)
        }*/
        String source = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity != null) {
                source = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
        }
        return source;
    }

    public static File urlToLocalFile(String fileUrl, String destination) {
        try {
            File file = new File(destination);
            file.getParentFile().mkdirs();
            if (file.exists())
                return file;

            URL url = new URL(fileUrl);
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(fileUrl);

            FileOutputStream fos = new FileOutputStream(destination);
            HttpResponse response = client.execute(request);
            fos.write(EntityUtils.toByteArray(response.getEntity()));
            fos.close();
            file = new File(destination);
            if (file.exists())
                return file;
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
