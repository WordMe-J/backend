package kr.wordme.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private UserCardSetProcessor processor;

    @Autowired
    private CardSetWriter writer;

    @Bean
    public Job sendCardSetJob() {
        return jobBuilderFactory.get("sendCardSetJob")
                .start(sendCardSetStep())
                .build();
    }

    @Bean
    public Step sendCardSetStep() {
        return stepBuilderFactory.get("sendCardSetStep")
                .<User, List<CardSet>>chunk(10)
                .reader(userReader())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<User> userReader() {
        // 구독한 사용자 데이터를 가져옵니다.
        return new JpaPagingItemReaderBuilder<User>()
                .name("userReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT u FROM User u")
                .build();
    }
}
