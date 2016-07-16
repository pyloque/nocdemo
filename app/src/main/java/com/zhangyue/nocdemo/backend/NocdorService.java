package com.zhangyue.nocdemo.backend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;

public class NocdorService extends Service {

    private MetaStore meta;
    private Messenger messenger;
    private BackendHandler handler;

    @Override
    public IBinder onBind(Intent intent) {
        meta = new MetaStore(this.getApplicationContext());
        String userId = intent.getStringExtra("user_id");
        String channelId = intent.getStringExtra("channel_id");
        String versionId = intent.getStringExtra("version_id");
        meta.setUserId(userId);
        meta.setChannelId(channelId);
        meta.setVersionId(versionId);
        meta.load();
        handler = new BackendHandler(this.getApplicationContext(), meta);
        messenger = new Messenger(handler);
        return messenger.getBinder();
    }

    @Override
    public void onRebind(Intent intent) {
        String userId = intent.getStringExtra("user_id");
        String channelId = intent.getStringExtra("channel_id");
        String versionId = intent.getStringExtra("version_id");
        meta.setUserId(userId);
        meta.setChannelId(channelId);
        meta.setVersionId(versionId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        handler.shutdown();
        return true;
    }

    @Override
    public void onDestroy() {
        handler.shutdown();
    }
}
