package unsl.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "cuentas")
public class Cuenta {

	public static enum Status {
		ACTIVO,
		BAJA
	}

	public static enum CurrencyType{
		PESO_AR,
		DOLAR,
		EURO
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long titular;

	private double saldo;


	@Enumerated(EnumType.STRING)
	private CurrencyType tipo_moneda;

	@Enumerated(EnumType.STRING)
	@JsonProperty("estado")
    private Status status;
	

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@JsonProperty("estado")
	public Status getStatus() {
		return status;
	}

	@JsonProperty("estado")
	public void setStatus(Status status) {
		this.status = status;
	}

	public void setTitular(Long titular) {
		this.titular = titular;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public void setTipo_moneda(CurrencyType tipo_moneda) {
		this.tipo_moneda = tipo_moneda;
	}

	public long getTitular() {
		return titular;
	}

	public double getSaldo() {
		return saldo;
	}

	public CurrencyType getTipo_moneda() {
		return tipo_moneda;
	}
}
