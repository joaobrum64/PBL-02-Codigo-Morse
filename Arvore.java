public class Arvore {
    private MorseNode raiz;
    private String[][] tabelaCodigos;

    public Arvore() {
        raiz = new MorseNode(' ');
        inicializarTabela();
    }


    private void inserir(String codigoMorse, char caractere) {
        MorseNode atual = raiz;
        for (char simbolo : codigoMorse.toCharArray()) {
            if (simbolo == '.') {
                if (atual.ponto == null)
                    atual.ponto = new MorseNode(' '); // ponto na esquerda (se livre)
                atual = atual.ponto;
            } else if (simbolo == '-') {
                if (atual.traco == null)
                    atual.traco = new MorseNode(' '); // traço na direita (se livre)
                atual = atual.traco;
            }
        }
        atual.caractere = caractere;
    }


    private void inicializarTabela() {
        tabelaCodigos = new String[][]{
                {"A", ".-"}, {"B", "-..."}, {"C", "-.-."}, {"D", "-.."},
                {"E", "."}, {"F", "..-."}, {"G", "--."}, {"H", "...."},
                {"I", ".."}, {"J", ".---"}, {"K", "-.-"}, {"L", ".-.."},
                {"M", "--"}, {"N", "-."}, {"O", "---"}, {"P", ".--."},
                {"Q", "--.-"}, {"R", ".-."}, {"S", "..."}, {"T", "-"},
                {"U", "..-"}, {"V", "...-"}, {"W", ".--"}, {"X", "-..-"},
                {"Y", "-.--"}, {"Z", "--.."},
                {"0", "-----"}, {"1", ".----"}, {"2", "..---"}, {"3", "...--"},
                {"4", "....-"}, {"5", "....."}, {"6", "-...."}, {"7", "--..."},
                {"8", "---.."}, {"9", "----."}
        };
        for (String[] par : tabelaCodigos) {
            inserir(par[1], par[0].charAt(0)); // Assimila letra de acordo com morse
        }
    }


    public MorseNode getRaiz() {
        return raiz;
    }


    public String getCodigoMorse(char letra) {
        for (String[] par : tabelaCodigos) {
            if (par[0].charAt(0) == letra) {
                return par[1];
            }
        }
        return null;
    }
}