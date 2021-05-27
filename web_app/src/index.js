import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import './index.css';
import Login from './Login/login';
import NotFound from './NotFound/NotFound'

ReactDOM.render(
  <BrowserRouter>
    <Switch>
      <Route exact path="/">
        <Login />
      </Route>
      <Route component={NotFound} />

    </Switch>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
