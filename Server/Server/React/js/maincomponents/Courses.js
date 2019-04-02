import React, { Component } from 'react';
import CourseItem from './CourseItem';
import PropTypes from 'prop-types';

class Courses extends Component {
  render() {
      return this.props.courses.map((course) => (
          <CourseItem key={course.id} course={course} delCourse={this.props.delCourse} />
      ));
  }
}

//PropTypes
Courses.propTypes = {
    courses: PropTypes.array.isRequired
}


export default Courses;
