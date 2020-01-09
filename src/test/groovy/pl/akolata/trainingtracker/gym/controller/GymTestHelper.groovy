package pl.akolata.trainingtracker.gym.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.akolata.trainingtracker.gym.model.api.GymResponse

@Component
class GymTestHelper {

    @Autowired
    GymController gymController

    CreateGymRequest validCreateGymRequest() {
        new CreateGymRequest("GYM")
    }

    GymResponse createGym(CreateGymRequest request) {
        def response = gymController.createGym(request)
        (GymResponse) response.body
    }
}
