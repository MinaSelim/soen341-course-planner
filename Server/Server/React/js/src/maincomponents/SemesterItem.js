import React, { Component } from 'react';
import { CardGroup, Badge, Card, CardImg, CardText,
  CardTitle, Button, Form, FormGroup,} from 'reactstrap';
import {
    Accordion,
    AccordionItem,
    AccordionItemHeading,
    AccordionItemButton,
    AccordionItemPanel,
} from 'react-accessible-accordion';
import 'react-accessible-accordion/dist/fancy-example.css';
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


function SemesterItem (props){  
    return(
            <AccordionItem>
            <AccordionItemHeading>
                <AccordionItemButton>
                    {props.semesterName}
                </AccordionItemButton>
            </AccordionItemHeading>
            <AccordionItemPanel>
               {props.courses.map((course)=> (
                   <div>{course}</div>
               ))}
            </AccordionItemPanel>
        </AccordionItem>
        );
    
}

export default SemesterItem;