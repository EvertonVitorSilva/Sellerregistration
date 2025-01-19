package db;

import java.io.Serial;

public class DbExeception extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public DbExeception(String msg){
        super(msg);
    }
}
