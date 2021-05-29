import React from 'react';
import LoginBox from './loginBox';
import RegisterBox from './registerBox';
import './login.css';
//import { useSpring, animated } from "react-spring";

class Login extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
          loginNotRegister : true
        };
        this.handle = this.handle.bind(this);
      }
    handle(){
        let b = !(this.state.loginNotRegister);
        this.setState({loginNotRegister: b});
    }

    render() {
        let re;
        let bText;
        if(this.state.loginNotRegister){
            re = <LoginBox/>;
            bText = 'Register';
        }
        else{
            bText = 'Login';
            re = <RegisterBox />;
        }
        return(
            <div className="box">
                <img class="watermark" src={'./logo1.png'} alt="Logo"/>
                <button type="button" id="switch" onClick={this.handle}>{bText}</button>
                {re}
            </div>
            );
      }
}

export default Login;