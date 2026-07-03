module tessera.toadkeep.toadkeepii {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    requires com.github.kwhat.jnativehook;
    requires org.freedesktop.dbus;
    // Unix-socket transport is loaded via ServiceLoader; requiring it keeps it
    // in the module graph for jlink images.
    requires org.freedesktop.dbus.transport.jre;

    opens tessera.toadkeep.toadkeepii to javafx.fxml;
    exports tessera.toadkeep.toadkeepii;
}