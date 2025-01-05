package com.example.footballmanager.controller;

import com.example.footballmanager.entity.Team;
import com.example.footballmanager.service.impl.TeamServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamServiceImpl teamService;

    public TeamController(TeamServiceImpl teamService) {
        this.teamService = teamService;
    }


    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return new ResponseEntity<>(teamService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        return new ResponseEntity<>(teamService.save(team), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable int id, @RequestBody Team updatedTeam) {
        return new ResponseEntity<>(teamService.update(id, updatedTeam), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Integer id) {
        return new ResponseEntity<>(teamService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) {
        teamService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

