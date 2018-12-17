package com.ifi.tp.fight.service;

import com.ifi.tp.fight.bo.Fight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FightServiceImpl implements FightService {

    private RestTemplate restTemplate;
    private String fightServiceUrl;

    @Autowired
    public FightServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Fight> getAllFights() {
        var url = fightServiceUrl + "/fights";
        var fights = restTemplate.getForObject(url, Fight[].class);
        return Arrays.asList(fights);
    }

    @Override
    public List<Fight> getAllFightsForFighter(String name) {
        var url = fightServiceUrl + "/fights/{name}";
        var fights = restTemplate.getForObject(url, Fight[].class, name);
        return Arrays.asList(fights);
    }

    @Override
    public List<Fight> getAllFightsBetweenFighter(String name1, String name2){
        var url = fightServiceUrl + "/fights/{name1}/{name2}";
        var fights = restTemplate.getForObject(url, Fight[].class, name1, name2);
        return Arrays.asList(fights);
    }

    @Override
    public Fight setFight(String name1, String name2){
        var url = fightServiceUrl + "/fights/engage/{name1}/{name2}";
        return restTemplate.getForObject(url, Fight.class, name1, name2);
    }

    @Value("${fight.service.url}")
    void setPokemonServiceUrl(String fightServiceUrl) {
        this.fightServiceUrl = fightServiceUrl;
    }

}
