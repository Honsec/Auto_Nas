# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\program\Androidandroid_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontoptimize  # 不优化输入的类文件
-dontusemixedcaseclassnames  # 混淆时不会产生形形色色的类名
-verbose  #混淆时是否记录日志
-dontskipnonpubliclibraryclasses  #指定不去忽略非公共的库类。
-dontskipnonpubliclibraryclassmembers   #指定不去忽略包可见的库类的成员。

#-optimizationpasses 5 # 指定代码的压缩级别
#-dontusemixedcaseclassnames  # 是否使用大小写混合
#-dontskipnonpubliclibraryclasses
#-dontpreverify  # 混淆时是否做预校验
#-verbose  # 混淆时是否做预校验
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法
 # 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService
-keep public class com.google.android.**


#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除
-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}



-keepclasseswithmembernames class * { # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembernames class * { # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {# 保持自定义控件类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keep class com.android.vending.licensing.ILicensingService
-keep class android.support.v4.** { *; }
-keep class javax.mail.**{*;}
-keep class com.sun.mail.**{*;}
-keep class javax.activation.**{*;}
-keep class org.apache.harmony.**{*;}
-keep class java.security.**{*;}

-keep public class android.net.**{*;}
-keep public class android.webkit.**{*;}

-dontwarn android.net.**
-dontwarn android.webkit.**




#Event bus
-keepclassmembers class ** {
    public void onEvent*(**);
}
#eventbus
-keep class de.greenrobot.**{*;}
-dontwarn de.greenrobot.**


# Only required if you use AsyncExecutor
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keepclassmembers class com.miniram.donpush.cn.base.ui.BaseActivity{
    private void invokeFragmentManagerNoteStateNotSaved();
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

#multidex
-keep class com.android.test.**{*;}
-dontwarn com.android.test.**

#common
-keep class com.android.support.**{*;}
-dontwarn com.android.support.**
-keep class android.support.**{*;}
-dontwarn android.support.**


#google
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames class * implements android.os.Parcelable
-keepclassmembers class * implements android.os.Parcelable {
  public static final *** CREATOR;
}

-keep @interface android.support.annotation.Keep
-keep @android.support.annotation.Keep class *
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
  @android.support.annotation.Keep <methods>;
}

-keep @interface com.google.android.gms.common.annotation.KeepName
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
  @com.google.android.gms.common.annotation.KeepName *;
}

-keep @interface com.google.android.gms.common.util.DynamiteApi
-keep public @com.google.android.gms.common.util.DynamiteApi class * {
  public <fields>;
  public <methods>;
}

-dontwarn android.security.NetworkSecurityPolicy