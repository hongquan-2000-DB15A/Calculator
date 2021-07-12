package com.example.appcaculation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, enter, add, sub, mul, div, clear, point;
    TextView txt1, txt2;

    public enum InputType { singleOperator, uraOperator, number, ans }
    public enum UraOpType { add, sub, mul, divide, pow, none}

    TextView mathOpView, currentInputView;

    InputType lastInputType = InputType.ans;

    double firstInputNumber = 0, secondInputNumber = 0;

    UraOpType uraType = UraOpType.none;
    UraOpType renderCacheUraType = UraOpType.none;

    boolean secondInputOccupied = false;

    double currentInputNumber = 0;

    boolean isFloatingNumber = false;

    int afterFloatingPoint = 0;

    double memory = 0;

    String mathOpString = "", currentInputString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.one);
        b2 = findViewById(R.id.two);
        b3 = findViewById(R.id.three);
        b4 = findViewById(R.id.four);
        b5 = findViewById(R.id.five);
        b6 = findViewById(R.id.six);
        b7 = findViewById(R.id.seven);
        b8 = findViewById(R.id.eight);
        b9 = findViewById(R.id.nine);
        b0 = findViewById(R.id.zero);
        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        mul = findViewById(R.id.mul);
        div = findViewById(R.id.div);
        point = findViewById(R.id.point);
        enter = findViewById(R.id.enter);
        clear = findViewById(R.id.clear);
        txt1 = findViewById(R.id.txt1);

        //to show value of this button in textView1
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumberCharacter(1);
            }
        });
        //to show value of this button in textView1
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumberCharacter(2);
            }
        });
        //to show value of this button in textView1
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumberCharacter(3);
            }
        });
        //to show value of this button in textView1
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { EnterNumberCharacter(4); }
        });
        //to show value of this button in textView1
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumberCharacter(5);
            }
        });
        //to show value of this button in textView1
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumberCharacter(6);
            }
        });
        //to show value of this button in textView1
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumberCharacter(7);
            }
        });
        //to show value of this button in textView1
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumberCharacter(8);
            }
        });
        //to show value of this button in textView1
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNumberCharacter(9);
            }
        });
        //to show value of this button in textView1
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { EnterNumberCharacter(0); }
        });
        //enter float point in textView1
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { EnterFloatPoint(); }
        });


        //To Add
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOp();

            }
        });
        //To subtract
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubOp();
            }
        });
        //To multiply
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MulOp();
            }
        });
        //To divide
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DivideOp();
            }
        });
        //To clear and refresh everything!
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allReset();
                ClearAll();
            }
        });
        //To calculate answer
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Answer();
            }
        });
    }

    //to reset all buttons and textview
    public void allReset(){
//        add.setBackgroundColor(getResources().getColor(R.color.buttonColor));
//        mul.setBackgroundColor(getResources().getColor(R.color.buttonColor));
//        sub.setBackgroundColor(getResources().getColor(R.color.buttonColor));
//        div.setBackgroundColor(getResources().getColor(R.color.buttonColor));
        enter.setEnabled(true);
        sub.setEnabled(true);
        mul.setEnabled(true);
        div.setEnabled(true);
        add.setEnabled(true);
        txt1.setText("");
    }
    //to change button color
    public void colorChange(Button b){
        b.setBackgroundColor(getResources().getColor(R.color.white));
    }

    void EnterNumberCharacter(int input)
    {
        if (lastInputType == InputType.uraOperator || lastInputType == InputType.ans || lastInputType == InputType.singleOperator)
        {
            currentInputNumber = input;
            isFloatingNumber = false;
            afterFloatingPoint = 0;
            Log.d("number", "Reset current input, current input is "+ currentInputNumber);
        } else if (lastInputType == InputType.number)
        {
            if (isFloatingNumber)
            {
                afterFloatingPoint++;
                currentInputNumber = Math.signum(currentInputNumber) * (Math.abs(currentInputNumber) + input * Math.pow(0.1, afterFloatingPoint));
            } else
            {
                if (currentInputNumber != 0 || input != 0)
                    currentInputNumber = Math.signum(currentInputNumber) * (Math.abs(currentInputNumber) * 10 + input);
            }
            Log.d("number", "Update current input to "+ currentInputNumber);
        }
        lastInputType = InputType.number;
        RenderCurrentInput();
    }

    void EnterFloatPoint()
    {
        if (lastInputType == InputType.uraOperator || lastInputType == InputType.ans || lastInputType == InputType.singleOperator)
        {
            currentInputNumber = 0;
            isFloatingNumber = true;
            afterFloatingPoint = 0;
            Log.d("number","Zero point...");
        }
        else if (lastInputType == InputType.number)
        {
            if (!isFloatingNumber)
            {
                isFloatingNumber = true;
                afterFloatingPoint = 0;
                Log.d("number","Enter float point");
            }
        }
        lastInputType = InputType.number;
        RenderCurrentInput();
    }

    void UraOp(UraOpType opTypeInput)
    {
        if (lastInputType == InputType.number || lastInputType == InputType.ans || lastInputType == InputType.singleOperator)
        {
            if (lastInputType == InputType.ans)
            {
                Log.d("Operator","Push answer to first input, set operator to " + uraType);
            }
            else
            {
                if (uraType != UraOpType.none)
                {
                    secondInputNumber = currentInputNumber;
                    DoMath();
                    Log.d("Operator","Calculated answer, then push to first input, set operator to " + opTypeInput);
                    RenderCurrentInput();
                } else
                {
                    Log.d("Operator","Push current input to first input, set operator to " + opTypeInput);
                }
            }
            firstInputNumber = currentInputNumber;
        }
        else if (lastInputType == InputType.uraOperator)
        {
            Log.d("Operator", "Change operator to " + opTypeInput);
        } else
        {
            Log.e("Operator","Opps, not suppose to be here");
        }

        lastInputType = InputType.uraOperator;
        uraType = opTypeInput;
        renderCacheUraType = uraType;
        RenderMathOp(false);
    }

    void AddOp()
    {
        UraOp(UraOpType.add);
    }

    void SubOp()
    {
        UraOp(UraOpType.sub);
    }

    void MulOp()
    {
        UraOp(UraOpType.mul);
    }

    void DivideOp()
    {
        UraOp(UraOpType.divide);
    }

    void PowerOp()
    {
        UraOp(UraOpType.pow);
    }

    void DoMath()
    {
        if (uraType==UraOpType.add) {
            currentInputNumber = secondInputNumber + firstInputNumber;
        }
        else if (uraType== UraOpType.sub) {
            currentInputNumber = firstInputNumber - secondInputNumber;
        }
        else if (uraType==UraOpType.mul) {
            currentInputNumber = firstInputNumber * secondInputNumber;
        }
        else if (uraType==UraOpType.divide) {
            currentInputNumber = firstInputNumber / secondInputNumber;
        }
        else if (uraType==UraOpType.pow) {
            currentInputNumber = (float) Math.pow(firstInputNumber, secondInputNumber);
        }
        Log.d("DoMath", "Doing operator " + uraType + " between " + firstInputNumber + " and "+secondInputNumber+",Result is "+currentInputNumber);
    }

    void Answer()
    {
        if (lastInputType == InputType.number || lastInputType == InputType.singleOperator)
        {
            if (uraType != UraOpType.none)
            {
                secondInputNumber = currentInputNumber;
                secondInputOccupied = true;
                DoMath();
                uraType = UraOpType.none;
            } else
            {
                firstInputNumber = currentInputNumber;
                Log.d("Answer","Set first input number "+firstInputNumber);
            }
        } else if (lastInputType == InputType.ans)
        {
            if (uraType != UraOpType.none)
            {
                firstInputNumber = currentInputNumber;
                DoMath();
            } else
            {
                //do nothing
            }
        } else if (lastInputType == InputType.uraOperator)
        {
            if (uraType != UraOpType.none)
            {
                secondInputNumber = currentInputNumber;
                secondInputOccupied = true;
                DoMath();
                uraType = UraOpType.none;
            }
            else
            {
                Log.e("Answer","Cannot run operator null, check your code again");
            }
        }
        Log.d("Answer", "Finish answer");
        RenderMathOp(true);
        RenderCurrentInput();
        lastInputType = InputType.ans;
    }

    void NegateOp()
    {
        currentInputNumber = -currentInputNumber;
        lastInputType = InputType.number;
        RenderCurrentInput();
        Log.d("SingleOp", "Negated current input to "+currentInputNumber);
    }

    void SqrtOp()
    {
        currentInputNumber = Math.sqrt(currentInputNumber);
        lastInputType = InputType.singleOperator;
        RenderCurrentInput();
        Log.d("SingleOp", "Squattered current input to "+currentInputNumber);
    }

    void PercentOp()
    {
        currentInputNumber *= 0.01f;
        lastInputType = InputType.singleOperator;
        RenderCurrentInput();
        Log.d("SingleOp", "Percentage current input to "+currentInputNumber);
    }

    void InverseOp()
    {
        currentInputNumber = 1.0f / currentInputNumber;
        lastInputType = InputType.singleOperator;
        RenderCurrentInput();
        Log.d("SingleOp", "Inversed current input to "+currentInputNumber);
    }

    void MemoryAdd()
    {
        memory += currentInputNumber;
    }

    void MemorySubtract()
    {
        memory -= currentInputNumber;
    }

    void MemoryRecall()
    {
        currentInputNumber = memory;
        RenderCurrentInput();
    }

    void MemoryClear()
    {
        memory = 0;
    }

    void MemoryRecallThenClear()
    {
        MemoryRecall();
        MemoryClear();
    }

    void ClearCurrentInput()
    {
        currentInputNumber = 0;
        isFloatingNumber = false;
        afterFloatingPoint = 0;
        RenderCurrentInput();
        Log.d("Clear", "Clear current input");
    }

    void ClearAll()
    {
        firstInputNumber = secondInputNumber = 0;
        secondInputOccupied = false;
        uraType = renderCacheUraType = UraOpType.none;
        lastInputType = InputType.ans;
        currentInputNumber = 0;
        isFloatingNumber = false;
        afterFloatingPoint = 0;
        memory = 0;
        mathOpString="";
        RenderCurrentInput();
        Log.d("Clear", "Clear all");
    }

    String UraOpRender()
    {
        if (renderCacheUraType==UraOpType.add) {
            return "+";
        }
        if (renderCacheUraType==UraOpType.sub) {
            return "-";
        }
        if (renderCacheUraType==UraOpType.mul) {
            return "x";
        }
        if (renderCacheUraType==UraOpType.divide) {
            return "/";
        }
        if (renderCacheUraType==UraOpType.pow) {
            return "^";
        }
        return "";
    }

    void RenderMathOp(boolean finalAnswer)
    {
        if (finalAnswer)
        {
            if (secondInputOccupied)
            {
                mathOpString = firstInputNumber + " " + UraOpRender() + " " + secondInputNumber + " = ";
                secondInputOccupied = false;
            } else
                mathOpString = firstInputNumber + " = ";
        } else
        {
            if (uraType != UraOpType.none)
            {
                mathOpString = firstInputNumber + " " + UraOpRender() +" ";
            }
            else
            {
                mathOpString = String.valueOf(firstInputNumber);
                if (mathOpString==null)
                {
                    mathOpString = "";
                }
            }
        }
        txt1.setText(mathOpString + currentInputString);
        Log.d("Render","mathOp: "+mathOpString+"|currentInput: "+currentInputString);
    }

    void RenderCurrentInput()
    {
        if (isFloatingNumber && afterFloatingPoint == 0) {
            currentInputString = currentInputNumber + ".";
        } else
        {
            currentInputString = String.valueOf(currentInputNumber);
            if (currentInputString==null)
            {
                currentInputString = "";
            }
        }
        txt1.setText(mathOpString + currentInputString);
        Log.d("Render","mathOp: "+mathOpString+"|currentInput: "+currentInputString);
    }
}