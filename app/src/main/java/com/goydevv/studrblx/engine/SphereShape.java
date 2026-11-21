package com.goydevv.studrblx.engine;

import com.goydevv.studrblx.model.Vector3;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Geometry.kt */
/* JADX INFO: loaded from: classes7.dex */
public final class SphereShape extends ConvexShape {
    private final float r;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SphereShape(Vector3 c, float r, List<Vector3> axes) {
        super(CollectionsKt.listOf(c), axes, CollectionsKt.emptyList());
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(axes, "axes");
        this.r = r;
    }

    public final float getR() {
        return this.r;
    }

    @Override // com.goydevv.studrblx.engine.ConvexShape
    public Pair<Float, Float> project(Vector3 axis) {
        Intrinsics.checkNotNullParameter(axis, "axis");
        float d = getCenter().dot(axis);
        float l = axis.getMagnitude();
        return TuplesKt.to(Float.valueOf(d - (this.r * l)), Float.valueOf((this.r * l) + d));
    }
}
