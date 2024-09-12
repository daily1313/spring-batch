package com.example.springbatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job simpleJob() {
        return new JobBuilder("simpleJob", jobRepository)
                .start(simpleFirstStep())
                .next(simpleSecondStep())
                .next(simpleThirdStep())
                .build();
    }

    @Bean
    public Step simpleFirstStep() {
        return new StepBuilder("simpleFirstStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is First Step");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step simpleSecondStep() {
        return new StepBuilder("simpleSecondStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Second Step");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public Step simpleThirdStep() {
        return new StepBuilder("simpleThirdStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> This is Third Step");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}

