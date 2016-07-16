package com.zhangyue.nocdemo.frontend;

import java.util.List;
import java.util.Set;

import nocket.door.MessageInfo;

/**
 * Created by pyloque on 16/7/16.
 */
public interface INocdorListener {
    public void onConnectionActive();
    public void onOfflineMessages(List<MessageInfo> messages);
    public void onUserOnline();
    public void onRealtimeMessage(MessageInfo message);
    public void onTagList(Set<String> tags);
    public void onConnectionLost();
}
