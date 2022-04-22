﻿using System;
using System.Collections.Generic;

namespace LMS.Models.LMSModels
{
    public partial class Classes
    {
        public Classes()
        {
            AssignmentCategories = new HashSet<AssignmentCategories>();
            Enrolled = new HashSet<Enrolled>();
        }

        public uint SemesterYear { get; set; }
        public string SemesterSeason { get; set; }
        public string Loc { get; set; }
        public TimeSpan? Start { get; set; }
        public TimeSpan? End { get; set; }
        public int CourseId { get; set; }
        public int ClassId { get; set; }
        public int ProfessorId { get; set; }

        public virtual Courses Course { get; set; }
        public virtual Professors Professor { get; set; }
        public virtual ICollection<AssignmentCategories> AssignmentCategories { get; set; }
        public virtual ICollection<Enrolled> Enrolled { get; set; }
    }
}
