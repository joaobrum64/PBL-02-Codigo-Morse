import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ArvoreMorse {
    private No raiz;


    public ArvoreMorse() {
        raiz = new No(' ', "");    // Construtor: cria a raiz da árvore com letra vazia e código vazio
    }


    public void inserir(char letra, String codigoMorse) {
        raiz = inserirRecursivo(raiz, letra, codigoMorse, codigoMorse); // Insere uma letra com seu código Morse na árvore
    }

    // Método recursivo para inserir um nó na árvore, seguindo os símbolos do código
    private No inserirRecursivo(No no, char letra, String codigoMorse, String codigoOriginal) {
       //Se o nó atual não existe ainda (null), ele cria um novo nó vazio (' ' e "").
        if (no == null) {
            no = new No(' ', "");
        }
        if (codigoMorse.isEmpty()) {
            no.letra = letra;                  // Guarda a letra nesse nó
            no.codigoMorse = codigoOriginal;  // Guarda o código completo
        } else {
            char simbolo = codigoMorse.charAt(0);  // Pega o primeiro símbolo (. ou -).
            if (simbolo == '.') {
                //Chama o método de novo (recursivamente) no filho esquerdo, removendo o primeiro símbolo (substring(1)).
                no.esquerdo = inserirRecursivo(no.esquerdo, letra, codigoMorse.substring(1), codigoOriginal);
            } else if (simbolo == '-') {
                //Chama o método de novo (recursivamente) no filho direito, removendo o primeiro símbolo (substring(1)).
                no.direito = inserirRecursivo(no.direito, letra, codigoMorse.substring(1), codigoOriginal);
            }
        }
        return no;
    }

    // Codifica uma palavra em texto para código Morse
    public String codificarPalavra(String palavra) {
        limparVisitados(raiz);  // Limpa marcações anteriores
        StringBuilder codigo = new StringBuilder();
        for (char letra : palavra.toUpperCase().toCharArray()) {
            if (letra != ' ') {
                String codigoLetra = buscarCodigo(raiz, letra);  // Busca código para cada letra
                if (codigoLetra != null) {
                    codigo.append(codigoLetra).append(" ");
                } else {
                    codigo.append("? ");  // Se letra não encontrada, usa '?'
                }
            }
        }
        return codigo.toString().trim();
    }

    // Busca recursivamente o código Morse para uma letra
    private String buscarCodigo(No no, char letra) {
        if (no == null) return null;
        if (no.letra == letra) {
            no.visitado = true;  // Marca nó como visitado
            return no.codigoMorse;
        }
        String esquerda = buscarCodigo(no.esquerdo, letra);
        if (esquerda != null) {
            no.visitado = true;
            return esquerda;
        }
        String direita = buscarCodigo(no.direito, letra);
        if (direita != null) {
            no.visitado = true;
            return direita;
        }
        return null;
    }

    // Decodifica uma palavra em código Morse para texto
    public String decodificarPalavra(String codigoMorse) {
        limparVisitados(raiz);  // Limpa marcações anteriores
        StringBuilder palavra = new StringBuilder();
        String[] codigos = codigoMorse.trim().split(" ");
        for (String codigo : codigos) {
            char letra = decodificarLetra(codigo);
            palavra.append(letra);
        }
        return palavra.toString();
    }

    // Decodifica um único código Morse para uma letra
    public char decodificarLetra(String codigoMorse) {
        No atual = raiz;
        atual.visitado = true;
        for (char simbolo : codigoMorse.toCharArray()) {
            if (simbolo == '.') {
                atual = atual.esquerdo;
            } else if (simbolo == '-') {
                atual = atual.direito;
            }
            if (atual == null) return '?';  // Código inválido
            atual.visitado = true;
        }
        return atual.letra;
    }

    // Limpa a marcação de visitado de todos os nós
    public void limparVisitados(No no) {
        if (no == null) return;
        no.visitado = false;
        limparVisitados(no.esquerdo);
        limparVisitados(no.direito);
    }

    // Desenha a árvore Morse em um Canvas (JavaFX)
    public void desenharArvore(Canvas tela) {
        GraphicsContext gc = tela.getGraphicsContext2D();
        gc.clearRect(0, 0, tela.getWidth(), tela.getHeight());  // Limpa tela
        desenharNo(gc, raiz, tela.getWidth() / 2, 40, tela.getWidth() / 4);  // Desenha raiz
    }

    // Método recursivo para desenhar cada nó na tela
    private void desenharNo(GraphicsContext gc, No no, double x, double y, double deslocamentoX) {
        if (no == null) return;

        gc.setStroke(no.visitado ? Color.RED : Color.BLACK);  // Nó visitado fica vermelho
        gc.strokeOval(x - 30, y - 15, 60, 30);  // Desenha oval para o nó

        String texto = (no.letra == ' ' ? "" : no.letra) + " (" + no.codigoMorse + ")";
        gc.strokeText(texto, x - 25, y + 5);  // Escreve letra e código

        // Desenha filho esquerdo
        if (no.esquerdo != null) {
            double novoX = x - deslocamentoX;
            double novoY = y + 60;
            gc.strokeLine(x, y + 15, novoX, novoY - 15);  // Linha até filho
            desenharNo(gc, no.esquerdo, novoX, novoY, deslocamentoX / 2);
        }

        // Desenha filho direito
        if (no.direito != null) {
            double novoX = x + deslocamentoX;
            double novoY = y + 60;
            gc.strokeLine(x, y + 15, novoX, novoY - 15);
            desenharNo(gc, no.direito, novoX, novoY, deslocamentoX / 2);
        }
    }
}
