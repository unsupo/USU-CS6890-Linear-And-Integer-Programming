package utilities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RatioTest {

    @Test public void testDivideByZero(){
        try{
            new Ratio(1,0);
            assertTrue(false);
        }catch (IllegalArgumentException iae){
            assertTrue(true);
        }
        try{
            new Ratio("10/0");
            assertTrue(false);
        }catch (IllegalArgumentException iae){
            assertTrue(true);
        }
    }

    @Test public void testSimplify(){
        assertEquals(new Ratio(2,2),new Ratio(1));
        assertEquals(new Ratio(6,2),new Ratio(3));
        assertEquals(new Ratio(12,24),new Ratio(1,2));
    }

    @Test public void testRatioAdd(){
        assertEquals(new Ratio(1,2).add(new Ratio(1,2)),new Ratio(1));
        assertEquals(new Ratio(5,2).plus(new Ratio(2,15)),new Ratio(79,30));
    }

    @Test public void testRatioSubtract(){
        assertEquals(new Ratio(1,2).subtract(new Ratio(1,2)),new Ratio(0));
        assertEquals(new Ratio(5,2).minus(new Ratio(2,15)),new Ratio(71,30));
    }

    @Test public void testStringConstructor(){
        assertEquals(new Ratio("1/2"),new Ratio(1,2));
        assertEquals(new Ratio("1"),new Ratio(1,1));
        assertEquals(new Ratio("1.1"),new Ratio(11,10));
    }

    @Test public void testDoubleConstructor(){
        assertEquals(new Ratio(1.1),new Ratio(11,10));
    }

    @Test public void testRatioMultiply(){
        assertEquals(new Ratio(1,2).multiply(new Ratio(2)),new Ratio(1));
    }

    @Test public void testRatioDivide(){
        assertEquals(new Ratio(1,2).divide(new Ratio(2)),new Ratio(1,4));
    }

    @Test public void testGetDoubleValue(){
        assertEquals(new Ratio(11,10).getDoubleValue(),1.1,0);
    }

    @Test public void testToString(){
        assertEquals(new Ratio(11,10).toString(),"11/10");
        assertEquals(new Ratio(10,10).toString(),"1");
        assertEquals(new Ratio(20,10).toString(),"2");
    }
}
