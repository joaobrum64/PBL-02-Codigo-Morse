public class MorseNode {
    char caractere;
    MorseNode ponto;  // esquerda
    MorseNode traco;  // direita
    boolean usado; // indica se foi usado (uso só para a interface)

    public MorseNode(char caractere) {
        this.caractere = caractere;
        this.ponto = null;
        this.traco = null;
        this.usado = false; // nenhum nó usado no início
    }
}