package com.example.tubes01.Model;

public class Calculator implements ICalculatorModel {
    private double op1;
    private String operator;
    private double result;


    @Override
    public double add(double op1, double op2) {
        setOperand(op1);
        result = op1 + op2;
        return result;
    }

    @Override
    public double sub(double op1, double op2) {
        setOperand(op1);
        result = op1 - op2;
        return result;
    }

    @Override
    public double div(double op1, double op2) {
        setOperand(op1);
        if(op2 != 0){
            result = op1/op2;
        }
        else{
            throw new IllegalArgumentException("Tidak bisa dibagi dengan 0");
        }
        return result;
    }

    @Override
    public double mul(double op1, double op2) {
        setOperand(op1);
        result = op1 * op2;
        return result;
    }

    public String getOperand(){return String.valueOf(op1);}

    public String getOperator(){return operator;}

    public void setOperand(double operand){this.op1 = operand; }

    public void setOperator(String operator){this.operator = operator;}
}


