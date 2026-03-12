plugins {
    id("java")
}

group = "com.rdev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation(platform("org.assertj:assertj-bom:3.27.7"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.platform:junit-platform-suite-engine")
    testImplementation("io.cucumber:cucumber-java:7.34.2")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.34.2")
    testImplementation("org.assertj:assertj-core")
}

tasks.test {
    useJUnitPlatform()
}