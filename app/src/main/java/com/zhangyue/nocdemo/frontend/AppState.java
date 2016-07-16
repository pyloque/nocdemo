package com.zhangyue.nocdemo.frontend;

/**
 * Created by pyloque on 16/7/15.
 */
public class AppState {

    private String versionId = "vtest";
    private String channelId = "ctest";
    private String userId = "inocdemo";

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getChannelId() {
        return channelId;
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
}
