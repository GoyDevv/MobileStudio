package com.exampletest.mobilestudiotest.lua;

import com.exampletest.mobilestudiotest.core.OutputLog;
import com.exampletest.mobilestudiotest.model.BinaryString;
import com.exampletest.mobilestudiotest.model.BrickColorV;
import com.exampletest.mobilestudiotest.model.Color3;
import com.exampletest.mobilestudiotest.model.Instance;
import com.exampletest.mobilestudiotest.model.NumberRange;
import com.exampletest.mobilestudiotest.model.Rect;
import com.exampletest.mobilestudiotest.model.UDim;
import com.exampletest.mobilestudiotest.model.UDim2;
import com.exampletest.mobilestudiotest.model.Vector2;
import com.exampletest.mobilestudiotest.model.Vector3;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: InstanceApi.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class Attributes {
    public static final Attributes INSTANCE = new Attributes();
    private static final Set<Instance> loaded = Collections.newSetFromMap(new WeakHashMap());

    private Attributes() {
    }

    public final void load(Instance i) {
        byte[] blob;
        Object objLoad$str;
        Intrinsics.checkNotNullParameter(i, "i");
        if (loaded.add(i)) {
            Object v = i.get("AttributesSerialize");
            if (v instanceof BinaryString) {
                blob = ((BinaryString) v).getBytes();
            } else {
                if (!(v instanceof String)) {
                    return;
                }
                blob = ((String) v).getBytes(Charsets.ISO_8859_1);
                Intrinsics.checkNotNullExpressionValue(blob, "getBytes(...)");
            }
            if (blob.length < 4) {
                return;
            }
            try {
                ByteBuffer bb = ByteBuffer.wrap(blob).order(ByteOrder.LITTLE_ENDIAN);
                int count = bb.getInt();
                for (int i2 = 0; i2 < count; i2++) {
                    String strLoad$str = load$str(bb);
                    switch (bb.get() & 255) {
                        case 2:
                            objLoad$str = load$str(bb);
                            break;
                        case 3:
                            objLoad$str = Boolean.valueOf(bb.get() != 0);
                            break;
                        case 5:
                            objLoad$str = Float.valueOf(bb.getFloat());
                            break;
                        case 6:
                            objLoad$str = Double.valueOf(bb.getDouble());
                            break;
                        case 9:
                            objLoad$str = new UDim(bb.getFloat(), bb.getInt());
                            break;
                        case 10:
                            objLoad$str = new UDim2(bb.getFloat(), bb.getInt(), bb.getFloat(), bb.getInt());
                            break;
                        case 14:
                            objLoad$str = new BrickColorV(bb.getInt());
                            break;
                        case 15:
                            objLoad$str = new Color3(bb.getFloat(), bb.getFloat(), bb.getFloat());
                            break;
                        case 16:
                            objLoad$str = new Vector2(bb.getFloat(), bb.getFloat());
                            break;
                        case 17:
                            objLoad$str = new Vector3(bb.getFloat(), bb.getFloat(), bb.getFloat());
                            break;
                        case 27:
                            objLoad$str = new NumberRange(bb.getFloat(), bb.getFloat());
                            break;
                        case 28:
                            objLoad$str = new Rect(bb.getFloat(), bb.getFloat(), bb.getFloat(), bb.getFloat());
                            break;
                        default:
                            return;
                    }
                    if (!i.getAttributes().containsKey(strLoad$str)) {
                        i.getAttributes().put(strLoad$str, objLoad$str);
                    }
                }
            } catch (Exception e) {
                OutputLog.INSTANCE.warn("Could not read attributes of " + i.fullName());
            }
        }
    }

    private static final String load$str(ByteBuffer bb) {
        int n = bb.getInt();
        byte[] b = new byte[n];
        bb.get(b);
        return new String(b, Charsets.UTF_8);
    }
}
