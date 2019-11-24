package unsl.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import unsl.entities.Cuenta;
import java.util.List;

@Service
public class RestService {

    public List<Cuenta> getCuentas(String url) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Cuenta>> cuentaResponse;
        List<Cuenta> cuentas = null;

        try {
            cuentaResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Cuenta>>() {});


            if (cuentaResponse != null && cuentaResponse.hasBody()) {
                cuentas = cuentaResponse.getBody();
            }
        } catch (Exception e) {
            throw new Exception(buildMessageError(e));
        }
        return cuentas;
    }

    public void logicDeleteCuenta(String url) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.delete(url);
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