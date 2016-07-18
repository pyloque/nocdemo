package com.zhangyue.nocdemo.frontend;

import android.graphics.Color;

/**
 * Created by pyloque on 16/7/16.
 */
public class DemoPresenter {

    private DemoView view;

    public DemoModel model;

    public DemoPresenter(DemoView view, DemoModel model) {
        this.view = view;
        this.model = model;
    }

    public DemoView getView() {
        return view;
    }

    public void setView(DemoView view) {
        this.view = view;
    }

    public DemoModel getModel() {
        return model;
    }

    public void setModel(DemoModel model) {
        this.model = model;
    }

    public void reload() {
        if(model.isStopped()) {
            view.getBtnSwitch().setBackgroundColor(Color.BLACK);
        } else {
            view.getBtnSwitch().setBackgroundColor(Color.GREEN);
        }
        view.getBtnSwitch().setText(model.getSwitchText());
        view.getTxtState().setText(model.getStateText());
        view.getEditUserId().setText(model.getUserId());
        view.getEditChannelId().setText(model.getChannelId());
        view.getEditVersionId().setText(model.getVersionId());
    }

    public boolean isStopped() {
        return model.isStopped();
    }
}
