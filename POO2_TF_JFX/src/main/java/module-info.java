module org.example.poo2_tf_jfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.poo2_tf_jfx.controller to javafx.fxml;
    exports org.example.poo2_tf_jfx;
}