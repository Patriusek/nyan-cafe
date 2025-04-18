module org.example.nyan_cafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    opens org.example.nyan_cafe to javafx.fxml;
    exports org.example.nyan_cafe;
}