import React, { Component } from 'react';
import { CardGroup, Badge, Card, CardImg, CardText,
  CardTitle, Button, Form, FormGroup,} from 'reactstrap';
import Courses from './Courses';
import AddCourse from './AddCourse';
import studentIcon from './images/student_icon.png';
import uuid from 'uuid';
import { Link } from 'react-router-dom';
import './userpage.css';

class userPage extends Component {

  constructor(props){
    super(props);
    this.state ={
      program: this.props.state.program,
      courses: [],
    };
    this.addCourse = this.addCourse.bind(this);
    this.getuser = this.getuser.bind(this);
  }

//Delete
delCourse = (id) => {
  this.setState({courses: [...this.state.courses.filter(course => course.id
  !== id)] });
}

// Add Course
addCourse = (title) => {
  const newCourse = {
    id: uuid.v4(),
    title,
  }
  this.setState({courses: [...this.state.courses, newCourse]});
}

getuser = () =>{
  if(this.props.state.user != null)
  {
    this.state.useremail= Object.keys(this.props.state.user)[12];
    console.log(this.state.useremail);
    return this.state.useremail;
  }
}

  render() {
    return (
      <div className="App">
        <CardGroup>
          <div>
            <Card style={profileStyle}>
              <CardImg src={studentIcon} alt="StudentLogo" style={profileImgStyle} />
              <p className='thick'>{this.props.state.lname}, {this.props.state.fname}</p>
              <p className='normal'>{this.props.state.email}</p>
            </Card>
            <div className='card'>
            <p className='thick'>Degree </p>
            <p className='normal'>{this.props.state.program} <br />Bachelor's Degree</p>
            </div>
            <Card style={{height: '72%'}}>
            <Link to={'/SignIn'}>
            <Button style={LogoutStyle}>Logout</Button>
            </Link>
            </Card>
     </div>


          <Card style={OptionCardStyle}>
            <CardTitle className="text-center"><h1><Badge style={badgeStyle} color="primary">Course Options</Badge></h1></CardTitle>
            <CardText>
              <Form>
                <FormGroup>
                  <AddCourse addCourse={this.addCourse} state={this.state} />             
                </FormGroup>
              </Form>
            </CardText>
            <Button style={btnStyle} color="primary">Generate Sequence</Button>
          </Card>

          <Card style={CompletedCardStyle}>
            <CardTitle className="text-center"><h1><Badge style={badgeStyle} color="primary">Completed Courses</Badge></h1></CardTitle>
            <CardText>
                <Courses courses={this.state.courses} delCourse={this.delCourse} />
            </CardText>
          </Card>
        </CardGroup>
      </div>
    );
  }
}

export default userPage;

const profileImgStyle = {
  width: '70px',
  height: '70px',
  border: 'solid #3366ff',
  borderRadius: '50%',
  cursor: 'pointer',
  float: 'left'
}

const OptionCardStyle = {
  height: "900px",
  width: "780px",
  border: "none" ,
  background: "#f2f2f2",
  padding: '10px '


}
const CompletedCardStyle = {
  height: "900px",
  width: "780px",
  border: "none" ,
  padding: '10px ',
  overflow: 'hidden scroll'

}

const profileStyle = {
  width: "250px",
  border: "",
}

const badgeStyle = {
  display: 'inline-block',
  width:'100%',
  zoom: 'reset',
}
const LogoutStyle = {
  position: 'absolute',
  top:'85.75%',
  left: '25%',
  width:'50%',
}

const btnStyle = {
  position: 'absolute',
  top:'90%',
  left: '25%',
  width:'50%',
  display: 'inline-block'
}
