import React from 'react';
import ReactDOM from 'react-dom';
import App from './test/test.js';
import './index.css';


//React has its own virtual DOM, we must use it to render out App Component
//into the "root" component
ReactDOM.render(<App />, document.getElementById('root'));

