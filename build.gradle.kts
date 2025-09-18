plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.planicore"
version = "1.0.0"
description = "planicore"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Настройка слоев для оптимизации Docker образа
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    layered {
        // Добавьте этот параметр - он обязателен при использовании кастомного слоения
        layerOrder = listOf(
            "dependencies",
            "spring-boot-loader",
            "snapshot-dependencies",
            "application"
        )

        application {
            intoLayer("spring-boot-loader")
            intoLayer("dependencies")
            intoLayer("snapshot-dependencies")
            intoLayer("application")
        }
    }
}

// Настройка манифеста
tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Main-Class" to "org.planicore.PlanicoreApplication"
        )
    }
}