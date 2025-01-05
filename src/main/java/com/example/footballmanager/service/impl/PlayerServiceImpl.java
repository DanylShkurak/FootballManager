package com.example.footballmanager.service.impl;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.entity.dto.PlayerDto;
import com.example.footballmanager.entity.mapper.PlayerMapper;
import com.example.footballmanager.repository.PlayerRepository;
import com.example.footballmanager.repository.TeamRepository;
import com.example.footballmanager.service.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PlayerMapper playerMapper;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public List<PlayerDto> findAll() {
        return playerRepository.findAll()
                .stream()
                .map(playerMapper::toPlayerDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlayerDto findById(Integer id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with id: " + id));
        return playerMapper.toPlayerDto(player);
    }

    @Override
    public PlayerDto save(PlayerDto player) {
        return playerMapper.toPlayerDto(
                playerRepository.save(
                        playerMapper.toPlayerEntity(player)));
    }

    @Override
    public PlayerDto update(int id, PlayerDto updatedPlayer) {
        PlayerDto existingPlayer = playerMapper.toPlayerDto(playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found with id: " + id)));

        existingPlayer.setAge(updatedPlayer.getAge());
        System.out.println(existingPlayer);

        return playerMapper.toPlayerDto(playerRepository.save(playerMapper.toPlayerEntity(existingPlayer)));
    }

    @Override
    public void deleteById(Integer id) {
        playerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public String transferPlayer(int playerId, int sellingTeamId, int buyingTeamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        Team sellingTeam = teamRepository.findById(sellingTeamId)
                .orElseThrow(() -> new IllegalArgumentException("Selling team not found"));

        Team buyingTeam = teamRepository.findById(buyingTeamId)
                .orElseThrow(() -> new IllegalArgumentException("Buying team not found"));

        validatePlayer(player, sellingTeam);

        double transferFee = calculateTransferFee(player);
        double commission = transferFee * (sellingTeam.getCommissionRate() / 100);
        double totalPayment = transferFee + commission;

        validateBalances(buyingTeam, totalPayment);
        updateTeamBalances(sellingTeam, buyingTeam, totalPayment);

        player.setTeam(buyingTeam);

        teamRepository.save(sellingTeam);
        teamRepository.save(buyingTeam);
        playerRepository.save(player);

        return "Transfer successful!";
    }

    private void validatePlayer(Player player, Team sellingTeam) {
        if (player.getAge() < 18) {
            throw new IllegalArgumentException("Player's age must be at least 18 years.");
        }
        if (player.getExperienceMonths() <= 0) {
            throw new IllegalArgumentException("Player must have positive experience.");
        }
        if (!sellingTeam.getPlayers().contains(player)) {
            throw new IllegalArgumentException("Selling team does not own the player.");
        }
    }

    private double calculateTransferFee(Player player) {
        return (player.getExperienceMonths() * 100000) / (player.getAge() / 12.0);
    }

    private void validateBalances(Team buyingTeam, double totalPayment) {
        if (buyingTeam.getBalance().compareTo(BigDecimal.valueOf(totalPayment)) < 0) {
            throw new IllegalArgumentException("Buying team does not have enough balance.");
        }
    }

    private void updateTeamBalances(Team sellingTeam, Team buyingTeam, double totalPayment) {
        BigDecimal buyingTeamBalance = buyingTeam.getBalance();
        BigDecimal sellingTeamBalance = sellingTeam.getBalance();

        buyingTeam.setBalance(buyingTeamBalance.subtract(BigDecimal.valueOf(totalPayment)));
        sellingTeam.setBalance(sellingTeamBalance.add(BigDecimal.valueOf(totalPayment)));
    }
}
