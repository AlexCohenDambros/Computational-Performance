public class Main {

    public static void main(String[] args) {

        System.out.println("Mapeamente Direto\n");

        // cria componentes da arquitetura

        IO io = new IO(System.out);
        RAM ram = new RAM(8 * 1024 * 1024);
        Cache cache = new Cache(4 * 1024, ram, 64);
        CPU cpu = new CPU(io, cache);

        try {

            // carrega "programa" na memória

            final int inicio = 10;

            ram.Write(inicio, 118);
            ram.Write(inicio + 1, 130);

            // executa programa

            cpu.Run(inicio);

        } catch (EnderecoInvalido e) {
            System.err.println("Erro: " + e);
        }
    }
}