using System;
using System.Collections.Generic;

namespace LMS.Models.LMSModels
{
    public partial class Assignments
    {
        public Assignments()
        {
            Submission = new HashSet<Submission>();
        }

        public string Name { get; set; }
        public uint Points { get; set; }
        public string Contents { get; set; }
        public DateTime Due { get; set; }
        public int AssignmentCategoryId { get; set; }
        public int AssignmentId { get; set; }

        public virtual AssignmentCategories AssignmentCategory { get; set; }
        public virtual ICollection<Submission> Submission { get; set; }
    }
}
