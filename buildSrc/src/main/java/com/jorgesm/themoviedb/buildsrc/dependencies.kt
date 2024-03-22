package com.jorgesm.themoviedb.buildsrc

object Libs{
    
    const val androidGradlePlugin = "com.android.tools.build:gradle:8.2.1"
    const val playServicesLocation = "com.google.android.gms:play-services-location:19.0.1"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.42.0"
    
    object Kotlin{
        private const val version = "1.7.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        
        object Coroutines{
            private const val  version ="1.6.0"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }
    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        const val material = "com.google.android.material:material:1.5.0"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        
        object Activity {
            private const val version = "1.4.0"
            const val ktx = "androidx.activity:activity-ktx:$version"
        }
        object Lifecycle{
            private const val version = "2.4.1"
            const val runtimeKtx ="androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModelKtx ="androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }
        object Navigation {
            private const val version ="2.5.0"
            const val gradlePlugin ="androidx.navigation:navigation-safe-args-gradle-plugin:$version"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }
        object Room {
            private const val version ="2.6.1"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val testing = "androidx.room:room-testing:$version"
            
        }
    }
    object OkHttp3 {
        private const val version = "4.9.2"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"
    }
    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$version"
    }
    object Hilt {
        private const val version = "2.42"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
    }
    object Glide {
        private const val version = "4.12.0"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }
    object Junit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }
    object Mockito{
        private const val version ="4.0.0"
        const val kotlin = "org.mockito.kotlin:mockito-kotlin:$version"
        const val inline = "org.mockito:mockito-inline:$version"
    }
    object Arrow {
        private const val version = "1.0.1"
        const val core = "io.arrow-kt:arrow-core:$version"
    }
    object JavaX{
        const val inject = "javax.inject:javax.inject:1"
    }
    const val turbine = "app.cash.turbine:turbine:0.7.0"
}