public class Tradutor {
    private final Arvore arvore;


    public Tradutor(Arvore arvore) {
        this.arvore = arvore;
    }


    private void limparNoUsado(MorseNode no) {
        if (no == null) return;
        no.usado = false; // Reseta o uso
        limparNoUsado(no.ponto);
        limparNoUsado(no.traco);
    }


    public String codificar(String texto) {
        limparNoUsado(arvore.getRaiz()); // limpa primeiro
        StringBuilder resultado = new StringBuilder();

        for (char c : texto.toUpperCase().toCharArray()) {
            if (c == ' ') {
                resultado.append(" / ");
            } else {
                String codigo = arvore.getCodigoMorse(c);
                if (codigo != null) {
                    resultado.append(codigo).append(" ");
                    marcarNo(codigo);
                } else {
                    resultado.append("? ");
                }
            }
        }
        return resultado.toString().trim();
    }


    public String decodificar(String codigoMorse) {
        limparNoUsado(arvore.getRaiz()); // limpa primeiro
        StringBuilder resultado = new StringBuilder();
        MorseNode raiz = arvore.getRaiz();

        String[] palavras = codigoMorse.trim().split(" / "); // espaçar palavras em caso de frase

        for (String palavra : palavras) { //
            String[] letras = palavra.split(" "); //
            for (String letraMorse : letras) { //
                MorseNode atual = raiz; //
                atual.usado = true; //
                for (char simbolo : letraMorse.toCharArray()) { /////// percorre cada simbolo de cada letra de cada palavra
                    if (simbolo == '.') { //
                        atual = atual.ponto; //
                    } else if (simbolo == '-') { //
                        atual = atual.traco; //
                    } //

                    if (atual == null) break;
                    atual.usado = true; // adiciona cada nó percorrido
                }
                resultado.append(atual != null ? atual.caractere : '?');
            }
            resultado.append(" ");
        }

        return resultado.toString().trim();
    }


    private void marcarNo(String codigo) {
        MorseNode atual = arvore.getRaiz();
        atual.usado = true; // adiciona raiz

        for (char simbolo : codigo.toCharArray()) {
            if (simbolo == '.') {
                atual = atual.ponto;
            } else if (simbolo == '-') {
                atual = atual.traco;
            }
            if (atual != null) {
                atual.usado = true;
            }
        }
    }
}