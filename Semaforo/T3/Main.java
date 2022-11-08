package T3;

import java.util.concurrent.Semaphore;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		Semaphore sEmpresa_A = new Semaphore(3);
		Semaphore sEmpresa_B = new Semaphore(3);
		Semaphore sPrincipal = new Semaphore(1);

		CSC csc = new CSC();

		Funcionario[] listaFuncionarios = new Funcionario[20];

		for(int i = 0; i < 10; i++){
			listaFuncionarios[i*2] = new Funcionario("F"+(i+1),'A', sEmpresa_A, sEmpresa_B, sPrincipal, csc);
			listaFuncionarios[i*2+1] = new Funcionario("F"+(i+1),'B', sEmpresa_A, sEmpresa_B, sPrincipal, csc);
		}

		for(int i = 0; i < 20; i++){
			listaFuncionarios[i].start();
		}

		for(int i = 0; i < 20; i++){
			listaFuncionarios[i].join();
		}
	}
}