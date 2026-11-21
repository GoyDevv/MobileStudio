# Mobile Studio — Android Studio Project Recovery Notes

## Recovered Project Configuration
- **Package / Namespace / Application ID**: `com.exampletest.mobilestudiotest` (kept 1:1 as in the original APK)
- **App Name**: `Mobile Studio` (`versionName = 1.1`, `versionCode = 2`)
- **SDK / Toolchain**: `compileSdk = 37`, `targetSdk = 37`, `minSdk = 24`, AGP `9.5.1`, Gradle `9.9.0`, Java 17
- **App Source Files**: 167 Java source files (decompiled from Kotlin 2.2.0)
- **Automatic JADX Fixes Applied**: Resolved all 420 synthetic lambda `this.f$0` references to their lexical receivers (`EnclosingClass.this`, `this$0`, or `mainActivity`), stripped `@Metadata` annotations from `.java` sources, and inlined the adaptive icon foreground gradient.
- **Assets & Resources**: 100% of original fonts (42 `.ttf`), material textures (133 images), adaptive launcher icons, Material 3 Dark theme, and backup/data-extraction rules restored.

## Methods Not Decompiled by JADX (35 methods across 15 files)
The uploaded JADX source archive did not include bytecode for these 35 methods (`Method dump skipped`). If you still have the original `.apk` file, you can decompile those specific methods with `jadx --comments-level debug` or `apktool` to restore their exact bodies:

