package com.zhangyue.nocdemo.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by pyloque on 16/7/16.
 */
public class MessageUniqueFilter {

    private Context context;
    private Set<Long> uniqueIds;
    private LinkedList<Long> ids;
    private final static int MAX_ITEMS = 20;

    public MessageUniqueFilter(Context context) {
        this.context = context;
    }

    public void load() {
        if(ids == null) {
            ids = new LinkedList<>();
            uniqueIds = new HashSet<>();
            SharedPreferences pref = context.getSharedPreferences("nocket_msg_ids", Context.MODE_PRIVATE);
            for(String id:pref.getStringSet("ids", new HashSet<String>())) {
                ids.add(Long.parseLong(id));
                uniqueIds.add(Long.parseLong(id));
            }
        }
    }

    public boolean add(Long id) {
        boolean fresh = uniqueIds.add(id);
        if(fresh) {
            if(ids.size() > MAX_ITEMS) {
                long deletedId = ids.removeFirst();
                uniqueIds.remove(deletedId);
            }
            ids.add(id);
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
