package cn.istarx.writeunittestdemo;

import java.math.BigInteger;

public class Factorial {

    public static BigInteger calculateFactorial(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("illegal argument.");
        }
        if (num == 0 || num == 1) {
            return new BigInteger("1");
        }
        // return num * calculateFactorial(num - 1);
        BigInteger bigIntegerNum = BigInteger.valueOf(num);
        BigInteger temp = BigInteger.ONE;
        BigInteger res = BigInteger.ONE;
        for (BigInteger i = BigInteger.ONE; i.compareTo(bigIntegerNum) <= 0; i = i.add(temp)) {
            res = res.multiply(i);
        }
        return res;
    }
}