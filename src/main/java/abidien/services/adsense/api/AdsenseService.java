package abidien.services.adsense.api;

import abidien.chuongga.Main;
import abidien.common.Config;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.adsense.AdSense;
import com.google.api.services.adsense.AdSenseScopes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Created by ABIDIEN on 31/07/2016.
 */
public class AdsenseService {
    private static AdsenseService instance = null;
//    protected ClassicSingleton() {
//        // Exists only to defeat instantiation.
//    }
    public static AdsenseService getInstance() {
        if(instance == null) {
            instance = new AdsenseService();
        }
        return instance;
    }

    private static final String APPLICATION_NAME = "TraiGa";

    private static final java.io.File DATA_STORE_DIR =
        new java.io.File(Config.adsenseDB);

    private static FileDataStoreFactory dataStoreFactory;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport httpTransport;
    private static GoogleAuthorizationCodeFlow flow;
    private static GoogleClientSecrets clientSecrets;

    private AdsenseService() {
        try {
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                    new InputStreamReader(Main.class.getResourceAsStream("/client_secrets.json")));
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets,
                    Collections.singleton(AdSenseScopes.ADSENSE_READONLY)).setDataStoreFactory(
                    dataStoreFactory).setAccessType("offline").setApprovalPrompt("force").build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public AdSense getAdsense(String userId) {
        // Authorization.
        Credential credential = null;
        try {
            credential = authorize(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return getAdsense(credential);
    }

    public AdSense getAdsense(Credential credential) {
        if (credential == null) return null;
        // Set up AdSense Management API client.
        AdSense adsense = new AdSense.Builder(
                new NetHttpTransport(), JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
                .build();
        return adsense;
    }

    private Credential authorize(String userId) throws Exception {
        // load client secrets
        if (clientSecrets.getDetails().getClientId().startsWith("Enter")
                || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
            System.out.println("Enter Client ID and Secret from "
                    + "https://code.google.com/apis/console/?api=adsense into "
                    + "adsense-cmdline-sample/src/main/resources/client_secrets.json");
            System.exit(1);
        }
        return flow.loadCredential(userId);
    }

    public String genLinkAddGAAccount() {
        String redirectUri = Config.host + "web/ga_account/callback";
        return flow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
    }

    public Credential addGAAccount(String userId, String code) throws IOException {
        String redirectUri = Config.host + "web/ga_account/callback";
        TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
        return flow.createAndStoreCredential(response, userId);
    }
}
