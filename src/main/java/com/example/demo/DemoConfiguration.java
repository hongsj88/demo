package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.LimitCheckingItemSkipPolicy;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DemoConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job batchJob() throws Exception {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }


    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(5)
                .reader(new ItemReader<String>() {
                    int i=0;
                    @Override
                    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        i++;
                        if (i == 3){
                            throw new SkippableException("this exception is skipped");
                        }
                        System.out.println("ItemReader : " + i);
                        return i > 20 ? null : String.valueOf(i);
                    }
                })
                .processor(itemProcess())
                .writer(itemWriter())
                .faultTolerant()
//                .skip(SkippableException.class)
//                .skipLimit(2)
//                .skipPolicy(LimitCheckingItemSkipPolicy()) // 두번째
//                .skipPolicy(new AlwaysSkipItemSkipPolicy()) // 세번째 무조건 실행
                .noSkip(NoSkippableException.class)
                .build();
    }

    private ItemProcessor<? super String, String> itemProcess() {
        return new SkipItemProcessor();
    }

    private ItemWriter<? super String> itemWriter() {
        return new SkipItemWriter();
    }

    @Bean
    public SkipPolicy LimitCheckingItemSkipPolicy(){

        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(SkippableException.class, true);
        LimitCheckingItemSkipPolicy limitCheckingItemSkipPolicy = new LimitCheckingItemSkipPolicy(4, exceptionClass);
        return limitCheckingItemSkipPolicy;
    }

}
