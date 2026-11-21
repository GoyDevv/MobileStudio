package com.exampletest.mobilestudiotest.format;

import android.util.Base64;
import android.util.Xml;
import com.exampletest.mobilestudiotest.model.CFrame;
import com.exampletest.mobilestudiotest.model.ClassDb;
import com.exampletest.mobilestudiotest.model.Instance;
import com.exampletest.mobilestudiotest.model.SharedStr;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: compiled from: RbxXmlReader.kt */
/* JADX INFO: loaded from: classes5.dex */
public final class RbxXmlReader {
    public static final RbxXmlReader INSTANCE = new RbxXmlReader();

    /* JADX INFO: compiled from: RbxXmlReader.kt */
    private static final class XNode {
        private final Map<String, String> attrs;
        private final ArrayList<XNode> children;
        private final String name;
        private final StringBuilder text;

        public XNode(String name, Map<String, String> attrs) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(attrs, "attrs");
            this.name = name;
            this.attrs = attrs;
            this.children = new ArrayList<>();
            this.text = new StringBuilder();
        }

        public final Map<String, String> getAttrs() {
            return this.attrs;
        }

        public final String getName() {
            return this.name;
        }

        public final ArrayList<XNode> getChildren() {
            return this.children;
        }

        public final StringBuilder getText() {
            return this.text;
        }

        public final XNode child(String n) {
            Object next;
            Intrinsics.checkNotNullParameter(n, "n");
            Iterator it = this.children.iterator();
            while (it.hasNext()) {
                next = it.next();
                if (StringsKt.equals(((XNode) next).name, n, true)) {
                    return (XNode) next;
                }
            }
            next = null;
            return (XNode) next;
        }

        public final String t(String n) {
            StringBuilder sb;
            String string;
            String string2;
            Intrinsics.checkNotNullParameter(n, "n");
            XNode xNodeChild = child(n);
            return (xNodeChild == null || (sb = xNodeChild.text) == null || (string = sb.toString()) == null || (string2 = StringsKt.trim((CharSequence) string).toString()) == null) ? "" : string2;
        }

