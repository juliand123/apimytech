package com.mytech.api.controllers;

import com.mytech.api.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class Preference {

    @Autowired
    private JWTUtil jwtUtil;

    @Value("${security.token.secret}")
    private String tokenMp;
    @Value("${security.api.url}")
    private String apiUrlMp;

    @RequestMapping (value = "api/preference/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPreference(@RequestHeader(value = "Authorization") String token,
                                           @RequestBody com.mytech.api.models.Preference preference
                                           ){
        if (!validateToken(token)){

            return new ResponseEntity<>("Error!, problema de autenticacion", HttpStatus.UNAUTHORIZED);
        }

        try {
            String preferenceId = preference.getPreferenceId();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + tokenMp); //accessToken can be the secret key you generate.
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> entity = new HttpEntity <> (headers);
            ResponseEntity<String> response = restTemplate.exchange(apiUrlMp+preferenceId, HttpMethod.GET, entity, String.class);
            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error!, Please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private boolean validateToken(String token){

        try {
            String userID = jwtUtil.getKey(token);
            return userID != null;
        }
        catch (Exception e)
        {
            return false;
        }


    }

}
