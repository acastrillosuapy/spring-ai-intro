package guru.springframework.spring_ai_intro.services;

import guru.springframework.spring_ai_intro.model.*;

public interface OpenAIService {

    String getAnswer(String question);

    Answer getAnswer(Question question);

    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);

    GetCapitalDetailedResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
}
