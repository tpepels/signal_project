import data_management.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CriticalThresholdAlertTest.class,
        HypotensiveHypoxemiaAlertTest.class,
        RapidDropAlertTest.class,
        LowSaturationAlertTest.class,
        TrendAlertTest.class,
        DataStorageTest.class
})
public class TestSuite {
}
