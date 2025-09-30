plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "edu.bsu.cs"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}
dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.json:json:20240303")
    implementation("org.slf4j:slf4j-nop:2.0.11")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("net.minidev:json-smart:2.5.0")
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "21.0.2"
    modules("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("edu.bsu.cs.Launcher")
}
