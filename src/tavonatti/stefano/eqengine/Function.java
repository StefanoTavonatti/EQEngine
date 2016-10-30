package tavonatti.stefano.eqengine;

import tavonatti.stefano.eqengine.exceptions.DublicatedVariableException;
import tavonatti.stefano.eqengine.exceptions.ParsingException;
import tavonatti.stefano.eqengine.exceptions.UndefinedVariable;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stefano on 30/10/16.
 */
public class Function extends Parser {

    private String name;
    private String code;
    private HashMap<String,String> arguments;

    private Function(String name,HashMap<String,String> args,String code) throws ScriptException {
        super();
        this.setName(name);
        this.setCode(code);

        arguments=args;

    }

    public static Function createFunction(String name,String code) throws DublicatedVariableException, ScriptException {

        Pattern removeFunction=Pattern.compile("[\\s\\t]*function");
        Matcher removeFunctionMatcher=removeFunction.matcher(name);
        name=removeFunctionMatcher.replaceFirst("");
        int index=name.indexOf("(");

        if(index==-1){
            return null;
        }

        String functionName=name.substring(0,index);

        Pattern variables=Pattern.compile("\\$[A-Za-z0-9]+");
        Matcher variablesMatcher=variables.matcher(name);

        HashMap<String,String> args=new HashMap<>();

        while (variablesMatcher.find()){
            String temp=variablesMatcher.group(0);
            temp=temp.substring(1);

            if(args.get(temp)!=null){
                throw new DublicatedVariableException("Duplicate variable in "+functionName+" function declaration");
            }

            args.put(temp,"");
        }


        return new Function(functionName,args,code);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String eval(HashMap<String,String> vars) throws UndefinedVariable, ParsingException, ScriptException, DublicatedVariableException {
        return  super.eval(getCode(),vars);
    }

    public HashMap<String,String> getArguments() {
        return arguments;
    }
}
