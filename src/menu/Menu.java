package menu;
import java.util.Scanner;
import cliente.*;

public class Menu implements Controlador{

	@Override
	public void abrirMenu() {
	
	String cpfDigitado = null;
	String senhaDigitada = null;
	Scanner leitor = new Scanner(System.in);
	
	int continuaPedindoSenha = 1;
	
	while(continuaPedindoSenha == 1) {
		
		System.out.println("Olá, bem-vindo ao Banco");
		cpfDigitado = leitor.nextLine();
		System.out.println("Agora digite sua senha:");
		senhaDigitada = leitor.nextLine();
		
		String senhaBD = cpfDigitado; //implementar que venha do bd essas duas variaves
		String cpfBD = cpfDigitado;
		
		if(senhaDigitada.equals(senhaBD) && cpfDigitado.equals(cpfBD)) {
			System.out.println("Login efetuado!");		
			continuaPedindoSenha = 0;
		}
		
		else {
			System.out.println("Senha incorreta, tente novamente");
		}
		
	}
	
	//CASOS 1,2 e 3
	
	//querry sql que retorna se o cara é cliente, funcionario ou diretor
	int tipoDeUsuario = 1;
	
	
	switch(tipoDeUsuario) {
	case 1:
		
		//AGORA PERGUNTA SE QUER ACESSAR A CONTA CORRENTE OU CONTA POUPANÇA;
			int continuaPerguntandoTipoConta = 1;
			int perguntaTipoConta = 1;
			while(continuaPerguntandoTipoConta == 1) {
				System.out.println("Você quer acessar: 1)Conta corrente | 2)Conta Poupança");
				perguntaTipoConta = leitor.nextInt();
			
				if(perguntaTipoConta == 1 || perguntaTipoConta == 2) {
					continuaPerguntandoTipoConta = 0;
				} else {
				
				}
			}
			int continuaMovOuRel = 1;
			int movimentacoesOuRelatorios = 1;
			
			while(continuaMovOuRel == 1) {
				System.out.println("=======BEM VINDO USUARIO=======");
				System.out.println("1) Movimentações | 2) Relatórios");
				movimentacoesOuRelatorios = leitor.nextInt();
				
				if(movimentacoesOuRelatorios == 1 || movimentacoesOuRelatorios ==2) {
					continuaMovOuRel = 0;
				} else {
					
				}
			}
			
			if(movimentacoesOuRelatorios == 1) {
				int perguntaSaqDepTransf = 1;
				int SaqDepTransf = 1;
				
				while(perguntaSaqDepTransf == 1) {
				System.out.println("=======MOVIMENTAÇÕES NA CONTA=======");
				System.out.println("1) Saque | 2) Depósito | 3) Transferência");
				SaqDepTransf = leitor.nextInt();
				
				if(SaqDepTransf == 1 || SaqDepTransf == 2 || SaqDepTransf == 3) {
					perguntaSaqDepTransf = 0;
				}
				
				if(SaqDepTransf == 1) {
					int continua = 1;
					while(continua == 1) {
						System.out.println("=====SAQUE=====");
						System.out.println("Digite o valor que você quer sacar:");
						double valorSaque = leitor.nextDouble();
						
						if (perguntaTipoConta == 1) {
							//os dados vão vir do sql lá em cima qnd verifica se a senha e cpf são iguais
							ContaCorrente c = new ContaCorrente(cpfDigitado, 0,0);
							if(c.getSaldo() >= valorSaque) {
								c.setSaldo(c.getSaldo() - valorSaque);
								continua = 0;
							}
						} else {
							ContaPoupanca c = new ContaPoupanca(cpfDigitado, 0,0);
							if(c.getSaldo() >= valorSaque) {
							c.setSaldo(c.getSaldo() - valorSaque);
							continua = 0;
							}
						}
					}
				}
				
				if(SaqDepTransf == 2) {
					int continua = 1;
					while(continua == 1) {
						System.out.println("=====SAQUE=====");
						System.out.println("Digite o valor que você quer depositar:");
						double valorDeposito = leitor.nextDouble();
						
						if(perguntaTipoConta == 1) {
							ContaCorrente c = new ContaCorrente(cpfDigitado, 0 ,0);
							c.setSaldo(c.getSaldo() + valorDeposito);
							continua = 0;
						} else {
							ContaPoupanca c = new ContaPoupanca(cpfDigitado, 0,0);
							c.setSaldo(c.getSaldo() + valorDeposito);
							continua = 0;
						}
					}
				}
				
				if(SaqDepTransf == 3) {
					int continua = 1;
					
					while(continua == 1) {
						System.out.println("=====TRANSFERÊNCIA=====");
						System.out.println("Digite o CPF da conta que você deseja transferir:");
						String cpfRecebe = leitor.nextLine();
						System.out.println("Qual valor você quer depositar?");
						double valorTransferencia = leitor.nextDouble();
						
						//SE FOR CONTA CORRENTE
						if(perguntaTipoConta == 1) {
							ContaCorrente manda = new ContaCorrente(cpfDigitado, 0 ,0); //  retornar do bd
							ContaCorrente recebe = new ContaCorrente(cpfRecebe, 0 ,0); //  retornar do bd
							
							if(manda.getSaldo() >= valorTransferencia) {
								manda.setSaldo(manda.getSaldo() - valorTransferencia - 0.20);
								recebe.setSaldo(recebe.getSaldo() + valorTransferencia);
							}else {
								System.out.println("Saldo insuficiente para essa transferência!");
							}
						} 
						//SE FOR CONTA POUPANÇA
						else {
							ContaPoupanca manda = new ContaPoupanca(cpfDigitado, 0 ,0); //  retornar do bd
							ContaPoupanca recebe = new ContaPoupanca(cpfRecebe, 0 ,0); //  retornar do bd
							if(manda.getSaldo() >= valorTransferencia) {
								manda.setSaldo(manda.getSaldo() - valorTransferencia - 0.20);
								recebe.setSaldo(recebe.getSaldo() + valorTransferencia);
								continua = 0;
							} else {
								System.out.println("Saldo insuficiente para essa transferencia!");
						}
					}
						
						
					}
				}
			}
		}
			
		if(movimentacoesOuRelatorios == 2) {
			System.out.println("=======RELATÓRIOS=======");
			System.out.println("1) Saldo | 2) Tributações | 3) Rendimento | 4) Seguro de vida");
			int escolhaRelatorio = leitor.nextInt();
			
			if(escolhaRelatorio == 1) {
				if(perguntaTipoConta == 1) {
					ContaCorrente c = new ContaCorrente(cpfDigitado, 0 ,0); //  retornar do bd
					c.getSaldo(); // substituir por query sql
				} else {
					ContaPoupanca c = new ContaPoupanca(cpfDigitado, 0 ,0); //  retornar do bd
					c.getSaldo(); // substituir por query sql
				}
			}
			
			if(escolhaRelatorio == 2) {
				double valorTotalOperacoes = 171; //retornar query do bd
				System.out.println("Total gasto nas operações até o momento: " +valorTotalOperacoes);
				System.out.println("Valor de cada operação bancária: ");
				System.out.println("Saque: R$0.10 (dez centavos);");
				System.out.println("Depósito: R$0.10 (dez centavos);");
				System.out.println("Transferência: R$0.20 (vinte centavos) do remetente;");
			}
			
			if(escolhaRelatorio == 3) {
				int continua = 1;
				
				while(continua == 1) {
					System.out.println("Digite o valor que deseja simular o rendimento: ");				
					
				}
			}
			
			if(escolhaRelatorio == 4) {
				int continua = 1;
				
				while(continua == 1) {
					System.out.println("=======SEGURO DE VIDA=======");
					System.out.println("De quanto você quer contrayar o seguro de vida?");
					double valorSeguro = leitor.nextDouble();
					double valorDebitado = valorSeguro * 0.2;
					boolean contratado = false;
					
					if(perguntaTipoConta == 1) {
						ContaCorrente c = new ContaCorrente(cpfDigitado, 0,0); 
						
						if(c.getSaldo() >= valorSeguro) {
							c.setSaldo(c.getSaldo() - valorDebitado);
							continua = 0;
							contratado = true;
						} else {
							System.out.println("Saldo Insuficiente para contratar este valor de seguro");
						}
					} else {
						ContaPoupanca c = new ContaPoupanca(cpfDigitado, 0,0); 
						
						if(c.getSaldo() >= valorSeguro) {
							c.setSaldo(c.getSaldo() - valorDebitado);
							continua = 0;
							contratado = true;
						} else {
							System.out.println("Saldo Insuficiente para contratar este valor de seguro");
						}
					}
					
					if(contratado == true) {
						System.out.println("Seguro de vida de R$"+valorSeguro+" contratado");
						System.out.println("R$"+valorDebitado+" foi debitado da conta!");
					}
					
					
					
					//Incluir no relatório de tributação o valor referente ao seguro de vida, caso este cliente possua
					//estas informações.
				}
			}
		}
			
			
			
			//FIM DO SE FOR CLIENTE
		break;
		
	}
	

	
	}
}