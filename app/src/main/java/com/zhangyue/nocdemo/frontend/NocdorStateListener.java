package com.zhangyue.nocdemo.frontend;

import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

import nocket.door.MessageInfo;

/**
 * Created by pyloque on 16/7/16.
 */
public  class NocdorStateListener implements INocdorListener {
    private DemoPresenter presenter;
    private boolean keepOfflineMessages;

    public NocdorStateListener(DemoPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onConnectionActive() {
        DemoModel model = presenter.getModel();
        model.setSwitchText("Stop");
        model.setStateText("Connection Active");
        model.setStopped(false);
        this.keepOfflineMessages = false;
        presenter.reload();
    }

    @Override
    public void onOfflineMessages(List<MessageInfo> messages) {
        StringBuffer buffer = new StringBuffer();
        for(MessageInfo message: messages) {
            buffer.append("id=" + message.getId() + " ");
            buffer.append("tag=" + message.getTag() + "\n");
        }
        String text = "";
        if(buffer.length() > 0) {
            text = buffer.substring(0, buffer.length() - 1);
        }
        DemoModel model = presenter.getModel();
        model.setStateText("Offline Message \n" + text);
        this.keepOfflineMessages = true;
        presenter.reload();
    }

    @Override
    public void onUserOnline() {
        DemoModel model = presenter.getModel();
        if(!this.keepOfflineMessages) {
            model.setStateText("User Online");
        }
        presenter.reload();
    }

    @Override
    public void onRealtimeMessage(MessageInfo message) {
        DemoModel model = presenter.getModel();
        StringBuffer buffer = new StringBuffer();
        buffer.append("id=" + message.getId() + "\n");
        buffer.append("tag=" + message.getTag() + "\n");
        buffer.append("title=" + message.getTitle());
        model.setStateText("Realtime Message \n" + buffer.toString());
        presenter.reload();
    }

    @Override
    public void onTagList(Set<String> tags) {
        StringBuffer buffer = new StringBuffer();
        for(String tag: tags) {
            buffer.append(tag);
            buffer.append("\n");
        }
        String text = "";
        if(buffer.length() > 0) {
            text = buffer.substring(0, buffer.length() - 1);
        }
        DemoModel model = presenter.getModel();
        model.setStateText("Tags \n" + text);
        presenter.reload();
    }

    @Override
    public void onConnectionLost() {
        DemoModel model = presenter.getModel();
        model.setStateText("Connection Lost");
        presenter.reload();
    }
}

