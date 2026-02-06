package session2.exercise3_1.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import session2.exercise3_1.base.BaseTest;
import session2.exercise3_1.pages.ButtonsPage;
import session2.exercise3_1.pages.AdvancedActionsPage;

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

        page.dragAndDrop();
        Assert.assertEquals(page.getDropMessage(), "Dropped!");
    }

    @Test
    public void testAdvancedActions() {
        AdvancedActionsPage advPage = new AdvancedActionsPage(driver);

        advPage.shiftClickDoubleButton();
        System.out.println("Shift + Click performed");

        advPage.ctrlClickRightButton();
        System.out.println("Ctrl + Click performed");

        advPage.typeWithShortcut();
        System.out.println("Typed with Shift shortcut (uppercase)");
    }
}
