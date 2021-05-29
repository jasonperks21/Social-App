import React from 'react';
//Register Box 
class RegisterBox extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      email: '',
      username: '',
      password: ''
    };
  }

  render() {
    return (

      <div className="login-wrapper">
      <h1>Sign Up</h1>
      <form onSubmit={this.handleSubmit}>
      <label>
          <p>Email</p>
          <input type="email" placeholder="example@something.com"/>
        </label>
        <label>
          <p>Username</p>
          <input type="text" placeholder="Username"/>
        </label>
        <label>
          <p>Password</p>
          <input type="password" placeholder="Password"/>
        </label>
        <label>
          <p>Confirm Password</p>
          <input type="password" placeholder="Confirm Password"/>
        </label>
        <div>
          <div className="bDown">
          <button type="submit" id="submit">Register</button>
          </div>
        </div>
      </form>
    </div>

    );
  }
}
export default RegisterBox;