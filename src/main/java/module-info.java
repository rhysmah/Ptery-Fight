module ca.bcit.comp2522.termproject.secretwonders {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens ca.bcit.comp2522.termproject.secretwonders to javafx.fxml;
    exports ca.bcit.comp2522.termproject.secretwonders;
}