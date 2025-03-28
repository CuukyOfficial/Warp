import java.security.MessageDigest
import java.util.Base64

plugins {
    id("java-library")
    id("maven-publish")
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
    maven("https://repo.varoplugin.de/repository/maven-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

val internal: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
    isTransitive = false
}

val runtimeDownload: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

fun DependencyHandler.modularInternal(dependencyNotation: Any, localFileName: String) : Dependency? {
    val file = file("${rootDir}/libs/${localFileName}.jar")
    return if (file.exists())
        this.add("internal", files(file))
    else
        this.add("internal", dependencyNotation)
}

dependencies {
    modularInternal("de.varoplugin:cfw:1.0.0-ALPHA-18", "CFW")
    implementation("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

	runtimeDownload("io.github.almighty-satan.slams:slams-standalone:1.1.4") {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "annotations")
    }
    runtimeDownload("io.github.almighty-satan.slams:slams-parser-jaskl:1.1.4") {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "slams-core")
        exclude(module = "annotations")
    }
    runtimeDownload("io.github.almighty-satan.slams:slams-papi:1.1.4") {
        exclude(group = "io.github.almighty-satan.jaskl")
        exclude(module = "slams-core")
        exclude(module = "annotations")
    }
    runtimeDownload("io.github.almighty-satan.jaskl:jaskl-yaml:1.5.0")
	runtimeDownload("com.github.cryptomorin:XSeries:11.3.0")
}

tasks.jar {
    if (project.hasProperty("destinationDir"))
        destinationDirectory.set(file(project.property("destinationDir").toString()))
    if (project.hasProperty("fileName"))
        archiveFileName.set(project.property("fileName").toString())

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(internal.map { if (it.isDirectory) it else zipTree(it) })
}

tasks.processResources {
    outputs.upToDateWhen { false }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(sourceSets.main.get().resources.srcDirs) {
        include("**/plugin.yml")
        expand("name" to project.name, "version" to project.version)
    }
}

val mdSha512: MessageDigest = MessageDigest.getInstance("SHA-512")
fun File.sha512() : ByteArray = mdSha512.digest(this.readBytes())
fun ByteArray.base64() : String = Base64.getEncoder().encodeToString(this)
