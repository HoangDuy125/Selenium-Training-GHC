package session2.exercise3_1.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import session2.exercise3_1.base.BaseTest;
import session2.exercise3_1.pages.ButtonsPage;

public class ButtonsTest extends BaseTest {
    @Test
    public void testAllActions() {
        ButtonsPage page = new ButtonsPage(driver);

        page.doubleClick();
        Assert.assertEquals(page.getDoubleClickMessage(), "You have done a double click");

        page.rightClick();
        Assert.assertEquals(page.getRightClickMessage(), "You have done a right click");

        page.dynamicClick();
        Assert.assertEquals(page.getDynamicClickMessage(), "You have done a dynamic click");
    }
}
