public class Main {

    public static void main(String[] args) {

        System.out.println("* Arquitetura von Neumann Basica - PSCF\n");

        // cria componentes da arquitetura

        IO io = new IO(System.out);
        RAM ram = new RAM(128);
        Cache cache = new Cache(8, ram);
        CPU cpu = new CPU(io, cache);

        try {

            // carrega "programa" na memória

            final int inicio = 10;

            ram.Write(inicio, 118);
            ram.Write(inicio+1, 130);

            // executa programa

            cpu.Run(inicio);

        } catch (EnderecoInvalido e) {
            System.err.println("Erro: " + e);
        }
    }
}

