package com.ifi.tp.fight.service;

import com.ifi.tp.fight.bo.Fight;

import java.util.List;

public interface FightService {

    List<Fight> getAllFights();

    List<Fight> getAllFightsForFighter(String name);

}
