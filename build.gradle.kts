import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun
import java.util.*

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
    // Spring Boot starters
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // Добавлен JPA starter

    // Мониторинг и метрики
    implementation("io.micrometer:micrometer-registry-prometheus")

    // Базы данных (раскомментируйте нужную)
    // runtimeOnly("com.h2database:h2")
    // runtimeOnly("org.postgresql:postgresql")

    // Утилиты
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // Тестирование
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<BootJar>("bootJar") {
    // Упрощенная конфигурация для Spring Boot 3.5.5
    layered {
        enabled = true
    }

    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Start-Class" to "org.planicore.PlanicoreApplication",
                "Main-Class" to "org.springframework.boot.loader.launch.JarLauncher",
                "Built-By" to System.getProperty("user.name"),
                "Built-Date" to Date().toString(),
                "Built-JDK" to System.getProperty("java.version"),
                "Spring-Boot-Version" to "3.5.5"
            )
        )
    }
}

tasks.named<BootRun>("bootRun") {
    systemProperty("spring.profiles.active", "development")
}

// Конфигурация для разработки
springBoot {
    buildInfo()
}

// Настройка компиляции
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}