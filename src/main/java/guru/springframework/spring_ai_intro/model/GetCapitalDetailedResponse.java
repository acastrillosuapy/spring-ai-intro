package guru.springframework.spring_ai_intro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalDetailedResponse(@JsonPropertyDescription("This is the city name") String city,
                                         @JsonPropertyDescription("This is population") Integer population,
                                         @JsonPropertyDescription("This is the region") String region,
                                         @JsonPropertyDescription("This is the language") String language,
                                         @JsonPropertyDescription("This is the currency") String currency) {
}
