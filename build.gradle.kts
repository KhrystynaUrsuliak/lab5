plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val jupiter = "5.8.1"
val lombok = "1.18.34"

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiter")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$jupiter")

    compileOnly("org.projectlombok:lombok:$lombok")
    annotationProcessor("org.projectlombok:lombok:$lombok")

    testCompileOnly("org.projectlombok:lombok:$lombok")
    testAnnotationProcessor("org.projectlombok:lombok:$lombok")

    implementation("com.mysql:mysql-connector-j:8.1.0")
    implementation("org.mongodb:mongodb-driver-sync:5.2.0")
    implementation("com.zaxxer:HikariCP:6.0.0")
    implementation("com.fasterxml.uuid:java-uuid-generator:5.1.0")
}

tasks.test {
    useJUnitPlatform()
}
