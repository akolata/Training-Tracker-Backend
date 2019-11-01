package pl.akolata.trainingtracker.user.dto;

import org.mapstruct.Mapper;
import pl.akolata.trainingtracker.core.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);
}
