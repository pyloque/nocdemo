package com.zhangyue.nocdemo.frontend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Messenger;
import android.widget.Button;
import android.widget.TextView;

import com.zhangyue.nocdemo.Helpers;
import com.zhangyue.nocdemo.backend.NocdorService;

import nocket.door.Nocdor;

/**
 * Created by pyloque on 16/7/15.
 */
public class NetworkReceiver extends BroadcastReceiver {
    private DemoModel model;
    private NocdorConnection conn;

    public NetworkReceiver(DemoModel model, NocdorConnection conn) {
        this.model = model;
        this.conn = conn;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(true) {return;}
        if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if(Helpers.isNetworkActive(context)) {
                intent = new Intent(context, NocdorService.class);
                intent.putExtra("user_id", model.getUserId());
                intent.putExtra("channel_id", model.getChannelId());
                intent.putExtra("version_id", model.getVersionId());
                context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
            } else {
                context.unbindService(conn);
            }
        }
    }
}
