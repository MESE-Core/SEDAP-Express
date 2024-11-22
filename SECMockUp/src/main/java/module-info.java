module secmockup {

    exports de.bundeswehr.sedap.express.mockup;

    opens de.bundeswehr.sedap.express.mockup;

    requires java.desktop;
    requires javafx.swing;
    requires transitive jdk.httpserver;
    requires transitive javafx.graphics;
    requires transitive javafx.fxml;
    requires transitive javafx.controls;
    requires transitive jogamp.fat;
    requires transitive WorldWindJava;
    requires transitive SEDAPExpress;

}