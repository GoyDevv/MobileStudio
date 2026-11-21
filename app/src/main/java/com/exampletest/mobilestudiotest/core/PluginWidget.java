package com.exampletest.mobilestudiotest.core;

import com.exampletest.mobilestudiotest.lua.LuaRuntime;
import com.exampletest.mobilestudiotest.model.Instance;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Studio.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class PluginWidget {
    private final Instance inst;
    private final LuaRuntime rt;

    public PluginWidget(Instance inst, LuaRuntime rt) {
        Intrinsics.checkNotNullParameter(inst, "inst");
        Intrinsics.checkNotNullParameter(rt, "rt");
        this.inst = inst;
        this.rt = rt;
    }

    public final Instance getInst() {
        return this.inst;
    }

    public final LuaRuntime getRt() {
        return this.rt;
    }
}
