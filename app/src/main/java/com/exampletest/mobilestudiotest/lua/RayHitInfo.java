package com.exampletest.mobilestudiotest.lua;

import com.exampletest.mobilestudiotest.model.Instance;
import com.exampletest.mobilestudiotest.model.Vector3;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LuaTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class RayHitInfo {
    private final float distance;
    private final Instance inst;
    private final int material;
    private final Vector3 normal;
    private final Vector3 position;

    public RayHitInfo(Instance inst, Vector3 position, Vector3 normal, float distance, int material) {
        Intrinsics.checkNotNullParameter(inst, "inst");
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(normal, "normal");
        this.inst = inst;
        this.position = position;
        this.normal = normal;
        this.distance = distance;
        this.material = material;
    }

    public final float getDistance() {
        return this.distance;
    }

    public final Instance getInst() {
        return this.inst;
    }

    public final int getMaterial() {
        return this.material;
    }

    public final Vector3 getNormal() {
        return this.normal;
    }

    public final Vector3 getPosition() {
        return this.position;
    }
}
