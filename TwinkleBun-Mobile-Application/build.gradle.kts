// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
}

 android {
     defaultConfig {
         …
         minSdkVersion 24
     }
 }
allprojects {
    repositories {
        google()
        // Add other repositories here if needed
    }
}
dependencies {
    …
    implementation 'com.google.ar:core:1.33.0'
}
