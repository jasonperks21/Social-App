import React from 'react';

class AddGroup extends React.Component{
    constructor(props) {
        super(props);

        this.state = {
          buttonPressed:false,
          input: '',
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }

      handleChange(event) {
        this.setState({input: event.target.value});
      }
      
      handleSubmit(event) {
        this.addGroup();
        this.setState({input: ''});
        event.preventDefault();
      }
      
      addGroup(){
        // Simple POST request with a JSON body using fetch
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({userId: this.props.userId, groupName: this.state.input})
        };
       fetch('/app/groups', requestOptions)
            .then(() => {
                window.location.reload(false);
            });
    }
    checkName(){
        if(this.state.input !== ''){
            return <input type="submit" value="Create" />;
        }
        return <div></div>
    }

    render(){
        //console.log(JSON.stringify({userId: this.props.userId, groupName: this.state.input})); 
        if(!this.state.buttonPressed){
            return (<button onClick={()=>{this.setState({buttonPressed: true})}} >Create new group</button>);
        }
        return (
            <form onSubmit={this.handleSubmit}> 
              <input type="text" value={this.state.input} placeholder="Enter Group Name" onChange={this.handleChange} id="CreateNewGroup"/>
              {this.checkName()}
            </form>
        )
        
    }
}

export default AddGroup;