package T3;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Funcionario extends Thread {
    public String nome;
    public char empresa;
    public Semaphore sEmpresaA;
    public Semaphore sEmpresaB;
    public Semaphore sNFuncionarios;
    public Semaphore sEmpresaAtual;
    private Random rand;
    public CSC csc;

    public Funcionario(String nome, char empresa, Semaphore sEmpresaA, Semaphore sEmpresaB, Semaphore sNFuncionarios,
                       Semaphore sEmpresaAtual, CSC csc) {
        this.nome = nome;
        this.empresa = empresa;
        this.sEmpresaA = sEmpresaA;
        this.sEmpresaB = sEmpresaB;
        this.sNFuncionarios = sNFuncionarios;
        this.sEmpresaAtual = sEmpresaAtual;
        this.rand = new Random();
        this.csc = csc;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(rand.nextInt(10_000)); // espera um tempo aleatório até 10 segundos

            sNFuncionarios.acquire(); // fecha a sala
            if (csc.getnFuncionarios() == 0) { // verificando a se a sala esta vazia
                if (empresa == 'A') { // se for A cria 3 lugares
                    sEmpresaB.acquire(3);
                    csc.setEmpresaAtual('A'); // empresa atual que esta na sala
                    sEmpresaA.acquire(); // diminuir 1
                } else {
                    sEmpresaA.acquire(3);
                    csc.setEmpresaAtual('B');
                    sEmpresaB.acquire();
                }
                System.out.println(nome + " [" + empresa + "] tentando acesso");
                csc.setnFuncionarios(csc.getnFuncionarios() + 1); // aumentar o número de pessoas dentro da sala
                sNFuncionarios.release(); // abri a porta
            } else { // caso a sala nao esteje vazia
                if (csc.getEmpresaAtual() == empresa) { // verificar a empresa
                    sNFuncionarios.release(); // abre a porta para outro funcionario
                    System.out.println(nome + " [" + empresa + "] tentando acesso");
                    if (empresa == 'A') {
                        sEmpresaA.acquire(); // entrar na fila para entrar na sala
                    } else {
                        sEmpresaB.acquire();
                    }
                    sNFuncionarios.acquire();
                    csc.setnFuncionarios(csc.getnFuncionarios() + 1); // modifica o numero de pessoas na sala
                    sNFuncionarios.release();
                } else { // se a empresa for diferente
                    // vai entrar na fila da sua empresa
                    sNFuncionarios.release();
                    System.out.println(nome + " [" + empresa + "] tentando acesso");
                    if (empresa == 'A') { // esperando a vez para entrar na sala
                        sEmpresaA.acquire();
                    } else {
                        sEmpresaB.acquire();
                    }

                    sNFuncionarios.acquire();
                    if (csc.getnFuncionarios() == 0) { // verificar se a sala esta vazia
                        if (empresa == 'A') {
                            sEmpresaB.acquire(3); // bloqueia a outra empresa
                            csc.setEmpresaAtual('A');
                        } else {
                            sEmpresaA.acquire(3);
                            csc.setEmpresaAtual('B');
                        }
                        csc.setnFuncionarios(csc.getnFuncionarios() + 1);
                        sNFuncionarios.release();
                    } else { // se nao estiver apenas abre a porta novamente para outro funcionario.
                        sNFuncionarios.release();
                    }

                    sNFuncionarios.acquire();
                    csc.setnFuncionarios(csc.getnFuncionarios() + 1);
                    sNFuncionarios.release();
                }
            }

            System.out.println("+ " + nome + " [" + empresa + "] acessou");
            csc.rodar(); // espera um tempo entre 5 e 10 segundos e sai da sala
            System.out.println("- " + nome + " [" + empresa + "] terminou acesso");

            if (empresa == 'A') { // depois de sair vai liberar uma vaga na sala da empresa
                sEmpresaA.release();
            } else {
                sEmpresaB.release();
            }
            sNFuncionarios.acquire();
            csc.setnFuncionarios(csc.getnFuncionarios() - 1); // diminui

            if (csc.getnFuncionarios() == 0) { // caso ele seja o ultimo seta as novas vagas na sala
                if (empresa == 'A') { // abre as vagas para a sala
                    sEmpresaB.release(3);
                } else {
                    sEmpresaA.release(3);
                }
            }
            sNFuncionarios.release(); // abre a porta para um novo funcionário.

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}