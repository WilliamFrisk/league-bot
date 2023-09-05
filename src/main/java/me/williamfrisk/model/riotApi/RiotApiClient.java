package me.williamfrisk.model.riotApi;


import me.williamfrisk.utils.Throw;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class RiotApiClient {
    private static final Logger logger = LoggerFactory.getLogger(RiotApiClient.class);

    public static JSONObject get(final String request) throws ApiException {
        HttpClient httpClient = HttpClients.createDefault();


        String requestString = RiotApiConstants.HOST +
                request +
                "?api_key=" +
                RiotApiConstants.CURRENT_KEY;

        HttpGet httpGet = new HttpGet(requestString);

        try {
            HttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                return new JSONObject(responseBody);
            } else {
                throw new ApiException("API Request failed with response code: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            Throw.asUnchecked(e);
            // This will never be executed. It's just here to make the compiler happy.
            return null;
        }
    }
}
