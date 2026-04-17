package funcionario;

public abstract class Funcionario {
	private String cpf;
	private String senha;
	private String cargo;
	
	public Funcionario(String cpf, String senha, String cargo) {
		super();
		this.cpf = cpf;
		this.senha = senha;
		this.cargo = cargo;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	
}
