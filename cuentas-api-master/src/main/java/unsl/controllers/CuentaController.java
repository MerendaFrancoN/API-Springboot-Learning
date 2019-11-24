package unsl.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unsl.entities.Cuenta;
import unsl.entities.ResponseError;
import unsl.services.CuentasService;

@RestController
public class CuentaController {
    @Autowired
    CuentasService cuentasService;


    @GetMapping(value = "/cuentas/{cuentaId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object getAccount(@PathVariable("cuentaId") Long cuentaId) {
        Cuenta cuenta = cuentasService.getCuenta(cuentaId);
        if ( cuenta == null) {
            return new ResponseEntity<>(new ResponseError(404, String.format("Cuenta %d not found", cuentaId)), HttpStatus.NOT_FOUND);
        }
        return cuenta;
    }

    @GetMapping(value = "/cuentas/busqueda")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object searchByTitular(@RequestParam("titular") Long titular) {
        List<Cuenta> cuentas = cuentasService.findByTitular(titular);
        if ( cuentas == null) {
            return new ResponseEntity<>(new ResponseError(404, String.format("Cuenta with dni %d not found", titular)), HttpStatus.NOT_FOUND);
        }
        return cuentas;
    }

    @PostMapping(value = "/cuentas")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Object createAccount(@RequestBody Cuenta cuenta){
        boolean pesoAR=false,euro=false,dolar=false;
        List<Cuenta> cuentas = cuentasService.findByTitular(cuenta.getTitular());

        try{
            cuentasService.getCliente(cuenta.getTitular());
        }catch (Exception e){
            return new ResponseEntity<>(new ResponseError(404, String.format("Cliente con id %d not found ", cuenta.getTitular())), HttpStatus.NOT_FOUND);
        }

        //Not proud of this code. Too lazy to refactor this.
        for(Cuenta cuentax: cuentas){
            if(cuentax.getTipo_moneda() == Cuenta.CurrencyType.PESO_AR)
                pesoAR = true;
            if(cuentax.getTipo_moneda() == Cuenta.CurrencyType.DOLAR)
                dolar = true;
            if(cuentax.getTipo_moneda() == Cuenta.CurrencyType.EURO)
                euro = true;
        } //Checking accounts

        if(cuenta.getTipo_moneda() == null)
            return new ResponseEntity<>(new ResponseError(400, "Se debe especificar tipo de cuenta (PESO_AR|EURO|DOLAR)"), HttpStatus.BAD_REQUEST);

        if(cuenta.getTipo_moneda() == Cuenta.CurrencyType.PESO_AR & pesoAR)
            return new ResponseEntity<>(new ResponseError(400, "Cuenta del tipo especificado ya creada (PESO_AR|EURO|DOLAR)"), HttpStatus.BAD_REQUEST);

        if(cuenta.getTipo_moneda() == Cuenta.CurrencyType.DOLAR & dolar)
            return new ResponseEntity<>(new ResponseError(400, "Cuenta del tipo especificado ya creada (PESO_AR|EURO|DOLAR)"), HttpStatus.BAD_REQUEST);

        if(cuenta.getTipo_moneda() == Cuenta.CurrencyType.EURO & euro)
            return new ResponseEntity<>(new ResponseError(400, "Cuenta del tipo especificado ya creada (PESO_AR|EURO|DOLAR)"), HttpStatus.BAD_REQUEST);

        cuenta.setSaldo(0.0);
        cuenta.setStatus(Cuenta.Status.ACTIVO);

        return cuentasService.saveCuenta(cuenta);


    }

    @PutMapping(value = "/cuentas/{id}") //TODO: ERROR WITH PATCH
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Object updateState(@RequestBody Cuenta updatedCuenta,@PathVariable Long id){

        Map<String, Object> mapResponse = new HashMap<>();

        Cuenta result = cuentasService.updateCuenta(updatedCuenta,id);

        if ( result == null)
            return new ResponseEntity<>(new ResponseError(404, String.format("Cuenta con id %d not found o saldo negativo", id)), HttpStatus.NOT_FOUND);
        if(updatedCuenta.getSaldo()<0)
            return new ResponseEntity<>(new ResponseError(400, String.format("Cuenta con id %d con Saldo negativo", id)), HttpStatus.BAD_REQUEST);

        mapResponse.put("id",result.getId());
        mapResponse.put("saldo",result.getSaldo());
        mapResponse.put("estado",result.getStatus());
        return  mapResponse;

    }

    @DeleteMapping(value = "/cuentas/{id}")
    @ResponseBody
    public Object deleteCuenta(@PathVariable("id") Long id) {
        Cuenta cuenta = cuentasService.deleteCuenta(id);
        if ( cuenta == null)
            return new ResponseEntity<>(new ResponseError(404, String.format("Cuenta con id %d no encontrado.", id)), HttpStatus.NOT_FOUND);

        return cuenta;
    }




}

