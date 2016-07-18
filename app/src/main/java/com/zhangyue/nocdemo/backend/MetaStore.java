package com.zhangyue.nocdemo.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.zhangyue.nocdemo.Helpers;

import nocket.door.TokenTuple;

/**
 * Created by pyloque on 16/7/15.
 */
public class MetaStore {

    private Context context;
    private long deviceId;
    private long token;
    private long tokenExpireTs;
    private int platformId;
    private String productId;
    private String channelId;
    private String versionId;
    private String phoneModel;
    private String userId;
    private String host;
    private int port;

    public MetaStore(Context context) {
        this.context = context;
        this.loadMeta();
    }

    public String getHost() {return host;}

    public int getPort() {return port;}

    public int getPlatformId() {
        return platformId;
    }

    public String getProductId() {
        return productId;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public long getTokenExpireTs() {
        return tokenExpireTs;
    }

    public void setTokenExpireTs(long tokenExpireTs) {
        this.tokenExpireTs = tokenExpireTs;
    }

    private void loadMeta() {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle meta = appInfo.metaData;
            host = meta.getString("zhangyue.nocdor.host");
            port = meta.getInt("zhangyue.nocdor.port");
            platformId = meta.getInt("zhangyue.nocdor.platform_id");
            productId = meta.getString("zhangyue.nocdor.product_id");
            phoneModel = Helpers.getPhoneModel();
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    public void save() {
        SharedPreferences pref = context.getSharedPreferences("nocket", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong("device_id", deviceId);
        editor.putLong("token", token);
        editor.putLong("token_expire_ts", tokenExpireTs);
        editor.commit();
    }

    public void load() {
        SharedPreferences pref = context.getSharedPreferences("nocket", Context.MODE_PRIVATE);
        deviceId = pref.getLong("device_id", 0);
        long token = pref.getLong("token", 0);
        long expireTs = pref.getLong("token_expire_ts", 0);
        long now = System.currentTimeMillis() / 1000;
        if(token > 0 && expireTs > now) {
            this.token = token;
            this.tokenExpireTs = expireTs;
        }
    }
}
