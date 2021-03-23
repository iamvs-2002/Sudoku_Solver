package com.example.sudokusolver;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

/* A Backtracking program in
Java to solve Sudoku problem */
public class Solver
{
    Context context;

    public Solver(Context context){
        this.context = context;
    }

    public boolean isSafe(int[][] board, int row, int col, int num) {
        // Row has the unique (row-clash)
        for (int d = 0; d < board.length; d++)
        {

            // Check if the number we are trying to
            // place is already present in
            // that row, return false;
            if (board[row][d] == num) {
                return false;
            }
        }

        // Column has the unique numbers (column-clash)
        for (int r = 0; r < board.length; r++)
        {

            // Check if the number
            // we are trying to
            // place is already present in
            // that column, return false;
            if (board[r][col] == num)
            {
                return false;
            }
        }

        // Corresponding square has
        // unique number (box-clash)
        int sqrt = (int)Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++)
        {
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++)
            {
                if (board[r][d] == num)
                {
                    return false;
                }
            }
        }

        // if there is no clash, it's safe
        return true;
    }

    public boolean solveSudoku(int[][] board, int n) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (board[i][j] == 0)
                {
                    row = i;
                    col = j;

                    // We still have some remaining
                    // missing values in Sudoku
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        // No empty space left
        if (isEmpty)
        {
            return true;
        }

        // Else for each-row backtrack
        for (int num = 1; num <= n; num++)
        {
            if (isSafe(board, row, col, num))
            {
                board[row][col] = num;
                if (solveSudoku(board, n))
                {
                    // print(board, n);
                    return true;
                }
                else
                {
                    // replace it
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    public void print(int[][] board, int N) {

        TextView t;
        // We got the answer, just print it
        for (int r = 0; r < N; r++)
        {
            for (int d = 0; d < N; d++)
            {
                int id = Integer.parseInt(String.valueOf(r+1)+String.valueOf(d+1));
                t = ((Activity)context).findViewById(id);
                t.setText(String.valueOf(board[r][d]));
                System.out.print(" ");
            }
        }
    }

    void printQuestn(int[][] board, int N) {
        TextView t;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                int id = Integer.parseInt(String.valueOf(i+1)+String.valueOf(j+1));
                t = ((Activity)context).findViewById(id);
                if (board[i][j]==0){
                    t.setText("");
                }
                else {
                    t.setText(String.valueOf(board[i][j]));
                }
            }
        }
    }

    public void clear(int[][] board, int N) {
        TextView t;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                int id = Integer.parseInt(String.valueOf(i+1)+String.valueOf(j+1));
                t = ((Activity)context).findViewById(id);
                t.setText("");
            }
        }
    }
}