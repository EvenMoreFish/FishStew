rootProject.name = "FishStew"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // compileOnly dependencies
            library("paper-api", "io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

            library("evenmorefish-plugin", "com.oheers.evenmorefish:even-more-fish-plugin:2.1.2")
            library("evenmorefish-api", "com.oheers.evenmorefish:even-more-fish-api:2.1.2")
            bundle("evenmorefish", listOf("evenmorefish-plugin", "evenmorefish-api"))

            // implementation dependencies

            // paperLibrary dependencies

            // Gradle plugins
            plugin("shadow", "com.gradleup.shadow").version("9.2.2")
            plugin("plugin-yml", "de.eldoria.plugin-yml.bukkit").version("0.8.0")
        }
    }
}
