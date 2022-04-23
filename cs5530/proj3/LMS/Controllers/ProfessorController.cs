using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

using LMS.Models.LMSModels;

using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace LMS.Controllers {
	[Authorize(Roles = "Professor")]
	public class ProfessorController : CommonController {
		public IActionResult Index() {
			return View();
		}

		public IActionResult Students(string subject, string num, string season, string year) {
			ViewData["subject"] = subject;
			ViewData["num"] = num;
			ViewData["season"] = season;
			ViewData["year"] = year;
			return View();
		}

		public IActionResult Class(string subject, string num, string season, string year) {
			ViewData["subject"] = subject;
			ViewData["num"] = num;
			ViewData["season"] = season;
			ViewData["year"] = year;
			return View();
		}

		public IActionResult Categories(string subject, string num, string season, string year) {
			ViewData["subject"] = subject;
			ViewData["num"] = num;
			ViewData["season"] = season;
			ViewData["year"] = year;
			return View();
		}

		public IActionResult CatAssignments(string subject, string num, string season, string year, string cat) {
			ViewData["subject"] = subject;
			ViewData["num"] = num;
			ViewData["season"] = season;
			ViewData["year"] = year;
			ViewData["cat"] = cat;
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

		public IActionResult Submissions(string subject, string num, string season, string year, string cat, string aname) {
			ViewData["subject"] = subject;
			ViewData["num"] = num;
			ViewData["season"] = season;
			ViewData["year"] = year;
			ViewData["cat"] = cat;
			ViewData["aname"] = aname;
			return View();
		}

		public IActionResult Grade(string subject, string num, string season, string year, string cat, string aname, string uid) {
			ViewData["subject"] = subject;
			ViewData["num"] = num;
			ViewData["season"] = season;
			ViewData["year"] = year;
			ViewData["cat"] = cat;
			ViewData["aname"] = aname;
			ViewData["uid"] = uid;
			return View();
		}

		/// <summary>
		/// Returns a JSON array of all the students in a class.
		/// Each object in the array should have the following fields:
		/// "fname" - first name
		/// "lname" - last name
		/// "uid" - user ID
		/// "dob" - date of birth
		/// "grade" - the student's grade in this class
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <returns>The JSON array</returns>
		public IActionResult GetStudentsInClass(string subject, int num, string season, int year) {
			var students =
				from enrollment in db.Enrolled
				join student in db.Students on enrollment.StudentId equals student.StudentId
				join classData in db.Classes on enrollment.ClassId equals classData.ClassId
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				where classData.SemesterSeason == season && classData.SemesterYear == year && department.Subject == subject
					&& course.Num == num
				select new {
					fname = student.FName,
					lname = student.LName,
					uid = student.UId,
					dob = student.Dob,
					grade = enrollment.Grade ?? "--"
				};

			return Json(students.ToArray());
		}



		/// <summary>
		/// Returns a JSON array with all the assignments in an assignment category for a class.
		/// If the "category" parameter is null, return all assignments in the class.
		/// Each object in the array should have the following fields:
		/// "aname" - The assignment name
		/// "cname" - The assignment category name.
		/// "due" - The due DateTime
		/// "submissions" - The number of submissions to the assignment
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="category">The name of the assignment category in the class, 
		/// or null to return assignments from all categories</param>
		/// <returns>The JSON array</returns>
		public IActionResult GetAssignmentsInCategory(string subject, int num, string season, int year, string category) {
			var assignments =
				from assignment in db.Assignments
				join assignmentCategory in db.AssignmentCategories
					on assignment.AssignmentCategoryId equals assignmentCategory.AssignmentCategoryId
				join classData in db.Classes on assignmentCategory.ClassId equals classData.ClassId
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				where department.Subject == subject && course.Num == num && classData.SemesterSeason == season
					&& classData.SemesterYear == year
					&& (category == null || assignmentCategory.Name == category)
				select new {
					aname = assignment.Name,
					cname = assignment.AssignmentCategory.Name,
					due = assignment.Due,
					submissions = (
							from submission in db.Submission
							where submission.Assignment.AssignmentId == assignment.AssignmentId
							select submission
						).Count()
				};

			return Json(assignments.ToArray());
		}


		/// <summary>
		/// Returns a JSON array of the assignment categories for a certain class.
		/// Each object in the array should have the folling fields:
		/// "name" - The category name
		/// "weight" - The category weight
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="category">The name of the assignment category in the class</param>
		/// <returns>The JSON array</returns>
		public IActionResult GetAssignmentCategories(string subject, int num, string season, int year) {
			var categories =
				from category in db.AssignmentCategories
				join classData in db.Classes on category.ClassId equals classData.ClassId
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				where department.Subject == subject && course.Num == num && classData.SemesterSeason == season
					&& classData.SemesterYear == year
				select new { name = category.Name, weight = category.Weight };

			return Json(categories.ToArray());
		}

		/// <summary>
		/// Creates a new assignment category for the specified class.
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="category">The new category name</param>
		/// <param name="catweight">The new category weight</param>
		/// <returns>A JSON object containing {success = true/false},
		///	false if an assignment category with the same name already exists in the same class.</returns>
		public IActionResult CreateAssignmentCategory(string subject, int num, string season, int year, string category, int catweight) {
			var classIds =
				from classData in db.Classes
				where classData.Course.Department.Subject == subject
					&& classData.Course.Num == num
					&& classData.SemesterSeason == season
					&& classData.SemesterYear == year
				select classData.ClassId;
			db.AssignmentCategories.Add(new AssignmentCategories {
				ClassId = classIds.First(),
				Name = category,
				Weight = (uint)catweight
			});

			try {
				db.SaveChanges();
			} catch {
				Debug.WriteLine("Error occurred while creating an assignment category.");
				return Json(new { success = false });
			}

			return Json(new { success = true });
		}

		/// <summary>
		/// Creates a new assignment for the given class and category.
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="category">The name of the assignment category in the class</param>
		/// <param name="asgname">The new assignment name</param>
		/// <param name="asgpoints">The max point value for the new assignment</param>
		/// <param name="asgdue">The due DateTime for the new assignment</param>
		/// <param name="asgcontents">The contents of the new assignment</param>
		/// <returns>A JSON object containing success = true/false,
		/// false if an assignment with the same name already exists in the same assignment category.</returns>
		public IActionResult CreateAssignment(string subject, int num, string season, int year, string category, string asgname, int asgpoints, DateTime asgdue, string asgcontents) {
			var assignmentCategoryIds =
				from assignmentCategory in db.AssignmentCategories
				where assignmentCategory.Class.Course.Department.Subject == subject
					&& assignmentCategory.Class.Course.Num == num
					&& assignmentCategory.Class.SemesterSeason == season
					&& assignmentCategory.Class.SemesterYear == year
					&& assignmentCategory.Name == category
				select assignmentCategory.AssignmentCategoryId;
			db.Assignments.Add(new Assignments {
				AssignmentCategoryId = assignmentCategoryIds.First(),
				Name = asgname,
				Points = (uint)asgpoints,
				Due = asgdue,
				Contents = asgcontents
			});

			try {
				db.SaveChanges();
			} catch {
				Debug.WriteLine("Error occurred while creating an assignment.");
				return Json(new { success = false });
			}

			var studentsInThisClass =
				from enrollment in db.Enrolled
				where enrollment.Class.Course.Department.Subject == subject
					&& enrollment.Class.Course.Num == num
					&& enrollment.Class.SemesterSeason == season
					&& enrollment.Class.SemesterYear == year
				select enrollment.Student;
			foreach (var student in studentsInThisClass) UpdateStudentGrade(subject, num, season, year, student.UId);

			return Json(new { success = true });
		}

		/// <summary>
		/// Gets a JSON array of all the submissions to a certain assignment.
		/// Each object in the array should have the following fields:
		/// "fname" - first name
		/// "lname" - last name
		/// "uid" - user ID
		/// "time" - DateTime of the submission
		/// "score" - The score given to the submission
		/// 
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="category">The name of the assignment category in the class</param>
		/// <param name="asgname">The name of the assignment</param>
		/// <returns>The JSON array</returns>
		public IActionResult GetSubmissionsToAssignment(string subject, int num, string season, int year, string category, string asgname) {
			var submissions =
				from submission in db.Submission
				where submission.Assignment.AssignmentCategory.Class.Course.Department.Subject
						== subject
					&& submission.Assignment.AssignmentCategory.Class.Course.Num == num
					&& submission.Assignment.AssignmentCategory.Class.SemesterSeason == season
					&& submission.Assignment.AssignmentCategory.Class.SemesterYear == year
					&& submission.Assignment.AssignmentCategory.Name == category
					&& submission.Assignment.Name == asgname
				select new {
					fname = submission.Student.FName,
					lname = submission.Student.LName,
					uid = submission.Student.UId,
					time = submission.Time,
					score = submission.Score
				};

			return Json(submissions.ToArray());
		}


		/// <summary>
		/// Set the score of an assignment submission
		/// </summary>
		/// <param name="subject">The course subject abbreviation</param>
		/// <param name="num">The course number</param>
		/// <param name="season">The season part of the semester for the class the assignment belongs to</param>
		/// <param name="year">The year part of the semester for the class the assignment belongs to</param>
		/// <param name="category">The name of the assignment category in the class</param>
		/// <param name="asgname">The name of the assignment</param>
		/// <param name="uid">The uid of the student who's submission is being graded</param>
		/// <param name="score">The new score for the submission</param>
		/// <returns>A JSON object containing success = true/false</returns>
		public IActionResult GradeSubmission(string subject, int num, string season, int year, string category, string asgname, string uid, int score) {
			var submissions =
				from submission in db.Submission
				where submission.Assignment.AssignmentCategory.Class.Course.Department.Subject
						== subject
					&& submission.Assignment.AssignmentCategory.Class.Course.Num == num
					&& submission.Assignment.AssignmentCategory.Class.SemesterYear == year
					&& submission.Assignment.AssignmentCategory.Class.SemesterSeason == season
					&& submission.Assignment.AssignmentCategory.Name == category
					&& submission.Assignment.Name == asgname
					&& submission.Student.UId == uid
				select submission;
			var targetSubmission = submissions.First();
			targetSubmission.Score = (uint)score;

			try {
				db.SaveChanges();
			} catch {
				Debug.WriteLine("Error occurred while grading a submission.");
				return Json(new { success = false });
			}

			UpdateStudentGrade(subject, num, season, year, uid);

			return Json(new { success = true });
		}

		private void UpdateStudentGrade(string subject, int num, string season, int year, string uid) {
			//If a student does not have a submission for an assignment, the score for that assignment is treated as 0.
			//If an AssignmentCategory does not have any assignments, it is not included in the calculation.
			var assignmentCategories =
				from assignmentCategory in db.AssignmentCategories
				where assignmentCategory.Class.Course.Department.Subject == subject
					&& assignmentCategory.Class.Course.Num == num
					&& assignmentCategory.Class.SemesterYear == year
					&& assignmentCategory.Class.SemesterSeason == season
				select assignmentCategory;
			//For each non - empty category in the class:
			double totalWeightedProportions = 0;
			foreach (var assignmentCategory in assignmentCategories) {
				var assignments =
					from assignment in db.Assignments
					where assignment.AssignmentCategoryId == assignmentCategory.AssignmentCategoryId
					select assignment;
				if (assignments.Count() == 0) continue;
				//Calculate the percentage of (total points earned / total max points) of all assignments in the category.
				uint totalPointsEarned = 0;
				uint totalMaxPoints = 0;
				foreach (var assignment in assignments) {
					totalMaxPoints += assignment.Points;
					var targetSubmission =
						(from submission in db.Submission
						 where submission.AssignmentId == assignment.AssignmentId
							 && submission.Student.UId == uid
						 select submission).FirstOrDefault();
					if (targetSubmission != null) totalPointsEarned += (uint)targetSubmission.Score;
				}
				double proportion = totalPointsEarned / (double)totalMaxPoints;
				//Multiply the percentage by the category weight.
				double weightedProportion = proportion * assignmentCategory.Weight / 100.0;
				totalWeightedProportions += weightedProportion;
			}
			//Compute the total of all scaled category totals from the previous step.
			//Compute the scaling factor to make all category weights add up to 100%. This scaling factor is 100 / (sum of all category weights).
			uint totalCategoryWeights = 0;
			foreach (var assignmentCategory in assignmentCategories)
				totalCategoryWeights += assignmentCategory.Weight;
			double scalingFactor = 100.0 / totalCategoryWeights;
			//Multiply the total score you computed in step 4 by the scaling factor you computed in step 5.
			//This is the total percentage the student earned in the class.
			double totalScaledProportion = totalWeightedProportions * scalingFactor;
			//Convert the class percentage to a letter grade using the scale found in our class syllabus.
			string grade = GetGradeByProportion(totalScaledProportion);

			var targetEnrollment = (
					from enrollment in db.Enrolled
					where enrollment.Class.Course.Department.Subject == subject
						&& enrollment.Class.Course.Num == num
						&& enrollment.Class.SemesterSeason == season
						&& enrollment.Class.SemesterYear == year
						&& enrollment.Student.UId == uid
					select enrollment
				).First();
			targetEnrollment.Grade = grade;

			try {
				db.SaveChanges();
			} catch {
				Debug.WriteLine("Error occurred while updating a student's grade.");
			}
		}

		private string GetGradeByProportion(double proportion) {
			if (proportion >= 0.93) return "A";
			if (proportion >= 0.9) return "A-";
			if (proportion >= 0.87) return "B+";
			if (proportion >= 0.83) return "B";
			if (proportion >= 0.8) return "B-";
			if (proportion >= 0.77) return "C+";
			if (proportion >= 0.73) return "C";
			if (proportion >= 0.70) return "C-";
			if (proportion >= 0.67) return "D+";
			if (proportion >= 0.63) return "D";
			if (proportion >= 0.6) return "D-";
			return "E";
		}

		/// <summary>
		/// Returns a JSON array of the classes taught by the specified professor
		/// Each object in the array should have the following fields:
		/// "subject" - The subject abbreviation of the class (such as "CS")
		/// "number" - The course number (such as 5530)
		/// "name" - The course name
		/// "season" - The season part of the semester in which the class is taught
		/// "year" - The year part of the semester in which the class is taught
		/// </summary>
		/// <param name="uid">The professor's uid</param>
		/// <returns>The JSON array</returns>
		public IActionResult GetMyClasses(string uid) {
			var classes =
				from classData in db.Classes
				join course in db.Courses on classData.CourseId equals course.CourseId
				join department in db.Departments on course.DepartmentId equals department.DepartmentId
				join professor in db.Professors on classData.ProfessorId equals professor.ProfessorId
				where professor.UId == uid
				select new {
					subject = department.Subject,
					number = course.Num,
					name = course.Name,
					season = classData.SemesterSeason,
					year = classData.SemesterYear
				};

			return Json(classes.ToArray());
		}

	}
}