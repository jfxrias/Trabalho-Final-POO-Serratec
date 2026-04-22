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
		        String sqlFunc = "SELECT * FROM funcionarios WHERE cpf = ? AND senha = ?";
		        java.sql.PreparedStatement stmtFunc = conn.prepareStatement(sqlFunc);
		        stmtFunc.setString(1, cpfDigitado);
		        stmtFunc.setString(2, senhaDigitada);
		        java.sql.ResultSet rsFunc = stmtFunc.executeQuery();
		        
		        if(rsFunc.next()) {
		            System.out.println("Login efetuado!");
		            continuaPedindoSenha = 0;
		        } else {
		            System.out.println("Senha incorreta, tente novamente");
		        }
		    }
		
		} catch (Exception e) {
		    e.printStackTrace();
	}
}

	int tipoDeUsuario = 0;

	try (java.sql.Connection conn = Conexao.conectar()) {
	    String sql = "SELECT cpf FROM clientes WHERE cpf = ? AND senha = ?";
	    java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1, cpfDigitado);
	    stmt.setString(2, senhaDigitada);
	    java.sql.ResultSet rs = stmt.executeQuery();
	    if (rs.next()) {
	        tipoDeUsuario = 1;
	    }
	} catch (Exception e) { e.printStackTrace(); }

	if (tipoDeUsuario == 0) {
	    try (java.sql.Connection conn = Conexao.conectar()) {
	        String sql = "SELECT cargo FROM funcionarios WHERE cpf = ? AND senha = ?";
	        java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, cpfDigitado);
	        stmt.setString(2, senhaDigitada);
	        java.sql.ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            String cargo = rs.getString("cargo");
	            if (cargo.equals("gerente"))    tipoDeUsuario = 2;
	            if (cargo.equals("diretor"))    tipoDeUsuario = 3;
	            if (cargo.equals("presidente")) tipoDeUsuario = 4;
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	}

	
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
						double taxa = 0.10;
					
						try (java.sql.Connection conn = Conexao.conectar()) {

				            String sql = "UPDATE contas SET saldo = saldo - ? WHERE cpf_titular = ?";
				            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
				            stmt.setDouble(1, valorSaque + taxa);
				            stmt.setString(2, cpfDigitado);
				            stmt.executeUpdate();

				            String log = "INSERT INTO operacoes (cpf_origem, tipo, valor) VALUES (?, 'saque', ?)";
				            java.sql.PreparedStatement stmtLog = conn.prepareStatement(log);
				            stmtLog.setString(1, cpfDigitado);
				            stmtLog.setDouble(2, valorSaque);
				            stmtLog.executeUpdate();

				            System.out.println("Saque realizado!");
				            try (java.io.FileWriter fw = new java.io.FileWriter("operacoes.txt", true);
				            	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
				            	    bw.write("Saque | CPF: " + cpfDigitado + " | Valor: R$ " + valorSaque + "\n");
				            	} catch (Exception ex) {
				            	    System.out.println("Erro ao gravar arquivo de operações.");
				            	}
				            continua = 0;

			} catch (Exception e) {
		      e.printStackTrace();
		}				
	}
}
				
				if(SaqDepTransf == 2) {
					int continua = 1;
					while(continua == 1) {
						System.out.println("=====DEPOSITO=====");
						System.out.println("Digite o valor que você quer depositar:");
						double valorDeposito = leitor.nextDouble();
						double taxa = 0.10;			    
					     
						try (java.sql.Connection conn = Conexao.conectar()) {


			            String sql = "UPDATE contas SET saldo = saldo + ? WHERE cpf_titular = ?";
			            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
			            stmt.setDouble(1, valorDeposito - taxa);
			            stmt.setString(2, cpfDigitado);
			            stmt.executeUpdate();

			            String log = "INSERT INTO operacoes (cpf_origem, tipo, valor) VALUES (?, 'deposito', ?)";
			            java.sql.PreparedStatement stmtLog = conn.prepareStatement(log);
			            stmtLog.setString(1, cpfDigitado);
			            stmtLog.setDouble(2, valorDeposito);
			            stmtLog.executeUpdate();

			            System.out.println("Depósito realizado!");
			            try (java.io.FileWriter fw = new java.io.FileWriter("operacoes.txt", true);
			            	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
			            	    bw.write("Deposito | CPF: " + cpfDigitado + " | Valor: R$ " + valorDeposito + "\n");
			            	} catch (Exception ex) {
			            	    System.out.println("Erro ao gravar arquivo de operações.");
			            	}
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
						System.out.println("Qual valor você quer transferir?");
						double valorTransferencia = leitor.nextDouble();
						
						double taxa = 0.20;
					   		
						try (java.sql.Connection conn = Conexao.conectar()) {

						    conn.setAutoCommit(false);

					    String sql1 = "UPDATE contas SET saldo = saldo - ? WHERE cpf_titular = ?";
			            java.sql.PreparedStatement stmt1 = conn.prepareStatement(sql1);
			            stmt1.setDouble(1, valorTransferencia + taxa);
			            stmt1.setString(2, cpfDigitado);

				            // quem recebe só valor
			            String sql2 = "UPDATE contas SET saldo = saldo + ? WHERE cpf_titular = ?";
			            java.sql.PreparedStatement stmt2 = conn.prepareStatement(sql2);
			            stmt2.setDouble(1, valorTransferencia);
			            stmt2.setString(2, cpfRecebe);

			            stmt1.executeUpdate();
			            stmt2.executeUpdate();

				            // LOG SEM TAXA (só valor real)
			            String log = "INSERT INTO operacoes (cpf_origem, cpf_destino, tipo, valor) VALUES (?, ?, ?, ?)";
			            java.sql.PreparedStatement stmtLog = conn.prepareStatement(log);
			            stmtLog.setString(1, cpfDigitado);
			            stmtLog.setString(2, cpfRecebe);
			            stmtLog.setObject(3, "transferencia", java.sql.Types.OTHER);
			            stmtLog.setDouble(4, valorTransferencia);
			            
			            stmtLog.executeUpdate();
				     
					    conn.commit();

					    System.out.println("Transferência realizada!");
					    try (java.io.FileWriter fw = new java.io.FileWriter("operacoes.txt", true);
					    	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
					    	    bw.write("Transferencia | CPF origem: " + cpfDigitado + " | CPF destino: " + cpfRecebe + " | Valor: R$ " + valorTransferencia + "\n");
					    	} catch (Exception ex) {
					    	    System.out.println("Erro ao gravar arquivo de operações.");
					    	}
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
						try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_saldo.txt", false);
							     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
							    bw.write("Relatorio de Saldo\n");
							    bw.write("CPF: " + cpfDigitado + "\n");
							    bw.write("Saldo: R$ " + saldo + "\n");
							} catch (Exception ex) {
							    System.out.println("Erro ao gravar relatório.");
							}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			
			if(escolhaRelatorio == 2) {
				try (java.sql.Connection conn = Conexao.conectar()) {

				    // Busca o total de taxas das operações do cliente
				    String sql = "SELECT " +
				        "SUM(CASE WHEN tipo = 'saque' THEN 0.10 " +
				        "WHEN tipo = 'deposito' THEN 0.10 " +
				        "WHEN tipo = 'transferencia' THEN 0.20 ELSE 0 END) as total_taxas " +
				        "FROM operacoes WHERE cpf_origem = ?";
				    java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
				    stmt.setString(1, cpfDigitado);
				    java.sql.ResultSet rs = stmt.executeQuery();

				    double totalTaxas = 0;
				    if (rs.next()) {
				        totalTaxas = rs.getDouble("total_taxas");
				    }

				    System.out.printf("Total de taxas bancárias: R$ %.2f%n", totalTaxas);
				    System.out.println("Saque: R$0,10 | Depósito: R$0,10 | Transferência: R$0,20");

				    // Verifica se o cliente tem seguro
				    String sqlSeguro = "SELECT valor FROM seguro WHERE cpf_cliente = ?";
				    java.sql.PreparedStatement stmtSeg = conn.prepareStatement(sqlSeguro);
				    stmtSeg.setString(1, cpfDigitado);
				    java.sql.ResultSet rsSeg = stmtSeg.executeQuery();

				    if (rsSeg.next()) {
				        double valorSeguro = rsSeg.getDouble("valor");
				        double tributoSeguro = valorSeguro * 0.20;
				        System.out.printf("Seguro de Vida contratado: R$ %.2f%n", valorSeguro);
				        System.out.printf("Tributo do Seguro (20%%): R$ %.2f%n", tributoSeguro);
				        System.out.printf("TOTAL GERAL: R$ %.2f%n", totalTaxas + tributoSeguro);
				    } else {
				        System.out.printf("TOTAL GERAL: R$ %.2f%n", totalTaxas);
				    }
				    
				    try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_tributacao.txt", false);
				    	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
				    	    bw.write("Relatorio de Tributacao\n");
				    	    bw.write("CPF: " + cpfDigitado + "\n");
				    	    bw.write("Total de taxas bancarias: R$ " + totalTaxas + "\n");
				    	    bw.write("Saque: R$0,10 | Deposito: R$0,10 | Transferencia: R$0,20\n");
				    	} catch (Exception ex) {
				    	    System.out.println("Erro ao gravar relatório.");
				    	}
				    
				} catch (Exception e) {
				    e.printStackTrace();
				}
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
			            try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_rendimento.txt", false);
			            	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
			            	    bw.write("Relatorio de Rendimento Poupanca\n");
			            	    bw.write("CPF: " + cpfDigitado + "\n");
			            	    bw.write("Valor simulado: R$ " + valorSimular + "\n");
			            	    bw.write("Prazo: " + dias + " dias\n");
			            	    bw.write("Rendimento: R$ " + rendimento + "\n");
			            	    bw.write("Valor total: R$ " + (valorSimular + rendimento) + "\n");
			            	} catch (Exception ex) {
			            	    System.out.println("Erro ao gravar relatório.");
			            	}
			            
			            continua = 0; //Para o loop parar depois de mostrar o resultado
			        } else {
			            System.out.println("Erro: Esta simulação é permitida apenas para Conta Poupança.");
			            continua = 0; // Para sair do loop mesmo em caso de erro
			        }			
					
				}
			}
			
			if(escolhaRelatorio == 4) {
			    System.out.println("=======SEGURO DE VIDA=======");
			    System.out.print("De quanto você quer contratar o seguro de vida? R$ ");
			    double valorSeguro = leitor.nextDouble();
			    double tributo = valorSeguro * 0.20;

			    try (java.sql.Connection conn = Conexao.conectar()) {

			        String sqlSaldo = "SELECT saldo FROM contas WHERE cpf_titular = ?";
			        java.sql.PreparedStatement stmtSaldo = conn.prepareStatement(sqlSaldo);
			        stmtSaldo.setString(1, cpfDigitado);
			        java.sql.ResultSet rsSaldo = stmtSaldo.executeQuery();

			        if (rsSaldo.next()) {
			            double saldoAtual = rsSaldo.getDouble("saldo");

			            if (saldoAtual < tributo) {
			                System.out.println("Saldo insuficiente para contratar o seguro.");
			            } else {
			                String sqlDebita = "UPDATE contas SET saldo = saldo - ? WHERE cpf_titular = ?";
			                java.sql.PreparedStatement stmtDeb = conn.prepareStatement(sqlDebita);
			                stmtDeb.setDouble(1, tributo);
			                stmtDeb.setString(2, cpfDigitado);
			                stmtDeb.executeUpdate();

			                String sqlSeguro = "INSERT INTO seguro (cpf_cliente, valor) VALUES (?, ?)";
			                java.sql.PreparedStatement stmtSeg = conn.prepareStatement(sqlSeguro);
			                stmtSeg.setString(1, cpfDigitado);
			                stmtSeg.setDouble(2, valorSeguro);
			                stmtSeg.executeUpdate();

			                System.out.printf("Seguro de R$ %.2f contratado!%n", valorSeguro);
			                System.out.printf("R$ %.2f foi debitado como tributo!%n", tributo);
			            }
			        }

			    } catch (Exception e) {
			        e.printStackTrace();
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
						    System.out.println("=====SAQUE=====");
						    System.out.println("Digite o valor que você quer sacar:");
						    double valorSaque = leitor.nextDouble();
						    double taxa = 0.10;

						    try (java.sql.Connection conn = Conexao.conectar()) {

						        String sqlSaldo = "SELECT saldo FROM contas WHERE cpf_titular = ?";
						        java.sql.PreparedStatement stmtSaldo = conn.prepareStatement(sqlSaldo);
						        stmtSaldo.setString(1, cpfDigitado);
						        java.sql.ResultSet rsSaldo = stmtSaldo.executeQuery();

						        if (rsSaldo.next()) {
						            double saldoAtual = rsSaldo.getDouble("saldo");
						            if (saldoAtual < valorSaque + taxa) {
						                System.out.println("Saldo insuficiente para o saque!");
						            } else {
						                String sql = "UPDATE contas SET saldo = saldo - ? WHERE cpf_titular = ?";
						                java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
						                stmt.setDouble(1, valorSaque + taxa);
						                stmt.setString(2, cpfDigitado);
						                stmt.executeUpdate();

						                String log = "INSERT INTO operacoes (cpf_origem, tipo, valor) VALUES (?, 'saque', ?)";
						                java.sql.PreparedStatement stmtLog = conn.prepareStatement(log);
						                stmtLog.setString(1, cpfDigitado);
						                stmtLog.setDouble(2, valorSaque);
						                stmtLog.executeUpdate();

						                System.out.println("Saque realizado!");
						                try (java.io.FileWriter fw = new java.io.FileWriter("operacoes.txt", true);
						                	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
						                	    bw.write("Saque | CPF: " + cpfDigitado + " | Valor: R$ " + valorSaque + "\n");
						                	} catch (Exception ex) {
						                	    System.out.println("Erro ao gravar arquivo de operações.");
						                	}
						            }
						        }

						    } catch (Exception e) {
						        e.printStackTrace();
						    }
						}
						
						if(SaqDepTransf == 2) {
						    System.out.println("=====DEPÓSITO=====");
						    System.out.println("Digite o valor que você quer depositar:");
						    double valorDeposito = leitor.nextDouble();
						    double taxa = 0.10;

						    try (java.sql.Connection conn = Conexao.conectar()) {

						        String sql = "UPDATE contas SET saldo = saldo + ? WHERE cpf_titular = ?";
						        java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
						        stmt.setDouble(1, valorDeposito - taxa);
						        stmt.setString(2, cpfDigitado);
						        stmt.executeUpdate();

						        String log = "INSERT INTO operacoes (cpf_origem, tipo, valor) VALUES (?, 'deposito', ?)";
						        java.sql.PreparedStatement stmtLog = conn.prepareStatement(log);
						        stmtLog.setString(1, cpfDigitado);
						        stmtLog.setDouble(2, valorDeposito);
						        stmtLog.executeUpdate();

						        System.out.println("Depósito realizado!");
						        try (java.io.FileWriter fw = new java.io.FileWriter("operacoes.txt", true);
						        	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
						        	    bw.write("Deposito | CPF: " + cpfDigitado + " | Valor: R$ " + valorDeposito + "\n");
						        	} catch (Exception ex) {
						        	    System.out.println("Erro ao gravar arquivo de operações.");
						        	}

						    } catch (Exception e) {
						        e.printStackTrace();
						    }
						}
						
					}	
						if(SaqDepTransf == 3) {
						    System.out.println("=====TRANSFERÊNCIA=====");
						    System.out.println("Digite o CPF da conta que você deseja transferir:");
						    leitor.nextLine();
						    String cpfRecebe = leitor.nextLine();
						    System.out.println("Qual valor você quer transferir?");
						    double valorTransferencia = leitor.nextDouble();
						    double taxa = 0.20;

						 try (java.sql.Connection conn = Conexao.conectar()) {

						 conn.setAutoCommit(false);

						 String sqlSaldo = "SELECT saldo FROM contas WHERE cpf_titular = ?";
						 java.sql.PreparedStatement stmtSaldo = conn.prepareStatement(sqlSaldo);
						 stmtSaldo.setString(1, cpfDigitado);
						 java.sql.ResultSet rsSaldo = stmtSaldo.executeQuery();

						    if (rsSaldo.next()) {
						      double saldoAtual = rsSaldo.getDouble("saldo");
						          if (saldoAtual < valorTransferencia + taxa) {
						                System.out.println("Saldo insuficiente para essa transferência!");
						            } else {
						                String sql1 = "UPDATE contas SET saldo = saldo - ? WHERE cpf_titular = ?";
						                java.sql.PreparedStatement stmt1 = conn.prepareStatement(sql1);
						                stmt1.setDouble(1, valorTransferencia + taxa);
						                stmt1.setString(2, cpfDigitado);
						                stmt1.executeUpdate();

						                String sql2 = "UPDATE contas SET saldo = saldo + ? WHERE cpf_titular = ?";
						                java.sql.PreparedStatement stmt2 = conn.prepareStatement(sql2);
						                stmt2.setDouble(1, valorTransferencia);
						                stmt2.setString(2, cpfRecebe);
						                stmt2.executeUpdate();

						                String log = "INSERT INTO operacoes (cpf_origem, cpf_destino, tipo, valor) VALUES (?, ?, ?, ?)";
						                java.sql.PreparedStatement stmtLog = conn.prepareStatement(log);
						                stmtLog.setString(1, cpfDigitado);
						                stmtLog.setString(2, cpfRecebe);
						                stmtLog.setObject(3, "transferencia", java.sql.Types.OTHER);
						                stmtLog.setDouble(4, valorTransferencia);
						                stmtLog.executeUpdate();

						                conn.commit();
						                System.out.println("Transferência realizada!");
						                try (java.io.FileWriter fw = new java.io.FileWriter("operacoes.txt", true);
						                	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
						                	    bw.write("Transferencia | CPF origem: " + cpfDigitado + " | CPF destino: " + cpfRecebe + " | Valor: R$ " + valorTransferencia + "\n");
						                	} catch (Exception ex) {
						                	    System.out.println("Erro ao gravar arquivo de operações.");
						                	}
						            }
						        }

						    } catch (Exception e) {
						        e.printStackTrace();
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
					            try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_saldo.txt", false);
					            	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
					            	    bw.write("Relatorio de Saldo\n");
					            	    bw.write("CPF: " + cpfDigitado + "\n");
					            	    bw.write("Saldo: R$ " + saldo + "\n");
					            	} catch (Exception ex) {
					            	    System.out.println("Erro ao gravar relatório.");
					            	}
					        }

					    } catch (Exception e) {
					        e.printStackTrace();
					    }
					}
				
					if(escolhaRelatorio == 2) {
							try (java.sql.Connection conn = Conexao.conectar()) {

							    // Busca o total de taxas das operações do cliente
							    String sql = "SELECT " +
							        "SUM(CASE WHEN tipo = 'saque' THEN 0.10 " +
							        "WHEN tipo = 'deposito' THEN 0.10 " +
							        "WHEN tipo = 'transferencia' THEN 0.20 ELSE 0 END) as total_taxas " +
							        "FROM operacoes WHERE cpf_origem = ?";
							    java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
							    stmt.setString(1, cpfDigitado);
							    java.sql.ResultSet rs = stmt.executeQuery();

							    double totalTaxas = 0;
							    if (rs.next()) {
							        totalTaxas = rs.getDouble("total_taxas");
							    }

							    System.out.printf("Total de taxas bancárias: R$ %.2f%n", totalTaxas);
							    System.out.println("Saque: R$0,10 | Depósito: R$0,10 | Transferência: R$0,20");

							    // Verifica se o cliente tem seguro
							    String sqlSeguro = "SELECT valor FROM seguro WHERE cpf_cliente = ?";
							    java.sql.PreparedStatement stmtSeg = conn.prepareStatement(sqlSeguro);
							    stmtSeg.setString(1, cpfDigitado);
							    java.sql.ResultSet rsSeg = stmtSeg.executeQuery();

							    if (rsSeg.next()) {
							        double valorSeguro = rsSeg.getDouble("valor");
							        double tributoSeguro = valorSeguro * 0.20;
							        System.out.printf("Seguro de Vida contratado: R$ %.2f%n", valorSeguro);
							        System.out.printf("Tributo do Seguro (20%%): R$ %.2f%n", tributoSeguro);
							        System.out.printf("TOTAL GERAL: R$ %.2f%n", totalTaxas + tributoSeguro);
							    } else {
							        System.out.printf("TOTAL GERAL: R$ %.2f%n", totalTaxas);
							    }
							    try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_tributacao.txt", false);
							    	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
							    	    bw.write("Relatorio de Tributacao\n");
							    	    bw.write("CPF: " + cpfDigitado + "\n");
							    	    bw.write("Total de taxas bancarias: R$ " + totalTaxas + "\n");
							    	    bw.write("Saque: R$0,10 | Deposito: R$0,10 | Transferencia: R$0,20\n");
							    	} catch (Exception ex) {
							    	    System.out.println("Erro ao gravar relatório.");
							    	}
							} catch (Exception e) {
							    e.printStackTrace();
							}
					
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
					            try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_rendimento.txt", false);
					            	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
					            	    bw.write("Relatorio de Rendimento Poupanca\n");
					            	    bw.write("CPF: " + cpfDigitado + "\n");
					            	    bw.write("Valor simulado: R$ " + valorSimular + "\n");
					            	    bw.write("Prazo: " + dias + " dias\n");
					            	    bw.write("Rendimento: R$ " + rendimento + "\n");
					            	    bw.write("Valor total: R$ " + (valorSimular + rendimento) + "\n");
					            	} catch (Exception ex) {
					            	    System.out.println("Erro ao gravar relatório.");
					            	}
					            continua = 0; //Para o loop parar depois de mostrar o resultado
					        } else {
					            System.out.println("Erro: Esta simulação é permitida apenas para Conta Poupança.");
					            continua = 0; // Para sair do loop mesmo em caso de erro
					        }			
							
						}
					}

					if(escolhaRelatorio == 4) {
					    try (java.sql.Connection conn = Conexao.conectar()) {

					        String sql = "SELECT COUNT(*) as total FROM contas c " +
					                     "JOIN funcionarios f ON c.agencia = f.agencia " +
					                     "WHERE f.cpf = ?";
					        java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
					        stmt.setString(1, cpfDigitado);
					        java.sql.ResultSet rs = stmt.executeQuery();

					        if (rs.next()) {
					            System.out.println("=======CONTAS AGÊNCIAS=======");
					            System.out.println("Total de contas na sua agência: " + rs.getInt("total"));
					        }

					    } catch (Exception e) {
					        e.printStackTrace();
					    }
					}
					//Caso for diretor ou presidente ele tem esse acesso!
					if(escolhaRelatorio == 5 && (tipoDeUsuario == 3 || tipoDeUsuario == 4)) {
					    try (java.sql.Connection conn = Conexao.conectar()) {

					        String sql = "SELECT cl.nome, cl.cpf, co.agencia FROM clientes cl " +
					                     "JOIN contas co ON cl.cpf = co.cpf_titular " +
					                     "ORDER BY cl.nome ASC";
					        java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
					        java.sql.ResultSet rs = stmt.executeQuery();

					        System.out.println("=== CLIENTES DO SISTEMA ===");
					        while (rs.next()) {
					            System.out.printf("Nome: %-20s | CPF: %s | Agência: %d%n",
					                rs.getString("nome"), rs.getString("cpf"), rs.getInt("agencia"));
					        }
					        
					        try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_clientes.txt", false);
					        	     java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
					        	    bw.write("Relatorio de Clientes do Sistema\n");
					        	    bw.write("CPF acessou: " + cpfDigitado + "\n");
					        	    // busca de novo para gravar no arquivo
					        	    String sql2 = "SELECT cl.nome, cl.cpf, co.agencia FROM clientes cl " +
					        	                  "JOIN contas co ON cl.cpf = co.cpf_titular " +
					        	                  "ORDER BY cl.nome ASC";
					        	    java.sql.PreparedStatement stmt2 = conn.prepareStatement(sql2);
					        	    java.sql.ResultSet rs2 = stmt2.executeQuery();
					        	    while (rs2.next()) {
					        	        bw.write("Nome: " + rs2.getString("nome") + " | CPF: " + rs2.getString("cpf") + " | Agencia: " + rs2.getInt("agencia") + "\n");
					        	    }
					        	    
					        	} catch (Exception ex) {
					        	    System.out.println("Erro ao gravar relatório.");
					        	}
					        
					    } catch (Exception e) {
					        e.printStackTrace();
					    }
					}
					//Caso ele for presidente ele tem esse acesso!
					if(escolhaRelatorio == 6 && tipoDeUsuario == 4) {
					    try (java.sql.Connection conn = Conexao.conectar()) {

					        String sql = "SELECT SUM(saldo) as total FROM contas";
					        java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
					        java.sql.ResultSet rs = stmt.executeQuery();

					        if (rs.next()) {
					            System.out.printf("Capital total armazenado no banco: R$ %.2f%n",
					                rs.getDouble("total"));
					            try (java.io.FileWriter fw = new java.io.FileWriter("relatorio_capital.txt", false);
					                    java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
					                   bw.write("Relatorio de Capital Total do Banco\n");
					                   bw.write("Capital total: R$ " + rs.getDouble("total") + "\n");
					            }
					        }

					    } catch (Exception e) {
					        e.printStackTrace();
					    }
					}
				}
					    //FIM DO SE FOR GERENTE
					break;

				}
	}
}