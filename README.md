# SoftwareEngineeringProject

Run the app.java to see the gui
It is contained here Main/src/main/java/Presentation/
Right now it has a login window and a courseList view window
userName: taxi password: taxi123

What is needed to be done?

* courseListView needs a viewCourseButton on its menu
  * when clicked the courseView it will present one of the following three panels student,professor, ta depending on accountType
* need to create courseViewPanel
  * student courseViewPanel is jsut an assignmentList panel, and a view grade button
  * ta courseVIewPanel is student courseViewPanel but contains a view Roster, grade button
  * prof got view Roster, grade Assignment, add Assignment, remove assignment
 * roster View Panel that shows professor, ta, students
* gradeView
  * student grade view only displays 1 list
  * ta grade view displays all grades + a enter Grade button
  * prof grade view displays all grades + enterGrade, clearGrade, modify grade button
* add Assignment Dialog
  * should ask for assignmentName, description. name should be unqiue within a course.
  
