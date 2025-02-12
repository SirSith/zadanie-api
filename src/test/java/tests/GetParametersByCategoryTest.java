package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.GetParametersByCategorySteps;


@RunWith(SerenityRunner.class)
public class GetParametersByCategoryTest extends BasicTestClass{

    @Steps
    GetParametersByCategorySteps getParametersByCategorySteps;

    @Test
    public void getParametersByIDTest() {
        getParametersByCategorySteps.getParametersByID(generatedAccessToken,randomParentID);
    }

    @Test
    public void getParametersByNonexistentIDTest() {
        getParametersByCategorySteps.getParametersByNonexistentID(generatedAccessToken);
    }
}