package com.zhangyue.nocdemo.frontend;

import android.graphics.Color;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by pyloque on 16/7/16.
 */
public class DemoView {

    private Button btnSwitch;
    private TextView txtState;
    private EditText editUserId;
    private EditText editChannelId;
    private EditText editVersionId;

    public DemoView(Button btnSwitch, TextView txtState, EditText editUserId, EditText editChannelId, EditText editVersionId) {
        this.btnSwitch = btnSwitch;
        this.txtState = txtState;
        this.editUserId = editUserId;
        this.editChannelId = editChannelId;
        this.editVersionId = editVersionId;
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

    public EditText getEditUserId() {
        return editUserId;
    }

    public void setEditUserId(EditText editUserId) {
        this.editUserId = editUserId;
    }

    public EditText getEditChannelId() {
        return editChannelId;
    }

    public void setEditChannelId(EditText editChannelId) {
        this.editChannelId = editChannelId;
    }

    public EditText getEditVersionId() {
        return editVersionId;
    }

    public void setEditVersionId(EditText editVersionId) {
        this.editVersionId = editVersionId;
    }
}
