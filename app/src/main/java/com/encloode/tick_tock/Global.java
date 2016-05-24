package com.encloode.tick_tock;

import android.content.Intent;
import android.view.View;

/**
 * Created by Riko Hamblin on 05/20/16.
 */
public class Global {

    static private EmployeeDatabase empDatabase = new EmployeeDatabase();
    static public int masterCode = 1234;

    static public EmployeeDatabase accessDatabase ()    {

       return empDatabase ;
    }

}
