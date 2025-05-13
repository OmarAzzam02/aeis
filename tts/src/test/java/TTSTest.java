
import org.aeis.tts.controller.TTSController;
import org.aeis.tts.service.TTSMessageProcessor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TTSController.class)
class TTSTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TTSMessageProcessor ttsMessageProcessor;

    @Test
    void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    void testGetAudioFileReturnsAudioBytes() throws Exception {
        byte[] fakeAudio = new byte[] {1, 2, 3, 4, 5};
        MockMultipartFile mockFile = new MockMultipartFile("audio", "audio.wav", "audio/wav", fakeAudio);
        Mockito.when(ttsMessageProcessor.getAudio()).thenReturn(mockFile);

        mockMvc.perform(post("/audio"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(fakeAudio));
    }
}
