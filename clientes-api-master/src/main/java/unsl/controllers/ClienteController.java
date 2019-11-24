package unsl.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Object createClient(@RequestBody Cliente cliente) {
        Map<String, Object> mapResponse = new HashMap<>();


        cliente.setEstado(Cliente.Status.ACTIVO); //Client status at creation "ACTIVO"
        mapResponse.put("id",clienteService.saveClient(cliente).getId());
        return  mapResponse;

    }

    @PutMapping(value = "/clientes/{id}")
    @ResponseBody
    public Object updateClient(@RequestBody Cliente cliente, @PathVariable Long id) {

        Map<String, Object> mapResponse = new HashMap<>();

        cliente.setId(id);

        Cliente res = clienteService.updateClient(cliente);



        if ( res == null) {
            return new ResponseEntity<>(new ResponseError(404, String.format("Cliente with ID %d not found", cliente.getId())), HttpStatus.NOT_FOUND);
        }
        mapResponse.put("nombre",res.getNombre());
        mapResponse.put("apellido",res.getApellido());
        mapResponse.put("estado", res.getEstado());

        return mapResponse;
    }

    @DeleteMapping(value = "/clientes/{id}")
    @ResponseBody
    public Object deleteClient(@PathVariable Long id) {
        Map<String, Object> mapResponse = new HashMap<>();

        Cliente res = clienteService.deleteClient(id);
        if ( res == null) {
            return new ResponseEntity<>(new ResponseError(404, String.format("Cliente with ID %d not found", id)), HttpStatus.NOT_FOUND);
        }

        mapResponse.put("estado",res.getEstado());
        return mapResponse;
    }

}

