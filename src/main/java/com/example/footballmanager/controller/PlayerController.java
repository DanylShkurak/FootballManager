package com.example.footballmanager.controller;

import com.example.footballmanager.entity.dto.PlayerDto;
import com.example.footballmanager.service.impl.PlayerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerServiceImpl playerService;

    public PlayerController(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayers() {
        return new ResponseEntity<>(playerService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto player) {
        return new ResponseEntity<>(playerService.save(player), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable int id, @RequestBody PlayerDto updatedPlayer) {
        return new ResponseEntity<>(playerService.update(id, updatedPlayer), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable Integer id) {
        return new ResponseEntity<>(playerService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Integer id) {
        playerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{playerId}/transfer")
    public ResponseEntity<String> transferPlayer(
            @PathVariable int playerId,
            @RequestParam int sellingTeamId,
            @RequestParam int buyingTeamId) {

        String result = playerService.transferPlayer(playerId, sellingTeamId, buyingTeamId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
