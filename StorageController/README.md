# Storage Controller

The storage controller is the brains of this project. It is written in Kotlin (because I wanted to learn it) and uses
[JSONDB](https://jsondb.io/) for storage. 

---
### Local Development / Running via an IDE

1. Make sure you have built/run the [StorageControllerUI](../StorageControllerUI) project. This sets up the
   `../data/web` directory with the UI.
2. Install IntelliJ.
3. Open the StorageController folder as a project.
4. Run the `main` in `com.michaelgrundvig.storage.Main.kt`.
---
### Local Development / Running via CLI

1. Make sure you have built/run the [StorageControllerUI](../StorageControllerUI) project. This sets up the 
`../data/web` directory with the UI.
2. Build the project with Gradle - use either `gradlew fatJar` or `gradlew.bat fatJar` depending on your system. This will
install Gradle if you don't have it already as well as pull down all dependencies and create an executable jar in the 
 build/libs folder. Yeah, Gradle is pretty slick :)
3. Make sure you have Java 17 or newer installed and on the path. 
4. Run `java -jar build/libs/StorageController-All-*.jar` - you need to replace the * with whatever version was
actually created. For instance, the name might look something like this `StorageController-All-1.0-SNAPSHOT.jar`.