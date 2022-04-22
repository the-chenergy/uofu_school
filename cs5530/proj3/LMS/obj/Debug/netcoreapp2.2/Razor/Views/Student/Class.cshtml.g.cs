#pragma checksum "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "25d1a407920daded91b9faaa87d06f7e26390ce0"
// <auto-generated/>
#pragma warning disable 1591
[assembly: global::Microsoft.AspNetCore.Razor.Hosting.RazorCompiledItemAttribute(typeof(AspNetCore.Views_Student_Class), @"mvc.1.0.view", @"/Views/Student/Class.cshtml")]
[assembly:global::Microsoft.AspNetCore.Mvc.Razor.Compilation.RazorViewAttribute(@"/Views/Student/Class.cshtml", typeof(AspNetCore.Views_Student_Class))]
namespace AspNetCore
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Threading.Tasks;
    using Microsoft.AspNetCore.Mvc;
    using Microsoft.AspNetCore.Mvc.Rendering;
    using Microsoft.AspNetCore.Mvc.ViewFeatures;
#line 1 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\_ViewImports.cshtml"
using Microsoft.AspNetCore.Identity;

#line default
#line hidden
#line 2 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\_ViewImports.cshtml"
using LMS;

#line default
#line hidden
#line 3 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\_ViewImports.cshtml"
using LMS.Models;

#line default
#line hidden
#line 4 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\_ViewImports.cshtml"
using LMS.Models.AccountViewModels;

#line default
#line hidden
#line 5 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\_ViewImports.cshtml"
using LMS.Models.ManageViewModels;

