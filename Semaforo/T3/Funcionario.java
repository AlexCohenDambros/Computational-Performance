package T3;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Funcionario extends Thread {
    public String nome;
    public char empresa;
    public Semaphore sEmpresaA;
    public Semaphore sEmpresaB;
    public Semaphore sPrincipal;
    private Random rand;
    public CSC csc;

    public Funcionario(String nome, char empresa, Semaphore sEmpresaA, Semaphore sEmpresaB, Semaphore sPrincipal, CSC csc) {
        this.nome = nome;
        this.empresa = empresa;
        this.sEmpresaA = sEmpresaA;
        this.sEmpresaB = sEmpresaB;
        this.sPrincipal = sPrincipal;
        this.rand = new Random();
        this.csc = csc;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(rand.nextInt(10_000)); // espera um tempo aleatório até 10 segundos

            sPrincipal.acquire(); // fecha a sala
            if (csc.getnFuncionarios() == 0) { // verifica se tem algum funcionário dentro da sala

                if (empresa == 'A') {
                    sEmpresaB.acquire(3); // remove a permissão da B
                    sEmpresaA.acquire(); // add empresa A
                } else {
                    sEmpresaA.acquire(3);
                    sEmpresaB.acquire();
                }

                csc.setEmpresaAtual(empresa); // seta empresa dentro da sala

                System.out.println(nome + " [" + empresa + "] tentando acesso");
                csc.setnFuncionarios(csc.getnFuncionarios() + 1); // add 1 funcionario que esta presente na sala

                sPrincipal.release(); // abre a sala
            } else { // caso a sala nao esteje vazia
                sPrincipal.release(); // abre a sala

                System.out.println(nome + " [" + empresa + "] tentando acesso");

                if (empresa == 'A') { // verifica qual é a sua empresa
                    sEmpresaA.acquire(); // entra na fila.
                } else {
                    sEmpresaB.acquire();
                }

                if (csc.getEmpresaAtual() != empresa) { // caso a empresa dentro da sala seja diferente
                    sPrincipal.acquire(); // fecha a sala
                    if (csc.getnFuncionarios() == 0) { // verifica se a sala esta vazia para pegar os permites para entrar na sala.
                        if (empresa == 'A') {
                            sEmpresaB.acquire(3);
                            csc.setEmpresaAtual('A');
                        } else {
                            sEmpresaA.acquire(3);
                            csc.setEmpresaAtual('B');
                        }

                    }
                    sPrincipal.release(); // abre a sala
                }
                sPrincipal.acquire();
                csc.setnFuncionarios(csc.getnFuncionarios() + 1); // add 1 funcionario que esta presente na sala
                sPrincipal.release();
            }

            System.out.println("+ " + nome + " [" + empresa + "] acessou");
            csc.rodar(); // espera um tempo aleatório entre 5 e 10 segundos
            System.out.println("- " + nome + " [" + empresa + "] terminou acesso");

            if (empresa == 'A') { // verifica qual empresa pertence e sai da sala.
                sEmpresaA.release();
            } else {
                sEmpresaB.release();
            }
            sPrincipal.acquire();
            csc.setnFuncionarios(csc.getnFuncionarios() - 1); // diminui 1 da fila

            if (csc.getnFuncionarios() == 0) { // verifica se caso o funcionario seja o ultimo a sair libera as vagas para outra empresa.
                if (empresa == 'A') {
                    sEmpresaB.release(3);
                } else {
                    sEmpresaA.release(3);
                }
            }
            sPrincipal.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}