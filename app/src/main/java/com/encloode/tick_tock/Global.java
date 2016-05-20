package com.encloode.tick_tock;

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
