public class No {
    char letra;
    String codigoMorse;
    No esquerdo;
    No direito;
    boolean visitado;  // marca se foi visitado

    public No(char letra, String codigoMorse) {
        this.letra = letra;
        this.codigoMorse = codigoMorse;
        this.visitado = false;
    }
}
