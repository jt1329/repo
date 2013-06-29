package teammates.test.cases.ui;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import teammates.common.Common;
import teammates.common.datatransfer.DataBundle;
import teammates.common.datatransfer.FeedbackSessionAttributes;
import teammates.logic.FeedbackSessionsLogic;
import teammates.ui.controller.ControllerServlet;

public class InstructorFeedbackDeleteActionTest extends BaseActionTest {

	DataBundle dataBundle;
	
	@BeforeClass
	public static void classSetUp() throws Exception {
		printTestClassHeader();
		URI = Common.PAGE_INSTRUCTOR_FEEDBACK_DELETE;
		sr.registerServlet(URI, ControllerServlet.class.getName());
	}

	@BeforeMethod
	public void caseSetUp() throws Exception {
		dataBundle = getTypicalDataBundle();
		restoreTypicalDataInDatastore();
	}
	
	@Test
	public void testAccessControl() throws Exception{
		
		FeedbackSessionAttributes fs = dataBundle.feedbackSessions.get("session1InCourse1");
		
		String[] submissionParams = new String[]{
				Common.PARAM_COURSE_ID, fs.courseId,
				Common.PARAM_FEEDBACK_SESSION_NAME, fs.feedbackSessionName,
		};
		
		verifyUnaccessibleWithoutLogin(submissionParams);
		verifyUnaccessibleForUnregisteredUsers(submissionParams);
		verifyUnaccessibleForStudents(submissionParams);
		verifyUnaccessibleForInstructorsOfOtherCourses(submissionParams);
		verifyAccessibleForInstructorsOfTheSameCourse(submissionParams);
		
		//recreate the entity
		FeedbackSessionsLogic.inst().createFeedbackSession(fs);
		verifyAccessibleForAdminToMasqueradeAsInstructor(submissionParams);		
	}
	
	@Test
	public void testExecuteAndPostProcess() throws Exception{
		
		//TODO: implement this
	}
	
}