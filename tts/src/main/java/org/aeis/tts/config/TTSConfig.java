package org.aeis.tts.config;

import org.aeis.tts.dto.AudioRequestDTO;
import org.aeis.tts.dto.AudioResponseDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


@Configuration
@EnableKafka
public class TTSConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String kafkaServerHost;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;



    @Bean
    public Queue<AudioRequestDTO> queue() {
        return new ConcurrentLinkedQueue<>();
    }


    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerHost);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        return props;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AudioRequestDTO> audioDTOConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AudioRequestDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        JsonDeserializer<AudioRequestDTO> deserializer = new JsonDeserializer<>(AudioRequestDTO.class);
        deserializer.addTrustedPackages("*");

        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(
                consumerConfig(),
                new StringDeserializer(),
                deserializer
        ));

        return factory;
    }




}
