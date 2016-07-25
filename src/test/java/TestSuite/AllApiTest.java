package TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ApiTests.UseCase1Test;
import ApiTests.UseCase2Test;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        UseCase1Test.class,
        UseCase2Test.class,
})
public class AllApiTest {
}