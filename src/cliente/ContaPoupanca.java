package cliente;

public class ContaPoupanca extends Conta {
	private String tipo = "poupanca";
	
	
    public ContaPoupanca(String cpfTitular, double saldo, int agencia) {
        super(cpfTitular, saldo, agencia);
    }

    public double simularRendimento(double valor, int dias) {
        double taxa = 0.005;
        return valor * (1 + taxa * dias);
    }
}