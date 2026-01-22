package com.parking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    
    @GetMapping("/")
    public String home() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Parking App</title>
                <style>
                    body { font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }
                    h1 { color: #2c3e50; }
                    .status { color: #27ae60; font-weight: bold; }
                    .links { margin-top: 30px; }
                    a { display: inline-block; margin: 10px; padding: 10px 20px; 
                        background-color: #3498db; color: white; text-decoration: none; 
                        border-radius: 5px; }
                    a:hover { background-color: #2980b9; }
                </style>
            </head>
            <body>
                <h1>🚗 Parking Management App</h1>
                <p class="status">✅ Application is running successfully!</p>
                <div class="links">
                    <a href="/health">Health Check</a>
                    <a href="/actuator/health">Detailed Health</a>
                </div>
            </body>
            </html>
            """;
    }
    
    @GetMapping("/health")
    public String health() {
        return "{\"status\": \"UP\", \"service\": \"parking-app\", \"timestamp\": \"" 
               + java.time.LocalDateTime.now() + "\"}";
    }
    
    @GetMapping("/api/status")
    public String apiStatus() {
        return "{\"message\": \"API is operational\", \"version\": \"1.0.0\"}";
    }
}