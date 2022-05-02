package com.mytech.api.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class Preference {


    
    @RequestMapping (value = "api/preference/", method = RequestMethod.POST)
    public String getPreference(@RequestBody com.mytech.api.models.Preference preference){
        String preferenceId = preference.getPreferenceId();
        String uri = "https://api.mercadopago.com/checkout/preferences/"+preferenceId;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String token = "APP_USR-6419339134623158-050215-e449eb0cef8abb9168295d40eda62ce3-1116513682";
        headers.set("Authorization", "Bearer " + token); //accessToken can be the secret key you generate.
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity <> (headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String result = response.getBody();
        return result;
    }

}
