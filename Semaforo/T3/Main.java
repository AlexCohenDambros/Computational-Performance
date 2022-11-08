package T3;

import java.util.concurrent.Semaphore;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Semaphore sEmpresa_A = new Semaphore(3);
		Semaphore sEmpresa_B = new Semaphore(3);
		Semaphore sNFuncionarios = new Semaphore(1); // conta o numero de funcionarios dentro da sala
		Semaphore sEmpresaAtual = new Semaphore(1); // identificar a empresa dentro da sala

		CSC csc = new CSC(sEmpresa_A, sEmpresa_B, sNFuncionarios);

		Funcionario[] listaFuncionarios = new Funcionario[20];

		for(int i = 0; i < 10; i++){ // inicializar as classes -> par da A impar da B
			listaFuncionarios[i*2] = new Funcionario("F"+(i+1),'A', sEmpresa_A, sEmpresa_B, sEmpresaAtual, sNFuncionarios, csc);
			listaFuncionarios[i*2+1] = new Funcionario("F"+(i+1),'B', sEmpresa_A, sEmpresa_B, sEmpresaAtual, sNFuncionarios, csc);
		}

		for(int i = 0; i < 20; i++){
			listaFuncionarios[i].start();
		}

		for(int i = 0; i < 20; i++){
			listaFuncionarios[i].join();
		}
	}
}
