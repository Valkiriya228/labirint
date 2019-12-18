package sample;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class test {

    Collection<String> input = new ArrayList<>(Arrays.asList("00000","00110","1s110","00010","01f00"));
    Collection<String> output = new ArrayList<>(Arrays.asList(
            "0","0","0","0","0",
            "0","0","1","1","0",
            "1","s","1","1","0",
            "0","●","●","1","0",
            "0","1","f","0","0"));

    Collection<String> input1 = new ArrayList<>(Arrays.asList("00000f1","0111101","1110001","0010101","0110001","0000111", "01s0111"));
    Collection<String> output1 = new ArrayList<>(Arrays.asList(
            "0","0","0","0","0","f","1",
            "0","1","1","1","1","●","1",
            "1","1","1","●","●","●","1",
            "0","0","1","●","1","0","1",
            "0","1","1","●","0","0","1",
            "0","0","●","●","1","1","1",
            "0","1","s","0","1","1","1"));
    Collection<String> input2 = new ArrayList<>(Arrays.asList("000f","0010","1010","0000","0s10"));
    Collection<String> output2 = new ArrayList<>(Arrays.asList(
            "0","●","●","f",
            "0","●","1","0",
            "1","●","1","0",
            "0","●","0","0",
            "0","s","1","0"));
    Logic log = new Logic();

    @Test
    public void logicOfGame1() throws Exception {
        assertEquals(output, log.logicOfGame((ArrayList<String>) input, 1, 2, 2 , 4));
        assertEquals(output1, log.logicOfGame((ArrayList<String>) input1, 5, 0, 2 , 6));
        assertEquals(output2, log.logicOfGame((ArrayList<String>) input2, 3, 0, 1 , 4));

    }

}
