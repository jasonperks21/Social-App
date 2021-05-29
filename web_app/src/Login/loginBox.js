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
  }

  render() {
    return (

        <div className="login-wrapper">
        <h1>Login</h1>
        <form onSubmit={this.handleSubmit}>
          <label>
            <p>Username</p>
            <input type="text" placeholder="Username"/>
          </label>
          <label>
            <p>Password</p>
            <input type="password" placeholder="Password"/>
          </label>
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