package pl.akolata.trainingtracker.gym.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.akolata.trainingtracker.gym.model.api.GymResponse;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindGymsResponse {
    private List<GymResponse> gyms = new LinkedList<>();
}
