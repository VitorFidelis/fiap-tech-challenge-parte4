package br.feedback.service;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SqsService {
    
    private final SqsClient sqsClient;
    private final String queueUrl = System.getenv("SQS_QUEUE_URL");
    
    public SqsService() {
        this.sqsClient = SqsClient.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
    }
    
    public void sendMessage(String message) {
        if (queueUrl == null || queueUrl.isBlank()) {
            throw new IllegalStateException("SQS_QUEUE_URL não configurada");
        }
        
        SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody(message)
            .build();
            
        sqsClient.sendMessage(sendMsgRequest);
    }
}
