using System;
using System.Collections.Generic;

namespace LMS.Models.LMSModels
{
    public partial class Enrolled
    {
        public string Grade { get; set; }
        public int StudentId { get; set; }
        public int ClassId { get; set; }
        public int EnrolledId { get; set; }

        public virtual Classes Class { get; set; }
        public virtual Students Student { get; set; }
    }
}
