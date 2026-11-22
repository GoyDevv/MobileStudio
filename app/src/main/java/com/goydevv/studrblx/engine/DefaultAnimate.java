package com.goydevv.studrblx.engine;

import com.goydevv.studrblx.model.CFrame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: KeyframeAnim.kt */
/* JADX INFO: loaded from: classes7.dex */
public final class DefaultAnimate {
    private String current;
    private ArrayList<AnimPlayer.Track> currentTracks;
    private String emoteName;
    private AnimPlayer.Track emoteTrack;
    private int idleLoops;
    private final AnimPlayer player = new AnimPlayer();
    private final boolean r15;
    private boolean reported;
    private final Random rnd;
    private final Map<String, List<Entry>> sets;
    private AnimPlayer.Track toolTrack;

    public DefaultAnimate(boolean r15) {
        this.r15 = r15;
        this.sets = this.r15 ? MapsKt.mapOf(TuplesKt.to("idle", CollectionsKt.listOf(new Entry[]{new Entry(507766666L, 9), new Entry(507766951L, 1)})), TuplesKt.to("walk", CollectionsKt.listOf(new Entry(913402848L, 10))), TuplesKt.to("run", CollectionsKt.listOf(new Entry(913376220L, 10))), TuplesKt.to("jump", CollectionsKt.listOf(new Entry(507765000L, 10))), TuplesKt.to("fall", CollectionsKt.listOf(new Entry(507767968L, 10))), TuplesKt.to("climb", CollectionsKt.listOf(new Entry(507765644L, 10))), TuplesKt.to("sit", CollectionsKt.listOf(new Entry(2506281703L, 10))), TuplesKt.to("toolnone", CollectionsKt.listOf(new Entry(507768375L, 10))), TuplesKt.to("wave", CollectionsKt.listOf(new Entry(507770239L, 10))), TuplesKt.to("point", CollectionsKt.listOf(new Entry(507770453L, 10))), TuplesKt.to("dance", CollectionsKt.listOf(new Entry[]{new Entry(507771019L, 10), new Entry(507771955L, 10), new Entry(507772104L, 10)})), TuplesKt.to("dance2", CollectionsKt.listOf(new Entry[]{new Entry(507776043L, 10), new Entry(507776720L, 10), new Entry(507776879L, 10)})), TuplesKt.to("dance3", CollectionsKt.listOf(new Entry[]{new Entry(507777268L, 10), new Entry(507777451L, 10), new Entry(507777623L, 10)})), TuplesKt.to("laugh", CollectionsKt.listOf(new Entry(507770818L, 10))), TuplesKt.to("cheer", CollectionsKt.listOf(new Entry(507770677L, 10)))) : MapsKt.mapOf(TuplesKt.to("idle", CollectionsKt.listOf(new Entry[]{new Entry(180435571L, 9), new Entry(180435792L, 1)})), TuplesKt.to("walk", CollectionsKt.listOf(new Entry(180426354L, 10))), TuplesKt.to("run", CollectionsKt.listOf(new Entry(180426354L, 10))), TuplesKt.to("jump", CollectionsKt.listOf(new Entry(125750702L, 10))), TuplesKt.to("fall", CollectionsKt.listOf(new Entry(180436148L, 10))), TuplesKt.to("climb", CollectionsKt.listOf(new Entry(180436334L, 10))), TuplesKt.to("sit", CollectionsKt.listOf(new Entry(178130996L, 10))), TuplesKt.to("toolnone", CollectionsKt.listOf(new Entry(182393478L, 10))), TuplesKt.to("wave", CollectionsKt.listOf(new Entry(128777973L, 10))), TuplesKt.to("point", CollectionsKt.listOf(new Entry(128853357L, 10))), TuplesKt.to("dance", CollectionsKt.listOf(new Entry[]{new Entry(182435998L, 10), new Entry(182491037L, 10), new Entry(182491065L, 10)})), TuplesKt.to("dance2", CollectionsKt.listOf(new Entry[]{new Entry(182436842L, 10), new Entry(182491248L, 10), new Entry(182491277L, 10)})), TuplesKt.to("dance3", CollectionsKt.listOf(new Entry[]{new Entry(182436935L, 10), new Entry(182491368L, 10), new Entry(182491423L, 10)})), TuplesKt.to("laugh", CollectionsKt.listOf(new Entry(129423131L, 10))), TuplesKt.to("cheer", CollectionsKt.listOf(new Entry(129423030L, 10))));
        this.rnd = new Random();
        this.current = "";
        this.currentTracks = new ArrayList<>();
        Iterator<List<Entry>> it = this.sets.values().iterator();
        while (it.hasNext()) {
            for (Entry e : it.next()) {
                AnimLibrary.INSTANCE.get(e.getId());
            }
        }
    }

