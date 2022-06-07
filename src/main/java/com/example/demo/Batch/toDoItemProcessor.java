package com.example.demo.Batch;

import com.example.demo.model.Todo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class toDoItemProcessor {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public FlatFileItemReader<Todo> reader() {
        FlatFileItemReader<Todo> reader = new FlatFileItemReader<>();
        reader.setResource(new PathResource("C:\\work\\demo\\ToDoList_Read.csv"));

        DefaultLineMapper<Todo> mapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] { "name", "deadline","status"} );

        BeanWrapperFieldSetMapper beanwrapper = new BeanWrapperFieldSetMapper();
        beanwrapper.setTargetType(Todo.class);

        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(beanwrapper);

        reader.setLineMapper(mapper);
        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<Todo> writer() {
        JdbcBatchItemWriter<Todo> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO toDoList (name, deadline, status) " +
                "VALUES (:name, :deadline,:status)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Todo, Todo>chunk(5)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public Job thisJob() {
        return jobBuilderFactory
                .get("thisJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();

    }

}
