package com.zhangyue.nocdemo.backend;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import nocket.door.ITokenFetcher;
import nocket.door.NocketException;
import nocket.door.TokenTuple;
import nocket.door.http.HttpClient;
import nocket.door.tools.QuerySigner;

/**
 * Created by pyloque on 16/7/15.
 */
public class IdemoTokenFetcher implements ITokenFetcher {

    private Context context;

    public IdemoTokenFetcher(Context context) {
        this.context = context;
    }

    @Override
    public TokenTuple fetch(int platformId, String productId, String userId, long deviceId) throws IOException {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
        }
        Bundle meta = appInfo.metaData;
        String host = meta.getString("zhangyue.nocdor.host");
        int port = meta.getInt("zhangyue.nocdor.port");
        String secret = "123456";
        int ttl = 600;
        String sign = QuerySigner.sign(secret, productId, userId, "" + deviceId, "" + ttl);
        JSONObject js = HttpClient.get(String.format("http://%s:%s/api/auth/token", host, port))
                .param("pid", productId)
                .param("uid", userId)
                .param("pid", "" + platformId)
                .param("did", "" + deviceId)
                .param("ttl", "" + ttl)
                .param("s", sign).start();
        try {
            return new TokenTuple(js.getLong("token"), js.getInt("ttl"));
        } catch (JSONException e) {
            throw new NocketException("json format error");
        }
    }
}
