package entities;

public class Salario extends VtrBank implements IVtrBank{

	public Salario() {
	}

	public Salario(String nome, int agencia, int conta) {
		super(nome, agencia, conta);
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
		
		sb.append("CONTA SALARIO");
		sb.append(super.extrato());
		
		return sb.toString();
	}


}
