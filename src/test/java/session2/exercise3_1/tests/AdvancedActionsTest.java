package session2.exercise3_1.tests;

import org.testng.annotations.Test;
import session2.exercise3_1.base.BaseTest;
import session2.exercise3_1.pages.AdvancedActionsPage;

public class AdvancedActionsTest extends BaseTest {
    @Test
    public void testKeyboardShortcuts() {
        AdvancedActionsPage page = new AdvancedActionsPage(driver);

        // Shift + Click
        page.shiftClickDoubleButton();
        System.out.println("Shift + Click performed on Double Click button");

        // Ctrl + Click
        page.ctrlClickRightButton();
        System.out.println("Ctrl + Click performed on Right Click button");

        // Shift + Typing
        page.typeWithShortcut();
        System.out.println("Typed with Shift shortcut (uppercase)");
    }
}