    /* JADX INFO: compiled from: KeyframeAnim.kt */
    private static final /* data */ class Entry {
        private final long id;
        private final int weight;

        public static /* synthetic */ Entry copy$default(Entry entry, long j, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                j = entry.id;
            }
            if ((i2 & 2) != 0) {
                i = entry.weight;
            }
            return entry.copy(j, i);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final long component1() {
            return this.id;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int component2() {
            return this.weight;
        }

        public final Entry copy(long id, int weight) {
            return new Entry(id, weight);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) other;
            return this.id == entry.id && this.weight == entry.weight;
        }

        public int hashCode() {
            return (Long.hashCode(this.id) * 31) + Integer.hashCode(this.weight);
        }

        public String toString() {
            return "Entry(id=" + this.id + ", weight=" + this.weight + ")";
        }

        public Entry(long id, int weight) {
            this.id = id;
            this.weight = weight;
        }

        public final long getId() {
            return this.id;
        }

        public final int getWeight() {
            return this.weight;
        }
    }

    public final boolean getReady() {
        for (String key : new String[]{"idle", "walk"}) {
            List<Entry> list = this.sets.get(key);
            if (list != null) {
                for (Entry e : list) {
                    if (AnimLibrary.INSTANCE.get(e.getId()) != null) {
                        return true;
                    }
                }
            }
        }
        if (false) {
        boolean z = true;
        Iterable iterableListOf = CollectionsKt.listOf(new String[]{"idle", "walk", "jump", "fall"});
        if ((iterableListOf instanceof Collection) && ((Collection) iterableListOf).isEmpty()) {
            return true;
        }
        Iterator it = iterableListOf.iterator();
        while (it.hasNext()) {
            Iterable iterable = this.sets.get((String) it.next());
            Intrinsics.checkNotNull(iterable);
            Iterable iterable2 = iterable;
            if (!(iterable2 instanceof Collection) || !((Collection) iterable2).isEmpty()) {
                Iterator it2 = iterable2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z = true;
                        break;
                    }
                    if (!(AnimLibrary.INSTANCE.get(((Entry) it2.next()).getId()) != null)) {
                        z = false;
                        break;
                    }
                }
            }
            if (!z) {
                return false;
            }
            z = true;
        }
        return true;
        }
        return false;
    }

    private final AnimClip pick(String name) {
        List<Entry> list = this.sets.get(name);
        if (list == null) {
            return null;
        }
        Iterator it = list.iterator();
        int total = 0;
        while (it.hasNext()) {
            total += ((Entry) it.next()).getWeight();
        }
        int r = this.rnd.nextInt(RangesKt.coerceAtLeast(total, 1));
        for (Entry e : list) {
            r -= e.getWeight();
            if (r < 0) {
                return AnimLibrary.INSTANCE.get(e.getId());
            }
        }
        return AnimLibrary.INSTANCE.get(((Entry) CollectionsKt.first((List) list)).getId());
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00b7  */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final void playCore(String name, float fade) {
        if (Intrinsics.areEqual(name, this.current)) {
            return;
        }
        Iterator<AnimPlayer.Track> it = this.currentTracks.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            AnimPlayer.Track next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            AnimPlayer.Track t = next;
            this.player.stop(t, fade);
        }
        this.currentTracks = new ArrayList<>();
        this.current = name;
        if (this.r15 && Intrinsics.areEqual(name, "walk")) {
            AnimClip animClipPick = pick("walk");
            if (animClipPick != null) {
                this.currentTracks.add(AnimPlayer.play$default(this.player, animClipPick, "walk", fade, 0.0f, 0.0f, 0, 24, null));
            }
            AnimClip animClipPick2 = pick("run");
            if (animClipPick2 != null) {
                this.currentTracks.add(AnimPlayer.play$default(this.player, animClipPick2, "run", fade, 1.0E-4f, 0.0f, 0, 16, null));
                return;
            }
            return;
        }
        AnimClip clip = pick(name);
        if (clip == null) {
            return;
        }
        ArrayList<AnimPlayer.Track> arrayList = this.currentTracks;
        AnimPlayer.Track trackPlay$default = AnimPlayer.play$default(this.player, clip, name, fade, 0.0f, 0.0f, 0, 24, null);
        switch (name.hashCode()) {
            case 113886:
                if (name.equals("sit")) {
                    trackPlay$default.setLooped(!Intrinsics.areEqual(name, "jump"));
                }
                break;
            case 3135355:
                if (name.equals("fall")) {
                    trackPlay$default.setLooped(!Intrinsics.areEqual(name, "jump"));
                }
                break;
            case 3273774:
                if (name.equals("jump")) {
                    trackPlay$default.setLooped(!Intrinsics.areEqual(name, "jump"));
                }
                break;
        }
        arrayList.add(trackPlay$default);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:132:0x014b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:40:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:69:0x0136  */
    /* JADX WARN: Code duplicated, block: B:75:0x015d  */
    /* JADX WARN: Code duplicated, block: B:76:0x0160  */
    /* JADX WARN: Code duplicated, block: B:78:0x0164  */
    /* JADX WARN: Code duplicated, block: B:79:0x016b  */
    /* JADX WARN: Code duplicated, block: B:81:0x016f  */
    /* JADX WARN: Code duplicated, block: B:83:0x017b  */
    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r10v15 java.lang.Object, still in use, count: 2, list:
          (r10v15 java.lang.Object) from 0x0124: PHI (r10 I:??) = (r10v4 java.lang.Object), (r10v15 java.lang.Object) binds: [B:65:0x0123, B:130:0x0124] A[DONT_GENERATE, DONT_INLINE]
          (r10v15 java.lang.Object A[D('element\1' java.lang.Object)]) from 0x0115: CHECK_CAST (com.goydevv.studrblx.engine.AnimPlayer$Track) (r10v15 java.lang.Object A[D('element\1' java.lang.Object)])
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:164)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:129)
        	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:93)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:132)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:67)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:50)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:96)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:27)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:36)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:44)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    public final void step(float dt, java.lang.String state, float speed, boolean toolEquipped, java.lang.String emote, float jumpAnimTime) {
        if (!getReady()) {
            return;
        }
        // Emote overlay handling
        if (emote != null && emote.length() > 0) {
            if (!Intrinsics.areEqual(emote, this.emoteName)) {
                if (this.emoteTrack != null) {
                    this.player.stop(this.emoteTrack, 0.15f);
                    this.emoteTrack = null;
                }
                AnimClip ec = pick(emote);
                if (ec != null) {
                    this.emoteTrack = this.player.play(ec, "emote", 0.15f, 1.0f, 1.0f, 1);
                    this.emoteTrack.setLooped(emote.startsWith("dance"));
                }
                this.emoteName = emote;
            }
        } else if (this.emoteTrack != null) {
            this.player.stop(this.emoteTrack, 0.15f);
            this.emoteTrack = null;
            this.emoteName = null;
        }

        // Tool equipped overlay (official Roblox toolnone animation)
        if (toolEquipped) {
            if (this.toolTrack == null || this.toolTrack.getStopping()) {
                AnimClip tc = pick("toolnone");
                if (tc != null) {
                    this.toolTrack = this.player.play(tc, "tool", 0.1f, 1.0f, 1.0f, 2);
                    this.toolTrack.setLooped(true);
                }
            }
        } else if (this.toolTrack != null) {
            this.player.stop(this.toolTrack, 0.1f);
            this.toolTrack = null;
        }

        // Core locomotion state machine matching official Roblox Animate LocalScript
        if ("Dead".equals(state)) {
            if (!"".equals(this.current)) {
                for (AnimPlayer.Track t : this.currentTracks) {
                    this.player.stop(t, 0.1f);
                }
                this.currentTracks.clear();
                this.current = "";
            }
        } else if ("Jumping".equals(state) || jumpAnimTime > 0.0f) {
            playCore("jump", 0.03f);
        } else if ("FreeFall".equals(state) || "Freefall".equals(state)) {
            playCore("fall", 0.08f);
        } else if ("Climbing".equals(state)) {
            playCore("climb", 0.06f);
            float cSpd = speed < 0.1f ? 0.0f : (speed / 5.0f);
            for (AnimPlayer.Track t : this.currentTracks) {
                t.setSpeed(cSpd);
            }
        } else if ("Seated".equals(state)) {
            playCore("sit", 0.08f);
        } else if ("Running".equals(state) && speed > 0.20f) {
            playCore("walk", 0.06f);
            float scale = Math.max(0.15f, speed / 14.5f);
            if (this.r15) {
                // Official Roblox R15 walk/run blend formula
                float runW = Math.max(0.0001f, Math.min(1.0f, (speed - 6.0f) / 10.0f));
                float walkW = Math.max(0.0001f, 1.0f - runW);
                for (AnimPlayer.Track t : this.currentTracks) {
                    t.setSpeed(scale);
                    if ("run".equals(t.getTag())) {
                        t.setTargetWeight(runW);
                    } else if ("walk".equals(t.getTag())) {
                        t.setTargetWeight(walkW);
                    }
                }
            } else {
                for (AnimPlayer.Track t : this.currentTracks) {
                    t.setSpeed(scale);
                }
            }
        } else {
            playCore("idle", 0.08f);
        }
        this.player.step(dt);
    }

    public final CFrame transform(String part1Name) {
        Intrinsics.checkNotNullParameter(part1Name, "part1Name");
        if (!getReady()) {
            return null;
        }
        CFrame cFrameTransform = this.player.transform(part1Name);
        return cFrameTransform == null ? CFrame.INSTANCE.getIDENTITY() : cFrameTransform;
    }
}
