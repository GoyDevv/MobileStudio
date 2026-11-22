package com.goydevv.studrblx;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import com.goydevv.studrblx.core.OutputLog;
import com.goydevv.studrblx.core.Settings;
import com.goydevv.studrblx.core.Studio;
import com.goydevv.studrblx.ui.Ui;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;

public final class StudioApp extends Application {
    public static final String LAST_CRASH_FILE = "last_crash.txt";
    private boolean placeOpened;
    private boolean started;

    private final Lazy studio = LazyKt.lazy(new Function0() {
        @Override
        public final Object invoke() {
            return StudioApp.studio_delegate$lambda$0(StudioApp.this);
        }
    });

    static final Studio studio_delegate$lambda$0(StudioApp this$0) {
        return new Studio(this$0);
    }

    public final Studio getStudio() {
        return (Studio) this.studio.getValue();
    }

    public final boolean getStarted() {
        return this.started;
    }

    public final void setStarted(boolean z) {
        this.started = z;
    }

    public final boolean getPlaceOpened() {
        return this.placeOpened;
    }

    public final void setPlaceOpened(boolean z) {
        this.placeOpened = z;
    }

    private String buildFullCrashReport(Thread t, Throwable e) {
        StringBuilder sb = new StringBuilder(4096);
        String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
        sb.append("=== StudioRblx Crash Report ===\n");
        sb.append("Time: ").append(ts).append('\n');
        sb.append("Package: ").append(getPackageName()).append('\n');
        sb.append("Device: ").append(Build.MANUFACTURER).append(' ').append(Build.MODEL)
          .append(" (Android ").append(Build.VERSION.RELEASE).append(" / SDK ").append(Build.VERSION.SDK_INT).append(")\n");
        sb.append("Thread: ").append(t != null ? t.getName() : "unknown").append("\n\n");

        sb.append("--- Stack Trace ---\n");
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        sb.append(sw.toString()).append('\n');

        try {
            List<OutputLog.Entry> entries = OutputLog.INSTANCE.snapshot();
            if (entries != null && !entries.isEmpty()) {
                sb.append("--- Recent Studio Output ---\n");
                int start = Math.max(0, entries.size() - 60);
                for (int idx = start; idx < entries.size(); idx++) {
                    OutputLog.Entry en = entries.get(idx);
                    sb.append('[').append(en.getLevel()).append("] ").append(en.getText()).append('\n');
                }
                sb.append('\n');
            }
        } catch (Throwable ignored) {
        }

        try {
            sb.append("--- Logcat Tail ---\n");
            java.lang.Process proc = Runtime.getRuntime().exec(new String[]{"logcat", "-d", "-t", "120", "*:W"});
            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
            br.close();
        } catch (Throwable ignored) {
        }
        return sb.toString();
    }

    private String saveCrashToFiles(String report) {
        String savedPath = "";
        byte[] bytes = report.getBytes(StandardCharsets.UTF_8);
        try {
            File internal = new File(getFilesDir(), LAST_CRASH_FILE);
            try (FileOutputStream fos = new FileOutputStream(internal)) {
                fos.write(bytes); fos.flush(); try { fos.getFD().sync(); } catch (Throwable ignoredSync) {}
            }
            savedPath = internal.getAbsolutePath();
        } catch (Throwable ignored) {
        }
        try {
            File extBase = getExternalFilesDir(null);
            if (extBase != null) {
                File dir = new File(extBase, "crash_logs");
                dir.mkdirs();
                String stamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
                File out = new File(dir, "StudioRblx_crash_" + stamp + ".txt");
                try (FileOutputStream fos = new FileOutputStream(out)) {
                    fos.write(bytes); fos.flush(); try { fos.getFD().sync(); } catch (Throwable ignoredSync) {}
                }
                savedPath = out.getAbsolutePath();
            }
        } catch (Throwable ignored) {
        }
        return savedPath;
    }

    private boolean isCrashProcess() {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                String pn = Application.getProcessName();
                return pn != null && pn.endsWith(":crash");
            }
        } catch (Throwable ignored) {
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (isCrashProcess()) {
            return;
        }
        final Thread.UncaughtExceptionHandler prev = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                try {
                    // Save raw stack trace FIRST so even if logcat/OutputLog fails or OS kills process, reopen ALWAYS shows crash screen
                    StringWriter sw0 = new StringWriter();
                    e.printStackTrace(new PrintWriter(sw0));
                    String quickReport = "=== StudioRblx Crash ===\nThread: " + (t != null ? t.getName() : "unknown") + "\n\n" + sw0.toString();
                    String savedPath = saveCrashToFiles(quickReport);
                    String report = quickReport;
                    try {
                        report = buildFullCrashReport(t, e);
                        savedPath = saveCrashToFiles(report);
                    } catch (Throwable ignored2) {
                    }
                    Intent i = new Intent(StudioApp.this, CrashActivity.class);
                    i.putExtra(CrashActivity.EXTRA_TRACE, report);
                    i.putExtra(CrashActivity.EXTRA_SAVED_PATH, savedPath);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    try { Thread.sleep(500L); } catch (InterruptedException ignored) {}
                    Process.killProcess(Process.myPid());
                    System.exit(10);
                } catch (Throwable ignored) {
                    if (prev != null) prev.uncaughtException(t, e);
                }
            }
        });
        Settings.INSTANCE.init(this);
        Ui.INSTANCE.applyTheme(Settings.INSTANCE.getTheme());
    }
}
