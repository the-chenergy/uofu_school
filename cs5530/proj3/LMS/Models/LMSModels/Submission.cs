using System;
using System.Collections.Generic;

namespace LMS.Models.LMSModels
{
    public partial class Submission
    {
        public uint? Score { get; set; }
        public string Contents { get; set; }
        public DateTime Time { get; set; }
        public int StudentId { get; set; }
        public int AssignmentId { get; set; }
        public int SubmissionId { get; set; }

        public virtual Assignments Assignment { get; set; }
        public virtual Students Student { get; set; }
    }
}
