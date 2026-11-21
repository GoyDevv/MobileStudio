package com.goydevv.studrblx;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public final class CrashActivity extends Activity {
    public static final String EXTRA_TRACE = "crash_trace";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String trace = getIntent() != null && getIntent().hasExtra(EXTRA_TRACE)
                ? getIntent().getStringExtra(EXTRA_TRACE)
                : "Unknown crash";

        float d = getResources().getDisplayMetrics().density;
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(0xFF1B1B1D);
        int pad = (int) (14 * d);
        root.setPadding(pad, pad, pad, pad);

        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        TextView title = new TextView(this);
        title.setText("StudioRblx Crash Report");
        title.setTextColor(0xFFFF5C5C);
        title.setTextSize(17.0f);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        top.addView(title, tlp);

        Button copyBtn = new Button(this);
        copyBtn.setText("Copy Log");
        copyBtn.setTextColor(0xFFFFFFFF);
        copyBtn.setBackgroundColor(0xFF00A2FF);
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (cm != null) {
                    cm.setPrimaryClip(ClipData.newPlainText("Crash Log", trace));
                    Toast.makeText(CrashActivity.this, "Copied stack trace", Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        blp.rightMargin = (int) (8 * d);
        top.addView(copyBtn, blp);

        Button restartBtn = new Button(this);
        restartBtn.setText("Restart");
        restartBtn.setTextColor(0xFFFFFFFF);
        restartBtn.setBackgroundColor(0xFF333337);
        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CrashActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
        top.addView(restartBtn);
        root.addView(top);

        ScrollView sv = new ScrollView(this);
        sv.setBackgroundColor(0xFF141416);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
        slp.topMargin = (int) (10 * d);

        HorizontalScrollView hsv = new HorizontalScrollView(this);
        TextView logView = new TextView(this);
        logView.setText(trace);
        logView.setTextColor(0xFFE0E0E0);
        logView.setTextSize(11.5f);
        logView.setTypeface(Typeface.MONOSPACE);
        logView.setTextIsSelectable(true);
        int lpad = (int) (10 * d);
        logView.setPadding(lpad, lpad, lpad, lpad);
        hsv.addView(logView);
        sv.addView(hsv);
        root.addView(sv, slp);

        setContentView(root);
    }
}
