plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.routinequestcse5236"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.routinequestcse5236"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packagingOptions {
        exclude("META-INF/atomicfu.kotlin_module")
    }
}

dependencies {
implementation("androidx.appcompat:appcompat:$rootProject.appCompatVersion")
implementation("androidx.activity:activity-ktx:$rootProject.activityVersion")

// Dependencies for working with Architecture components
// You'll probably have to update the version numbers in build.gradle (Project)

// Room components
implementation("androidx.room:room-ktx:$rootProject.roomVersion")
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.databinding:compiler:3.2.0-alpha11")
//kapt("androidx.room:room-compiler:$rootProject.roomVersion")
    kapt("androidx.room:room-compiler:2.4.3")

    androidTestImplementation("androidx.room:room-testing:$rootProject.roomVersion")

// Lifecycle components
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$rootProject.lifecycleVersion")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:$rootProject.lifecycleVersion")
implementation("androidx.lifecycle:lifecycle-common-java8:$rootProject.lifecycleVersion")

// Kotlin components
implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$rootProject.coroutines")
api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$rootProject.coroutines")

// UI
implementation("androidx.constraintlayout:constraintlayout:$rootProject.constraintLayoutVersion")
implementation("com.google.android.material:material:$rootProject.materialVersion")

// Testing
testImplementation("junit:junit:$rootProject.junitVersion")
androidTestImplementation("androidx.arch.core:core-testing:$rootProject.coreTestingVersion")
/*androidTestImplementation("androidx.test.espresso:espresso-core:$rootProject.espressoVersion", {
exclude group: 'com.android.support'; module: 'support-annotations'
})*/

androidTestImplementation("androidx.test.ext:junit:$rootProject.androidxJunitVersion")

    implementation("org.jetbrains.kotlin:kotlin-stdlib")

//old
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
