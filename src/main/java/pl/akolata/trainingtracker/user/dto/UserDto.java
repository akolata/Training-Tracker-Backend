package pl.akolata.trainingtracker.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
}
