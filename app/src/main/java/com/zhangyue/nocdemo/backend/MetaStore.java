package com.zhangyue.nocdemo.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.zhangyue.nocdemo.Helpers;

/**
 * Created by pyloque on 16/7/15.
 */
public class MetaStore {

    private Context context;
    private long deviceId;
    private long token;
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
        editor.commit();
    }

    public void load() {
        SharedPreferences pref = context.getSharedPreferences("nocket", Context.MODE_PRIVATE);
        deviceId = pref.getLong("device_id", 0);
        token = pref.getLong("token", 0);
    }
}
