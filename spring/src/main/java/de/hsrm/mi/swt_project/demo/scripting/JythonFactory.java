package de.hsrm.mi.swt_project.demo.scripting;

import org.python.util.PythonInterpreter;

public class JythonFactory {
    /**
     * Private default constructor to avoid instanciation.
     */
    private JythonFactory() {
        // do nothing
    }
    
    public static PythonInterpreter getInterpreter() {
        return new PythonInterpreter();
    } 

}
