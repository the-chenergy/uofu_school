using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

using LMS.Models.LMSModels;

using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace LMS.Controllers {
	[Authorize(Roles = "Student")]
	public class StudentController : CommonController {

		public IActionResult Index() {
			return View();
		}

		public IActionResult Catalog() {
			return View();
		}

		public IActionResult Class(string subject, string num, string season, string year) {
			ViewData["subject"] = subject;
			ViewData["num"] = num;
			ViewData["season"] = season;
			ViewData["year"] = year;
			return View();
		}

		public IActionResult Assignment(string subject, string num, string season, string year, string cat, string aname) {
			ViewData["subject"] = subject;
			ViewData["num"] = num;
			ViewData["season"] = season;
			ViewData["year"] = year;
			ViewData["cat"] = cat;
			ViewData["aname"] = aname;
			return View();
		}


		public IActionResult ClassListings(string subject, string num) {
			System.Diagnostics.Debug.WriteLine(subject + num);
			ViewData["subject"] = subject;
			ViewData["num"] = num;
			return View();
		}


		/*******Begin code to modify********/

		/// <summary>
		/// Returns a JSON array of the classes the given student is enrolled in.
		/// Each object in the array should have the following fields:
		/// "subject" - The subject abbreviation of the class (such as "CS")
		/// "number" - The course number (such as 5530)
		/// "name" - The course name
		/// "season" - The season part of the semester
		/// "year" - The year part of the semester
		/// "grade" - The grade earned in the class, or "--" if one hasn't been assigned
		/// </summary>
		/// <param name="uid">The uid of the student</param>
		/// <returns>The JSON array</returns>
		public IActionResult GetMyClasses(string uid) {
			var classes =
				from classData in db.Classes
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				join enrollment in db.Enrolled on classData.ClassId equals enrollment.ClassId
				join student in db.Students on enrollment.StudentId equals student.StudentId
				where student.UId == uid
				select new {
					subject = department.Subject,
					number = course.Num,
					name = course.Name,
					season = classData.SemesterSeason,
					year = classData.SemesterYear,
					grade = enrollment.Grade ?? "--"
				};

			return Json(classes.ToArray());
		}

		/// <summary>
		/// Returns a JSON array of all the assignments in the given class that the given student is enrolled in.
		/// Each object in the array should have the following fields:
		/// "aname" - The assignment name
		/// "cname" - The category name that the assignment belongs to
		/// "due" - The due Date/Time
		/// "score" - The score earned by the student, or null if the student has not submitted to this assignment.
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="uid"></param>
		/// <returns>The JSON array</returns>
		public IActionResult GetAssignmentsInClass(string subject, int num, string season, int year, string uid) {
			var targetSubmissions =
				from assignment in db.Assignments
				join category in db.AssignmentCategories
					on assignment.AssignmentCategoryId equals category.AssignmentCategoryId
				join classData in db.Classes on category.ClassId equals classData.ClassId
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				join enrollment in db.Enrolled on classData.ClassId equals enrollment.ClassId
				join student in db.Students on enrollment.StudentId equals student.StudentId
				join submission in db.Submission on assignment.AssignmentId equals submission.AssignmentId
				where department.Subject == subject && course.Num == num && classData.SemesterSeason == season
					&& classData.SemesterYear == year && student.UId == uid && submission.StudentId == student.StudentId
				select submission;
			var targetSubmission = targetSubmissions.FirstOrDefault();

			var assignments =
				from assignment in db.Assignments
				join category in db.AssignmentCategories
					on assignment.AssignmentCategoryId equals category.AssignmentCategoryId
				join classData in db.Classes on category.ClassId equals classData.ClassId
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				join enrollment in db.Enrolled on classData.ClassId equals enrollment.ClassId
				join student in db.Students on enrollment.StudentId equals student.StudentId
				where department.Subject == subject && course.Num == num && classData.SemesterSeason == season
					&& classData.SemesterYear == year && student.UId == uid
				select new {
					aname = assignment.Name,
					cname = category.Name,
					due = assignment.Due,
					score = targetSubmission == null ? null : (int?)targetSubmission.Score
				};

			return Json(assignments.ToArray());
		}



		/// <summary>
		/// Adds a submission to the given assignment for the given student
		/// The submission should use the current time as its DateTime
		/// You can get the current time with DateTime.Now
		/// The score of the submission should start as 0 until a Professor grades it
		/// If a Student submits to an assignment again, it should replace the submission contents
		/// and the submission time (the score should remain the same).
		/// Does *not* automatically reject late submissions.
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="categoryName">The name of the assignment category in the class</param>
		/// <param name="asgname">The new assignment name</param>
		/// <param name="uid">The student submitting the assignment</param>
		/// <param name="contents">The text contents of the student's submission</param>
		/// <returns>A JSON object containing {success = true/false}.</returns>
		public IActionResult SubmitAssignmentText(string subject, int num, string season, int year,
				string category, string asgname, string uid, string contents) {
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

			if (targetSubmission is null) {
				db.Submission.Add(new Submission {
					AssignmentId = targetAssignment.AssignmentId,
					StudentId = targetStudent.StudentId,
					Time = DateTime.Now,
					Score = 0,
					Contents = contents
				});
			} else {
				targetSubmission.Time = DateTime.Now;
				targetSubmission.Contents = contents;
			}

			try {
				db.SaveChanges();
			} catch {
				Debug.WriteLine("Error occurred while submitting an assignment.");
				return Json(new { success = false });
			}

			return Json(new { success = true });
		}


		/// <summary>
		/// Enrolls a student in a class.
		/// </summary>
		/// <param name="subject">The department subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester</param>
		/// <param name="year">The year part of the semester</param>
		/// <param name="uid">The uid of the student</param>
		/// <returns>A JSON object containing {success = {true/false},
		/// false if the student is already enrolled in the Class.</returns>
		public IActionResult Enroll(string subject, int num, string season, int year, string uid) {
			var targetClasses =
				from classData in db.Classes
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				where department.Subject == subject && course.Num == num && classData.SemesterSeason == season
					&& classData.SemesterYear == year
				select classData;
			var targetClass = targetClasses.First();

			var targetStudents =
				from student in db.Students
				where student.UId == uid
				select student;
			var targetStudent = targetStudents.First();

			db.Enrolled.Add(new Enrolled {
				ClassId = targetClass.ClassId,
				StudentId = targetStudent.StudentId
			});

			try {
				db.SaveChanges();
			} catch {
				Debug.WriteLine("Error occurred while enrolling.");
				return Json(new { success = false });
			}

			return Json(new { success = true });
		}



		/// <summary>
		/// Calculates a student's GPA
		/// A student's GPA is determined by the grade-point representation of the average grade in all their classes.
		/// Assume all classes are 4 credit hours.
		/// If a student does not have a grade in a class ("--"), that class is not counted in the average.
		/// If a student does not have any grades, they have a GPA of 0.0.
		/// Otherwise, the point-value of a letter grade is determined by the table on this page:
		/// https://advising.utah.edu/academic-standards/gpa-calculator-new.php
		/// </summary>
		/// <param name="uid">The uid of the student</param>
		/// <returns>A JSON object containing a single field called "gpa" with the number value</returns>
		public IActionResult GetGPA(string uid) {

			return Json(null);
		}

		/*******End code to modify********/

	}
}