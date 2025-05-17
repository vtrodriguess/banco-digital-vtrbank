package entities;

public interface IVtrBank {
	
	void deposita(double valor);
	void saca(double valor, double taxa);
	String extrato();
	void transferencia(IVtrBank destino, double valor);
	void receber(double valor);
	String getNome();
}
