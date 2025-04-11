plugins {
    id("java-library")
    id("maven-publish")
    id("com.gradleup.shadow") version "8.3.6"
}

group = "de.cuuky"
version = "2.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
    withJavadocJar()
}

tasks.compileJava { options.encoding = "UTF-8" }
tasks.javadoc { options.encoding = "UTF-8" }

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.varoplugin.de/releases/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation("de.varoplugin:cfw:1.0.0-ALPHA-21") {
        exclude(group = "org.spigotmc")
    }
    shadow("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    val slamsVersion = "1.2.0"
    implementation("io.github.almighty-satan.slams:slams-standalone:$slamsVersion") {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "annotations")
    }
    implementation("io.github.almighty-satan.slams:slams-parser-jaskl:$slamsVersion") {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "slams-core")
        exclude(module = "annotations")
    }
    implementation("io.github.almighty-satan.slams:slams-papi:$slamsVersion") {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "slams-core")
        exclude(module = "annotations")
    }
    implementation("io.github.almighty-satan.jaskl:jaskl-yaml:1.6.1")
}

tasks.jar {
    if (project.hasProperty("destinationDir"))
        destinationDirectory.set(file(project.property("destinationDir").toString()))
    if (project.hasProperty("fileName"))
        archiveFileName.set(project.property("fileName").toString())
}

tasks.processResources {
    outputs.upToDateWhen { false }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(sourceSets.main.get().resources.srcDirs) {
        include("**/plugin.yml")
        expand("name" to project.name, "version" to project.version)
    }
}

tasks.shadowJar {
    isEnableRelocation = true
    relocationPrefix = "de.cuuky.warp.dependencies"
}

tasks.build {
    finalizedBy(tasks.shadowJar)
}
