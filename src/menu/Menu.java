package menu;
import java.util.Scanner;
import cliente.*;

public class Menu implements Controlador{

	@Override
	public void abrirMenu() {
		
		String cpf;
		String senha;
		Scanner leitor = new Scanner(System.in);
		
		
		int continua = 1; //boolean?
		
		while(continua == 1) {
			System.out.println("Bem-vindo ao Banco!");
			System.out.println("Digite seu cpf: ");
			cpf = leitor.nextLine();
			System.out.println("Agora informe sua senha: ");
			senha = leitor.nextLine();
			
			// implementar se a pessoa quer conta corrente ou conta poupanca
			
			//implementar sql, que vai retornar se os login deu certo, vai alterar o valor da var sucesso vai ser 1;
			
			boolean sucesso = true;
			
			if(sucesso == true) {
				continua = 0;
			} else {
				//vai rodar o looping while até o desgraçado acertar a senha
			}
		}
		
		
		
		//implementar sql, que retorna o tipo de acesso 1-cliente, 2-gerente, 3- ;
		
		int tipoAcesso = 1; // lembrar de alterar!
		
		switch(tipoAcesso) {
		

		case 1:
			
				//cliente
				ContaCorrente c = new ContaCorrente("849.321.321-21","1234", 12,12,12);
				System.out.println("1- Movimentações na Conta");
				System.out.println("2- Relatórios");

				int escolha = leitor.nextInt();
				
				switch(escolha) {
				case 1:
					System.out.println("Você selecionou Movimentações!");
					System.out.println("1- Saque, 2- Depósito, 3- Transferência");
					int subEscolha = leitor.nextInt();
					
					switch(subEscolha) {
					case 1:
						System.out.println("Você escolheu a opção de Saque");
						System.out.println("Quanto você deseja sacar?");
						double valor = leitor.nextDouble();
						double valorFinal = (valor * -1);
						c.setSaldo(valorFinal);
					
						break;
						
					case 2:
						System.out.println("Você escolheu a opção de Depósito");
						System.out.println("Quanto você deseja depositar?");
						double valor2 = leitor.nextDouble();
						c.setSaldo(c.getSaldo() - valor2);
						
						break;
						
					case 3:
						System.out.println("Você escolheu a opção de Transferencia");
						System.out.println("Quanto você deseja transferir?");
						double valor3 = leitor.nextDouble();
						// ai eh sofrido
						break;
						
						
					default: System.out.println("Escolha inválida!");
					}
					break;
					
				case 2:

					
					System.out.println("Você selecionou Relatórios!");
					System.out.println("1- Saldo, 2- Tributação, 3- Rendimento, 4-Desafio(?)");
					
					break;
					default: 
						System.out.println("Opção inválida!");
					}
				 
			break;
			
		case 2:
			
			//Funcionario no cargo de gerente;
			System.out.println("Olá gerente nomeVindoDoSql!");
			System.out.println("1- Movimentações e Informações, 2- Relatórios");
			int escolha2 = leitor.nextInt();
			
			switch(escolha2) {

			case 1:
				// gerente fazendo operações nas contas dos clientes
				System.out.println("Você escolheu Movimentações e Informações!");
				System.out.println("1- Saque, 2- Depósito, 3- Transferência");
				int subEscolha = leitor.nextInt();
				
				switch(subEscolha) {
				case 1:
					System.out.println("Você escolheu a opção de Saque");
					System.out.println("Qual CPF da conta que você deseja sacar?");
					double cpfCliente = leitor.nextDouble();
					
					//consulta sql que vai trazer os dados do cliente e vai para o constructor abaixo
					
					ContaCorrente c2 = new ContaCorrente("849.321.321-21","1234", 12,12,12);
					System.out.println("Qual valor será sacado da conta do cliente?");
					double valor = leitor.nextDouble();
					double valorFinal = valor * -1;
					

					String senhaCliente;
					
					
					for(int i = 3; i>0;i--) {
						senhaCliente = leitor.nextLine();
						
						//verificação no banco que vai retornar true ou false para a var acertou;
						boolean acertou = false;//lembrar de apagar isso
						
						if(acertou == true) {
							c2.setSaldo(valorFinal);
							break;
						}
						
						else {
							if(i == 3) {
								System.out.println("Peça para o cliente digitar sua senha: ");
							} else{
								System.out.println("Senha incorreta, tentativa(s) restante(s)" +i);
							}
						}
						
					}
					
					
					//esse codigo está errado, vc deve acertar;
					
					
					

					
					
				
					break;
					
				case 2:
					System.out.println("Você escolheu a opção de Depósito");
					System.out.println("Quanto você deseja depositar?");
					double valor2 = leitor.nextDouble();
					
					break;
					
				case 3:
					System.out.println("Você escolheu a opção de Transferencia");
					System.out.println("Quanto você deseja transferir?");
					double valor3 = leitor.nextDouble();
					// ai eh sofrido
					break;
					
					
				default: System.out.println("Escolha inválida!");
				}
				break;
				
			case 2:
				System.out.println("Você escolheu Relatórios!");
				System.out.println("1- Saldo, 2- Tributação, 3- Conta Poupança, 4- Números de contas da Agência");
				int subEscolha2 = leitor.nextInt();
				
				switch(subEscolha2) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				}
						
				break;
				
				default: System.out.println("Escolha não existe");
			}
			
			break;
		case 3:
			//Funcionario no cargo de diretor;
			break;
		default:
			System.out.println("Tipo de conta invalido;");
		}
		
		leitor.close();
	}
}
