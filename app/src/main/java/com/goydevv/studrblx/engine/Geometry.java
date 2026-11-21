package com.goydevv.studrblx.engine;

import com.goydevv.studrblx.model.CFrame;
import com.goydevv.studrblx.model.EnumValue;
import com.goydevv.studrblx.model.Instance;
import com.goydevv.studrblx.model.PartData;
import com.goydevv.studrblx.model.Vector3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Geometry.kt */
/* JADX INFO: loaded from: classes7.dex */
public final class Geometry {
    private static final List<Vector3> ROUND_AXES;
    public static final Geometry INSTANCE = new Geometry();
    private static final float H = 0.5f;

    private Geometry() {
    }

    static {
        Iterable iterableListOf = CollectionsKt.listOf(new Vector3[]{new Vector3(1.0f, 0.0f, 0.0f), new Vector3(0.0f, 1.0f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f), new Vector3(1.0f, 1.0f, 0.0f), new Vector3(1.0f, -1.0f, 0.0f), new Vector3(1.0f, 0.0f, 1.0f), new Vector3(1.0f, 0.0f, -1.0f), new Vector3(0.0f, 1.0f, 1.0f), new Vector3(0.0f, 1.0f, -1.0f), new Vector3(1.0f, 1.0f, 1.0f), new Vector3(1.0f, 1.0f, -1.0f), new Vector3(1.0f, -1.0f, 1.0f), new Vector3(-1.0f, 1.0f, 1.0f)});
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableListOf, 10));
        Iterator it = iterableListOf.iterator();
        while (it.hasNext()) {
            arrayList.add(((Vector3) it.next()).getUnit());
        }
        ROUND_AXES = (List) arrayList;
    }

    private final List<Pair<Vector3, Float>> planes(int shape, Vector3 s) {
        float hx = s.getX() * H;
        float hy = s.getY() * H;
        float hz = s.getZ() * H;
        List<Pair<Vector3, Float>> listListOf = CollectionsKt.listOf(new Pair[]{TuplesKt.to(Vector3.INSTANCE.getX(), Float.valueOf(hx)), TuplesKt.to(Vector3.INSTANCE.getX().unaryMinus(), Float.valueOf(hx)), TuplesKt.to(Vector3.INSTANCE.getY(), Float.valueOf(hy)), TuplesKt.to(Vector3.INSTANCE.getY().unaryMinus(), Float.valueOf(hy)), TuplesKt.to(Vector3.INSTANCE.getZ(), Float.valueOf(hz)), TuplesKt.to(Vector3.INSTANCE.getZ().unaryMinus(), Float.valueOf(hz))});
        if (shape == 3) {
            Vector3 n = new Vector3(0.0f, s.getZ(), -s.getY()).getUnit();
            return CollectionsKt.listOf(new Pair[]{TuplesKt.to(Vector3.INSTANCE.getX(), Float.valueOf(hx)), TuplesKt.to(Vector3.INSTANCE.getX().unaryMinus(), Float.valueOf(hx)), TuplesKt.to(Vector3.INSTANCE.getY().unaryMinus(), Float.valueOf(hy)), TuplesKt.to(Vector3.INSTANCE.getZ(), Float.valueOf(hz)), TuplesKt.to(n, Float.valueOf(0.0f))});
        }
        return listListOf;
    }

    public final Pair<Float, Vector3> rayPart(Vector3 o, Vector3 d, Instance part) {
        CollisionMeshes.Mesh meshForPart$default;
        Intrinsics.checkNotNullParameter(o, "o");
        Intrinsics.checkNotNullParameter(d, "d");
        Intrinsics.checkNotNullParameter(part, "part");
        if (CollisionMeshes.INSTANCE.hasGeometry(part) && (meshForPart$default = CollisionMeshes.forPart$default(CollisionMeshes.INSTANCE, part, false, 2, null)) != null) {
            return CollisionMeshes.INSTANCE.ray(meshForPart$default, o, d, 1000000.0f);
        }
        CFrame cf = PartData.INSTANCE.cframe(part);
        Vector3 size = PartData.INSTANCE.size(part);
        int shape = PartData.INSTANCE.shape(part);
        Vector3 lo = cf.pointToObject(o);
        Vector3 ld = cf.vectorToObject(d);
        if (shape == 0) {
            float r = Math.min(size.getX(), Math.min(size.getY(), size.getZ())) * H;
            float b = lo.dot(ld);
            float c = lo.dot(lo) - (r * r);
            float disc = (b * b) - c;
            if (disc < 0.0f) {
                return null;
            }
            float sq = (float) Math.sqrt(disc);
            float t = (-b) - sq;
            if (t < 0.0f) {
                t = (-b) + sq;
            }
            if (t < 0.0f) {
                return null;
            }
            Vector3 hitLocal = lo.plus(ld.times(t));
            return TuplesKt.to(Float.valueOf(t), cf.vectorToWorld(hitLocal.getUnit()));
        }
        float tEnter = -3.4028235E38f;
        float tExit = Float.MAX_VALUE;
        Vector3 enterN = Vector3.INSTANCE.getY();
        for (Pair<Vector3, Float> pair : planes(shape, size)) {
            Vector3 n = pair.component1();
            float off = pair.component2().floatValue();
            float denom = n.dot(ld);
            float dist = off - n.dot(lo);
            if (Math.abs(denom) >= 1.0E-8f) {
                float t2 = dist / denom;
                if (denom < 0.0f) {
                    if (t2 > tEnter) {
                        tEnter = t2;
                        enterN = n;
                    }
                } else if (t2 < tExit) {
                    tExit = t2;
                }
                if (tEnter > tExit) {
                    return null;
                }
            } else if (dist < 0.0f) {
                return null;
            }
        }
        if (tExit < 0.0f) {
            return null;
        }
        return TuplesKt.to(Float.valueOf(tEnter >= 0.0f ? tEnter : 0.0f), cf.vectorToWorld(enterN));
    }

    public final RayHit raycast(List<? extends Instance> parts, Vector3 o, Vector3 d, float maxDist, Function1<? super Instance, Boolean> filter) {
        Pair<Float, Vector3> pairRayPart;
        Intrinsics.checkNotNullParameter(parts, "parts");
        Intrinsics.checkNotNullParameter(o, "o");
        Intrinsics.checkNotNullParameter(d, "d");
        Intrinsics.checkNotNullParameter(filter, "filter");
        RayHit best = null;
        for (Instance p : parts) {
            if (filter.invoke(p).booleanValue() && (pairRayPart = rayPart(o, d, p)) != null && pairRayPart.getFirst().floatValue() <= maxDist && (best == null || pairRayPart.getFirst().floatValue() < best.getT())) {
                best = new RayHit(p, pairRayPart.getFirst().floatValue(), o.plus(d.times(pairRayPart.getFirst().floatValue())), pairRayPart.getSecond());
            }
        }
        return best;
    }

    public final List<Vector3> corners(CFrame cf, Vector3 size) {
        Intrinsics.checkNotNullParameter(cf, "cf");
        Intrinsics.checkNotNullParameter(size, "size");
        Vector3 h = size.times(H);
        ArrayList arrayList = new ArrayList(8);
        for (int i = 0; i < 8; i++) {
            int i2 = i;
            arrayList.add(cf.times(new Vector3((i2 & 1) != 0 ? h.getX() : -h.getX(), (i2 & 2) != 0 ? h.getY() : -h.getY(), (i2 & 4) != 0 ? h.getZ() : -h.getZ())));
        }
        return arrayList;
    }

    public final Pair<Vector3, Vector3> worldAabb(CFrame cf, Vector3 size) {
        Intrinsics.checkNotNullParameter(cf, "cf");
        Intrinsics.checkNotNullParameter(size, "size");
        Vector3 h = size.times(H);
        Vector3 ex = new Vector3((Math.abs(cf.getR00()) * h.getX()) + (Math.abs(cf.getR01()) * h.getY()) + (Math.abs(cf.getR02()) * h.getZ()), (Math.abs(cf.getR10()) * h.getX()) + (Math.abs(cf.getR11()) * h.getY()) + (Math.abs(cf.getR12()) * h.getZ()), (Math.abs(cf.getR20()) * h.getX()) + (Math.abs(cf.getR21()) * h.getY()) + (Math.abs(cf.getR22()) * h.getZ()));
        Vector3 p = cf.getPosition();
        return TuplesKt.to(p.minus(ex), p.plus(ex));
    }

    public final Pair<Vector3, Vector3> partAabb(Instance p) {
        Intrinsics.checkNotNullParameter(p, "p");
        return worldAabb(PartData.INSTANCE.cframe(p), PartData.INSTANCE.size(p));
    }

    public final Pair<Vector3, Vector3> boundsOf(List<? extends Instance> parts) {
        Intrinsics.checkNotNullParameter(parts, "parts");
        if (parts.isEmpty()) {
            return null;
        }
        Vector3 mn = new Vector3(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        Vector3 mx = mn.unaryMinus();
        for (Instance p : parts) {
            Pair<Vector3, Vector3> pairPartAabb = partAabb(p);
            Vector3 a = pairPartAabb.component1();
            Vector3 b = pairPartAabb.component2();
            mn = mn.min(a);
            mx = mx.max(b);
        }
        return TuplesKt.to(mn, mx);
    }

    public final Pair<Vector3, Float> contact(ConvexShape shape, Vector3 lo, Vector3 hi, Instance part) {
        CollisionMeshes.Mesh meshForPart$default;
        Intrinsics.checkNotNullParameter(shape, "shape");
        Intrinsics.checkNotNullParameter(lo, "lo");
        Intrinsics.checkNotNullParameter(hi, "hi");
        Intrinsics.checkNotNullParameter(part, "part");
        if (CollisionMeshes.INSTANCE.hasGeometry(part) && (meshForPart$default = CollisionMeshes.forPart$default(CollisionMeshes.INSTANCE, part, false, 2, null)) != null) {
            return CollisionMeshes.INSTANCE.push(meshForPart$default, shape, lo, hi);
        }
        Vector3 m = sat(shape, shapeOf$default(this, part, null, 2, null));
        if (m == null) {
            return null;
        }
        return TuplesKt.to(m, Float.valueOf(partAabb(part).getSecond().getY()));
    }

    public static /* synthetic */ ConvexShape shapeOf$default(Geometry geometry, Instance instance, CFrame cFrame, int i, Object obj) {
        if ((i & 2) != 0) {
            cFrame = null;
        }
        return geometry.shapeOf(instance, cFrame);
    }

    public final ConvexShape shapeOf(Instance part, CFrame cfOverride) {
        Intrinsics.checkNotNullParameter(part, "part");
        CFrame cf = cfOverride == null ? PartData.INSTANCE.cframe(part) : cfOverride;
        Vector3 s = PartData.INSTANCE.size(part);
        Vector3 h = s.times(H);
        int i = 0;
        List ax = CollectionsKt.listOf(new Vector3[]{cf.getRightVector(), cf.getUpVector(), cf.getBackVector()});
        Object obj = part.get("shape");
        if (obj == null) {
            obj = part.get("Shape");
        }
        EnumValue enumValue = obj instanceof EnumValue ? (EnumValue) obj : null;
        Integer numValueOf = enumValue != null ? Integer.valueOf(enumValue.getValue()) : null;
        if (numValueOf != null && numValueOf.intValue() == 0) {
            Vector3 position = cf.getPosition();
            float fMin = Math.min(s.getX(), Math.min(s.getY(), s.getZ())) * H;
            Iterable iterable = ROUND_AXES;
            Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(cf.vectorToWorld((Vector3) it.next()));
            }
            return new SphereShape(position, fMin, (List) arrayList);
        }
        if (numValueOf == null || numValueOf.intValue() != 2) {
            if (PartData.INSTANCE.shape(part) == 3) {
                Iterable local = CollectionsKt.listOf(new Vector3[]{new Vector3(-h.getX(), -h.getY(), -h.getZ()), new Vector3(h.getX(), -h.getY(), -h.getZ()), new Vector3(-h.getX(), -h.getY(), h.getZ()), new Vector3(h.getX(), -h.getY(), h.getZ()), new Vector3(-h.getX(), h.getY(), h.getZ()), new Vector3(h.getX(), h.getY(), h.getZ())});
                Vector3 slopeN = cf.vectorToWorld(new Vector3(0.0f, s.getZ(), -s.getY()).getUnit());
                Vector3 slopeD = cf.vectorToWorld(new Vector3(0.0f, s.getY(), s.getZ()).getUnit());
                Iterable iterable2 = local;
                Collection arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable2, 10));
                Iterator it2 = iterable2.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(cf.times((Vector3) it2.next()));
                }
                return new ConvexShape((List) arrayList2, CollectionsKt.plus((Collection<? extends Vector3>) ax, slopeN), CollectionsKt.plus((Collection<? extends Vector3>) ax, slopeD));
            }
            return new ConvexShape(corners(cf, s), ax, ax);
        }
        float r = Math.min(s.getY(), s.getZ()) * H;
        char c = '\f';
        Iterable iterableUntil = RangesKt.until(0, 12);
        Collection arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableUntil, 10));
        Iterator<Integer> it3 = iterableUntil.iterator();
        while (it3.hasNext()) {
            double dNextInt = (((double) ((IntIterator) it3).nextInt()) * 6.283185307179586d) / 12.0d;
            arrayList3.add(new Vector3(0.0f, (float) (((double) r) * Math.cos(dNextInt)), (float) (((double) r) * Math.sin(dNextInt))));
        }
        int i2 = i;
        List ring = (List) arrayList3;
        List<Vector3> list = ring;
        Collection arrayList4 = new ArrayList();
        for (Vector3 vector3 : list) {
            Vector3 h2 = h;
            Vector3[] vector3Arr = new Vector3[2];
            vector3Arr[i2] = cf.times(new Vector3(-h2.getX(), vector3.getY(), vector3.getZ()));
            vector3Arr[1] = cf.times(new Vector3(h2.getX(), vector3.getY(), vector3.getZ()));
            CollectionsKt.addAll(arrayList4, CollectionsKt.listOf(vector3Arr));
            h = h2;
        }
        List verts = (List) arrayList4;
        Iterable iterableUntil2 = RangesKt.until(i2, 6);
        Collection arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableUntil2, 10));
        Iterable iterable3 = iterableUntil2;
        int i3 = 0;
        for (Iterator<Integer> it4 = iterable3.iterator(); it4.hasNext(); it4 = it4) {
            double dNextInt2 = ((((double) ((IntIterator) it4).nextInt()) + 0.5d) * 6.283185307179586d) / 12.0d;
            arrayList5.add(cf.vectorToWorld(new Vector3(0.0f, (float) Math.cos(dNextInt2), (float) Math.sin(dNextInt2))));
        }
        List sideN = (List) arrayList5;
        Iterable iterableUntil3 = RangesKt.until(0, 6);
        Collection arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableUntil3, 10));
        Iterator<Integer> it5 = iterableUntil3.iterator();
        while (it5.hasNext()) {
            int iNextInt = ((IntIterator) it5).nextInt();
            arrayList6.add(cf.vectorToWorld(((Vector3) ring.get((iNextInt + 1) % 12)).minus((Vector3) ring.get(iNextInt))).getUnit());
        }
        List sideE = (List) arrayList6;
        return new ConvexShape(verts, CollectionsKt.plus((Collection) CollectionsKt.listOf(cf.getRightVector()), (Iterable) sideN), CollectionsKt.plus((Collection) CollectionsKt.listOf(cf.getRightVector()), (Iterable) sideE));
    }

    public final ConvexShape aabbShape(Vector3 center, Vector3 half) {
        Intrinsics.checkNotNullParameter(center, "center");
        Intrinsics.checkNotNullParameter(half, "half");
        ArrayList arrayList = new ArrayList(8);
        for (int i = 0; i < 8; i++) {
            int i2 = i;
            arrayList.add(center.plus(new Vector3((i2 & 1) != 0 ? half.getX() : -half.getX(), (i2 & 2) != 0 ? half.getY() : -half.getY(), (i2 & 4) != 0 ? half.getZ() : -half.getZ())));
        }
        ArrayList v = arrayList;
        List ax = CollectionsKt.listOf(new Vector3[]{Vector3.INSTANCE.getX(), Vector3.INSTANCE.getY(), Vector3.INSTANCE.getZ()});
        return new ConvexShape(v, ax, ax);
    }

    public final Vector3 sat(ConvexShape a, ConvexShape b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        ArrayList axes = new ArrayList(a.getNormals().size() + b.getNormals().size() + 16);
        axes.addAll(a.getNormals());
        axes.addAll(b.getNormals());
        for (Vector3 ea : a.getEdges()) {
            for (Vector3 eb : b.getEdges()) {
                Vector3 c = ea.cross(eb);
                if (c.getMagnitude() > 1.0E-4f) {
                    axes.add(c.getUnit());
                }
            }
        }
        float best = Float.MAX_VALUE;
        Vector3 bestAxis = Vector3.INSTANCE.getY();
        Iterator it = axes.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Object next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Vector3 ax = (Vector3) next;
            Pair<Float, Float> pairProject = a.project(ax);
            float a0 = pairProject.component1().floatValue();
            float a1 = pairProject.component2().floatValue();
            Pair<Float, Float> pairProject2 = b.project(ax);
            float b0 = pairProject2.component1().floatValue();
            float b1 = pairProject2.component2().floatValue();
            float o = Math.min(a1 - b0, b1 - a0);
            if (o <= 0.0f) {
                return null;
            }
            if (o < best) {
                best = o;
                bestAxis = ax;
            }
        }
        if (a.getCenter().minus(b.getCenter()).dot(bestAxis) < 0.0f) {
            bestAxis = bestAxis.unaryMinus();
        }
        return bestAxis.times(best);
    }

    public static /* synthetic */ boolean aabbOverlap$default(Geometry geometry, Vector3 vector3, Vector3 vector4, Vector3 vector5, Vector3 vector6, float f, int i, Object obj) {
        if ((i & 16) != 0) {
            f = 0.0f;
        }
        return geometry.aabbOverlap(vector3, vector4, vector5, vector6, f);
    }

    public final boolean aabbOverlap(Vector3 a0, Vector3 a1, Vector3 b0, Vector3 b1, float pad) {
        Intrinsics.checkNotNullParameter(a0, "a0");
        Intrinsics.checkNotNullParameter(a1, "a1");
        Intrinsics.checkNotNullParameter(b0, "b0");
        Intrinsics.checkNotNullParameter(b1, "b1");
        return a0.getX() <= b1.getX() + pad && a1.getX() >= b0.getX() - pad && a0.getY() <= b1.getY() + pad && a1.getY() >= b0.getY() - pad && a0.getZ() <= b1.getZ() + pad && a1.getZ() >= b0.getZ() - pad;
    }

    public final float maxOf3(Vector3 v) {
        Intrinsics.checkNotNullParameter(v, "v");
        return Math.max(v.getX(), Math.max(v.getY(), v.getZ()));
    }
}
