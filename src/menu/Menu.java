package menu;
import java.util.Scanner; 
import cliente.*;
import funcionario.*;
import pacoteConexao.Conexao;

public class Menu implements Controlador{

	@Override
	public void abrirMenu() {
	
	String cpfDigitado = null;
	String senhaDigitada = null;
	Scanner leitor = new Scanner(System.in);
	
	int continuaPedindoSenha = 1;
	
	while(continuaPedindoSenha == 1) {
		
		System.out.println("Olá, bem-vindo ao Banco");
		System.out.println("Digite seu CPF:");
		cpfDigitado = leitor.nextLine();
		System.out.println("Agora digite sua senha:");
		senhaDigitada = leitor.nextLine();
		
		
		try (java.sql.Connection conn = pacoteConexao.Conexao.conectar()) {
			if(conn == null){
			System.out.println("Erro de conexão com o banco!");
			return;
}
		    String sql = "SELECT * FROM clientes WHERE cpf = ? AND senha = ?";

		    java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
		    stmt.setString(1, cpfDigitado);
		    stmt.setString(2, senhaDigitada);

		    java.sql.ResultSet rs = stmt.executeQuery();

		    if(rs.next()) {
		        System.out.println("Login efetuado!");
		        continuaPedindoSenha = 0;
		    } else {
		        System.out.println("Senha incorreta, tente novamente");
		    }
		
		} catch (Exception e) {
		    e.printStackTrace();
	}
}

	int tipoDeUsuario = 1;
	
	switch(tipoDeUsuario) {
	case 1:
		//INICIO SE FOR CLIENTE
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
						
						try (java.sql.Connection conn = Conexao.conectar()) {

						    String sql = "UPDATE contas SET saldo = saldo - ? WHERE cpf_titular = ?";

						    java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
						    stmt.setDouble(1, valorSaque);
						    stmt.setString(2, cpfDigitado);

						    stmt.executeUpdate();

						    System.out.println("Saque realizado!");
						    continua = 0;

			} catch (Exception e) {
		      e.printStackTrace();
		}				
	}
}
				
				if(SaqDepTransf == 2) {
					int continua = 1;
					while(continua == 1) {
						System.out.println("=====SAQUE=====");
						System.out.println("Digite o valor que você quer depositar:");
						double valorDeposito = leitor.nextDouble();
						
						try (java.sql.Connection conn = Conexao.conectar()) {

						    String sql = "UPDATE contas SET saldo = saldo + ? WHERE cpf_titular = ?";

						    java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
						    stmt.setDouble(1, valorDeposito);
						    stmt.setString(2, cpfDigitado);

						    stmt.executeUpdate();

						    System.out.println("Depósito realizado!");
						    continua = 0;

						} catch (Exception e) {
						    e.printStackTrace();
						}
						
					}
				}
				
