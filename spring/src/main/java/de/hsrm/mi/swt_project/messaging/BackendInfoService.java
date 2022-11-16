package de.hsrm.mi.swt_project.messaging;

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
     * @param FEHLTNOCHDTO = Obeject has to be converted
     */
    //public void convertMap(String topicname, BackendOperation operation, String FEHLTNOCHDTO);
}
