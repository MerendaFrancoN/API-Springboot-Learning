package unsl.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsl.entities.Cliente;
import unsl.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    public Cliente getCliente(Long clientId) {
        return clienteRepository.findById(clientId).orElse(null);
    }

    public Cliente findByDni(Long dni) {
        return clienteRepository.findByDni(dni);
    }

    public Cliente saveClient(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente updateClient(Cliente updatedClient){
        Cliente cliente = clienteRepository.findById(updatedClient.getId()).orElse(null);

        //CONTROLS
        if (cliente ==  null){
            return null;
        }
        if(updatedClient.getNombre() != null)
            cliente.setNombre(updatedClient.getNombre());
        if(updatedClient.getApellido() != null)
            cliente.setApellido(updatedClient.getApellido());

        return clienteRepository.save(cliente);
    }

    public Cliente deleteClient(Long userId) {
        Cliente cliente = clienteRepository.findById(userId).orElse(null);
        if (cliente ==  null){
            return null;
        }
        cliente.setEstado(Cliente.Status.BAJA);
        return clienteRepository.save(cliente);
    }
}
