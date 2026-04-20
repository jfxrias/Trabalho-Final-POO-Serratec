package cliente;

public class ContaCorrente extends Conta {

    private double taxaTotal;

    public ContaCorrente(String cpfTitular, double saldo, int agencia) {
        super(cpfTitular, saldo, agencia);
        this.taxaTotal = 0;
    }

    public double getTaxaTotal() {
        return taxaTotal;
    }

    public void setTaxaTotal(double taxaTotal) {
        this.taxaTotal = taxaTotal;
    }

    @Override
    public void sacar(double valor) {
        super.sacar(valor);
        setSaldo(getSaldo() - 0.10);
        setTaxaTotal(getTaxaTotal() + 0.10);
    }

    @Override
    public void depositar(double valor) {
        super.depositar(valor);
        setSaldo(getSaldo() - 0.10);
        setTaxaTotal(getTaxaTotal() + 0.10);
    }

    @Override
    public void transferir(Conta destino, double valor) {
        super.transferir(destino, valor);
        setSaldo(getSaldo() - 0.20);
        setTaxaTotal(getTaxaTotal() + 0.20);
    }
}