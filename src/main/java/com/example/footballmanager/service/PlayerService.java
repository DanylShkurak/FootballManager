package com.example.footballmanager.service;

import com.example.footballmanager.entity.dto.PlayerDto;

import java.util.List;

public interface PlayerService {
    List<PlayerDto> findAll();

    PlayerDto findById(Integer id);

    PlayerDto save(PlayerDto player);

    PlayerDto update(int id, PlayerDto updatedPlayer);

    void deleteById(Integer id);

    String transferPlayer(int playerId, int sellingTeamId, int buyingTeamId);
}
