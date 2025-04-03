package guru.springframework.spring_ai_intro.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring_ai_intro.model.*;
import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OpenAIServicesImpl implements OpenAIService {

    private final ChatModel chatModel;

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info-prompt.st")
    private Resource getCapitalWithInfoPrompt;

    @Autowired
    ObjectMapper objectMapper;

    public OpenAIServicesImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest) {
        System.out.println("getCapital was called!");

        BeanOutputConverter<GetCapitalResponse> converter = new BeanOutputConverter<>(GetCapitalResponse.class);
        String format = converter.getFormat();
        System.out.println("Format:\n" + format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));

        System.out.println(chatModel.getDefaultOptions().getModel());
        System.out.println(chatModel.getDefaultOptions().getTemperature());
        System.out.println(chatModel.getDefaultOptions().getMaxTokens());
        System.out.println(chatModel.getDefaultOptions().getFrequencyPenalty());
        System.out.println(chatModel.getDefaultOptions().getPresencePenalty());
        System.out.println(chatModel.getDefaultOptions().getStopSequences());
        System.out.println(chatModel.getDefaultOptions().getFrequencyPenalty());
        System.out.println(chatModel.getDefaultOptions().getTopK());
        System.out.println(chatModel.getDefaultOptions().getTopP());

        ChatResponse response = chatModel.call(prompt);
        System.out.println("Response\n" + response.getResult().getOutput().getText());
        return converter.convert(response.getResult().getOutput().getText());
    }

    @Override
    public GetCapitalDetailedResponse getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
        System.out.println("getCapitalWithInfo was called!");
        BeanOutputConverter<GetCapitalDetailedResponse> converter =
                new BeanOutputConverter<>(GetCapitalDetailedResponse.class);
        String format = converter.getFormat();
        System.out.println("Format:\n" + format);
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(),
                "format", format));
        ChatResponse response = chatModel.call(prompt);
        System.out.println("Response:\n" + response.getResult().getOutput().getText());
        return converter.convert(response.getResult().getOutput().getText());
    }

    @Override
    public Answer getAnswer(Question question) {
        System.out.println("getAnswer was called!");
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getText());
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getText();
    }
}
