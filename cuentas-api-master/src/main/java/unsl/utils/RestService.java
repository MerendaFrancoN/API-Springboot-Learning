package unsl.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import unsl.entities.Cliente;

@Service
public class RestService {

    public Cliente getCliente(String url) throws Exception{
        RestTemplate restTemplate = new RestTemplate();

        Cliente cliente;

        try{
            cliente = restTemplate.getForObject(url, Cliente.class);

        }catch (Exception e){
            throw new Exception(buildMessageError(e));
        }

        return cliente;

    }

    private String buildMessageError(Exception e) {
        String msg = e.getMessage();
        if (e instanceof HttpClientErrorException) {
            msg = ((HttpClientErrorException) e).getResponseBodyAsString();
        } else if (e instanceof HttpServerErrorException) {
            msg =  ((HttpServerErrorException) e).getResponseBodyAsString();
        }
        return msg;
    }

}