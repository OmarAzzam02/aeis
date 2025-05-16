package org.aeis.aiabstractionlayer.config;

import org.aeis.aiabstractionlayer.dto.DeviceStatusDTO;
import org.aeis.aiabstractionlayer.payload.*;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class AiKafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Producer
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // Consumer
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ai-abstraction-layer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeviceStatusDTO> deviceStatusDTOConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DeviceStatusDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        JsonDeserializer<DeviceStatusDTO> deserializer = new JsonDeserializer<>(DeviceStatusDTO.class);
        deserializer.addTrustedPackages("*");
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                  deserializer
        ));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SttTranslationsPayload> sttTranslationsContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SttTranslationsPayload> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        JsonDeserializer<SttTranslationsPayload> deserializer = new JsonDeserializer<>(SttTranslationsPayload.class);
        deserializer.addTrustedPackages("*");
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                deserializer
        ));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LectureSummaryResponsePayload> lectureSummariesContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LectureSummaryResponsePayload> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        JsonDeserializer<LectureSummaryResponsePayload> deserializer = new JsonDeserializer<>(LectureSummaryResponsePayload.class);
        deserializer.addTrustedPackages("*");
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                deserializer
        ));
        return factory;
    }

    // Optionally create topics programmatically
    @Bean
    public NewTopic recordControlTopic() {
        return TopicBuilder.name("record_control").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic lectureSummaryRequestsTopic() {
        return TopicBuilder.name("lecture_summary_requests").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic lectureSummaryResponsesTopic() {
        return TopicBuilder.name("lecture_summary_responses").partitions(1).replicas(1).build();
    }

    // camera_frames, raw_audio, tts_audio, stt_translations can also be declared if you want
}
