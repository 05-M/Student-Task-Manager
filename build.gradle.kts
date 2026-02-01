plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false // استخدم الـ alias الجديد
    alias(libs.plugins.ksp) apply false
}