using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

using LMS.Models.LMSModels;

using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace LMS.Controllers {
	public class CommonController : Controller {

		/*******Begin code to modify********/

		// TODO: Uncomment and change 'X' after you have scaffoled


		protected Team120LMSContext db;

		public CommonController() {
			db = new Team120LMSContext();
		}


		/*
		 * WARNING: This is the quick and easy way to make the controller
		 *          use a different LibraryContext - good enough for our purposes.
		 *          The "right" way is through Dependency Injection via the constructor 
		 *          (look this up if interested).
		*/

		// TODO: Uncomment and change 'X' after you have scaffoled

		public void UseLMSContext(Team120LMSContext ctx) {
			db = ctx;
		}

		protected override void Dispose(bool disposing) {
			if (disposing) {
				db.Dispose();
			}
			base.Dispose(disposing);
		}

		/// <summary>
		/// Retrieve a JSON array of all departments from the database.
		/// Each object in the array should have a field called "name" and "subject",
		/// where "name" is the department name and "subject" is the subject abbreviation.
		/// </summary>
		/// <returns>The JSON array</returns>
		public IActionResult GetDepartments() {
			/*
			 using (Team120LibraryContext db = new Team120LibraryContext())
            {
                var query =
                from title in db.Titles
                join inventory in db.Inventory on title.Isbn equals inventory.Isbn
                join checkedOut in db.CheckedOut on inventory.Serial equals checkedOut.Serial where checkedOut.CardNum == card
                
                select new { title = title.Title, author = title.Author, serial = inventory.Serial};
                return Json(query.ToArray());

            }
			 */

			var query =
				from department in db.Departments
				select new { name = department.Name, subject = department.Subject };

			return Json(query.ToArray());


			// TODO: Do not return this hard-coded array.
			//return Json(new[] { new { name = "None", subject = "NONE" } });
		}



		/// <summary>
		/// Returns a JSON array representing the course catalog.
		/// Each object in the array should have the following fields:
		/// "subject": The subject abbreviation, (e.g. "CS")
		/// "dname": The department name, as in "Computer Science"
		/// "courses": An array of JSON objects representing the courses in the department.
		///            Each field in this inner-array should have the following fields:
		///            "number": The course number (e.g. 5530)
		///            "cname": The course name (e.g. "Database Systems")
		/// </summary>
		/// <returns>The JSON array</returns>
		public IActionResult GetCatalog() {
			var departments =
				from department in db.Departments
				select new {
					subject = department.Subject,
					dname = department.Name,
					courses =
						(from course in db.Courses
						 where course.DepartmentId == department.DepartmentId
						 select new { number = course.Num, cname = course.Name }).ToArray()
				};

			return Json(departments.ToArray());
		}

		/// <summary>
		/// Returns a JSON array of all class offerings of a specific course.
		/// Each object in the array should have the following fields:
		/// "season": the season part of the semester, such as "Fall"
		/// "year": the year part of the semester
		/// "location": the location of the class
		/// "start": the start time in format "hh:mm:ss"
		/// "end": the end time in format "hh:mm:ss"
		/// "fname": the first name of the professor
		/// "lname": the last name of the professor
		/// </summary>
		/// <param name="subject">The subject abbreviation, as in "CS"</param>
		/// <param name="number">The course number, as in 5530</param>
		/// <returns>The JSON array</returns>
		public IActionResult GetClassOfferings(string subject, int number) {
			Debug.Write("GetClassOfferings");
			var classes =
				from classData in db.Classes
				join course in db.Courses
					on classData.CourseId equals course.CourseId
				join department in db.Departments
					on course.DepartmentId equals department.DepartmentId
				join professor in db.Professors
					on classData.ProfessorId equals professor.ProfessorId
				where department.Subject == subject && course.Num == number
				select new {
					season = classData.SemesterSeason,
					year = classData.SemesterYear,
					location = classData.Loc,
					start = classData.Start,
					end = classData.End,
					fname = professor.FName,
					lname = professor.LName
				};

			return Json(classes.ToArray());
		}

		/// <summary>
		/// This method does NOT return JSON. It returns plain text (containing html).
		/// Use "return Content(...)" to return plain text.
		/// Returns the contents of an assignment.
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="category">The name of the assignment category in the class</param>
		/// <param name="asgname">The name of the assignment in the category</param>
		/// <returns>The assignment contents</returns>
		public IActionResult GetAssignmentContents(string subject, int num, string season, int year, string category, string asgname) {
			var targetContents =
				from assignment in db.Assignments
				join assignmentCategory in db.AssignmentCategories
					on assignment.AssignmentCategoryId equals assignmentCategory.AssignmentCategoryId
				join classData in db.Classes on assignmentCategory.ClassId equals classData.ClassId
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				where department.Subject == subject && course.Num == num && classData.SemesterSeason == season
					&& classData.SemesterYear == year && assignment.Name == asgname
					&& assignmentCategory.Name == category
				select assignment.Contents;
			return Content(targetContents.First());
		}


		/// <summary>
		/// This method does NOT return JSON. It returns plain text (containing html).
		/// Use "return Content(...)" to return plain text.
		/// Returns the contents of an assignment submission.
		/// Returns the empty string ("") if there is no submission.
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="category">The name of the assignment category in the class</param>
		/// <param name="asgname">The name of the assignment in the category</param>
		/// <param name="uid">The uid of the student who submitted it</param>
		/// <returns>The submission text</returns>
		public IActionResult GetSubmissionText(string subject, int num, string season, int year, string category, string asgname, string uid) {
			var targetStudents =
				from student in db.Students
				where student.UId == uid
				select student;
			var targetStudent = targetStudents.First();

			var targetAssignments =
				from assignment in db.Assignments
				join assignmentCategory in db.AssignmentCategories
					on assignment.AssignmentCategoryId equals assignmentCategory.AssignmentCategoryId
				join classData in db.Classes on assignmentCategory.ClassId equals classData.ClassId
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				where department.Subject == subject && course.Num == num && classData.SemesterSeason == season
					&& classData.SemesterYear == year && assignment.Name == asgname
					&& assignmentCategory.Name == category
				select assignment;
			var targetAssignment = targetAssignments.First();

			var targetSubmissions =
				from submission in db.Submission
				where submission.AssignmentId == targetAssignment.AssignmentId
					&& submission.StudentId == targetStudent.StudentId
				select submission;
			var targetSubmission = targetSubmissions.FirstOrDefault();

			return Content(targetSubmission.Contents);
		}


		/// <summary>
		/// Gets information about a user as a single JSON object.
		/// The object should have the following fields:
		/// "fname": the user's first name
		/// "lname": the user's last name
		/// "uid": the user's uid
		/// "department": (professors and students only) the name (such as "Computer Science") of the department for the user. 
		///               If the user is a Professor, this is the department they work in.
		///               If the user is a Student, this is the department they major in.    
		///               If the user is an Administrator, this field is not present in the returned JSON
		/// </summary>
		/// <param name="uid">The ID of the user</param>
		/// <returns>
		/// The user JSON object 
		/// or an object containing {success: false} if the user doesn't exist
		/// </returns>
		public IActionResult GetUser(string uid) {
			var targetStudents =
				from student in db.Students
				join department in db.Departments on student.DepartmentId equals department.DepartmentId
				where student.UId == uid
				select new {
					fname = student.FName,
					lname = student.LName,
					uid = student.UId,
					department = department.Name
				};
			var targetStudent = targetStudents.FirstOrDefault();

			if (!(targetStudent is null)) return Json(targetStudent);

			var targetProfessors =
				from professor in db.Professors
				join department in db.Departments on professor.DepartmentId equals department.DepartmentId
				where professor.UId == uid
				select new {
					fname = professor.FName,
					lname = professor.LName,
					uid = professor.UId,
					department = department.Name
				};
			var targetProfessor = targetProfessors.FirstOrDefault();

			if (!(targetProfessor is null)) return Json(targetProfessor);

			var targetAdministrators =
				from administrator in db.Administrators
				where administrator.UId == uid
				select new {
					fname = administrator.FName,
					lname = administrator.LName,
					uid = administrator.UId,
				};
			var targetAdministrator = targetAdministrators.FirstOrDefault();

			if (!(targetAdministrator is null)) return Json(targetAdministrator);

			return Json(new { success = false });
		}


		/*******End code to modify********/

	}
}