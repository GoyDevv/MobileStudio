package com.exampletest.mobilestudiotest;

import android.app.Application;
import com.exampletest.mobilestudiotest.core.Settings;
import com.exampletest.mobilestudiotest.core.Studio;
import com.exampletest.mobilestudiotest.ui.Ui;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: StudioApp.kt */
/* JADX INFO: loaded from: classes7.dex */
public final class StudioApp extends Application {
    private boolean placeOpened;
    private boolean started;

    /* JADX INFO: renamed from: studio$delegate, reason: from kotlin metadata */
    private final Lazy studio = LazyKt.lazy(new Function0() { // from class: com.exampletest.mobilestudiotest.StudioApp$$ExternalSyntheticLambda0
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
        Settings.INSTANCE.init(this);
        Ui.INSTANCE.applyTheme(Settings.INSTANCE.getTheme());
    }
}
