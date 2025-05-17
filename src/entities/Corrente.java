package entities;

public class Corrente extends VtrBank{
	
	private double limite;
	private double cofrinho;
	
	public Corrente() {
	}

	public Corrente(String nome, int agencia, int conta) {
		super(nome, agencia, conta);
		this.limite = 5000;
		this.cofrinho = 0.0;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	@Override
	public void deposita(double valor) {
		super.deposita(valor);
	}

	@Override
	public void saca(double valor, double taxa) {
		super.saca(valor, taxa);

	}
	
	@Override
	public void transferencia(IVtrBank destino, double valor) {
		super.transferencia(destino, valor);

	}

	@Override
	public void receber(double valor) {
		super.receber(valor);
	}

	@Override
	public String extrato() {
		StringBuilder sb = new StringBuilder();

		sb.append("CONTA CORRENTE");
		sb.append(super.extrato());
		sb.append("\n");
		sb.append("Cofrinho: R$" + cofrinho);

		return sb.toString();
	}
	
	public void emprestimo(double valor) {
		if(valor > limite) {
			System.out.println("Emprestimo máximo de R$5000,00");
			System.out.println("Disponivel: " + limite);
		}
		else {
			saldo += valor;
			limite -= valor;
		}
	}
	
	public void guardarCofrinho(double valor) {
		if(valor > saldo) {
			System.out.println("Saldo indisponível para guardar, voce tem: R$" + saldo);
		}
		else {
			cofrinho += valor;
			saldo -= valor;
		}
	}
	
	public void resgatarCofrinho(double valor) {
		if(valor > cofrinho) {
			System.out.println("Saldo indisponível para resgatar, voce tem: R$" + cofrinho);
		}
		else {
			cofrinho -= valor;
			saldo += valor;
		}
	}

}
