package com.goydevv.studrblx;

import android.app.Application;
import com.goydevv.studrblx.core.Settings;
import com.goydevv.studrblx.core.Studio;
import com.goydevv.studrblx.ui.Ui;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: StudioApp.kt */
/* JADX INFO: loaded from: classes7.dex */
public final class StudioApp extends Application {
    private boolean placeOpened;
    private boolean started;

    /* JADX INFO: renamed from: studio$delegate, reason: from kotlin metadata */
    private final Lazy studio = LazyKt.lazy(new Function0() { // from class: com.goydevv.studrblx.StudioApp$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
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

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        final Thread.UncaughtExceptionHandler prev = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                try {
                    java.io.StringWriter sw = new java.io.StringWriter();
                    e.printStackTrace(new java.io.PrintWriter(sw));
                    android.content.Intent i = new android.content.Intent(StudioApp.this, CrashActivity.class);
                    i.putExtra(CrashActivity.EXTRA_TRACE, "Thread: " + t.getName() + "\n\n" + sw.toString());
                    i.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    android.os.Process.killProcess(android.os.Process.myPid());
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
