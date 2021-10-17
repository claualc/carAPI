package com.carapi.domain.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.carapi.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.ui.ModelMap;

import lombok.Data;

@Entity
@Data
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String nome;
    private String login;

    private String token;

    // public static UserDTO create(User user, String token) {
    //     ModelMap modelMapper = new ModelMap();
    //     UserDTO dto = modelMapper.map(user, UserDTO.class);
    //     dto.token = token;
    //     return dto;
    // }

    public String toJSON() throws JsonProcessingException {
        ObjectMapper m = new ObjectMapper();
        return m.writeValueAsString(this);
    }
}
    