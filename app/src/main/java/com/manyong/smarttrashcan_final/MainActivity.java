package com.manyong.smarttrashcan_final;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost mTab = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent(this, MapActivity.class);
        spec = mTab.newTabSpec("MapsActivity").setIndicator("지도").setContent(intent);
        mTab.addTab(spec);

        intent = new Intent(this, SecondTab.class);
        spec = mTab.newTabSpec("SecondTab").setIndicator("내정보").setContent(intent);
        mTab.addTab(spec);

        intent = new Intent(this, ThirdTab.class);
        spec = mTab.newTabSpec("ThirdTab").setIndicator("쓰레기통비우기").setContent(intent);
        mTab.addTab(spec);
    }
}
