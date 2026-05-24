plugins {
    id("java")
}

group = "com.flatorte"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

val lwjglVersion = "3.3.3"
val jomlVersion = "1.10.5"
val imguiVersion = "1.86.11"

val os = System.getProperty("os.name").lowercase()
val arch = System.getProperty("os.arch").lowercase()

val lwjglNatives = when {
    os.contains("win") -> "natives-windows"
    os.contains("mac") -> if (arch.contains("aarch64") || arch.contains("arm")) "natives-macos-arm64" else "natives-macos"
    os.contains("linux") -> if (arch.contains("aarch64") || arch.contains("arm")) "natives-linux-arm64" else "natives-linux"
    else -> throw Error("Système d'exploitation non supporté")
}

dependencies {
    implementation("org.joml:joml:$jomlVersion")
    val lwjglModules = listOf(
        "",
        "-glfw",
        "-opengl",
        "-assimp",
        "-stb",
        "-nanovg"
    )

    for (module in lwjglModules) {
        implementation("org.lwjgl:lwjgl$module:$lwjglVersion")
        runtimeOnly("org.lwjgl:lwjgl$module:$lwjglVersion:$lwjglNatives")
    }

    implementation("io.github.spair:imgui-java-binding:$imguiVersion")
    implementation("io.github.spair:imgui-java-lwjgl3:$imguiVersion")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}