import React from 'react';
//import  { Redirect } from 'react-router-dom';
//Login Box
class LoginBox extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        user: '',
        pwd : '',
        token: null,
        loggedIn: false,
        checked: true,
        errorMsg: '',
        userId: null
    };
    this.changeChecked = this.changeChecked.bind(this);
    this.handleUsername = this.handleUsername.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handlePassword = this.handlePassword.bind(this);
  }

  changeChecked(){
    this.setState({checked: !this.state.checked});
  }
  handleUsername(event) {
    this.setState({user: event.target.value});
  }
  handlePassword(event) {
    this.setState({pwd: event.target.value});
  }
  handleSubmit(event) {
    if(this.state.pwd === '' || this.state.user === ''){
      this.setState({errorMsg: 'Please fill in all the fields!'});
    }
    else{
      this.login()
    }
    event.preventDefault();
  }

  async login(){
    // Simple POST request with a JSON body using fetch
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({username: this.state.user, password: this.state.pwd})
    };
   fetch('/app/login', requestOptions)
   .then((res) => res.json())
   .then((response) =>  {
     if(response.status === 401){
      this.setState({errorMsg: 'Wrong username or password'});
     }
     if(response.token){
      this.setState({token: response.token});
      localStorage.setItem( 'token', response.token );
      localStorage.setItem( 'rememberMe', this.state.checked );
      const head = {'headers': {'Authorization': 'Bearer ' + response.token}}
      console.log(response);
      fetch('/app/users/?q='+this.state.user, head)
      .then((res) => res.json())
      .then((response) =>  {
        if(response.status !== 404 && response.status !== 401 && response.status !== 400 && response.status !== 500){
          response.forEach(element => {
            console.log(element)
            console.log(this.state.user)
            if(element.userName === this.state.user){
              this.setState({loggedIn:true, userId: element.userId});  
            }
          });
        }
      }
    );
  }
  });
}

  render() {
    if(this.state.loggedIn){
      localStorage.setItem( 'userId', this.state.userId);
      localStorage.setItem( 'token', this.state.token);
      window.location.replace('/');
    }
    return (

        <div className="login-wrapper">
        <h1>Login</h1>
        <form onSubmit={this.handleSubmit}>
          <label>
            <p>Username</p>
            <input type="text" placeholder="Username" value={this.state.user} onChange={this.handleUsername}/>
          </label>
          <label>
            <p>Password</p>
            <input type="password" placeholder="Password" value={this.state.pwd} onChange={this.handlePassword}/>
          </label>
          
          <label id="RememberMe">
            <input type="checkbox" name="remember" value="remember" checked={this.state.checked} onChange={this.changeChecked} ></input>
            <span>Remember Me</span>
            
          </label>
          <p className="errorMsg">{this.state.errorMsg}</p>
          <br></br>
          <a id='forgot' href="/login">Forgot your password?</a>
          <div>
            
            <div className="bDown">
            <button type="submit" id="submit">Login</button>
            </div>
          </div>
        </form>
      </div>
    );
  }

}

export default LoginBox;