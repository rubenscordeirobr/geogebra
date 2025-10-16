plugins {
    alias(libs.plugins.geogebra.idea)
    id("inspect-gradle-config")
}

description = "GeoGebra"

subprojects {
    afterEvaluate {
        // forward command line properties to every test JVM
        tasks.withType<Test>().configureEach {
            project.properties.forEach { (property, value) ->
                if (value is String) {
                    systemProperty(property.trim(), value)
                }
            }
        }
    }
}
