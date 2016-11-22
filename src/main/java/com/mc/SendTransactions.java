package com.mc;

import org.apache.commons.io.FileUtils;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.io.File;
import java.io.IOException;

@EnableBinding(Source.class)
public class SendTransactions {

    private static final int MESSAGE_COUNT = 1;

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "${fixedDelay}", maxMessagesPerPoll = "${messagesPerPoll}"))
    public MessageSource<String> send() throws IOException {
        String message = FileUtils.readFileToString(new File("payload.csv"));
        System.out.println(message);
        return () -> new GenericMessage<>(message);
    }
}
