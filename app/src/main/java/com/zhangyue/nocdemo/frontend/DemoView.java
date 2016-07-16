package com.zhangyue.nocdemo.frontend;

import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by pyloque on 16/7/16.
 */
public class DemoView {

    private Button btnSwitch;
    private TextView txtState;

    public DemoView(Button btnSwitch, TextView txtState) {
        this.btnSwitch = btnSwitch;
        this.txtState = txtState;
    }

    public Button getBtnSwitch() {
        return btnSwitch;
    }

    public void setBtnSwitch(Button btnSwitch) {
        this.btnSwitch = btnSwitch;
    }

    public TextView getTxtState() {
        return txtState;
    }

    public void setTxtState(TextView txtState) {
        this.txtState = txtState;
    }
}
