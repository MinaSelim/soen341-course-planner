import React, { Component } from 'react';
import CourseItem from './CourseItem';
import PropTypes from 'prop-types';

function Courses (props) {
 
      return props.courses.map((course) => (
          <CourseItem key={course.id} course={course} delCourse={props.delCourse} />
      ));
  
}

//PropTypes
Courses.propTypes = {
    courses: PropTypes.array.isRequired
}


export default Courses;