				if(SaqDepTransf == 3) {
					int continua = 1;
					
					while(continua == 1) {
						System.out.println("=====TRANSFERÊNCIA=====");
						System.out.println("Digite o CPF da conta que você deseja transferir:");
						leitor.nextLine(); // Limpa o enter que fica no teclado
						String cpfRecebe = leitor.nextLine();
						System.out.println("Qual valor você quer depositar?");
						double valorTransferencia = leitor.nextDouble();
						
						try (java.sql.Connection conn = Conexao.conectar()) {

						    conn.setAutoCommit(false);

						    String sql1 = "UPDATE contas SET saldo = saldo - ? WHERE cpf_titular = ?";
						    String sql2 = "UPDATE contas SET saldo = saldo + ? WHERE cpf_titular = ?";

						    java.sql.PreparedStatement stmt1 = conn.prepareStatement(sql1);
						    java.sql.PreparedStatement stmt2 = conn.prepareStatement(sql2);

						    stmt1.setDouble(1, valorTransferencia);
						    stmt1.setString(2, cpfDigitado);

						    stmt2.setDouble(1, valorTransferencia);
						    stmt2.setString(2, cpfRecebe);

						    stmt1.executeUpdate();
						    stmt2.executeUpdate();

						    conn.commit();

						    System.out.println("Transferência realizada!");
						    continua = 0;

				    } catch (Exception e) {
				    	e.printStackTrace();
				}			
			}
		}
	}
}
			
		if(movimentacoesOuRelatorios == 2) {
			System.out.println("=======RELATÓRIOS=======");
			System.out.println("1) Saldo | 2) Tributações | 3) Rendimento | 4) Seguro de Vida");
			int escolhaRelatorio = leitor.nextInt();
			
			if(escolhaRelatorio == 1) {
				try (java.sql.Connection conn = Conexao.conectar()) {

					String sql = "SELECT saldo FROM contas WHERE cpf_titular = ?";

					java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, cpfDigitado);

					java.sql.ResultSet rs = stmt.executeQuery();

					if (rs.next()) {
						double saldo = rs.getDouble("saldo");
						System.out.println("Saldo: R$ " + saldo);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			
			if(escolhaRelatorio == 2) {
				double valorTotalOperacoes = 171; //retornar query do bd
				System.out.println("Total gasto nas operações até o momento: " +valorTotalOperacoes);
				
				// Simulando que você buscou no BD se o cliente tem seguro (Tabela 'seguro')
			    double valorDoSeguroNoBD = 10000.00; // Isso virá da sua query SQL
			    
			    if(valorDoSeguroNoBD > 0) {
			        double tributoSeguro = valorDoSeguroNoBD * 0.02; // Cálculo dos 2%
			        System.out.printf("Tributo sobre Seguro de Vida: R$ %.2f\n", tributoSeguro);
			        System.out.printf("TOTAL GERAL DE TRIBUTOS: R$ %.2f\n", (valorTotalOperacoes + tributoSeguro));
			    }
			    

			    System.out.println("\nValor de cada operação bancária: ");
			    System.out.println("Saque: R$ 0.10 | Depósito: R$ 0.10 | Transferência: R$ 0.20");
			}
				
			
			if(escolhaRelatorio == 3) {
				int continua = 1;
				
				while(continua == 1) {
					if(perguntaTipoConta == 2) { 
			            System.out.println("=== SIMULAÇÃO DE RENDIMENTO POUPANÇA ===");
			            System.out.print("Digite o valor que deseja simular: ");
			            double valorSimular = leitor.nextDouble();
			            
			            System.out.print("Digite a quantidade de dias para o prazo: ");
			            int dias = leitor.nextInt();

			            //Calculamos o rendimento (Taxa de 0.5% ao mês)
			            double rendimento = valorSimular * (Math.pow(1 + (0.005 / 30), dias) - 1);
			            
			            System.out.printf("Rendimento no prazo de %d dias: R$ %.2f\n", dias, rendimento);
			            System.out.printf("Valor total (Principal + Rendimento): R$ %.2f\n", (valorSimular + rendimento));
			            
			            continua = 0; //Para o loop parar depois de mostrar o resultado
			        } else {
			            System.out.println("Erro: Esta simulação é permitida apenas para Conta Poupança.");
			            continua = 0; // Para sair do loop mesmo em caso de erro
			        }			
					
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
	    case 2:
	    case 3:
	    case 4:
		// 2 gerente, 3 diretor, 4 presidente
		
		//INICIO SE FOR GERENTE
		
		//INICIO SE FOR CLIENTE
				//AGORA PERGUNTA SE QUER ACESSAR A CONTA CORRENTE OU CONTA POUPANÇA;
					int continuaPerguntandoTipoConta2 = 1;
					int perguntaTipoConta2 = 1;
					while(continuaPerguntandoTipoConta2 == 1) {
						System.out.println("Você quer acessar: 1)Conta corrente | 2)Conta Poupança");
						perguntaTipoConta2 = leitor.nextInt();
					
						if(perguntaTipoConta2 == 1 || perguntaTipoConta2 == 2) {
							continuaPerguntandoTipoConta2 = 0;
						} else {
						
						}
					}
					int continuaMovOuRel2 = 1;
					int movimentacoesOuRelatorios2 = 1;
					
					while(continuaMovOuRel2 == 1) {
						System.out.println("=======BEM VINDO USUARIO=======");
						System.out.println("1) Movimentações | 2) Relatórios");
						movimentacoesOuRelatorios2 = leitor.nextInt();
						
						if(movimentacoesOuRelatorios2 == 1 || movimentacoesOuRelatorios2 ==2) {
							continuaMovOuRel2 = 0;
						} else {
							
						}
					}
					
					if(movimentacoesOuRelatorios2 == 1) {
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
								
								if (perguntaTipoConta2 == 1) {
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
								
								if(perguntaTipoConta2 == 1) {
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
								if(perguntaTipoConta2 == 1) {
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
					
				if(movimentacoesOuRelatorios2 == 2) {
					System.out.println("=======RELATÓRIOS=======");
					System.out.println("1) Saldo | 2) Tributações | 3) Rendimento | 4) Contas Agência");
					if(tipoDeUsuario == 3) {
						System.out.println("Opções adicionais para Diretor:");
						System.out.println("5) Informações Clientes");
					} 
					
					if(tipoDeUsuario == 4) {
						System.out.println("Opções adicionais para Presidente:");
						System.out.println("5) Informações Clientes | 6) Capital Total do Banco");
					}
					
					int escolhaRelatorio = leitor.nextInt();
					
					if(escolhaRelatorio == 1) {
					    try (java.sql.Connection conn = Conexao.conectar()) {

					        String sql = "SELECT saldo FROM contas WHERE cpf_titular = ?";

					        java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
					        stmt.setString(1, cpfDigitado);

					        java.sql.ResultSet rs = stmt.executeQuery();

					        if (rs.next()) {
					            double saldo = rs.getDouble("saldo");
					            System.out.println("Saldo: R$ " + saldo);
					        }

					    } catch (Exception e) {
					        e.printStackTrace();
					    }
					}
					
					if(escolhaRelatorio == 2) {
						double valorTotalOperacoes = 171; //retornar query do bd
						System.out.println("Total gasto nas operações até o momento: " +valorTotalOperacoes);
						
						// Simulando que você buscou no BD se o cliente tem seguro (Tabela 'seguro')
					    double valorDoSeguroNoBD = 10000.00; // Isso virá da sua query SQL
					    
					    if(valorDoSeguroNoBD > 0) {
					        double tributoSeguro = valorDoSeguroNoBD * 0.02; // Cálculo dos 2%
					        System.out.printf("Tributo sobre Seguro de Vida: R$ %.2f\n", tributoSeguro);
					        System.out.printf("TOTAL GERAL DE TRIBUTOS: R$ %.2f\n", (valorTotalOperacoes + tributoSeguro));
					    }
					    

					    System.out.println("\nValor de cada operação bancária: ");
					    System.out.println("Saque: R$ 0.10 | Depósito: R$ 0.10 | Transferência: R$ 0.20");
					}
						
					
					if(escolhaRelatorio == 3) {
						int continua = 1;
						
						while(continua == 1) {
							if(perguntaTipoConta2 == 2) { 
					            System.out.println("=== SIMULAÇÃO DE RENDIMENTO POUPANÇA ===");
					            System.out.print("Digite o valor que deseja simular: ");
					            double valorSimular = leitor.nextDouble();
					            
					            System.out.print("Digite a quantidade de dias para o prazo: ");
					            int dias = leitor.nextInt();

					            //Calculamos o rendimento (Taxa de 0.5% ao mês)
					            double rendimento = valorSimular * (Math.pow(1 + (0.005 / 30), dias) - 1);
					            
					            System.out.printf("Rendimento no prazo de %d dias: R$ %.2f\n", dias, rendimento);
					            System.out.printf("Valor total (Principal + Rendimento): R$ %.2f\n", (valorSimular + rendimento));
					            
					            continua = 0; //Para o loop parar depois de mostrar o resultado
					        } else {
					            System.out.println("Erro: Esta simulação é permitida apenas para Conta Poupança.");
					            continua = 0; // Para sair do loop mesmo em caso de erro
					        }			
							
						}
					}

					
					if(escolhaRelatorio == 4) {
						int continua = 1;
						
						while(continua == 1) {
							System.out.println("=======CONTAS AGÊNCIAS=======");
							//query no sql COUNT() que retorna a var abaixo;
							int countBD = 15;
							System.out.println("O total de contas na sua agência é de: "+ countBD+" pessoa(s)");
							
						}
					}
					//Caso for diretor ou presidente ele tem esse acesso!
					if(escolhaRelatorio == 5 && (tipoDeUsuario == 3 || tipoDeUsuario == 4)) {
						/* query puxar Relatório com as informações de Nome, CPF e Agência de todos 
						os clientes do sistema em ordem alfabética */
					}
					//Caso ele for presidente ele tem esse acesso!
					if(escolhaRelatorio == 6 && tipoDeUsuario == 4) {
						//query do Relatório com o valor total do capital armazenado no banco
					}
				}
					//FIM DO SE FOR GERENTE
		break;
		  
		}
	}
}