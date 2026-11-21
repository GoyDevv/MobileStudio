package com.exampletest.mobilestudiotest.lua;

import com.exampletest.mobilestudiotest.core.OutputLog;
import com.exampletest.mobilestudiotest.model.ClassDb;
import com.exampletest.mobilestudiotest.model.EnumValue;
import com.exampletest.mobilestudiotest.model.Instance;
import com.exampletest.mobilestudiotest.model.InstanceFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaNumber;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.UpValue;
import org.luaj.vm2.Upvaldesc;
import org.luaj.vm2.Varargs;

/* JADX INFO: compiled from: RobloxGlobals.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class RobloxGlobals {
    public static final RobloxGlobals INSTANCE = new RobloxGlobals();

    private RobloxGlobals() {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:44:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:45:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:49:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:54:0x0104  */
    /* JADX WARN: Code duplicated, block: B:57:0x010b  */
    /* JADX WARN: Code duplicated, block: B:74:0x00d5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:75:0x00e5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:76:0x00db A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x0100 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x00c1 A[SYNTHETIC] */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public final Instance resolveRequirePath(Instance caller, String path) {
        Instance parent;
        String clean = "";
        String strFullName;
        Instance instanceFindFirstChild$default;
        Intrinsics.checkNotNullParameter(caller, "caller");
        Intrinsics.checkNotNullParameter(path, "path");
        Instance cur = caller;
        Iterable iterableSplit$default = StringsKt.split((CharSequence) path, new char[]{'/'}, false, 0);
        Collection arrayList = new ArrayList();
        for (Object obj : iterableSplit$default) {
            if (((String) obj).length() > 0) {
                arrayList.add(obj);
            }
        }
        List<String> parts = CollectionsKt.toMutableList(arrayList);
        if (parts.isEmpty()) {
            throw new LuaError("require path \"" + path + "\" is empty");
        }
        String str = (String) parts.get(0);
        switch (str.hashCode()) {
            case 46:
                if (str.equals(".")) {
                    cur = caller.getParent();
                    parts.remove(0);
                    for (String name : parts) {
                        if (!Intrinsics.areEqual(name, ".")) {
                            if (Intrinsics.areEqual(name, "..")) {
                                if (cur != null) {
                                    parent = cur.getParent();
                                } else {
                                    parent = null;
                                }
                                cur = parent;
                            } else {
                                clean = StringsKt.removeSuffix(StringsKt.removeSuffix(name, (CharSequence) ".luau"), (CharSequence) ".lua");
                                if (cur != null || (instanceFindFirstChild$default = Instance.findFirstChild$default(cur, clean, false, 2, null)) == null) {
                                    if (cur != null || (strFullName = cur.fullName()) == null) {
                                        strFullName = "nil";
                                    }
                                    throw new LuaError("require(\"" + path + "\"): " + clean + " is not a child of " + strFullName);
                                }
                                cur = instanceFindFirstChild$default;
                            }
                        }
                    }
                    if (cur != null) {
                        return cur;
                    }
                    throw new LuaError("require(\"" + path + "\") went above the game");
                }
                throw new LuaError("require path \"" + path + "\" must start with ./, ../ or @self (aliases aren't supported)");
            case 1472:
                if (str.equals("..")) {
                    Instance parent2 = caller.getParent();
                    Instance cur2 = parent2 != null ? parent2.getParent() : null;
                    parts.remove(0);
                    cur = cur2;
                    while (Intrinsics.areEqual(CollectionsKt.firstOrNull(parts), "..")) {
                        cur = cur != null ? cur.getParent() : null;
                        parts.remove(0);
                    }
                    Unit unit = Unit.INSTANCE;
                    while (true) {
                        String name = parts.isEmpty() ? "" : (String) parts.get(0);
                        if (!Intrinsics.areEqual(name, ".")) {
                            if (Intrinsics.areEqual(name, "..")) {
                                if (cur != null) {
                                    parent = cur.getParent();
                                } else {
                                    parent = null;
                                }
                                cur = parent;
                            } else {
                                clean = StringsKt.removeSuffix(StringsKt.removeSuffix(name, (CharSequence) ".luau"), (CharSequence) ".lua");
                                if (cur != null) {
                                }
                            }
                        }
                        break;
                    }
                    if (cur != null) {
                        strFullName = "nil";
                    } else {
                        strFullName = "nil";
                    }
                    throw new LuaError("require(\"" + path + "\"): " + clean + " is not a child of " + strFullName);
                }
                throw new LuaError("require path \"" + path + "\" must start with ./, ../ or @self (aliases aren't supported)");
            case 62631820:
                if (str.equals("@self")) {
                    parts.remove(0);
                    while (true) {
                        String name = parts.isEmpty() ? "" : (String) parts.get(0);
                        if (!Intrinsics.areEqual(name, ".")) {
                            if (Intrinsics.areEqual(name, "..")) {
                                if (cur != null) {
                                    parent = cur.getParent();
                                } else {
                                    parent = null;
                                }
                                cur = parent;
                            } else {
                                clean = StringsKt.removeSuffix(StringsKt.removeSuffix(name, (CharSequence) ".luau"), (CharSequence) ".lua");
                                if (cur != null) {
                                }
                            }
                        }
                        break;
                    }
                    if (cur != null) {
                        strFullName = "nil";
                    } else {
                        strFullName = "nil";
                    }
                    throw new LuaError("require(\"" + path + "\"): " + clean + " is not a child of " + strFullName);
                }
                throw new LuaError("require path \"" + path + "\" must start with ./, ../ or @self (aliases aren't supported)");
            default:
                throw new LuaError("require path \"" + path + "\" must start with ./, ../ or @self (aliases aren't supported)");
        }
    }

    public final void install(final LuaRuntime rt) {
        Intrinsics.checkNotNullParameter(rt, "rt");
        final Globals g = rt.getGlobals();
        final long start = System.nanoTime();
        LuaValue luaValue = g.get("bit32");
        if (!luaValue.istable()) {
            luaValue = null;
        }
        if (luaValue != null) {
            luaValue.set("countlz", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return RobloxGlobals.install$lambda$5$lambda$2((Varargs) obj);
                }
            }));
            luaValue.set("countrz", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda13
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return RobloxGlobals.install$lambda$5$lambda$3((Varargs) obj);
                }
            }));
            luaValue.set("byteswap", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda24
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return RobloxGlobals.install$lambda$5$lambda$4((Varargs) obj);
                }
            }));
        }
        g.set("print", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$6(g, (Varargs) obj);
            }
        }));
        g.set("warn", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda46
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$7(g, (Varargs) obj);
            }
        }));
        g.set("wait", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda49
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$8(rt, (Varargs) obj);
            }
        }));
        g.set("Wait", g.get("wait"));
        g.set("delay", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda50
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$9(rt, (Varargs) obj);
            }
        }));
        g.set("spawn", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$10(rt, (Varargs) obj);
            }
        }));
        g.set("tick", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$11((Varargs) obj);
            }
        }));
        g.set("time", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda53
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$12(rt, (Varargs) obj);
            }
        }));
        g.set("elapsedTime", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$13(start, (Varargs) obj);
            }
        }));
        g.set("typeof", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$14((LuaValue) obj);
            }
        }));
        g.set("game", rt.wrap(rt.getDm()));
        g.set("Game", rt.wrap(rt.getDm()));
        g.set("workspace", rt.wrap(rt.getDm().getWorkspace()));
        g.set("Workspace", rt.wrap(rt.getDm().getWorkspace()));
        g.set("shared", rt.getSharedTable());
        g.set("__iter", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$15(g, (Varargs) obj);
            }
        }));
        g.set("unpack", g.get("table").get("unpack"));
        g.set("ypcall", g.get("pcall"));
        g.set("require", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$16(rt, (LuaValue) obj);
            }
        }));
        g.set("getfenv", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$19(rt, g, (Varargs) obj);
            }
        }));
        g.set("setfenv", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$20(rt, (Varargs) obj);
            }
        }));
        g.set("newproxy", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$21((Varargs) obj);
            }
        }));
        g.set("loadstring", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$22(g, rt, (Varargs) obj);
            }
        }));
        final LuaTable settingsRoot = StudioSettings.INSTANCE.create(rt);
        g.set("settings", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$23(settingsRoot, (Varargs) obj);
            }
        }));
        final Instance instanceCreate = InstanceFactory.INSTANCE.create("UserSettings");
        Instance instanceCreate2 = InstanceFactory.INSTANCE.create("UserGameSettings");
        instanceCreate2.setName("GameSettings");
        instanceCreate2.set("ControlMode", new EnumValue(0), false);
        instanceCreate2.set("ComputerMovementMode", new EnumValue(0), false);
        instanceCreate2.set("TouchMovementMode", new EnumValue(0), false);
        instanceCreate2.set("MasterVolume", Float.valueOf(1.0f), false);
        instanceCreate2.set("SavedQualityLevel", new EnumValue(0), false);
        instanceCreate2.set("RotationType", new EnumValue(0), false);
        instanceCreate2.attachRaw(instanceCreate);
        g.set("UserSettings", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$26(rt, instanceCreate, (Varargs) obj);
            }
        }));
        g.set("version", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$27((Varargs) obj);
            }
        }));
        g.set("printidentity", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$28((Varargs) obj);
            }
        }));
        g.set("gcinfo", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$29((Varargs) obj);
            }
        }));
        LuaTable task = new LuaTable();
        task.set("wait", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$31(rt, (Varargs) obj);
            }
        }));
        task.set("spawn", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$32(rt, (Varargs) obj);
            }
        }));
        task.set("defer", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$33(rt, (Varargs) obj);
            }
        }));
        task.set("delay", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$35(rt, (Varargs) obj);
            }
        }));
        task.set("cancel", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$37(rt, (Varargs) obj);
            }
        }));
        task.set("synchronize", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$38((Varargs) obj);
            }
        }));
        task.set("desynchronize", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$39((Varargs) obj);
            }
        }));
        g.set("task", task);
        LuaTable instLib = new LuaTable();
        instLib.set("new", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$41(rt, (Varargs) obj);
            }
        }));
        instLib.set("fromExisting", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$44(rt, (LuaValue) obj);
            }
        }));
        g.set("Instance", instLib);
        LuaTable luaTable = new LuaTable();
        luaTable.set("new", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$48$lambda$47((Varargs) obj);
            }
        }));
        Unit unit = Unit.INSTANCE;
        g.set("RaycastParams", luaTable);
        LuaTable luaTable2 = new LuaTable();
        luaTable2.set("new", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$51$lambda$50((Varargs) obj);
            }
        }));
        Unit unit2 = Unit.INSTANCE;
        g.set("OverlapParams", luaTable2);
        LuaTable luaTable3 = new LuaTable();
        luaTable3.set("new", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$54$lambda$53((Varargs) obj);
            }
        }));
        Unit unit3 = Unit.INSTANCE;
        g.set("DockWidgetPluginGuiInfo", luaTable3);
        LuaValue math = g.get("math");
        math.set("clamp", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$55((Varargs) obj);
            }
        }));
        math.set("sign", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$56((LuaValue) obj);
            }
        }));
        math.set("round", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$57((LuaValue) obj);
            }
        }));
        math.set("noise", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$58((Varargs) obj);
            }
        }));
        math.set("lerp", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$59((Varargs) obj);
            }
        }));
        math.set("map", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$60((Varargs) obj);
            }
        }));
        LuaValue string = g.get("string");
        string.set("split", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$63((Varargs) obj);
            }
        }));
        LuaValue table = g.get("table");
        table.set("find", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$64((Varargs) obj);
            }
        }));
        table.set("clear", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$66((LuaValue) obj);
            }
        }));
        table.set("create", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda40
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$67((Varargs) obj);
            }
        }));
        table.set("freeze", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$68((LuaValue) obj);
            }
        }));
        table.set("isfrozen", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$69((LuaValue) obj);
            }
        }));
        table.set("clone", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$70((LuaValue) obj);
            }
        }));
        table.set("move", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$71((Varargs) obj);
            }
        }));
        table.set("getn", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda45
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$72((LuaValue) obj);
            }
        }));
        LuaTable utf8 = new LuaTable();
        utf8.set("char", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda47
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$73((Varargs) obj);
            }
        }));
        utf8.set("len", LuaTypesKt.fn1(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda48
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$74((LuaValue) obj);
            }
        }));
        utf8.set("charpattern", "[\u0000-\u007fÂ-ý][\u0080-¿]*");
        g.set("utf8", utf8);
    }

    private static final String install$joinArgs(Globals g, Varargs a) {
        StringBuilder sb = new StringBuilder();
        LuaValue tostring = g.get("tostring");
        int i = 1;
        int iNarg = a.narg();
        if (1 <= iNarg) {
            while (true) {
                if (i > 1) {
                    sb.append(' ');
                }
                sb.append(tostring.call(a.arg(i)).tojstring());
                if (i == iNarg) {
                    break;
                }
                i++;
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    static final Varargs install$lambda$5$lambda$2(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaInteger luaIntegerValueOf = LuaValue.valueOf(Integer.numberOfLeadingZeros((int) a.arg(1).checklong()));
        Intrinsics.checkNotNullExpressionValue(luaIntegerValueOf, "valueOf(...)");
        return luaIntegerValueOf;
    }

    static final Varargs install$lambda$5$lambda$3(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaInteger luaIntegerValueOf = LuaValue.valueOf(Integer.numberOfTrailingZeros((int) a.arg(1).checklong()));
        Intrinsics.checkNotNullExpressionValue(luaIntegerValueOf, "valueOf(...)");
        return luaIntegerValueOf;
    }

    static final Varargs install$lambda$5$lambda$4(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaNumber luaNumberValueOf = LuaValue.valueOf(((long) Integer.reverseBytes((int) a.arg(1).checklong())) & 4294967295L);
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final Varargs install$lambda$6(Globals $g, Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        OutputLog.INSTANCE.print(install$joinArgs($g, a));
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$7(Globals $g, Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        OutputLog.INSTANCE.warn(install$joinArgs($g, a));
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$8(LuaRuntime $rt, Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        return $rt.yieldFor(RangesKt.coerceAtLeast(a.arg(1).optdouble(0.03d), 0.029d));
    }

    static final Varargs install$lambda$9(LuaRuntime $rt, Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaFunction luaFunctionCheckfunction = a.arg(2).checkfunction();
        Intrinsics.checkNotNullExpressionValue(luaFunctionCheckfunction, "checkfunction(...)");
        LuaThread co = $rt.newThread(luaFunctionCheckfunction);
        $rt.delay(a.arg(1).optdouble(0.0d), co);
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$10(LuaRuntime $rt, Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaFunction luaFunctionCheckfunction = a.arg(1).checkfunction();
        Intrinsics.checkNotNullExpressionValue(luaFunctionCheckfunction, "checkfunction(...)");
        LuaThread co = $rt.newThread(luaFunctionCheckfunction);
        $rt.delay(0.0d, co);
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$11(Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        LuaNumber luaNumberValueOf = LuaValue.valueOf(System.currentTimeMillis() / 1000.0d);
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final Varargs install$lambda$12(LuaRuntime $rt, Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        LuaNumber luaNumberValueOf = LuaValue.valueOf($rt.getTime());
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final Varargs install$lambda$13(long $start, Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        LuaNumber luaNumberValueOf = LuaValue.valueOf((System.nanoTime() - $start) / 1.0E9d);
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final LuaValue install$lambda$14(LuaValue v) {
        Intrinsics.checkNotNullParameter(v, "v");
        LuaString luaStringValueOf = LuaValue.valueOf(LuaTypes.INSTANCE.typeOf(v));
        Intrinsics.checkNotNullExpressionValue(luaStringValueOf, "valueOf(...)");
        return luaStringValueOf;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x006f, code lost:
    
        if (((r1 == null || (r1 = r1.get(org.luaj.vm2.LuaValue.CALL)) == null || !r1.isfunction()) ? false : true) != false) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static final org.luaj.vm2.Varargs install$lambda$15(org.luaj.vm2.Globals r7, org.luaj.vm2.Varargs r8) {
        /*
            java.lang.String r0 = "a"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            org.luaj.vm2.LuaValue r0 = r8.arg1()
            boolean r1 = r0.isfunction()
            if (r1 == 0) goto L11
            goto L71
        L11:
            boolean r1 = r0.istable()
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L51
            org.luaj.vm2.LuaValue r1 = r0.getmetatable()
            if (r1 == 0) goto L26
            java.lang.String r4 = "__iter"
            org.luaj.vm2.LuaValue r4 = r1.get(r4)
            goto L27
        L26:
            r4 = 0
        L27:
            if (r4 == 0) goto L37
            boolean r5 = r4.isfunction()
            if (r5 == 0) goto L37
            r2 = r0
            org.luaj.vm2.Varargs r2 = (org.luaj.vm2.Varargs) r2
            org.luaj.vm2.Varargs r2 = r4.invoke(r2)
            goto L4d
        L37:
            r5 = 3
            org.luaj.vm2.LuaValue[] r5 = new org.luaj.vm2.LuaValue[r5]
            java.lang.String r6 = "next"
            org.luaj.vm2.LuaValue r6 = r7.get(r6)
            r5[r3] = r6
            r5[r2] = r0
            r2 = 2
            org.luaj.vm2.LuaValue r3 = org.luaj.vm2.LuaValue.NIL
            r5[r2] = r3
            org.luaj.vm2.Varargs r2 = org.luaj.vm2.LuaValue.varargsOf(r5)
        L4d:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            goto L72
        L51:
            boolean r1 = r0.isuserdata()
            if (r1 == 0) goto L73
            org.luaj.vm2.LuaValue r1 = r0.getmetatable()
            if (r1 == 0) goto L6e
            org.luaj.vm2.LuaString r4 = org.luaj.vm2.LuaValue.CALL
            org.luaj.vm2.LuaValue r4 = (org.luaj.vm2.LuaValue) r4
            org.luaj.vm2.LuaValue r1 = r1.get(r4)
            if (r1 == 0) goto L6e
            boolean r1 = r1.isfunction()
            if (r1 != r2) goto L6e
            goto L6f
        L6e:
            r2 = r3
        L6f:
            if (r2 == 0) goto L73
        L71:
            r2 = r8
        L72:
            return r2
        L73:
            org.luaj.vm2.LuaError r1 = new org.luaj.vm2.LuaError
            com.exampletest.mobilestudiotest.lua.LuaTypes r2 = com.exampletest.mobilestudiotest.lua.LuaTypes.INSTANCE
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            java.lang.String r2 = r2.typeOf(r0)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "attempt to iterate over a "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r2 = r3.append(r2)
            java.lang.String r3 = " value"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exampletest.mobilestudiotest.lua.RobloxGlobals.install$lambda$15(org.luaj.vm2.Globals, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs");
    }

    private static final UpValue install$envSlot(LuaValue f) {
        Upvaldesc[] ups;
        LuaClosure c = f instanceof LuaClosure ? (LuaClosure) f : null;
        if (c == null || (ups = c.p.upvalues) == null) {
            return null;
        }
        int length = ups.length;
        for (int i = 0; i < length; i++) {
            LuaString luaString = ups[i].name;
            if (Intrinsics.areEqual(luaString != null ? luaString.tojstring() : null, "_ENV")) {
                UpValue[] upValues = c.upValues;
                Intrinsics.checkNotNullExpressionValue(upValues, "upValues");
                return (UpValue) ArraysKt.getOrNull(upValues, i);
            }
        }
        return null;
    }

    private static final LuaValue install$funcAt(LuaRuntime $rt, int level) {
        try {
            LuaValue luaValue = $rt.getDebugGetinfo().call(LuaValue.valueOf(level), LuaValue.valueOf("f")).get("func");
            Intrinsics.checkNotNull(luaValue);
            return luaValue;
        } catch (Exception e) {
            LuaValue luaValue2 = LuaValue.NIL;
            Intrinsics.checkNotNull(luaValue2);
            return luaValue2;
        }
    }

    static final LuaValue install$lambda$16(LuaRuntime $rt, LuaValue m) {
        LuaValue luaValue;
        Intrinsics.checkNotNullParameter(m, "m");
        Object obj = m.isuserdata() ? m.touserdata() : null;
        if (!(obj instanceof Instance)) {
            obj = null;
        }
        Instance inst = (Instance) obj;
        if (inst == null) {
            if (m.isnumber()) {
                throw new LuaError("require(assetId) is not supported offline");
            }
            if (m.isstring()) {
                UpValue upValueInstall$envSlot = install$envSlot(install$funcAt($rt, 1));
                LuaValue value = upValueInstall$envSlot != null ? upValueInstall$envSlot.getValue() : null;
                LuaTable luaTable = value instanceof LuaTable ? (LuaTable) value : null;
                if (luaTable != null && (luaValue = luaTable.get("script")) != null) {
                    Object obj2 = luaValue.isuserdata() ? luaValue.touserdata() : null;
                    Instance caller = (Instance) (obj2 instanceof Instance ? obj2 : null);
                    if (caller != null) {
                        RobloxGlobals robloxGlobals = INSTANCE;
                        String str = m.tojstring();
                        Intrinsics.checkNotNullExpressionValue(str, "tojstring(...)");
                        inst = robloxGlobals.resolveRequirePath(caller, str);
                    }
                }
                throw new LuaError("require(\"" + m.tojstring() + "\") needs to be called from a script");
            }
            throw new LuaError("Attempted to call require with invalid argument(s).");
        }
        return $rt.require(inst);
    }

    static final Varargs install$lambda$19(LuaRuntime $rt, Globals $g, Varargs a) {
        LuaValue f;
        Object value;
        LuaValue value2;
        Intrinsics.checkNotNullParameter(a, "a");
        LuaValue x = a.arg1();
        if (x.isfunction()) {
            f = x;
        } else if (x.isnil()) {
            f = install$funcAt($rt, 1);
        } else {
            if (!x.isnumber()) {
                throw new LuaError("bad argument #1 to 'getfenv' (number expected)");
            }
            if (x.toint() == 0) {
                LuaTable luaTableLoadingModuleEnv = $rt.loadingModuleEnv();
                if (luaTableLoadingModuleEnv != null) {
                    return luaTableLoadingModuleEnv;
                }
                Object bottom = null;
                for (int lvl = 1; lvl < 251; lvl++) {
                    LuaValue f2 = install$funcAt($rt, lvl);
                    if (f2.isnil()) {
                        break;
                    }
                    UpValue upValueInstall$envSlot = install$envSlot(f2);
                    if (upValueInstall$envSlot != null && (value = upValueInstall$envSlot.getValue()) != null) {
                        bottom = value;
                    }
                }
                return bottom != null ? (Varargs) bottom : $g;
            }
            f = install$funcAt($rt, x.toint());
        }
        Intrinsics.checkNotNull(f);
        UpValue upValueInstall$envSlot2 = install$envSlot(f);
        return (upValueInstall$envSlot2 == null || (value2 = upValueInstall$envSlot2.getValue()) == null) ? $g : value2;
    }

    static final Varargs install$lambda$20(LuaRuntime $rt, Varargs a) {
        LuaValue f;
        Intrinsics.checkNotNullParameter(a, "a");
        LuaValue x = a.arg1();
        LuaTable t = a.arg(2).checktable();
        if (x.isfunction()) {
            f = x;
        } else {
            f = install$funcAt($rt, x.isnil() ? 1 : x.checkint());
        }
        Intrinsics.checkNotNull(f);
        UpValue upValueInstall$envSlot = install$envSlot(f);
        if (upValueInstall$envSlot == null) {
            throw new LuaError("'setfenv' cannot change environment of given object");
        }
        upValueInstall$envSlot.setValue(t);
        return f;
    }

    static final Varargs install$lambda$21(Varargs a) {
        LuaValue mt;
        Intrinsics.checkNotNullParameter(a, "a");
        LuaValue x = a.arg1();
        if (x.isuserdata()) {
            mt = x.getmetatable();
        } else {
            mt = x.toboolean() ? new LuaTable() : null;
        }
        return new LuaUserdata(new Object(), mt);
    }

    static final Varargs install$lambda$22(Globals $g, LuaRuntime $rt, Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaSandbox luaSandbox = LuaSandbox.INSTANCE;
        LuaValue luaValueArg1 = a.arg1();
        Intrinsics.checkNotNullExpressionValue(luaValueArg1, "arg1(...)");
        if (luaSandbox.isBytecode(luaValueArg1)) {
            Varargs varargsVarargsOf = LuaValue.varargsOf(LuaValue.NIL, LuaValue.valueOf("bytecode is not allowed"));
            Intrinsics.checkNotNullExpressionValue(varargsVarargsOf, "varargsOf(...)");
            return varargsVarargsOf;
        }
        String src = a.arg1().checkjstring();
        String name = a.arg(2).optjstring("loadstring");
        try {
            UpValue upValueInstall$envSlot = install$envSlot(install$funcAt($rt, 1));
            LuaValue value = upValueInstall$envSlot != null ? upValueInstall$envSlot.getValue() : null;
            LuaTable env = value instanceof LuaTable ? (LuaTable) value : null;
            if (env == null) {
                env = $g;
            }
            Globals globals = $rt.getGlobals();
            LuauTranspiler luauTranspiler = LuauTranspiler.INSTANCE;
            Intrinsics.checkNotNull(src);
            LuaValue luaValueLoad = globals.load(LuauTranspiler.transpile$default(luauTranspiler, src, false, 2, null), "=" + name, env);
            Intrinsics.checkNotNull(luaValueLoad);
            return luaValueLoad;
        } catch (LuaError e) {
            LuaValue luaValue = LuaValue.NIL;
            String message = e.getMessage();
            if (message == null) {
                message = "syntax error";
            }
            Varargs varargsVarargsOf2 = LuaValue.varargsOf(luaValue, LuaValue.valueOf($rt.cleanError(message)));
            Intrinsics.checkNotNull(varargsVarargsOf2);
            return varargsVarargsOf2;
        }
    }

    static final Varargs install$lambda$23(LuaTable $settingsRoot, Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return $settingsRoot;
    }

    static final Varargs install$lambda$26(LuaRuntime $rt, Instance $userSettings, Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return $rt.wrap($userSettings);
    }

    static final Varargs install$lambda$27(Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        LuaString luaStringValueOf = LuaValue.valueOf("0.600.0");
        Intrinsics.checkNotNullExpressionValue(luaStringValueOf, "valueOf(...)");
        return luaStringValueOf;
    }

    static final Varargs install$lambda$28(Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        OutputLog.INSTANCE.print("Current identity is 2");
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$29(Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        LuaNumber luaNumberValueOf = LuaValue.valueOf((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024.0d);
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final Varargs install$lambda$31(LuaRuntime $rt, Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        Varargs varargsVarargsOf = LuaValue.varargsOf($rt.yieldFor(a.arg(1).optdouble(0.0d)).arg1(), LuaValue.NONE);
        Intrinsics.checkNotNullExpressionValue(varargsVarargsOf, "let(...)");
        return varargsVarargsOf;
    }

    static final Varargs install$lambda$32(LuaRuntime $rt, Varargs a) {
        LuaThread co;
        Intrinsics.checkNotNullParameter(a, "a");
        LuaValue f = a.arg(1);
        if (f.isthread()) {
            Intrinsics.checkNotNull(f, "null cannot be cast to non-null type org.luaj.vm2.LuaThread");
            co = (LuaThread) f;
        } else {
            LuaFunction luaFunctionCheckfunction = f.checkfunction();
            Intrinsics.checkNotNullExpressionValue(luaFunctionCheckfunction, "checkfunction(...)");
            co = $rt.newThread(luaFunctionCheckfunction);
        }
        Varargs varargsSubargs = a.subargs(2);
        Intrinsics.checkNotNullExpressionValue(varargsSubargs, "subargs(...)");
        $rt.resume(co, varargsSubargs);
        return co;
    }

    static final Varargs install$lambda$33(LuaRuntime $rt, Varargs a) {
        LuaThread co;
        Intrinsics.checkNotNullParameter(a, "a");
        LuaValue f = a.arg(1);
        if (f.isthread()) {
            Intrinsics.checkNotNull(f, "null cannot be cast to non-null type org.luaj.vm2.LuaThread");
            co = (LuaThread) f;
        } else {
            LuaFunction luaFunctionCheckfunction = f.checkfunction();
            Intrinsics.checkNotNullExpressionValue(luaFunctionCheckfunction, "checkfunction(...)");
            co = $rt.newThread(luaFunctionCheckfunction);
        }
        Varargs varargsSubargs = a.subargs(2);
        Intrinsics.checkNotNullExpressionValue(varargsSubargs, "subargs(...)");
        $rt.defer(co, varargsSubargs);
        return co;
    }

    static final Varargs install$lambda$35(final LuaRuntime $rt, Varargs a) {
        final LuaThread co;
        Intrinsics.checkNotNullParameter(a, "a");
        LuaValue f = a.arg(2);
        if (f.isthread()) {
            Intrinsics.checkNotNull(f, "null cannot be cast to non-null type org.luaj.vm2.LuaThread");
            co = (LuaThread) f;
        } else {
            LuaFunction luaFunctionCheckfunction = f.checkfunction();
            Intrinsics.checkNotNullExpressionValue(luaFunctionCheckfunction, "checkfunction(...)");
            co = $rt.newThread(luaFunctionCheckfunction);
        }
        final Varargs args = a.subargs(3);
        $rt.after(a.arg(1).optdouble(0.0d), new Function0() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return RobloxGlobals.install$lambda$35$lambda$34($rt, co, args);
            }
        });
        return co;
    }

    static final Unit install$lambda$35$lambda$34(LuaRuntime $rt, LuaThread $co, Varargs $args) {
        Intrinsics.checkNotNull($args);
        $rt.resume($co, $args);
        return Unit.INSTANCE;
    }

    static final Varargs install$lambda$37(LuaRuntime $rt, Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaValue luaValueArg = a.arg(1);
        LuaThread luaThread = luaValueArg instanceof LuaThread ? (LuaThread) luaValueArg : null;
        if (luaThread != null) {
            $rt.cancel(luaThread);
        }
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$38(Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$39(Varargs it) {
        Intrinsics.checkNotNullParameter(it, "it");
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$41(LuaRuntime $rt, Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        String cls = a.arg(1).checkjstring();
        ClassDb classDb = ClassDb.INSTANCE;
        Intrinsics.checkNotNull(cls);
        if (classDb.isServiceName(cls) || SetsKt.setOf(new String[]{"DataModel", "Player", "PlayerGui"}).contains(cls)) {
            throw new LuaError("Unable to create an Instance of type \"" + cls + "\"");
        }
        Instance i = InstanceFactory.INSTANCE.create(cls);
        LuaValue luaValueArg = a.arg(2);
        Intrinsics.checkNotNullExpressionValue(luaValueArg, "arg(...)");
        Object obj = luaValueArg.isuserdata() ? luaValueArg.touserdata() : null;
        if (!(obj instanceof Instance)) {
            obj = null;
        }
        Instance instance = (Instance) obj;
        if (instance != null) {
            Instance.setParent$default(i, instance, 0, 2, null);
        }
        return $rt.wrap(i);
    }

    static final LuaValue install$lambda$44(LuaRuntime $rt, LuaValue v) {
        Intrinsics.checkNotNullParameter(v, "v");
        Object obj = v.isuserdata() ? v.touserdata() : null;
        Instance instance = (Instance) (obj instanceof Instance ? obj : null);
        if (instance == null) {
            throw new LuaError("Instance expected, got " + LuaTypes.INSTANCE.typeOf(v));
        }
        Instance instanceCloneTree = instance.cloneTree();
        Iterator it = CollectionsKt.toList(instanceCloneTree.getChildren()).iterator();
        while (it.hasNext()) {
            ((Instance) it.next()).destroy();
        }
        return $rt.wrap(instanceCloneTree);
    }

    static final Varargs install$lambda$48$lambda$47(Varargs varargs) {
        Intrinsics.checkNotNullParameter(varargs, "<unused var>");
        LuaTable luaTable = new LuaTable();
        luaTable.set("FilterDescendantsInstances", new LuaTable());
        luaTable.set("FilterType", LuaTypes.INSTANCE.wrap(new EnumItemV("RaycastFilterType", "Exclude", 0)));
        luaTable.set("IgnoreWater", LuaValue.FALSE);
        luaTable.set("CollisionGroup", "Default");
        luaTable.set("RespectCanCollide", LuaValue.FALSE);
        luaTable.set("AddToFilter", LuaTypesKt.fnv(new Function1() { // from class: com.exampletest.mobilestudiotest.lua.RobloxGlobals$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RobloxGlobals.install$lambda$48$lambda$47$lambda$46$lambda$45((Varargs) obj);
            }
        }));
        return luaTable;
    }

    static final Varargs install$lambda$48$lambda$47$lambda$46$lambda$45(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaValue list = a.arg(1).get("FilterDescendantsInstances");
        LuaValue v = a.arg(2);
        if (v.istable()) {
            int i = 1;
            int length = v.length();
            if (1 <= length) {
                while (true) {
                    list.set(list.length() + 1, v.get(i));
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
        } else {
            int i2 = list.length();
            list.set(i2 + 1, v);
        }
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$51$lambda$50(Varargs varargs) {
        Intrinsics.checkNotNullParameter(varargs, "<unused var>");
        LuaTable luaTable = new LuaTable();
        luaTable.set("FilterDescendantsInstances", new LuaTable());
        luaTable.set("FilterType", LuaTypes.INSTANCE.wrap(new EnumItemV("RaycastFilterType", "Exclude", 0)));
        luaTable.set("MaxParts", 0);
        luaTable.set("CollisionGroup", "Default");
        luaTable.set("RespectCanCollide", LuaValue.FALSE);
        return luaTable;
    }

    static final Varargs install$lambda$54$lambda$53(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaTable luaTable = new LuaTable();
        luaTable.set("InitialDockState", a.arg(1));
        luaTable.set("InitialEnabled", LuaValue.valueOf(a.arg(2).optboolean(false)));
        luaTable.set("FloatingXSize", LuaValue.valueOf(a.arg(4).optint(300)));
        luaTable.set("FloatingYSize", LuaValue.valueOf(a.arg(5).optint(300)));
        luaTable.set("MinWidth", LuaValue.valueOf(a.arg(6).optint(150)));
        luaTable.set("MinHeight", LuaValue.valueOf(a.arg(7).optint(150)));
        return luaTable;
    }

    static final Varargs install$lambda$55(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        double x = a.arg(1).checkdouble();
        double mn = a.arg(2).checkdouble();
        double mx = a.arg(3).checkdouble();
        if (mn > mx) {
            throw new LuaError("max must be greater than or equal to min");
        }
        LuaNumber luaNumberValueOf = LuaValue.valueOf(RangesKt.coerceIn(x, mn, mx));
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final LuaValue install$lambda$56(LuaValue a) {
        int i;
        Intrinsics.checkNotNullParameter(a, "a");
        double x = a.checkdouble();
        if (x > 0.0d) {
            i = 1;
        } else {
            i = x < 0.0d ? -1 : 0;
        }
        LuaInteger luaIntegerValueOf = LuaValue.valueOf(i);
        Intrinsics.checkNotNullExpressionValue(luaIntegerValueOf, "valueOf(...)");
        return luaIntegerValueOf;
    }

    static final LuaValue install$lambda$57(LuaValue a) {
        Intrinsics.checkNotNullParameter(a, "a");
        double x = a.checkdouble();
        LuaNumber luaNumberValueOf = LuaValue.valueOf(x >= 0.0d ? Math.floor(0.5d + x) : -Math.floor((-x) + 0.5d));
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final Varargs install$lambda$58(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaNumber luaNumberValueOf = LuaValue.valueOf(Noise.INSTANCE.perlin(a.arg(1).optdouble(0.0d), a.arg(2).optdouble(0.0d), a.arg(3).optdouble(0.0d)));
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final Varargs install$lambda$59(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        double x = a.arg(1).checkdouble();
        LuaNumber luaNumberValueOf = LuaValue.valueOf(((a.arg(2).checkdouble() - x) * a.arg(3).checkdouble()) + x);
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final Varargs install$lambda$60(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        double x = a.arg(1).checkdouble();
        double i0 = a.arg(2).checkdouble();
        double i1 = a.arg(3).checkdouble();
        double o0 = a.arg(4).checkdouble();
        double o1 = a.arg(5).checkdouble();
        LuaNumber luaNumberValueOf = LuaValue.valueOf((((x - i0) * (o1 - o0)) / (i1 - i0)) + o0);
        Intrinsics.checkNotNullExpressionValue(luaNumberValueOf, "valueOf(...)");
        return luaNumberValueOf;
    }

    static final Varargs install$lambda$63(Varargs a) {
        Collection parts;
        Intrinsics.checkNotNullParameter(a, "a");
        CharSequence s = a.arg(1).checkjstring();
        String sep = a.arg(2).optjstring(",");
        LuaTable t = new LuaTable();
        Intrinsics.checkNotNull(sep);
        boolean z = sep.length() == 0;
        Intrinsics.checkNotNull(s);
        if (!z) {
            parts = StringsKt.split(s, new String[]{sep}, false, 0);
        } else {
            CharSequence charSequence = s;
            Collection arrayList = new ArrayList(charSequence.length());
            for (int i = 0; i < charSequence.length(); i++) {
                arrayList.add(String.valueOf(charSequence.charAt(i)));
            }
            parts = (List) arrayList;
        }
        Iterator it = parts.iterator();
        while (it.hasNext()) {
            t.insert(0, LuaValue.valueOf((String) it.next()));
        }
        return t;
    }

    static final Varargs install$lambda$64(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaTable t = a.arg(1).checktable();
        LuaValue v = a.arg(2);
        int init = a.arg(3).optint(1);
        LuaValue NIL = LuaValue.NIL;
        Intrinsics.checkNotNullExpressionValue(NIL, "NIL");
        int i = init;
        int length = t.length();
        if (i <= length) {
            while (!t.get(i).eq_b(v)) {
                if (i != length) {
                    i++;
                }
            }
            LuaValue r = LuaValue.valueOf(i);
            Intrinsics.checkNotNullExpressionValue(r, "valueOf(...)");
            NIL = r;
        }
        return NIL;
    }

    static final LuaValue install$lambda$66(LuaValue a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaTable t = a.checktable();
        ArrayList keys = new ArrayList();
        LuaValue k = LuaValue.NIL;
        while (true) {
            Varargs n = t.next(k);
            k = n.arg1();
            if (k.isnil()) {
                break;
            }
            keys.add(k);
        }
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            t.set((LuaValue) it.next(), LuaValue.NIL);
        }
        LuaValue NONE = LuaValue.NONE;
        Intrinsics.checkNotNullExpressionValue(NONE, "NONE");
        return NONE;
    }

    static final Varargs install$lambda$67(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaTable t = new LuaTable();
        int n = a.arg(1).checkint();
        LuaValue v = a.arg(2);
        if (!v.isnil()) {
            int i = 1;
            if (1 <= n) {
                while (true) {
                    t.set(i, v);
                    if (i == n) {
                        break;
                    }
                    i++;
                }
            }
        }
        return t;
    }

    static final LuaValue install$lambda$68(LuaValue it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it;
    }

    static final LuaValue install$lambda$69(LuaValue it) {
        Intrinsics.checkNotNullParameter(it, "it");
        LuaBoolean FALSE = LuaValue.FALSE;
        Intrinsics.checkNotNullExpressionValue(FALSE, "FALSE");
        return FALSE;
    }

    static final LuaValue install$lambda$70(LuaValue a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaTable src = a.checktable();
        LuaTable t = new LuaTable();
        LuaValue k = LuaValue.NIL;
        while (true) {
            Varargs n = src.next(k);
            k = n.arg1();
            if (k.isnil()) {
                return t;
            }
            t.set(k, n.arg(2));
        }
    }

    static final Varargs install$lambda$71(Varargs a) {
        LuaTable dst;
        Intrinsics.checkNotNullParameter(a, "a");
        LuaTable src = a.arg(1).checktable();
        int f = a.arg(2).checkint();
        int e = a.arg(3).checkint();
        int tpos = a.arg(4).checkint();
        if (a.arg(5).istable()) {
            LuaValue luaValueArg = a.arg(5);
            Intrinsics.checkNotNull(luaValueArg, "null cannot be cast to non-null type org.luaj.vm2.LuaTable");
            dst = (LuaTable) luaValueArg;
        } else {
            dst = src;
        }
        if (tpos <= f) {
            int i = f;
            if (i <= e) {
                while (true) {
                    dst.set((tpos + i) - f, src.get(i));
                    if (i == e) {
                        break;
                    }
                    i++;
                }
            }
        } else {
            int i2 = e;
            if (f <= i2) {
                while (true) {
                    dst.set((tpos + i2) - f, src.get(i2));
                    if (i2 == f) {
                        break;
                    }
                    i2--;
                }
            }
        }
        Intrinsics.checkNotNull(dst);
        return dst;
    }

    static final LuaValue install$lambda$72(LuaValue a) {
        Intrinsics.checkNotNullParameter(a, "a");
        LuaInteger luaIntegerValueOf = LuaValue.valueOf(a.length());
        Intrinsics.checkNotNullExpressionValue(luaIntegerValueOf, "valueOf(...)");
        return luaIntegerValueOf;
    }

    static final Varargs install$lambda$73(Varargs a) {
        Intrinsics.checkNotNullParameter(a, "a");
        StringBuilder sb = new StringBuilder();
        int i = 1;
        int iNarg = a.narg();
        if (1 <= iNarg) {
            while (true) {
                sb.appendCodePoint(a.arg(i).checkint());
                if (i == iNarg) {
                    break;
                }
                i++;
            }
        }
        LuaString luaStringValueOf = LuaValue.valueOf(sb.toString());
        Intrinsics.checkNotNullExpressionValue(luaStringValueOf, "valueOf(...)");
        return luaStringValueOf;
    }

    static final LuaValue install$lambda$74(LuaValue a) {
        Intrinsics.checkNotNullParameter(a, "a");
        byte[] m_bytes = a.checkstring().m_bytes;
        Intrinsics.checkNotNullExpressionValue(m_bytes, "m_bytes");
        String s = new String(m_bytes, a.checkstring().m_offset, a.checkstring().m_length, Charsets.UTF_8);
        LuaInteger luaIntegerValueOf = LuaValue.valueOf(s.codePointCount(0, s.length()));
        Intrinsics.checkNotNullExpressionValue(luaIntegerValueOf, "valueOf(...)");
        return luaIntegerValueOf;
    }
}