        public final float f(String n) {
            Intrinsics.checkNotNullParameter(n, "n");
            Float floatOrNull = StringsKt.toFloatOrNull(t(n));
            if (floatOrNull != null) {
                return floatOrNull.floatValue();
            }
            return 0.0f;
        }
    }

    private RbxXmlReader() {
    }

    /* JADX WARN: Code duplicated, block: B:47:0x0180  */
    public final RbxDocument read(byte[] bytes) throws XmlPullParserException, RbxFormatException, IOException {
        Object next;
        String str;
        Instance instance;
        Iterable children;
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        XmlPullParser p = Xml.newPullParser();
        p.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
        p.setInput(new ByteArrayInputStream(bytes), null);
        Intrinsics.checkNotNull(p);
        XNode root = parseTree(p);
        if (root == null) {
            throw new RbxFormatException("Empty XML");
        }
        ArrayList warnings = new ArrayList();
        HashMap refs = new HashMap();
        ArrayList pending = new ArrayList();
        HashMap shared = new HashMap();
        ArrayList pendingShared = new ArrayList();
        LinkedHashMap meta = new LinkedHashMap();
        Iterator it = root.getChildren().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((XNode) next).getName(), "SharedStrings"));
        XNode xNode = (XNode) next;
        String str2 = "";
        if (xNode == null || (children = xNode.getChildren()) == null) {
            str = "";
        } else {
            Iterable<XNode> iterable = children;
            for (XNode xNode2 : iterable) {
                HashMap map = shared;
                XmlPullParser p2 = p;
                String str3 = xNode2.getAttrs().get("md5");
                if (str3 == null) {
                    str3 = str2;
                }
                RbxXmlReader rbxXmlReader = INSTANCE;
                String str4 = str2;
                String string = xNode2.getText().toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                map.put(str3, rbxXmlReader.decodeB64(string));
                p = p2;
                str2 = str4;
            }
            str = str2;
        }
        ArrayList roots = new ArrayList();
        Iterator<XNode> it2 = root.getChildren().iterator();
        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
        while (it2.hasNext()) {
            XNode next2 = it2.next();
            Intrinsics.checkNotNullExpressionValue(next2, "next(...)");
            XNode c = next2;
            String name = c.getName();
            if (Intrinsics.areEqual(name, "Item")) {
                read$build(roots, refs, pending, pendingShared, warnings, c, null);
            } else if (Intrinsics.areEqual(name, "Meta")) {
                LinkedHashMap linkedHashMap = meta;
                String str5 = c.getAttrs().get("name");
                if (str5 == null) {
                    str5 = str;
                }
                linkedHashMap.put(str5, c.getText().toString());
            }
        }
        Iterator it3 = pending.iterator();
        Intrinsics.checkNotNullExpressionValue(it3, "iterator(...)");
        while (it3.hasNext()) {
            Object next3 = it3.next();
            Intrinsics.checkNotNullExpressionValue(next3, "next(...)");
            Triple triple = (Triple) next3;
            Instance i = (Instance) triple.component1();
            String n = (String) triple.component2();
            String r = (String) triple.component3();
            LinkedHashMap<String, Object> props = i.getProps();
            if (Intrinsics.areEqual(r, "null")) {
                instance = null;
            } else if (r.length() == 0) {
                instance = null;
            } else {
                instance = (Instance) refs.get(r);
            }
            props.put(n, instance);
        }
        Iterator it4 = pendingShared.iterator();
        Intrinsics.checkNotNullExpressionValue(it4, "iterator(...)");
        while (it4.hasNext()) {
            Object next4 = it4.next();
            Intrinsics.checkNotNullExpressionValue(next4, "next(...)");
            Triple triple2 = (Triple) next4;
            Instance i2 = (Instance) triple2.component1();
            String n2 = (String) triple2.component2();
            String k = (String) triple2.component3();
            LinkedHashMap<String, Object> props2 = i2.getProps();
            byte[] bArr = (byte[]) shared.get(k);
            if (bArr == null) {
                bArr = new byte[0];
            }
            props2.put(n2, new SharedStr(bArr));
        }
        return new RbxDocument(roots, meta, warnings);
    }

    private static final void read$build(ArrayList<Instance> arrayList, HashMap<String, Instance> map, ArrayList<Triple<Instance, String, String>> arrayList2, ArrayList<Triple<Instance, String, String>> arrayList3, ArrayList<String> arrayList4, XNode node, Instance parent) {
        Iterable children;
        Iterable iterable = null;
        int i = 0;
        ArrayList<String> arrayList5 = arrayList4;
        String str = node.getAttrs().get("class");
        if (str == null) {
            str = "Folder";
        }
        Instance inst = new Instance(str);
        if (ClassDb.INSTANCE.isServiceName(inst.getClassName()) && parent == null) {
            inst.setService(true);
        }
        String str2 = node.getAttrs().get("referent");
        if (str2 != null) {
            map.put(str2, inst);
        }
        XNode xNodeChild = node.child("Properties");
        if (xNodeChild != null && (children = xNodeChild.getChildren()) != null) {
            Iterable<XNode> iterable2 = children;
            int i2 = 0;
            for (XNode xNode : iterable2) {
                String str3 = xNode.getAttrs().get("name");
                if (str3 == null) {
                    iterable = iterable2;
                    i = i2;
                } else {
                    try {
                        String name = xNode.getName();
                        if (Intrinsics.areEqual(name, "Ref")) {
                            try {
                                String string = xNode.getText().toString();
                                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                                try {
                                    arrayList2.add(new Triple<>(inst, str3, StringsKt.trim((CharSequence) string).toString()));
                                    inst.getPropTypes().put(str3, 19);
                                    iterable = iterable2;
                                    i = i2;
                                } catch (Exception e) {
                                    iterable = iterable2;
                                    i = i2;
                                    arrayList5.add("Bad value for " + str3 + ": " + e.getMessage());
                                }
                            } catch (Exception e2) {
                            }
                        } else {
                            try {
                                if (Intrinsics.areEqual(name, "SharedString")) {
                                    String string2 = xNode.getText().toString();
                                    Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                                    try {
                                        arrayList3.add(new Triple<>(inst, str3, StringsKt.trim((CharSequence) string2).toString()));
                                        inst.getPropTypes().put(str3, 28);
                                        iterable = iterable2;
                                        i = i2;
                                    } catch (Exception e3) {
                                        iterable = iterable2;
                                        i = i2;
                                        arrayList5.add("Bad value for " + str3 + ": " + e3.getMessage());
                                    }
                                } else {
                                    try {
                                        Pair<Integer, Object> pairConvert = INSTANCE.convert(xNode);
                                        int iIntValue = pairConvert.component1().intValue();
                                        Object objComponent2 = pairConvert.component2();
                                        if (iIntValue == -1) {
                                            iterable = iterable2;
                                            try {
                                                i = i2;
                                                try {
                                                    arrayList5.add("Unknown XML property type " + xNode.getName() + " (" + str3 + ")");
                                                } catch (Exception e4) {
                                                    arrayList5.add("Bad value for " + str3 + ": " + e4.getMessage());
                                                }
                                            } catch (Exception e5) {
                                                i = i2;
                                            }
                                        } else {
                                            iterable = iterable2;
                                            i = i2;
                                            inst.getProps().put(str3, objComponent2);
                                            inst.getPropTypes().put(str3, Integer.valueOf(iIntValue));
                                        }
                                    } catch (Exception e6) {
                                        Exception e = e6;
                                        iterable = iterable2;
                                        i = i2;
                                        arrayList5.add("Bad value for " + str3 + ": " + e.getMessage());
                                        iterable2 = iterable;
                                        i2 = i;
                                    }
                                }
                            } catch (Exception e7) {
                                Exception e = e7;
                                iterable = iterable2;
                                i = i2;
                                arrayList5.add("Bad value for " + str3 + ": " + e.getMessage());
                                iterable2 = iterable;
                                i2 = i;
                            }
                        }
                    } catch (Exception e8) {
                        Exception e = e8;
                    }
                }
                iterable2 = iterable;
                i2 = i;
            }
        }
        if (parent == null) {
            arrayList.add(inst);
        } else {
            inst.attachRaw(parent);
        }
        Iterable children2 = node.getChildren();
        Collection arrayList6 = new ArrayList();
        for (Object obj : children2) {
            Iterable iterable3 = children2;
            if (Intrinsics.areEqual(((XNode) obj).getName(), "Item")) {
                arrayList6.add(obj);
            }
            children2 = (List) iterable3;
        }
        Iterator it = ((List) arrayList6).iterator();
        while (it.hasNext()) {
            read$build(arrayList, map, arrayList2, arrayList3, arrayList5, (XNode) it.next(), inst);
            arrayList5 = arrayList4;
        }
    }

    private final byte[] decodeB64(String s) {
        try {
            byte[] bArrDecode = Base64.decode(new Regex("\\s").replace(s, ""), 0);
            Intrinsics.checkNotNull(bArrDecode);
            return bArrDecode;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    private final XNode parseTree(XmlPullParser p) throws XmlPullParserException, IOException {
        ArrayList stack = new ArrayList();
        XNode root = null;
        int ev = p.getEventType();
        while (ev != 1) {
            switch (ev) {
                case 2:
                    HashMap attrs = new HashMap();
                    int attributeCount = p.getAttributeCount();
                    for (int i = 0; i < attributeCount; i++) {
                        attrs.put(p.getAttributeName(i), p.getAttributeValue(i));
                    }
                    String name = p.getName();
                    Intrinsics.checkNotNullExpressionValue(name, "getName(...)");
                    XNode n = new XNode(name, attrs);
                    if (stack.isEmpty()) {
                        root = n;
                    } else {
                        ((XNode) CollectionsKt.last((List) stack)).getChildren().add(n);
                    }
                    stack.add(n);
                    break;
                case 3:
                    if (!stack.isEmpty()) {
                        stack.remove(stack.size() - 1);
                    }
                    break;
                case 4:
                case 5:
                    if (!stack.isEmpty()) {
                        ((XNode) CollectionsKt.last((List) stack)).getText().append(p.getText());
                    }
                    break;
            }
            ev = p.nextToken();
        }
        return root;
    }

    private final CFrame cframe(XNode n) {
        return new CFrame(n.f("X"), n.f("Y"), n.f("Z"), n.f("R00"), n.f("R01"), n.f("R02"), n.f("R10"), n.f("R11"), n.f("R12"), n.f("R20"), n.f("R21"), n.f("R22"));
    }

    private final String content(XNode n) {
        XNode u = n.child("url");
        if (u == null) {
            String string = n.getText().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return StringsKt.trim((CharSequence) string).toString();
        }
        String string2 = u.getText().toString();
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        return StringsKt.trim((CharSequence) string2).toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x0722, code lost:
    
        if (r4.equals("Content") != false) goto L236;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00db, code lost:
    
        if (r4.equals("CFrame") == false) goto L277;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:?, code lost:
    
        return kotlin.TuplesKt.to(16, cframe(r27));
     */
    /* JADX WARN: Code restructure failed: missing block: B:331:?, code lost:
    
        return kotlin.TuplesKt.to(1, content(r27));
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x010d, code lost:
    
        if (r4.equals("CoordinateFrame") != false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0123, code lost:
    
        if (r4.equals("ContentId") == false) goto L277;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final kotlin.Pair<java.lang.Integer, java.lang.Object> convert(com.exampletest.mobilestudiotest.format.RbxXmlReader.XNode r27) {
        /*
            Method dump skipped, instruction units count: 2202
            To view this dump add '--comments-level debug' option
        */
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:15:0x0035 A[ORIG_RETURN, RETURN] */
    private final float parseSpecial(String t) {
        String upperCase = t.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        switch (upperCase) {
            case "INF":
                return Float.POSITIVE_INFINITY;
            case "NAN":
                return Float.NaN;
            case "-INF":
                return Float.NEGATIVE_INFINITY;
            default:
                return 0.0f;
        }
    }
}
