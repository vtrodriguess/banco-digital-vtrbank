package entities;

public abstract class VtrBank implements IVtrBank {
	
	protected String nome;
	protected double saldo;
	protected int agencia;
	protected int conta;
	
	public VtrBank() {
		
	}

	public VtrBank(String nome, int agencia, int conta) {
		this.nome = nome;
		this.saldo = 0.0;
		this.agencia = agencia;
		this.conta = conta;
	}

	@Override
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getSaldo() {
		return saldo;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getConta() {
		return conta;
	}

	public void setConta(int conta) {
		this.conta = conta;
	}
	
	@Override
	public void deposita(double valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor precisa ser maior do que zero");
		} else {
			saldo += valor;
		}

	}

	@Override
	public void saca(double valor, double taxa) {
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor precisa ser maior do que zero");
		} else if (saldo - (valor + taxa) < 0) {
			throw new IllegalArgumentException("Valor de Saque precisa ser menor ou igual ao saldo");
		} else {
			saldo = saldo - valor - taxa;
		}

	}
	
	@Override
	public void transferencia(IVtrBank destino, double valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor precisa ser maior do que zero");
		} else if (saldo - valor < 0) {
			throw new IllegalArgumentException("Valor de Transferencia precisa ser menor ou igual ao saldo");
		} else {
			saldo -= valor;
			destino.receber(valor);
		}
		

	}

	@Override
	public void receber(double valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor precisa ser maior do que zero");
		} else {
			saldo += valor;
		}
	}

	@Override
	public String extrato() {
		StringBuilder sb = new StringBuilder();

		sb.append("\n");
		sb.append("Nome: " + nome);
		sb.append("\n");
		sb.append("Agencia: " + agencia);
		sb.append("\n");
		sb.append("Conta corrente: " + conta);
		sb.append("\n");
		sb.append("Saldo: " + saldo);

		return sb.toString();
	}
	
	

	

}
