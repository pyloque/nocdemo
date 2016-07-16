package com.zhangyue.nocdemo.frontend;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zhangyue.nocdemo.Opcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import nocket.door.MessageInfo;

/**
 * Created by pyloque on 16/7/15.
 */
public class FrontendHandler extends Handler {
    private final static String TAG = "FRONTEND";
    private INocdorListener listener;

    public FrontendHandler(INocdorListener listener) {
        this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        switch(msg.what) {
            case Opcode.CONNECTION_ACTIVE:
                listener.onConnectionActive();
                break;
            case Opcode.OFFLINE_MESSAGE:
                ArrayList<String> data = msg.getData().getStringArrayList("messages");
                List<MessageInfo> messages = new ArrayList<>();
                for(String js: data) {
                    MessageInfo message = new MessageInfo();
                    try {
                        message.fromJson(new JSONObject(js));
                        messages.add(message);
                    } catch (JSONException e) {
                        Log.e(TAG, "handleOfflineMessage: json format error", e);
                    }
                }
                listener.onOfflineMessages(messages);
                break;
            case Opcode.USER_ONLINE:
                listener.onUserOnline();
                break;
            case Opcode.REALTIME_MESSAGE:
                String js = msg.getData().getString("message");
                MessageInfo message = new MessageInfo();
                try {
                    message.fromJson(new JSONObject(js));
                    listener.onRealtimeMessage(message);
                } catch (JSONException e) {
                    Log.e(TAG, "handleRealtimeMessage: json format error", e);
                }
                break;
            case Opcode.TAG_LIST:
                ArrayList<String> tags = msg.getData().getStringArrayList("tags");
                listener.onTagList(new HashSet<>(tags));
                break;
            case Opcode.CONNECTION_LOST:
                listener.onConnectionLost();
                break;
        }
    }

}
