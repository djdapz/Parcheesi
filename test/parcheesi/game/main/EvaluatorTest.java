package parcheesi.game.main;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by devondapuzzo on 5/2/17.
 */
public class EvaluatorTest {
    @Test
    public void runTests() throws Exception {
        //every
        Evaluator eval = new Evaluator();
        assertNotNull(eval.runTests(100));
    }

}