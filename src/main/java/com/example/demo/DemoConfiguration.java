package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                    .<String, String>chunk(10)
                    .reader(null)
                    .writer(items-> System.out.println(items))
                    .build();
        }


}
