package unsl.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import unsl.config.CacheConfig;
import unsl.entities.Cliente;
import unsl.repository.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Cacheable(CacheConfig.CLIENTE_CACHE)
    public List<Cliente> getAll() {

        simulateSlowService();
        return clienteRepository.findAll();
    }

    @Cacheable(CacheConfig.CLIENTE_CACHE)
    public Cliente getCliente(Long clientId) {

        simulateSlowService();
        return clienteRepository.findById(clientId).orElse(null);
    }

    @Cacheable(CacheConfig.CLIENTE_CACHE)
    public Cliente findByDni(Long dni) {

        simulateSlowService();
        return clienteRepository.findByDni(dni);
    }

    @CachePut(CacheConfig.CLIENTE_CACHE)
    public Cliente saveClient(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @CachePut(CacheConfig.CLIENTE_CACHE)
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

    @CachePut(CacheConfig.CLIENTE_CACHE)
    public Cliente deleteClient(Long userId) {
        Cliente cliente = clienteRepository.findById(userId).orElse(null);
        if (cliente ==  null){
            return null;
        }cliente.setEstado(Cliente.Status.BAJA);

        return clienteRepository.save(cliente);
    }


    private void simulateSlowService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
