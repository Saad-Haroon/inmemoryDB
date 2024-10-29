package com.systemdesign.inmemorydb;

import com.systemdesign.inmemorydb.dao.NodeRepository;
import com.systemdesign.inmemorydb.dao.entity.Node;
import com.systemdesign.inmemorydb.service.ConsistentHashingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InmemorydbApplication {

	public static void main(String[] args) {
		SpringApplication.run(InmemorydbApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(ConsistentHashingService consistentHashingService, NodeRepository nodeRepository) {
		return args -> {
			String initialNodeId = "System Design";
			Node initialNode = new Node(initialNodeId);
			consistentHashingService.addNode(initialNode);
			nodeRepository.addNode(initialNode);

			String sampleKey = "systemDesign";
			String sampleValue = "in memory DB";
			consistentHashingService.putData(sampleKey, sampleValue);
		};
	}
}
