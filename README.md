# Android - Clean Architecture - Kotlin [![CircleCI](https://circleci.com/gh/kingpowerclick/HIS.Android.svg?style=shield&circle-token=34ac99ace696889e6e948c0cdd673ffcd3d3a28d)](https://circleci.com/gh/kingpowerclick/HIS.Android) 
The purpose of this repo is to follow up Clean Architecture principles by bringing them to Android. It is worth saying that the idea is to take advantage of the Kotlin Programming Language features plus also pull in lessons learned and ideas from other interesting aproaches like Functional Programming.

## Blog post with implementation details explanation:
[Architecting Android… Reloaded](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/)

## Other material worth reading:

[Architecting Android…The clean way?](http://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)

[Architecting Android…The evolution](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/)

[Tasting Dagger 2 on Android](http://fernandocejas.com/2015/04/11/tasting-dagger-2-on-android/)

[Clean Architecture…Dynamic Parameters in Use Cases](http://fernandocejas.com/2016/12/24/clean-architecture-dynamic-parameters-in-use-cases/)

### ----------------------------------------------------------------------------------------------

## Local Development
Here are some useful Gradle/adb commands for executing this example:

 * `./gradlew deployDebug` - Builds and install the debug apk on the current connected device.
 * `./gradlew runUnitTests` - Execute all unit tests (both unit and integration).
 * `./gradlew runAcceptanceTests` - Execute all acceptance tests (UI Automate test).
 * `./gradlew runCoverageTests` - Execute coverage tests.
