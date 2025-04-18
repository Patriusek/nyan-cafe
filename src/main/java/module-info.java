module org.example.nyan_cafe {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.nyan_cafe to javafx.fxml;
    exports org.example.nyan_cafe;
}