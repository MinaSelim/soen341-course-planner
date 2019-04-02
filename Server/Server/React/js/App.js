import React, { Component } from 'react';
import './App.css';
import SignIn from './logincomponents/SignIn';
import SignUp from './logincomponents/SignUp';
import Authenticator from './firebase/Authenticator'; 
import Userpage from './maincomponents/Userpage';
import { BrowserRouter as Router, Route, Switch, Redirect} from "react-router-dom";


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: null,
      email: '',
      password: '',
      fname: '',
      lname: '',
      program: '',
      idnumber: '',
    };
    this.authListener = this.authListener.bind(this)
    this.handleChange = this.handleChange.bind(this)
    this.changeEmail = this.changeEmail.bind(this)
    this.changeFname = this.changeFname.bind(this)
    this.changeLname = this.changeLname.bind(this)
    this.changeProgram = this.changeProgram.bind(this)
  }
  handleChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }
  
  changeEmail(val){
    this.setState({email:val});
  }
  changeFname(val){
    this.setState({fname:val});
  }
  changeLname(val){
    this.setState({lname:val});
  }
  changeProgram(val){
    this.setState({program:val});
  }


  componentDidMount(){ 
    this.authListener();
  }
  componentWillMount(){
    sessionStorage.getItem('email') && this.setState({
      email: JSON.parse(sessionStorage.getItem('email'))
    }) 
    sessionStorage.getItem('fname') && this.setState({
      fname: JSON.parse(sessionStorage.getItem('fname'))
    }) 
    sessionStorage.getItem('lname') && this.setState({
      lname: JSON.parse(sessionStorage.getItem('lname'))
    }) 
    sessionStorage.getItem('program') && this.setState({
      program: JSON.parse(sessionStorage.getItem('program'))
    }) 
  }
  componentWillUpdate(nextProps, nextState) {
    sessionStorage.setItem('fname', JSON.stringify(nextState.fname));
    sessionStorage.setItem('lname', JSON.stringify(nextState.lname));
    sessionStorage.setItem('email', JSON.stringify(nextState.email));
    sessionStorage.setItem('program', JSON.stringify(nextState.program));
  }
  authListener() {
    Authenticator.auth().onAuthStateChanged((user) => {
      if (user) {
        this.setState({ user });
      } else {
        this.setState({ user: null });
      }
    });
  }
  
  render() {
    return (
      <Router>
      <div className="App">
      <Switch>
            <Route path='/SignIn' render={props => (<SignIn handleChange={this.handleChange} state={this.state} changeEmail={this.changeEmail} changeFname={this.changeFname} changeLname={this.changeLname} changeProgram={this.changeProgram}/>)} /> 
            {Authenticator.database().ref('users').orderByChild('idnumber').equalTo(this.state.idnumber) != null && <Route path='/Main' render={props => (<Userpage handleChange={this.handleChange} state={this.state}/>)}/>}
            <Route path='/SignUp' render={props => (<SignUp handleChange={this.handleChange} state={this.state}/>)} />
            <Redirect from="/" to="SignIn" /> 
      </Switch>
      </div>
      </Router>
    );
  }
}
export default App;
