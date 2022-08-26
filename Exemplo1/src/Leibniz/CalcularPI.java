package Leibniz;

public class CalcularPI {

    public static void main(String[] args) {

        double pi = 0.0;
        double numerador = 1;

        long tempoInicial = System.nanoTime();

        for (long i = 0; i < 5_000_000_000L; i++) {
            pi += numerador / (2 * i +1);
            numerador *= -1;
        }

        pi *= 4;

        long tempoFinal = System.nanoTime() - tempoInicial;
        System.out.println("\nValor de PI: " + pi);
        System.out.println("Tempo: " + tempoFinal / 1e9 + " segundos");

    }


}
