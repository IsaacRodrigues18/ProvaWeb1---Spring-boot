package ifs.com.rssapi.Dto;

import ifs.com.rssapi.Enums.RoleEnum;

import java.time.LocalDate;

public record UsuarioDto(String nome,
                         String login,
                         String senha,
                         String email,
                         LocalDate dataNascimento,
                         boolean status,
                         RoleEnum role) {
}
