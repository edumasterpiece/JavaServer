
package JDBC;

import java.io.DataOutputStream;

public abstract class Service {
      
    DataOutputStream responseWriter;

    public Service(DataOutputStream responseWriter){
        this.responseWriter = responseWriter;
    }
    
    public abstract void doWork();
}

