import React, { Component } from 'react'
import coursesIcon from './images/courses_icon.png';
import {Button} from 'reactstrap';
import PropTypes from 'prop-types';

export class CourseItem extends Component {
  render() {
    const {id} = this.props.course;
    return (
      <div style={boxStyle}>
        <p style={fontStyle}>
        <img src={coursesIcon} alt="CourseLogo" style={imgStyle}/>{this.props.course.title}
        <Button onClick={this.props.delCourse.bind(this,id)} style={btnStyle} close/>
        </p>
      </div>
    )
  }
}

//PropTypes
CourseItem.propTypes = {
    course: PropTypes.object.isRequired
}

const fontStyle = {
  fontSize: '30px',
  fontWeight: 'bold',
  fontFamily: 'Arial, Helvetica, sans-serif',
}


const boxStyle = {
    background: '#f4f4f4',
    padding: '10px',
    borderBottom: '1px #ccc dotted',
    margin: '5px'
}

const btnStyle = {
    padding: '2px 2px',
    cursor: 'pointer',
    float: 'right'
}

const imgStyle = { 
    width: '50px',
    height: '50px',
    border: 'solid #3366ff',
    borderRadius: '100%',
    margin: '10px',
}


export default CourseItem

