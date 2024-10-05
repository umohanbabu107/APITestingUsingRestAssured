package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    public Logger logger;

    @BeforeClass
    public void loggerSetup(){
        this.logger = LogManager.getLogger(this.getClass());
    }

}
