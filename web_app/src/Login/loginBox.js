import React from 'react';
//Login Box
class LoginBox extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        user: '',
        pwd : '',
        token: null,
        login: true
    };
    this.handle = this.handle.bind(this)
  }
  handle(){
    this.setState({login:false});
  }


  render() {
    return (
        <div className="login-wrapper">
        <h1>Log In</h1>
        <form onSubmit={this.handleSubmit}>
          <label>
            <p>Username</p>
            <input type="text" placeholder="Username"/>
          </label>
          <br></br>
          <label>
            <p>Password</p>
            <input type="password" placeholder="Password"/>
          </label>
          <div>
            <div className="bDown">
            <button type="submit" id="submit">Submit</button>
            </div>
            
          </div>
        </form>
      </div>
    );
  }

}

export default LoginBox;