public class CacheLine {
    private int r;

    private int tamanhoCache;

    private int[] dados;
    private int t;
    private boolean modif;

    private Memoria ram;
    public CacheLine(int kWords, int r, int tamanhoCache, Memoria ram) {
        this.ram=ram;
        this.tamanhoCache=tamanhoCache;
        this.r=r;
        this.dados = new int[kWords];
        this.t = -1;
        this.modif=false;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public boolean isModif() {
        return modif;
    }

    public void setModif(boolean modif) {
        this.modif = modif;
    }

    public int Read(int t, int w) throws EnderecoInvalido {
        if(!(t==this.t)){
            changeBlock(t);
        }
        return this.dados[w];

    }

    public void Write(int t, int w, int valor) throws EnderecoInvalido {
        if(!(t==this.t)){
            changeBlock(t);
        }
        this.dados[w]=valor;
    }

    private void changeBlock(int t) throws EnderecoInvalido {
        if(modif){
            int kLineBits = (int) (Math.log(this.dados.length) / Math.log(2));
            int totalCacheBits = (int) (Math.log(tamanhoCache) / Math.log(2));

            int init = this.t << totalCacheBits | this.r << kLineBits;
            for(int i = 0; i<this.dados.length;i++){
                ram.Write(i+init,dados[i]);
            }
        }
        modif = false;
        int kLineBits = (int) (Math.log(this.dados.length) / Math.log(2));
        int totalCacheBits = (int) (Math.log(tamanhoCache) / Math.log(2));

        int init = t << totalCacheBits | this.r << kLineBits;
        for(int i = 0; i<this.dados.length;i++){
            this.dados[i]=ram.Read(i+init);
        }
        this.t=t;
    }
}
