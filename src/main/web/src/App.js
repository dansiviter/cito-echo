import React, { Component } from 'react';
import './App.css';
import { withStyles } from 'material-ui/styles';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import List from 'material-ui/List';
import Typography from 'material-ui/Typography';
import IconButton from 'material-ui/IconButton';
import Warning from 'material-ui-icons/Warning';
import Ok from 'material-ui-icons/Done';
import Grid from 'material-ui/Grid';
import Paper from 'material-ui/Paper';
import TextField from 'material-ui/TextField';
import Button from 'material-ui/Button';
import WebStomp from 'webstomp-client';

const styles = theme => ({
	button: {
		margin: theme.spacing.unit,
	},
	leftPadding: {
		paddingLeft: theme.spacing.unit,
	},
	rightPadding: {
		paddingRight: theme.spacing.unit,
	},
	info: {
		margin: theme.spacing.unit,
	},
	container: {
		margin: theme.spacing.unit * 3,
	},
	form: {
		padding: theme.spacing.unit,
	}
});

class App extends Component {

	constructor(props) {
		super(props);
		this.state = { connected: false, text: '', sendText: null };
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	componentDidMount = () => {
		this.startStomp();
	}

	startStomp = () => {
		if (this.stomp) {
			this.stomp.disconnect(this.disconnected);
			this.stomp = null;
		}

		var ws = ((window.location.protocol === 'https:') ? 'wss://' : 'ws://') + window.location.host + '/websocket';
		this.stomp = WebStomp.client(ws, { debug: false }); //, { binary: true }); UNDERTOW-1249
		this.stomp.connect({ host: window.location.host }, this.connected, this.errored);
	}

	disconnected = (e) => {
		this.setState({
			connected: false
		});
		this.appendText('DISCONNECTED');
	}

	connected = (e) => {
		this.setState({
			connected: true
		});
		this.stomp.subscribe('queue/echo', this.handleMessage);
		this.appendText('CONNECTED');
	}

	errored = (e) => {
		this.setState({
			connected: false
		});
		
		if (e instanceof CloseEvent) {
			this.appendText('CONNECTION CLOSED: ' + e.reason);
			return;
		}

		let body = e.body;
		if (!body) {
			body = 'Unknown error. See console.';
		}
		this.appendText('ERROR: ' + body);
	}

	handleMessage = (e) => {
		this.appendText('<< ' + e.body);
	}

	handleChange = (e) => {
		this.setState({ sendText: e.target.value });
	}

	handleSubmit = (e) => {
		e.preventDefault();
		this.stomp.send('echo', '' + this.state.sendText + '', { 'content-type': 'text/plain' })
		this.appendText('>> ' + this.state.sendText);
	}

	appendText = (str) => {
		let text = this.state.text;
		if (text) {
			text += '\n';
		}
		const date = new Date().toISOString();
		text += date.substr(date.indexOf('T') + 1, date.length) + ': ' + str;
		this.setState({ text: text });
		this.outputArea.scrollTop = this.outputArea.scrollHeight;
	}

	render() {
		const { classes } = this.props;

		return (
				<div className='App'>
					<AppBar position='static'>
						<Toolbar>
							<Typography type='title' color='inherit' className='Flex'>
								Citõ Echo
							</Typography>
							<Button raised onClick={ this.startStomp }>
								{ this.state.connected ? 'Connected' : 'Disconnected' }
								{ this.state.connected ? (
										<Ok className={ classes.rightLeft } />
								) : (
										<Warning className={ classes.rightLeft } />
								)}
							</Button>
						</Toolbar>
					</AppBar>
					<div className={ classes.container }>
						<Grid container justify='center' spacing={ 24 }>
							<Grid item sm>
								<div>
									<p>
										Send a message and the requests and responses will appear in the output field.
									</p>
									<p>
										Try sending a non-alphanumeric character as this will trigger the validation, resulting in the connection being terminated.
									</p>
									<p>
										If you need to connect, post error, click the 'Disconnected' button.
									</p>
								</div>
							</Grid>
							<Grid item sm>
								<Paper className={ classes.form }>
									<form onSubmit={ this.handleSubmit }>
										<div>
											<textarea
													readOnly 
													rows='15'
													cols='80'
													ref={(el) => { this.outputArea = el; }}
													placeholder='Output will appear here!'
													value={ this.state.text } />
										</div>
										<div>
											<TextField
													id='message'
													label='Message' 
													margin='normal'
													autoFocus
													className={ classes.rightPadding }
													onChange={ this.handleChange } />
											<Button
													raised
													disabled={ !this.state.connected || this.state.sendText == null }
													color='primary'
													type='submit'>
												Send
											</Button>
										</div>
									</form>
								</Paper>
							</Grid>
						</Grid>
					</div>
				</div>
		);
	}
}

export default withStyles(styles)(App);
