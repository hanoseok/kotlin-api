import net.researchgate.release.GitAdapter
import net.researchgate.release.ReleaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun ReleaseExtension.git(configure: GitAdapter.GitConfig.() -> Unit) =
    (getProperty("git") as GitAdapter.GitConfig).configure()

object DependencyVersions {
    const val coroutinesVersion = "1.4.2"
    const val reactorKotlinExtensionsVersion = "1.1.2"
    const val jacksonVersion = "2.12.1"
    const val springmockkVersion = "3.0.1"
    const val junit5Version = "5.7.1"
    const val okhttpVersion = "4.8.1"
    const val logstashLogbackEncoderVersion = "6.6"
    const val testContainersVersion = "1.15.2"
    const val springRetryVersion = "1.3.1"
    const val jettyReactiveHttpClientVersion = "1.1.5"        // spring boot가 jetty 10, 11을 지원할때 까지 버전 업 불가
    const val springfoxVersion = "3.0.0"
    const val redissonVersion = "3.15.3"
    const val nettyForRedissonVersion = "4.1.59.Final"
    const val sccCommonsVersion = "0.2.0"
    const val balloonsVersion = "0.3.12"
    const val springKafkaVersion = "2.7.3"
}

plugins {
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    // ./gradlew dependencyUpdates -Drevision=release
    id("com.github.ben-manes.versions") version "0.36.0"
    id("net.researchgate.release") version "2.8.1"

    kotlin("jvm") version "1.4.30"
    kotlin("plugin.spring") version "1.4.30"
}

group = "com.kakao.aston"
java.sourceCompatibility = JavaVersion.VERSION_11

release {
    scmAdapters = listOf(GitAdapter::class.java)
    tagTemplate = "${version}-production"

    git {
        requireBranch = "master"
    }
}

repositories {
    mavenCentral()
    maven("https://packages.confluent.io/maven/")
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.retry:spring-retry:${DependencyVersions.springRetryVersion}")
    implementation("org.springframework:spring-aspects")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${DependencyVersions.jacksonVersion}")
    implementation("net.logstash.logback:logstash-logback-encoder:${DependencyVersions.logstashLogbackEncoderVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${DependencyVersions.coroutinesVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${DependencyVersions.coroutinesVersion}")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:${DependencyVersions.reactorKotlinExtensionsVersion}")
    implementation("io.springfox:springfox-boot-starter:${DependencyVersions.springfoxVersion}")
    implementation("org.springframework.kafka:spring-kafka:${DependencyVersions.springKafkaVersion}")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("junit", "junit")
        exclude(module = "mockito-core")
    }
    testImplementation("org.junit.jupiter:junit-jupiter:${DependencyVersions.junit5Version}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${DependencyVersions.junit5Version}")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:2.24.0")
    testImplementation("net.javacrumbs.json-unit:json-unit-json-path:2.24.0")

    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testImplementation("com.ninja-squad:springmockk:${DependencyVersions.springmockkVersion}")
    testImplementation("com.squareup.okhttp3:mockwebserver:${DependencyVersions.okhttpVersion}")
    testImplementation("com.squareup.okhttp3:okhttp:${DependencyVersions.okhttpVersion}")
    testImplementation("org.testcontainers:testcontainers:${DependencyVersions.testContainersVersion}")
    testImplementation("org.testcontainers:junit-jupiter:${DependencyVersions.testContainersVersion}")
    testImplementation("org.testcontainers:rabbitmq:${DependencyVersions.testContainersVersion}")
    testImplementation("org.testcontainers:mongodb:${DependencyVersions.testContainersVersion}")
    testImplementation("org.testcontainers:kafka:${DependencyVersions.testContainersVersion}")
    testImplementation("net.joshka:junit-json-params:1.1.0")
    testImplementation("org.glassfish:javax.json:1.1.4")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    systemProperty("spring.profiles.active", "test")
    useJUnitPlatform()
}

