package unsl.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import unsl.entities.Cuenta;

@Service
public class RestService {

    public Cuenta getCuenta(String url) throws Exception{
        RestTemplate restTemplate = new RestTemplate();

        Cuenta cuenta;

        try{
            cuenta = restTemplate.getForObject(url, Cuenta.class);

        }catch (Exception e){
            throw new Exception(buildMessageError(e));
        }

        return cuenta;

    }

    public void updateCuenta(String url, Cuenta updatedCuenta) throws Exception{
        RestTemplate restTemplate = new RestTemplate();

        try{
            HttpEntity<Cuenta> requestEntity = new HttpEntity<>(updatedCuenta);
                //restTemplate.patchForObject(url,requestEntity,Cuenta.class); //TODO: ERROR WITH PATCH
            restTemplate.put(url,requestEntity);
        }catch (Exception e){
            throw new Exception(buildMessageError(e));
        }

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