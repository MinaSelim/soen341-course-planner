import React, { Component } from 'react';
import "./common.css"

class CreateAccountComponent extends Component {
   constructor(props) {
      super(props);
   }

   render() {
      return (
         <div className="Generic-Box-Container">
            <span className="Generic-X-button"><strong>X</strong></span>
            <br/>
            <div className="Generic-Text-Wrapper">
               Log In To Continue
               <p>
                  <input type="text" name="email" placeholder="Email"
                   className="Generic-Block-Size Generic-Input-Box "/>
               </p>
               <p>
                  <input type="text" name="firstName" placeholder="First Name"
                   className="Generic-Block-Size Generic-Input-Box "/>
               </p>
               <p>
                  <input type="text" name="lastName" placeholder="Last Name"
                   className="Generic-Block-Size Generic-Input-Box "/>
               </p>
               <br/>
               <p>
                  <input type="password" name="password" placeholder="Create Password"
                   className="Generic-Block-Size Generic-Input-Box"/>
               </p>
               <p>
                  <input type="password" name="password" placeholder="Confirm Password"
                   className="Generic-Block-Size Generic-Input-Box"/>
               </p>
               <br/>
               <p>VERIFY HUMANITY, BUT THERE IS NO HOPE FOR HUMANITY</p>
               <button className="Generic-Block-Size Generic-Button ">Sign Up</button>
               <br/>
            </div>
         </div>
     );

   }

}

export default CreateAccountComponent;