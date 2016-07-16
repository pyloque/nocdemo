package com.zhangyue.nocdemo.backend;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import nocket.door.ITokenFetcher;
import nocket.door.NocketException;
import nocket.door.http.HttpClient;
import nocket.door.tools.QuerySigner;

/**
 * Created by pyloque on 16/7/15.
 */
public class IdemoTokenFetcher implements ITokenFetcher {

    @Override
    public long fetch(int platformId, String productId, String userId, long deviceId) throws IOException {
        String secret = "123456";
        int ttl = 600;
        String sign = QuerySigner.sign(secret, productId, userId, "" + deviceId, "" + ttl);
        JSONObject js = HttpClient.get("http://192.168.31.102:8080/api/auth/token").param("pid", productId).param("uid", userId)
                .param("pid", "" + platformId).param("did", "" + deviceId).param("ttl", "" + ttl)
                .param("s", sign).start();
        try {
            return js.getLong("token");
        } catch (JSONException e) {
            throw new NocketException("json format error");
        }
    }
}
