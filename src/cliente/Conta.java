package cliente;

public abstract class Conta extends Cliente{
	private double saldo;
	private int agencia;
	
	public Conta(String cpf, String senha, double saldo, int agencia) {
		super(cpf, senha);
		this.saldo = saldo;
		this.agencia = agencia;
	}
	
	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public int getAgencia() {
		return agencia;
	}
	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}
}
