package com.zhangyue.nocdemo.frontend;

import android.graphics.Color;

/**
 * Created by pyloque on 16/7/16.
 */
public class DemoModel {
    private CharSequence switchText = "Start";
    private CharSequence stateText = "";
    private boolean stopped = true;


    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public CharSequence getSwitchText() {
        return switchText;
    }

    public void setSwitchText(CharSequence switchText) {
        this.switchText = switchText;
    }

    public CharSequence getStateText() {
        return stateText;
    }

    public void setStateText(CharSequence stateText) {
        this.stateText = stateText;
    }
}
