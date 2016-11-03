package tavonatti.stefano.eqengine.test;

import org.junit.Test;
import tavonatti.stefano.eqengine.Parser;
import tavonatti.stefano.eqengine.exceptions.ParsingException;

import java.text.ParseException;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by stefano on 30/10/16.
 */
public class ParserTest {

    @Test
    public void evalSimple() throws Exception {
        Parser parser=new Parser();
        HashMap<String,String> hm=new HashMap<>();
        hm.put("arg1","10");
        hm.put("arg2","20");
        System.out.println("parsing result "+ parser.eval("arg3=5\n\nresult= $arg3 * ($arg1 + $arg2) \nreturn $result",hm));
        int result= Integer.parseInt(parser.eval("arg3=5\nresult= $arg3 * ($arg1 + $arg2) \nreturn $result",hm));
        assertEquals("result must be equal to 150",result,150);
    }

    @Test
    public void evalFunction() throws Exception{
        Parser parser=new Parser();
        String script="function sum($arg1, $arg2):\n\tres=$arg1+$arg2\n\treturn $res\n\nreturn sum(2,3) + sum(1,1)+sum(5,5)";
        int result= Integer.parseInt(parser.eval(script));
        System.out.println("evalFunction() result: "+result);
        assertEquals("sum must be 17",result,17);
    }

    @Test
    public void evalNestedFunction() throws Exception{
        Parser parser=new Parser();
        String script="function sum($arg1, $arg2):\n\tres=$arg1+$arg2\n\treturn $res\n\nreturn sum(sum(1,1),3) + sum(1,1)";
        int result= Integer.parseInt(parser.eval(script));
        System.out.println("evalFunction() result: "+result);
        assertEquals("sum must be 7",result,7);
    }

    @Test
    public void evalSimpleBoolean() throws Exception{
        Parser parser=new Parser();
        String result=parser.eval("return 2>1");
        System.out.println("2>1: "+result);
        boolean res= Boolean.parseBoolean(result);
        assertEquals("2>1 must be true",res,true);
    }

    @Test
    public void ifElse() throws Exception{
        Parser parser=new Parser();
        String result=parser.eval("arg=-1\nif $arg>3:\n\treturn 22\n else\n\targ=$arg-1\n\treturn 16+$arg");
        System.out.println("resulte simple if "+result);
        int res=Integer.parseInt(result);
        assertEquals("result mus be 14",res,14);
    }

    @Test
    public void ifElseTrue() throws Exception{
        Parser parser=new Parser();
        String result=parser.eval("arg=-1\nif $arg<3:\n\treturn 22\n else\n\targ=$arg-1\n\treturn 16+$arg");
        System.out.println("resulte simple if true: "+result);
        int res=Integer.parseInt(result);
        assertEquals("result mus be 15",res,15);
    }

    @Test(expected = ParsingException.class)
    public void ifElseNONbool() throws Exception{

        Parser parser=new Parser();
        String result=parser.eval("arg=-1\nif $arg+3:\n\treturn 22\n else\n\treturn 15+$arg");
        System.out.println("resulte simple if "+result);

    }

    @Test
    public void whileSimple() throws Exception{

        Parser parser=new Parser();
        String result=parser.eval("arg=1\nwhile $arg<3:\n\targ=$arg+1\nreturn $arg");
        System.out.println("resulte simple while "+result);

        int res=Integer.parseInt(result);
        assertEquals("arg value must be 3",res,3);

    }

    @Test
    public void whileSimple2() throws Exception{

        Parser parser=new Parser();
        String result=parser.eval("arg=1\nwhile $arg<3:\n\targ=$arg+2\n\targ=$arg-1\nreturn $arg");
        System.out.println("resulte simple while "+result);

        int res=Integer.parseInt(result);
        assertEquals("arg value must be 3",res,3);

    }

}