package com.zhangyue.nocdemo.frontend;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.zhangyue.nocdemo.Opcode;

/**
 * Created by pyloque on 16/7/15.
 */
public class NocdorConnection implements ServiceConnection {
    private final static String TAG = "NOCDORCONN";

    private Messenger messenger;
    private DemoPresenter presenter;

    public NocdorConnection(Messenger messenger, DemoPresenter presenter) {
        this.messenger = messenger;
        this.presenter = presenter;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder binder) {
        Message msg = Message.obtain();
        msg.what = Opcode.CONNECT_SERVER;
        msg.replyTo = messenger;
        Messenger target = new Messenger(binder);
        try {
            target.send(msg);
        } catch (RemoteException e) {
            Log.e(TAG, "onServiceConnected: send message error", e);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        DemoModel model = presenter.getModel();
        model.setStopped(true);
        model.setSwitchText("Start");
        model.setStateText("");
        presenter.reload();
    }
}
