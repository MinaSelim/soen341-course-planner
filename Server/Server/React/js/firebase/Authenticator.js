import * as firebase from 'firebase';
var config = {
  apiKey: "AIzaSyAGrqLENpZ1w6ewJjwZ7ihZJyThL4p8SGY",
  authDomain: "soen341userdatabase.firebaseapp.com",
  databaseURL: "https://soen341userdatabase.firebaseio.com",
  projectId: "soen341userdatabase",
  storageBucket: "soen341userdatabase.appspot.com",
  messagingSenderId: "336376368606"
};
firebase.initializeApp(config);
export const database = firebase.database();
export const auth = firebase.auth();
export const storage = firebase.storage().ref();

export default firebase;
