package pl.akolata.trainingtracker.gym.command;

import lombok.Value;
import pl.akolata.trainingtracker.gym.entity.Gym;

import javax.validation.constraints.NotBlank;

@Value
public class CreateGymCommand {

    @NotBlank
    private final String name;

    public Gym toGym() {
        Gym gym = new Gym();
        gym.setName(name);
        return gym;
    }
}
