package com.dhitzel.server.quiz;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Solver {

    private Logger logger = LoggerFactory.getLogger(Solver.class);

    private List<MathFunction> functionList;

    public Solver() { 

        MathFunction sqrt = new MathFunction()  {
            public double calculate(double x) {
                if (x < 0) {
                    logger.error("sqrt can only be performed on non-negative numbers");
                    return -1;
                }
                double result = Math.sqrt(x);

                logger.info("sqrt(" + x + ") is " + result);
                return result;
            }
        };

        MathFunction inv = new MathFunction()  {
            public double calculate(double x) {
                if (x == 0) {
                    logger.error("inv can only be performed on non-zero numbers");
                    return -1;
                }
                double result = 1 / x;

                logger.info("inverse(" + x + ") is " + result);
                return result;
            }
        };

        functionList = new ArrayList<>();
        functionList.add(sqrt);
        functionList.add(inv);

        int x = 4;

        List<Double> results = solveAll(x);

        for (Double result : results) {
            logger.info("result: " + result);
        }

        int[] intArray = { 4, 6, 8 };

        logger.info("Before");
        for (int i=0; i<intArray.length; i++) {
            logger.info("intArray[" + i + "] : " + intArray[i]);
        }
        arrayParameter(intArray);
        logger.info("After");
        for (int i=0; i<intArray.length; i++) {
            logger.info("intArray[" + i + "] : " + intArray[i]);
        }
    }

    public List<Double> solveAll(double x) {
        List<Double> result = new ArrayList<Double>();
        for (MathFunction function : this.functionList) {
            result.add(new Double(function.calculate(x)));
        }

        return result;
    }

    public void arrayParameter(int[] intArray) {
        for (int i=0; i<intArray.length; i++) {
            intArray[i] += 2;
        }
        logger.info("During");
        for (int i=0; i<intArray.length; i++) {
            logger.info("intArray[" + i + "] : " + intArray[i]);
        }

    }

}
