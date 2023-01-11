package de.hsrm.mi.swt_project.demo.api.editor;
/**
 * DTO that server receives from client whenever a specific script should be loaded for a npc.
 * Includes script and the an id which identifies the npc the script is for.
 * @author Marie Bohnert, Tom Gouthier
 */
public record GetScriptDTO(String script, int npcId) {
    
}
