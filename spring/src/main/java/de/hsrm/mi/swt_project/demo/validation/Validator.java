package de.hsrm.mi.swt_project.demo.validation;

/**
 * Classes implementing this interface are
 * able to perform some sort of validation.
 * 
 * @author Sascha Scheid
 */
public interface Validator {
    
    /**
     * Checks if the validation criteria are met.
     * 
     * @return true if validation criteria are met, else false
     */
    public boolean validate();
    
}
