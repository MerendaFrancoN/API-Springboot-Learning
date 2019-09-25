package unsl.controllers;
import java.util.List;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.sun.tracing.dtrace.Attributes;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unsl.entities.Cliente;
import unsl.entities.ResponseError;
import unsl.services.ClienteService;

import javax.annotation.PostConstruct;

@RestController
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping(value = "/clientes")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> getAll() {
       return clienteService.getAll();
    }

    @GetMapping(value = "/clientes/{clienteId}")
    @ResponseBody
    public Object getCliente(@PathVariable("clienteId") Long clienteId) {
        Cliente cliente = clienteService.getCliente(clienteId);
        if ( cliente == null) {
            return new ResponseEntity<>(new ResponseError(404, String.format("Cliente %d not found", clienteId)), HttpStatus.NOT_FOUND);
        }
        return cliente;
    }

    @GetMapping(value = "/clientes/busqueda")
    @ResponseBody
    public Object searchClient(@RequestParam("dni") Long dni) {
        Cliente cliente = clienteService.findByDni(dni);
        if ( cliente == null)
            return new ResponseEntity<>(new ResponseError(404, String.format("Cliente with dni %d not found", dni)), HttpStatus.NOT_FOUND);
        return cliente;
    }

    @PostMapping(value = "/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Long createClient(@RequestBody Cliente cliente) {
        cliente.setEstado(Cliente.Status.ACTIVO); //Client status at creation "ACTIVO"
        return clienteService.saveClient(cliente).getId();
        //"{ \"id\" : "+cliente.getId()+" }"; //TODO: Do it BETTER, CONTROLS.

    }

    @PutMapping(value = "/clientes/{id}")
    @ResponseBody
    public Object updateClient(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente res = clienteService.updateClient(cliente);
        if ( res == null) {
            return new ResponseEntity<>(new ResponseError(404, String.format("Cliente with ID %d not found", cliente.getId())), HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @DeleteMapping(value = "/clientes/{id}")
    @ResponseBody
    public Object deleteClient(@PathVariable Long id) {
        Cliente res = clienteService.deleteClient(id);
        if ( res == null) {
            return new ResponseEntity<>(new ResponseError(404, String.format("Cliente with ID %d not found", id)), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(null,HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/clientes/{id}")
    @ResponseBody
    public Object deleteLogicClient(@RequestBody Cliente cliente, @PathVariable Long id){
        Cliente res = clienteService.getCliente(id);

        if(cliente.getEstado()!=null) //TODO: Better?
            res.setEstado(cliente.getEstado());

        return clienteService.saveClient(res);
    }

}

