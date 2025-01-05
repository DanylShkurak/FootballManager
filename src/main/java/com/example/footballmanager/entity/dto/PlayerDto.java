package com.example.footballmanager.entity.dto;

import com.example.footballmanager.entity.Team;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDto {
    private Integer id;
    private String name;
    private int age;
    private int experience;
    private Team team;
}
