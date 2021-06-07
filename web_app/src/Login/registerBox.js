import React from 'react';
import PasswordStrengthBar from 'react-password-strength-bar';
import  { Redirect } from 'react-router-dom';
//Register Box 
class RegisterBox extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      email: '',
      username: '',
      password: '',
      passwordC: '',
      errorMsg: '',
      dispName: '',
      loggedIn: false,
      userId: null
    };
    this.handleEmail = this.handleEmail.bind(this);
    this.handleUsername = this.handleUsername.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handlePassword = this.handlePassword.bind(this);
    this.handlePasswordC = this.handlePasswordC.bind(this);
    this.handleDisp = this.handleDisp.bind(this);
  }

  handleEmail(event) {
    this.setState({email: event.target.value});
  }
  handleUsername(event) {
    this.setState({username: event.target.value});
  }
  handlePassword(event) {
    this.setState({password: event.target.value});
  }
  handlePasswordC(event) {
    this.setState({passwordC: event.target.value});
  }
  handleDisp(event) {
    this.setState({dispName: event.target.value});
  }

  async addUser(){
    // Simple POST request with a JSON body using fetch
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({username: this.state.username, displayname: this.state.dispName, email: this.state.email, password: this.state.password})
    };
   fetch('/app/users', requestOptions)
   .then((res) => res.json())
   .then((response) =>  {
            if(response.emailError){
              this.setState({errorMsg: 'Email incorrect or already in use'});
            }
            else if(response.passwordError){
              this.setState({errorMsg: 'Invalid Password!'})
            }
            else if(response.usernameError){
              this.setState({errorMsg: 'Username Already in use!'})
            }
            else{
              this.setState({loggedIn: true, userId: response.user})
            }   
        });
}


  handleSubmit(event) {
    if(this.state.password === this.state.passwordC){
      if(this.state.password.length>7){
        console.log('Matched!');
        this.addUser();
      }
      else{
        this.setState({errorMsg: 'Passwords is too short'});
      }
    }
    else{
      this.setState({errorMsg: 'Passwords does not match!'});
    }
    event.preventDefault();
  }

  render() {
    if(this.state.loggedIn){
      this.props.callBack(this.state.userId);
      return <Redirect to='/'/>;
    }
    return (

      <div className="login-wrapper">
      <h1>Sign Up</h1>
      <form onSubmit={this.handleSubmit}>
      <label>
          <p>Email</p>
          <input type="email" placeholder="example@something.com" value={this.state.email} onChange={this.handleEmail}/>
        </label>
        <label>
          <p>Username</p>
          <input type="text" placeholder="Username" value={this.state.username} onChange={this.handleUsername}/>
        </label>
        <label>
          <p>Display Name</p>
          <input type="text" placeholder="Display Name" value={this.state.dispName} onChange={this.handleDisp}/>
        </label>
        <label>
          <p>Password</p>
          <input type="password" placeholder="Password" value={this.state.password} onChange={this.handlePassword}/>
        </label>
        <label>
          <p>Confirm Password</p>
          <input type="password" placeholder="Confirm Password" value={this.state.passwordC} onChange={this.handlePasswordC}/>
        </label>
        <PasswordStrengthBar password={this.state.password} />
        <div>
          <div className="bDown">
            <p className="errorMsg">{this.state.errorMsg}</p>
          <button type="submit" id="submit">Register</button>
          </div>
        </div>
      </form>
    </div>

    );
  }
}
export default RegisterBox;