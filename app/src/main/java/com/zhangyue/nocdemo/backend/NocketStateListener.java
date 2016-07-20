package com.zhangyue.nocdemo.backend;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.zhangyue.nocdemo.Opcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nocket.door.INocketListener;
import nocket.door.MessageInfo;
import nocket.door.TokenTuple;

/**
 * Created by pyloque on 16/7/15.
 */
public class NocketStateListener implements INocketListener {
    private final static String TAG = "NOCDEMO";

    private MetaStore meta;
    private Messenger messenger;
    private MessageUniqueFilter filter;

    public NocketStateListener(Context context, MetaStore meta, Messenger messenger) {
        this.meta = meta;
        this.messenger = messenger;
        this.filter = new MessageUniqueFilter(context);
    }

    @Override
    public void onNewDeviceId(long deviceId) {
        meta.setDeviceId(deviceId);
        meta.save();
    }

    @Override
    public void onNewToken(TokenTuple token) {
        meta.setToken(token.getToken());
        meta.setTokenExpireTs(System.currentTimeMillis() / 1000 + token.getTtl());
        meta.save();
    }

    private void send(Message message) {
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            Log.e(TAG, "send message to frontend error", e);
        }
    }

    @Override
    public void onActive() {
        Message msg = Message.obtain();
        msg.what = Opcode.CONNECTION_ACTIVE;
        send(msg);
        filter.load();
    }

    @Override
    public void onOfflineMessages(List<MessageInfo> messages) {
        Bundle bundle = new Bundle();
        ArrayList<String> data = new ArrayList<>();
        for (MessageInfo message : messages) {
            data.add(message.asJson().toString());
        }
        if (!data.isEmpty()) {
            Message msg = Message.obtain();
            msg.what = Opcode.OFFLINE_MESSAGE;
            bundle.putStringArrayList("messages", data);
            msg.setData(bundle);
            send(msg);
        }
    }

    @Override
    public void onRealtimeMessage(MessageInfo message) {
        Message msg = Message.obtain();
        msg.what = Opcode.REALTIME_MESSAGE;
        Bundle bundle = new Bundle();
        bundle.putString("message", message.asJson().toString());
        msg.setData(bundle);
        send(msg);
    }

    @Override
    public void onUserOnline() {
        Message msg = Message.obtain();
        msg.what = Opcode.USER_ONLINE;
        send(msg);
    }

    @Override
    public void onTagList(Set<String> set) {
        Message msg = Message.obtain();
        msg.what = Opcode.TAG_LIST;
        Bundle bundle = new Bundle();
        ArrayList<String> data = new ArrayList<>(set);
        bundle.putStringArrayList("tags", data);
        msg.setData(bundle);
        send(msg);
    }

    @Override
    public void onLost() {
        Message msg = Message.obtain();
        msg.what = Opcode.CONNECTION_LOST;
        send(msg);
    }
}
