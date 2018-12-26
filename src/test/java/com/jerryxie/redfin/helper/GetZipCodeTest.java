package com.jerryxie.redfin.helper;

import org.junit.Test;

public class GetZipCodeTest {
    @Test
    public void test() {
        BayareaZipCodeUtility util = new BayareaZipCodeUtility();
        System.out.println(util.getZipcodes());
        
    }
    
}
