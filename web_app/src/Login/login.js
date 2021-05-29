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
      }

    render() {
        let re;
        if(this.state.loginNotRegister){
            re = <LoginBox/>;
        }
        else{
            re = <RegisterBox />;
        }
        return(
            <div className="box">
                <img class="watermark" src={'./logo1.png'} alt="Logo"/>
                {re}
            </div>
            );
      }
}

export default Login;