1. `engine/DefaultAnimate.java` — `com.exampletest.mobilestudiotest.engine.DefaultAnimate.step(float, java.lang.String, float, boolean, java.lang.String, float):void`
2. `engine/PlaySession.java` — `com.exampletest.mobilestudiotest.engine.PlaySession.animateR6(float, java.lang.String):void`
3. `engine/PlaySession.java` — `com.exampletest.mobilestudiotest.engine.PlaySession.solveDistanceConstraints():void`
4. `engine/PlaySession.java` — `com.exampletest.mobilestudiotest.engine.PlaySession.mouseValue$lambda$151(com.exampletest.mobilestudiotest.engine.PlaySession, org.luaj.vm2.LuaTable, org.luaj.vm2.LuaValue, org.luaj.vm2.LuaValue):org.luaj.vm2.LuaValue`
5. `format/RbxXmlReader.java` — `com.exampletest.mobilestudiotest.format.RbxXmlReader.convert(com.exampletest.mobilestudiotest.format.RbxXmlReader$XNode):kotlin.Pair`
6. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi._init_$lambda$5(com.exampletest.mobilestudiotest.lua.InstanceApi, org.luaj.vm2.LuaValue, org.luaj.vm2.LuaValue):org.luaj.vm2.LuaValue`
7. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi._init_$lambda$8(com.exampletest.mobilestudiotest.lua.InstanceApi, org.luaj.vm2.LuaValue, org.luaj.vm2.LuaValue):org.luaj.vm2.LuaValue`
8. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.getK(com.exampletest.mobilestudiotest.model.Instance, java.lang.String):java.lang.Object`
9. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.setK(com.exampletest.mobilestudiotest.model.Instance, java.lang.String, java.lang.Object):void`
10. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.typeHint(com.exampletest.mobilestudiotest.model.Instance, java.lang.String):java.lang.Integer`
11. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.animationTrack$lambda$40(com.exampletest.mobilestudiotest.engine.PlaySession$ScriptTrack, kotlin.jvm.internal.Ref$BooleanRef, kotlin.jvm.internal.Ref$FloatRef, kotlin.jvm.internal.Ref$ObjectRef, com.exampletest.mobilestudiotest.lua.InstanceApi, kotlin.jvm.internal.Ref$ObjectRef, com.exampletest.mobilestudiotest.model.Instance, org.luaj.vm2.LuaTable, org.luaj.vm2.LuaValue, org.luaj.vm2.LuaValue):org.luaj.vm2.LuaValue`
12. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.callMethod(java.lang.String, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs`
13. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.collectionCall(java.lang.String, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs`
14. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.workspaceCall(java.lang.String, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs`
15. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.modelCall(com.exampletest.mobilestudiotest.model.Instance, java.lang.String, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs`
16. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.partCall(com.exampletest.mobilestudiotest.model.Instance, java.lang.String, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs`
17. `lua/InstanceApi.java` — `com.exampletest.mobilestudiotest.lua.InstanceApi.humanoidCall(com.exampletest.mobilestudiotest.model.Instance, java.lang.String, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs`
18. `lua/LuaTypes.java` — `com.exampletest.mobilestudiotest.lua.LuaTypes._init_$lambda$17(java.lang.Object, java.lang.String):org.luaj.vm2.LuaValue`
19. `lua/LuaTypes.java` — `com.exampletest.mobilestudiotest.lua.LuaTypes._init_$lambda$26(java.lang.Object, java.lang.String):org.luaj.vm2.LuaValue`
20. `lua/LuaTypes.java` — `com.exampletest.mobilestudiotest.lua.LuaTypes._init_$lambda$47(java.lang.Object, java.lang.String):org.luaj.vm2.LuaValue`
21. `lua/LuaTypes.java` — `com.exampletest.mobilestudiotest.lua.LuaTypes._init_$lambda$55(java.lang.Object, java.lang.String):org.luaj.vm2.LuaValue`
22. `lua/LuaTypes.java` — `com.exampletest.mobilestudiotest.lua.LuaTypes._init_$lambda$62(java.lang.Object, java.lang.String):org.luaj.vm2.LuaValue`
23. `lua/PluginApi.java` — `com.exampletest.mobilestudiotest.lua.PluginApi.call(com.exampletest.mobilestudiotest.lua.LuaRuntime, com.exampletest.mobilestudiotest.model.Instance, java.lang.String, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs`
24. `lua/PluginApi.java` — `com.exampletest.mobilestudiotest.lua.PluginApi.pluginMouse$lambda$10(com.exampletest.mobilestudiotest.lua.LuaRuntime, org.luaj.vm2.LuaTable, org.luaj.vm2.LuaValue, org.luaj.vm2.LuaValue):org.luaj.vm2.LuaValue`
25. `lua/RobloxGlobals.java` — `com.exampletest.mobilestudiotest.lua.RobloxGlobals.install$lambda$15(org.luaj.vm2.Globals, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs`
26. `lua/RobloxServices.java` — `com.exampletest.mobilestudiotest.lua.RobloxServices.call(com.exampletest.mobilestudiotest.lua.InstanceApi, com.exampletest.mobilestudiotest.lua.LuaRuntime, com.exampletest.mobilestudiotest.model.Instance, java.lang.String, org.luaj.vm2.Varargs):org.luaj.vm2.Varargs`
27. `model/Enums.java` — `com.exampletest.mobilestudiotest.model.Enums.typeForProp(java.lang.String, java.lang.String):java.lang.String`
28. `render/Draco.java` — `com.exampletest.mobilestudiotest.render.Draco.decodePortable(com.exampletest.mobilestudiotest.render.Draco$AttDecoder, int, com.exampletest.mobilestudiotest.render.Draco$Reader):void`
29. `render/SceneRenderer.java` — `com.exampletest.mobilestudiotest.render.SceneRenderer.batchSpec(com.exampletest.mobilestudiotest.model.Instance):java.util.List`
30. `ui/Autocomplete.java` — `com.exampletest.mobilestudiotest.ui.Autocomplete.resolveRoot(java.lang.String, com.exampletest.mobilestudiotest.core.Studio, com.exampletest.mobilestudiotest.model.Instance, java.lang.String, int, int):com.exampletest.mobilestudiotest.ui.Autocomplete$T`
31. `ui/Autocomplete.java` — `com.exampletest.mobilestudiotest.ui.Autocomplete.member(com.exampletest.mobilestudiotest.ui.Autocomplete$T, com.exampletest.mobilestudiotest.ui.Autocomplete$Step, com.exampletest.mobilestudiotest.core.Studio):com.exampletest.mobilestudiotest.ui.Autocomplete$T`
32. `ui/Autocomplete.java` — `com.exampletest.mobilestudiotest.ui.Autocomplete.completeString(com.exampletest.mobilestudiotest.core.Studio, com.exampletest.mobilestudiotest.model.Instance, java.lang.String, int, int):com.exampletest.mobilestudiotest.ui.Autocomplete$Result`
33. `ui/Fonts.java` — `com.exampletest.mobilestudiotest.ui.Fonts.forFamily(java.lang.String, int, boolean):com.exampletest.mobilestudiotest.ui.Fonts$Face`
34. `ui/GuiOverlay.java` — `com.exampletest.mobilestudiotest.ui.GuiOverlay.layoutChildren(com.exampletest.mobilestudiotest.model.Instance, android.graphics.RectF, float):java.util.List`
35. `ui/PropertiesPanel.java` — `com.exampletest.mobilestudiotest.ui.PropertiesPanel.categoryOf(com.exampletest.mobilestudiotest.model.Instance, java.lang.String, int):java.lang.String`
