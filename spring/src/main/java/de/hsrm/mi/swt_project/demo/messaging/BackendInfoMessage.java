package de.hsrm.mi.swt_project.demo.messaging;

public record BackendInfoMessage(String topicName, BackendOperation operation, String jsonString) {
    
}
