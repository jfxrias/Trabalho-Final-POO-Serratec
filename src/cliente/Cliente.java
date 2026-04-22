package cliente;

public class Cliente {
	private String cpf;
	private String senha;
	private String nome;
	
	public Cliente(String cpf, String senha, String nome) {
		this.cpf = cpf;
		this.senha = senha;
		this.nome = nome;
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
	public void getNome(String nome) {
		this.nome = nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}