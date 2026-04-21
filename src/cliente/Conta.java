package cliente;

public abstract class Conta {

    private String cpfTitular;
    private double saldo;
    private int agencia;

    public Conta(String cpfTitular, double saldo, int agencia) {
        this.cpfTitular = cpfTitular;
        this.saldo = saldo;
        this.agencia = agencia;
    }

    public String getCpfTitular() {
        return cpfTitular;
    }

    public void setCpfTitular(String cpfTitular) {
        this.cpfTitular = cpfTitular;
    }

    public double getSaldo() {
    	//querry sql reto
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

    public void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
        setSaldo(getSaldo() - valor);
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
        setSaldo(getSaldo() + valor);
    }

    public void transferir(Conta destino, double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
        this.sacar(valor);
        destino.depositar(valor);
    }
}