import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/* This makes a window with one button that prints "Hello world" to the console
 */

public class Main extends Application{
    //don't know yet if ill need to keep this here or just in the function but we'll see
    StackPane root;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        root = new StackPane();

        window.setTitle("Dashboard");

        //create new button and assign some code to it
        Button btn = new Button("Hello world"); //this is actually really similar to TKinter
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello world!");
            }
        });

        root.getChildren().add(btn); //i guess add the button to the window
        window.setScene(new Scene(root, 1280, 720)); //create new window, specifying resolution
        window.show();
    }
}
