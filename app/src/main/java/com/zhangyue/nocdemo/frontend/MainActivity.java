package com.zhangyue.nocdemo.frontend;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Messenger;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangyue.nocdemo.Helpers;
import com.zhangyue.nocdemo.backend.NocdorService;
import com.zhangyue.nocdemo.R;

import java.util.List;
import java.util.Set;

import nocket.door.MessageInfo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NetworkReceiver networkReceiver;
    private boolean editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btnSwitch = (Button)findViewById(R.id.btn_switch);
        TextView txtState = (TextView)findViewById(R.id.txt_state);
        final EditText editUserId = (EditText)findViewById(R.id.edit_user_id);
        final EditText editChannelId = (EditText)findViewById(R.id.edit_channel_id);
        final EditText editVersionId = (EditText)findViewById(R.id.edit_version_id);
        final Button btnEdit = (Button)findViewById(R.id.btn_edit);

        final DemoPresenter presenter = new DemoPresenter(new DemoView(btnSwitch, txtState, editUserId, editChannelId,editVersionId), new DemoModel());
        presenter.reload();

        INocdorListener listener = new NocdorStateListener(presenter);
        Messenger messenger = new Messenger(new FrontendHandler(listener));
        final NocdorConnection conn = new NocdorConnection(messenger, presenter);

        btnSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(presenter.isStopped()) {
                    Intent intent = new Intent(MainActivity.this, NocdorService.class);
                    DemoModel model = presenter.getModel();
                    intent.putExtra("user_id", model.getUserId());
                    intent.putExtra("channel_id", model.getChannelId());
                    intent.putExtra("version_id", model.getVersionId());
                    bindService(intent,conn , Context.BIND_AUTO_CREATE);
                } else {
                    unbindService(conn);
                    presenter.getModel().setStopped(true);
                    presenter.getModel().setSwitchText("Start");
                    presenter.reload();
                }
            }

        });

        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!editing) {
                    editing = true;
                    btnEdit.setText("Commit");
                    btnEdit.setBackgroundColor(Color.RED);
                    editUserId.setInputType(InputType.TYPE_CLASS_TEXT);
                    editChannelId.setInputType(InputType.TYPE_CLASS_TEXT);
                    editVersionId.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    editing = false;
                    btnEdit.setText("Edit");
                    btnEdit.setBackgroundColor(Color.BLUE);
                    editUserId.setInputType(InputType.TYPE_NULL);
                    editChannelId.setInputType(InputType.TYPE_NULL);
                    editVersionId.setInputType(InputType.TYPE_NULL);
                    DemoModel model = presenter.getModel();
                    if(editUserId.getText().toString().equals(model.getUserId())
                            && editChannelId.getText().toString().equals(model.getChannelId())
                            && editVersionId.getText().toString().equals(model.getVersionId())) {
                        return;
                    }
                    model.setUserId(editUserId.getText().toString());
                    model.setChannelId(editChannelId.getText().toString());
                    model.setVersionId(editVersionId.getText().toString());
                }
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkReceiver = new NetworkReceiver(presenter.getModel(), conn);
        registerReceiver(networkReceiver, filter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
