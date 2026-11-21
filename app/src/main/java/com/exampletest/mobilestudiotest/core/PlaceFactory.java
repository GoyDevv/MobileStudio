package com.exampletest.mobilestudiotest.core;

import com.exampletest.mobilestudiotest.format.RbxDocument;
import com.exampletest.mobilestudiotest.model.CFrame;
import com.exampletest.mobilestudiotest.model.ClassDb;
import com.exampletest.mobilestudiotest.model.Color3;
import com.exampletest.mobilestudiotest.model.DataModel;
import com.exampletest.mobilestudiotest.model.EnumValue;
import com.exampletest.mobilestudiotest.model.Instance;
import com.exampletest.mobilestudiotest.model.InstanceFactory;
import com.exampletest.mobilestudiotest.model.Vector3;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PlaceFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
public final class PlaceFactory {
    public static final PlaceFactory INSTANCE = new PlaceFactory();
    private static final List<String> required = CollectionsKt.listOf(new String[]{"Workspace", "Players", "Lighting", "MaterialService", "ReplicatedFirst", "ReplicatedStorage", "ServerScriptService", "ServerStorage", "StarterGui", "StarterPack", "StarterPlayer", "Teams", "SoundService", "TextChatService"});

    private PlaceFactory() {
    }

    /* JADX INFO: compiled from: PlaceFactory.kt */
    public enum Template {
        BASEPLATE("Baseplate", "A large grey baseplate and a spawn"),
        CLASSIC("Classic Baseplate", "The classic studded baseplate"),
        EMPTY("Empty Place", "Just a spawn location, no ground");

        private final String subtitle;
        private final String title;
        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Template> getEntries() {
            return $ENTRIES;
        }

        Template(String title, String subtitle) {
            this.title = title;
            this.subtitle = subtitle;
        }

        public final String getSubtitle() {
            return this.subtitle;
        }

        public final String getTitle() {
            return this.title;
        }
    }

    public static /* synthetic */ DataModel newPlace$default(PlaceFactory placeFactory, Template template, int i, Object obj) {
        if ((i & 1) != 0) {
            template = Template.BASEPLATE;
        }
        return placeFactory.newPlace(template);
    }

    public final DataModel newPlace(Template template) {
        Intrinsics.checkNotNullParameter(template, "template");
        DataModel dm = new DataModel();
        dm.getMeta().put("ExplicitAutoJoints", "true");
        for (String s : required) {
            service(dm, s);
        }
        Instance instanceFindService = dm.findService("Workspace");
        Intrinsics.checkNotNull(instanceFindService);
        Instance instanceCreate = InstanceFactory.INSTANCE.create("Camera");
        instanceCreate.set("CFrame", CFrame.Companion.lookAt$default(CFrame.INSTANCE, new Vector3(0.0f, 20.0f, 40.0f), Vector3.INSTANCE.getZERO(), null, 4, null), false);
        instanceCreate.attachRaw(instanceFindService);
        instanceFindService.set("CurrentCamera", instanceCreate, false);
        ensureTerrain(instanceFindService);
        if (template != Template.EMPTY) {
            Instance instanceCreate2 = InstanceFactory.INSTANCE.create("Part");
            instanceCreate2.setName("Baseplate");
            instanceCreate2.set("Anchored", true, false);
            instanceCreate2.set("Locked", true, false);
            if (template == Template.CLASSIC) {
                instanceCreate2.set("size", new Vector3(512.0f, 20.0f, 512.0f), false);
                instanceCreate2.set("CFrame", new CFrame(0.0f, -10.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4088, null), false);
                instanceCreate2.set("Color3uint8", Color3.INSTANCE.fromRGB(99, 95, 98), false);
                instanceCreate2.set("TopSurface", new EnumValue(3), false);
            } else {
                instanceCreate2.set("size", new Vector3(2048.0f, 16.0f, 2048.0f), false);
                instanceCreate2.set("CFrame", new CFrame(0.0f, -8.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4088, null), false);
                instanceCreate2.set("Color3uint8", Color3.INSTANCE.fromRGB(91, 93, 105), false);
                Instance instanceCreate3 = InstanceFactory.INSTANCE.create("Texture");
                instanceCreate3.setName("Texture");
                instanceCreate3.set("Texture", "rbxassetid://6372755229", false);
                instanceCreate3.set("Face", new EnumValue(1), false);
                instanceCreate3.set("StudsPerTileU", Float.valueOf(8.0f), false);
                instanceCreate3.set("StudsPerTileV", Float.valueOf(8.0f), false);
                instanceCreate3.set("Transparency", Float.valueOf(0.8f), false);
                instanceCreate3.attachRaw(instanceCreate2);
            }
            instanceCreate2.attachRaw(instanceFindService);
        }
        Instance instanceCreate4 = InstanceFactory.INSTANCE.create("SpawnLocation");
        instanceCreate4.set("CFrame", new CFrame(0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 4088, null), false);
        instanceCreate4.set("Color3uint8", Color3.INSTANCE.fromRGB(163, 162, 165), false);
        instanceCreate4.attachRaw(instanceFindService);
        Instance sp = dm.findService("StarterPlayer");
        Intrinsics.checkNotNull(sp);
        InstanceFactory.INSTANCE.create("StarterPlayerScripts").attachRaw(sp);
        InstanceFactory.INSTANCE.create("StarterCharacterScripts").attachRaw(sp);
        return dm;
    }

    private final Instance service(DataModel dm, String cls) {
        Instance instanceFindService = dm.findService(cls);
        if (instanceFindService != null) {
            return instanceFindService;
        }
        Instance s = InstanceFactory.INSTANCE.create(cls);
        s.setService(true);
        s.attachRaw(dm);
        return s;
    }

    public final DataModel fromDocument(RbxDocument doc) {
        Intrinsics.checkNotNullParameter(doc, "doc");
        DataModel dm = new DataModel();
        dm.getMeta().putAll(doc.getMeta());
        for (Instance r : doc.getRoots()) {
            if (ClassDb.INSTANCE.isServiceName(r.getClassName())) {
                r.setService(true);
            }
            r.attachRaw(dm);
        }
        for (String s : required) {
            service(dm, s);
        }
        Instance ws = dm.findService("Workspace");
        Intrinsics.checkNotNull(ws);
        if (ws.get("CurrentCamera") == null) {
            Instance cam = ws.findFirstChildOfClass("Camera");
            if (cam == null) {
                cam = InstanceFactory.INSTANCE.create("Camera");
                cam.attachRaw(ws);
            }
            ws.set("CurrentCamera", cam, false);
        }
        ensureTerrain(ws);
        return dm;
    }

    private final void ensureTerrain(Instance ws) {
        if (ws.findFirstChildOfClass("Terrain") != null) {
            return;
        }
        Instance t = InstanceFactory.INSTANCE.create("Terrain");
        t.setName("Terrain");
        t.set("Anchored", true, false);
        t.set("Locked", true, false);
        t.set("WaterColor", Color3.INSTANCE.fromRGB(12, 84, 92), false);
        t.set("WaterTransparency", Float.valueOf(0.3f), false);
        t.set("WaterReflectance", Float.valueOf(1.0f), false);
        t.set("WaterWaveSize", Float.valueOf(0.15f), false);
        t.set("WaterWaveSpeed", Float.valueOf(10.0f), false);
        t.setService(false);
        t.attachRaw(ws);
    }
}
