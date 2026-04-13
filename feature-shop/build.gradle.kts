plugins {
    id("diary.android.library")
    id("diary.hilt")
}

dependencies {
    implementation(project(":core-model"))
    implementation(project(":core-designsystem"))
    implementation(project(":core-common"))
    implementation(project(":core-navigation"))
    implementation(project(":core-domain"))
    implementation(project(":core-preferences"))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.accompanist.navigation.animation)
}
