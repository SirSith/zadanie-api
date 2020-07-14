package tests;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;
import steps.GetCategoriesByIDSteps;


@RunWith(SerenityRunner.class)
public class GetCategoriesByIDTest extends BasicTestClass{

    @Steps
    GetCategoriesByIDSteps getCategoriesByIDSteps;

    @Test
    public void getCategoryByIDTest() {getCategoriesByIDSteps.getCategoryByID(generatedAccessToken, randomParentID);}

    @Test
    public void getNonexistentCategoryTest() {getCategoriesByIDSteps.getNonexistentCategory(generatedAccessToken);}
}