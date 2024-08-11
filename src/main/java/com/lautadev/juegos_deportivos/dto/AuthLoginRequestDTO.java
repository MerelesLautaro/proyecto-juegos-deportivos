package com.lautadev.juegos_deportivos.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(@NotBlank String username,
                                  @NotBlank String password) {
}
