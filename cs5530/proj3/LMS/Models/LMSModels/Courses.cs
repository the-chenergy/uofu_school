using System;
using System.Collections.Generic;

namespace LMS.Models.LMSModels
{
    public partial class Courses
    {
        public Courses()
        {
            Classes = new HashSet<Classes>();
        }

        public string Name { get; set; }
        public int Num { get; set; }
        public int DepartmentId { get; set; }
        public int CourseId { get; set; }

        public virtual Departments Department { get; set; }
        public virtual ICollection<Classes> Classes { get; set; }
    }
}
