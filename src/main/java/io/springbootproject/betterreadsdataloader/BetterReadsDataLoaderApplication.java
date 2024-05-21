package io.springbootproject.betterreadsdataloader;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import connection.DataStaxAstraProperties;
import com.author.Author;
import com.author.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties (DataStaxAstraProperties.class)
public class BetterReadsDataLoaderApplication {

	@Autowired 
	AuthorRepository authorRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BetterReadsDataLoaderApplication.class, args);
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties){
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder->builder.withCloudSecureConnectBundle(bundle);
	}

	@PostConstruct
	public void start(){
		Author author = new Author();
		author.setId("RandomId");
		author.setName("RandomName");
		author.setPersonalName("RandomPersonalName");
		authorRepository.save(author);
	}
}