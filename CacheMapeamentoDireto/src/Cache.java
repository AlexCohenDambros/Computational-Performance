import java.util.LinkedList;

public class Cache extends Memoria {
    private int kWords;
    private int tamanho;
    private LinkedList<CacheLine> cacheLines;
    private final Memoria ram;

    private CacheLine cacheLine;


    public Cache(int tamanho, Memoria ram, int kWords) {
        super(tamanho);
        this.cacheLines = new LinkedList<>();
        this.tamanho = tamanho;
        this.kWords = kWords;
        this.ram = ram;
        for (int line = 0; line < Math.ceilDiv(tamanho, kWords); line++) {
            cacheLines.add(new CacheLine(kWords, line, tamanho, ram));
        }

    }


    @Override
    int Read(int endereco) throws EnderecoInvalido {
        int kLineBits = (int) (Math.log(kWords) / Math.log(2));
        int totalCacheBits = (int) (Math.log(tamanho) / Math.log(2));


        int w = (endereco & (kWords - 1));
        int r = (endereco >> kLineBits & (Math.ceilDiv(tamanho, kWords)-1));
        int t = (endereco >> totalCacheBits);

        return cacheLines.get(r).Read(t, w);
    }

    @Override
    void Write(int endereco, int valor) throws EnderecoInvalido {
        int kLineBits = (int) (Math.log(kWords) / Math.log(2));
        int totalCacheBits = (int) (Math.log(tamanho) / Math.log(2));


        int w = (endereco & (kWords - 1));
        int r = (endereco >> kLineBits & (Math.ceilDiv(tamanho, kWords)-1));
        int t = (endereco >> totalCacheBits);

        cacheLines.get(r).Write(t, w, valor);
    }
}

