package de.hsrm.mi.swt_project.demo.messaging;

/**
 * BackendInfoService Interface
 * @author Finn Schindel, Felix Ruf
 * @version 1.0
 */
public interface BackendInfoService {

    /**
     * send Info (map) to client in a periodicaly Manner 
     * @param topicName = the name where the client subscription 
     * @param operation = the operation for the sending infomation
     * @param jsonString = Converted Object zu JsonString
     */
    public void sendInfo(String topicName, BackendOperation operation, String jsonString);

    /**
     * 
     * @param topicName = the name where the client subscription 
     * @param operation = the operation for the sending infomation
     * @param mapObject = Obeject SendMapDTO has to be converted into a JSON-String
     */
    public void sendInfo(String topicName, BackendOperation operation, SendMapDTO object);

    /**
     * 
     * @param topicName = the name where the client subscription 
     * @param operation = the operation for the sending infomation
     * @param SendGameUpdateDTO = Obeject SendGameUpdateDTO has to be converted into a JSON-String
     */
    public void sendInfo(String topicName, BackendOperation operation, SendGameUpdateDTO object);

    /**
     * 
     * @param topicName = the name where the client subscription 
     * @param operation = the operation for the sending infomation
     * @param SendMoveableUpdateDTO = Obeject SendMoveableUpdateDTO has to be converted into a JSON-String
     */
    public void sendInfo(String topicName, BackendOperation operation, SendMoveableUpdateDTO object);


    /**
     * 
     * @param topicName = the name where the client subscription 
     * @param operation = the operation for the sending infomation
     * @param GetMapUpdateDTO = Object GetMapUpdateDTO has to be converted into a JSON-String
     */
    public void sendInfo(String topicName, BackendOperation operation, GetMapUpdateDTO object);
}
