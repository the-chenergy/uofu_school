using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace LMS.Models.LMSModels
{
    public partial class Team120LMSContext : DbContext
    {
        public Team120LMSContext()
        {
        }

        public Team120LMSContext(DbContextOptions<Team120LMSContext> options)
            : base(options)
        {
        }

        public virtual DbSet<Administrators> Administrators { get; set; }
        public virtual DbSet<AssignmentCategories> AssignmentCategories { get; set; }
        public virtual DbSet<Assignments> Assignments { get; set; }
        public virtual DbSet<Classes> Classes { get; set; }
        public virtual DbSet<Courses> Courses { get; set; }
        public virtual DbSet<Departments> Departments { get; set; }
        public virtual DbSet<Enrolled> Enrolled { get; set; }
        public virtual DbSet<Professors> Professors { get; set; }
        public virtual DbSet<Students> Students { get; set; }
        public virtual DbSet<Submission> Submission { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseMySql("Server=atr.eng.utah.edu;User Id=u1172983;Password=a;Database=Team120LMS");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Administrators>(entity =>
            {
                entity.HasKey(e => e.AdministratorId)
                    .HasName("PRIMARY");

                entity.HasIndex(e => e.UId)
                    .HasName("uID");

                entity.Property(e => e.AdministratorId)
                    .HasColumnName("AdministratorID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Dob)
                    .HasColumnName("DOB")
                    .HasColumnType("date");

                entity.Property(e => e.FName)
                    .IsRequired()
                    .HasColumnName("fName")
                    .HasColumnType("varchar(100)");

                entity.Property(e => e.LName)
                    .IsRequired()
                    .HasColumnName("lName")
                    .HasColumnType("varchar(100)");

                entity.Property(e => e.UId)
                    .IsRequired()
                    .HasColumnName("uID")
                    .HasColumnType("char(8)");
            });

            modelBuilder.Entity<AssignmentCategories>(entity =>
            {
                entity.HasKey(e => e.AssignmentCategoryId)
                    .HasName("PRIMARY");

                entity.HasIndex(e => new { e.ClassId, e.Name })
                    .HasName("ClassID_2")
                    .IsUnique();

                entity.Property(e => e.AssignmentCategoryId)
                    .HasColumnName("AssignmentCategoryID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.ClassId)
                    .HasColumnName("ClassID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnType("varchar(100)");

                entity.HasOne(d => d.Class)
                    .WithMany(p => p.AssignmentCategories)
                    .HasForeignKey(d => d.ClassId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("AssignmentCategories_ibfk_1");
            });

            modelBuilder.Entity<Assignments>(entity =>
            {
                entity.HasKey(e => e.AssignmentId)
                    .HasName("PRIMARY");

                entity.HasIndex(e => e.AssignmentCategoryId)
                    .HasName("AssignmentCategoryID");

                entity.HasIndex(e => new { e.Name, e.AssignmentCategoryId })
                    .HasName("Name")
                    .IsUnique();

                entity.Property(e => e.AssignmentId)
                    .HasColumnName("AssignmentID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.AssignmentCategoryId)
                    .HasColumnName("AssignmentCategoryID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Contents)
                    .IsRequired()
                    .HasColumnType("varchar(8192)");

                entity.Property(e => e.Due).HasColumnType("datetime");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnType("varchar(100)");

                entity.HasOne(d => d.AssignmentCategory)
                    .WithMany(p => p.Assignments)
                    .HasForeignKey(d => d.AssignmentCategoryId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Assignments_ibfk_1");
            });

            modelBuilder.Entity<Classes>(entity =>
            {
                entity.HasKey(e => e.ClassId)
                    .HasName("PRIMARY");

                entity.HasIndex(e => e.ProfessorId)
                    .HasName("ProfessorID");

                entity.HasIndex(e => new { e.CourseId, e.SemesterYear, e.SemesterSeason })
                    .HasName("CourseID_2")
                    .IsUnique();

                entity.Property(e => e.ClassId)
                    .HasColumnName("ClassID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.CourseId)
                    .HasColumnName("CourseID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.End).HasColumnType("time");

                entity.Property(e => e.Loc).HasColumnType("varchar(100)");

                entity.Property(e => e.ProfessorId)
                    .HasColumnName("ProfessorID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.SemesterSeason)
                    .IsRequired()
                    .HasColumnType("varchar(255)");

                entity.Property(e => e.Start).HasColumnType("time");

                entity.HasOne(d => d.Course)
                    .WithMany(p => p.Classes)
                    .HasForeignKey(d => d.CourseId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Classes_ibfk_1");

                entity.HasOne(d => d.Professor)
                    .WithMany(p => p.Classes)
                    .HasForeignKey(d => d.ProfessorId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Classes_ibfk_2");
            });

            modelBuilder.Entity<Courses>(entity =>
            {
                entity.HasKey(e => e.CourseId)
                    .HasName("PRIMARY");

                entity.HasIndex(e => e.DepartmentId)
                    .HasName("DepartmentID");

                entity.HasIndex(e => new { e.Num, e.DepartmentId })
                    .HasName("Num_2")
                    .IsUnique();

                entity.Property(e => e.CourseId)
                    .HasColumnName("CourseID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.DepartmentId)
                    .HasColumnName("DepartmentID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnType("varchar(100)");

                entity.Property(e => e.Num).HasColumnType("int(11)");

                entity.HasOne(d => d.Department)
                    .WithMany(p => p.Courses)
                    .HasForeignKey(d => d.DepartmentId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Courses_ibfk_1");
            });

            modelBuilder.Entity<Departments>(entity =>
            {
                entity.HasKey(e => e.DepartmentId)
                    .HasName("PRIMARY");

                entity.Property(e => e.DepartmentId)
                    .HasColumnName("DepartmentID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Name)
                    .IsRequired()
                    .HasColumnType("varchar(100)");

                entity.Property(e => e.Subject)
                    .IsRequired()
                    .HasColumnType("varchar(4)");
            });

            modelBuilder.Entity<Enrolled>(entity =>
            {
                entity.HasIndex(e => e.ClassId)
                    .HasName("ClassID");

                entity.HasIndex(e => new { e.StudentId, e.ClassId })
                    .HasName("StudentID")
                    .IsUnique();

                entity.Property(e => e.EnrolledId)
                    .HasColumnName("EnrolledID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.ClassId)
                    .HasColumnName("ClassID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Grade).HasColumnType("varchar(2)");

                entity.Property(e => e.StudentId)
                    .HasColumnName("StudentID")
                    .HasColumnType("int(11)");

                entity.HasOne(d => d.Class)
                    .WithMany(p => p.Enrolled)
                    .HasForeignKey(d => d.ClassId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Enrolled_ibfk_2");

                entity.HasOne(d => d.Student)
                    .WithMany(p => p.Enrolled)
                    .HasForeignKey(d => d.StudentId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Enrolled_ibfk_1");
            });

            modelBuilder.Entity<Professors>(entity =>
            {
                entity.HasKey(e => e.ProfessorId)
                    .HasName("PRIMARY");

                entity.HasIndex(e => e.DepartmentId)
                    .HasName("DepartmentID");

                entity.HasIndex(e => e.UId)
                    .HasName("uID");

                entity.Property(e => e.ProfessorId)
                    .HasColumnName("ProfessorID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.DepartmentId)
                    .HasColumnName("DepartmentID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Dob)
                    .HasColumnName("DOB")
                    .HasColumnType("date");

                entity.Property(e => e.FName)
                    .IsRequired()
                    .HasColumnName("fName")
                    .HasColumnType("varchar(100)");

                entity.Property(e => e.LName)
                    .IsRequired()
                    .HasColumnName("lName")
                    .HasColumnType("varchar(100)");

                entity.Property(e => e.UId)
                    .IsRequired()
                    .HasColumnName("uID")
                    .HasColumnType("char(8)");

                entity.HasOne(d => d.Department)
                    .WithMany(p => p.Professors)
                    .HasForeignKey(d => d.DepartmentId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Professors_ibfk_1");
            });

            modelBuilder.Entity<Students>(entity =>
            {
                entity.HasKey(e => e.StudentId)
                    .HasName("PRIMARY");

                entity.HasIndex(e => e.DepartmentId)
                    .HasName("DepartmentID");

                entity.HasIndex(e => e.UId)
                    .HasName("uID");

                entity.Property(e => e.StudentId)
                    .HasColumnName("StudentID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.DepartmentId)
                    .HasColumnName("DepartmentID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Dob)
                    .HasColumnName("DOB")
                    .HasColumnType("date");

                entity.Property(e => e.FName)
                    .IsRequired()
                    .HasColumnName("fName")
                    .HasColumnType("varchar(100)");

                entity.Property(e => e.LName)
                    .IsRequired()
                    .HasColumnName("lName")
                    .HasColumnType("varchar(100)");

                entity.Property(e => e.UId)
                    .IsRequired()
                    .HasColumnName("uID")
                    .HasColumnType("char(8)");

                entity.HasOne(d => d.Department)
                    .WithMany(p => p.Students)
                    .HasForeignKey(d => d.DepartmentId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Students_ibfk_1");
            });

            modelBuilder.Entity<Submission>(entity =>
            {
                entity.HasIndex(e => e.AssignmentId)
                    .HasName("AssignmentID");

                entity.HasIndex(e => e.StudentId)
                    .HasName("StudentID");

                entity.Property(e => e.SubmissionId)
                    .HasColumnName("SubmissionID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.AssignmentId)
                    .HasColumnName("AssignmentID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Contents)
                    .IsRequired()
                    .HasColumnType("varchar(8192)");

                entity.Property(e => e.StudentId)
                    .HasColumnName("StudentID")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Time).HasColumnType("datetime");

                entity.HasOne(d => d.Assignment)
                    .WithMany(p => p.Submission)
                    .HasForeignKey(d => d.AssignmentId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Submission_ibfk_2");

                entity.HasOne(d => d.Student)
                    .WithMany(p => p.Submission)
                    .HasForeignKey(d => d.StudentId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("Submission_ibfk_1");
            });
        }
    }
}
