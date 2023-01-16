package de.hsrm.mi.swt_project.demo.util;

import java.io.IOException;
import java.io.InputStream;

import org.python.util.PythonInterpreter;
import org.springframework.core.io.ClassPathResource;

import de.hsrm.mi.swt_project.demo.controls.Direction;

public class JythonFactory {

    public static final String API_PATH = "npcApi.py";

    protected static final PythonInterpreter INTERPRETER = new PythonInterpreter();
    protected static boolean initialized = false;

    /**
     * Private default constructor to avoid instanciation.
     */
    private JythonFactory() {
        // empty
    }
    
    public static PythonInterpreter getInterpreter(){

        if (!initialized) {
            init();
        }

        return INTERPRETER;
    }

    /**
     * Initialized interpreter by setting global context and
     * executing the api script.
     */
    protected static void init() {

        INTERPRETER.set("Direction", Direction.class);

        try (InputStream resource = new ClassPathResource(API_PATH).getInputStream()) {
            INTERPRETER.execfile(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialized = true;
    }

}
