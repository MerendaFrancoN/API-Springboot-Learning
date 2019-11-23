package unsl.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unsl.entities.Cuenta;
import unsl.entities.Transferencia;
import unsl.repository.TransferenciaRepository;
import unsl.utils.RestService;

@Service
public class TransferenciasService {
    @Autowired
    TransferenciaRepository transferenciaRepository;

    @Autowired
    RestService restService;

    public Object saveTransferencia(Transferencia transferencia) {
        return transferenciaRepository.save(transferencia);
    }

    public Transferencia getTransferencia(Long id){
        return transferenciaRepository.findById(id).orElse(null);
    }

    public Cuenta getCuenta(Long id) throws Exception{
        return restService.getCuenta(String.format("http://54.84.140.133:8886/cuentas/%d",id));

    }

    public void updateCuenta(Cuenta cuenta) throws  Exception{
        restService.updateCuenta(String.format("http://54.84.140.133:8886/cuentas/%d",cuenta.getId()),cuenta);


    }

}
