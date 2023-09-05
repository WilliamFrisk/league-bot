package me.williamfrisk.model.riotApi;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Summoner {

    private static final String REQUEST_BASE = "/lol/summoner/v4/summoners";

    private final String accountId;
    private final int profileIconId;
    private final long revisionDate;
    private final String name;
    private final String id;
    private final String puuid;
    private final long summonerLevel;

    public static Summoner ofSummonerName(String summonerName) {
        try {
            String modifiedSummonerName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8).replace("+", "%20");
            JSONObject response = RiotApiClient.get(REQUEST_BASE + "/by-name/" + modifiedSummonerName);
            assert response != null;
            return new Summoner(
                    response.getString("accountId"),
                    response.getInt("profileIconId"),
                    response.getLong("revisionDate"),
                    response.getString("name"),
                    response.getString("id"),
                    response.getString("puuid"),
                    response.getLong("summonerLevel")
            );
        } catch (ApiException e) {
            // TODO: Do something smart here
            return null;
        }
    }

    private Summoner(String accountId, int profileIconId, long revisionDate, String name, String id, String puuid, long summonerLevel) {
        this.accountId = accountId;
        this.profileIconId = profileIconId;
        this.revisionDate = revisionDate;
        this.name = name;
        this.id = id;
        this.puuid = puuid;
        this.summonerLevel = summonerLevel;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPuuid() {
        return puuid;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }
}
