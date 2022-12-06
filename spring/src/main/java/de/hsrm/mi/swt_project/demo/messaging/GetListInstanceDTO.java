package de.hsrm.mi.swt_project.demo.messaging;

import java.util.LinkedList;
import java.util.List;

import de.hsrm.mi.swt_project.demo.instancehandling.Instance;

/**
 * @param instancelist = List from all GetInstanceInfoDTO
 * @author Astrid Klemmer, Finn Schindel
 */
public record GetListInstanceDTO(List<GetInstanceInfoDTO> instancelist) {
    
    /**
     * create a list of GetInstanceInfoDTO
     * @param instances = List of all Instance
     * @return a list of GetInstanceInfoDTO
     */
    public static GetListInstanceDTO from(List<Instance> instances) {

        List<GetInstanceInfoDTO> dtoList = new LinkedList<>();

        for (Instance instance : instances) {
            dtoList.add(GetInstanceInfoDTO.from(instance));
        }
        return new GetListInstanceDTO(dtoList);
    }
}
