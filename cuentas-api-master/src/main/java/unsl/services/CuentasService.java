package unsl.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsl.entities.Cliente;
import unsl.entities.Cuenta;
import unsl.repository.CuentaRepository;
import unsl.utils.RestService;

@Service
public class CuentasService {
    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    RestService restService;

    public Cuenta getCuenta(Long cuentaId) {
        return cuentaRepository.findById(cuentaId).orElse(null);
    }

    public List<Cuenta> findByTitular(Long titular) {
        return cuentaRepository.findByTitular(titular);
    }

    public Cuenta saveCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Cliente getCliente(Long clienteId) throws Exception{
        return restService.getCliente(String.format("http://localhost:8888/clientes/%d",clienteId));
    }

    public Cuenta updateCuenta(Cuenta updatedCuenta, Long id){
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);

        //CONTROLS
        if (cuenta ==  null) {
            return null;
        }
        if(updatedCuenta.getStatus()!=null)
            cuenta.setStatus(updatedCuenta.getStatus());
        if(updatedCuenta.getSaldo()>=0)
            cuenta.setSaldo(updatedCuenta.getSaldo());
        return cuentaRepository.save(cuenta);
    }


    public Cuenta deleteCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElse(null);;
        if (cuenta ==  null){
            return null;
        }
        else{
            cuenta.setStatus(Cuenta.Status.BAJA);

            return cuentaRepository.save(cuenta);
        }

    }
}
