﻿using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

using LMS.Models.LMSModels;

using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace LMS.Controllers
{
    [Authorize(Roles = "Administrator")]
    public class AdministratorController : CommonController
    {
        public IActionResult Index()
        {
            return View();
        }

        public IActionResult Department(string subject)
        {
            ViewData["subject"] = subject;
            return View();
        }

        public IActionResult Course(string subject, string num)
        {
            ViewData["subject"] = subject;
            ViewData["num"] = num;
            return View();
        }

        /*******Begin code to modify********/

        /// <summary>
        /// Returns a JSON array of all the courses in the given department.
        /// Each object in the array should have the following fields:
        /// "number" - The course number (as in 5530)
        /// "name" - The course name (as in "Database Systems")
        /// </summary>
        /// <param name="subject">The department subject abbreviation (as in "CS")</param>
        /// <returns>The JSON result</returns>
        public IActionResult GetCourses(string subject)
        {
            var courses =
                from course in db.Courses
                join department in db.Departments
                    on course.DepartmentId equals department.DepartmentId
                where department.Subject == subject
                select new { number = course.Num, name = course.Name };

            return Json(courses.ToArray());
        }





        /// <summary>
        /// Returns a JSON array of all the professors working in a given department.
        /// Each object in the array should have the following fields:
        /// "lname" - The professor's last name
        /// "fname" - The professor's first name
        /// "uid" - The professor's uid
        /// </summary>
        /// <param name="subject">The department subject abbreviation</param>
        /// <returns>The JSON result</returns>
        public IActionResult GetProfessors(string subject)
        {

            var professors =
                    from professor in db.Professors
                    join department in db.Departments on professor.DepartmentId equals department.DepartmentId
                    where department.Subject == subject
                    select new { lname = professor.LName, fname = professor.FName, uid = professor.UId };


            return Json(professors.ToArray());
        }



        /// <summary>
        /// Creates a course.
        /// A course is uniquely identified by its number + the subject to which it belongs
        /// </summary>
        /// <param name="subject">The subject abbreviation for the department in which the course will be added</param>
        /// <param name="number">The course number</param>
        /// <param name="name">The course name</param>
        /// <returns>A JSON object containing {success = true/false},
        /// false if the Course already exists.</returns>
        public IActionResult CreateCourse(string subject, int number, string name)
        {
            Courses course = new Courses
            {
                Name = name,
                Num = number,
                DepartmentId = GetDepartmentIdBySubjectAbbrev(subject),
            };
            try
            {
                db.Courses.Add(course);
                db.SaveChanges();
            }
            catch
            {
                Debug.WriteLine("Error occurred while creating new course.");
                return Json(new { success = false });
            }
            return Json(new { success = true });
        }



        /// <summary>
        /// Creates a class offering of a given course.
        /// </summary>
        /// <param name="subject">The department subject abbreviation</param>
        /// <param name="number">The course number</param>
        /// <param name="season">The season part of the semester</param>
        /// <param name="year">The year part of the semester</param>
        /// <param name="start">The start time</param>
        /// <param name="end">The end time</param>
        /// <param name="location">The location</param>
        /// <param name="instructor">The uid of the professor</param>
        /// <returns>A JSON object containing {success = true/false}. 
        /// false if another class occupies the same location during any time 
        /// within the start-end range in the same semester, or if there is already
        /// a Class offering of the same Course in the same Semester.</returns>
        public IActionResult CreateClass(string subject, int number,
            string season, int year, DateTime start, DateTime end,
            string location, string instructor)
        {
            Classes classData = new Classes
            {
                SemesterYear = (uint)year,
                SemesterSeason = season,
                Loc = location,
                Start = start.TimeOfDay,
                End = end.TimeOfDay,
                CourseId = GetCourseIdBySubjectAndNumber(subject, number),
                ProfessorId = GetProfessorIDByUid(instructor),
            };
            try
            {
                db.Classes.Add(classData);
                db.SaveChanges();
            }
            catch
            {
                Debug.WriteLine("Error occurred while creating new class.");
                return Json(new { success = false });
            }
            return Json(new { success = true });

        }


        /*******Helper Methods********/
        private int GetDepartmentIdBySubjectAbbrev(string subjectAbbrev)
        {
            var departmentIds =
                from department in db.Departments
                where department.Subject == subjectAbbrev
                select department.DepartmentId;

            return departmentIds.First();
        }

        private int GetProfessorIDByUid(string uID)
        {
            var professorIds =
                from professor in db.Professors
                where professor.UId == uID
                select professor.ProfessorId;

            return professorIds.First();
        }

        private int GetCourseIdBySubjectAndNumber(string subject, int number)
        {
            var courseIds =
                from course in db.Courses
                join department in db.Departments on
                course.DepartmentId equals department.DepartmentId
                where course.Num == number && department.Subject == subject
                select course.CourseId;

            return courseIds.First();
        }
    }
}