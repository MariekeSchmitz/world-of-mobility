package de.hsrm.mi.swt_project.demo.util;

import java.io.IOException;
import java.io.InputStream;

import org.python.util.PythonInterpreter;
import org.springframework.core.io.ClassPathResource;

import de.hsrm.mi.swt_project.demo.controls.Direction;

public class JythonFactory {

    public static final String API_PATH = "npcApi.py";
    
    public static PythonInterpreter getInterpreter(){
        try (InputStream resource = new ClassPathResource(API_PATH).getInputStream()) {
            PythonInterpreter pythonInterpreter = new PythonInterpreter();
            pythonInterpreter.set("Direction", Direction.class);
            pythonInterpreter.execfile(resource);
            return pythonInterpreter;
        } catch (IOException e) {
            return new PythonInterpreter();
        }
    } 

}
