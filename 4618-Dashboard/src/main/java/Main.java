import edu.wpi.first.wpilibj.networktables.NetworkTable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;
import javafx.scene.canvas.Canvas;

import java.lang.reflect.Field;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        //NetworkTable.setClientMode();
        //NetworkTable.initialize();
        //NetworkTable table = NetworkTable.getTable("CustomDashboard");

        Group root = new Group();
        window.setTitle("Dashboard");
        window.setScene(new Scene(root, 1280, 720));
        window.show();

        Pane menu = new Pane();
        menu.setStyle("-fx-background-color: red");
        menu.setPrefSize(100, window.getHeight());

        Pane content = new Pane();
        content.setPrefSize(window.getWidth() - menu.getPrefWidth(), window.getHeight());
        content.setLayoutX(menu.getPrefWidth());

        Pane home_pane = new Pane();
        home_pane.setStyle("-fx-background-color: blue");
        home_pane.setPrefSize(content.getPrefWidth(), content.getPrefHeight());

        Pane drive_pane = new Pane();
        drive_pane.setStyle("-fx-background-color: yellow");
        drive_pane.setPrefSize(content.getPrefWidth(), content.getPrefHeight());

        Button home = new Button("Home");
        home.setOnAction(event -> {
            content.getChildren().clear();
            content.getChildren().add(home_pane);
        });
        home.setPrefSize(menu.getPrefWidth(), 40.0);

        Button drive = new Button("Drive");
        drive.setOnAction(event -> {
            content.getChildren().clear();
            content.getChildren().add(drive_pane);
        });
        drive.setPrefSize(menu.getPrefWidth(), 40.0);
        drive.setLayoutY(40);

        //dummy chart
        //axes
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Speed");
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis, yAxis);

        Tooltip dummytooltip = new Tooltip();
        try {
            Field fieldBehavior = Tooltip.class.getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(dummytooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        XYChart.Series leftSpeed = new XYChart.Series();
        leftSpeed.setName("Left Speed");

        leftSpeed.getData().add(new XYChart.Data(1, 10));
        leftSpeed.getData().add(new XYChart.Data(2, 6));
        leftSpeed.getData().add(new XYChart.Data(4, 12));

        chart.getData().addAll(leftSpeed);
        home_pane.getChildren().add(chart);

        for (XYChart.Series<Number, Number> s : chart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip(d.getYValue().toString()));
            }
        }

        double angle = 45;

        //textboxes
        TextField p = new TextField("Angle");
        p.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");

        Button getp = new Button("Set Angle");
        getp.setOnAction(event -> {

        });
        home_pane.getChildren().addAll(p, getp);

        content.getChildren().add(home_pane);



        //compass
        Canvas canvas = new Canvas(250, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(54, 78, 66));
        gc.fillOval(0, 0, 250, 250);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        gc.strokeLine(125, 125, 125 * Math.cos((Math.PI / 180) * angle) , 125 * Math.sin((Math.PI / 180) * angle)); //125 is the middle

        content.getChildren().add(canvas);
        canvas.setTranslateX(100);
        canvas.setTranslateY(400);

        menu.getChildren().add(home);
        menu.getChildren().add(drive);

        root.getChildren().add(menu);
        root.getChildren().add(content);
        //window.setFullScreen(true);
    }
}
