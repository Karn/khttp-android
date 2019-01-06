![kHttp](./docs/assets/khttp-android-logo.svg)

## kHttp Android
Lightweight HTTP requests library for Android.

[![Kotlin](https://img.shields.io/badge/Kotlin-1.3.11-blue.svg?style=flat-square)](http://kotlinlang.org)
[![RxJava](https://img.shields.io/badge/RxJava-2.1.10-green.svg?style=flat-square)](http://github.com/ReactiveX/RxJava)
[![Build Status](https://img.shields.io/travis/Karn/khttp-android.svg?style=flat-square)](https://travis-ci.org/Karn/khttp-android)
[![Codecov](https://img.shields.io/codecov/c/github/karn/khttp-android.svg?style=flat-square)](https://codecov.io/gh/Karn/khttp-android)
[![GitHub (pre-)release](https://img.shields.io/github/release/karn/khttp-android/all.svg?style=flat-square)
](./../../releases)

kHttp Android is similar to Python's `requests` module. This library has been adapted from [kHttp by jkcclemens](https://github.com/jkcclemens/khttp) which is no longer maintained.

#### GETTING STARTED
kHttp Android (pre-)releases are available via JitPack. It is recommended that  a specific release version is selected when using the library in production as there may be breaking changes at anytime.

> **Tip:** Test out the canary channel to try out features by using the latest develop snapshot; `develop-SNAPSHOT`.

```Groovy
// Project level build.gradle
// ...
repositories {
    maven { url 'https://jitpack.io' }
}
// ...

// Module level build.gradle
dependencies {
    // Replace version with release version, e.g. 1.0.0-alpha, -SNAPSHOT
    implementation "io.karn:khttp-android:[VERSION]"
}
```

#### USAGE
The most basic case is as follows:

```Kotlin
// Get your IP synchronously
val ipAddress = get(url = "http://httpbin.org/ip").jsonObject

// Get your IP asynchronously
get(url = "http://httpbin.org/ip")
    .subscribe { response : Response ->
        val ipAddress = response.jsonObject
    }
```

#### CONTRIBUTING
There are many ways to [contribute](./.github/CONTRIBUTING.md), you can
- submit bugs,
- help track issues,
- review code changes.