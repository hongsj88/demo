package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class NotSynchronizedConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;


    @Bean
    public Job batchJob() throws Exception {
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("slaveStep")
                .<Customer, Customer>chunk(100)
                .reader(customerItemReader())
                .writer(customItemWriter())
                .taskExecutor(taskExecutor())
                .build();

    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor =new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("not-safety-thread-");

        return executor;

    }


    @Bean
    @StepScope
    public JdbcCursorItemReader<Customer> customerItemReader() {

        return new JdbcCursorItemReaderBuilder<Customer>()
                .fetchSize(60)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Customer.class))
                .sql("select id, firstName,lastName , birthdate from customer")
                .name("NotSafetyReader")
                .build();
    }






    @Bean
    @StepScope
    public JdbcBatchItemWriter customItemWriter() {
        JdbcBatchItemWriter<Customer> itemWriter = new JdbcBatchItemWriter<>();

        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("insert into customer2 values(:id,:firstName,:lastName,:birthdate)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();

        return itemWriter;

    }

}