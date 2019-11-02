package pl.akolata.utils

import com.fasterxml.jackson.databind.ObjectMapper

class SpecificationHelper {

    static asJsonString(Object o) {
        return new ObjectMapper().writeValueAsString(o)
    }
}
