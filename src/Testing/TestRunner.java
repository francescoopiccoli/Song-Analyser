package Testing;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * TestRunner class is the runner class of the junit test
 * @author picc
 *
 */
public class TestRunner {

	public static void main(String[] args) {
		
		Result result = JUnitCore.runClasses(TestJUnit.class);
		for (Failure failure : result.getFailures()) {
			
			System.out.println(failure.toString());
		}
		
			System.out.println(result.wasSuccessful());
	}

}

