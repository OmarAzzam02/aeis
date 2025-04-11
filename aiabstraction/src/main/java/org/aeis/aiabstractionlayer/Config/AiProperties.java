package org.aeis.aiabstractionlayer.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aeis.ai")
@Data
public class AiProperties {
    // Example config fields
    private String defaultSummaryModel = "gpt-3.5-turbo";

    // If you want to store default targetLanguage, etc., do so here.
    private String defaultTranslationTarget = "French";

    // etc.
}
