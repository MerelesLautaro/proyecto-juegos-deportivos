package com.lautadev.juegos_deportivos.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username","message","JWT","status"})
public record AuthLoginResponseDTO(String username,
                                   String message,
                                   String JWT,
                                   boolean status) {
}
