import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainInterface extends Application {
    private final Arvore arvore = new Arvore();
    private final Tradutor tradutor = new Tradutor(arvore);
    private final Canvas canvas = new Canvas(1200, 600);


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        Label labelInserir = new Label("Inserir texto ou código Morse:");
        TextArea textEntrada = new TextArea();
        textEntrada.setPromptText("Digite aqui");
        textEntrada.setPrefHeight(150);
        textEntrada.setPrefWidth(300);

        Button buttonExecutar = new Button("Codificar/Decodificar");

        Label labelResultado = new Label("Resultado:");
        TextArea areaResultado = new TextArea();
        areaResultado.setEditable(false);
        areaResultado.setWrapText(true);
        areaResultado.setPrefHeight(150);
        areaResultado.setPrefWidth(300);

        VBox entradaBox = new VBox(5, labelInserir, textEntrada);
        entradaBox.setAlignment(Pos.CENTER);

        VBox resultadoBox = new VBox(5, labelResultado, areaResultado);
        resultadoBox.setAlignment(Pos.CENTER);

        HBox parteCima = new HBox(20, entradaBox, buttonExecutar, resultadoBox);
        parteCima.setPadding(new Insets(10));
        parteCima.setAlignment(Pos.CENTER);

        buttonExecutar.setOnAction(e -> {
            String entrada = textEntrada.getText();
            String resultado;
            if (entrada.matches("[.\\-/ ]+")) {
                resultado = tradutor.decodificar(entrada);
            } else {
                resultado = tradutor.codificar(entrada);
            }
            areaResultado.setText(resultado);
            desenharArvore();
        });

        BorderPane layoutPrincipal = new BorderPane();
        layoutPrincipal.setTop(parteCima);
        layoutPrincipal.setCenter(canvas);
        Scene scene = new Scene(layoutPrincipal, 1300, 800);
        primaryStage.setTitle("Codificador/Decodificador Morse");
        primaryStage.setScene(scene);
        primaryStage.show();
        desenharArvore();
    }


    private void desenharArvore() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        desenharNo(gc, arvore.getRaiz(), canvas.getWidth() / 2, 40, canvas.getWidth() / 4, 100);
    }


    private void desenharNo(GraphicsContext gc, MorseNode no, double x, double y, double offsetX, double offsetY) {
        if (no == null) return;

        gc.setFill(no.usado ? Color.GREEN : Color.web("#25165b"));
        gc.fillOval(x - 15, y - 15, 30, 30);

        gc.setStroke(no.usado ? Color.DARKGREEN : Color.web("#bd3b70"));
        gc.strokeOval(x - 15, y - 15, 30, 30);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(14));
        String texto = no.caractere == ' ' ? "" : String.valueOf(no.caractere);
        gc.fillText(texto, x - 4, y + 5);

        if (no.ponto != null) {
            gc.setStroke(no.ponto.usado ? Color.GREEN : Color.web("#000033"));
            gc.strokeLine(x, y + 15, x - offsetX, y + offsetY - 15);
            desenharNo(gc, no.ponto, x - offsetX, y + offsetY, offsetX / 2, offsetY);
        }

        if (no.traco != null) {
            gc.setStroke(no.traco.usado ? Color.GREEN : Color.web("#000033"));
            gc.strokeLine(x, y + 15, x + offsetX, y + offsetY - 15);
            desenharNo(gc, no.traco, x + offsetX, y + offsetY, offsetX / 2, offsetY);
        }
    }
}
