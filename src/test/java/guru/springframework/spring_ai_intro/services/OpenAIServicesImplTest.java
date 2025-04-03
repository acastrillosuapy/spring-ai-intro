package guru.springframework.spring_ai_intro.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OpenAIServicesImplTest {

    @Autowired
    OpenAIService openAIService;

    @Test
    void getAnswer() {

        String question = "Give me 10 sentences that end with the word apple";
        String answer = openAIService.getAnswer(question);
        System.out.println(question);
        System.out.println();
        System.out.println("Got the answer:");
        System.out.println();
        System.out.println(answer);
    }
}