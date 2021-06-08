import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import Login from '../Login/login';
import NotFound from '../NotFound/NotFound';
import Home from "../pages/home/Home"
import Groups from "../pages/groups/groups"
import Search from "../pages/Search/Search"
import "./styles.css";



class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
          loggedIn : false,
          userId : localStorage.getItem( 'userId' ),
          token : localStorage.getItem( 'token' ),
          coords: null
        };
        this.success = this.success.bind(this);
      }

    success(position) {
            this.setState({coords: {lat:position.coords.latitude,lon:position.coords.longitude }})
            console.log(this.state.coords)
            }
    componentDidMount(){
        if ("geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(this.success)
        }
        else {
            console.log('geolocation is not enabled on this browser');
            }
      }
    
    handleUserId = (childData) => {
        console.log(childData.userId);
        this.setState({userId: childData.userId});
        localStorage.setItem( 'userId', childData.userId );
    }

    render() {
        //console.log(this.state.userId);
        //console.log(this.state.token);
        return(
        <BrowserRouter>
            <Switch>
            <Route exact path="/login">
                <Login parentCallback = {this.handleUserId}/>
            </Route>
            <Route exact path="/">
                <Home userId={this.state.userId} token={this.state.token}/>
            </Route>
            <Route exact path="/groups">
                <Groups userId={this.state.userId} token={this.state.token}/>
            </Route>
            <Route exact path="/search">
                <Search userId={this.state.userId} token={this.state.token}/>
            </Route>
            <route path="/profile/:username">

            </route>
            <Route component={NotFound} />
            </Switch>
        </BrowserRouter>);
      }
}
export default App;

