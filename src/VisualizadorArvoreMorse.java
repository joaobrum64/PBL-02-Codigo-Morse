import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.util.Scanner;

public class VisualizadorArvoreMorse extends Application {

    @Override
    public void start(Stage palco) {
        palco.setTitle("Visualizador de Árvore Morse");

        ArvoreMorse arvore = new ArvoreMorse();

        // Inserindo letras com códigos Morse
        arvore.inserir('A', ".-");
        arvore.inserir('B', "-...");
        arvore.inserir('C', "-.-.");
        arvore.inserir('D', "-..");
        arvore.inserir('E', ".");
        arvore.inserir('F', "..-.");
        arvore.inserir('G', "--.");
        arvore.inserir('H', "....");
        arvore.inserir('I', "..");
        arvore.inserir('J', ".---");
        arvore.inserir('K', "-.-");
        arvore.inserir('L', ".-..");
        arvore.inserir('M', "--");
        arvore.inserir('N', "-.");
        arvore.inserir('O', "---");
        arvore.inserir('P', ".--.");
        arvore.inserir('Q', "--.-");
        arvore.inserir('R', ".-.");
        arvore.inserir('S', "...");
        arvore.inserir('T', "-");
        arvore.inserir('U', "..-");
        arvore.inserir('V', "...-");
        arvore.inserir('W', ".--");
        arvore.inserir('X', "-..-");
        arvore.inserir('Y', "-.--");
        arvore.inserir('Z', "--..");


        Canvas tela = new Canvas(800, 600);
        arvore.desenharArvore(tela);

        Group raizVisual = new Group();
        raizVisual.getChildren().add(tela);

        Scene cena = new Scene(raizVisual, 800, 600);
        palco.setScene(cena);
        palco.show();

        // Console para interatividade
        new Thread(() -> executarConsole(arvore, tela)).start();
    }

    private void executarConsole(ArvoreMorse arvore, Canvas tela) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nDigite 1 para codificar, 2 para decodificar, 0 para sair:");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpa enter

            if (opcao == 0) break;

            System.out.print("Digite a palavra ou código morse: ");
            String entrada = scanner.nextLine();

            if (opcao == 1) {
                String codificado = arvore.codificarPalavra(entrada);
                System.out.println("Codificado: " + codificado);
            } else if (opcao == 2) {
                String decodificado = arvore.decodificarPalavra(entrada);
                System.out.println("Decodificado: " + decodificado);
            }

            // Atualiza a árvore na tela
            javafx.application.Platform.runLater(() -> arvore.desenharArvore(tela));
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
