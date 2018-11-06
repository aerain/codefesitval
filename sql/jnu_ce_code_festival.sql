/* user TABLE */
CREATE TABLE users(
  id VARCHAR(100) NOT NULL PRIMARY KEY,
  password VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  grade int(4),
  score_get INT(10) NOT NULL,
  first_bool BOOLEAN NOT NULL DEFAULT FALSE,
  second_bool BOOLEAN NOT NULL DEFAULT FALSE,
  third_bool BOOLEAN NOT NULL DEFAULT FALSE
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

/* problem TABLE */
CREATE TABLE problem(
  num  INT(10) NOT NULL PRIMARY KEY,
  title CHAR(255) NOT NULL,
  content TEXT(65535) NOT NULL,
  answer TEXT(65535) NOT NULL,
  testcase TEXT(65535) NOT NULL,
  answer_input TEXT(65535) NOT NULL
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

/* problem_one (1학년 문제 테이블)*/
CREATE TABLE problem_grade_one(
  grade int(4) NOT NULL PRIMARY KEY,
  problemNum INT(4) NOT NULL,
  title CHAR(255) NOT NULL,
  content TEXT(65535) NOT NULL,
  answer TEXT(65535) NOT NULL,
  testcase TEXT(65535) NOT NULL,
  answer_input TEXT(65535) NOT NULL
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

/* problem_two (2학년 문제 테이블)*/
CREATE TABLE problem_grade_two(
  grade int(4) NOT NULL PRIMARY KEY,
  problemNum INT(4) NOT NULL,
  title CHAR(255) NOT NULL,
  content TEXT(65535) NOT NULL,
  answer TEXT(65535) NOT NULL,
  testcase TEXT(65535) NOT NULL,
  answer_input TEXT(65535) NOT NULL
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

/* problem_three (3학년 문제 테이블)*/
CREATE TABLE problem_grade_three(
  grade int(4) NOT NULL PRIMARY KEY,
  problemNum INT(4) NOT NULL,
  title CHAR(255) NOT NULL,
  content TEXT(65535) NOT NULL,
  answer TEXT(65535) NOT NULL,
  testcase TEXT(65535) NOT NULL,
  answer_input TEXT(65535) NOT NULL
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

/* problem_four (4학년 문제 테이블)*/
CREATE TABLE problem_grade_four(
  grade int(4) NOT NULL PRIMARY KEY,
  problemNum INT(4) NOT NULL,
  title CHAR(255) NOT NULL,
  content TEXT(65535) NOT NULL,
  answer TEXT(65535) NOT NULL,
  testcase TEXT(65535) NOT NULL,
  answer_input TEXT(65535) NOT NULL
) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;