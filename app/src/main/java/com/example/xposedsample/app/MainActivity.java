package com.example.xposedsample.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.xposedsample.utils.J;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Set<Map.Entry<String, Integer>> entrySet = MethodStatistics.getStatistics().entrySet();

        List<String> stringList = new ArrayList<String>();
        stringList.add("First");
        for (Map.Entry<String, Integer> entry : entrySet) {
            stringList.add(String.format("%d - %s", entry.getValue(), entry.getKey()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringList);

        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
    }
}
