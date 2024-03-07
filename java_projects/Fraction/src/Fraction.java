import java.lang.Math;
//Andrew Delis
//CS6011
//Fraction Assignment
public class Fraction implements Comparable<Fraction> {
    //contents of the fraction
    long _numerator, _denominator;

    //Fraction() - the default constructor, which sets the value of the fraction to 0/1.
    Fraction() {
        _numerator = 0;
        _denominator = 1;
    }

    //Fraction( long n, long d ) - a constructor which sets
    //the value of the fraction to a specific numerator (n) and denominator (d).
    Fraction(long n, long d) {
            _numerator = n;
            _denominator = d;

        //Throw exception if the denominator is zero
        if (_denominator == 0) {
            throw new IllegalArgumentException("Argument 'divisor' is 0");
        }

        //make sure the sign is in the numerator spot
        if ((_numerator > 0 && _denominator < 0)) {
            _numerator *= -1;
            _denominator *= -1;
        }
        reduce();
    }

    //Fraction plus( Fraction rhs ) - Returns a new fraction that is the result of the
    //right hand side (rhs) fraction added to this fraction.
    Fraction plus( Fraction rhs){
        Fraction f = new Fraction();
        if (_denominator == rhs._denominator) {
            f._denominator = _denominator;
            f._numerator = _numerator + rhs._numerator;
        }
        else {
            f._denominator = _denominator * rhs._denominator;
            f._numerator = (_numerator * rhs._denominator) + (rhs._numerator * _denominator);
        }
        f.reduce();
        return f;
    }

    //Fraction minus(Fraction rhs) - Returns a new fraction that is the result of the
    //right hand side (rhs) fraction subtracted from this fraction.
    Fraction minus( Fraction rhs){
        Fraction f = new Fraction();
        if (_denominator == rhs._denominator) {
            f._denominator = _denominator;
            f._numerator = _numerator - rhs._numerator;
        }
        else {
            f._denominator = _denominator * rhs._denominator;
            f._numerator = (_numerator * rhs._denominator) - (rhs._numerator * _denominator);
        }
        f.reduce();
        return f;
    }

    //Fraction times(Fraction rhs) - Returns a new fraction that is the result of this fraction
    // multiplied by the right hand side (rhs) fraction.
    Fraction times(Fraction rhs) {
        Fraction f = new Fraction();
        f._numerator = _numerator * rhs._numerator;
        f._denominator = _denominator * rhs._denominator;
        f.reduce();
        return f;
    }

    //Fraction dividedBy(Fraction rhs) - Returns a new fraction that is the result of this fraction
    // divided by the right hand side (rhs) fraction.
    Fraction dividedBy(Fraction rhs) {
        Fraction f = new Fraction();
        rhs.reciprocal();
        f._numerator = _numerator * rhs._numerator;
        f._denominator = _denominator * rhs._denominator;
//        rhs.times(f);
        f.reduce();
        return f;
    }

    //Fraction reciprocal() - Returns a new fraction that is the reciprocal of this fraction.
    void reciprocal() {
        long temp = _numerator;
        _numerator = _denominator;
        _denominator = temp;
        reduce();
        Fraction f = new Fraction(_numerator, _denominator);
    }

    //String toString() - Returns a string representing this fraction.
    // The string should have the format: "N/D", where N is the numerator, and D is the denominator.
    // This method should always print the reduced form of the fraction.
    // If the fraction is negative, the sign should be displayed on the numerator,
    // e.g., "-1/2" not "1/-2".
    String toFractionString(){
        //make sure the sign is in the numerator spot
        if (_denominator < 0) {
            _numerator *= -1;
            _denominator *= -1;
        }
        reduce();
        //save the values of the numerator and denominator in variables
        //and then convert them to a string
        long n = _numerator;
        long d = _denominator;
        String nString = String.valueOf(n);
        String dString = String.valueOf(d);
        return (n + "/" + d);
    }

    //double toDouble() - Returns a (double precision) floating point number that is
    //the approximate value of this fraction, printed as a real number.
    double toDouble() {
        double val = _numerator / _denominator;
        return val;
    }

    public int compareTo(Fraction rhs){
        int f1 = (int) this.toDouble();
        int f2 = (int) rhs.toDouble();
        if (f1 > f2) {
            return 1;
        }
        else if (f1 < f2) {
            return -1;
        }
        else {
            return 0;
        }
    }

    //long GCD() - returns the greatest common divisor
    //of this fraction's numerator and denominator
    //this is a helper method for the reduce method below.
    private long GCD() {
        long gcd = _numerator;
        long remainder = _denominator;
        while( remainder != 0 ) {
            long temp = remainder;
            remainder = gcd % remainder;
            gcd = temp;
        }
        //returning the absolute value so I don't have to mess with signs
        //signs are already taken care of in the constructor
        return Math.abs(gcd);
    }

    //void reduce() - Changes this fraction to its reduced form.
    private void reduce() {
        long gcd = GCD();
        _numerator /= gcd;
        _denominator /= gcd;
    }


}


