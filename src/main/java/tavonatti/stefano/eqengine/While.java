package tavonatti.stefano.eqengine;

import tavonatti.stefano.eqengine.exceptions.DublicatedVariableException;
import tavonatti.stefano.eqengine.exceptions.ParsingException;
import tavonatti.stefano.eqengine.exceptions.UndefinedFunctionException;
import tavonatti.stefano.eqengine.exceptions.UndefinedVariable;

import javax.script.ScriptException;
import java.util.HashMap;

/**
 * Created by stefano on 03/11/16.
 */
public class While extends Parser {

    private String condition;
    private String code;

    private While(String condition,String code) throws ScriptException {
        super();
        this.condition=condition;
        this.code=code;
    }

    public static While createWhile(String rawCondition,String code) throws ScriptException {
        String condition=rawCondition.replace("while","").replaceAll(":","");
        return new While(condition,code);
    }

    public String eval(HashMap<String,String> args) throws DublicatedVariableException, ParsingException, UndefinedVariable, UndefinedFunctionException, ScriptException {
        while (evalCond(args)){
            super.eval(code,args);
        }

        return "";
    }
    private boolean evalCond(HashMap<String,String> vars) throws DublicatedVariableException, ParsingException, UndefinedVariable, UndefinedFunctionException, ScriptException {
        String res=super.eval(condition,vars);
        boolean b=Boolean.parseBoolean(super.eval(condition,vars));
        return b;
    }

    public String getCondition() {
        return condition;
    }

    public String getCode() {
        return code;
    }
}
