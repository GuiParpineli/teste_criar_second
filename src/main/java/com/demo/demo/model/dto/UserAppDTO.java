package com.demo.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserAppDTO(Integer id, String name, String lastname, String jwt) {
}
