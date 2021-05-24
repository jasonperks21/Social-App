import React from 'react';
import './App.css';

class Demo extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      users : [],
      input : ''
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    this.setState({input: event.target.value});
  }

  handleSubmit(event) {
    this.post();
    this.setState({input: ''});
    event.preventDefault();
  }

  async componentDidMount() {
    const apiUrl = '/app/test';
    await fetch(apiUrl).then((response) => response.json()).then((data) => {
      this.setState({ users: data});
    });
    
   }
  
   async post(){
    {
      // Simple POST request with a JSON body using fetch
      const requestOptions = {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(this.state.input)
      };
      await fetch('/app/add', requestOptions)
          .then(response => {
            window.location.reload(false);
          });
  }
  }

  render() {
      let l = this.state.users
      if(l.length>0){
        return ( 
          <div className="App">
        <header className="App-header">
          <div className = "Edit">
            <h1>Edit: </h1>
            <form onSubmit={this.handleSubmit} >
              <label>
                Post:  
                <input type="text" value={this.state.input} onChange={this.handleChange} />
              </label>
              <input type="submit" value="Submit" />
            </form>
          </div>
        
        <div className="Results">
          <h1>Results:</h1>
          {l.map((data, key) => {
          return (
            <div key={key}>
              {data.id +
                ": " +
                data.text}
            </div>
            );
          })
          }
          <p></p>
          </div>
        </header>
      </div>
        );
      }
      else{
        return ( 
          <div className="App">
        <header className="App-header">
          <p>
            Start Springboot server to see results
          </p>
          <p></p>
        </header>
      </div>
        );
      }   
  }
}

export default Demo;
