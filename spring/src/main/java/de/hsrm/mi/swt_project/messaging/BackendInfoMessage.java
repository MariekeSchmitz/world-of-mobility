package de.hsrm.mi.swt_project.messaging;

public record BackendInfoMessage(String topicName, BackendOperation operation, String jsonString) {
    
}