#line default
#line hidden
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"25d1a407920daded91b9faaa87d06f7e26390ce0", @"/Views/Student/Class.cshtml")]
    [global::Microsoft.AspNetCore.Razor.Hosting.RazorSourceChecksumAttribute(@"SHA1", @"363c4fd446cecdc21217d95f921ea2b5901a3ca3", @"/Views/_ViewImports.cshtml")]
    public class Views_Student_Class : global::Microsoft.AspNetCore.Mvc.Razor.RazorPage<dynamic>
    {
        #pragma warning disable 1998
        public async override global::System.Threading.Tasks.Task ExecuteAsync()
        {
            BeginContext(0, 2, true);
            WriteLiteral("\r\n");
            EndContext();
#line 2 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
  
  ViewData["Title"] = "Class";
  Layout = "~/Views/Shared/StudentLayout.cshtml";

#line default
#line hidden
            BeginContext(92, 710, true);
            WriteLiteral(@"

<h4 id=""classname"">Class</h4>

<div id=""departmentDiv"" class=""col-md-12"">
  <div class=""panel panel-primary"">
    <div class=""panel-heading"">
      <h3 class=""panel-title"">Assignments</h3>
    </div>
    <div class=""panel-body"">
      <table id=""tblAssignments"" class=""table table-bordered table-striped table-responsive table-hover"">
        <thead>
          <tr>
            <th align=""left"" class=""productth"">Name</th>
            <th align=""left"" class=""productth"">Category</th>
            <th align=""left"" class=""productth"">Due</th>
            <th align=""left"" class=""productth"">Score</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>
</div>






");
            EndContext();
            DefineSection("Scripts", async() => {
                BeginContext(823, 418, true);
                WriteLiteral(@"
  <script type=""text/javascript"">

    LoadData();

    function PopulateTable(tbl, offerings) {
      var newBody = document.createElement(""tbody"");


      $.each(offerings, function (i, item) {
        var tr = document.createElement(""tr"");

        var td = document.createElement(""td"");
        var a = document.createElement(""a"");
        a.setAttribute(""href"", ""/Student/Assignment/?subject="" + '");
                EndContext();
                BeginContext(1242, 19, false);
#line 50 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                                                             Write(ViewData["subject"]);

#line default
#line hidden
                EndContext();
                BeginContext(1261, 15, true);
                WriteLiteral("\' + \"&num=\" + \'");
                EndContext();
                BeginContext(1277, 15, false);
#line 50 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                                                                                                Write(ViewData["num"]);

#line default
#line hidden
                EndContext();
                BeginContext(1292, 18, true);
                WriteLiteral("\' + \"&season=\" + \'");
                EndContext();
                BeginContext(1311, 18, false);
#line 50 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                                                                                                                                  Write(ViewData["season"]);

#line default
#line hidden
                EndContext();
                BeginContext(1329, 16, true);
                WriteLiteral("\' + \"&year=\" + \'");
                EndContext();
                BeginContext(1346, 16, false);
#line 50 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                                                                                                                                                                     Write(ViewData["year"]);

#line default
#line hidden
                EndContext();
                BeginContext(1362, 880, true);
                WriteLiteral(@"' + ""&cat="" + item.cname + ""&aname="" + item.aname);
        a.appendChild(document.createTextNode(item.aname));
        td.appendChild(a);
        tr.appendChild(td);


        var td = document.createElement(""td"");
        td.appendChild(document.createTextNode(item.cname));
        tr.appendChild(td);

        var td = document.createElement(""td"");
        td.appendChild(document.createTextNode(item.due));
        tr.appendChild(td);

        var td = document.createElement(""td"");
        if (item.score != null) {
          td.appendChild(document.createTextNode(item.score));
        }
        else {
          td.appendChild(document.createTextNode(""--""));
        }

        tr.appendChild(td);

        newBody.appendChild(tr);
      });

      tbl.appendChild(newBody);

    }

    function LoadData() {

      classname.innerText = '");
                EndContext();
                BeginContext(2243, 19, false);
#line 83 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                        Write(ViewData["subject"]);

#line default
#line hidden
                EndContext();
                BeginContext(2262, 1, true);
                WriteLiteral(" ");
                EndContext();
                BeginContext(2264, 15, false);
#line 83 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                                             Write(ViewData["num"]);

#line default
#line hidden
                EndContext();
                BeginContext(2279, 1, true);
                WriteLiteral(" ");
                EndContext();
                BeginContext(2281, 18, false);
#line 83 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                                                              Write(ViewData["season"]);

#line default
#line hidden
                EndContext();
                BeginContext(2299, 1, true);
                WriteLiteral(" ");
                EndContext();
                BeginContext(2301, 16, false);
#line 83 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                                                                                  Write(ViewData["year"]);

#line default
#line hidden
                EndContext();
                BeginContext(2317, 121, true);
                WriteLiteral("\';\r\n\r\n      var tbl = document.getElementById(\"tblAssignments\");\r\n\r\n      $.ajax({\r\n        type: \'POST\',\r\n        url: \'");
                EndContext();
                BeginContext(2439, 46, false);
#line 89 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
         Write(Url.Action("GetAssignmentsInClass", "Student"));

#line default
#line hidden
                EndContext();
                BeginContext(2485, 68, true);
                WriteLiteral("\',\r\n        dataType: \'json\',\r\n        data: {\r\n          subject: \'");
                EndContext();
                BeginContext(2554, 19, false);
#line 92 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
               Write(ViewData["subject"]);

#line default
#line hidden
                EndContext();
                BeginContext(2573, 27, true);
                WriteLiteral("\',\r\n          num: Number(\'");
                EndContext();
                BeginContext(2601, 15, false);
#line 93 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                  Write(ViewData["num"]);

#line default
#line hidden
                EndContext();
                BeginContext(2616, 24, true);
                WriteLiteral("\'),\r\n          season: \'");
                EndContext();
                BeginContext(2641, 18, false);
#line 94 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
              Write(ViewData["season"]);

#line default
#line hidden
                EndContext();
                BeginContext(2659, 28, true);
                WriteLiteral("\',\r\n          year: Number(\'");
                EndContext();
                BeginContext(2688, 16, false);
#line 95 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
                   Write(ViewData["year"]);

#line default
#line hidden
                EndContext();
                BeginContext(2704, 21, true);
                WriteLiteral("\'),\r\n          uid: \'");
                EndContext();
                BeginContext(2726, 18, false);
#line 96 "E:\Academics\phase3\uofu_school\cs5530\proj3\LMS\Views\Student\Class.cshtml"
           Write(User.Identity.Name);

#line default
#line hidden
                EndContext();
                BeginContext(2744, 314, true);
                WriteLiteral(@"'},
        success: function (data, status) {
          //alert(JSON.stringify(data));
          PopulateTable(tbl, data);

        },
          error: function (ex) {
              alert(""GetAssignmentsInClass controller did not return successfully"");
        }
        });


    }

  </script>

");
                EndContext();
            }
            );
            BeginContext(3061, 4, true);
            WriteLiteral("\r\n\r\n");
            EndContext();
        }
        #pragma warning restore 1998
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.ViewFeatures.IModelExpressionProvider ModelExpressionProvider { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IUrlHelper Url { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.IViewComponentHelper Component { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IJsonHelper Json { get; private set; }
        [global::Microsoft.AspNetCore.Mvc.Razor.Internal.RazorInjectAttribute]
        public global::Microsoft.AspNetCore.Mvc.Rendering.IHtmlHelper<dynamic> Html { get; private set; }
    }
}
#pragma warning restore 1591
