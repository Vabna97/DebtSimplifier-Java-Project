package com.BillsSplitMain;

import com.BillsSplitMain.service.BillsSplitUtil;

import java.io.*;

public class BillsSplitMain {

    public static void main(String[] args) throws IOException {
	// write your code here


        InputStreamReader read = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(read);
        //BillsSplitMain ob = new BillsSplitMain();
        System.out.println("Welcome to BILLS SPLIT");
        BillsSplitUtil util = new BillsSplitUtil();
        util.initialConfig(in);




    }
}


