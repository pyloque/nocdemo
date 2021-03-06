package com.zhangyue.nocdemo.backend;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.zhangyue.nocdemo.Opcode;

import nocket.door.IMessageFilter;
import nocket.door.Nocdor;

/**
 * Created by pyloque on 16/7/15.
 */
public class BackendHandler extends Handler {

    private Nocdor door;
    private MetaStore meta;
    private Context context;
    private MessageUniqueFilter filter;

    public BackendHandler(Context context, MetaStore meta) {
        this.context = context;
        this.meta = meta;
        this.filter = new MessageUniqueFilter(context);
    }

    @Override
    public void handleMessage(Message msg) {
        switch(msg.what) {
            case Opcode.CONNECT_SERVER:
                this.filter.load();
                door = new Nocdor(meta.getHost(), meta.getPort(), new IdemoTokenFetcher(context));
                door.user(meta.getPlatformId(), meta.getProductId(), meta.getUserId())
                        .deviceId(meta.getDeviceId())
                        .vcp(meta.getVersionId(), meta.getChannelId(), meta.getPhoneModel())
                        .listener(new NocketStateListener(context, meta, msg.replyTo))
                        .filter(filter)
                        .start();
                break;
            case Opcode.GET_TAG_LIST:
                door.requestTags();
                break;
        }
    }

    public void shutdown() {
        if(door != null) {
            door.shutdown();
            door = null;
        }
    }
}
