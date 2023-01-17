package de.hsrm.mi.swt_project.demo.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScriptValidatorTest {

    @Test
    void testWithValidImport() {
        String script = "import random";
        Validator validator = new ScriptValidator(script);
        assertTrue(validator.validate());
    }

    @Test
    void testWithOneInvalidImport() {
        String script = "import sys";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithMixedImports() {
        String script = String.join("\n", "import random", "import sys");
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithImportUsingFromAndValidModule() {
        String script = "    from random import randint";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithImportUsingFromAndInvalidModule() {
        String script = "    from os    import something    ";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithVariableNameContainingImport() {
        String script = "my_import = 1";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithValidFunctions() {
        String script = "do_something()";
        Validator validator = new ScriptValidator(script);
        assertTrue(validator.validate());
    }

    @Test
    void testWithExec() {
        String script = "exec('1 + 1')";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithEval() {
        String script = "eval('int(1)')";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithEvalAndWhitespace() {
        String script = "eval    ('int(1)')";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithFunctionNameContainingExec() {
        String script = "execute_something()";
        Validator validator = new ScriptValidator(script);
        assertTrue(validator.validate());
    }

    @Test
    void testWithFunctionNameContainingEval() {
        String script = "evaluate_something()";
        Validator validator = new ScriptValidator(script);
        assertTrue(validator.validate());
    }

    @Test
    void testWithFunctionNameEndingWithEval() {
        String script = "my_eval()";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithFunctionNameEndingWithExec() {
        String script = "my_exec()";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

    @Test
    void testWithValidApiCall() {
        String script = "npc.turnLeft()";
        Validator validator = new ScriptValidator(script);
        assertTrue(validator.validate());
    }

    @Test
    void testWithValidApiCallAndArgs() {
        String script = "npc.distanceTo(something)";
        Validator validator = new ScriptValidator(script);
        assertTrue(validator.validate());
    }

    @Test
    void testWithUnknownApiCall() {
        String script = "npc.some_unknwon_function()";
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }

}
