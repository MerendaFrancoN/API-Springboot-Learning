package unsl.entities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "clientes", uniqueConstraints={@UniqueConstraint(columnNames = {"dni"})})
public class Cliente {


	public static enum Status {
		ACTIVO,
		BAJA
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long dni;

	private String nombre;

	private String apellido;

	@Enumerated(EnumType.STRING)
    private Status estado;
	
	public void setId(long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setDni(Long dni) {
		this.dni = dni;
	}

	public Long getDni() {
		return dni;
	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@JsonProperty("estado")
	public Status getEstado() {
		return estado;
	}

	@JsonProperty("estado")
	public void setEstado(Status estado) {
		this.estado = estado;
	}
}
