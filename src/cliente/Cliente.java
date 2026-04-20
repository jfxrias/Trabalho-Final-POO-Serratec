package cliente;

public class Cliente {
	private String cpf;
	private String senha;

	
	public Cliente(String cpf, String senha) {
		this.cpf = cpf;
		this.senha = senha;
	}
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
		//cod sql
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}