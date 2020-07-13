package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.GetIDsSteps;


@RunWith(SerenityRunner.class)
public class GetIDsTest extends BasicTestClass {

    @Steps
    GetIDsSteps getIDsSteps;

    @Test
    public void getIDsTest() {
        getIDsSteps.getIDs(generatedAccessToken);
    }

    @Test
    public void getIDsWithParentTest() {
        getIDsSteps.getIDsWithParent(generatedAccessToken);
    }

    @Test
    public void getIDsFromChildTest() {
        getIDsSteps.getIDsFromChild(generatedAccessToken, randomParentID);
    }

    @Test
    public void getIDsWithNonexistentParentTest() {
        getIDsSteps.getIDsWithNonexistentParent(generatedAccessToken);
    }
}