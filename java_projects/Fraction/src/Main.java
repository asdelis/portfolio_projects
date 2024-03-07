import org.junit.runner.manipulation.Ordering;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        //Make a new fraction and fraction test
        FractionTest fractionTest = new FractionTest();
        //runAllTests()
        fractionTest.runAllTests();
        try {
            Fraction exceptionTest = new Fraction(10, 0);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Argument 'divisor' is 0");
        }

        ArrayList<Fraction> fractionArrayList = new ArrayList<Fraction>();
        Fraction fractionObject1 = new Fraction(1, 3);
        Fraction fractionObject2 = new Fraction(2, 3);
        Fraction fractionObject3 = new Fraction(4, 3);
        fractionArrayList.add(fractionObject1);
        fractionArrayList.add(fractionObject3);
        fractionArrayList.add(fractionObject2);
        Collections.sort(fractionArrayList);
        for (int i = 0; i < 3; i++){
            long num = fractionArrayList.get(i)._numerator;
            System.out.println(num);
        }

    }
}