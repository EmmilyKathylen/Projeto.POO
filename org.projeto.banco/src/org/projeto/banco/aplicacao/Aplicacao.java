package org.projeto.banco.aplicacao;

import java.math.BigDecimal;
import java.util.Scanner;

import org.projeto.banco.modelo.Cliente;
import org.projeto.banco.modelo.ContaBancaria;
import org.projeto.banco.persistence.PersistenciaEmArquivo;

public class Aplicacao {
	
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		
		while (true) {
			System.out.println("Bem-vindo ao banco! O que você deseja?");
			System.out.println("1. Cadastrar-se como cliente");
			System.out.println("2. Acessar");
			System.out.println("3. Deletar cliente");
			System.out.println("4. Sair");

			int opcao = scanner.nextInt();
			scanner.nextLine();

			ContaBancaria[] contas;
			switch (opcao) {
			case 1:
				System.out.println("Digite seu nome:");
				String nome = scanner.nextLine();
				System.out.println("Digite seu CPF:");
				String cpf = scanner.nextLine();
				Cliente cliente = new Cliente(cpf, nome);
				PersistenciaEmArquivo.getInstance().salvarCliente(cliente);
				break;

			case 2:
				System.out.println("Digite seu CPF:");
				cpf = scanner.next();

				cliente = PersistenciaEmArquivo.getInstance().localizarClientePorCPF(cpf);

				if (cliente == null) {
					System.out.println(" O cliente não foi encontrado");
					
					
					break;
				}

				System.out.println("Cliente selecionado: " + cliente.getNome());
				System.out.println("\nO que você gostaria de fazer?\n");
				System.out.println("1. Criar nova conta");
				System.out.println("2. Ver informações das contas");
				System.out.println("3. Realizar Deposito");
				System.out.println("4. Realizar Saque");
				System.out.println("5. Realizar Transferencia");
				System.out.println("6. Consultar saldo");
				System.out.println("7. Deletar conta");

				opcao = scanner.nextInt();
				scanner.nextLine();

				switch (opcao) {
				
				case 1:
					ContaBancaria conta = new ContaBancaria();
					cliente.adicionarConta(conta);
					PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
					System.out.println("Conta criada com sucesso!" +"\n");
					break;
					

					
				case 2:
					if (cliente.getContas().size() == 0) {
						System.err.println("Nao ha contas associada a este cliente.");
					} else {
						for (ContaBancaria c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					break;
					
					
					
				case 3: //depositar
					
					if (cliente.getContas().size() == 0) {
						System.err.println("Nao ha contas associada a este cliente.");
					} else {
						for (ContaBancaria c : cliente.getContas()) {
							System.out.println(c);
						}
					}
					System.out.println("Em qual conta deseja realizar o deposito?");
					int opcaoContaDeposito = 0;
					double quantia = 0.0;
					opcaoContaDeposito = scanner.nextInt();
					System.out.println("Insira o valor da quantia a ser depositada: ");
					quantia = scanner.nextDouble();
					ContaBancaria temp = cliente.localizarContaNumero(opcaoContaDeposito);
					if (temp != null) {
						temp.depositar(new BigDecimal(quantia));
						PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
					}
					break;
					
					
					
				
				case 4: //sacar
					
					 if (cliente.getContas().size() == 0) {
					        System.err.println("Nao ha contas associada a este cliente.");
					    } else {
					        for (ContaBancaria c : cliente.getContas()) {
					            System.out.println(c);
					        }
					    }
					System.out.println("Em qual conta deseja realizar o saque?");
					int opcaoContaSaque = 0;
					double quantiaSaque = 0.0;
					opcaoContaSaque = scanner.nextInt();
					System.out.println("Insira o valor do saque");
					quantiaSaque = scanner.nextInt();
					ContaBancaria tempSaque = cliente.localizarContaNumero(opcaoContaSaque);
					if (tempSaque != null) {
					   tempSaque.sacar(new BigDecimal(quantiaSaque));
					   PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
					}
					
					break;
					
					
					
					
				case 5: //transferir 
					
					System.out.println("Insira a conta de origem:");
					if (cliente.getContas().size() < 2) {
						System.out.println("Esse cliente possui menos de duas para realizar a transferência.");
					}else {
						for (ContaBancaria c : cliente.getContas()) {
							System.out.println(c);
						}
					System.out.println("Digite o número da conta origem:");
					int numContaOrigem = scanner.nextInt();
					System.out.println("Digite o número da conta destino:");
					int numContaDestino = scanner.nextInt();
					System.out.println("Digite o valor da transferência:");
					double valorTransferencia = scanner.nextDouble();
					
					ContaBancaria contaOrigem = cliente.localizarContaNumero(numContaOrigem);
					ContaBancaria contaDestino = cliente.localizarContaNumero(numContaDestino);
					
					if (contaOrigem != null & contaDestino != null) {
						 contaOrigem.transferir(contaDestino, new BigDecimal (valorTransferencia));	
							PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(cliente);
							System.out.println("Transferência realizada com sucesso.");
						}else {
						System.err.println("Conta de origem ou conta destino inválida");
					}
					}
				}
				break;

				
			case 6: // consultar saldo
			
			case 7: //deletar conta
				
			
			case 3: // deletar cliente
				 System.out.println("Digite o CPF do cliente que deseja deletar:");
				    String cpfDeletar = scanner.next();

				    Cliente clienteDeletar = PersistenciaEmArquivo.getInstance().localizarClientePorCPF(cpfDeletar);

				    if (clienteDeletar == null) {
				        System.out.println("O cliente não foi encontrado.");
				        PersistenciaEmArquivo.getInstance().atualizarClienteCadastro(clienteDeletar);
				    } else {
				        System.out.println("Cliente deletado com sucesso.");
				    }
				    break;   
				
			case 4:
				
				System.out.println("Obrigado pela preferência, até logo!");
				System.exit(0);
				
				default:
				System.out.println("Opção inválida");
				break;
			}
		}
		
		}
		
	}	

