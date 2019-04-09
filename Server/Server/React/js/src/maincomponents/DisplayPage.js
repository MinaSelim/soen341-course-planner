import React, { Component } from 'react';
import { CardGroup, Badge, Card, CardImg, CardText,
  CardTitle, Button, Form, FormGroup,} from 'reactstrap';
import Courses from './Courses';
import AddCourse from './AddCourse';
import studentIcon from './images/student_icon.png';
import uuid from 'uuid';
import { Link } from 'react-router-dom';
import './userpage.css';
import './DisplayPage.css';
import SignIn from '../logincomponents/SignIn.js';
import { BrowserRouter as Router, Route, Switch} from "react-router-dom";
import ReactDOM from 'react-dom';

class DisplayPage extends Component{
    constructor(){
        super();

        this.state ={
            semesters: [],
            isLoaded: false,
            error: null,
            program: sessionStorage.getItem("program"),
            //TODO Must be an array of strings
            coursesTaken: sessionStorage.getItem("coursesTaken"),
            program: '',
            lname: '',
            fname: '',
            idnumber: '',
            courses: [],
              }
              this.addCourse = this.addCourse.bind(this);
              this.getuser = this.getuser.bind(this);
              this.delCourse = this.delCourse.bind(this);
              this.formatString = this.formatString.bind(this);
              this.handleLogout = this.handleLogout.bind(this);
              this.formatIDNumber = this.formatIDNumber.bind(this);

        

        //this.formatProgramString = this.formatProgramString.bind(this);
    }

   
 
//Delete
delCourse(id){
    this.setState({courses: [...this.state.courses.filter(course => course.id
    !== id)] });
  }
  
  // Add Course
  addCourse(title){
    const newCourse = {
      id: uuid.v4(),
      title,
    }
    this.setState({courses: [...this.state.courses, newCourse]});
  }
  
  getuser(){
    if(this.props.state.user != null)
    {
      this.state.useremail= Object.keys(this.props.state.user)[12];
      return this.state.useremail;
    }
  }
  
  
  formatString(programString){
    var programString = this.state.program;
    programString = programString.slice(1, programString.length-1);
    return programString;
  }

  formatFName(NString){
    var  NString = this.state.fname;
    NString = NString.slice(1, NString.length-1);
    return NString;
  }

  formatIDNumber(idString){
    var idString = this.state.idnumber;
    idString = idtring.slice(1, idString.length-1);
    return idString;
  }
  
  formatLName(LString){
    var LString = this.state.lname;
    LString = LString.slice(1, LString.length-1);
    return LString;
  }
  
  formatEmail(MailString){
    var MailString = this.state.email;
    MailString = MailString.slice(1, MailString.length-1);
    return MailString;
  }

  handleLogout(e){
    e.preventDefault();
    window.location.reload();
   }
  
    packageRequest(programString, coursesTaken){
        let requestObject = {
            "programCode": "SOEN",
            "coursesTaken": ["COMP232","COMP248", "COMP249", "COMP352"]
        }
    }

    componentWillMount(){ 
        var state ={
          program: sessionStorage.getItem("program"),
          lname: sessionStorage.getItem("lname"),
          fname: sessionStorage.getItem("fname"),
          email: sessionStorage.getItem("email"),
          idNumber: sessionStorage.getItem("idNumber"),
          courses: [],
        };
    
        this.setState(() => ({
          program: sessionStorage.getItem("program"),
          lname: sessionStorage.getItem("lname"),
          fname: sessionStorage.getItem("fname"),
          email: sessionStorage.getItem("email"),
          idnumber: sessionStorage.getItem("idNumber"),
          courses: [],
        }));
      
      }
    

   /** componentDidMount(){
        fetch('localhost:8080/sequence'), {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },

            body: JSON.stringify({
                programCode: this.formatProgramString(this.state.program),
                coursesTaken: this.state.coursesTaken
            })

        }
    }
    */ 

    render(){
        var programString = this.formatString(this.state.program);
        var fName = this.formatFName(this.state.fname);
        var lName = this.formatLName(this.state.lname);
        var eMail = this.formatEmail(this.state.email);
        return(

           

            <div className="App">
                 <CardGroup>
                   <div>
                     <Card style={profileStyle}>
                       <CardImg src={studentIcon} alt="StudentLogo" style={profileImgStyle} />
                       <p className='thick'>{lName}, {fName}</p>
                       <p className='normal'>{eMail}</p>
                     </Card>
                     <div className='card'>
                     <p className='thick'>Degree </p>
                     <p className='normal'>{programString} <br />Bachelor's Degree</p>
                     </div>
                     <Card style={{height: '72%'}}>
                     <Button
                        type="submit"
                        variant="contained"
                        color= 'primary'
                        style={LogoutStyle}
                        onClick={this.handleLogout}
                       >
                         Logout
                      </Button>
                     </Card>
                     </div>
         
         
                   <Card style={OptionCardStyle}>
                     <CardTitle className="text-center"><h1><Badge style={badgeStyle} color="primary">Sequence Schedule</Badge></h1></CardTitle>
                    
                   </Card>
                 </CardGroup>
               </div>
            
        );
    }
}


export default DisplayPage;


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
  