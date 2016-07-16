package com.zhangyue.nocdemo.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by pyloque on 16/7/16.
 */
public class MessageUniqueFilter {

    private Context context;
    private SortedSet<Long> ids;

    public MessageUniqueFilter(Context context) {
        this.context = context;
    }

    public void load() {
        if(ids == null) {
            ids = new TreeSet<>();
            SharedPreferences pref = context.getSharedPreferences("nocket_msg_ids", Context.MODE_PRIVATE);
            for(String id:pref.getStringSet("ids", new HashSet<String>())) {
                ids.add(Long.parseLong(id));
            }
        }
    }

    public boolean add(Long id) {
        boolean fresh = ids.add(id);
        if(fresh) {
            Set<String> raws = new HashSet<>();
            for(Long s: ids) {
                raws.add(s.toString());
            }
            SharedPreferences pref = context.getSharedPreferences("nocket_msg_ids", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putStringSet("ids", raws);
            editor.commit();
        }
        return fresh;
    }
}
