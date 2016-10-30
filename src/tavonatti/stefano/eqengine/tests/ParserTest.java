package tavonatti.stefano.eqengine.tests;

import org.junit.Assert;
import org.junit.Test;
import tavonatti.stefano.eqengine.Parser;

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
        String script="function sum($arg1, $arg2):\n\tres=$arg1+$arg2\n\treturn $res\n\nreturn sum(2,3)";
        int result= Integer.parseInt(parser.eval(script));
        System.out.println("evalFunction() result: "+result);
    }


}