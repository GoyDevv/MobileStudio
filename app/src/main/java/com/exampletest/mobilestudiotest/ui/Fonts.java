package com.exampletest.mobilestudiotest.ui;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import com.exampletest.mobilestudiotest.model.EnumValue;
import com.exampletest.mobilestudiotest.model.FontValue;
import com.exampletest.mobilestudiotest.model.Instance;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Fonts.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class Fonts {
    private static AssetManager assets;
    public static final Fonts INSTANCE = new Fonts();
    private static final HashMap<String, Typeface> cache = new HashMap<>();
    private static final HashMap<Object, Face> faceCache = new HashMap<>();

    private Fonts() {
    }

    public final void init(AssetManager a) {
        Intrinsics.checkNotNullParameter(a, "a");
        assets = a;
    }

    /* JADX INFO: compiled from: Fonts.kt */
    public static final class Face {
        private final boolean fakeBold;
        private final boolean italic;
        private final Typeface typeface;

        public Face(Typeface typeface, boolean fakeBold, boolean italic) {
            Intrinsics.checkNotNullParameter(typeface, "typeface");
            this.typeface = typeface;
            this.fakeBold = fakeBold;
            this.italic = italic;
        }

        public final boolean getFakeBold() {
            return this.fakeBold;
        }

        public final boolean getItalic() {
            return this.italic;
        }

        public final Typeface getTypeface() {
            return this.typeface;
        }
    }

    private final Typeface asset(String name) {
        Typeface typefaceCreateFromAsset;
        synchronized (cache) {
            Typeface typeface = cache.get(name);
            if (typeface != null) {
                return typeface;
            }
            try {
                AssetManager assetManager = assets;
                typefaceCreateFromAsset = assetManager != null ? Typeface.createFromAsset(assetManager, "fonts/" + name + ".ttf") : null;
            } catch (Exception e) {
                typefaceCreateFromAsset = null;
            }
            if (typefaceCreateFromAsset == null) {
                return null;
            }
            cache.put(name, typefaceCreateFromAsset);
            return typefaceCreateFromAsset;
        }
    }

    private final Typeface system(int weight, boolean italic) {
        if (Build.VERSION.SDK_INT >= 28) {
            Typeface typefaceCreate = Typeface.create(Typeface.SANS_SERIF, RangesKt.coerceIn(weight, 100, 900), italic);
            Intrinsics.checkNotNullExpressionValue(typefaceCreate, "create(...)");
            return typefaceCreate;
        }
        Typeface typefaceCreate2 = Typeface.create(Typeface.SANS_SERIF, (weight >= 600 ? 1 : 0) | (italic ? 2 : 0));
        Intrinsics.checkNotNullExpressionValue(typefaceCreate2, "create(...)");
        return typefaceCreate2;
    }

    private final Face weighted(int weight, boolean italic, List<Pair<Integer, String>> files) {
        Object next;
        Iterator it = files.iterator();
        if (it.hasNext()) {
            next = it.next();
            if (it.hasNext()) {
                int iAbs = Math.abs(((Number) ((Pair) next).getFirst()).intValue() - weight);
                do {
                    Object next2 = it.next();
                    int iAbs2 = Math.abs(((Number) ((Pair) next2).getFirst()).intValue() - weight);
                    if (iAbs > iAbs2) {
                        next = next2;
                        iAbs = iAbs2;
                    }
                } while (it.hasNext());
            }
        } else {
            next = null;
        }
        Intrinsics.checkNotNull(next);
        Pair best = (Pair) next;
        Typeface tf = asset((String) best.getSecond());
        boolean z = false;
        if (tf == null) {
            return new Face(system(weight, italic), false, italic);
        }
        if (weight >= 600 && ((Number) best.getFirst()).intValue() < 600) {
            z = true;
        }
        return new Face(tf, z, italic);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0266, code lost:
    
        if (r0.equals("sourcesans") != false) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0268, code lost:
    
        if (r15 == false) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x02b0, code lost:
    
        if (r0.equals("merriweather") != false) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x02c0, code lost:
    
        if (r0.equals("gotham") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x02da, code lost:
    
        if (r0.equals("montserrat") != false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0318, code lost:
    
        if (r0.equals("buildersans") != false) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x032b, code lost:
    
        if (r0.equals("comicneue") != false) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0059, code lost:
    
        if (r0.equals("accanthisadfstd") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0355, code lost:
    
        if (r0.equals("michroma") != false) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0364, code lost:
    
        if (r0.equals("fondamento") != false) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:?, code lost:
    
        return weighted(r9, r15, kotlin.collections.CollectionsKt.listOf((java.lang.Object[]) new kotlin.Pair[]{kotlin.TuplesKt.to(400, "Arimo-Regular"), kotlin.TuplesKt.to(r10, "Arimo-Bold")}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0063, code lost:
    
        if (r0.equals("gothamssm") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:?, code lost:
    
        return weighted(r14, r15, kotlin.collections.CollectionsKt.listOf((java.lang.Object[]) new kotlin.Pair[]{kotlin.TuplesKt.to(400, "RobotoCondensed"), kotlin.TuplesKt.to(r10, "RobotoCondensed-Bold")}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:?, code lost:
    
        return forFamily$one(r14, r15, "SourceSans-Italic");
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:?, code lost:
    
        return weighted(r14, false, kotlin.collections.CollectionsKt.listOf((java.lang.Object[]) new kotlin.Pair[]{kotlin.TuplesKt.to(java.lang.Integer.valueOf(300), "SourceSans-Light"), kotlin.TuplesKt.to(400, "SourceSans-Regular"), kotlin.TuplesKt.to(600, "SourceSans-Semibold"), kotlin.TuplesKt.to(r10, "SourceSans-Bold")}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:?, code lost:
    
        return forFamily$one(r14, r15, "Merriweather");
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:?, code lost:
    
        return weighted(r14, r15, kotlin.collections.CollectionsKt.listOf((java.lang.Object[]) new kotlin.Pair[]{kotlin.TuplesKt.to(400, "Montserrat-Regular"), kotlin.TuplesKt.to(500, "Montserrat-Medium"), kotlin.TuplesKt.to(r10, "Montserrat-Bold"), kotlin.TuplesKt.to(900, "Montserrat-Black")}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:?, code lost:
    
        return new com.exampletest.mobilestudiotest.ui.Fonts.Face(system(r14, r15), false, r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:?, code lost:
    
        return forFamily$one(r14, r15, "ComicNeue");
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:?, code lost:
    
        return forFamily$one(r14, r15, "Michroma");
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:?, code lost:
    
        return forFamily$one(r14, r15, "Fondamento");
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0090, code lost:
    
        if (r0.equals("balthazar") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x009a, code lost:
    
        if (r0.equals("highwaygothic") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00d4, code lost:
    
        if (r0.equals("sourcesanspro") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00de, code lost:
    
        if (r0.equals("comicneueangular") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00f8, code lost:
    
        if (r0.equals("arimo") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0102, code lost:
    
        if (r0.equals("arial") != false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0108, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(r0, "legacyarial") != false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x010a, code lost:
    
        r9 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x013d, code lost:
    
        if (r0.equals("guru") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x003f, code lost:
    
        if (r0.equals("legacyarial") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01ab, code lost:
    
        if (r0.equals("zekton") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01e9, code lost:
    
        if (r0.equals("roboto") == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01f3, code lost:
    
        if (r0.equals("robotocondensed") != false) goto L91;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.exampletest.mobilestudiotest.ui.Fonts.Face forFamily(java.lang.String r13, int r14, boolean r15) {
        /*
            Method dump skipped, instruction units count: 1112
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exampletest.mobilestudiotest.ui.Fonts.forFamily(java.lang.String, int, boolean):com.exampletest.mobilestudiotest.ui.Fonts$Face");
    }

    private static final Face forFamily$one(int $weight, boolean $italic, String file) {
        Typeface typefaceAsset = INSTANCE.asset(file);
        if (typefaceAsset != null) {
            return new Face(typefaceAsset, $weight >= 600, $italic);
        }
        return new Face(INSTANCE.system($weight, $italic), false, $italic);
    }

    private final Triple<String, Integer, Boolean> fromEnum(int v) {
        Integer numValueOf = Integer.valueOf(700);
        switch (v) {
            case 0:
                return new Triple<>("LegacyArial", numValueOf, false);
            case 1:
                return new Triple<>("Arial", 400, false);
            case 2:
                return new Triple<>("Arial", numValueOf, false);
            case 3:
                return new Triple<>("SourceSansPro", 400, false);
            case 4:
                return new Triple<>("SourceSansPro", numValueOf, false);
            case 5:
                return new Triple<>("SourceSansPro", Integer.valueOf(300), false);
            case 6:
                return new Triple<>("SourceSansPro", 400, true);
            case 7:
                return new Triple<>("AccanthisADFStd", 400, false);
            case 8:
                return new Triple<>("Merriweather", 400, false);
            case 9:
                return new Triple<>("ComicNeueAngular", numValueOf, false);
            case 10:
                return new Triple<>("Inconsolata", 400, false);
            case 11:
                return new Triple<>("HighwayGothic", 400, false);
            case 12:
                return new Triple<>("Zekton", 400, false);
            case 13:
                return new Triple<>("PressStart2P", 400, false);
            case 14:
                return new Triple<>("Guru", 400, false);
            case 15:
                return new Triple<>("Balthazar", 400, false);
            case 16:
                return new Triple<>("SourceSansPro", 600, false);
            case 17:
                return new Triple<>("GothamSSm", 400, false);
            case 18:
                return new Triple<>("GothamSSm", 500, false);
            case 19:
                return new Triple<>("GothamSSm", numValueOf, false);
            case 20:
                return new Triple<>("GothamSSm", 900, false);
            case 21:
                return new Triple<>("AmaticSC", 400, false);
            case 22:
                return new Triple<>("Bangers", 400, false);
            case 23:
                return new Triple<>("Creepster", 400, false);
            case 24:
                return new Triple<>("DenkOne", 400, false);
            case 25:
                return new Triple<>("Fondamento", 400, false);
            case 26:
                return new Triple<>("FredokaOne", 400, false);
            case 27:
                return new Triple<>("GrenzeGotisch", 400, false);
            case 28:
                return new Triple<>("IndieFlower", 400, false);
            case 29:
                return new Triple<>("JosefinSans", 400, false);
            case 30:
                return new Triple<>("Jura", 400, false);
            case 31:
                return new Triple<>("Kalam", 400, false);
            case 32:
                return new Triple<>("LuckiestGuy", 400, false);
            case 33:
                return new Triple<>("Merriweather", 400, false);
            case 34:
                return new Triple<>("Michroma", 400, false);
            case 35:
                return new Triple<>("Nunito", 400, false);
            case 36:
                return new Triple<>("Oswald", 400, false);
            case 37:
                return new Triple<>("PatrickHand", 400, false);
            case 38:
                return new Triple<>("PermanentMarker", 400, false);
            case 39:
                return new Triple<>("Roboto", 400, false);
            case 40:
                return new Triple<>("RobotoCondensed", 400, false);
            case 41:
                return new Triple<>("RobotoMono", 400, false);
            case 42:
                return new Triple<>("Sarpanch", 400, false);
            case 43:
                return new Triple<>("SpecialElite", 400, false);
            case 44:
                return new Triple<>("TitilliumWeb", 400, false);
            case 45:
                return new Triple<>("Ubuntu", 400, false);
            case 46:
                return new Triple<>("BuilderSans", 400, false);
            case 47:
                return new Triple<>("BuilderSans", 500, false);
            case 48:
                return new Triple<>("BuilderSans", numValueOf, false);
            case 49:
                return new Triple<>("BuilderSans", 800, false);
            case 50:
                return new Triple<>("Arimo", 400, false);
            case 51:
                return new Triple<>("Arimo", numValueOf, false);
            default:
                return new Triple<>("SourceSansPro", 400, false);
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x006e A[Catch: all -> 0x00a7, TryCatch #0 {, blocks: (B:18:0x0035, B:24:0x0045, B:29:0x0058, B:32:0x0069, B:34:0x009c, B:33:0x006e), top: B:40:0x0035 }] */
    public final Face of(Instance c) {
        Object key;
        Face faceForFamily;
        Intrinsics.checkNotNullParameter(c, "c");
        Object obj = c.get("FontFace");
        FontValue ff = obj instanceof FontValue ? (FontValue) obj : null;
        if (ff == null) {
            Object obj2 = c.get("Font");
            EnumValue enumValue = obj2 instanceof EnumValue ? (EnumValue) obj2 : null;
            key = Integer.valueOf(enumValue != null ? enumValue.getValue() : 3);
        } else {
            key = ff;
        }
        synchronized (faceCache) {
            Face face = faceCache.get(key);
            if (face != null) {
                return face;
            }
            if (ff != null) {
                if (ff.getFamily().length() > 0) {
                    faceForFamily = INSTANCE.forFamily(ff.getFamily(), ff.getWeight(), ff.getStyle() == 1);
                } else {
                    Triple<String, Integer, Boolean> tripleFromEnum = INSTANCE.fromEnum(((Integer) key).intValue());
                    faceForFamily = INSTANCE.forFamily(tripleFromEnum.component1(), tripleFromEnum.component2().intValue(), tripleFromEnum.component3().booleanValue());
                }
            } else {
                Triple<String, Integer, Boolean> tripleFromEnum2 = INSTANCE.fromEnum(((Integer) key).intValue());
                faceForFamily = INSTANCE.forFamily(tripleFromEnum2.component1(), tripleFromEnum2.component2().intValue(), tripleFromEnum2.component3().booleanValue());
            }
            faceCache.put(key, faceForFamily);
            return faceForFamily;
        }
    }
}
