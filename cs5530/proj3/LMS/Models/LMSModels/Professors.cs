using System;
using System.Collections.Generic;

namespace LMS.Models.LMSModels
{
    public partial class Professors
    {
        public Professors()
        {
            Classes = new HashSet<Classes>();
        }

        public string UId { get; set; }
        public string FName { get; set; }
        public string LName { get; set; }
        public DateTime Dob { get; set; }
        public int ProfessorId { get; set; }
        public int DepartmentId { get; set; }

        public virtual Departments Department { get; set; }
        public virtual ICollection<Classes> Classes { get; set; }
    }
}
