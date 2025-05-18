package banco;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entities.Corrente;
import entities.IVtrBank;
import entities.Poupanca;
import entities.Salario;
import entities.VtrBank;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		List<IVtrBank> conta = new ArrayList<>();

		iniciarSistema(sc, conta);

	}

	public static VtrBank cadastro(Scanner sc, int tipo) {

		System.out.println("Nome: ");
		String nome = sc.next();
		System.out.println("Agencia: ");
		int agencia = sc.nextInt();
		System.out.println("Conta: ");
		int conta = sc.nextInt();

		int qntdConta = String.valueOf(conta).length();
		int qntdAgencia = String.valueOf(agencia).length();

		if (qntdConta != 6 || qntdAgencia != 4) {
			throw new IllegalArgumentException("Conta deve ter 6 dígitos e agência 4.");
		}

		if (tipo == 1) {
			return new Corrente(nome, agencia, conta);
		} else if (tipo == 2) {
			return new Poupanca(nome, agencia, conta);
		} else if (tipo == 3) {
			return new Salario(nome, agencia, conta);
		} else {
			throw new IllegalArgumentException("Tipo de conta inválido.");
		}
	}

	public static boolean verificacao(List<IVtrBank> conta, int menu) {
		Class<?> tipoConta = null;
		switch (menu) {
		case 1:
			System.out.println("Iniciando cadastro...");
			return false;
		case 2:
			tipoConta = Corrente.class;
			break;
		case 3:
			tipoConta = Poupanca.class;
			break;
		case 4:
			tipoConta = Salario.class;
			break;
		default:
			System.out.println("Inválido");
			return false;
		}

		boolean existe = false;
		if (!conta.isEmpty()) {
			for (int i = 0; i < conta.size(); i++) {
				if (tipoConta.isInstance(conta.get(i))) {
					existe = true;
					break;
				}

			}
		}

		return existe;
	}

	public static void iniciarSistema(Scanner sc, List<IVtrBank> conta) {
		int menu = 0;
		int contaUsadaSalario = -1;
		int contaUsadaCorrente = -1;
		int contaUsadaPoupanca = -1;
		do {
			System.out.println("1 - Cadastrar \n2 - Conta Corrente \n3 - Conta Poupanca \n4 - Conta Salario \n5 - Sair");
			menu = sc.nextInt();

			boolean existe = verificacao(conta, menu);

			try {

				switch (menu) {
				case 1: {
			        System.out.println("Tipo da conta para cadastrar:\n1 - Corrente\n2 - Poupança\n3 - Salário");
			        int tipo = sc.nextInt();
			        try {
			            IVtrBank novaConta = cadastro(sc, tipo);
			            conta.add(novaConta);
			            System.out.println("Conta cadastrada com sucesso.");
			        } catch (IllegalArgumentException e) {
			            System.out.println(e.getMessage());
			        }
			        break;
			    }
				case 2: {
					if (existe) {
						contaUsadaCorrente = contaUsada(sc);
						contaCorrente(sc, conta, contaUsadaCorrente);
						break;
					} else {
						System.out.println("Nenhuma conta do tipo cadastrada");
						break;
					}
				}
				case 3: {
					if (existe) {
						contaUsadaPoupanca = contaUsada(sc);
						contaPoupanca(sc, conta, contaUsadaPoupanca);
						break;
					} else {
						System.out.println("Nenhuma conta do tipo cadastrada");
						break;
					}
				}
				case 4: {
					if (existe) {
						contaUsadaSalario = contaUsada(sc);
						contaCorrente(sc, conta, contaUsadaSalario);
						break;
					} else {
						System.out.println("Nenhuma conta do tipo cadastrada");
						break;
					}
				}
				case 5: {
					System.out.println("Saindo...");
					break;
				}
				default:
					throw new IllegalArgumentException("Digite valores de acordo com o menu");
				}

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		} while (menu != 5);
	}

	public static void depositar(Scanner sc, List<IVtrBank> contas, int contaU) {
		System.out.println("Valor para deposito: ");
		double dep = sc.nextDouble();
		try {
			contas.get(contaU).deposita(dep);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void sacar(Scanner sc, List<IVtrBank> contas, double taxa, int contaUsada) {
		System.out.println("Valor para sacar: ");
		double saque = sc.nextDouble();
		try {
			contas.get(contaUsada).saca(saque, taxa);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void extrato(Scanner sc, List<IVtrBank> contas, int contaU, Class<?> tipoEsperado) {
		if (tipoEsperado.isInstance(contas.get(contaU))) {
			System.out.println(contas.get(contaU).extrato());
		}
	}

	public static void emprestimo(Scanner sc, List<IVtrBank> contas, int contaUsada) {
		if (contas.get(contaUsada) instanceof Corrente) {
			System.out.println("Valor emprestimo: ");
			double valor = sc.nextDouble();
			((Corrente) contas.get(contaUsada)).emprestimo(valor);
			;
		} else {
			System.out.println("Emprestimo indisponível");
		}

	}

	public static void cofrinho(Scanner sc, List<IVtrBank> contas, int contaUsada) {
		System.out.println("Tipo de operação: 1 - Guardar \n2 - Resgatar");
		int operacao = sc.nextInt();

		if (contas.get(contaUsada) instanceof Corrente) {
			if (operacao == 1) {
				System.out.println("Valor a guardar: ");
				double valor = sc.nextDouble();
				((Corrente) contas.get(contaUsada)).guardarCofrinho(valor);
			} else if (operacao == 2) {
				System.out.println("Valor a resgatar: ");
				double valor = sc.nextDouble();
				((Corrente) contas.get(contaUsada)).resgatarCofrinho(valor);
			} else {
				System.out.println("Operação incorreta");
			}

		} else {
			System.out.println("Disponível apenas para conta corrente");
		}
	}

	public static void listaContas(List<IVtrBank> contas, Class<?> tipoEsperado) {
		if (!contas.isEmpty()) {
			for (int i = 0; i < contas.size(); i++) {
				if (tipoEsperado.isInstance(contas.get(i))) {
					System.out.println(
							i + ": " + contas.get(i).getNome() + " - " + contas.get(i).getClass().getSimpleName());
				}

			}
			System.out.println("--------------------------");
		} else {
			System.out.println("Sem conta cadastrada");
		}
	}

	public static void transferencia(Scanner sc, List<IVtrBank> contas, int contaUsada, int contaT) {
		IVtrBank origem = contas.get(contaUsada);
		IVtrBank destino = contas.get(contaT);
		System.out.println("Valor: ");
		double valor = sc.nextDouble();
		try {
			origem.transferencia(destino, valor);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public static int contaUsada(Scanner sc) {
		System.out.println("Escolha a conta a ser usada: ");
		int contaU = sc.nextInt();

		return contaU;
	}

	public static int contaTransferir(Scanner sc) {
		System.out.println("Escolha a conta para transferir: ");
		int contaT = sc.nextInt();

		return contaT;
	}

	public static void contaCorrente(Scanner sc, List<IVtrBank> corrente, int contaUsada) {
		int menu = 0;
		do {
			System.out.println(
					"1 - Depositar \n2 - Sacar \n3 - Extrato \n4 - Listar \n5 - Transferencia \n6 - Emprestimo \n7 - Cofrinho \n8 - Sair");
			menu = sc.nextInt();

			switch (menu) {

			case 1: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					depositar(sc, corrente, contaUsada);
					break;
				}
			}
			case 2: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					sacar(sc, corrente, 5, contaUsada);
					break;
				}
			}
			case 3: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					extrato(sc, corrente, contaUsada, Corrente.class);
					break;
				}
			}
			case 4: {
				listaContas(corrente, Corrente.class);
				break;
			}
			case 5: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					int contaT = contaTransferir(sc);
					IVtrBank destino = corrente.get(contaT);
					IVtrBank origem = corrente.get(contaUsada);
					if (destino instanceof Salario & !(origem instanceof Salario)) {
						System.out.println("Não é possível realizar transferencia");
						break;
					} else {
						transferencia(sc, corrente, contaUsada, contaT);
						break;
					}
				}
			}
			case 6: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					emprestimo(sc, corrente, contaUsada);
					break;
				}
			}
			case 7: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					cofrinho(sc, corrente, contaUsada);
					break;
				}
			}
			case 8: {
				System.out.println("Saindo...");
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + menu);
			}

		} while (menu != 8);
	}

	public static void contaPoupanca(Scanner sc, List<IVtrBank> poupanca, int contaUsada) {
		int menu = 0;
		do {
			System.out.println(
					"1 - Depositar \n2 - Sacar \n3 - Extrato \n4 - Listar \n5 - Transferencia \n6 - Sair");
			menu = sc.nextInt();

			switch (menu) {
			case 1: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					depositar(sc, poupanca, contaUsada);
					break;
				}
			}
			case 2: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					sacar(sc, poupanca, 10, contaUsada);
					break;
				}
			}
			case 3: {
				if (contaUsada == -1) {
					System.out.println("Nenhuma conta cadastrada");
					break;
				} else {
					extrato(sc, poupanca, contaUsada, Poupanca.class);
					break;
				}
			}
			case 4: {
				listaContas(poupanca, Poupanca.class);
				break;
			}
			case 5: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					int contaT = contaTransferir(sc);
					IVtrBank destino = poupanca.get(contaT);
					IVtrBank origem = poupanca.get(contaUsada);
					if (!(destino instanceof Corrente) && !(origem instanceof Poupanca)) {
						System.out.println("Não é possível realizar transferencia");
						break;
					} else {
						transferencia(sc, poupanca, contaUsada, contaT);
						break;
					}
				}
			}
			case 6: {
				System.out.println("Saindo...");
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + menu);
			}

		} while (menu != 6);
	}

	public static void contaSalario(Scanner sc, List<IVtrBank> salario, int contaUsada) {
		int menu = 0;
		do {
			System.out.println(
					"1 - Depositar \n2 - Sacar \n3 - Extrato \n4 - Listar \n5 - Transferencia \n6 - Sair");
			menu = sc.nextInt();

			switch (menu) {
			case 1: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					depositar(sc, salario, contaUsada);
					break;
				}
			}
			case 2: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					sacar(sc, salario, 10, contaUsada);
					break;
				}
			}
			case 3: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					extrato(sc, salario, contaUsada, Salario.class);
					break;
				}
			}
			case 4: {
				listaContas(salario, Salario.class);
				break;
			}
			case 5: {
				if (contaUsada == -1) {
					System.out.println("Não existe conta cadastrada");
					break;
				} else {
					int contaT = contaTransferir(sc);
					IVtrBank destino = salario.get(contaT);
					IVtrBank origem = salario.get(contaUsada);
					if (!(destino instanceof Corrente) && !(origem instanceof Salario)) {
						System.out.println("Não é possível realizar transferencia");
						break;
					} else {
						transferencia(sc, salario, contaUsada, contaT);
						break;
					}
				}
			}
			case 6: {
				System.out.println("Saindo...");
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + menu);
			}

		} while (menu != 6);

	}

}
