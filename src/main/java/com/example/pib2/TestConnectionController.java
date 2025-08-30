package com.example.pib2;

import com.example.pib2.TestConnection;
import com.example.pib2.TestConnectionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TestConnectionController {

    private final TestConnectionRepository repository;

    public TestConnectionController(TestConnectionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/test-connection")
    public List<TestConnection> testConnection() {
        return repository.findAll();
    }
}
