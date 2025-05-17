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
		int menu = 0;

		do {
			System.out.println("1 - Conta Corrente \n2 - Conta Poupanca \n3 - Conta Salario \n4 - Sair");
			menu = sc.nextInt();

			try {

				switch (menu) {
				case 1: {
					contaCorrente(sc, conta);
					break;
				}
				case 2: {
					contaPoupanca(sc, conta);
					break;
				}
				case 3: {
					contaSalario(sc, conta);
					break;
				}
				case 4: {
					System.out.println("Saindo...");
					break;
				}
				default:
					throw new IllegalArgumentException("Digite valores de acordo com o menu");
				}

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}

		} while (menu != 4);

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

	public static void depositar(Scanner sc, List<IVtrBank> contas) {
		int contaU = contaUsada(sc);
		System.out.println("Valor para deposito: ");
		double dep = sc.nextDouble();
		try {
			contas.get(contaU).deposita(dep);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void sacar(Scanner sc, List<IVtrBank> contas, double taxa) {
		int contaU = contaUsada(sc);
		System.out.println("Valor para sacar: ");
		double saque = sc.nextDouble();
		try {
			contas.get(contaU).saca(saque, taxa);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void extrato(Scanner sc, List<IVtrBank> contas) {
		int contaU = contaUsada(sc);
		System.out.println(contas.get(contaU).extrato());
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

		}
		else {
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

	public static void transferencia(Scanner sc, List<IVtrBank> contas, int contaU, int contaT) {
		IVtrBank origem = contas.get(contaU);
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

	public static void contaCorrente(Scanner sc, List<IVtrBank> corrente) {
		int menu = 0;
		do {
			System.out.println(
					"1 - Cadastrar \n2 - Depositar \n3 - Sacar \n4 - Extrato \n5 - Listar \n6 - Transferencia \n7 - Emprestimo \n8 - Cofrinho \n9 - Sair");
			menu = sc.nextInt();

			switch (menu) {
			case 1: {
				IVtrBank cadastro = cadastro(sc, 1);
				corrente.add(cadastro);
				break;
			}
			case 2: {
				depositar(sc, corrente);
				break;
			}
			case 3: {
				sacar(sc, corrente, 5);
				break;
			}
			case 4: {
				extrato(sc, corrente);
				break;
			}
			case 5: {
				listaContas(corrente, Corrente.class);
				break;
			}
			case 6: {
				int contaU = contaUsada(sc);
				int contaT = contaTransferir(sc);
				IVtrBank destino = corrente.get(contaT);
				IVtrBank origem = corrente.get(contaU);
				if (destino instanceof Salario & !(origem instanceof Salario)) {
					System.out.println("Não é possível realizar transferencia");
					break;
				} else {
					transferencia(sc, corrente, contaU, contaT);
					break;
				}
			}
			case 7: {
				int contaU = contaUsada(sc);
				emprestimo(sc, corrente, contaU);
				break;
			}
			case 8: {
				int contaU = contaUsada(sc);
				cofrinho(sc, corrente, contaU);
				break;
			}
			case 9: {
				System.out.println("Saindo...");
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + menu);
			}

		} while (menu != 9);
	}

	public static void contaPoupanca(Scanner sc, List<IVtrBank> poupanca) {
		int menu = 0;
		do {
			System.out.println(
					"1 - Cadastrar \n2 - Depositar \n3 - Sacar \n4 - Extrato \n5 - Listar \n6 - Transferencia \n7 - Sair");
			menu = sc.nextInt();

			switch (menu) {
			case 1: {
				IVtrBank cadastro = cadastro(sc, 2);
				poupanca.add(cadastro);
				break;
			}
			case 2: {
				depositar(sc, poupanca);
				break;
			}
			case 3: {
				sacar(sc, poupanca, 10);
				break;
			}
			case 4: {
				extrato(sc, poupanca);
				break;
			}
			case 5: {
				listaContas(poupanca, Poupanca.class);
				break;
			}
			case 6: {
				int contaU = contaUsada(sc);
				int contaT = contaTransferir(sc);
				IVtrBank destino = poupanca.get(contaT);
				IVtrBank origem = poupanca.get(contaU);
				if (!(destino instanceof Corrente) && !(origem instanceof Poupanca)) {
					System.out.println("Não é possível realizar transferencia");
					break;
				} else {
					transferencia(sc, poupanca, contaU, contaT);
					break;
				}
			}
			case 7: {
				System.out.println("Saindo...");
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + menu);
			}

		} while (menu != 7);
	}

	public static void contaSalario(Scanner sc, List<IVtrBank> salario) {
		int menu = 0;
		do {
			System.out.println(
					"1 - Cadastrar \n2 - Depositar \n3 - Sacar \n4 - Extrato \n5 - Listar \n6 - Transferencia \n7 - Sair");
			menu = sc.nextInt();

			switch (menu) {
			case 1: {
				IVtrBank cadastro = cadastro(sc, 3);
				salario.add(cadastro);
				break;
			}
			case 2: {
				depositar(sc, salario);
				break;
			}
			case 3: {
				sacar(sc, salario, 10);
				break;
			}
			case 4: {
				extrato(sc, salario);
				break;
			}
			case 5: {
				listaContas(salario, Salario.class);
				break;
			}
			case 6: {
				int contaU = contaUsada(sc);
				int contaT = contaTransferir(sc);
				IVtrBank destino = salario.get(contaT);
				IVtrBank origem = salario.get(contaU);
				if (!(destino instanceof Corrente) && !(origem instanceof Salario)) {
					System.out.println("Não é possível realizar transferencia");
					break;
				} else {
					transferencia(sc, salario, contaU, contaT);
					break;
				}
			}
			case 7: {
				System.out.println("Saindo...");
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + menu);
			}

		} while (menu != 7);

	}

}
