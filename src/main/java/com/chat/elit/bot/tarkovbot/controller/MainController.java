package com.chat.elit.bot.tarkovbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class MainController {

    //@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private String url = "https://discord.com/api/oauth2/authorize?client_id=742310849024360459&permissions=8&redirect_uri=http://localhost:8080/redirect&scope=bot";
            //"https://discord.com/api/oauth2/authorize?response_type=code&client_id=742310849024360459&scope=bot&state=15773059ghq9183habn&redirect_uri=http://localhost:8080/redirect&prompt=consent";

    @RequestMapping("/StartBot")
    public String initialization(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response;

        try{
            //restTemplate.execute(url, HttpMethod.GET, );
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            System.out.println(response.getStatusCode());
            System.out.println(response);
        } catch (RestClientException e) {
            e.printStackTrace();
            return "Request failed :(";
        }

        return response.getBody();
    }

    @RequestMapping(value = "/redirect", params = {"code", "state"}, method = GET)
    public String index(@RequestParam("code") String code, @RequestParam("state") String state){
        System.out.println("Magic box is working! : " + code + ", " + state);
        return "Welcome, dude :). Here is your damn codes: " + code + ", " + state;
    }
}
