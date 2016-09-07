# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\IDE\android-sdk-windows/tools/proguard/proguard-android.txt
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

#volley
-keep class com.android.volley.**{*;}
-dontwarn com.android.volley.**
#volley
-keep class com.mcxiaoke.volley.**{*;}
-dontwarn com.mcxiaoke.volley.**

#apache
-keep class org.jbundle.util.**{*;}
-dontwarn org.jbundle.util.**