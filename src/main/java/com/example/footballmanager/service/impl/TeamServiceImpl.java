package com.example.footballmanager.service.impl;


import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repository.TeamRepository;
import com.example.footballmanager.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Integer id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public Team update(int id, Team updatedTeam) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));

        existingTeam.setBalance(updatedTeam.getBalance());

        return teamRepository.save(existingTeam);
    }

    public void deleteById(Integer id) {
        teamRepository.deleteById(id);
    }
}
