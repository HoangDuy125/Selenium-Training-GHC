package session2.exercise3_1.tests;

import org.testng.annotations.Test;
import session2.exercise3_1.base.BaseTest;
import session2.exercise3_1.pages.AdvancedActionsPage;

public class AdvancedActionsTest extends BaseTest {

    @Test
    public void testShiftClick() {
        AdvancedActionsPage advPage = new AdvancedActionsPage(driver);
        advPage.shiftClickDoubleButton();
        System.out.println("Shift + Click performed on Double Click button");
    }

    @Test
    public void testCtrlClick() {
        AdvancedActionsPage advPage = new AdvancedActionsPage(driver);
        advPage.ctrlClickRightButton();
        System.out.println("Ctrl + Click performed on Right Click button");
    }

    @Test
    public void testShiftTyping() {
        AdvancedActionsPage advPage = new AdvancedActionsPage(driver);
        advPage.typeWithShortcut();
        System.out.println("Typed with Shift shortcut (uppercase)");
    }
}
