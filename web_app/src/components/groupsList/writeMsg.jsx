import "./writeMsg.css";
import React from 'react';

class WriteMsg extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
          input: '',
          coords: this.props.coords
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
      }
    sendMsg(){
        // Simple POST request with a JSON body using fetch
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + this.props.token },
            body: JSON.stringify({userId: this.props.userId, 
                                groupId: this.props.groupInfo.groupId, 
                                message: this.state.input, 
                                replyId: '', 
                                categoryId: 1,
                                longitude: this.state.coords.lon,
                                latitude: this.state.coords.lat
                            })
        };
       fetch('/app/posts', requestOptions)
            .then((response) => {
                //console.log(requestOptions)
                console.log(response);
                this.setState({input: ''})
                //window.location.reload(false);
            });
    }
    handleChange(e){
        this.setState({input: e.target.value})
    }
    handleSubmit(e){
        this.sendMsg()
        e.preventDefault();
    }

    render(){
        //console.log(this.state.coords.lat)
        return (
            <div className="send">
              <div className="sendWrapper">
                <form onClick={this.handleSubmit}>
                    <div className="sendTop">
                    <input placeholder="Write a message" className="shareInput" onChange={this.handleChange} value={this.state.input}/> 
                    </div>
                    <div>
                    <input type="submit" className="sendButton" value="send" />
                    {/* <input type='submit'  onClick={this.handleSubmit}>Send</input> */}
                    </div>
                </form>
              </div>
            </div>
          );
    }
}

export default WriteMsg;