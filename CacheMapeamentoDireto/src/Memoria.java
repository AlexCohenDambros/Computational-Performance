class EnderecoInvalido extends Exception {
    public final int ender;

    public EnderecoInvalido(int e) {
        super();
        ender = e;
    }

    @Override
    public String toString() {
        return "Endereco " + ender + " invalido!";
    }
}

// Memória (base)

abstract class Memoria {
    protected int capacidade;

    public Memoria(int capacidade) {
        this.capacidade = capacidade;
    }

    public void VerificaEndereco(int endereco) throws EnderecoInvalido {
        if (endereco < 0 || endereco >= capacidade)
            throw new EnderecoInvalido(endereco);
    }

    // a implementar na subclasse
    abstract int Read(int endereco) throws EnderecoInvalido;

    abstract void Write(int endereco, int valor) throws EnderecoInvalido;
}
