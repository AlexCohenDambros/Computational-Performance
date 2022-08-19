public class Cache extends Memoria {

    private final int[] dadosCache;
    private final Memoria ram;

    private int blocoAtual;
    private boolean modificada;


    public Cache(int tamanho, Memoria ram) {     // W é a capacidade da memória em "words"
        super(tamanho);
        this.dadosCache = new int[tamanho];
        this.ram = ram;
        this.modificada = false;
    }

    @Override
    public int Read(int endereco) throws EnderecoInvalido {
        if (blocoAtual != endereco / dadosCache.length) {
            if (modificada) {
                colaRAM();
                modificada = false;
            }
            blocoAtual = endereco / dadosCache.length;
            copiaRAM();


        }

        return dadosCache[endereco - blocoAtual * dadosCache.length];

    }

    @Override
    public void Write(int endereco, int x) throws EnderecoInvalido {
        if (blocoAtual != endereco / dadosCache.length) {
            if (modificada) {
                colaRAM();
                modificada = false;
            }
            blocoAtual = endereco / dadosCache.length;
            copiaRAM();

        }
        dadosCache[endereco - blocoAtual * dadosCache.length] = x;
    }

    public void copiaRAM() throws EnderecoInvalido {
        for (int i = 0; i < dadosCache.length; i++) {
            dadosCache[i] = ram.Read(i + blocoAtual * dadosCache.length);
        }
    }

    public void colaRAM() throws EnderecoInvalido {
        for (int i = 0; i < dadosCache.length; i++) {
            ram.Write(i + blocoAtual * dadosCache.length, dadosCache[i]);
        }
    }
}

