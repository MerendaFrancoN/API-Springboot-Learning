package unsl.entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "transferencias")
public class Transferencia {



	public static enum Status {
		PENDIENTE,
		PROCESADA,
		CANCELADA
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long id_cuenta_origen;

	private long id_cuenta_destino;

	private double monto;



	private boolean processed;


	@Enumerated(EnumType.STRING)
    private Status estado;

	//Getters and setters

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId_cuenta_origen(Long id_cuenta_origen) {
		this.id_cuenta_origen = id_cuenta_origen;
	}

	public void setId_cuenta_destino(long id_cuenta_destino) {
		this.id_cuenta_destino = id_cuenta_destino;
	}

	public long getId_cuenta_origen() {
		return id_cuenta_origen;
	}

	public long getId_cuenta_destino() {
		return id_cuenta_destino;
	}

	public double getMonto() {return monto;}

	public void setMonto(double monto) {this.monto = monto; }

	public void setId_cuenta_origen(long id_cuenta_origen) {
		this.id_cuenta_origen = id_cuenta_origen;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public Status getEstado() {
		return estado;
	}

	public void setEstado(Status estado) {
		this.estado = estado;
	}

}
