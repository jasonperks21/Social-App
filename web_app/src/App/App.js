import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import Login from '../Login/login';
import NotFound from '../NotFound/NotFound';
import Home from "../pages/home/Home"
import Groups from "../pages/groups/groups"
import "./styles.css";



class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
          loggedIn : true,
          userId : '1'
        };
      }

    render() {
        return(
        <BrowserRouter>
            <Switch>
            <Route exact path="/login">
                <Login />
            </Route>
            <Route exact path="/">
                <Home userId={this.state.userId}/>
            </Route>
            <Route exact path="/groups">
                <Groups userId={this.state.userId}/>
            </Route>
            <route path="/profile/:username">

            </route>
            <Route component={NotFound} />
            </Switch>
        </BrowserRouter>);
      }
}

export default App;