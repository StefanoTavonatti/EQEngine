package tavonatti.stefano.eqengine.tests;

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
        System.out.println("parsing result "+ parser.eval("arg3=13\nresult=$arg1 + $arg2+$arg3 \nreturn $result",hm));
    }

}