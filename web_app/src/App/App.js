import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import Login from '../Login/login';
import NotFound from '../NotFound/NotFound';
//import Home from '../Home/Home';
import Home from "../pages/home/Home"
import "./styles.css";



class App extends React.Component {
    render() { return(
        <BrowserRouter>
            <Switch>
            <Route path="/login">
                <Login />
            </Route>
            <Route path="/">
                <Home />
            </Route>
            <Route component={NotFound} />
            </Switch>
        </BrowserRouter>);
      }
}

export default App;