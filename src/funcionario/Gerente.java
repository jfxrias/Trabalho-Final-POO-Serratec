package funcionario;

public class Gerente extends Funcionario {
    private int agencia;

    public Gerente(String cpf, String senha, String cargo, int agencia) {
        super(cpf, senha, cargo);
        this.agencia = agencia;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }
}