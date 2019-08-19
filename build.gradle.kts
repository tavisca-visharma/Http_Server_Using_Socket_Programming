plugins {
    java
    id("org.sonarqube") version "2.7"
    jacoco
}
// end::apply-plugin[]

// tag::jacoco-configuration[]
jacoco {
    toolVersion = "0.8.4"
    reportsDir = file("$buildDir/customJacocoReportDir")
}

group = "com.tavisca.workshops"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_10
    targetCompatibility = JavaVersion.VERSION_1_10
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.tavisca.workshops.HttpServer.ServerControllerAndClientHandler.ServerController"
    }
}

tasks.named<Test>("test") {
    dependsOn("cleanTest")
    useJUnitPlatform {
        includeEngines("junit-jupiter")
        excludeTags("to_be_done-tests")
    }

    testLogging {
        showExceptions = true
        events("passed", "skipped", "failed")
    }
}