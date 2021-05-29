import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import Login from '../Login/login';
import NotFound from '../NotFound/NotFound';
//import Home from '../Home/Home';
import Home from "../pages/home/Home"
import "./styles.css";



class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
          loggedIn : true,
          userId : '9'
        };
      }

    render() { return(
        <BrowserRouter>
            <Switch>
            <Route path="/login">
                <Login />
            </Route>
            <Route path="/">
                <Home userId={this.state.userId}/>
            </Route>
            <Route component={NotFound} />
            </Switch>
        </BrowserRouter>);
      }
}

export default App;