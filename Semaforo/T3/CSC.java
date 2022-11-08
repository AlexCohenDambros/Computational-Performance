package T3;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class CSC {
	private Random rand;
	private char empresaAtual;
	public int nFuncionarios;
	public Semaphore sEmpresa_A;
	public Semaphore sEmpresa_B;
	public Semaphore sNFuncionarios;

	public CSC(Semaphore sEmpresa_A, Semaphore sEmpresa_B, Semaphore sNFuncionarios) {
		this.rand = new Random();
		this.sEmpresa_A = sEmpresa_A;
		this.sEmpresa_B = sEmpresa_B;
		this.sNFuncionarios = sNFuncionarios;
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
