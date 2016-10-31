package tavonatti.stefano.eqengine;

import tavonatti.stefano.eqengine.exceptions.DublicatedVariableException;
import tavonatti.stefano.eqengine.exceptions.ParsingException;
import tavonatti.stefano.eqengine.exceptions.UndefinedFunctionException;
import tavonatti.stefano.eqengine.exceptions.UndefinedVariable;

import javax.script.ScriptException;
import java.util.HashMap;

/**
 * Created by stefano on 31/10/16.
 */
public class IfElse extends Parser {

    private String ifCode;
    private String elseCode;
    private String condition;

    private IfElse(String ifCode,String elseCode,String condition) throws ScriptException {
        super();
        this.ifCode=ifCode;
        this.elseCode=elseCode;
        this.condition=condition;
    }

    public static IfElse createIfElse(String ifcode,String elsecode,String rawCondition) throws ScriptException {

        String condition=rawCondition.substring(rawCondition.indexOf("if")+2).replaceAll(":","");

        return new IfElse(ifcode,elsecode,condition);
    }

    public String eval(HashMap<String,String> args) throws DublicatedVariableException, ParsingException, UndefinedVariable, UndefinedFunctionException, ScriptException {
        boolean cond=Boolean.parseBoolean(super.eval(getCondition(),args));
        if(cond)
            return eval(getIfCode(),args);
        else
            return eval(getElseCode(),args);
    }

    public String getIfCode() {
        return ifCode;
    }

    public String getElseCode() {
        return elseCode;
    }

    public String getCondition() {
        return condition;
    }
}
