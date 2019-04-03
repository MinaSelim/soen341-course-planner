import React from 'react';
import ReactDOM from 'react-dom';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import withStyles from '@material-ui/core/styles/withStyles';
import blue from '@material-ui/core/colors/blue';
import Authenticator from '../firebase/Authenticator';
import {database} from '../firebase/Authenticator';
import { BrowserRouter as Router, Redirect, Link} from 'react-router-dom';
import createBrowserHistory from 'history/createBrowserHistory';
import './Login.css';
import Main from '../maincomponents/Userpage.js';


const styles = theme => ({
  main: {
    width: 'auto',
    display: 'block',
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
  },
  form: {
    width: '100%', 
    marginTop: theme.spacing.unit,
  },
  submit: {
    marginTop: theme.spacing.unit * 3,
  },
  controller: {
    marginTop: theme.spacing.unit * 3,
    width: '50%',
  },
});

function SignUp(props) {
  const { classes } = props;
  const history = createBrowserHistory({forceRefresh:true});
  return (
    <main className={classes.main}>
      <CssBaseline />
      <Link to={'/SignIn'}>
      <Button
            type="submit"
            variant="contained"
            color="primary"
            className={classes.controller}
      >
            Login
          </Button>
      </Link>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            className={classes.controller}
          >
            Register
          </Button>
      <Paper className={classes.paper}>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <form className={classes.form}>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="idnumber">Student Id</InputLabel>
            <Input id="idnumeber" name= "idnumber" autoFocus autoComplete="idnumber" value={props.state.idnumber} onChange={props.handleChange.bind(this)}/>
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="email">E-mail</InputLabel>
            <Input id="email" name="email" autoFocus autoComplete="email" value={props.state.email} onChange={props.handleChange.bind(this)}/>
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="program">Program</InputLabel>
            <Input name="program" type="program" id="program" value={props.state.program} onChange={props.handleChange.bind(this)}/>
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="fname">First Name</InputLabel>
            <Input name="fname" type="fname" id="fname"  value={props.state.fname} onChange={props.handleChange.bind(this)}/>
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="lname">Last Name</InputLabel>
            <Input name="lname" type="lname" id="lname"  value={props.state.lname} onChange={props.handleChange.bind(this)}/>
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="password">Password</InputLabel>
            <Input name="password" type="password" id="password" autoComplete="current-password" value={props.state.password} onChange={props.handleChange.bind(this)}/>
          </FormControl>
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={signup}
          >
            Sign up
          </Button>
        </form>
      </Paper>
    </main>

  );
  function signup(e){
    e.preventDefault();
    Authenticator.auth().createUserWithEmailAndPassword(props.state.email, props.state.password).then((user)=>
    {
      var userID = Authenticator.auth().currentUser.uid;
      var fname=props.state.fname;
      var lname=props.state.lname;
      var email=props.state.email;
      var program=props.state.program;
      var idnumber=props.state.idnumber;

      if (user !== null) 
      {
        database.ref('users').child(userID).set({
            fname,
            lname,
            email,
            program,
            idnumber,
          });
      }
    }).then(()=>{
      const element = (
        <Router>
        <Main program={this.props.state.program}/>
        </Router>
      );
      ReactDOM.render(element, document.getElementById('root'));
    })
  }
}

SignUp.propTypes = {
  classes: PropTypes.object.isRequired,
};

SignUp.contextTypes = {
  router: PropTypes.object.isRequired,
}

export default withStyles(styles)(SignUp);