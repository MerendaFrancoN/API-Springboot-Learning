package unsl.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unsl.entities.Cuenta;
import unsl.entities.ResponseError;
import unsl.entities.Transferencia;
import unsl.services.TransferenciasService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TransferenciaController {

    @Autowired
    TransferenciasService transferenciasService;




    @PostMapping(value = "/transferencias")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Object createTransferencia(@RequestBody Transferencia transferencia) {
        Map<String, Object> mapResponse = new HashMap<>();

        try {
            Cuenta cuentaOrigen = transferenciasService.getCuenta(transferencia.getId_cuenta_origen());
            Cuenta cuentaDestino = transferenciasService.getCuenta(transferencia.getId_cuenta_destino());
            double saldoFinalOrigen = 0.0;
            double saldoFinalDestino = 0.0;


            if (cuentaDestino == null || cuentaOrigen == null)
                return new ResponseEntity<>(new ResponseError(404, "Cuenta de origen o destina no existente"), HttpStatus.NOT_FOUND);

            if (cuentaDestino.getId() == cuentaOrigen.getId())
                return new ResponseEntity<>(new ResponseError(400, "Cuenta de origen y destino iguales"), HttpStatus.BAD_REQUEST);

            if (transferencia.getMonto() < 0)
                return new ResponseEntity<>(new ResponseError(400, "Monto de la transferencia no puede ser negativo"), HttpStatus.BAD_REQUEST);

            if (cuentaDestino.getTipo_moneda() != cuentaOrigen.getTipo_moneda())
                return new ResponseEntity<>(new ResponseError(400, "Cuenta de origen y destino de distintos tipos"), HttpStatus.BAD_REQUEST);

            saldoFinalOrigen = cuentaOrigen.getSaldo() - transferencia.getMonto();

            if (saldoFinalOrigen < 0.0)
                return new ResponseEntity<>(new ResponseError(400, "Saldo insuficiente"), HttpStatus.BAD_REQUEST);

            cuentaOrigen.setSaldo(saldoFinalOrigen);

            transferenciasService.updateCuenta(cuentaOrigen);

            transferencia.setEstado(Transferencia.Status.PENDIENTE);
            transferencia.setProcessed(false);

        } catch (Exception e) {
            e.printStackTrace();
        }

        transferenciasService.saveTransferencia(transferencia);

        mapResponse.put("id_cuenta_origen",transferencia.getId_cuenta_origen());
        mapResponse.put("id_cuenta_destino",transferencia.getId_cuenta_destino());
        mapResponse.put("estado",transferencia.getEstado());
        mapResponse.put("monto",transferencia.getMonto());


        return mapResponse;

    }

    @PutMapping(value="/transferencias/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object processTransferencia(@RequestBody Transferencia transferenciaState, @PathVariable Long id){


        Transferencia transferenciaToProcess = transferenciasService.getTransferencia(id);
        if(transferenciaToProcess.isProcessed())
            return new ResponseEntity<>(new ResponseError(400, "Transferencia ya procesada o cancelada"), HttpStatus.BAD_REQUEST);

        try{

            if(transferenciaState.getEstado() == Transferencia.Status.PROCESADA){
                Cuenta cuentaDestino = transferenciasService.getCuenta(transferenciaToProcess.getId_cuenta_destino());
                double saldoActualizado =cuentaDestino.getSaldo()+transferenciaToProcess.getMonto();
                //Actualizar el saldo
                cuentaDestino.setSaldo(saldoActualizado);
                transferenciasService.updateCuenta(cuentaDestino);
                //Setear estado
                transferenciaToProcess.setEstado(Transferencia.Status.PROCESADA);
                transferenciaToProcess.setProcessed(true);

            }else if(transferenciaState.getEstado() == Transferencia.Status.CANCELADA){
                Cuenta cuentaOrigen = transferenciasService.getCuenta(transferenciaToProcess.getId_cuenta_origen());

                //Actualizar el saldo
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo()+ transferenciaToProcess.getMonto());
                transferenciasService.updateCuenta(cuentaOrigen);
                //Setear estado
                transferenciaToProcess.setEstado(Transferencia.Status.CANCELADA);
                transferenciaToProcess.setProcessed(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return transferenciasService.saveTransferencia(transferenciaToProcess);

    }

}

