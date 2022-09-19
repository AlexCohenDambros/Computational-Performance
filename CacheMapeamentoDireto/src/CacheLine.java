public class CacheLine {
    private int r; //index da cache line

    private int tamanhoCache;

    private int[] dados;
    private int t; //identificador do bloco
    private boolean modif; //flag de modificação

    private Memoria ram;

    public CacheLine(int kWords, int r, int tamanhoCache, Memoria ram) {
        this.ram = ram;
        this.tamanhoCache = tamanhoCache;
        this.r = r;
        this.dados = new int[kWords];
        this.t = -1;
        this.modif = false;
    }

    public int Read(int t, int w) throws EnderecoInvalido {
        if (t != this.t) { //caso o bloco diferente do endereço solicitado esteja na cache line (cache miss)
            changeBlock(t); //faz o processo pra trocar de bloco
        }
        return this.dados[w]; //read

    }

    public void Write(int t, int w, int valor) throws EnderecoInvalido {
        if (t != this.t) {
            changeBlock(t);
        }
        this.dados[w] = valor;
        modif = true;
    }

    private void changeBlock(int t) throws EnderecoInvalido {
        int kWordsBits = (int) (Math.log(this.dados.length) / Math.log(2)); // Quantidade de bits para uma palavra
        int kRowBits = (int) (Math.log(Math.ceilDiv(tamanhoCache, this.dados.length)) / Math.log(2)); // Quantidade de bits para o R
        int s = this.t << kRowBits | this.r << kWordsBits; // começo do bloco na ram ESSE É O 'S'

        if (modif) {
            for (int i = 0; i < this.dados.length; i++) {
                ram.Write(i + s, dados[i]); //substitui os dados nos endereços da ram
            }
        }
        s = t << kRowBits | this.r << kWordsBits; // carregar o s com o t do bloco novo.
        modif = false; //flag de modificação false

        for (int i = 0; i < this.dados.length; i++) {
            this.dados[i] = ram.Read(i + s);
        }
        this.t = t;
    }
}
