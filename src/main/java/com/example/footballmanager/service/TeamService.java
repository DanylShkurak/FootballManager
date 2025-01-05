package com.example.footballmanager.service;

import com.example.footballmanager.entity.Team;

import java.util.List;

public interface TeamService {
    List<Team> findAll();

    Team findById(Integer id);

    Team save(Team team);

    Team update(int id, Team updatedTeam);

    void deleteById(Integer id);
}
