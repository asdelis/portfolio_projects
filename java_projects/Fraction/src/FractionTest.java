import org.junit.jupiter.api.Assertions;
import org.junit.Test;

class FractionTest {
    //run all the tests
    @Test
    public void runAllTests() {
        constructorTest1();
        constructorTest2();
        reciprocalTest();
        toDoubleTest();
        compareToTest();
        toFractionStringTest();
        plusTest();
        minusTest();
        timesTest();
        dividedByTest();
    }
    //test constructor that takes no arguments
    @Test
        public void constructorTest1() {
        //Fraction();
        Fraction fractionObject = new Fraction();
        long n = fractionObject._numerator;
        long d = fractionObject._denominator;
        Assertions.assertEquals(n, 0);
        Assertions.assertEquals(d, 1);
    }
    //test constructor that takes arguments
    @Test
        public void constructorTest2() {
        Fraction fractionObject = new Fraction(1, 2);
        Assertions.assertEquals(fractionObject._numerator, 1);
        Assertions.assertEquals(fractionObject._denominator, 2);
        //testing whether the if statement worked
        Fraction fractionObject2 = new Fraction(1, -2);
        Assertions.assertEquals(fractionObject2._numerator, -1);
        Assertions.assertEquals(fractionObject2._denominator, 2);
        //check to see if reduce() worked
        Fraction fractionObject3 = new Fraction(2, 4);
        Assertions.assertEquals(fractionObject3._numerator, 1);
        Assertions.assertEquals(fractionObject3._denominator, 2);
        }
    @Test
    public void reciprocalTest() {
        Fraction fractionObject = new Fraction(8, 24);
        fractionObject.reciprocal();
        Assertions.assertEquals(fractionObject._numerator, 3);
    }
    @Test
    public void toDoubleTest() {
        Fraction fractionObject = new Fraction(4, 10);
        double val = fractionObject.toDouble();
        Assertions.assertEquals(val, fractionObject.toDouble());
    }
    @Test
    public void compareToTest() {
        Fraction fractionObject1 = new Fraction(2, 3);
        Fraction fractionObject2 = new Fraction(1, 3);
        Fraction fractionObject3 = new Fraction(2, 3);
        int isEqual = fractionObject1.compareTo(fractionObject2);
        int isEqual2 = fractionObject2.compareTo(fractionObject1);
        int isEqual3 = fractionObject2.compareTo(fractionObject3);
    }
    @Test
    public void toFractionStringTest() {
        Fraction fractionObject1 = new Fraction(4, 10);
        String s1 = fractionObject1.toFractionString();
        Assertions.assertEquals(s1, "2/5");
        Fraction fractionObject2 = new Fraction(4, -10);
        String s2 = fractionObject2.toFractionString();
        Assertions.assertEquals(s2, "-2/5");
    }
    @Test
    public void plusTest() {
        Fraction fractionObject1 = new Fraction(2, 3);
        Fraction fractionObject2 = new Fraction(3, 3);
        Fraction fractionObject3 = new Fraction();
        fractionObject3 = fractionObject1.plus(fractionObject2);
        Assertions.assertEquals(fractionObject3._numerator, 5);
    }
    @Test
    public void minusTest() {
        Fraction fractionObject1 = new Fraction(2, 3);
        Fraction fractionObject2 = new Fraction(3, 3);
        Fraction fractionObject3 = new Fraction();
        fractionObject3 = fractionObject2.minus(fractionObject1);
    }
    @Test
    public void timesTest() {
        Fraction fractionObject1 = new Fraction(2, 3);
        Fraction fractionObject2 = new Fraction(2, 3);
        Fraction fractionObject3 = new Fraction();
        fractionObject3 = fractionObject1.times(fractionObject2);
        Assertions.assertEquals(fractionObject3._numerator, 4);
    }
    @Test
    public void dividedByTest() {
        Fraction fractionObject1 = new Fraction(2, 3);
        Fraction fractionObject2 = new Fraction(2, 3);
        Fraction fractionObject3 = new Fraction();
        fractionObject3 = fractionObject1.dividedBy(fractionObject2);
        Assertions.assertEquals(fractionObject3._numerator, 1);
    }

}