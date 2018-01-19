package AutoEditor;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        char alliance;
        // load the image
        Image fieldImage;
        alliance = getAlliance();
        if (alliance == 'r')
            fieldImage = new Image("https://i.imgur.com/vLhJBfP.png"); //TODO: Get this to copy to output directory
        else
            fieldImage = new Image("https://i.imgur.com/T5LGLla.png");

        // add field image to it's container and double it's size
        ImageView field = new ImageView();
        field.setImage(fieldImage);
        field.preserveRatioProperty().setValue(true);

        // The field's canvas (to draw on)
        Canvas fieldCanvas = new Canvas(100, 100);
        fieldCanvas.getGraphicsContext2D().setFill(Color.BEIGE);
        fieldCanvas.getGraphicsContext2D().fillRect(0, 0, 100, 100);
        // fieldCanvas.setTranslateX(field.getTranslateX());
        // fieldCanvas.setTranslateY(field.getTranslateY());

        StackPane fieldPane = new StackPane(); // for the field and its canvas
        fieldPane.getChildren().addAll(field, fieldCanvas);
        fieldPane.setAlignment(Pos.TOP_LEFT);

        Group root = new Group();
        root.getChildren().addAll(fieldPane);
        root.setTranslateX(0);
        root.setTranslateY(0);

        stage.setTitle("Auto Editor");
        stage.setScene(new Scene(root, 1280, 720));
        stage.setMaximized(true);
        stage.show();
        onResize(stage, field, fieldCanvas);

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            onResize(stage, field, fieldCanvas);
        });

        field.setPickOnBounds(true); // allows clicking on transparent pixels
        field.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            System.out.println(Double.toString(x) + " " + Double.toString(y));
        });

        fieldCanvas.setOnMouseClicked(e -> {
            GraphicsContext gc = fieldCanvas.getGraphicsContext2D();
            gc.setFill(Color.GREEN);

            gc.fillOval(e.getX(), e.getY(), 10, 10);
        });
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    char getAlliance() {
        // dummy function
        return 'r';
    }

    void onResize(Stage stage, ImageView field, Canvas fieldCanvas) {
        System.out.println(field.getFitWidth() + " : " + field.getFitHeight());
        field.setFitHeight(stage.getHeight() - 50);
        fieldCanvas.setWidth(500); //TODO: Find a way to properly scale this with field width (field.getFitWidth == 0)
        fieldCanvas.setHeight(field.getFitHeight());
    }
}
