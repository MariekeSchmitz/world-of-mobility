package de.hsrm.mi.swt_project.demo.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;


/**
 * Implements the BackendInfoService interface
 * @author Finn Schindel, Felix Ruf
 * @version 1.0
 */
public class BackendInfoServiceImpl implements BackendInfoService{

    Logger logger = LoggerFactory.getLogger(BackendInfoServiceImpl.class);

    @Autowired
    private SimpMessagingTemplate messaging;

    @Override
    public void sendInfo(String topicName, BackendOperation operation, String jsonString) {
            logger.info("Send Info: Topicname = {}, Operation = {}", topicName, operation);
            String destName = "/topic/" + topicName;
            messaging.convertAndSend(destName, new BackendInfoMessage(topicName, operation, jsonString));
    }

    @Override
    public void sendInfo(String topicName, BackendOperation operation, SendMoveableUpdateDTO Object) {
        logger.info("Send Info: Topicname = {}, Operation = {}", topicName, operation);
        String destName = "/topic/" + topicName;
        messaging.convertAndSend(destName, Object);    
    }

    @Override
    public void sendInfo(String topicName, BackendOperation operation, SendMapDTO Object) {
        logger.info("Send Info: Topicname = {}, Operation = {}", topicName, operation);
        String destName = "/topic/" + topicName;
        messaging.convertAndSend(destName, Object);    
    }

    @Override
    public void sendInfo(String topicName, BackendOperation operation, SendGameUpdateDTO Object) {
        logger.info("Send Info: Topicname = {}, Operation = {}", topicName, operation);
        String destName = "/topic/" + topicName;
        messaging.convertAndSend(destName, Object);       
    }

    @Override
    public void sendInfo(String topicName, BackendOperation operation, GetMapUpdateDTO Object) {
        logger.info("Send Info: Topicname = {}, Operation = {}", topicName, operation);
        String destName = "/topic/" + topicName;
        messaging.convertAndSend(destName, Object);  
    }

    
}
