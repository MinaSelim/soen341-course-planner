import React, { Component } from 'react';
import { CardGroup, Badge, Card, CardImg, CardText,
  CardTitle, Button, Form, FormGroup,} from 'reactstrap';
import Courses from './Courses';
import AddCourse from './AddCourse';
import studentIcon from './images/student_icon.png';
import uuid from 'uuid';
import { Link } from 'react-router-dom';
import './userpage.css';
import SignIn from '../logincomponents/SignIn.js';
import DisplayPage from './DisplayPage.js';
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
            coursesTaken: sessionStorage.getItem("coursesTaken")

        }

        this.formatProgramString = this.formatProgramString.bind(this);
    }

   
    formatString(programString){
        let programString = this.state.program;
        programString = programString.slice(1, programString.length-1);
        return programString;
    }

    componentDidMount(){
        fetch('localhost:8080/sequence'), {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },

            body: JSON.stringify({
                program: this.formatProgramString(this.state.program),
                coursesTaken: this.state.coursesTaken
            })

        }
    }

    render(){
        return(
            <div>
              <div>Loading...</div>
            </div>
        );
    }
}


export default DisplayPage;