package de.hsrm.mi.swt_project.demo.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class validates the scripts for Scriptable
 * objects.
 * 
 * As the scripts are provided by users it is 
 * neccessary to be restrictive regarding its
 * content.
 * 
 * Scripts containing unallowed imports, 
 * forbidden methods or unknown API calls will
 * be rejected.
 * 
 * @author Sascha Scheid
 */
public class ScriptValidator implements Validator {

    protected Set<String> allowedImports = new HashSet<>();
    protected Set<String> apiMethods = new HashSet<>();
    protected Set<String> forbiddenMethods = new HashSet<>();

    protected String script = "";

    public ScriptValidator(String script) {

        this.allowedImports.add("random");

        this.apiMethods.add("start");
        this.apiMethods.add("stop");
        this.apiMethods.add("getCurrentTile");
        this.apiMethods.add("getFrontTile");
        this.apiMethods.add("getCurrentVelocity");
        this.apiMethods.add("getOrientation");
        this.apiMethods.add("isMotorizedObject");
        this.apiMethods.add("checkMotorizedObject");
        this.apiMethods.add("checkPassenger");
        this.apiMethods.add("isTrafficLightGreen");
        this.apiMethods.add("turnLeft");
        this.apiMethods.add("turnRight");
        this.apiMethods.add("accelerate");
        this.apiMethods.add("brake");
        this.apiMethods.add("surroundingTiles");
        this.apiMethods.add("nearbyRoadUsers");
        this.apiMethods.add("distanceTo");

        this.forbiddenMethods.add("eval");
        this.forbiddenMethods.add("exec");

        this.script = script;
    }

    @Override
    public boolean validate() {
        return this.script != null
            && !this.script.isEmpty()
            && !scriptContainsForbiddenImports() 
            && !scriptContainsForbiddenFunctions()
            && !scriptUsesNonApiCalls();
    }

    /**
     * Checks if the script contains methods that are
     * not allowed to use.
     * 
     * This is done by checking if the script contains
     * any blacklisted function followed by optional
     * whitespace and a "(".
     * 
     * Ending function or variable names with the name
     * of forbidden functions will cause this test to
     * fail.
     * 
     * @return true if forbidden methods were found, else false
     */
    protected boolean scriptContainsForbiddenFunctions() {
    
        List<String> regexes = new ArrayList<>();

        for (int i = 0; i < this.forbiddenMethods.size(); i++) {
            regexes.add("%s\\s*\\(.*\\)");
        }

        String expression = String.join("|", regexes);
        expression = String.format(expression, this.forbiddenMethods.toArray());

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(this.script);

        return matcher.find();
    } 

    /**
     * Checks if the script contains imports that are
     * not allowed to use.
     * 
     * This is done by checking if import statements
     * are present that try to load modules that are
     * not whitelisted.
     * 
     * Using "from module import function" syntax will
     * always causes this check to fail.
     * 
     * Ending function or variable names with "function"
     * will cause this check to fail.
     * 
     * @return true if forbidden imports were found, else false
     */
    protected boolean scriptContainsForbiddenImports() {

        List<String> regexes = new ArrayList<>();
        regexes.add(".*from\\s+.*import");

        for (int i = 0; i < this.allowedImports.size(); i++) {
            regexes.add(".*import\\s+(?!%s).*");
        }

        String expression = String.join("|", regexes);
        expression = String.format(expression, this.allowedImports.toArray());

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(this.script);

        return matcher.find();
    }

    /**
     * Checks if the script contains API calls that are
     * not available.
     * 
     * This is done by checking the methods that are called
     * on a object called "npc", which provides the API.
     * 
     * @return true if unknown API calls are found, else false
     */
    protected boolean scriptUsesNonApiCalls() {

        String expression = String.join("|", this.apiMethods);
        expression = String.format("npc\\s*\\.\\s*(?!%s).*\\(", expression);

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(this.script);

        return matcher.find();
    }
    
}
