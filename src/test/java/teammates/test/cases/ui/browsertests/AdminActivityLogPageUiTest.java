package teammates.test.cases.ui.browsertests;

import static org.testng.AssertJUnit.assertEquals;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import teammates.common.util.Const;
import teammates.common.util.Url;
import teammates.test.pageobjects.AdminActivityLogPage;
import teammates.test.pageobjects.Browser;
import teammates.test.pageobjects.BrowserPool;

public class AdminActivityLogPageUiTest extends BaseUiTestCase {
    
    private static Browser browser;
    private static AdminActivityLogPage logPage;
       
    @BeforeClass
    public static void classSetup() throws Exception {
        printTestClassHeader();
        browser = BrowserPool.getBrowser();
    }
    
    @Test
    public void testAll(){
        
        testContent();
        testViewActionsLink();
        testInputValidation();
    }
    
    
    public void testContent() {
        
        ______TS("content: typical page");
        
        Url logPageUrl = createUrl(Const.ActionURIs.ADMIN_ACTIVITY_LOG_PAGE);
        logPage = loginAdminToPage(browser, logPageUrl, AdminActivityLogPage.class);
        logPage.verifyIsCorrectPage();
    }
    
    
    public void testViewActionsLink(){
        
        ______TS("Link: recent actions link");
        
        try {
            String expectedPersonInfo = logPage.getPersonInfoOfFirstEntry();      
            logPage.clickViewActionsButtonOfFirstEntry();
            String actualPersonInfo = logPage.getFilterBoxString();
            assertEqualsIfQueryStringNotEmpty(expectedPersonInfo, actualPersonInfo);            
        } catch (NoSuchElementException emptylogs) {
            /*
             * This can happen if this test is run right after the server is started.
             * In this case, no view actions can be done.
             */
        }
    }
    
    public void testInputValidation(){
        
        ______TS("invalid query format");
        
        logPage.fillQueryBoxWithText("this is a invalid query");
        logPage.clickSearchSubmitButton();
        
        assertEquals("Error with the query: Invalid format", logPage.getQueryMessage());
    }
    
    
    @AfterClass
    public static void classTearDown() throws Exception {
        BrowserPool.release(browser);
    }
    
    private void assertEqualsIfQueryStringNotEmpty(String expected, String actual) {
        String emptyQuery = "person:";
        if (!expected.equals(emptyQuery)) {
            assertEquals(expected, actual);
        }
    }
    
}
