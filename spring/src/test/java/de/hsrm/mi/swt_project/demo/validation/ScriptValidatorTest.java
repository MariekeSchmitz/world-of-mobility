package de.hsrm.mi.swt_project.demo.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScriptValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "import random",
        "do_something()",
        "execute_something()",
        "evaluate_something()",
        "npc.turnLeft()",
        "npc.distanceTo(something)"
    })
    void testValidScripts(String script) {
        Validator validator = new ScriptValidator(script);
        assertTrue(validator.validate());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "import sys",
        "import random\nimport sys",
        "from random import randint",
        "    from     os    import    something    ",
        "my_import = 1",
        "exec('1 + 1')",
        "eval('int(1)')",
        "eval    ('int(1)')",
        "my_eval()",
        "my_exec()",
        "npc.some_unknwon_function()"
    })
    void testInvalidScripts(String script) {
        Validator validator = new ScriptValidator(script);
        assertFalse(validator.validate());
    }
    
}
