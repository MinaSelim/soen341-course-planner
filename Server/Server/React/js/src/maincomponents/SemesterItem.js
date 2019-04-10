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


class SemesterItem extends Component(){
    constructor(props){
        super(props);
    }


    render(){
        return(
            <AccordionItem>
            <AccordionItemHeading>
                <AccordionItemButton>
                    What harsh truths do you prefer to ignore?
                </AccordionItemButton>
            </AccordionItemHeading>
            <AccordionItemPanel>
                <p>
                    Exercitation in fugiat est ut ad ea cupidatat ut in
                    cupidatat occaecat ut occaecat consequat est minim minim
                    esse tempor laborum consequat esse adipisicing eu
                    reprehenderit enim.
                </p>
            </AccordionItemPanel>
        </AccordionItem>
        );
    }
}

export default SemesterItem;