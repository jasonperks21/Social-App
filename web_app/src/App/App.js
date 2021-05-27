import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import Login from '../Login/login';
import NotFound from '../NotFound/NotFound'


class App extends React.Component {
    render() { return(
        <BrowserRouter>
            <Switch>
            <Route exact path="/">
                <Login />
            </Route>
            <Route component={NotFound} />
            </Switch>
        </BrowserRouter>);
      }
}

export default App;