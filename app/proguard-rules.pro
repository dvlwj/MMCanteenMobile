# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote okhttp3.internal.Platform

# java.nio.file.* usage which cannot be used at runtime. Animal sniffer annotation.
-dontwarn okio.Okio

# JDK 7-only method which is @hide on Android. Animal sniffer annotation.
-dontwarn okio.DeflaterSink

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# Library not included in proguard and still like real
#-keep com.github.medyo

#which will keep only the no-arg constructor of the service defined in META-INF/services/kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader. Anything more is keeping too much.
#-keep class kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoaderImpl

#-keepnames class kotlin.reflect.***
-dontwarn kotlin.reflect.jvm.internal.**
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor
#-dontwarn kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl
#-dontwarn kotlin.reflect.jvm.internal.impl.load.java.JavaClassFinder
#-dontwarn kotlin.reflect.jvm.internal.impl.resolve.OverridingUtil
#-dontwarn kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor
#-dontwarn kotlin.reflect.jvm.internal.impl.types.DescriptorSubstitutor
#-dontwarn kotlin.reflect.jvm.internal.impl.types.TypeConstructor