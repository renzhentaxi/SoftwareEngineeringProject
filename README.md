# SoftwareEngineeringProject

Run the app.java to see the gui
It is contained here Main/src/main/java/Presentation/

#Demo accounts

|AccountType |userName | password|
|:----------:|:-------:|:-------:|
| student    | stud    | stud    |
| ta         | ta      | ta      |
| professor  | prof    | prof    |


#How to add accounts?
first create an entry in accounts.json in Data\
then you can add it to various courses in courses.json
then just run the app once, it will add courses to the account automatically.

In order to login to the app, you must provide a userName + password entry in Data\passwordList


#Tests
- [x] user can login
- [x] user can logout at most of the screens
- [x] student/ta/prof can see all his courses
- [x] student/ta/prof can see all assignments for a given course
- [x] ta/prof can via roster
- [x] ta/prof can enter grade
- [x] prof can add/del assignment
- [x] prof can modify/clear grade
    