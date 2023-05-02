plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    jacoco
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("org.jetbrains.compose") version "1.4.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Use the Kotlin JUnit 5 integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    // Use the JUnit 5 integration.
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")

    // This dependency is used by the application.
    implementation ("org.apache.commons:commons-csv:1.5")
    implementation("com.google.guava:guava:31.1-jre")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    implementation("org.xerial:sqlite-jdbc:3.41.2.1")
    implementation(compose.material3)
    implementation(compose.desktop.currentOs)

    // Logging
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    implementation("org.slf4j:slf4j-simple:1.7.29")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
    val failedTests = mutableListOf<TestDescriptor>()
    val skippedTests = mutableListOf<TestDescriptor>()

    addTestListener (object: TestListener {
        override fun beforeSuite(suite: TestDescriptor?) { }
        override fun beforeTest(testDescriptor: TestDescriptor?) { }
        override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
            when (result.resultType) {
                TestResult.ResultType.FAILURE -> failedTests.add(testDescriptor)
                TestResult.ResultType.SKIPPED -> skippedTests.add(testDescriptor)
                else -> {}
            }
        }
        override fun afterSuite(suite: TestDescriptor, result: TestResult) {
            if (suite.parent == null) { // root suite
                logger.lifecycle("####################################################################################")
                logger.lifecycle("Test result: ${result.resultType}")
                logger.lifecycle(
                    "Test summary: ${result.testCount} tests, " +
                            "${result.successfulTestCount} succeed, " +
                            "${result.skippedTestCount} skipped, " +
                            "${result.failedTestCount} failed."
                )
            }
        }
    })

}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("jacoco/report.xml"))
        csv.required.set(true)
        csv.outputLocation.set(layout.buildDirectory.file("jacoco/report.csv"))
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.jacocoTestCoverageVerification {
    classDirectories.setFrom( classDirectories.files.flatMap { fileTree(it) {
        include("**/RBBalancer.class", "**/AVLBalancer.class", "**/BINStruct", "**/AVLStruct.class", "**/BINStruct.class")
    } })
    dependsOn(tasks.jacocoTestReport)
    violationRules {
        rule {
            element = "CLASS"
            limit {
                counter = "BRANCH"
                minimum = 0.5.toBigDecimal()
            }
        }
        rule {
            element = "CLASS"
            limit {
                counter = "LINE"
                minimum = 0.6.toBigDecimal()
            }
        }
        rule {
            element = "CLASS"
            limit {
                counter = "METHOD"
                minimum = 0.9.toBigDecimal()
            }
        }
    }
}

application {
    mainClass.set("trees.AppKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}