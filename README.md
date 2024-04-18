# Metaborg Gradle Convention plugin
[![Build][github-build-badge]][github-build]
[![License][license-badge]][license]
[![Maven Release][maven-release-badge]][maven-release]
[![GitHub Release][github-release-badge]][github-release]

This is a Gradle plugin that provides an opinionated configuration of Metaborg (Spoofax) projects. It is used to share common configurations between projects. It contains the following plugins:

- `org.metaborg.gradle-convention.compositebuild`: Convention for composite builds.
- `org.metaborg.gradle-convention.java`: Convention for Java and Kotlin projects using JUnit.


## Quick Start
To use this plugin, apply it using the `plugins` block.

```kotlin
plugins {
    id("org.metaborg.convention.java") version "1.0.0"
}
```


## License
Copyright 2024 Delft University of Technology

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at <http://www.apache.org/licenses/LICENSE-2.0>.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an _"as is" basis, without warranties or conditions of any kind_, either express or implied. See the License for the specific language governing permissions and limitations under the License.


[github-build-badge]: https://github.com/metaborg/metaborg-gradle-convention/actions/workflows/build.yml/badge.svg
[github-build]: https://github.com/metaborg/metaborg-gradle-convention/actions
[license-badge]: https://img.shields.io/github/license/metaborg/metaborg-gradle-convention
[license]: https://github.com/metaborg/metaborg-gradle-convention/blob/main/LICENSE
[maven-release-badge]: https://img.shields.io/maven-central/v/com.example/myapp
[maven-release]: https://mvnrepository.com/artifact/com.example/myapp
[github-release-badge]: https://img.shields.io/github/v/release/metaborg/metaborg-gradle-convention
[github-release]: https://github.com/metaborg/metaborg-gradle-convention/releases