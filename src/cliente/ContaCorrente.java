package cliente;

public class ContaCorrente extends Conta{
	
	private int tipo = 0; // 0 para CC, 1 para CP
	
	public ContaCorrente(String cpf, String senha, double saldo, int agencia, int tipo) {
		super(cpf, senha, saldo, agencia);
		this.tipo = tipo;
	}

	@Override
	public double getSaldo() {
		// TODO Auto-generated method stub
		return super.getSaldo();
	}

	@Override
	public void setSaldo(double saldo) {
		// TODO Auto-generated method stub
		super.setSaldo(saldo);
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}