package dev.pteus.spring_ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build(); // build a default chat client, useful when there is only 1 client configured
    }

    @GetMapping("/chat")
    public String chat(){
        return chatClient.prompt()
                .user("Tell me an interesting fact about java")
                .call()// this a blocking call
                .content(); // just the LLM response
    }
    
    @GetMapping("/stream")
    public Flux<String> stream() {
        return chatClient.prompt()
                .user("I'm visiting Portugal soon, can you give me 10 places I must visit?")
                .stream()// streams the response, ie, non-blocking, starts returning chunks of the response as they arrive
                .content();
    }

    @GetMapping("/joke")
    public ChatResponse joke(){
        return chatClient.prompt()
                .user("Tell me a dad hoke")
                .call()
                .chatResponse(); // returns a lot more information, depends on the model we are using
    }
}
