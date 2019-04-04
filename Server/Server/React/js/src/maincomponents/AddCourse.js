import React, { Component } from 'react'
import {Button} from 'reactstrap';
import './AutoCompleteText.css';
import Authenticator from '../firebase/Authenticator';

export class AddCourse extends Component {
  
  constructor (props) {
    super(props)
    this.handleKeyDown = this.handleKeyDown.bind(this);
    this.getCourseIDs = this.getCourseIDs.bind(this);
    this.onTextChanged = this.onTextChanged.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.formatProgramString = this.formatProgramString.bind(this);
    this.verifySubmittedCourseTaken = this.verifySubmittedCourseTaken.bind(this);
    this.courseIDs = []
    this.state = {
        cursor: 0,
        suggestions: [],
        text: '',
        count: 0,
        program: sessionStorage.getItem("program")
    }
}

getCourseIDs() {
    console.log(this.state.program);
    var programString = this.state.program;
    programString = this.formatProgramString(programString);
    console.log(programString);
    var updateDb = Authenticator.database().ref("programs").child(programString);
    console.log(updateDb);
    updateDb.once("value").then((snapshot) =>{
      for(let i=0; i<Object.keys(snapshot.val()).length; i++)
      {
        this.courseIDs.push(Object.keys(snapshot.val())[i]);
        console.log(this.courseIDs[i]);
      }
    });
}


verifySubmittedCourseTaken(courseString) {
    let coursesAvailable =  []; 
    updateDb.once("value").then((snapshot) => {
        for(let i=0; i<Object.keys(snapshot.val()).length; i++)
        {
          courseAvailable.push(Object.keys(snapshot.val())[i]);
          console.log("Course Available:");
          console.log(this.courseAvailable[i]);
        }
      });
    
    var validString = true;
    for(let i=0; i<coursesAvailable.length; i++){
        if(courseString.localeCompare(coursesAvailable[i]) != 0 )
            validString = false;
    }

    return validString;
}

handleKeyDown(e) {
    const { cursor, suggestions } = this.state
    // arrow up/down button should select next/previous list element
    if (e.keyCode === 38 && cursor > 0) {
      this.setState( prevState => ({
        cursor: prevState.cursor - 1
      }))
    } else if (e.keyCode === 40 && cursor < suggestions.length - 1) {
      this.setState( prevState => ({
        cursor: prevState.cursor + 1
      }))
    }
  }

    onTextChanged(e){
      const value = e.target.value;
      let suggestions = [];
      if (value.length > 0){
          const regex = new RegExp(`^${value}`, 'i');
          suggestions = this.courseIDs.sort().filter(v => regex.test(v));
      }
      this.setState(() => ({suggestions, text: value}));
  }

  suggestionSelected (value) {
      this.setState (() => ({
          text: value,
          suggestions: [],
      }))
  }

  renderSuggestions() {
      const {suggestions, cursor} = this.state;
      if(suggestions.length === 0) {
          return null
      }
       return ( 
           
          <ul>
              {suggestions.map((courseID, i) => <li 
              key={courseID._i}
              className={cursor === i ? 'active' : null}
              onClick={() => this.suggestionSelected(courseID)}
              >
              <span>{courseID}</span>
              </li>)}
          </ul>
      )
  }

  onSubmit(e){
    e.preventDefault();
    if(this.verifySubmittedCourseTaken(this.state.text))
        this.props.addCourse(this.state.text);
    else
        console.log("Can not add this course"); 
    this.setState({text: ''});
}

  formatProgramString(programString){
    var programString = this.state.program;
    programString = programString.slice(1, programString.length-1);
    return programString;
  }


    onChange(e){return this.setState({[e.target.name]: e.target.value});}


  render() {
    const { text } = this.state;
    if(this.state.count<1)
    {this.getCourseIDs();
        this.setState({count: this.state.count+1})}
    return (
      <div>
      <form onSubmit={this.onSubmit} style={{display: 'flex'}}>
          <div className="AutoCompleteText">
          <input 
          type="text"
          placeholder="Add Courses Completed ..."
          value={text}
          onChange={this.onTextChanged}
          onKeyDown={this.handleKeyDown}
          />
          {this.renderSuggestions()}
          </div>

          <div>
          <Button 
          onClick={'submit'} 
          color="primary" 
          type="submit" 
          value="submit"
          className="btn"> 
          Add
          </Button>
          </div>
      </form>
      </div>
    )
  }
}
export default AddCourse
