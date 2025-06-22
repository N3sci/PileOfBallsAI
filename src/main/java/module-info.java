module application.pileofballs {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.pileofballs to javafx.fxml;
    exports application.pileofballs;
    exports application.pileofballs.Controller;
    opens application.pileofballs.Controller to javafx.fxml;
}