import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import Login from '../Login/login';
import NotFound from '../NotFound/NotFound';
import Home from '../Home/Home';



class App extends React.Component {
    render() { return(
        <BrowserRouter>
            <Switch>
            <Route path="/login">
                <Login />
            </Route>
            <Route path ='/' component={Home} />
            <Route component={NotFound} />
            </Switch>
        </BrowserRouter>);
      }
}

export default App;