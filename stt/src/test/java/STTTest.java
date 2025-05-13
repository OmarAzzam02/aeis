


import org.aeis.stt.service.STTProcessorService;
import org.aeis.stt.service.WebSocketSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class STTTest {

    @Mock
    private WebSocketSender webSocketSender;




    @InjectMocks
    private STTProcessorService sttProcessorService;

    @Test
    void sttOutputListener_shouldSendMessage_whenMessageReceived() {

        String testMessage = "This is a test STT message.";


        sttProcessorService.SttOutputListener(testMessage);

        verify(webSocketSender, times(1)).send(testMessage);

        // Optionally, if you wanted to verify logging (requires more setup for SLF4J loggers or using a test appender):
        // verify(logger).info("Received message: {}", testMessage);
    }

    @Test
    void sttOutputListener_shouldHandleEmptyMessage() {

        String emptyMessage = "";


        sttProcessorService.SttOutputListener(emptyMessage);


        verify(webSocketSender, times(1)).send(emptyMessage);
    }

    @Test
    void sttOutputListener_shouldHandleNullMessage() {

        String nullMessage = null; // Kafka messages can technically be null


        sttProcessorService.SttOutputListener(nullMessage);


        verify(webSocketSender, times(1)).send(nullMessage);
    }
}
