package T3;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CSC {
	private Random rand;
	private char empresaAtual;
	public int nFuncionarios;

	public CSC() {
		this.rand = new Random();
		this.empresaAtual = '-';
		this.nFuncionarios = 0;
	}

	public void rodar(){
		try {
			Thread.sleep(rand.nextInt(5_000,10_000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getnFuncionarios() {
		return nFuncionarios;
	}

	public void setnFuncionarios(int nFuncionarios) {
		this.nFuncionarios = nFuncionarios;
	}

	public char getEmpresaAtual() {
		return empresaAtual;
	}

	public void setEmpresaAtual(char empresaAtual) {
		this.empresaAtual = empresaAtual;
	}
}