package unsl.services;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import unsl.config.CacheConfig;
import unsl.entities.Cliente;
import unsl.entities.Cuenta;
import unsl.repository.ClienteRepository;
import unsl.utils.RestService;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    RestService restService;

    @Cacheable(value = CacheConfig.CLIENTE_CACHE)
    public List<Cliente> getAll() {

        simulateSlowService();
        return clienteRepository.findAll();
    }

    @Cacheable(value = CacheConfig.CLIENTE_CACHE)
    public Cliente getCliente(Long clientId) {

        simulateSlowService();
        return clienteRepository.findById(clientId).orElse(null);
    }

    @Cacheable(value = CacheConfig.CLIENTE_CACHE)
    public Cliente findByDni(Long dni) {

        simulateSlowService();
        return clienteRepository.findByDni(dni);
    }

    @CacheEvict(value = CacheConfig.CLIENTE_CACHE, allEntries = true)
    public Cliente saveClient(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @CacheEvict(value = CacheConfig.CLIENTE_CACHE, allEntries = true)
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

    @CacheEvict(value = CacheConfig.CLIENTE_CACHE, allEntries = true)
    public Cliente deleteClient(Long userId) {
        Cliente cliente = clienteRepository.findById(userId).orElse(null);
        if (cliente ==  null){
            return null;
        }

        cliente.setEstado(Cliente.Status.BAJA);

        List<Cuenta> cuentas;
        try {
            cuentas = restService.getCuentas("http://localhost:8887/cuentas/busqueda?titular="+userId);
            Iterator i = cuentas.iterator();
            while(i.hasNext()){
                Cuenta cuentaTmp = (Cuenta) i.next();
                restService.logicDeleteCuenta("http://localhost:8887/cuentas/"+cuentaTmp.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
