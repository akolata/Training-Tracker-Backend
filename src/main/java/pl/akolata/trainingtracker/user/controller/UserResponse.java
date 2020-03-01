package pl.akolata.trainingtracker.user.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.user.dto.UserDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UserDto user;
}
