package com.example.sudokusolver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int[][] board;
    int N;//size of board
    int flag = 0;
    GridLayout gridLayout;
    RelativeLayout relativeLayout,relativeLayout2;
    Button generate,clear,solve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        Random r = new Random();

        int[][] a = new int[][] {
                { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
                { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
                { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
                { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
                { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
                { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
                { 0, 0, 5, 2, 0, 6, 3, 0, 0 }
        };

        int[][] b = new int[][] {
                {1 ,0 ,3 ,4 ,0 ,0 ,7 ,0 ,9},
                {0 ,5 ,6 ,0 ,8 ,9 ,0 ,2 ,3},
                {0 ,8 ,9 ,1 ,0 ,3 ,4 ,0 ,6},
                {2 ,1 ,4 ,0 ,6 ,5 ,0 ,9 ,7},
                {3 ,0 ,0 ,8 ,0 ,7 ,0 ,1 ,4},
                {8 ,0 ,7 ,0 ,1 ,4 ,0 ,6 ,5},
                {0 ,3 ,1 ,0 ,4 ,0 ,9 ,7 ,8},
                {6 ,4 ,0 ,9 ,7 ,0 ,5 ,3 ,1},
                {0 ,7 ,8 ,0 ,0 ,1 ,0 ,4 ,2}
        };



        Solver s = new Solver(MainActivity.this);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restart = new Intent(MainActivity.this,MainActivity.class);
                startActivity(restart);
                finish();
            }
        });
        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag==0){
                    Toast.makeText(MainActivity.this, "First generate the problem.", Toast.LENGTH_SHORT).show();
                    return;
                }
                s.clear(board,N);
                if (s.solveSudoku(board, N))
                {
                    // print solution
                    s.print(board, N);
                }
                else {
                    System.out.println("No solution");
                }
            }
        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rand = r.nextInt(2);
                switch (rand){
                    case 0:
                        board=a;
                        break;
                    case 1:
                        board=b;
                        break;
                }
                N = board.length;
                s.printQuestn(board,N);
                flag=1;
            }
        });
    }

    private void init() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;


        relativeLayout = findViewById(R.id.relativelayout);
        relativeLayout2 = findViewById(R.id.relativelayout2);
        generate = findViewById(R.id.generatebtn);
        clear = findViewById(R.id.clearbtn);
        solve = findViewById(R.id.solvebtn);


        gridLayout = (GridLayout) findViewById(R.id.gridview);
        gridLayout.removeAllViews();


        int column = 9;
        int row = 9;

        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row);

        int total = row*column;

        for (int i = 0, c = 0, r = 0; i < total; i++, c++)
        {
            if (c == column)
            {
                //move to next row after all the columns are filled for first row
                c = 0;
                r++;
            }

            //create a new textview
            TextView textView = new TextView(this);
            String id = String.valueOf(r+1) + String.valueOf(c+1);
            textView.setId(Integer.parseInt(id));
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundColor(Color.WHITE);
            textView.setTextSize(19);
            textView.setTextColor(Color.BLACK);


            //setting the width and height for this textview
            textView.setWidth(
                    (int)width/column
                            - relativeLayout.getPaddingLeft()
                            - relativeLayout2.getPaddingLeft()
            );
            textView.setHeight(
                    (int)width/column
                            - relativeLayout.getPaddingTop()
                            - relativeLayout2.getPaddingTop()
            );



            //setting the row and column number for this textview
            GridLayout.Spec rowSpan = GridLayout.spec(r, 1);
            GridLayout.Spec colspan = GridLayout.spec(c, 1);
            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(
                    rowSpan, colspan);
            textView.setLayoutParams(gridParam);
            gridLayout.addView(textView, gridParam);

            ViewGroup.MarginLayoutParams  parameter = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
            //parameter =  (RelativeLayout.LayoutParams) textView.getLayoutParams();
            parameter.setMargins(2, 2, 2, 2); // left, top, right, bottom
            textView.setLayoutParams(parameter);

        }

        Log.e("Colmn", String.valueOf(gridLayout.getColumnCount()));
        Log.e("Row", String.valueOf(gridLayout.getRowCount()));

        /*
         * To access the text view, use:
         * int id = ID_TO_BE_ACCESSED
         * textview.findViewById(id)
         * */
    }
}