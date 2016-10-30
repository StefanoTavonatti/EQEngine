package tavonatti.stefano.eqengine;

import tavonatti.stefano.eqengine.exceptions.ParsingException;
import tavonatti.stefano.eqengine.exceptions.UndefinedVariable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by stefano on 30/10/16.
 */
public class Parser {
    ScriptEngine engine;

    public Parser() throws ScriptException {
        ScriptEngineManager manager;
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("js");
        //Object result = engine.eval("4*5");
    }

    public String eval(String script) throws UndefinedVariable, ParsingException {
        HashMap<String,String> hm=new HashMap<>();
        return eval(script,hm);
    }

    public String eval(String script, HashMap<String,String> args) throws UndefinedVariable, ParsingException {
        String lines[]=script.split("\n");

        //Pattern function=Pattern.compile("\\s*\\t*function\\s*\\t*[A-Za-z]+\\([A-Za-z]\\)");

        //Pattern test=Pattern.compile("(\\$[A-Za-z0-9]+)");
        /*Pattern test=Pattern.compile("(\\s*\\t*[A-Za-z0-9]+=)");

        Matcher t=test.matcher(script);
        while (t.find()){
            System.out.println(t.group(0));
        }*/


        Pattern returnPatter=Pattern.compile("return");


        for(String line:lines){
            Matcher returnMatcher=returnPatter.matcher(line);
            if(returnMatcher.find()){
                String st=line.replaceAll(returnMatcher.group(0),"");
                st=replaceVariableWithValues(st,args);
                return evalLine(st,args);
            }
            else{
                evalLine(line,args);
            }
        }

        return null;
    }

    private String evalLine(String line, HashMap<String,String> vars) throws UndefinedVariable, ParsingException {
        Pattern variables=Pattern.compile("([\\s\\t]*[A-Za-z0-9]+[\\s\\t]*=)");

        Matcher matcher=variables.matcher(line);

        /*while (matcher.find())
            System.out.println(matcher.group(0));*/

        if(matcher.find()){
            String variable=matcher.group(0);
            line=line.replace(variable,"");
            line=replaceVariableWithValues(line,vars);
            String result=evalEQ(line);
            vars.put(variable.substring(0,variable.length()-1),result);
            return result;

        }
        else{
            line=replaceVariableWithValues(line,vars);
            String result=evalEQ(line);
            return result;
        }


    }

    private String replaceVariableWithValues(String line,HashMap<String,String> vars) throws UndefinedVariable {

        Pattern variables=Pattern.compile("(\\$[A-Za-z0-9]+)");
        Matcher matcher=variables.matcher(line);

        while (matcher.find()){
            String var=matcher.group(0);
            String var1=var.substring(1);
            if(vars.get(var1)==null){
                UndefinedVariable undefinedVariable=new UndefinedVariable("Variable "+var1+" is not defined");
                throw undefinedVariable;
            }
            else
            {
                line=line.replaceAll("\\$"+var1,vars.get(var1));
            }
        }

        return line;
    }

    private String evalEQ(String line) throws ParsingException {//TODO custom engine, check special chars

        /*if(line.contains("(")){

        }
        else{
            Pattern mul=Pattern.compile("[0-9]+(\\.[0-9]+)?[\\s\\t]*\\*[\\s\\t]*[0-9]+(\\.[0-9]+)?");
            Matcher mulMatcher=mul.matcher(line);
            if(mulMatcher.find()){
                String temp=mulMatcher.group(0);
                line=line.replace(temp,evalEQ(temp));
            }
            else
        }*/

        if(!line.matches("[\\s\\t0-9\\+\\-\\*\\/\\(\\)]+"))
        {
            throw new ParsingException();
        }

        try {
            return engine.eval(line).toString();
        } catch (ScriptException e) {

            throw new ParsingException();
        }
    }

}
