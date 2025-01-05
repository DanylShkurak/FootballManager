package com.example.footballmanager.entity.mapper;


import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.dto.PlayerDto;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDto toPlayerDto(Player player) {
        if (player == null) {
            return null;
        }

        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setName(player.getName());
        playerDto.setAge(player.getAge());
        playerDto.setExperience(player.getExperienceMonths());
        playerDto.setTeam(player.getTeam());

        return playerDto;
    }

    public Player toPlayerEntity(PlayerDto playerDto) {
        if (playerDto == null) {
            return null;
        }

        Player player = new Player();
        player.setId(playerDto.getId() == null ? 0 : playerDto.getId());
        player.setName(playerDto.getName());
        player.setAge(playerDto.getAge());
        player.setExperienceMonths(playerDto.getExperience());
        player.setTeam(playerDto.getTeam());

        return player;
    }
}
