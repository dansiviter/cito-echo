import React, { Component } from 'react';
import './App.css';
import { withStyles } from 'material-ui/styles';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import IconButton from 'material-ui/IconButton';
import Warning from 'material-ui-icons/Warning';
import Grid from 'material-ui/Grid';
import Paper from 'material-ui/Paper';
import TextField from 'material-ui/TextField';
import Button from 'material-ui/Button';

class App extends Component {
	render() {
		return (
				<div className="App">
					<AppBar position="static">
						<Toolbar>
							<Typography type="title" color="inherit" className="Flex">
								Citõ Echo
							</Typography>
							<IconButton color="contrast">
								<Warning />
							</IconButton>
						</Toolbar>
					</AppBar>
					<div className="Container">
						<Grid container justify="center" spacing={ 24 }>
							<Grid item xs={12}>
								<form>
									<TextField
											id="message"
											label="Message" 
											margin="normal" />
									<Button raised color="primary" >
										Send
									</Button>
								</form>
							</Grid>
						</Grid>
					</div>
				</div>
		);
	}
}

export default App;
