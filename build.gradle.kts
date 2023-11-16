plugins {
    kotlin("jvm") version "1.9.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.google.com/")
    maven("https://plugins.gradle.org/m2/")
}

dependencies {
    //implementation("org.seleniumhq.selenium:selenium-java:4.0.0")

    implementation(files("C:\\Program Files\\selenium-server-standalone-3.141.59.jar"))
    implementation("io.github.bonigarcia:webdrivermanager:5.5.3")
    implementation("com.google.api-client:google-api-client:1.30.2")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.32.1")
    implementation("com.google.apis:google-api-services-sheets:v4-rev612-1.25.0")
    implementation(files("C:\\Program Files\\API Google/google-api-services-sheets-v4-rev612-1.25.0.jar"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation(kotlin("stdlib"))
    implementation("com.github.johnrengelman.shadow:com.github.johnrengelman.shadow.gradle.plugin:7.1.2")

}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.Main" // Adjust with your actual package and class name
    }

    from(sourceSets.main.get().output) {
        include("META-INF/**")
    }
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "org.example.Main"
    }
//    from(sourceSets.main.get().output) {
//        include("META-INF/**")
//    }
}

kotlin {
    jvmToolchain(17)

}